# transfer-example

## Environment

- Java 11.0.2
- maven 3.6.0

## Build

```
$ mvn package
```

## Run

```
$ mvn exec:java
```

## Endpoints

- `POST /accounts` Create a new account
```
$ curl -d '{"name": "Alice", "amount": 100}' http://localhost:7000/accounts
{
    "id": 123,
    "name": "Alice",
    "amount": 100
}
```

- `GET /accounts` Get the list of accounts
```
$ curl http://localhost:7000/accounts
{
    "accounts": [
        {"id": 123, "name": "Alice", "amount": 100},{"id": 456, "name": "Bob", "amount": 100}
    ]
}
```

- `GET /accounts/:id` Get a single account with id
```
$ curl http://localhost:7000/accounts/123
{
    "id": 123,
    "name": "Alice",
    "amount": 100
}
```

- `POST /transfers` Create a new transfer
```
$ curl -d '{"from_user_id": 123, "to_user_id": 456, "amount": 51}' http://localhost:7000/accounts
{
    "id": 789,
    "from_user_id": 123,
    "to_user_id": 456,
    "amount": 51
}
```

- `GET /transfers` Get the list of transfers
```
$ curl http://localhost:7000/transfers
{
    "transfers": [
        {"id": 789, "from_user_id": 123, "to_user_id": 456, "amount": 51}
    ]
}
```

- `GET /transfers/:id` Get a single transfer
```
$ curl http://localhost:7000/transfers/789
{
    "id": 789,
    "from_user_id": 123,
    "to_user_id": 456,
    "amount": 51
}
```
