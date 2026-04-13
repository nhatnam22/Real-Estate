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