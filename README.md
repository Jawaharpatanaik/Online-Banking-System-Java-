# Online Banking System (Maven WAR)

## Overview
Minimal Java web application (JSP + Servlets + JDBC + MySQL) for demo/learning purposes.
Features:
- User registration (admin approval required)
- Login / Logout
- Account overview
- Fund transfer (between accounts)
- Transaction history
- Admin panel for approving accounts

## Prerequisites
- Java 8+
- Maven
- Tomcat 9
- MySQL (database `bank_db`)
- Set MySQL credentials in `src/main/java/com/bank/util/DBConnection.java` if different.

## Setup
1. Import project into VS Code or IDE as a Maven project.
2. Run SQL in `db/schema.sql` to create database and tables.
3. Build:
```
mvn clean package
```
4. Deploy `target/online-banking.war` to Tomcat `webapps` or run with `mvn tomcat7:deploy` (configure plugin).
5. Open `http://localhost:8080/online-banking/`

## Notes
- Passwords are stored in plaintext in this demo. For production, hash passwords (BCrypt).
- Add input validation and CSRF protections for real applications.
