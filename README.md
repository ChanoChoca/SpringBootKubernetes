# Spring Boot With Kubernetes

A Spring Boot Proyect, using Postgres (for Courses) and MySQL (for Users), docker to isolate applications in containers and Kubernetes to handle that containers.

[//]: # (## Screenshots)

[//]: # ()
[//]: # (![WoT App Screenshot]&#40;images/img-home.png&#41;)

[//]: # ()
[//]: # (![WoT App Screenshot]&#40;images/img-products.png&#41;)

[//]: # ()
[//]: # (![WoT App Screenshot]&#40;images/img-cart.png&#41;)


## Tools Used

- Spring Boot
  - DevTools
  - Spring Web (consider use Reactive Web)
  - Spring Data JPA
  - MySQL Driver (for Courses)
  - PostgreSQL Driver (for Students)
  - OpenFeign
  - Validation
- Docker
- Kubernetes


## Environment Variables

To run this project, you will need to modify the application.properties in msvc-usuarios and msvc-cursos. Modify the following elements:

`spring.datasource.password`: Use your datasource password for MySQL and PostgreSQL

`serverTimezone`: Modify this if you are not from Buenos Aires

`server.port`: Modify this only if you have problems with the port


## Testing

Right click on `MsvcUsuariosApplication` and `MsvcCursosApplication` then run.

## Commands

Move to the Project Directory
``` bash
cd msvc-usuarios
```

To clean the target folder and package the application

When to Clean and Package:
- After making significant code changes: To ensure that you are working with the latest compiled code.
- Before deploying: To create a fresh build for deployment.
- When facing build issues: To troubleshoot by starting with a clean state.
``` bash
.\mvnw.cmd clean package
```
or
``` bash
.\mvnw clean package -DskipTests
```

### Running the Application

### Option 1: From the local machine

``` bash
java -jar .\target\msvc-usuarios-0.0.1-SNAPSHOT.jar
```

### Option 2: With Docker

**Note**: Remember to have Docker desktop open, also remember. Also remember to be located in the **course-kubernetes folder**.

Before cleaning the target folder, build the Docker image. This is built considering the location of the Dockerfile.
``` bash
docker build -t usuarios . -f .\msvc-usuarios\Dockerfile
```
Note: You can add : before name for tag name (Example :v2)

To list the Docker images
``` bash
docker images
```

To run the Docker image, replace [REPOSITORY or IMAGE ID] with the actual image ID. Replace anyport with the port you want to use
``` bash
docker run -p anyport:8001 [REPOSITORY or IMAGE ID]
```
or run it separately from the terminal (default is attach)
``` bash
docker run -d -p anyport:8001 [REPOSITORY or IMAGE ID]
```
or run it with an interactive terminal
``` bash
docker run -p 8001:8001 --rm -it usuarios /bin/sh
```
or run with a created newtork
``` bash
docker run -p 8001:8001 -d --rm --name msvc-usuarios --network spring usuarios
```
or run with a .env file
``` bash
docker run -p 8001:8001 --env-file .\msvc-usuarios\.env -d --rm --name msvc-usuarios --network spring usuarios
```


To list the running containers
``` bash
docker ps
```

To list the stopped containers
``` bash
docker ps -a
```

To view logs, replace [CONTAINER ID or NAME] with the actual container ID or name
``` bash
docker logs [CONTAINER ID or NAME]
```

To stop a running container, replace [CONTAINER ID or NAME] with the actual container ID or name
``` bash
docker stop [CONTAINER ID or NAME]
```

To remove a container when it stops
``` bash
docker run -p 8001:8001 -d --rm usuarios
```
To remove a stopped container or image
``` bash
docker rm [CONTAINER ID or NAME] or docker rmi [REPOSITORY or IMAGE ID]
```
To remove a list of stopped containers or images
``` bash
docker [image or container] prune
```

[//]: # (First activity: use cp to move files to/from the container)


[//]: # (In downloads folder:)

[//]: # (docker cp .\Login.java af16832d7811:/app/Login.java)

[//]: # (Inside container running with -it:)

[//]: # (su -)

[//]: # (apt-get update)

[//]: # (apt-get install nano)

[//]: # (exit)

[//]: # (To view and/or edit file in linux container:)

[//]: # (nano Login.java)

[//]: # (To execute the file Login.java:)

[//]: # (javac Login.java)

[//]: # (java Login)


[//]: # (In downloads folder:)

[//]: # (docker cp 02a62c7b7b2d:/app/Login.java .\Login2.java)

[//]: # (You can see the Login2.java file in downloads)


[//]: # (To copy logs in the container and paste in the downloads folder:)

[//]: # (docker run -p 8001:8001 --rm -d usuarios)

[//]: # (docker logs 2388)

[//]: # (docker cp 2388eeafaab5:/app/logs .\logs)

To inspect the container or image.
``` bash
docker [image or container] inspect [REPOSITORY or IMAGE ID or CONTAINER ID or NAME]
```

To create a network (network default is bridge).
``` bash
docker network create [name]
```

To list the networks
``` bash
docker network ls
```

To create an image of MySQL with Docker
``` bash
docker pull mysql:8.0
```

To create a container with mysql image:
The -e indicates that it is an environment variable.
The -v is used to mount a volume (volumeName:Directory).
Note: If the container was assigned --rm then --restart=always has no effect.
``` bash
docker run -d -p 3307:3306 --name mysql8 --network spring 
-e MYSQL_ROOT_PASSWORD=chanochoca -e MYSQL_DATABASE=msvc-usuarios 
-v data-mysql:/var/lib/mysql --restart=always mysql:8.0
```

To view MySQL container logs.
``` bash
docker logs -f mysql
```

To create an image of MySQL with Docker.
``` bash
docker pull postgres:16.3-alpine3.20 
```

To create a container with mysql image.
``` bash
docker run -p 5433:5432 --name postgres16 --network spring 
-e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=msvc-cursos -d 
-v data-postgres:/var/lib/postgresql/data --restart=always postgres:16.3-alpine3.20
```

To start a stopped container.
``` bash
docker start [CONTAINER ID or NAME]
```

Connect to the created mysql instance.
``` bash
docker run -it --rm --network spring mysql:8.0 [bash or /bin/bash]
```

[//]: # (Activity with MySQL and Docker)

[//]: # (show databases;)

[//]: # (use msvc-usuarios)

[//]: # (show tables;)

[//]: # (desc usuarios;)

[//]: # (select * from usuarios;)

Connect to the created postgres instance.
``` bash
docker run -it --rm --network spring postgres:16.3-alpine3.20 psql -h postgres16 -U postgres
```

[//]: # (Activity with PostgreSQL and Docker)

[//]: # (\c msvc-cursos;)

[//]: # (\dt;)

[//]: # (\d+ cursos)

[//]: # (How can a dockerized application communicate with an application on the host machine?)

[//]: # (host.docker.internal allows containers to communicate with the host, )

[//]: # (while 127.0.0.1 or localhost refers to the container itself, not the host.)

## Authors

- [@Juan Ignacio Caprioli (ChanoChoca)](https://github.com/ChanoChoca)

## Badges

[//]: # (Add badges from somewhere like: [shields.io]&#40;https://shields.io/&#41;)

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)

[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)
