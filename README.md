
# Real-Estate Platform

A comprehensive full-stack real estate platform designed to connect property owners with potential buyers or renters. The platform allows users to browse, search, and list properties with advanced features such as account upgrades and paid promotions via MoMo E-Wallet integration.

## 🚀 Key Features

### 🏡 Property Listings & Management
- **Post Creation:** Users can easily create and manage property listings with detailed descriptions and images.
- **Interactive Listings:** Detailed property pages featuring user interactions such as commenting, rating, and reporting posts to ensure quality content.
- **Media Management:** Seamless integration with **Cloudinary API** for robust image upload and storage.

### 🔍 Advanced Search
- **Full-Text Search:** Implemented powerful full-text search capabilities, allowing users to quickly find properties based on specific keywords, locations, and various listing attributes.

### 💳 Account Upgrades & Monetization (MoMo Integration)
- **MoMo Top-up System:** Integrated a secure payment gateway using **MoMo E-Wallet**. Users can seamlessly add funds to their in-app wallet balance.
- **VIP Account Upgrade:** Users can use their topped-up balance to purchase premium/VIP account tiers.
- **Boost Listings (Top Search Priority):** Accounts that have been upgraded to premium tiers benefit from an automated algorithm that prioritizes and pushes their property posts to the very top of the search results, maximizing visibility and reach to potential buyers/renters.

### 🔐 Security & Authentication
- **OAuth2 & Social Login:** Integrated **Google OAuth2** for quick and secure social logins.
- **JWT Authentication:** Implemented robust JSON Web Token (JWT) based authentication for secure API access.
- **Session Management:** Utilized **Redis** for efficient session management, token storage, and caching to enhance security and performance.

## 🛠️ Tech Stack

### Backend
- **Java / Spring Boot:** Core framework for building robust RESTful APIs.
- **Spring Security:** For handling authentication and authorization (JWT & OAuth2).
- **MySQL:** Primary relational database for storing user data, property listings, and transaction history.
- **Redis:** Used for caching and managing JWT refresh tokens.
- **MoMo Payment API:** Integrated for handling real-time wallet top-ups.

### Frontend
- **React.js:** Core library for building the user interface.
- **Redux Toolkit:** Used for efficient global state management.
- **Cloudinary:** Handled frontend image uploads directly to the cloud.

## 💡 Architecture & Implementation Details

- **Payment Flow & Account Upgrading:** The MoMo integration uses an HMAC-SHA256 signature to securely request payment URLs. Upon successful payment on the MoMo app/website, the system processes Webhook (IPN) callbacks to update user balances in real-time. This balance is then used by the internal billing system to process VIP account upgrades and boost listings.
- **Search Algorithm:** The database queries are optimized to sort and prioritize results based on the account's VIP status, ensuring that premium users consistently receive top visibility.
- **Security:** Passwords are securely hashed, and API endpoints are protected based on user roles (e.g., standard users vs. premium users vs. admins).

---
*Developed with a focus on clean code, security, and a seamless user experience.*

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

