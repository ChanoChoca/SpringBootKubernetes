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

**Note**: Remember to have Docker desktop open.

Before cleaning the target folder, build the Docker image. This is built considering the location of the Dockerfile.
``` bash
docker build -t usuarios .
```

To list the Docker images
``` bash
docker images
```

To run the Docker image, replace [REPOSITORY or IMAGE ID] with the actual image ID. Replace anyport with the port you want to use
``` bash
docker run -p anyport:8001 [REPOSITORY or IMAGE ID]
```

To list the running containers
``` bash
docker ps
```

To list the stopped containers
``` bash
docker ps -a
```

To stop a running container, replace [CONTAINER ID or NAME] with the actual container ID or name
``` bash
docker stop [CONTAINER ID or NAME]
```

## Authors

- [@Juan Ignacio Caprioli (ChanoChoca)](https://github.com/ChanoChoca)

## Badges

[//]: # (Add badges from somewhere like: [shields.io]&#40;https://shields.io/&#41;)

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)

[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)
