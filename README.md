# Spring Boot Practica Assassins

Este es un proyecto de práctica para aprender y trabajar con Spring Boot.

## Cómo levantar el proyecto

### Generar llaves para OAuth2 con JWT

1. Construye la llave privada:
    ```bash
    openssl genpkey -algorithm RSA -out app.key -pkeyopt rsa_keygen_bits:2048
    ```
2. Genera la llave pública:
    ```bash
    openssl rsa -pubout -in app.key -out app.pub
    ```

### Ejecutar con Docker

1. Construye el proyecto con Maven:
    ```bash
    mvn clean install
    ```
2. Construye la imagen Docker:
    ```bash
    docker build -t db-h2 .
    ```
3. Levanta los contenedores con Docker Compose:
    ```bash
    docker compose up -d
    ```

4. Ejecuta la aplicación Spring Boot:
    ```bash
    java -jar target/db-h2.jar
    ```

### Cargar Endpoints en POSTMAN

1. Importar el siguiente archivo en postman que está dentro de la siguiente ruta:
    ```bash
    src\main\resources\templates\Spring Practica for Assassins.postman_collection.json
    ```

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un "issue" para discutir lo que te gustaría cambiar.
