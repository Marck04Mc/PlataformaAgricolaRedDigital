@echo off
echo Instalando dependencias de Python...
python -m pip install -r requirements.txt

echo.
echo Iniciando aplicacion web...
python app.py
