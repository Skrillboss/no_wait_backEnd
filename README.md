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




# **Documentación: Códigos de Error Personalizados**

## **Estructura del Código**

- **APP**: Prefijo que indica que es un código interno de la aplicación.
- **XXXX**: Número de cuatro dígitos que identifica el error.

---

## **Categorías y Rangos de Códigos**

### **1. Errores de Validación de Datos (1000 - 1999)**
- Errores relacionados con datos proporcionados por el usuario que no cumplen con las reglas o restricciones.
- **Ejemplos**: Campos faltantes, formatos incorrectos, valores inválidos.

### **2. Errores de Conflictos de Recursos (2000 - 2999)**
- Errores relacionados con conflictos en el estado actual del servidor.
- **Ejemplos**: Recursos duplicados, intentos de operaciones no válidas debido a restricciones.

### **3. Errores de Autenticación y Autorización (3000 - 3999)**
- Errores relacionados con el acceso al sistema.
- **Ejemplos**: Credenciales inválidas, permisos insuficientes, sesiones expiradas.

### **4. Errores de Negocio (4000 - 4999)**
- Errores específicos de la lógica de negocio de la aplicación.
- **Ejemplos**: Restricciones en operaciones, reglas de negocio no cumplidas.

### **5. Errores Internos del Servidor (5000 - 5999)**
- Errores inesperados dentro del servidor.
- **Ejemplos**: Fallos en la conexión a la base de datos, excepciones no controladas.

### **6. Errores de Integración con Servicios Externos (6000 - 6999)**
- Errores relacionados con servicios o APIs de terceros.
- **Ejemplos**: Tiempo de espera agotado, respuestas inválidas de servicios externos.

---

## **Ejemplos de Códigos por Categoría**

### **Errores de Validación de Datos (1000 - 1999)**
| Código    | Descripción                                    |
|-----------|-----------------------------------------------|
| APP-1001  | Campo obligatorio faltante                   |
| APP-1002  | Formato inválido en un campo                 |
| APP-1003  | Longitud del campo excedida                  |

### **Errores de Conflictos de Recursos (2000 - 2999)**
| Código    | Descripción                                    |
|-----------|-----------------------------------------------|
| APP-2001  | `nickName` ya existe                          |
| APP-2002  | Correo electrónico ya registrado              |
| APP-2003  | Recurso en uso, no se puede eliminar          |

### **Errores de Autenticación y Autorización (3000 - 3999)**
| Código    | Descripción                                    |
|-----------|-----------------------------------------------|
| APP-3001  | Credenciales incorrectas                     |
| APP-3002  | Acceso denegado por permisos insuficientes    |
| APP-3003  | Token de autenticación expirado              |

### **Errores de Negocio (4000 - 4999)**
| Código    | Descripción                                    |
|-----------|-----------------------------------------------|
| APP-4001  | No se puede realizar la operación solicitada  |
| APP-4002  | Stock insuficiente                            |
| APP-4003  | El cliente no cumple los requisitos mínimos   |

### **Errores Internos del Servidor (5000 - 5999)**
| Código    | Descripción                                    |
|-----------|-----------------------------------------------|
| APP-5001  | Error de conexión con la base de datos        |
| APP-5002  | Excepción no controlada                      |
| APP-5003  | Error al generar respuesta JSON              |

### **Errores de Integración con Servicios Externos (6000 - 6999)**
| Código    | Descripción                                    |
|-----------|-----------------------------------------------|
| APP-6001  | Tiempo de espera agotado en servicio externo  |
| APP-6002  | Respuesta no válida de un servicio externo    |
| APP-6003  | Servicio externo no disponible               |

---

## **Ventajas de esta Organización**
1. **Clara segmentación:** Facilita encontrar el código correspondiente a cada error.
2. **Escalabilidad:** Si necesitas agregar más errores, puedes extender fácilmente cada rango.
3. **Mantenimiento:** Los desarrolladores y el equipo tienen un esquema claro para identificar problemas.

