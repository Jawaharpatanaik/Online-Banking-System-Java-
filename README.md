# Online Banking System (JSP + Servlets + MySQL)

## Introduction

This is a simple online banking web application built using Java Servlets, JSP, JDBC, and MySQL.
It is designed as a learning project to understand how a real-world banking system works, including login, account management, fund transfers, and admin approval.

## What the Project Can Do

* Register a new user (account stays inactive until admin approves)
* Login and logout
* View account details and current balance
* Transfer money to another user’s account
* View transaction history
* Admin dashboard to approve newly registered users

## What You Need Installed

Before running the project, make sure you have:

* Java 8 or higher
* Apache Tomcat 9
* MySQL server
* Maven
* Any IDE like VS Code, IntelliJ, or Eclipse

## Database Setup

1. Open MySQL and create the database by running the SQL file located in:

```
db/schema.sql
```

2. Default MySQL settings used in this project:

* Database: `bank_db`
* Username: `root`
* Password: `root`

If your MySQL settings are different, update them here:

```
src/main/java/com/bank/util/DBConnection.java
```

## How to Run the Project

1. Open the project in your IDE as a Maven project.
2. Build the application:

```
mvn clean package
```

3. After the build completes, a `.war` file will be generated in:

```
target/online-banking.war
```

4. Deploy the WAR file to Tomcat’s `webapps` folder.
5. Start Tomcat and open your browser:

```
http://localhost:8080/online-banking/
```

## Default Admin Login

* Email: `admin@bank.com`
* Password: `admin123`

## Important Notes

* Passwords are stored as plain text for learning purposes only.
  In real applications, always hash passwords (e.g., BCrypt).
* Add input validation and security checks such as CSRF protection for production systems.

## Purpose of the Project

This project is mainly meant for:

* Students
* Beginners in full-stack Java
* Learning JDBC and database integration
* Understanding web development using JSP and Servlets

Feel free to customize, extend, and improve the system as needed.
