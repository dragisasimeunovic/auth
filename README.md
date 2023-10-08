# Demo project for authentication

### Pre-requirements

- Download project from this GitHub repository: https://github.com/dragisasimeunovic/auth
- Postgres server
- Java 17
- Postman

### How to run

1. Create database schema called **auth**
2. Open **application.properties** and adapt **spring.datasource.username** and **spring.datasource.password** with your database user credentials
3. Run Spring Boot app

### How to test

**_First way:_**
1. Import Postman collection (postman/Auth.postman_collection.json)
2. Execute **Register** request
3. Use token that you've got and put it as Bearer token for **Health** request (if you are authenticated successfully, you will be able to see _Server is up and running!_ message)

**_Second way:_**
1. Import Postman collection (postman/Auth.postman_collection.json)
2. Execute **Register** request
3. Execute **Login** request with correct credentials
4. Use token that you've got and put it as Bearer token for **Health** request (if you are authenticated successfully, you will be able to see _Server is up and running!_ message)

_**Note:**_ You won't be able to access to /health endpoint if you are not authenticated!

### Important notes

Idea behind this task was to make microservice for authentication. I was thinking about adding some parts for authorization, but I didn't do it because it was not explicitly requested, so I didn't want to complicate or over-engineer current solution.
