import requests
import uuid

BASE_URL = 'http://localhost:5000'
SESSION = requests.Session()

def test_product_registration():
    print('=== PRUEBA DE REGISTRO DE PRODUCTO ===\n')
    
    # 1. Login como productor
    print('1. Iniciando sesión como admin...')
    login_response = SESSION.post(f'{BASE_URL}/login', data={
        'username': 'admin',
        'password': 'admin123'
    })
    
    if login_response.status_code == 200:
        print('   ✅ Login exitoso\n')
    else:
        print('   ❌ Error en login')
        return
    
    # 2. Registrar un productor
    print('2. Registrando productor de prueba...')
    productor_identificacion = '1234567890'
    productor_data = {
        'nombre': 'Juan Pérez',
        'identificacion': productor_identificacion
    }
    
    productor_response = requests.post('http://localhost:8081/api/productores', json=productor_data)
    if productor_response.status_code in [200, 201]:
        print(f'   ✅ Productor registrado: {productor_data["nombre"]} (ID: {productor_identificacion})\n')
    else:
        print(f'   ⚠️  Productor ya existe o error: {productor_response.status_code}\n')
    
    # 3. Registrar un producto
    print('3. Registrando producto...')
    producto_data = {
        'nombre': 'Café Orgánico Premium',
        'descripcion': 'Café 100% orgánico cultivado en las montañas',
        'imagenUrl': 'https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=400',
        'precio': '15.50',
        'cantidad': '100',
        'productorIdentificacion': productor_identificacion
    }
    
    producto_response = SESSION.post(f'{BASE_URL}/productos/crear', data=producto_data)
    
    if producto_response.status_code == 200:
        print('   ✅ Producto registrado exitosamente\n')
    else:
        print(f'   ❌ Error al registrar producto: {producto_response.status_code}\n')
    
    # 4. Verificar que el producto se guardó
    print('4. Verificando productos registrados...')
    productos_response = requests.get('http://localhost:8084/api/productos')
    
    if productos_response.status_code == 200:
        productos = productos_response.json()
        print(f'   Total de productos en el sistema: {len(productos)}')
        
        # Buscar nuestro producto
        cafe_producto = next((p for p in productos if p.get('nombre') == 'Café Orgánico Premium'), None)
        
        if cafe_producto:
            print('\n   ✅ ¡PRODUCTO ENCONTRADO!')
            print(f'      - Nombre: {cafe_producto["nombre"]}')
            print(f'      - Precio: ${cafe_producto["precio"]}')
            print(f'      - Cantidad: {cafe_producto["cantidadDisponible"]} kg')
            print(f'      - Productor ID: {cafe_producto["productorId"]}')
        else:
            print('\n   ❌ Producto no encontrado en la lista')
    else:
        print(f'   ❌ Error al obtener productos: {productos_response.status_code}')
    
    print('\n=== FIN DE LA PRUEBA ===')

if __name__ == '__main__':
    test_product_registration()
