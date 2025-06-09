# Vending Machine Software Documentation

## Overview
The Vending Machine Software is a Java-based application designed to simulate a vending machine. It allows users to view available products, select items for purchase, and manage inventory through a MySQL database. The user interface is built using Swing (javax.swing) for a responsive and visually appealing experience, while JDBC handles database connectivity for persistent storage of product data.

## Project Setup

### Prerequisites
- **JDK**: Version 17 or later (e.g., OpenJDK or Oracle JDK).
- **IDE**: IntelliJ IDEA, Eclipse, or similar.
- **MySQL**: MySQL Server 8.0 or later, with MySQL Workbench or command-line client.
- **Maven**: Build tool for dependency management.
- **MySQL Connector/J**: JDBC driver for MySQL (added via Maven).

### Project Structure
```
com.vending
├── App.java                # Main entry point to launch the application
├── dao
│   └── ProductDAO.java     # Data Access Object for database operations
├── db
│   └── DatabaseConnection.java  # Utility for JDBC database connection
├── model
│   └── Product.java        # Model class representing a product
└── ui
    └── VendingMachineUI.java  # Swing-based user interface
```

### Installation Steps
1. **Install JDK**:
   - Download and install JDK 17 from [Oracle](https://www.oracle.com/java) or [OpenJDK](https://openjdk.java.net).
   - Set `JAVA_HOME` environment variable to the JDK path.
2. **Install MySQL**:
   - Install MySQL Server and a client (e.g., MySQL Workbench).
   - Start the server: `sudo systemctl start mysql` (Linux) or `net start mysql` (Windows).
3. **Set Up Maven Project**:
   - Create a new Maven project in your IDE.
   - Add the MySQL Connector/J dependency to `pom.xml`:
     ```xml
     <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>8.0.33</version>
     </dependency>
     ```
4. **Database Setup**:
   - Create the database and table using MySQL:
     ```sql
     CREATE DATABASE IF NOT EXISTS vendingmachine_db;
     USE vendingmachine_db;
     CREATE TABLE products (
         id INT AUTO_INCREMENT PRIMARY KEY,
         name VARCHAR(100) NOT NULL,
         price DECIMAL(10,2) NOT NULL,
         quantity INT NOT NULL
     );
     INSERT INTO products (name, price, quantity) VALUES
     ('Cola', 1.50, 10),
     ('Chips', 1.00, 15),
     ('Candy', 0.75, 20);
     ```
   - Update `DatabaseConnection.java` with your MySQL username and password.
5. **Build the Project**:
   - Run in the terminal:
     ```bash
     cd ~/dev/vending-machine/VendingMachine
     mvn clean install
     ```
6. **Run the Application**:
   - Execute: `mvn exec:java`
   - The Swing UI will launch, displaying available products.

## Database Schema
- **Database**: `vendingmachine_db`
- **Table**: `products`
  | Column   | Type          | Description                     |
  |----------|---------------|---------------------------------|
  | id       | INT           | Unique product ID, auto-increment |
  | name     | VARCHAR(100)  | Product name (e.g., "Cola")     |
  | price    | DECIMAL(10,2) | Product price (e.g., 1.50)      |
  | quantity | INT           | Available stock                 |

## Key Components

### Model: Product.java
- **Purpose**: Represents a vending machine product.
- **Attributes**:
  - `id`: Unique identifier.
  - `name`: Product name.
  - `price`: Cost of the product.
  - `quantity`: Stock available.
- **Methods**: Getters, setters, and `toString` for display.

### DAO: ProductDAO.java
- **Purpose**: Handles database operations for products.
- **Key Methods**:
  - `getAllProducts()`: Retrieves all products from the database.
  - `updateProductQuantity(int productId, int newQuantity)`: Updates stock after a purchase.
- **Dependencies**: Uses `DatabaseConnection` for JDBC connectivity.

### Database Connection: DatabaseConnection.java
- **Purpose**: Establishes a connection to the MySQL database.
- **Configuration**:
  - URL: `jdbc:mysql://localhost:3306/vendingmachine_db`
  - User: MySQL username (e.g., `root`).
  - Password: MySQL password.
- **Method**: `getConnection()` loads the MySQL JDBC driver and returns a connection.

### UI: VendingMachineUI.java
- **Purpose**: Provides a Swing-based interface for user interaction.
- **Features**:
  - Displays products in a grid layout.
  - Allows users to click buttons to purchase items.
  - Updates inventory and shows status messages.
- **Aesthetics**:
  - Soft blue background, white panels.
  - Clear Arial fonts (14-20 pt).
  - Light blue buttons with tooltips.
- **Layout**: Uses `BorderLayout` for main panel, `GridLayout` for product buttons.
- **Responsiveness**: Adjusts to window resizing with `pack()` and scrollable product panel.
- **Accessibility**: Tooltips and keyboard navigation (e.g., Enter key).

### Entry Point: App.java
- **Purpose**: Launches the application.
- **Method**: Uses `SwingUtilities.invokeLater` to start the UI on the Event Dispatch Thread.

## Usage
1. **Launch the Application**:
   - Run `mvn exec:java` from the project directory.
   - The UI displays a window titled "Vending Machine".
2. **View Products**:
   - Products (e.g., "Cola ($1.50, Stock: 10)") appear as buttons in a grid.
3. **Purchase a Product**:
   - Click a product button to buy.
   - If stock is available, the quantity decreases, and a message (e.g., "Purchased Cola for $1.50") appears.
   - If out of stock, a message (e.g., "Cola is out of stock!") displays.
4. **Error Handling**:
   - Database errors (e.g., connection failure) show on the status label.

## Troubleshooting
- **Error: "MySQL JDBC Driver not found"**:
  - Ensure the MySQL Connector/J dependency is in `pom.xml`.
  - Run: `mvn clean install`
  - Verify JAR: `ls ~/.m2/repository/mysql/mysql-connector-java/8.0.33/`
- **Database Connection Issues**:
  - Test connection: `mysql -h localhost -P 3306 -u root -p vendingmachine_db`
  - Start MySQL: `sudo systemctl start mysql` (Linux) or `net start mysql` (Windows).
  - Create database/table if missing (see Installation Steps).
- **Build Failure**:
  - Run with debug: `mvn exec:java -e -X`
  - Check `pom.xml` for correct dependencies and plugin configuration.

## System Requirements
- **OS**: Windows, Linux, or macOS.
- **RAM**: 2 GB minimum.
- **Disk Space**: 500 MB for project and MySQL.

## Notes
- Update MySQL credentials in `DatabaseConnection.java` to match your setup.
- Ensure the MySQL server is running before launching the application.
- The UI refreshes after each purchase to reflect updated stock.

## Version
- **Current Version**: 1.0-SNAPSHOT
- **Last Updated**: June 09, 2025