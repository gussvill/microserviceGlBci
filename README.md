# microserviceGlBci

# Instrucciones para construir y ejecutar el proyecto

### Este proyecto es un microservicio para la creación y consulta de usuarios desarrollado con Spring Boot y Gradle, y debe cumplir con los siguientes requisitos:

### Requisitos

* Uso exclusivo de Java 8 (más de dos características propias de la versión).
* Pruebas unitarias de las funcionalidades del Service (deseable Spock Framework).
* Todos los endpoints deben aceptar y retornar solamente JSON, incluso para los mensajes de error, y deben manejar las
  excepciones.

### Prerequisitos

Para construir y ejecutar el proyecto se necesitan los siguientes programas:

* Java Development Kit (JDK) versión 8 o superior.
* Gradle versión 4.10.2 o superior.

### Paso 1: Clonar el repositorio

```shell
git clone git@github.com:gussvill/microserviceGlBci.git
```

### Paso 2: Importar el proyecto en un IDE

Abra su IDE favorito (por ejemplo, Eclipse o IntelliJ IDEA) y abra el proyecto importando el archivo build.gradle.

### Paso 3: Ejecutar pruebas unitarias

Antes de construir el proyecto, es recomendable ejecutar las pruebas unitarias. Para hacerlo, abra una terminal, vaya al
directorio del proyecto y ejecute el siguiente comando:

```shell
gradle test
```

### Paso 4: Construir el proyecto

Para construir el proyecto, abra una terminal, vaya al directorio del proyecto y ejecute el siguiente comando:

```shell
./gradlew build
```

### Paso 5: Ejecutar el microservicio

Para ejecutar el microservicio, abra una terminal, vaya al directorio del proyecto y ejecute el siguiente comando:

```shell
java -jar build/libs/bci-microservice-0.0.1-SNAPSHOT.jar
```

### Endpoints

El microservicio tiene dos endpoints:

* POST /sign-up: Este endpoint es para crear un usuario. Debe enviar una solicitud POST con el siguiente contrato de
  entrada en formato JSON:

```json
{
  "name" : "Gustavo Villegas",
  "email" : "gussvill@gmail.com",
  "password": "Guss2020",
  "phones": [
    {
      "number": 5255766,
      "cityCode": 2,
      "countryCode": "56"
    },
    {
      "number": 93099285,
      "cityCode": 9,
      "countryCode": "56"
    }
  ]
}
```

El endpoint retorna el siguiente contrato de salida en formato JSON:

```json
{
  "id": "6b6cb264-3ed2-4a62-bedc-15b1a6ab5914",
  "created": "may 18, 2023 03:14:58 PM",
  "lastLogin": "The user is not logged in yet. No Data!",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndXNzdmlsbEBnbWFpbC5jb20iLCJleHAiOjE2ODQ0Mzc1OTgsInBhc3N3b3JkIjoiJDJhJDEwJHJKUVJJNUU0SkwzOUZUenVLRWJzYi5NUkIucy40bnFCRC9PazlwTk9adjZOc296SUw0OUJHIn0.zCGAYISpug32ptcN-CxEK19AQHshZc0xZ87zMB2m09E",
  "isActive": true
}
```

* GET /login: Devuelve la siguiente información del usuario, una vez autenticándose con el token generado anteriormente
  y con el siguiente contrato de entrada en formato JSON:

```json
{
  "email" : "gussvill@gmail.com"
}
```

El endpoint retorna el siguiente contrato de salida en formato JSON:

```json
{
  "id": "6b6cb264-3ed2-4a62-bedc-15b1a6ab5914",
  "created": "may 18, 2023 03:14:58 PM",
  "lastLogin": "The user is not logged in yet. No Data!",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndXNzdmlsbEBnbWFpbC5jb20iLCJleHAiOjE2ODQ0Mzc3MDV9.kJ77ortd5YIJWw6LOr3SlostE6Y4PUndZ5g1hPfbZ8E",
  "name": "Gustavo Villegas",
  "email": "gussvill@gmail.com",
  "password": "$2a$10$rJQRI5E4JL39FTzuKEbsb.MRB.s.4nqBD/Ok9pNOZv6NsozIL49BG",
  "phones": "[{\"id\":null,\"number\":5255766,\"cityCode\":2,\"countryCode\":\"56\"},{\"id\":null,\"number\":93099285,\"cityCode\":9,\"countryCode\":\"56\"}]",
  "isActive": true
}
```

### Guides

Utilizar el archivo postman incluido en la carpeta documentations para probar los endpoints.


