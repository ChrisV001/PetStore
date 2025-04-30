# Pet Store Application

A Spring Boot application for managing users, pets, and purchase history.

## Prerequisites

- Used Java 24, the latest version, initialized with Spring Boot that Intellij offers.
- Maven 3.4.5
- PostgreSQL

## Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/ChrisV001/PetStore.git
   cd petstore
   ```

2. **Configure environment variables** Create a `.env` file at the project root. Enter the variables to connect with your local database

   ```bash
   DB_HOST=host
   DB_PORT=port
   DB_NAME=dbname
   DB_USER=user
   DB_PASSWORD=password
   ```

4. **Build and run the project**
- Run the application via Intellij IDEA, or you will need to set up the local variables according to the operating system that you will run this app.
- You could use this command if you want to run it through the terminal:
  ```bash
  ./mvnw clean package
  ```
  - The app will start on: `http://localhost:8080`

## API Endpoints

### REST (JSON)

- **Users**

    - `GET /user/all` — list all users
    - `GET /user/create-users` — create 10 random users
    - `GET /user/buy-pets` — perform purchase for each user

- **Pets**

    - `GET /pet/all` — list all pets
    - `GET /pet/create-pets` — create 20 random pets

- **History**

    - `GET /logs/get-all` — list purchase history

### GraphQL

- Single endpoint: `POST /graphql`
- Interactive IDE: `http://localhost:8080/graphiql`

Refer to `src/main/resources/graphql/petstore.graphqls` for schema details.

## Testing

- You can run them through Intellij one by one and test them, or you can use the command below:

- **Unit tests**
  ```bash
  ./mvnw test
  ```

## Notes

- By default, Hibernate will auto-create/update the schema in Postgres.
- Secrets are loaded from `.env` via `dotenv-java`.

