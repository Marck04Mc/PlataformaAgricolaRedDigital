import requests
import json

# Test what the producers service returns
response = requests.get('http://localhost:8081/api/productores')
print(f'Status Code: {response.status_code}')
print(f'Response:')
print(json.dumps(response.json(), indent=2))
