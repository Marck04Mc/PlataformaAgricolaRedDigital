from flask import Flask, render_template, request, jsonify, redirect, url_for, flash, session
from flask_login import LoginManager, login_user, logout_user, login_required, current_user
from functools import wraps
import requests
import os
import uuid
from models import init_db, get_user_by_username, get_user_by_id, verify_password, create_user

app = Flask(__name__)
app.secret_key = 'plataforma-agricola-secret-key-2024'

# Initialize Flask-Login
login_manager = LoginManager()
login_manager.init_app(app)
login_manager.login_view = 'login'

# Initialize database
init_db()

@login_manager.user_loader
def load_user(user_id):
    return get_user_by_id(int(user_id))

# Role-based access decorator
def role_required(*roles):
    def decorator(f):
        @wraps(f)
        def decorated_function(*args, **kwargs):
            if not current_user.is_authenticated:
                return redirect(url_for('login'))
            if current_user.role not in roles:
                flash('No tienes permisos para acceder a esta página', 'error')
                return redirect(url_for('index'))
            return f(*args, **kwargs)
        return decorated_function
    return decorator

# Microservices URLs
SERVICES = {
    'producers': os.getenv('PRODUCERS_URL', 'http://producers-service:8081/api/productores'),
    'trading': os.getenv('TRADING_URL', 'http://trading-service:8082/api/contratos'),
    'logistics': os.getenv('LOGISTICS_URL', 'http://logistics-service:8083/api/envios'),
    'inventory': os.getenv('INVENTORY_URL', 'http://inventory-service:8084/api/productos'),
    'payments': os.getenv('PAYMENTS_URL', 'http://payments-service:8085/api/pagos'),
    'certifications': os.getenv('CERTIFICATIONS_URL', 'http://certifications-service:8086/api/certificaciones')
}

@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        
        if verify_password(username, password):
            user = get_user_by_username(username)
            login_user(user)
            flash('¡Bienvenido!', 'success')
            
            if user.role == 'productor':
                return redirect(url_for('dashboard_productor'))
            elif user.role == 'comprador':
                return redirect(url_for('dashboard_comprador'))
            elif user.role == 'transportista':
                return redirect(url_for('dashboard_transportista'))
            elif user.role == 'admin':
                return redirect(url_for('dashboard_admin'))
            else:
                return redirect(url_for('index'))
        else:
            flash('Usuario o contraseña incorrectos', 'error')
    
    return render_template('login.html')

@app.route('/register', methods=['GET', 'POST'])
def register():
    if request.method == 'POST':
        username = request.form['username']
        email = request.form['email']
        password = request.form['password']
        role = request.form.get('role', 'productor')
        
        additional_fields = {
            'nombre': request.form.get('nombre'),
            'identificacion': request.form.get('identificacion'),
            'telefono': request.form.get('telefono'),
            'ciudad': request.form.get('ciudad'),
            'direccion': request.form.get('direccion'),
            'finca': request.form.get('finca'),
            'hectareas': request.form.get('hectareas'),
            'empresa': request.form.get('empresa'),
            'tipo_negocio': request.form.get('tipo_negocio'),
            'empresa_transporte': request.form.get('empresa_transporte'),
            'placa_vehiculo': request.form.get('placa_vehiculo'),
            'capacidad': request.form.get('capacidad')
        }
        
        if create_user(username, email, password, role, **additional_fields):
            flash('Usuario registrado exitosamente. Por favor inicia sesión.', 'success')
            return redirect(url_for('login'))
        else:
            flash('El usuario o email ya existe', 'error')
    
    return render_template('register.html')

@app.route('/logout')
@login_required
def logout():
    logout_user()
    flash('Sesión cerrada exitosamente', 'success')
    return redirect(url_for('login'))

@app.route('/')
@login_required
def index():
    return render_template('index.html')

# --- DASHBOARDS ---

