# Real Estate Backend

## Overview
This is the backend for the Real Estate project, developed using **Java Spring Boot**. It provides a robust, scalable, and secure API for handling authentication, payments, real-time interactions, and advanced search functionalities.

## Tech Stack
- **Spring Boot**: Backend framework for building RESTful APIs
- **Spring Security & JWT**: Secure authentication and authorization
- **OAuth2**: Integration with Google OAuth2 for seamless login
- **Redis**: Session management and caching
- **MoMo E-Wallet API**: Payment gateway integration
- **Elasticsearch**: Full-text and dynamic search
- **WebSockets**: Real-time commenting system
- **PostgreSQL/MySQL**: Relational database management

## Features
### Authentication & Authorization
- **JWT Authentication**: Token generation, verification, and refresh with secure encryption.
- **OAuth2 Integration**: Google OAuth2 login for enhanced security and user experience.
- **Session Management with Redis**: Supports remote logout and session refresh.

### Payment Processing
- **MoMo E-Wallet Integration**: Handles payment requests, transaction verification, and return processing.

### Search & Filtering
- **Full-Text Search**: Powered by Elasticsearch for fast and accurate searches.
- **Dynamic Search**: Advanced filtering options for real estate listings.

### Real-Time Features
- **WebSockets for Comments**: Enables instant synchronization of user comments in real-time.

## Getting Started
### Prerequisites
Ensure you have the following installed on your system:
- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL/MySQL](https://www.postgresql.org/)
- [Redis](https://redis.io/)

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/real-estate-backend.git
   ```
2. Navigate to the project folder:
   ```sh
   cd real-estate-backend
   ```
3. Configure the database in `application.properties` or `application.yml`.
4. Install dependencies and build the project:
   ```sh
   mvn clean install
   ```
5. Run the application:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints
| Method | Endpoint                 | Description                           |
|--------|--------------------------|---------------------------------------|
| POST   | /auth/register           | User registration                    |
| POST   | /auth/login              | User login with JWT                  |
| GET    | /auth/refresh            | Refresh JWT token                    |
| GET    | /properties              | Get property listings                 |
| POST   | /payments/momo           | Process MoMo E-Wallet payments       |
| GET    | /search                  | Perform full-text search             |
| WS     | /comments                | Real-time commenting via WebSockets  |

## Contributing
1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -m 'Add feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Open a Pull Request

## License
This project is licensed under the MIT License.

