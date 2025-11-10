# 🧪 Guía de Testing - Refactorización de Contraseñas

## 📌 Objetivo

Verificar que la refactorización de contraseñas funcionó correctamente y que todos los endpoints nuevos están operativos.

---

## 🔧 Setup Inicial

### 1. Compilar y ejecutar la aplicación

```bash
# En la raíz del proyecto
mvn clean package
mvn spring-boot:run
```

### 2. Verificar que la aplicación está corriendo

```bash
curl http://localhost:8080/api/v1/usuarios
```

Debería responder con una lista de usuarios (o error 404 si no hay usuarios).

---

## 📝 Test Cases

### Test 1: Crear Usuario con Contraseña Válida

**Objetivo:** Verificar que se puede crear un usuario con una contraseña hasheada automáticamente.

```bash
curl -X POST http://localhost:8080/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "pNombre": "Juan",
    "sNombre": "Carlos",
    "aPaterno": "López",
    "aMaterno": "Ruiz",
    "email": "juan.lopez@example.com",
    "telefono": "+56987654321",
    "direccion": "Calle Principal 123",
    "passwordHashed": "TestPassword123!"
  }'
```

**Resultado esperado:**
- ✅ Status: 201 Created
- ✅ La respuesta contiene `passwordHashed` con valor hasheado (comienza con `$2a$`)
- ✅ El id_usuario se asigna automáticamente

---

### Test 2: Crear Usuario con Contraseña Inválida

**Objetivo:** Verificar que se rechaza una contraseña débil.

```bash
curl -X POST http://localhost:8080/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "pNombre": "María",
    "sNombre": "José",
    "aPaterno": "García",
    "aMaterno": "Martínez",
    "email": "maria.garcia@example.com",
    "telefono": "+56987654322",
    "direccion": "Calle Secundaria 456",
    "passwordHashed": "weak"
  }'
```

**Resultado esperado:**
- ✅ Status: 400 Bad Request
- ✅ Mensaje: "La contraseña debe tener al menos 8 caracteres..."

---

### Test 3: Validar Contraseña Fuerte

**Objetivo:** Verificar el endpoint de validación.

```bash
curl -X POST http://localhost:8080/api/v1/usuarios/validar-contrasena \
  -H "Content-Type: application/json" \
  -d '{
    "password": "SuperSecure123!"
  }'
```

**Resultado esperado:**
- ✅ Status: 200 OK
- ✅ Response: `"Válida: true, Fortaleza: FUERTE"`

---

### Test 4: Validar Contraseña Débil

**Objetivo:** Verificar que se reportan contraseñas débiles.

```bash
curl -X POST http://localhost:8080/api/v1/usuarios/validar-contrasena \
  -H "Content-Type: application/json" \
  -d '{
    "password": "password123"
  }'
```

**Resultado esperado:**
- ✅ Status: 200 OK
- ✅ Response: `"Válida: false, Fortaleza: DÉBIL"`

---

### Test 5: Autenticar Usuario (Credenciales Correctas)

**Objetivo:** Verificar que la autenticación funciona con credenciales válidas.

Primero, crea un usuario (usa el Test 1, anota el id_usuario).

```bash
curl -X POST http://localhost:8080/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "idUsuario": 1,
    "password": "TestPassword123!"
  }'
```

**Resultado esperado:**
- ✅ Status: 200 OK
- ✅ Response: `"Autenticación exitosa"`

---

### Test 6: Autenticar Usuario (Credenciales Incorrectas)

**Objetivo:** Verificar que la autenticación falla con contraseña incorrecta.

```bash
curl -X POST http://localhost:8080/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "idUsuario": 1,
    "password": "WrongPassword123!"
  }'
```

**Resultado esperado:**
- ✅ Status: 401 Unauthorized
- ✅ Response: `"Credenciales inválidas"`

---

### Test 7: Autenticar Usuario Inexistente

**Objetivo:** Verificar que falla gracefully con usuario inexistente.

```bash
curl -X POST http://localhost:8080/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "idUsuario": 9999,
    "password": "AnyPassword123!"
  }'
```

**Resultado esperado:**
- ✅ Status: 401 Unauthorized
- ✅ Response: `"Credenciales inválidas"`

---

### Test 8: Cambiar Contraseña (Exitoso)

**Objetivo:** Verificar que se puede cambiar contraseña verificando la anterior.

```bash
curl -X PUT http://localhost:8080/api/v1/usuarios/1/cambiar-contrasena \
  -H "Content-Type: application/json" \
  -d '{
    "oldPassword": "TestPassword123!",
    "newPassword": "NewPassword456!"
  }'
```