@app.route('/dashboard/productor')
@login_required
@role_required('admin', 'productor')
def dashboard_productor():
    try: productos = requests.get(SERVICES['inventory'], timeout=2).json()
    except: productos = []
    try: contratos = requests.get(SERVICES['trading'], timeout=2).json()
    except: contratos = []
    try: envios = requests.get(SERVICES['logistics'], timeout=2).json()
    except: envios = []
    try: certificaciones = requests.get(SERVICES['certifications'], timeout=2).json()
    except: certificaciones = []
    return render_template('dashboard_productor.html', productos=productos, contratos=contratos, envios=envios, certificaciones=certificaciones)

@app.route('/dashboard/comprador')
@login_required
@role_required('admin', 'comprador')
def dashboard_comprador():
    try: contratos = requests.get(SERVICES['trading'], timeout=2).json()
    except: contratos = []
    try: envios = requests.get(SERVICES['logistics'], timeout=2).json()
    except: envios = []
    try: pagos = requests.get(SERVICES['payments'], timeout=2).json()
    except: pagos = []
    try: productores = requests.get(SERVICES['producers'], timeout=2).json()
    except: productores = []
    return render_template('dashboard_comprador.html', contratos=contratos, envios=envios, pagos=pagos, productores=productores)

@app.route('/dashboard/transportista')
@login_required
@role_required('admin', 'transportista')
def dashboard_transportista():
    try: envios = requests.get(SERVICES['logistics'], timeout=2).json()
    except: envios = []
    pendientes = len([e for e in envios if e.get('estado') == 'PENDIENTE'])
    entregados = len([e for e in envios if e.get('estado') == 'ENTREGADO'])
    return render_template('dashboard_transportista.html', envios=envios, envios_pendientes=pendientes, envios_entregados=entregados)

@app.route('/dashboard/admin')
@login_required
@role_required('admin')
def dashboard_admin():
    try: productos = requests.get(SERVICES['inventory'], timeout=2).json()
    except: productos = []
    try: contratos = requests.get(SERVICES['trading'], timeout=2).json()
    except: contratos = []
    try: envios = requests.get(SERVICES['logistics'], timeout=2).json()
    except: envios = []
    try: pagos = requests.get(SERVICES['payments'], timeout=2).json()
    except: pagos = []
    try: certificaciones = requests.get(SERVICES['certifications'], timeout=2).json()
    except: certificaciones = []
    return render_template('dashboard_admin.html', productos=productos, contratos=contratos, envios=envios, pagos=pagos, certificaciones=certificaciones)

# --- MARKETPLACE & CART ---

@app.route('/marketplace')
@login_required
@role_required('admin', 'comprador')
def marketplace():
    try:
        productos = requests.get(SERVICES['inventory'], timeout=2).json()
    except: productos = []
    return render_template('marketplace.html', productos=productos)

@app.route('/marketplace/checkout', methods=['POST'])
@login_required
@role_required('admin', 'comprador')
def checkout_cart():
    try:
        data = request.get_json()
        items = data.get('items', [])
        
        if not items:
            return jsonify({'success': False, 'message': 'Carrito vacío'})
        
        total_amount = sum(item['price'] * item['quantity'] for item in items)
        
        contratos_creados = []
        for item in items:
            producto_response = requests.get(f"{SERVICES['inventory']}/{item['id']}", timeout=5)
            if producto_response.status_code == 200:
                producto = producto_response.json()
                contrato_data = {
                    'productorId': producto['productorId'],
                    'compradorId': current_user.uuid,  # Use UUID instead of ID
                    'terminos': f"Compra de {item['quantity']}kg de {item['name']} a ${item['price']}/kg"
                }
                contrato_response = requests.post(SERVICES['trading'], json=contrato_data, timeout=5)
                if contrato_response.status_code == 200:
                    contratos_creados.append(str(uuid.uuid4()))
        
        if not contratos_creados:
             return jsonify({'success': False, 'message': 'No se pudieron crear los contratos'})

        pago_data = {
            'contratoId': contratos_creados[0],
            'monto': total_amount,
            'metodoPago': 'TARJETA_CREDITO'
        }
        
        pago_response = requests.post(SERVICES['payments'], json=pago_data, timeout=5)
        
        if pago_response.status_code == 200:
            session['pending_purchase'] = {
                'items': items,
                'total': total_amount,
                'contratos': contratos_creados
            }
            return jsonify({'success': True, 'message': 'Compra procesada exitosamente'})
        else:
            return jsonify({'success': False, 'message': 'Error al procesar el pago'})
            
    except Exception as e:
        return jsonify({'success': False, 'message': str(e)})

