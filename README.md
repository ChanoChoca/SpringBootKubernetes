# Spring Boot With Kubernetes

An Spring Boot Proyect, using Postgres (for Courses) and MySQL (for Users), docker to isolate applications in containers and Kubernetes to handle that containers.

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

Right click for `MsvcUsuariosApplication` or `MsvcCursosApplication` and run.

## Authors

- [@Juan Ignacio Caprioli (ChanoChoca)](https://github.com/ChanoChoca)

## Badges

[//]: # (Add badges from somewhere like: [shields.io]&#40;https://shields.io/&#41;)

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)

[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)
