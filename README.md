#  ☕ PROYECTO DIPLOMADO JAVA ☕️
## 🤓 Francisco Miztli López Salinas 🤓

-----
## Descripción del Proyecto
Un proyecto para la administración de una pequeña tienda de abarrotes.

## Tecnologías utilizadas
- **Java**
- **Spring Boot**
- **Thymeleaf**
- **JPA**
- **MariaDB**

## **Instrucciones para ejecutar** 
### 1.- Clona el repo e importalo en **Intellij**
``` 
git clone https://github.com/1franky/tiendaAbarrotes.git
``` 
---
### 2.- Dirígete al archivo application *application.properties* ubicado en `src/main/resources/` y busca las siguinetes lineas
```
# Images
app.upload.dir=/Users/franciscolopez/imagesProyectos/springWeb/
# app.upload.dir=C:\\springWeb\\
spring.servlet.multipart.max-file-size = 5MB
spring.servlet.multipart.max-request-size = 5MB
```

### Comenta la linea 
- `app.upload.dir=/Users/franciscolopez/imagesProyectos/springWeb/`  
y descomenta la linea 
-  `#app.upload.dir=C:\\springWeb\\`

### Dirigete a la Ruta C:\\ y create la carpeta `springWeb`

____

### 3.- Descomprime la carpeta `images.zip` en la carpeta recien creada de tal modo que quede  `C:\\springWeb\\images`

--- 
### 4.- Regresa al archivo *application.properties* ubicado en `src/main/resources/` y busca las siguientes lineas.
```
spring.datasource.url=jdbc:mariadb://localhost:3306/pvi
spring.datasource.username=root
#spring.datasource.password=${passwordDB}
spring.datasource.password=Francisco31.
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```
#### Configura la url username y password de tu base de datos.

### 5.- Dentro de `src/main/resources/` se encuentran los siguientes ficheros:
* **schema.sql**
* **data.sql**

En estos se encuentran la definición de la base de datos como algunos datos de prueba.

#### 5.1.- Ejecuta estos archivos en tu base datos local.
#### 5.2.- En lugar de ejcutar estos archivos en tu consola puedes Ejecuatar *`PVIFranciscoLopezApplicationTests`* ubicado en `src/test/java/mx/unam/dgtic` y ejecutalo esto creara el schema y cargara los datos


---

### 6. 3. Ejecuta `mvn spring-boot:run`

En tu navegador dirigite a http://localhost:8080/ 

Note: Por el momento Funcionando operaciones *CRUD* en tablas *Categories, Images, Proveedores, Phones, Emails* y Funcionando sin todas las operacion *CRUD* *Products*