**Resultado esperado:**
- ✅ Status: 200 OK
- ✅ Response: `"Contraseña cambiada exitosamente"`

**Verificar:**
```bash
# Intentar autenticarse con contraseña antigua (debe fallar)
curl -X POST http://localhost:8080/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "idUsuario": 1,
    "password": "TestPassword123!"
  }'
# Resultado esperado: 401 Unauthorized

# Intentar autenticarse con nueva contraseña (debe funcionar)
curl -X POST http://localhost:8080/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "idUsuario": 1,
    "password": "NewPassword456!"
  }'
# Resultado esperado: 200 OK
```

---

### Test 9: Cambiar Contraseña (Contraseña Anterior Incorrecta)

**Objetivo:** Verificar que falla si la contraseña anterior es incorrecta.

```bash
curl -X PUT http://localhost:8080/api/v1/usuarios/1/cambiar-contrasena \
  -H "Content-Type: application/json" \
  -d '{
    "oldPassword": "WrongPassword123!",
    "newPassword": "AnotherPassword789!"
  }'
```

**Resultado esperado:**
- ✅ Status: 401 Unauthorized
- ✅ Mensaje: "La contraseña anterior no es correcta"

---

### Test 10: Cambiar Contraseña (Nueva Contraseña Débil)

**Objetivo:** Verificar que la nueva contraseña también se valida.

```bash
curl -X PUT http://localhost:8080/api/v1/usuarios/1/cambiar-contrasena \
  -H "Content-Type: application/json" \
  -d '{
    "oldPassword": "NewPassword456!",
    "newPassword": "weak"
  }'
```

**Resultado esperado:**
- ✅ Status: 400 Bad Request
- ✅ Mensaje: "La contraseña debe tener al menos 8 caracteres..."

---

### Test 11: Cambiar Contraseña (Nueva igual a la anterior)

**Objetivo:** Verificar que no se permite reutilizar la misma contraseña.

```bash
curl -X PUT http://localhost:8080/api/v1/usuarios/1/cambiar-contrasena \
  -H "Content-Type: application/json" \
  -d '{
    "oldPassword": "NewPassword456!",
    "newPassword": "NewPassword456!"
  }'
```

**Resultado esperado:**
- ✅ Status: 400 Bad Request
- ✅ Mensaje: "La nueva contraseña no puede ser igual a la anterior"

---

### Test 12: Resetear Contraseña (Admin)

**Objetivo:** Verificar que un administrador puede resetear contraseña sin verificar la anterior.

```bash
curl -X PATCH http://localhost:8080/api/v1/usuarios/1/resetear-contrasena \
  -H "Content-Type: application/json" \
  -d '{
    "newPassword": "AdminReset123!"
  }'
```

**Resultado esperado:**
- ✅ Status: 200 OK
- ✅ Response: `"Contraseña reseteada exitosamente"`

**Verificar:**
```bash
# Autenticarse con la nueva contraseña
curl -X POST http://localhost:8080/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "idUsuario": 1,
    "password": "AdminReset123!"
  }'
# Resultado esperado: 200 OK
```

---

### Test 13: Resetear Contraseña (Usuario Inexistente)

**Objetivo:** Verificar que falla gracefully.

```bash
curl -X PATCH http://localhost:8080/api/v1/usuarios/9999/resetear-contrasena \
  -H "Content-Type: application/json" \
  -d '{
    "newPassword": "NewPassword123!"
  }'
```

**Resultado esperado:**
- ✅ Status: 404 Not Found
- ✅ Mensaje: "Usuario no encontrado..."

---

### Test 14: Niveles de Fortaleza

**Objetivo:** Verificar que se reportan correctamente los niveles de fortaleza.

```bash
# Caso 1: DÉBIL
curl -X POST http://localhost:8080/api/v1/usuarios/validar-contrasena \
  -H "Content-Type: application/json" \
  -d '{"password": "short1"}'
# Esperado: "Válida: false, Fortaleza: DÉBIL"

# Caso 2: MODERADA
curl -X POST http://localhost:8080/api/v1/usuarios/validar-contrasena \
  -H "Content-Type: application/json" \
  -d '{"password": "Mod1!"}'
# Esperado: "Válida: false, Fortaleza: DÉBIL" (falta longitud)

# Caso 3: FUERTE
curl -X POST http://localhost:8080/api/v1/usuarios/validar-contrasena \
  -H "Content-Type: application/json" \
  -d '{"password": "Strong123!"}'
# Esperado: "Válida: true, Fortaleza: FUERTE"

# Caso 4: MUY FUERTE
curl -X POST http://localhost:8080/api/v1/usuarios/validar-contrasena \
  -H "Content-Type: application/json" \
  -d '{"password": "VeryStrong123!Secure"}'
# Esperado: "Válida: true, Fortaleza: MUY FUERTE"
```

