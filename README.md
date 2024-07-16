# Proyecto Literatula Desafío

El proyecto **Literatula Desafío** es una aplicación Java basada en Spring Boot que permite interactuar con la API de Gutendex para obtener información sobre libros y autores. Los datos extraídos se almacenan en una base de datos PostgreSQL. A continuación, se describen los detalles del proyecto:

## Características

- **Interacción con Gutendex**: La aplicación se conecta a la API de Gutendex para obtener información sobre libros y autores.

- **Base de datos PostgreSQL**: Los datos extraídos se almacenan en una base de datos PostgreSQL.

- **Menú interactivo**: La aplicación presenta un menú interactivo para que el usuario pueda realizar las siguientes acciones:
  - Registrar un nuevo libro.
  - Registrar un nuevo autor.
  - Consultar autores vivos en un año específico.

## Dependencias

El proyecto utiliza las siguientes dependencias:

```xml
<dependencies>
    <!-- ... Otras dependencias ... -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <!-- ... Otras dependencias ... -->
</dependencies>

##Configuración
En el archivo application.properties, configura las siguientes propiedades:

spring.application.name=literatula_desafio
spring.datasource.url=jdbc:postgresql://${DB_HOST}/challenge_literatula
spring.datasource.username=${DB_Users}
spring.datasource.password=${DB_password}
spring.datasource.driver-class-name=org.postgresql.Driver
hibernate.dialect=org.hibernate.dialect.HSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpg.format-sql=true

##Ejecución

-Clona este repositorio.

-Asegúrate de tener JDK 17 y Maven 3.x instalados.

-Ejecuta mvn clean package para compilar el proyecto.

-Ejecuta la aplicación con uno de los siguientes métodos:
- 1. java -jar -Dspring.profiles.active=test target/literatula_desafio-0.1.0.jar
- 2. mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"

Una vez que la aplicación esté en funcionamiento, podrás interactuar con el menú y registrar libros y autores en la base de datos.


Recuerda reemplazar los marcadores `${DB_HOST}`, `${DB_Users}` y `${DB_password}` con los valores específicos de tu entorno