# --- PRODUCTOS ---

@app.route('/productos')
@login_required
def productos():
    try:
        response = requests.get(SERVICES['inventory'], timeout=5)
        productos = response.json() if response.status_code == 200 else []
    except:
        productos = []
    
    # Fetch productores to enrich product list
    try:
        productores_response = requests.get(SERVICES['producers'], timeout=5)
        productores = productores_response.json() if productores_response.status_code == 200 else []
    except:
        productores = []
    
    # Create a map of productorId -> productor name
    productores_map = {p['id']: p['nombre'] for p in productores}
    
    # Enrich productos with producer names
    for producto in productos:
        producto['productorNombre'] = productores_map.get(producto.get('productorId'), 'Desconocido')
    
    return render_template('productos.html', productos=productos, productores=productores)

@app.route('/productos/crear', methods=['POST'])
@login_required
@role_required('admin', 'productor')
def crear_producto():
    try:
        print(f"DEBUG: Form data: {dict(request.form)}")  # Debug log
        
        # Get producer identification from form
        productor_identificacion = request.form.get('productorIdentificacion')
        print(f"DEBUG: Looking for producer with ID: {productor_identificacion}")  # Debug log
        
        if not productor_identificacion:
            flash('Debe ingresar la identificación del productor', 'error')
            return redirect(url_for('productos'))
        
        # Fetch all producers to find the one with matching identification
        productores_response = requests.get(SERVICES['producers'], timeout=5)
        if productores_response.status_code != 200:
            flash('Error al obtener lista de productores', 'error')
            return redirect(url_for('productos'))
        
        productores = productores_response.json()
        productor = next((p for p in productores if p.get('identificacion') == productor_identificacion), None)
        
        if not productor:
            flash(f'No se encontró un productor con identificación: {productor_identificacion}', 'error')
            return redirect(url_for('productos'))
        
        print(f"DEBUG: Found producer: {productor}")  # Debug log
        
        # Create product with producer UUID
        data = {
            'nombre': request.form['nombre'],
            'descripcion': request.form['descripcion'],
            'imagenUrl': request.form['imagenUrl'],
            'precio': float(request.form['precio']),
            'cantidadDisponible': float(request.form['cantidad']),
            'productorId': productor['id']  # Use the UUID from the found producer
        }
        
        print(f"DEBUG: Sending product data: {data}")  # Debug log
        
        response = requests.post(SERVICES['inventory'], json=data, timeout=5)
        print(f"DEBUG: Response status: {response.status_code}, body: {response.text}")  # Debug log
        
        if response.status_code == 200 or response.status_code == 201:
            flash('Producto registrado exitosamente', 'success')
        else:
            flash(f'Error al registrar producto: {response.text}', 'error')
    except Exception as e:
        print(f"DEBUG: Exception occurred: {str(e)}")  # Debug log
        import traceback
        traceback.print_exc()
        flash(f'Error al registrar producto: {str(e)}', 'error')
    return redirect(url_for('productos'))

# --- PRODUCTORES ---

@app.route('/productores')
@login_required
@role_required('admin', 'productor')
def productores():
    try:
        response = requests.get(SERVICES['producers'], timeout=5)
        productores = response.json() if response.status_code == 200 else []
    except: productores = []
    return render_template('productores.html', productores=productores)

@app.route('/productores/crear', methods=['POST'])
@login_required
@role_required('admin', 'productor')
def crear_productor():
    data = {'nombre': request.form['nombre'], 'identificacion': request.form['identificacion']}
    try:
        requests.post(SERVICES['producers'], json=data, timeout=5)
        flash('Productor registrado exitosamente', 'success')
    except:
        flash('Error al registrar productor', 'error')
    return redirect(url_for('productores'))

