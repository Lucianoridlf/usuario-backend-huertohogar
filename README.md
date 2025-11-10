# 🌱 Usuario Backend - Huerto Hogar

API de Usuarios y Autenticación para el proyecto Huerto Hogar. Gestiona el registro, autenticación y control de contraseñas de usuarios.

## 📋 Descripción

Microservicio de backend que proporciona operaciones CRUD completas para usuarios, así como funcionalidad de autenticación y gestión de contraseñas con cifrado BCrypt.

**Versión:** 2.0.0  
**Fecha de actualización:** 2025-11-09

---

## ✨ Características Principales

✅ **Gestión de Usuarios CRUD**
- Crear, leer, actualizar (completo y parcial) y eliminar usuarios
- Validación automática de datos

✅ **Autenticación Segura**
- Login con contraseña hasheada (BCrypt)
- Validación de credenciales
- Excepciones manejadas correctamente

✅ **Gestión de Contraseñas**
- Validación de contraseñas seguras
- Cambio de contraseña (verificando la anterior)
- Reset de contraseña (administrador)
- Cálculo de fortaleza de contraseña

✅ **Gestión de Ciudades y Regiones**
- CRUD completo para ciudades
- CRUD completo para regiones

---

## 🔐 Requisitos de Contraseña

Las contraseñas deben cumplir:
- ✅ Mínimo 8 caracteres
- ✅ Al menos una mayúscula (A-Z)
- ✅ Al menos una minúscula (a-z)
- ✅ Al menos un número (0-9)
- ✅ Al menos un carácter especial (@$!%*?&)

**Ejemplo válido:** `MiPassword123!`

---

## 📦 Tecnologías

- **Java 17** - Lenguaje de programación
- **Spring Boot 3.x** - Framework web
- **Spring Data JPA** - Acceso a datos
- **Spring Security** - Seguridad (BCrypt)
- **MySQL** - Base de datos
- **Swagger/OpenAPI** - Documentación interactiva
- **Maven** - Gestor de dependencias
- **Lombok** - Reducción de código boilerplate

---

## 🚀 Inicio Rápido

### Requisitos Previos
- Java JDK 17+
- Maven 3.8+
- MySQL 8.0+

### Instalación

```bash
# 1. Clonar el repositorio
git clone https://github.com/Lucianoridlf/usuario-backend-huertohogar.git
cd usuario-backend-huertohogar

# 2. Configurar base de datos
# Editar src/main/resources/application.properties
# Establecer conexión a tu MySQL

# 3. Compilar y ejecutar
mvn clean package
mvn spring-boot:run
```

### Acceso a la API

```
📍 API Base URL: http://localhost:8080/api/v1
📚 Swagger UI: http://localhost:8080/swagger-ui.html
📖 OpenAPI Docs: http://localhost:8080/v3/api-docs
```

---

## 📚 Documentación de Endpoints

### Usuarios

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/usuarios` | Crear nuevo usuario |
| GET | `/usuarios` | Listar todos los usuarios |
| GET | `/usuarios/{id}` | Obtener usuario por ID |
| PUT | `/usuarios/{id}` | Actualizar usuario completo |
| PATCH | `/usuarios/{id}` | Actualizar usuario parcialmente |
| DELETE | `/usuarios/{id}` | Eliminar usuario |
| GET | `/usuarios/categoria/{id}` | Buscar usuarios por apellido paterno |

### Autenticación y Contraseñas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/usuarios/authenticate` | Autenticar usuario |
| PUT | `/usuarios/{id}/cambiar-contrasena` | Cambiar contraseña |
| PATCH | `/usuarios/{id}/resetear-contrasena` | Resetear contraseña (admin) |
| POST | `/usuarios/validar-contrasena` | Validar contraseña |

### Ciudades

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/ciudades` | Crear ciudad |
| GET | `/ciudades` | Listar ciudades |
| GET | `/ciudades/{id}` | Obtener ciudad |
| PUT | `/ciudades/{id}` | Actualizar ciudad |
| DELETE | `/ciudades/{id}` | Eliminar ciudad |

### Regiones

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/regiones` | Crear región |
| GET | `/regiones` | Listar regiones |
| GET | `/regiones/{id}` | Obtener región |
| PUT | `/regiones/{id}` | Actualizar región |
| DELETE | `/regiones/{id}` | Eliminar región |

---

## 📝 Ejemplos de Uso

### Crear Usuario

