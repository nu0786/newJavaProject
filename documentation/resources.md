# Create an account
```
curl http://localhost:8090/api/account/ -X POST \
-H 'Content-Type: application/json' \
-d '{"fullName": "john doe", "email": "john@doe.com", "phone": "079896854123", "birthdate": "1987-12-23", "currency": "usd"}'
```

# Find an account
```
curl http://localhost:8090/api/account/:accountNumber -X GET \
-H 'Content-Type: application/json'
```