from flask import Flask, render_template, request, jsonify, redirect, url_for
import requests
import os

app = Flask(__name__)

# Microservices URLs (use service names when running in Docker)
SERVICES = {
    'producers': os.getenv('PRODUCERS_URL', 'http://producers-service:8081/api/productores'),
    'trading': os.getenv('TRADING_URL', 'http://trading-service:8082/api/contratos'),
    'logistics': os.getenv('LOGISTICS_URL', 'http://logistics-service:8083/api/envios'),
    'inventory': os.getenv('INVENTORY_URL', 'http://inventory-service:8084/api/productos'),
    'payments': os.getenv('PAYMENTS_URL', 'http://payments-service:8085/api/pagos'),
    'certifications': os.getenv('CERTIFICATIONS_URL', 'http://certifications-service:8086/api/certificaciones')
}

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/productores')
def productores():
    try:
        response = requests.get(SERVICES['producers'], timeout=5)
        productores = response.json() if response.status_code == 200 else []
    except:
        productores = []
    return render_template('productores.html', productores=productores)

@app.route('/productores/crear', methods=['POST'])
def crear_productor():
    data = {
        'nombre': request.form['nombre'],
        'identificacion': request.form['identificacion']
    }
    try:
        requests.post(SERVICES['producers'], json=data, timeout=5)
    except:
        pass
    return redirect(url_for('productores'))

@app.route('/contratos')
def contratos():
    return render_template('contratos.html')

@app.route('/contratos/crear', methods=['POST'])
def crear_contrato():
    data = {
        'productorId': request.form['productorId'],
        'compradorId': request.form['compradorId'],
        'terminos': request.form['terminos']
    }
    try:
        requests.post(SERVICES['trading'], json=data, timeout=5)
    except:
        pass
    return redirect(url_for('contratos'))

@app.route('/envios')
def envios():
    return render_template('envios.html')

@app.route('/envios/crear', methods=['POST'])
def crear_envio():
    data = {
        'pedidoId': request.form['pedidoId'],
        'transportistaId': request.form['transportistaId']
    }
    try:
        requests.post(SERVICES['logistics'], json=data, timeout=5)
    except:
        pass
    return redirect(url_for('envios'))

@app.route('/productos')
def productos():
    return render_template('productos.html')

@app.route('/productos/crear', methods=['POST'])
def crear_producto():
    data = {
        'nombre': request.form['nombre'],
        'descripcion': request.form['descripcion'],
        'precio': float(request.form['precio']),
        'cantidad': float(request.form['cantidad']),
        'productorId': request.form['productorId']
    }
    try:
        requests.post(SERVICES['inventory'], json=data, timeout=5)
    except:
        pass
    return redirect(url_for('productos'))

@app.route('/pagos')
def pagos():
    return render_template('pagos.html')

@app.route('/pagos/crear', methods=['POST'])
def crear_pago():
    data = {
        'contratoId': request.form['contratoId'],
        'monto': float(request.form['monto']),
        'metodoPago': request.form['metodoPago']
    }
    try:
        requests.post(SERVICES['payments'], json=data, timeout=5)
    except:
        pass
    return redirect(url_for('pagos'))

@app.route('/certificaciones')
def certificaciones():
    return render_template('certificaciones.html')

@app.route('/certificaciones/crear', methods=['POST'])
def crear_certificacion():
    data = {
        'productorId': request.form['productorId'],
        'tipo': request.form['tipo'],
        'urlDocumento': request.form['urlDocumento']
    }
    try:
        requests.post(SERVICES['certifications'], json=data, timeout=5)
    except:
        pass
    return redirect(url_for('certificaciones'))

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
