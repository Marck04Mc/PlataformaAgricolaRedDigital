import requests
import uuid

# Config
BASE_URL = 'http://localhost:5000'
SESSION = requests.Session()

def run_test():
    print('=== INICIANDO DIAGNÓSTICO DE COMPRA ===')

    # 1. Registrar y Login Comprador
    username = f'buyer_{uuid.uuid4().hex[:6]}'
    print(f'1. Registrando comprador: {username}...')
    r = SESSION.post(f'{BASE_URL}/register', data={
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
    
    r = SESSION.post(f'{BASE_URL}/login', data={
        'username': username,
        'password': '123'
    })
    print(f'   Login status: {r.status_code}')

    # 2. Obtener un producto para comprar
    print('\n2. Buscando productos...')
    try:
        r = requests.get('http://localhost:8084/api/productos')
        productos = r.json()
        if not productos:
            print('   ❌ ERROR: No hay productos en el inventario para probar.')
            return
        
        producto = productos[0]
        print(f'   Producto encontrado: {producto["nombre"]} (ID: {producto["id"]})')
    except Exception as e:
        print(f'   ❌ ERROR conectando al servicio de inventario: {e}')
        return

    # 3. Probar Checkout
    print('\n3. Probando Finalizar Compra (Checkout)...')
    cart_items = [{
        'id': producto['id'],
        'name': producto['nombre'],
        'price': producto['precio'],
        'quantity': 1.0,
        'imageUrl': producto.get('imagenUrl', '')
    }]
    
    try:
        r = SESSION.post(f'{BASE_URL}/marketplace/checkout', json={'items': cart_items})
        print(f'   Status Code: {r.status_code}')
        print(f'   Respuesta: {r.text}')
        
        if r.status_code == 200 and r.json().get('success'):
            print('\n✅ EL BACKEND FUNCIONA CORRECTAMENTE.')
            print('   Si no te deja finalizar en el navegador, es un problema de JavaScript o caché.')
        else:
            print('\n❌ EL BACKEND FALLÓ.')
            print('   Revisar logs de los microservicios (Trading o Payments).')
            
    except Exception as e:
        print(f'   ❌ Error en la petición: {e}')

if __name__ == '__main__':
    run_test()
