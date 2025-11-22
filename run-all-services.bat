@echo off
echo Starting all microservices...

start "Producers Service (8081)" cmd /k "cd services\producers-service && ..\..\mvnw spring-boot:run"
timeout /t 5

start "Trading Service (8082)" cmd /k "cd services\trading-service && ..\..\mvnw spring-boot:run"
timeout /t 5

start "Logistics Service (8083)" cmd /k "cd services\logistics-service && ..\..\mvnw spring-boot:run"
timeout /t 5

start "Inventory Service (8084)" cmd /k "cd services\inventory-service && ..\..\mvnw spring-boot:run"
timeout /t 5

start "Payments Service (8085)" cmd /k "cd services\payments-service && ..\..\mvnw spring-boot:run"
timeout /t 5

start "Certifications Service (8086)" cmd /k "cd services\certifications-service && ..\..\mvnw spring-boot:run"

echo All services started in separate windows.
