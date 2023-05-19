# microserviceGlBci

# Instrucciones para construir y ejecutar el proyecto

### Este proyecto es un microservicio para la creación y consulta de usuarios, desarrollado con Spring Boot y Gradle, y debe cumplir con los siguientes requisitos:

### Requisitos

* Desarrolle una aplicación que exponga una API RESTful de creación de usuarios.
* Todos los endpoints deben aceptar y retornar solamente JSON, incluso para los mensajes de error, y deben manejar las
  excepciones.
* Todos los mensajes deben seguir el formato: {"mensaje": "mensaje de error"}
* Pruebas unitarias de las funcionalidades del Service (deseable Spock Framework).
* Plazo: 2 días, si tienes algún inconveniente con el tiempo comunícate con nosotros
* Banco de datos en memoria. Ejemplo: HSQLDB o H2.
* Proceso de build vía Gradle o Maven.
* Persistencia con JPA. Ejemplo: EclipseLink, Hibernate u OpenJPA.
* Framework SpringBoot.
* Uso exclusivo de Java 8+ (más de dos características propias de la versión).
* Entrega en un repositorio público (github o bitbucket) con el código fuente y script de
creación de BD.
* Readme explicando cómo probarlo.
* Diagrama de la solución.
* Diagrama de componentes y un diagrama de secuencia del proyecto cumpliendo estándares UML.


### Requisitos Opcionales
* JWT como token
* Pruebas unitarias
* Swagger 

### Registro
* Ese endpoint deberá recibir un usuario con los campos "nombre", "correo", "contraseña",
más un listado de objetos "teléfono", respetando el siguiente formato:


* /sign-up: endpoint de creación de un usuario, cuyo contrato de entrada debe ser el siguiente:
```json
{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}
```
```json
{
  "name": String,
  "email": String,
  "password": String,
  "phones": [
    {
      "number": long,
      "citycode": int,
      "contrycode": String
    }
  ]
}
```

* Responder el código de status HTTP adecuado
* En caso de éxito, retorne el usuario y los siguientes campos:
  * id: id del usuario (puede ser lo que se genera por el banco de datos, pero sería
  más deseable un UUID)
  * created: fecha de creación del usuario
  * modified: fecha de la última actualización de usuario
  * last_login: del último ingreso (en caso de nuevo usuario, va a coincidir con la
  fecha de creación)
  * token: token de acceso de la API (puede ser UUID o JWT)
  * isactive: Indica si el usuario sigue habilitado dentro del sistema.
  * Si caso el correo conste en la base de datos, deberá retornar un error "El correo ya
    registrado".
  * Donde el correo debe seguir una expresión regular para validar que formato sea el
    correcto. (aaaaaaa@undominio.algo), si no cumple enviar mensaje de error.
  * La clave debe seguir una expresión regular para validar que formato sea el correcto.
    Debe tener solo una Mayúscula y solamente dos números (no necesariamente
    consecutivos), en combinación de letras minúsculas, largo máximo de 12 y mínimo 8.
    "a2asfGfdfdf4", si no cumple enviar mensaje de error.
  * El token deberá ser persistido junto con el usuario
  * El usuario debe ser persistido en una BD utilizando spring data (deseable H2). En caso
    de la contraseña, sería ideal que pudiese ser encriptada.
  * Si el usuario ya existe, debe enviar mensaje de error indicando que ya existe.

* En caso de error de un endpoint debe retornar:
```json
{
  "error": [{
    "timestamp": Timestamp,
    "codigo": int,
    "detail": String
  }]
}
```

### Deseable
* /login: endpoint el cual será para consultar el usuario, para ello debe utilizar el token generado
en el endpoint anterior para realizar la consulta y así retornar toda la información del usuario
persistido, considere que el token debe cambiar al ejecutar por lo que se actualizará el token.
  * El contrato de salida debe ser:

```json
{
  "id": "e5c6cf84-8860-4c00-91cd-22d3be28904e",
  "created": "Nov 16, 2021 12:51:43 PM",
  "lastLogin": "Nov 16, 2021 12:51:43 PM",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpb0B0ZXN0...",
  "isActive": true,
  "name": "Julio Gonzalez",
  "email": "julio@testssw.cl",
  "password": "a2asfGfdfdf4",
  "phones": [
    {
      "number": 87650009,
      "citycode": 7,
      "contrycode": "25"
    }
  ]
}
```

### Prerequisitos para construir y ejecutar el proyecto

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

* POST http://localhost:8081/bci-microservice/api/sign-up: Este endpoint es para crear un usuario. Debe enviar una solicitud POST con el siguiente contrato de
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

* GET http://localhost:8081/bci-microservice/api/login: Devuelve la siguiente información del usuario, una vez autenticándose con el token generado anteriormente
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

* GET http://localhost:8081/bci-microservice/api/users: Devuelve la lista de todos los usuarios, una vez autenticándose con el token asociado al usuario.:

```json
{

}
```

El endpoint retorna el siguiente contrato de salida en formato JSON:

```json
[
  {
    "id": "6d9722b9-5952-43ce-bd71-47896188a9b9",
    "created": "may 18, 2023 09:21:31 PM",
    "lastLogin": "may 18, 2023 09:21:46 PM",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndXNzdmlsbEBnbWFpbC5jb20iLCJleHAiOjE2ODQ0NTk2MDZ9.LhQi6ZRCJq2BPQ7cZ7IJv1cNyU7441MPPwH2VdB0akc",
    "name": "Gustavo Villegas",
    "email": "gussvill@gmail.com",
    "password": "$2a$10$Ql217EDuGP8Zm3ULyiuIKe9SIbFOHbapURqYY.K7ZgnevKSGD1DOi",
    "phones": [],
    "listPhones": "[{\"id\":null,\"number\":93099285,\"cityCode\":9,\"countryCode\":\"56\"},{\"id\":null,\"number\":5255766,\"cityCode\":2,\"countryCode\":\"56\"}]",
    "active": true
  }
]
```

### Guides

* Utilizar el archivo postman incluido en la carpeta documentations para probar los endpoints.
* Como alternativa se puede utilizar la url de Swagger para probar los endpoints: http://localhost:8081/bci-microservice/swagger-ui.html


