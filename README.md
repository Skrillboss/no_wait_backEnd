# 🛠️ NoWait Backend

**NoWait Backend** es la parte del servidor de nuestra aplicación NoWait, construida con [Java Spring Boot](https://spring.io/projects/spring-boot), [Spring Security](https://spring.io/projects/spring-security), [Lombok](https://projectlombok.org/) y usando [PostgreSQL](https://www.postgresql.org/) como base de datos. Este proyecto utiliza una **arquitectura hexagonal** para garantizar la separación de preocupaciones y la flexibilidad del sistema.

## 📐 Arquitectura Hexagonal

La **arquitectura hexagonal**, también conocida como **arquitectura de puertos y adaptadores**, permite que el sistema esté dividido en capas, donde cada capa se comunica a través de interfaces (puertos) y adaptadores. Esta estructura mejora la mantenibilidad y la escalabilidad del software.

### Características de la Arquitectura Hexagonal

- **Adaptadores**: Interfaces de comunicación con el mundo exterior (API, bases de datos, etc.).
- **Core**: Contiene la lógica de negocio y se mantiene independiente de las implementaciones externas.
- **Puertos**: Interfaces que definen cómo interactúan las partes externas con el core.

Para más información sobre la arquitectura hexagonal, puedes consultar [este artículo sobre arquitectura hexagonal](https://martinfowler.com/bliki/HexagonalArchitecture.html).

## 🔒 Seguridad

El proyecto utiliza **Spring Security** para gestionar la autenticación y autorización de los usuarios mediante **JSON Web Tokens (JWT)**. 

### Funcionamiento de JWT

1. **Acceso Inicial**: Al iniciar sesión, se genera un **access token** (JWT) que otorga al usuario 10 minutos para realizar peticiones.
2. **Refresh Token**: Se proporciona un **refresh token** (también un JWT) que permite obtener un nuevo access token después de que el anterior haya expirado.
3. **Autenticación**: Las peticiones autorizadas se realizan utilizando el **Bearer token** en el header de cada solicitud.

### Ejemplo de Peticiones

```http
POST /login
Content-Type: application/json

{
  "username": "usuario",
  "password": "contraseña"
}
```
RESPUESTA
```
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR..."
}
```
📦 Tecnologías Usadas
Spring Boot
Spring Security
Lombok
PostgreSQL
Spring Data JPA
