# nubank-challenge

Checks and Balances: A Nubank Challenge!

## tl;dr

This project defines a RESTful API to manage users and transactions, money in or
out, controlling the balance. It was developed using Clojure as programming
language and Leiningen for automating and configuration.

## Getting Started

Nubank's Challenge can be started using `docker-compose`.

```bash
docker-compose up
```

Everything will be attached locally on port `3000`.

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
curl http://localhost:3000/v1/users/:userId \
    -X DELETE
```

### List Transactions

```bash
curl http://localhost:3000/v1/users/:userId/transactions
```

### Add a Transaction

```bash
curl http://localhost:3000/v1/users/:userId/transactions \
    -X POST \
    -H 'Content-Type: application/json' \
    -d '{"type": "IN", "value": 1.99}'
```

### Find a Transaction

```bash
curl http://localhost:3000/v1/users/:userId/transactions/:transactionId
```

### Delete a Transaction

```bash
curl http://localhost:3000/v1/users/:userId/transactions/:transactionId \
    -X DELETE
```

### Find the Balance for User

```bash
curl http://localhost:3000/v1/users/:userId/balance
```

## Documentation

Source code documentation isn't available on start. To generate docs, call
`docker-compose` command with `lein codox`.

```bash
docker-compose exec api lein codox
```

Browse it using
[http://localhost:3000/doc/index.html](http://localhost:3000/doc/index.html).

## Testing

This project was designed and developed using TDD as main concept. To execute
the available tests, call `docker-compose` command too with `lein test`.

```bash
docker-compose exec api lein test
```
