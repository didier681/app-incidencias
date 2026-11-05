# Incidencias Backend

API REST para gestionar incidencias y solicitudes de servicios comunitarios. Es el backend de un TCC universitario y está construido con arquitectura hexagonal.

## Descripción del sistema

- **Usuarios generales:** se registran, reportan incidencias (por ejemplo, un poste dañado), solicitan servicios comunitarios y consultan el estado de lo que han creado.
- **Administradores:** gestionan usuarios y categorías, consultan todas las incidencias y solicitudes, y cambian su estado.

| Entidad | Estados |
|---|---|
| Incidencia | `PENDIENTE`, `EN_PROCESO`, `RESUELTA` |
| Servicio comunitario | `SOLICITADO`, `APROBADO`, `RECHAZADO`, `FINALIZADO` |

Cada registro empieza en el primer estado de su lista. El sistema no impone un orden entre estados: un administrador puede pasar a cualquiera de ellos.

## Stack

- Spring Boot 4.1.1 (Web MVC, Data JPA, Security, Validation)
- Java 21
- PostgreSQL
- JWT (jjwt 0.12.6) y contraseñas cifradas con BCrypt
- JUnit 5 y Maven (incluye el wrapper `mvnw`)

## Cómo ejecutarlo

### Requisitos

- JDK 21
- PostgreSQL en `localhost:5432`, con el usuario `postgres` y una base de datos llamada `incidencias_db`:

  ```sql
  CREATE DATABASE incidencias_db;
  ```

Las tablas se crean solas al arrancar (`spring.jpa.hibernate.ddl-auto=update`).

### Variables de entorno

| Variable | Uso | Valor por defecto |
|---|---|---|
| `DB_PASSWORD` | Contraseña de PostgreSQL | `root` |
| `JWT_SECRET` | Clave para firmar los tokens; debe tener al menos 32 bytes | Clave de desarrollo definida en `application.properties` |

Los valores por defecto son solo para desarrollo local. En producción hay que definir ambas variables, con una clave JWT nueva.

### Arranque (PowerShell)

```powershell
$env:DB_PASSWORD = "tu_contraseña"
$env:JWT_SECRET = "una-clave-aleatoria-de-al-menos-32-caracteres"
.\mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

### Crear el primer administrador

El registro público siempre crea usuarios con rol `USUARIO_GENERAL`, y `POST /api/usuarios` ya exige ser administrador. Por eso el primer administrador se crea así:

1. Registra el usuario con `POST /api/auth/registro`.
2. Cambia su rol en la base de datos:

   ```sql
   UPDATE usuarios SET rol = 'ADMIN' WHERE correo = 'admin@correo.com';
   ```

### Pruebas

```powershell
.\mvnw test
```

`IncidenciasBackendApplicationTests` arranca Spring y necesita PostgreSQL activo. Las pruebas de los servicios usan implementaciones falsas en memoria y no necesitan base de datos. Para correr solo estas:

```powershell
.\mvnw test -Dtest="IncidenciaServiceImplTest,ServicioComunitarioServiceImplTest"
```

## Endpoints

Salvo los de `/api/auth`, todos requieren el encabezado `Authorization: Bearer <token>`, con el token que devuelve el login.

"Autenticado" significa cualquier usuario con sesión válida, sea `USUARIO_GENERAL` o `ADMIN`.

### Autenticación

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| POST | `/api/auth/login` | Público | Inicia sesión; devuelve el token y el rol |
| POST | `/api/auth/registro` | Público | Crea un usuario con rol `USUARIO_GENERAL` |

### Usuarios

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| GET | `/api/usuarios` | `ADMIN` | Lista los usuarios |
| POST | `/api/usuarios` | `ADMIN` | Crea un usuario con el rol indicado |
| DELETE | `/api/usuarios/{id}` | `ADMIN` | Elimina un usuario |

### Categorías

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| GET | `/api/categorias` | Autenticado | Lista las categorías |
| POST | `/api/categorias` | `ADMIN` | Crea una categoría |
| DELETE | `/api/categorias/{id}` | `ADMIN` | Elimina una categoría |

### Incidencias

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| POST | `/api/incidencias` | `USUARIO_GENERAL`, `ADMIN` | Reporta una incidencia |
| GET | `/api/incidencias` | `ADMIN` | Lista las incidencias; filtros opcionales `?estado=` y `?categoriaId=` |
| GET | `/api/incidencias/mis-incidencias` | Autenticado | Lista las incidencias del usuario actual |
| PATCH | `/api/incidencias/{id}/estado` | `ADMIN` | Cambia el estado de una incidencia |

### Servicios comunitarios

| Método | Ruta | Rol | Descripción |
|---|---|---|---|
| POST | `/api/servicios-comunitarios` | `USUARIO_GENERAL`, `ADMIN` | Solicita un servicio |
| GET | `/api/servicios-comunitarios` | `ADMIN` | Lista las solicitudes; filtro opcional `?estado=` |
| GET | `/api/servicios-comunitarios/mis-solicitudes` | Autenticado | Lista las solicitudes del usuario actual |
| PATCH | `/api/servicios-comunitarios/{id}/estado` | `ADMIN` | Cambia el estado de una solicitud |

## Arquitectura hexagonal

El paquete base es `com.didier.incidencias_backend`. Las dependencias siempre apuntan hacia el dominio:

```text
adapter.in.web  ──►  port.in  ◄──  application.service  ──►  port.out  ◄──  adapter.out.persistence
(controladores)      (casos de uso)   (implementación)       (contratos)     (JPA)
```

```text
src/main/java/com/didier/incidencias_backend/
├── domain/                          Núcleo del negocio; no importa Spring ni JPA
│   ├── model/                       Entidades, enums y excepciones de negocio
│   └── port/
│       ├── in/                      Lo que el sistema ofrece (IncidenciaUseCase)
│       └── out/                     Lo que el sistema necesita (IncidenciaRepositoryPort)
├── application/
│   └── service/                     Implementa los casos de uso (IncidenciaServiceImpl)
└── infrastructure/
    ├── adapter/
    │   ├── in/web/                  Controladores, DTO y manejo global de errores
    │   └── out/persistence/         Entidades JPA, repositorios, mappers y adaptadores
    └── security/                    JWT, filtro de autenticación y reglas de acceso
