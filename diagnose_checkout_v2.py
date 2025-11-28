import requests
import uuid

# Config
BASE_URL = 'http://localhost:5000'
SESSION = requests.Session()

def run_test():
    print('=== INICIANDO DIAGNÓSTICO DE COMPRA (DETALLADO) ===')

    # 1. Registrar y Login Comprador
    username = f'buyer_{uuid.uuid4().hex[:6]}'
    print(f'1. Registrando comprador: {username}...')
    SESSION.post(f'{BASE_URL}/register', data={
        'username': username,
        'email': f'{username}@test.com',
        'password': '123',
        'role': 'comprador',
        'nombre': 'Test Buyer',
        'identificacion': '12345',
        'telefono': '555555',
        'ciudad': 'Test City',
        'direccion': 'Test Address'
    })
    
    SESSION.post(f'{BASE_URL}/login', data={'username': username, 'password': '123'})

    # 2. Obtener producto
    try:
        r = requests.get('http://localhost:8084/api/productos')
        productos = r.json()
        if not productos:
            print('❌ No hay productos.')
            return
        producto = productos[0]
    except:
        print('❌ Error obteniendo productos.')
        return

    # 3. Probar Checkout
    print(f'3. Intentando comprar: {producto["nombre"]}')
    cart_items = [{
        'id': producto['id'],
        'name': producto['nombre'],
        'price': producto['precio'],
        'quantity': 1.0,
        'imageUrl': producto.get('imagenUrl', '')
    }]
    
    try:
        r = SESSION.post(f'{BASE_URL}/marketplace/checkout', json={'items': cart_items})
        print(f'Status Code: {r.status_code}')
        print(f'Response Text: {r.text}') # AQUÍ VEREMOS EL ERROR EXACTO
    except Exception as e:
        print(f'Excepción: {e}')

if __name__ == '__main__':
    run_test()
