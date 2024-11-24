# Spring Boot Project: Statement Viewer 🧑‍💻

This project is a **Spring Boot** application designed to manage and display financial statements for users and administrators with specific features and access restrictions.

## Features 🚀

### Admin Features
- Admin users can:
  - View all financial statements.
  - Apply advanced filters to search and categorize statements.

### User Features
- Regular users can:
  - View financial statements from the past **three months** only.
  - Access a simplified and limited interface for statement viewing.

### Testing & Coverage 🧪
- Includes **JUnit tests** to ensure application reliability and functionality.
- Generates a **test coverage report and Sonarcube report** located in the `/src/main/resources/reports` directory.

## Technologies Used
- **Java**: Primary programming language.
- **Spring Boot**: Backend framework.
- **Spring Data JPA**: For database interactions.
- **JUnit 5**: For unit testing.
- **JaCoCo**: For test coverage reporting.

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/yourusername/statement-viewer.git
cd statement-viewer
mvn clean install
mvn spring-boot:run
```

Feel free to contribute or raise issues if you encounter any! 🚀
