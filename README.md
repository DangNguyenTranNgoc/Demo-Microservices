# Demo Microservices

Demo project for microservices. This project currently only include service User Authentication and Authorization (UAA). This service store data in the PostgreSQL database server and have two roles (ADMIN and CLIENT). The list of API served:

**Anyone** can:

- User login and return a JWT Token if success.
- User signup and return a JWT Token if success.

**CLIENT** role can:

- All service above.
- Get infomation of current user.
- Get new JWT token.

**ADMIN** role can:

- All service above.
- Get host name (for testing when deploy in multi container)
- Delete user.
- Search for a user.

For more detail or use these API, deploy the service and go to link: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## ✨ Technology ✨

    ✅ Spring Boot

    ✅ PostgreSQL

    ✅ Docker

    ❌ K8S

## ✨ How to ✨

### Run

#### 🔰 Run with Docker 🔰

Step 1: Clone this repo `git clone https://github.com/DangNguyenTranNgoc/Demo-Microservices.git`

Step 2: Install Maven [link here](https://maven.apache.org/install.html)

Step 3: Move to Demo-Microservices/uaa folder and run `build-image.bat`. Input the option (or leave it using the default)

Step 4: Move to Demo-Microservices folder and run `docker-compose -f docker-compose.yml up` (if you don't want to see logs use `docker-compose -f docker-compose.yml -d up`)

Step 5: Open browser with url [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

#### 🔰 Run with without Docker 🔰

Step 1 and 2 as [above](#run-with-docker)

Step 3: Install PostgreSQL [link here](https://www.postgresql.org/download/)

Step 4: Create new database (Optional)

Step 5: Update file `Demo-Microservices/uaa/src/main/resources/bootstrap.yml`

    `postgres` to your database.

    `5432` to your database port.

    `username` to your database login name.

    `password` to your password.

Step 6: Move console to `Demo-Microservices/uaa` and run `mvn clean install`

Step 7: Run `java -jar target target\uaa-<version>.jar` (i.e. `java -jar target target\uaa-1.0.0.jar`)

Step 8: Open browser with url [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

