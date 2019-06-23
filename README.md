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

### List Transactions

```bash
curl http://localhost:3000/v1/users/:userId/transactions
```

### Add a Transaction

```bash
curl http://localhost:3000/v1/users/:userId/transactions \
    -X POST \
    -H 'Content-Type: application/json'
    -d '{"type": "IN", "value": 1.99}'
```

### Find a Transaction

```bash
curl http://localhost:3000/v1/users/:userId/transactions/:transactionId
```

### Delete a Transaction

```bash
curl -X DELETE http://localhost:3000/v1/users/:userId/transactions/:transactionId
```

## Testing

```bash
docker-compose exec api lein test
```