---

## 🔄 Flujo Completo de Usuario

### Scenario: Nuevo Usuario y Cambio de Contraseña

```bash
# 1. Crear usuario
USER_ID=$(curl -s -X POST http://localhost:8080/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "pNombre": "TestUser",
    "sNombre": "Test",
    "aPaterno": "Test",
    "aMaterno": "Test",
    "email": "test@test.com",
    "telefono": "+56123456789",
    "direccion": "Test Address",
    "passwordHashed": "InitialPassword123!"
  }' | jq -r '.idUsuario')

echo "Usuario creado con ID: $USER_ID"

# 2. Autenticarse con contraseña inicial
curl -X POST http://localhost:8080/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\": $USER_ID, \"password\": \"InitialPassword123!\"}"
# Esperado: 200 OK

# 3. Cambiar contraseña
curl -X PUT http://localhost:8080/api/v1/usuarios/$USER_ID/cambiar-contrasena \
  -H "Content-Type: application/json" \
  -d '{
    "oldPassword": "InitialPassword123!",
    "newPassword": "UpdatedPassword456!"
  }'
# Esperado: 200 OK

# 4. Intentar autenticarse con contraseña antigua (debe fallar)
curl -X POST http://localhost:8080/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\": $USER_ID, \"password\": \"InitialPassword123!\"}"
# Esperado: 401 Unauthorized

# 5. Autenticarse con nueva contraseña (debe funcionar)
curl -X POST http://localhost:8080/api/v1/usuarios/authenticate \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\": $USER_ID, \"password\": \"UpdatedPassword456!\"}"
# Esperado: 200 OK
```

---

## 📊 Matriz de Tests Esperados

| # | Test | Status Esperado | Notas |
|---|------|-----------------|-------|
| 1 | Crear usuario válido | 201 | Password hasheada |
| 2 | Crear usuario débil | 400 | Validación de contraseña |
| 3 | Validar fuerte | 200 | Válida: true |
| 4 | Validar débil | 200 | Válida: false |
| 5 | Autenticar correcto | 200 | Exitosa |
| 6 | Autenticar incorrecto | 401 | Inválidas |
| 7 | Autenticar inexistente | 401 | Manejo graceful |
| 8 | Cambiar exitoso | 200 | Contraseña actualizada |
| 9 | Cambiar anterior incorrecta | 401 | Seguridad |
| 10 | Cambiar nueva débil | 400 | Validación |
| 11 | Cambiar igual anterior | 400 | Prevención |
| 12 | Reset admin | 200 | Éxito |
| 13 | Reset inexistente | 404 | No encontrado |
| 14 | Fortaleza niveles | 200 | Correcta reportada |

---

## 🐛 Troubleshooting

### Error: "Password cannot be resolved to a type"
**Solución:** Ejecutar `mvn clean` y recompilar. Los archivos Password.java deben estar eliminados.

### Error: "Contraseña no válida" en creación
**Solución:** Verificar que la contraseña cumple: 8+ chars, mayúscula, minúscula, número, carácter especial.

### Error: "Autenticación fallida" inesperadamente
**Solución:** 
1. Verificar que el usuario existe
2. Verificar que la contraseña es exacta (case-sensitive)
3. Verificar que la base de datos tiene los datos correctos

### Error: 500 en cambio de contraseña
**Solución:** Revisar logs del servidor para detalles específicos del error.

---

## 📝 Reporte de Tests

**Fecha:** [Fecha del test]
**Ambiente:** [Dev/Staging/Production]
**Versión:** 2.0.0

| Test | ✅/❌ | Notas |
|------|-------|-------|
| Test 1 | ✅ | Completado |
| Test 2 | ✅ | Completado |
| Test 3 | ✅ | Completado |
| ... | | |
| **RESUMEN** | **✅ TODOS PASARON** | Listo para producción |

---

## 📋 Checklist Final

- ⬜ Todos los tests unitarios pasaron
- ⬜ Tests de integración pasaron
- ⬜ Validación de contraseña funciona
- ⬜ Autenticación funciona
- ⬜ Cambio de contraseña funciona
- ⬜ Reset de contraseña funciona
- ⬜ Manejo de errores es correcto
- ⬜ Performance es aceptable
- ⬜ Logs son claros y útiles
- ⬜ Documentación está actualizada

---

**Última actualización:** 2025-11-09
