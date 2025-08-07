# Todo Backend

This is a Spring Boot backend for the Todo app.

## Features
- REST API for managing tasks and users
- JWT-based authentication
- PostgreSQL database integration
- Email service using Gmail SMTP

## Setup

### Environment Variables

Create environment variables for sensitive info:

- `DB_NAME` - your PostgreSQL database name  
- `DB_USERNAME` - database username  
- `DB_PASSWORD` - database password  
- `SMTP_USERNAME` - Gmail address for sending emails  
- `SMTP_PASSWORD` - Gmail app password  
- `JWT_SECRET` - secret key for JWT token signing

### application.properties

The application uses these variables from the environment. Example properties:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/${DB_NAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.mail.username=${SMTP_USERNAME}
spring.mail.password=${SMTP_PASSWORD}

jwt.secret=${JWT_SECRET}