```bash
curl -X POST http://localhost:8080/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "pNombre": "Juan",
    "sNombre": "Carlos",
    "aPaterno": "López",
    "aMaterno": "García",
    "email": "juan@example.com",
    "telefono": "+56987654321",
    "direccion": "Calle 123",
    "passwordHashed": "SecurePassword123!"
  }'
```

### Autenticar Usuario

```bash
curl -X POST http://localhost:8080/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "idUsuario": 1,
    "password": "SecurePassword123!"
  }'
```

### Cambiar Contraseña

```bash
curl -X PUT http://localhost:8080/api/v1/usuarios/1/cambiar-contrasena \
  -H "Content-Type: application/json" \
  -d '{
    "oldPassword": "SecurePassword123!",
    "newPassword": "NewPassword456!"
  }'
```

---

## 🗂️ Estructura del Proyecto

```
src/main/java/cl/huertohogar/usuario_backend/
├── config/              # Configuraciones (Security, OpenAPI)
├── controller/          # REST Controllers
├── dto/                 # Data Transfer Objects
├── exception/           # Excepciones personalizadas
├── model/               # Entidades JPA
├── repository/          # Interfaces JPA Repository
├── service/             # Lógica de negocio
└── UsuarioBackendApplication.java
```

---

## 🔄 Cambios Recientes (v2.0.0)

### ✨ Refactorización: Migración de Contraseñas

La gestión de contraseñas ha sido **centralizada en la tabla Usuario**:

- ✅ **Eliminado:** Tabla separada `password`
- ✅ **Agregado:** Campo `password_hashed` en tabla `usuario`
- ✅ **Eliminado:** `PasswordController`, `PasswordService`, `PasswordRepository`
- ✅ **Agregado:** Métodos de autenticación en `UsuarioService`
- ✅ **Agregado:** Nuevos endpoints en `UsuarioController`

**Beneficios:**
- Simplificación de la estructura de datos
- Mejor performance (menos JOINs)
- Centralización de lógica de seguridad
- Mantenimiento más fácil

Consultar [CAMBIOS_REFACTORIZACIÓN.md](./CAMBIOS_REFACTORIZACIÓN.md) para detalles completos.

---

## 📚 Documentación Disponible

1. **[CAMBIOS_REFACTORIZACIÓN.md](./CAMBIOS_REFACTORIZACIÓN.md)**
   - Resumen de cambios realizados
   - Arquitectura nueva
   - Beneficios de la refactorización

2. **[EJEMPLOS_ENDPOINTS.md](./EJEMPLOS_ENDPOINTS.md)**
   - Ejemplos prácticos de todos los endpoints
   - Casos de uso comunes
   - Integración con frontend

3. **[MIGRACION_BASE_DATOS.md](./MIGRACION_BASE_DATOS.md)**
   - Guía paso a paso de migración
   - Scripts SQL
   - Rollback

4. **[TESTING_GUIDE.md](./TESTING_GUIDE.md)**
   - Test cases detallados
   - Verificación post-migración
   - Troubleshooting

---

## 🧪 Testing

```bash
# Ejecutar tests unitarios
mvn test

# Ejecutar con reporte de cobertura
mvn test jacoco:report

# Ver reporte
open target/site/jacoco/index.html
```

---

## 🔐 Seguridad

- 🔒 Contraseñas hasheadas con **BCrypt**
- 🔑 Validación de contraseñas robusta
- ✅ Manejo de excepciones seguro
- 📝 Logs detallados de intentos de autenticación
- 🛡️ CORS configurado apropiadamente

---

## 🐛 Troubleshooting

### Error: "Contraseña no válida"
Asegúrate que la contraseña cumple todos los requisitos (8+ chars, mayúscula, minúscula, número, carácter especial).

### Error: "Autenticación fallida"
1. Verifica que el usuario existe
2. Asegúrate que la contraseña es exacta (case-sensitive)
3. Verifica la conexión a base de datos

### Error: "Connection refused"
Verifica que MySQL está corriendo y las credenciales en `application.properties` son correctas.

---

## 📞 Soporte

Para reportar bugs o sugerir mejoras, contacta al equipo de desarrollo.

---

## 📄 Licencia

Este proyecto es parte del programa HuertoHogar.

---

**Desarrollado por:** Equipo de Desarrollo HuertoHogar  
**Última actualización:** 2025-11-09  
**Versión:** 2.0.0