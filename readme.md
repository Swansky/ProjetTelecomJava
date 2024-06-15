# BatchProcessor

## Introduction

This project is a result of a project for my school. The goal was to create a batch processor that monitors a directory for new CSV files, parses the files to extract user data, and inserts the data into a database.
Requirements can be found [here](PROJET.md)

## Table of Contents
1. [Architecture](#architecture)
2. [Setup](#setup)
3. [Usage](#usage)
4. [Technical Choices](#technical-choices)
5. [Class Details](#class-details)
6. [Error Handling](#error-handling)
7. [Testing](#testing)
8. [License](#license)


## Architecture

The application is composed of the following components:

- **Main Class (`Program`)**: Entry point of the application.
- **FileWatcher**: Monitors a directory for new files.
- **CSVParser**: Parses CSV files to extract user data.
- **DatabaseManager**: Handles database operations (like create connection).
- **Repository (`UserRepository`)**: Handles database operations (like insert in batch).
- **FileMover**: Moves processed files to another directory.
- **Models (`Reimbursement`)**: Represents the data structure for the user.

## Setup

### Prerequisites

- Java Development Kit (JDK) 21 or higher
- Maven (for dependency management)
- MariaDB

### Configuration

A configuration file (`application.properties`) is present in resources directory with the following content:

```properties
# Database connection properties
db.uri=jdbc:mariadb://localhost:3306/project_telecom_java
db.username=root
db.password=example
```

### Building the Project

Clone the repository and build the project using Maven:

```bash
git clone https://github.com/Swansky/ProjetTelecomJava.git
cd BatchProcessor
mvn clean install
```

## Technical Choices

1. **Maven**: Used for dependency management and build automation.
2. **WatchService API**: Utilized to monitor the file system for new files.
3. **MariaDB**: Used because it's open-source and easy to set up and because H2 database for test have a mariadb mode.
4. **OpenCSV**: Used for parsing CSV files.
## Class Details

### Main Class (`Program`)

Orchestrates the overall workflow by initializing components and handling the sequence of operations.

### FileWatcher

Monitors the specified directory for new CSV files using Java's `WatchService`.

### CSVParser

Reads and parses CSV files to extract user data into `Reimbursement` objects.

### DatabaseManager

Handles database operations such as create connection.

### FileMover

Moves processed files to a specified directory.

### Models (`User`)

Represents a user record with fields corresponding to the CSV data and database schema.

## Error Handling

The application includes basic error handling mechanisms such as try-catch blocks to catch and handle exceptions. It's essential to handle errors gracefully to avoid crashing the application and to log errors for debugging and maintenance purposes.

## Logging

I use build-in logging system of Java (java.util.logging) to log errors and information messages.

## Testing

To ensure the robustness and reliability of the application, it's crucial to write unit tests and integration tests. Using testing frameworks like JUnit can help automate and streamline the testing process.

### Unit Tests

Some unit tests are written to test individual components of the application in isolation. For the database, I use H2 database in memory to test the repository.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
