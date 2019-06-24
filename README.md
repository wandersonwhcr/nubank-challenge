# nubank-challenge

Checks and Balances: A Nubank Challenge!

## tl;dr

This project defines a RESTful API to manage users and transactions, money in or
out, controlling the balance. It uses Clojure as programming language.

## Getting Started

Nubank's Challenge can be started using `docker-compose`.

```bash
docker-compose up
```

Everything will be attached locally on port `3000`.

## Usage

Users and transactions can be managed using a REST structure. Every available
request and response are denoted above. Examples are using curl but any HTTP
client can be used. Resources are identified using random UUID and new resources
are informed at response headers.

### List Users

```bash
curl http://localhost:3000/v1/users

HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: 2

[]
```

### Add a User

```bash
curl http://localhost:3000/v1/users \
    -X POST \
    -H 'Content-Type: application/json' \
    -d '{"name": "John Doe"}'

HTTP/1.1 201 Created
Location: /v1/users/8399f5c7-4d19-41a4-8cf5-9484fa6564eb
X-Resource-Identifier: 8399f5c7-4d19-41a4-8cf5-9484fa6564eb
Content-Length: 0
```

### Find a User

```bash
curl http://localhost:3000/v1/users/:userId

HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: 63

{"id":"8399f5c7-4d19-41a4-8cf5-9484fa6564eb","name":"John Doe"}
```

### Delete a User

```bash
curl http://localhost:3000/v1/users/:userId \
    -X DELETE

HTTP/1.1 204 No Content
Date: Sun, 23 Jun 2019 23:52:50 GMT
Server: Jetty(9.2.21.v20170120)
```

### List Transactions

```bash
curl http://localhost:3000/v1/users/:userId/transactions

HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: 2

[]
```

### Add a Transaction

```bash
curl http://localhost:3000/v1/users/:userId/transactions \
    -X POST \
    -H 'Content-Type: application/json' \
    -d '{"type": "IN", "value": 1.99}'

HTTP/1.1 201 Created
Location: /v1/users/8399f5c7-4d19-41a4-8cf5-9484fa6564eb/transactions/bcd478ed-617b-495d-895a-9a7b55aacba1
X-Resource-Identifier: bcd478ed-617b-495d-895a-9a7b55aacba1
Content-Length: 0
```

### Find a Transaction

```bash
curl http://localhost:3000/v1/users/:userId/transactions/:transactionId

HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: 119

{"id":"bcd478ed-617b-495d-895a-9a7b55aacba1","user-id":"24f13323-d9d5-410e-a02a-776933115a1c","type":"IN","value":1.99}
```

### Delete a Transaction

```bash
curl http://localhost:3000/v1/users/:userId/transactions/:transactionId \
    -X DELETE

HTTP/1.1 204 No Content
```

### Find the Balance for User

```bash
curl http://localhost:3000/v1/users/:userId/balance

HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Content-Length: 13

{"value":1.99}
```

## Technologies

Everything was developed using Clojure as programming language, using hashmaps
on memory to store data from users and transactions. To take advantage of
functional programming, immutability and chaining functions was used.

Ring was choiced as HTTP Server to run this RESTful API, routing requests with
Compojure. Leiningen was used to automate some development tasks, like testing
or generating documentation. This source was developed using TDD techniques and
Code Coverage.

This project was designed with SoC and Service-oriented architecture, built with
2 layers: controllers and services. Requests are handle on controller layer and
service layer processes the results, that is returned by controller. If the
request generates an error, the controller handles the exceptions and outputs
the correct response code. Input is validated through Clojure records and JSON
Schemas.

To avoid floating point arithmetic problems and execute the correct handling of
decimal values, a calculator was created using BigDecimals. Atom variables and
object locking prevent memory leak, because multiple requests to the same user
can break the balance calculation.

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
