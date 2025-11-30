# 🌱 Usuario Backend - Huerto Hogar

API REST de Usuarios y Autenticación para el proyecto Huerto Hogar. Microservicio que gestiona registro, autenticación JWT, roles y control de contraseñas.

**Versión:** 3.0.0  
**Última actualización:** 2025-01-XX  
**Producción:** https://hh-usuario-backend-n6qwg.ondigitalocean.app

---

## 📦 Tecnologías

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.5.7 | Framework web |
| Spring Data JPA | - | Acceso a datos |
| PostgreSQL | - | Base de datos |
| BCrypt | - | Hash de contraseñas |
| SpringDoc OpenAPI | 2.6.0 | Documentación Swagger |
| Maven | 3.9.5 | Gestor de dependencias |

---

## 🚀 Instalación

### Requisitos Previos
- Java JDK 21+
- Maven 3.9+
- PostgreSQL 15+

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/tu-usuario/usuario-backend-huertohogar.git
cd usuario-backend-huertohogar

# 2. Configurar base de datos (editar application.properties)
# spring.datasource.url=jdbc:postgresql://localhost:5432/huertohogar
# spring.datasource.username=tu_usuario
# spring.datasource.password=tu_password

# 3. Compilar
./mvnw clean package -DskipTests

# 4. Ejecutar
./mvnw spring-boot:run
```

### URLs

| Entorno | URL |
|---------|-----|
| Local | http://localhost:8080/api/v1 |
| Producción | https://hh-usuario-backend-n6qwg.ondigitalocean.app/api/v1 |
| Swagger UI (Local) | http://localhost:8080/swagger-ui.html |
| Swagger UI (Prod) | https://hh-usuario-backend-n6qwg.ondigitalocean.app/swagger-ui.html |

---

## 🔐 Autenticación

El sistema usa **JWT** para autenticación entre microservicios.

### Roles
- `USER` - Usuario normal (asignado automáticamente al registrarse)
- `ADMIN` - Administrador (puede ver todos los usuarios, resetear contraseñas)

### Flujo de Autenticación
1. Usuario se registra en `POST /usuarios` → recibe datos sin rol
2. Usuario hace login en `POST /usuarios/authenticate` → recibe JWT token + datos completos
3. Frontend envía JWT en header: `Authorization: Bearer <token>`

---

## 📚 Endpoints

### Endpoints Públicos (No requieren autenticación)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/usuarios` | Registrar nuevo usuario |
| `POST` | `/usuarios/authenticate` | Login (retorna JWT) |
| `POST` | `/usuarios/validar-contrasena` | Validar formato de contraseña |
| `GET` | `/regiones` | Listar regiones |
| `GET` | `/regiones/{id}` | Obtener región por ID |
| `GET` | `/ciudades` | Listar ciudades |
| `GET` | `/ciudades/{id}` | Obtener ciudad por ID |

### Endpoints Protegidos (Requieren JWT)

| Método | Endpoint | Rol Requerido | Descripción |
|--------|----------|---------------|-------------|
| `GET` | `/usuarios` | ADMIN | Listar todos los usuarios |
| `GET` | `/usuarios/{id}` | USER/ADMIN | Obtener usuario (USER solo ve el suyo) |
| `PUT` | `/usuarios/{id}` | USER/ADMIN | Actualizar usuario completo |
| `PATCH` | `/usuarios/{id}` | USER/ADMIN | Actualizar usuario parcialmente |
| `DELETE` | `/usuarios/{id}` | ADMIN | Eliminar usuario |
| `PUT` | `/usuarios/{id}/cambiar-contrasena` | USER | Cambiar contraseña propia |
| `PATCH` | `/usuarios/{id}/resetear-contrasena` | ADMIN | Resetear contraseña de usuario |
| `PUT` | `/usuarios/{id}/rol` | ADMIN | Cambiar rol de usuario |

---

## 📝 Ejemplos de Uso

### 1. Registrar Usuario

```bash
curl -X POST https://hh-usuario-backend-n6qwg.ondigitalocean.app/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "sNombre": "Carlos",
    "aPaterno": "López",
    "aMaterno": "García",
    "rut": "12345678",
    "dv": "9",
    "fechaNacimiento": "1990-05-15",
    "idRegion": 1,
    "direccion": "Calle Ejemplo 123",
    "email": "juan.lopez@email.com",
    "telefono": "+56912345678",
    "passwordHashed": "MiPassword123!"
  }'
```