# --- CONTRATOS ---

@app.route('/contratos')
@login_required
@role_required('admin', 'productor', 'comprador')
def contratos():
    try:
        response = requests.get(SERVICES['trading'], timeout=5)
        contratos = response.json() if response.status_code == 200 else []
    except: contratos = []
    return render_template('contratos.html', contratos=contratos)

@app.route('/contratos/crear', methods=['POST'])
@login_required
@role_required('admin', 'productor', 'comprador')
def crear_contrato():
    data = {
        'productorId': request.form['productorId'],
        'compradorId': request.form['compradorId'],
        'terminos': request.form['terminos']
    }
    try:
        requests.post(SERVICES['trading'], json=data, timeout=5)
        flash('Contrato creado exitosamente', 'success')
    except:
        flash('Error al crear contrato', 'error')
    return redirect(url_for('contratos'))

# --- ENVIOS ---

@app.route('/envios')
@login_required
@role_required('admin', 'transportista', 'comprador')
def envios():
    try:
        response = requests.get(SERVICES['logistics'], timeout=5)
        envios = response.json() if response.status_code == 200 else []
    except: envios = []
    return render_template('envios.html', envios=envios)

@app.route('/envios/crear', methods=['POST'])
@login_required
@role_required('admin', 'transportista')
def crear_envio():
    data = {'pedidoId': request.form['pedidoId'], 'transportistaId': request.form['transportistaId']}
    try:
        requests.post(SERVICES['logistics'], json=data, timeout=5)
        flash('Envío registrado exitosamente', 'success')
    except:
        flash('Error al registrar envío', 'error')
    return redirect(url_for('envios'))

@app.route('/envios/actualizar/<id>', methods=['POST'])
@login_required
@role_required('admin', 'transportista')
def actualizar_envio(id):
    nuevo_estado = request.form['nuevoEstado']
    try:
        requests.put(f"{SERVICES['logistics']}/{id}", json={'estado': nuevo_estado}, timeout=5)
        flash('Estado del envío actualizado', 'success')
    except:
        flash('Error al actualizar estado', 'error')
    return redirect(url_for('envios'))

# --- PAGOS ---

@app.route('/pagos')
@login_required
@role_required('admin', 'comprador')
def pagos():
    try:
        response = requests.get(SERVICES['payments'], timeout=5)
        pagos = response.json() if response.status_code == 200 else []
    except: pagos = []
    return render_template('pagos.html', pagos=pagos)

@app.route('/pagos/crear', methods=['POST'])
@login_required
@role_required('admin', 'comprador')
def crear_pago():
    data = {
        'contratoId': request.form['contratoId'],
        'monto': float(request.form['monto']),
        'metodoPago': request.form['metodoPago']
    }
    try:
        requests.post(SERVICES['payments'], json=data, timeout=5)
        flash('Pago procesado exitosamente', 'success')
    except:
        flash('Error al procesar pago', 'error')
    return redirect(url_for('pagos'))

# --- CERTIFICACIONES ---

@app.route('/certificaciones')
@login_required
@role_required('admin', 'productor')
def certificaciones():
    try:
        response = requests.get(SERVICES['certifications'], timeout=5)
        certificaciones = response.json() if response.status_code == 200 else []
    except: certificaciones = []
    return render_template('certificaciones.html', certificaciones=certificaciones)

@app.route('/certificaciones/crear', methods=['POST'])
@login_required
@role_required('admin', 'productor')
def crear_certificacion():
    data = {
        'productorId': request.form['productorId'],
        'tipo': request.form['tipo'],
        'urlDocumento': request.form['urlDocumento']
    }
    try:
        requests.post(SERVICES['certifications'], json=data, timeout=5)
        flash('Certificación registrada exitosamente', 'success')
    except:
        flash('Error al registrar certificación', 'error')
    return redirect(url_for('certificaciones'))

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
