# pb_task_24_04

Swagger file at: http://localhost:8080/swagger-ui.html


## Table of Contents

- [Technologies](#technologies)
- [Installation](#installation)
- [Configuration](#configuration)

## Technologies

The project uses the following technologies and frameworks:

- Java
- Spring (Framework, Boot, MVC, Data JPA)
- Postgresql (as database)
- H2 (database for testing)
- Lombok (for boilerplate reduction)
- Maven (for project management)
- Jackson (XML parsing)
- Opencsv (CSV parsing)
- Commons io (Get file info)
- Springdoc openapi (Swagger documentation)
- Git (for version control)

## Installation

To run the project locally, follow these steps:

1. Clone the repository:

   ```
   git clone https://github.com/YaremaMaksym/pb_task_24_04.git
   ```

2. Open the project in your preferred IDE.

3. Set up the database:

   * Install and configure PostgreSQL on your system.
   * Create database 'pb_db'
   * Update the `application.yaml` file (see [Configuration](#configuration)) with your PostgreSQL credentials.

4. Run the application

The application should now be running on [http://localhost:8080](http://localhost:8080).

## Configuration
The project uses the `application.yml` file to configure the database connection. Here is an example of the file contents:

```
spring:
    application:
        name: pb_task_24_04
    datasource:
        url: jdbc:postgresql://localhost:5432/pb_db
        username: postgres
        password: pass
    jpa:
        hibernate:
            ddl-auto: update

```
Make sure to replace `postgres` and `pass` with your actual PostgreSQL database credentials.