**Respuesta (201 Created):**
```json
{
  "idUsuario": 1,
  "nombre": "Juan",
  "sNombre": "Carlos",
  "aPaterno": "López",
  "aMaterno": "García",
  "rut": "12345678",
  "dv": "9",
  "fechaNacimiento": "1990-05-15",
  "idRegion": 1,
  "direccion": "Calle Ejemplo 123",
  "email": "juan.lopez@email.com",
  "telefono": "+56912345678"
}
```

### 2. Login

```bash
curl -X POST https://hh-usuario-backend-n6qwg.ondigitalocean.app/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan.lopez@email.com",
    "password": "MiPassword123!"
  }'
```

**Respuesta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "idUsuario": 1,
  "nombre": "Juan",
  "sNombre": "Carlos",
  "aPaterno": "López",
  "aMaterno": "García",
  "rut": "12345678",
  "dv": "9",
  "fechaNacimiento": "1990-05-15",
  "idRegion": 1,
  "direccion": "Calle Ejemplo 123",
  "email": "juan.lopez@email.com",
  "telefono": "+56912345678",
  "rol": "USER"
}
```

### 3. Obtener Usuario (con JWT)

```bash
curl -X GET https://hh-usuario-backend-n6qwg.ondigitalocean.app/api/v1/usuarios/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### 4. Cambiar Contraseña

```bash
curl -X PUT https://hh-usuario-backend-n6qwg.ondigitalocean.app/api/v1/usuarios/1/cambiar-contrasena \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "oldPassword": "MiPassword123!",
    "newPassword": "NuevaPassword456!"
  }'
```

---

## 🔐 Requisitos de Contraseña

| Requisito | Descripción |
|-----------|-------------|
| Longitud | Mínimo 8 caracteres |
| Mayúscula | Al menos una (A-Z) |
| Minúscula | Al menos una (a-z) |
| Número | Al menos uno (0-9) |
| Especial | Al menos uno (@$!%*?&) |

**Ejemplo válido:** `MiPassword123!`

---

## 📊 Modelo Usuario

| Campo | Tipo | Requerido | Descripción |
|-------|------|-----------|-------------|
| idUsuario | Long | Auto | ID único |
| nombre | String | ✅ | Primer nombre |
| sNombre | String | ❌ | Segundo nombre |
| aPaterno | String | ✅ | Apellido paterno |
| aMaterno | String | ✅ | Apellido materno |
| rut | String | ✅ | RUT sin DV |
| dv | String | ✅ | Dígito verificador |
| fechaNacimiento | LocalDate | ✅ | Fecha nacimiento |
| idRegion | Long | ✅ | ID de región |
| direccion | String | ✅ | Dirección completa |
| email | String | ✅ | Email (único) |
| telefono | String | ❌ | Teléfono |
| passwordHashed | String | ✅ | Contraseña (se hashea) |
| rol | String | Auto | USER o ADMIN |

---

## ⚠️ Códigos de Error

| Código | Significado |
|--------|-------------|
| 400 | Bad Request - Datos inválidos |
| 401 | Unauthorized - JWT inválido o expirado |
| 403 | Forbidden - Sin permisos para el recurso |
| 404 | Not Found - Usuario/recurso no existe |
| 409 | Conflict - Email ya registrado |
| 500 | Internal Server Error |

---

## 🗂️ Estructura del Proyecto

```
src/main/java/cl/huertohogar/usuario_backend/
├── config/
│   ├── OpenAPIConfig.java      # Configuración Swagger
│   ├── SecurityConfig.java     # Configuración seguridad
│   └── WebConfig.java          # CORS
├── controller/
│   ├── UsuarioController.java  # Endpoints usuarios
│   ├── RegionController.java   # Endpoints regiones
│   └── CiudadController.java   # Endpoints ciudades
├── dto/
│   ├── AuthenticationRequest.java
│   ├── AuthenticationResponse.java
│   ├── UsuarioResponse.java
│   └── Password*.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── EmailAlreadyExistsException.java
│   └── *NotFoundException.java
├── model/
│   ├── Usuario.java
│   ├── Region.java
│   └── Ciudad.java
├── repository/
│   └── *Repository.java
├── service/
│   └── *Service.java
└── UsuarioBackendApplication.java
```

---

## 🌐 CORS

Orígenes permitidos:
- `https://huertohogar.nyc3.cdn.digitaloceanspaces.com`
- `http://huertohogar-frontend.s3-website-us-east-1.amazonaws.com`

---

## 🧪 Testing

```bash
# Ejecutar tests
./mvnw test

# Ejecutar con cobertura
./mvnw test jacoco:report
```

---

## 📄 Licencia

Proyecto HuertoHogar - Duoc UC

---

**Desarrollado por:** Equipo HuertoHogar  
**Versión:** 3.0.0