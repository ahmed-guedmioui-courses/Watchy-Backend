# Watchy: Movie & TV Series App Backend (Ktor & MongoDB)

This repository contains the backend code for the Watchy Android app, a dynamic platform for exploring movies and TV series. Developed using Ktor for server-side programming and MongoDB Atlas for data storage, the backend adheres to secure and efficient practices to enhance user experience.

## Key Features:

API Endpoint: Serves data requests from the Watchy app, providing information on favorites and bookmarks for a specific user. 
Authentication: Implements JWT-based authentication to guarantee user identity and secure access to personalized data.
User Management: Handles user registration, login, and profile management, securely storing user information in MongoDB Atlas.
Favoriting & Bookmarking: Enables users to create and manage lists of favorite movies and TV shows, synchronizing data between app and backend.
Data Synchronization: Seamlessly synchronizes user data (favorites, bookmarks) between the local Room database (app) and MongoDB Atlas (backend).

## Getting Started:

Clone the repository: git clone https://github.com/ahmed-guedmioui/Watchy-Backend.git
Database connection: Ensure you have a MongoDB Atlas account and configure connection details in the application.conf file.

## Contributions:

We welcome your contributions! Feel free to suggest improvements, fix bugs, add features, or enhance documentation. Please follow the contributing guidelines outlined in the CONTRIBUTING.md file.

Community Appreciation:

A big thank you to the Ktor, Kotlin, and MongoDB communities for their contributions and support. Together, we can make Watchy an even better experience for movie and TV series enthusiasts!
