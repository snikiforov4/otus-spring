## Run postgres inside docker container

#### Pull image (optional):
```
docker pull postgres:10.5
```

#### Run container
Before run container <br /> 
`export POSTGRES_VOLUME=/Users/user/postgresql/data` <br />
Path should be absolute <br />

```
docker run --name library-postgres \
	-e POSTGRES_DB=library \
	-v $POSTGRES_VOLUME:/var/lib/postgresql/data \
	-p 127.0.0.1:15432:5432 \
	-d postgres:10.5
```

#### Connect to postgres 

```
docker exec -it library-postgres psql -U postgres 
```

##### Run as `postgres`
```
CREATE USER dbuser SUPERUSER;
ALTER DATABASE library OWNER TO dbuser;
GRANT ALL PRIVILEGES ON DATABASE library TO dbuser;
```

##### Run as `dbuser` inside `library` db
```
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA data;
CREATE SCHEMA usr;
```