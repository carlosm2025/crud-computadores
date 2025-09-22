# 🖥️ CRUD Empresa de Computadores

**ESPECIALIZACIÓN EN DESARROLLO DE SOFTWARE**  
**UNIMINUTO**  
**Estudiante: CARLOS GERARDO MEDINA LOPEZ**

---

Sistema CRUD básico para gestión de computadores desarrollado con **Java Spring Boot** y **MySQL**.

## 🚀 Características

- **Arquitectura MVC** (Model-View-Controller)
- **Base de datos MySQL** para persistencia
- **API REST** completa
- **Operaciones CRUD** (Create, Read, Update, Delete)
- **Búsquedas avanzadas** por marca, modelo y precio

## 📋 Requisitos

- **Java JDK 17** o superior
- **Maven 3.6+**
- **MySQL 8.0+**

## ⚙️ Configuración

### 1. Base de datos MySQL
```sql
CREATE DATABASE computadores_db;
USE computadores_db;

CREATE TABLE computadores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    procesador VARCHAR(100) NOT NULL,
    memoria_ram VARCHAR(50) NOT NULL,
    almacenamiento VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 2. Configuración de conexión
Editar `src/main/resources/application-mysql.properties`:
```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/computadores_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=admin
```

## 🏃‍♂️ Ejecución

```bash
# Compilar y ejecutar con MySQL
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

La aplicación estará disponible en: **http://localhost:8080**

## 📡 API Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/computadores` | Obtener todos los computadores |
| GET | `/api/computadores/{id}` | Obtener computador por ID |
| POST | `/api/computadores` | Crear nuevo computador |
| PUT | `/api/computadores/{id}` | Actualizar computador |
| DELETE | `/api/computadores/{id}` | Eliminar computador |
| GET | `/api/computadores/buscar` | Búsqueda avanzada |

### Ejemplo de uso:
```bash
# Obtener todos los computadores
curl http://localhost:8080/api/computadores

# Crear nuevo computador
curl -X POST http://localhost:8080/api/computadores \
  -H "Content-Type: application/json" \
  -d '{
    "marca": "Dell",
    "modelo": "Inspiron 15",
    "procesador": "Intel Core i5",
    "memoriaRam": "8GB DDR4",
    "almacenamiento": "512GB SSD",
    "precio": 899.99,
    "stock": 10
  }'
```

## 🏗️ Estructura del Proyecto

```
src/main/java/com/empresa/computadores/
├── ComputadoresApplication.java     # Clase principal
├── controller/
│   └── ComputadorController.java    # Controlador REST
├── model/
│   └── Computador.java              # Entidad JPA
├── repository/
│   └── ComputadorRepository.java    # Repositorio JPA
└── service/
    └── ComputadorService.java       # Lógica de negocio
```

## 📊 Modelo de Datos

```java
public class Computador {
    private Long id;
    private String marca;
    private String modelo;
    private String procesador;
    private String memoriaRam;
    private String almacenamiento;
    private Double precio;
    private Integer stock;
    private LocalDateTime fechaCreacion;
}
```

---
### 1. Configurar Java -------------------------------
## export JAVA_HOME="/c/Program Files/Java/jdk-17.0.2"
## export PATH="$JAVA_HOME/bin:$PATH"

# 2. Verificar Java --------------------------------------
## java -version

# 3. Ejecutar aplicación ----------------------------------------
## "C:\Program Files\Apache\Maven\apache-maven-3.9.11\bin\mvn" spring-boot:run -Dspring-boot.run.profiles=mysql

## http://localhost:8080

