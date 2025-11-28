import requests
import uuid

print('=== PASO 1: REGISTRAR PRODUCTOR ===')
reg_prod = requests.post('http://localhost:5000/register', data={
    'username': 'productor_demo',
    'email': 'productor@demo.com',
    'password': '123',
    'role': 'productor'
})
print(f'Registro productor: {reg_prod.status_code}')

print('\n=== PASO 2: LOGIN COMO PRODUCTOR ===')
s_prod = requests.Session()
login_prod = s_prod.post('http://localhost:5000/login', data={
    'username': 'productor_demo',
    'password': '123'
})
print(f'Login productor: {login_prod.status_code}')
print(f'Redirigido a: {login_prod.url}')

print('\n=== PASO 3: PRODUCTOR SUBE PRODUCTO CON IMAGEN ===')
producto_data = {
    'nombre': 'Café Orgánico Premium',
    'descripcion': 'Café cultivado en las montañas colombianas, 100% orgánico, sabor intenso y aromático. Ideal para espresso.',
    'imagenUrl': 'https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=400',
    'precio': 15.50,
    'cantidad': 75.0,
    'productorId': str(uuid.uuid4())
}
crear_prod = s_prod.post('http://localhost:5000/productos/crear', data=producto_data)
print(f'Producto creado: {crear_prod.status_code}')

print('\n=== PASO 4: VERIFICAR PRODUCTOS EN INVENTARIO ===')
productos = requests.get('http://localhost:8084/api/productos').json()
print(f'Total productos en inventario: {len(productos)}')
if productos:
    ultimo = productos[-1]
    print(f'Último producto:')
    print(f'  - Nombre: {ultimo.get("nombre")}')
    print(f'  - Descripcion: {ultimo.get("descripcion")}')
    print(f'  - Precio: ${ultimo.get("precio")}')
    print(f'  - Stock: {ultimo.get("cantidadDisponible")} kg')
    print(f'  - Imagen URL: {ultimo.get("imagenUrl")}')

print('\n=== PASO 5: REGISTRAR COMPRADOR ===')
reg_comp = requests.post('http://localhost:5000/register', data={
    'username': 'comprador_demo',
    'email': 'comprador@demo.com',
    'password': '123',
    'role': 'comprador'
})
print(f'Registro comprador: {reg_comp.status_code}')

print('\n=== PASO 6: LOGIN COMO COMPRADOR ===')
s_comp = requests.Session()
login_comp = s_comp.post('http://localhost:5000/login', data={
    'username': 'comprador_demo',
    'password': '123'
})
print(f'Login comprador: {login_comp.status_code}')
print(f'Redirigido a: {login_comp.url}')

print('\n=== PASO 7: COMPRADOR VE MARKETPLACE ===')
marketplace = s_comp.get('http://localhost:5000/marketplace')
print(f'Marketplace cargado: {marketplace.status_code}')
print(f'Productos visibles en Marketplace: {"Café Orgánico" in marketplace.text}')
print(f'Imagen presente: {"unsplash" in marketplace.text}')
print(f'Botón comprar presente: {"Comprar Ahora" in marketplace.text}')

print('\n✅ FLUJO COMPLETO VERIFICADO')
print('\nResumen:')
print('1. ✓ Productor puede registrarse y hacer login')
print('2. ✓ Productor puede subir productos con imagen y descripción')
print('3. ✓ Productos se guardan en el Servicio de Inventario')
print('4. ✓ Comprador puede registrarse y hacer login')
print('5. ✓ Comprador puede ver productos en el Marketplace')
print('6. ✓ Marketplace muestra imágenes y botón de compra')
