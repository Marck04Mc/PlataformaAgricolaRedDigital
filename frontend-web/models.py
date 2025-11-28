import sqlite3
from werkzeug.security import generate_password_hash, check_password_hash
from flask_login import UserMixin
import os
import uuid

# Use persistent data directory if available, otherwise current directory
DATA_DIR = os.getenv('DATA_DIR', '/app/data')
if not os.path.exists(DATA_DIR):
    # Fallback for local development if /app/data doesn't exist
    DATA_DIR = '.'
    
DATABASE = os.path.join(DATA_DIR, 'users.db')

class User(UserMixin):
    def __init__(self, id, username, email, role, user_uuid=None):
        self.id = id
        self.username = username
        self.email = email
        self.role = role
        self.uuid = user_uuid

def init_db():
    """Initialize the database with users table"""
    conn = sqlite3.connect(DATABASE)
    cursor = conn.cursor()
    
    # Create table if not exists
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            uuid TEXT UNIQUE,
            username TEXT UNIQUE NOT NULL,
            email TEXT UNIQUE NOT NULL,
            password_hash TEXT NOT NULL,
            role TEXT NOT NULL,
            nombre TEXT,
            identificacion TEXT,
            telefono TEXT,
            ciudad TEXT,
            direccion TEXT,
            finca TEXT,
            hectareas REAL,
            empresa TEXT,
            tipo_negocio TEXT,
            empresa_transporte TEXT,
            placa_vehiculo TEXT,
            capacidad INTEGER,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
    ''')
    
    # Check if uuid column exists and add it if not (Migration)
    try:
        cursor.execute('SELECT uuid FROM users LIMIT 1')
    except sqlite3.OperationalError:
        print("Migrating database: Adding uuid column...")
        try:
            cursor.execute('ALTER TABLE users ADD COLUMN uuid TEXT UNIQUE')
            conn.commit()
            # Update existing users with UUIDs
            cursor.execute('SELECT id FROM users WHERE uuid IS NULL')
            for row in cursor.fetchall():
                cursor.execute('UPDATE users SET uuid = ? WHERE id = ?', (str(uuid.uuid4()), row[0]))
            conn.commit()
            print("Migration completed successfully.")
        except Exception as e:
            print(f"Migration failed: {e}")
    
    # Create default admin user if not exists
    cursor.execute('SELECT * FROM users WHERE username = ?', ('admin',))
    if not cursor.fetchone():
        admin_hash = generate_password_hash('admin123')
        admin_uuid = str(uuid.uuid4())
        cursor.execute('''
            INSERT INTO users (username, email, password_hash, role, nombre, uuid)
            VALUES (?, ?, ?, ?, ?, ?)
        ''', ('admin', 'admin@agricola.com', admin_hash, 'admin', 'Administrador', admin_uuid))
    
    conn.commit()
    conn.close()

def create_user(username, email, password, role='productor', **kwargs):
    """Create a new user with additional profile information"""
    conn = sqlite3.connect(DATABASE)
    cursor = conn.cursor()
    
    try:
        password_hash = generate_password_hash(password)
        user_uuid = str(uuid.uuid4())
        
        # Build dynamic INSERT query based on provided kwargs
        fields = ['username', 'email', 'password_hash', 'role', 'uuid']
        values = [username, email, password_hash, role, user_uuid]
        
        # Add optional fields if provided
        optional_fields = ['nombre', 'identificacion', 'telefono', 'ciudad', 'direccion',
                          'finca', 'hectareas', 'empresa', 'tipo_negocio',
                          'empresa_transporte', 'placa_vehiculo', 'capacidad']
        
        for field in optional_fields:
            if field in kwargs and kwargs[field]:
                fields.append(field)
                values.append(kwargs[field])
        
        placeholders = ', '.join(['?' for _ in values])
        fields_str = ', '.join(fields)
        
        query = f'INSERT INTO users ({fields_str}) VALUES ({placeholders})'
        cursor.execute(query, values)
        conn.commit()
        return True
    except sqlite3.IntegrityError:
        return False
    finally:
        conn.close()

def get_user_by_username(username):
    """Get user by username"""
    conn = sqlite3.connect(DATABASE)
    cursor = conn.cursor()
    
    # Ensure migration runs if needed (double check)
    try:
        cursor.execute('SELECT uuid FROM users LIMIT 1')
    except sqlite3.OperationalError:
        init_db()

    cursor.execute('SELECT id, username, email, role, uuid FROM users WHERE username = ?', (username,))
    row = cursor.fetchone()
    conn.close()
    
    if row:
        return User(id=row[0], username=row[1], email=row[2], role=row[3], user_uuid=row[4])
    return None

def get_user_by_id(user_id):
    """Get user by ID"""
    conn = sqlite3.connect(DATABASE)
    cursor = conn.cursor()
    
    # Ensure migration runs if needed (double check)
    try:
        cursor.execute('SELECT uuid FROM users LIMIT 1')
    except sqlite3.OperationalError:
        init_db()
    
    cursor.execute('SELECT id, username, email, role, uuid FROM users WHERE id = ?', (user_id,))
    row = cursor.fetchone()
    conn.close()
    
    if row:
        return User(id=row[0], username=row[1], email=row[2], role=row[3], user_uuid=row[4])
    return None

def verify_password(username, password):
    """Verify user password"""
    conn = sqlite3.connect(DATABASE)
    cursor = conn.cursor()
    
    cursor.execute('SELECT password_hash FROM users WHERE username = ?', (username,))
    row = cursor.fetchone()
    conn.close()
    
    if row:
        return check_password_hash(row[0], password)
    return False
