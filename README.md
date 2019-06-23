# nubank-challenge

```bash
docker-compose up
```

## Usage

### List Users

```bash
curl http://localhost:3000/v1/users
```

### Add a User

```bash
curl http://localhost:3000/v1/users \
    -X POST \
    -H 'Content-Type: application/json' \
    -d '{"name": "John Doe"}'
```

### Find a User

```bash
curl http://localhost:3000/v1/users/:userId
```

### Delete a User

```bash
curl -X DELETE http://localhost:3000/v1/users/:userId
```

## Testing

```bash
 docker-compose exec api lein test
```
