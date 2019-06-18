# nubank-challenge

```bash
docker run --interactive --tty \
    --volume `pwd`/app:/usr/src/app \
    --workdir /usr/src/app \
    --publish 3000:3000 \
    clojure:alpine \
    lein ring server
```
