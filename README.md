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
  "name": "Julio Gonzalez",
  "email": "julio@testssw.cl",
  "password": "a2asfGfdfdf4",
  "phones": [
    {
      "number": 87650009,
      "cityCode": 7,
      "countryCode": "25"
    }
  ]
}
```

El endpoint retorna el siguiente contrato de salida en formato JSON:

```json
{
  "id": "b2b8a1cc-5b7d-435c-82d7-ab8cf4855d2f",
  "created": "May 01, 2023 01:34:55 AM",
  "lastLogin": "The user is not logged in yet. No Data!",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpb0B0ZXN0c3N3LmNsIiwiZXhwIjoxNjgyOTI1Mjk1LCJwYXNzd29yZCI6IiQyYSQxMCRMaERqb28zMnJuZXNTQjFDczNVUTV1TjhSWERYSzZhd2pzZnhNRTMxTTFOeWVnblNuS0NZSyJ9.ZUv58i9kDgvUV2nRDUgkSRiEq0Qr3lowPKUFjFbNKdo",
  "active": true
}
```

* GET /login: Devuelve la siguiente informacion del usuario, una vez autenticandose con el token generado anteriormente
  y con el siguiente contrato de entrada en formato JSON:

```json
{
  "email": "julio@testssw.cl"
}

```

El endpoint retorna el siguiente contrato de salida en formato JSON:

```json
{
  "id": "b2b8a1cc-5b7d-435c-82d7-ab8cf4855d2f",
  "created": "May 01, 2023 01:34:55 AM",
  "lastLogin": "The user is not logged in yet. No Data!",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpb0B0ZXN0c3N3LmNsIiwiZXhwIjoxNjgyOTI1Mjk1LCJwYXNzd29yZCI6IiQyYSQxMCRMaERqb28zMnJuZXNTQjFDczNVUTV1TjhSWERYSzZhd2pzZnhNRTMxTTFOeWVnblNuS0NZSyJ9.ZUv58i9kDgvUV2nRDUgkSRiEq0Qr3lowPKUFjFbNKdo",
  "name": "Julio Gonzalez",
  "email": "julio@testssw.cl",
  "password": "$2a$10$LhDjoo32rnesSB1Cs3UQ5uN8RXDXK6awjsfxME31M1NyegnSnKCYK",
  "phones": [],
  "active": true
}
```

### Guides

Utilizar el archivo postman incluido en la carpeta documentations para probar los endpoints.


