## Run MongoDB inside docker container

#### Pull image (optional):
```
docker pull mongo:4.0.1
```

#### Run container
Before run container <br /> 
`export MONGO_VOLUME=/Users/$(whoami)/mongo/data` <br />
Path should be absolute

```
docker run --name library-mongo \
	-v $MONGO_VOLUME:/data/db \
	-e MONGO_INITDB_ROOT_USERNAME=dbuser \
	-e MONGO_INITDB_ROOT_PASSWORD=dbsecret \
	-d mongo:4.0.1
```

#### Connect to MongoDB
```
docker exec -it library-mongo mongo --port 27017 -u 'dbuser' -p 'dbsecret' --authenticationDatabase 'admin'
```
