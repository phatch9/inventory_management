# Inventory Management System

## Project Overview

Inventory Management System is a platform tailored for small business owners, enabling them to effectively handle stock management, maintain supplier records, and seamlessly track orders. The system will be implemented as a web application with a three-tier architecture, using JDBC to interact with a MySQL database.

## Key Features

* **Product Management**: Operations to create new products/ update product details/ delete products. Effectively tracking stock levels.
* **Order Management**: Track the status of orders from the time a customer places an order to delivery.
* **Reporting**: Generate stock reports, order reports, and supplier records.

## Instructions for Setting Up and Running the Project

**Step 1: Install Java Development Kit (JDK)**

Download the JDK from Oracle's website or install it using a package manager. To check if Java is installed:

```bash
java --version
```

**Step 2: Set Up MySQL Database**

1. Install MySQL if it's not already installed on your system.

2. Create a Database and Table: After installing MySQL, you need to create a database and tables. This can be done by executing SQL queries in `create_schema.sql`. 

3. Initialize Database: Populate tables with queries in `initialize_data.sql`.

4. Download SQL connector library and import it in Intellj.

**Step 3: Clone the Project Repository to your local machine using Git**

```
https://github.com/katiethng/inventory_management.git
```

**Step 4: Configure Database Connection**

Edit the `DatabaseConnection.java` file to ensure the correct database credentials.  Modify the DB_URL, DB_USER, and DB_PASSWORD fields to match your MySQL configuration.

```
private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory_system";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

**Step 5: Compile and Run the Application**

Open a terminal, navigate to the project directory, and compile that java source files.

```
javac -d bin src/*.java
```

Run the application by executing the following command:
```java -cp bin inventory_management
```

## Dependencies and Required Software
* JDK (Version 8 or higher)
* MySQL Server for database management
* MySQL JDBC Driver for database connectivity

## Additional Configuration Steps
* Add the MySQL JDBC Driver**:
If you are using an IDE (Intellij IDEA/ Eclipse/ etc.), add the JDBC Driver (`mysql-connector-java-x.x.x.jar`) to the project's libraries or classpath.

## Authors

* Thu Nguyen: SID 015065245
* Phat Chau: SID 016878589

#### Creation Date: November 20th, 2024
#### Last Modified Date: December 9th, 2024
