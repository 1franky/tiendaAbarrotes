# 🏪 Sistema de Administración de Tienda de Abarrotes

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)

## 📋 Descripción
Sistema web para la gestión y administración de una tienda de abarrotes. Desarrollado como proyecto final para el Diplomado en Java.

### 👨‍💻 Autor
Francisco Miztli López Salinas

## 🛠️ Tecnologías Utilizadas
- Java (JDK 17+)
- Spring Boot 3.x
- Spring Data JPA
- Thymeleaf (Motor de plantillas)
- MariaDB (Base de datos)
- Maven (Gestión de dependencias)

## ✨ Características
- Gestión de productos
- Administración de categorías
- Control de proveedores
- Manejo de imágenes
- Gestión de contactos (teléfonos y correos)
- Operaciones CRUD completas para las principales entidades

## 🚀 Guía de Instalación

## 📂 Estructura del proyecto importante
```bash
tiendaAbarrotes/
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── schema.sql
│   │   │   └── data.sql
│   └── test/
│       └── java/mx/unam/dgtic/
│           └── PVIFranciscoLopezApplicationTests.java
```

### Prerrequisitos
- Java JDK 17 o superior
- Maven 3.6+
- MariaDB 10.x
- IDE (recomendado: IntelliJ IDEA)
- Git (Opctional)

### 1. Clonar el Repositorio
```bash
git clone https://github.com/1franky/tiendaAbarrotes.git
cd tiendaAbarrotes
```

### 2. Configuración del Almacenamiento de Imágenes  
2.1. Localiza el archivo `application.properties` en `src/main/resources/`  
2.2. Configura el directorio de almacenamiento de imágenes:
   ```properties
   # Comenta esta línea
   # app.upload.dir=/Users/franciscolopez/imagesProyectos/springWeb/
  
   # Descomenta y usa esta línea para Windows
   app.upload.dir=C:\\springWeb\\
   ```

2.3. Crea el directorio `springWeb` en la unidad C: (Windows)  
2.4. Descomprime el archivo `images.zip` en `C:\springWeb\`  

### 3. Configuración de la Base de Datos   
3.1. En `application.properties`, configura los datos de conexión:

   ```properties
   spring.datasource.url=jdbc:mariadb://localhost:3306/pvi
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
   ```

### 4. Inicialización de la Base de Datos
Tienes dos opciones:
#### Opción A: Ejecución Manual
4.1. Localiza los archivos en `src/main/resources/`:       
    - `schema.sql` (estructura de la base de datos)       
    - `data.sql` (datos de prueba)      
4.2. Ejecuta estos scripts en tu servidor MariaDB     

#### Opción B: Ejecución Automatizada
4.1. Ejecuta `PVIFranciscoLopezApplicationTests` ubicado en `src/test/java/mx/unam/dgtic`   
4.2. Esto creará el schema y cargará los datos automáticamente


### 5. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: http://localhost:8080

## 📝 Estado del Proyecto

### Funcionalidades Implementadas (CRUD Completo) mediante interfaz web
- ✅ Categorías
- ✅ Imágenes
- ✅ Proveedores
- ✅ Teléfonos
- ✅ Correos electrónicos (No enviar solo catalogo)

### En Desarrollo
- ⚠️ Productos (CRUD parcial)
- ⚠️ USERS (Sin empezar)
- ⚠️ Tickets (Sin empezar)
- ⚠️ Clients (Sin empezar)

## 🤝 Contribuciones
Las contribuciones NO son bienvenidas es un proyecto escolar.
1. No hagas fork del repositorio
2. No crees una nueva rama
3. No envies un pull request

## 📄 Licencia
Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para más detalles.
