# Library Management System

This project is a Library Management System developed as part of university class assignment. The application allows users to browse books, register with different roles, and manage the library database with varying levels of access and functionality. The project was built using Spring Boot, Spring Security, MySQL, Thymeleaf, and Bootstrap.

## Table of Contents
- [Features](#features)
- [Roles and Functionalities](#roles-and-functionalities)
- [Default User Logins](#default-user-logins)
- [Usage](#usage)
- [Security](#security)
- [Technologies Used](#technologies-used)
- [Video Demonstration](#video-demonstration)

## Features
- Home page for browsing books
- User registration for Readers and Publishers
- Role-based access control with different functionalities for each role
- Password hashing with random hashing functions
- CSRF protection
- Administrator panel for managing user roles
- Design using Bootstrap
- Error handling with appropriate user messages

## Roles and Functionalities

### Unregistered User
- Can view book details

### Reader
- Can borrow books
- Can purchase books

### Publisher
- Can add new books
- Can edit owned books
- Can remove owned books

### Librarian
- Can see all borrowed books
- Can handle book returns
- Can delete any book

### Administrator
- Can view all user accounts
- Can manage all user accounts
- Can delete any user account
- Can delete any book

## Default User Logins

- **Administrator**
  - Username: admin
  - Password: password

- **Librarian**
  - Username: librarian
  - Password: password

- **Publisher**
  - Username: publisher
  - Password: password

- **Reader**
  - Username: reader
  - Password: password

## Usage
- Visit the home page to browse books.
- Register as a Reader or Publisher using the respective registration forms.
- Log in with your credentials to access role-specific functionalities.
- Administrators can manage user roles through the admin panel.

## Security
- Passwords are hashed using bcrypt, PBKDF2, MD4, or SHA-256.
- CSRF protection is implemented to secure the application against cross-site request forgery attacks.

## Technologies Used
- Spring Boot
- Spring Security
- MySQL
- Thymeleaf
- Bootstrap
- HTML/CSS/JavaScript

## Video Demonstration
A short video demonstration of the application can be found [here](https://youtu.be/2I9QRXFDj-Y).

