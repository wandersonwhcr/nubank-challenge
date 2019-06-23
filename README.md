# nubank-challenge

```bash
docker-compose up
```

## Usage

### List Users

```
curl http://localhost:3000/v1/users
```

### Add a User

```
curl http://localhost:3000/v1/users \
    -X POST \
    -H 'Content-Type: application/json' \
    -d '{"name": "John Doe"}'
```

### Find a User

```
curl http://localhost:3000/v1/users/:userId
```

### Delete a User

```
curl -X DELETE http://localhost:3000/v1/users/:userId
```
