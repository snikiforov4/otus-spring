# Yet Another Twitter Clone

## Run MongoDB inside docker container

#### Pull image (optional):
```
docker pull mongo:4.0.1
```

#### Run container
Before run container. Set up variable (path **must** be absolute!): <br /> 
`export MONGO_VOLUME=/Users/$(whoami)/mongo/twitter-data` <br />

```
docker run --name twitter-mongo \
	-v $MONGO_VOLUME:/data/db \
	-e MONGO_INITDB_ROOT_USERNAME=dbuser \
	-e MONGO_INITDB_ROOT_PASSWORD=dbsecret \
	-p 127.0.0.1:27018:27017 \
	-d mongo:4.0.1
```

#### Connect to MongoDB
```
docker exec -it twitter-mongo mongo --port 27017 -u 'dbuser' -p 'dbsecret' --authenticationDatabase 'admin'
```