```

| Capa | Responsabilidad | Puede depender de |
|---|---|---|
| Dominio | Modelo del negocio y contratos (puertos) | Solo Java |
| Aplicación | Reglas de los casos de uso | Dominio; usa `@Service` de Spring para registrar los servicios |
| Infraestructura | HTTP, base de datos y seguridad | Dominio y frameworks |

Las pruebas lo demuestran: `IncidenciaServiceImplTest` ejecuta el servicio real con repositorios falsos en memoria, sin Spring ni base de datos.

## Principios SOLID

| Principio | Clase de ejemplo | Cómo se aplica |
|---|---|---|
| **S** Responsabilidad única | `IncidenciaMapper` | Solo convierte entre `Incidencia` e `IncidenciaJpaEntity`. No guarda datos ni aplica reglas de negocio. |
| **O** Abierto/cerrado | `AuthServiceImpl` | Depende de `PasswordEncoderPort` y `TokenGeneratorPort`. Para cambiar BCrypt o JWT basta con una nueva implementación del puerto, sin modificar esta clase. |
| **L** Sustitución de Liskov | `IncidenciaRepositoryAdapter` | Implementa `IncidenciaRepositoryPort`, igual que el repositorio falso de `IncidenciaServiceImplTest`. `IncidenciaServiceImpl` funciona con cualquiera de los dos. |
| **I** Segregación de interfaces | `LoginUseCase` | Solo expone el login, separado de `UsuarioUseCase`. `AuthServiceImpl` implementa únicamente lo que le corresponde. |
| **D** Inversión de dependencias | `IncidenciaServiceImpl` | Depende de las interfaces `IncidenciaRepositoryPort`, `UsuarioRepositoryPort` y `CategoriaRepositoryPort`, no de JPA. |

## Patrones de diseño

| Patrón | Ejemplo | Para qué se usa |
|---|---|---|
| Adapter | `IncidenciaRepositoryAdapter`, `PasswordEncoderAdapter` | Adapta Spring Data JPA y Spring Security a los puertos que define el dominio. |
| Mapper | `IncidenciaMapper`, `UsuarioMapper` | Convierte el modelo de dominio en entidad JPA y viceversa, para que el dominio no conozca la persistencia. |
| DTO | `IncidenciaRequest`, `IncidenciaResponse` | Define lo que entra y sale por la API. Por ejemplo, `UsuarioResponse` no expone la contraseña. |
| Repository / Port | `IncidenciaRepositoryPort`, `IncidenciaJpaRepository` | El puerto expresa qué datos necesita el negocio; el repositorio JPA resuelve cómo obtenerlos. |
| Inyección por constructor | `IncidenciaController`, `IncidenciaServiceImpl` | Las dependencias son `final` y explícitas, y las pruebas pueden pasar implementaciones falsas sin usar Spring. |
