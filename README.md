# caffe-management-system
Java-based Cafe Management System. A desktop app (JavaFX GUI or Console ) for managing menus , processing orders , and tracking inventory. Uses SQLite for simple, local data storage and follows OOP principles  for clean, maintainable code.
# Cafe Management System

This project is a simple and efficient desktop application for managing a cafe, developed using Java. It is designed to handle the core daily operations, focusing on clean code, maintainability, and fundamental OOP principles.

## Key Features

* **Menu Management:** Add, update, and delete menu items, including their names and prices.
* **Order Management:** Process new customer orders, calculate the total bill, and record transactions.
* **Inventory Control:** Track the available quantities of menu items.
* **Simple Reporting:** Generate basic reports, such as total daily sales.

## Core Design (OOP)

The application is built using Object-Oriented principles to ensure the code is modular and scalable. The main classes include:

* `MenuItem`: A model class to store data for each menu item (e.g., name, price, quantity).
* `Order`: Manages a list of items for a single customer order and calculates the total price.
* `CafeManager`: The main controller class that manages the application's logic (e.g., adding orders, viewing reports).
* `DatabaseHandler`: A dedicated class (designed as a Singleton) to handle all database interactions, ensuring a single connection point.

## Technology Stack

This project uses a minimal and stable set of technologies for high efficiency and simplicity:

* **Language:** Java (JDK 11 or 17)
* **User Interface (UI):**
    * **JavaFX:** Used for the graphical user interface (GUI) to provide a simple and modern user experience.
    * **Console Application:** A base version is available as a command-line application.
* **Database:**
    * **SQLite:** A simple, serverless, file-based database.
    * **SQLite JDBC:** The driver used to connect the Java application to the SQLite database.
* **Data Handling (Alternative):** Supports using text files (JSON/CSV) with libraries like **Gson** or **Jackson**.
* **Build Tool:** Maven or Gradle is used to manage dependencies (like `sqlite-jdbc`).

## Getting Started

### Prerequisites

* Java JDK 11 or later (from Oracle or OpenJDK)
* An IDE like IntelliJ IDEA Community Edition or Eclipse
* Maven or Gradle (if not integrated into your IDE)

### Installation

1.  Clone the repository to your local machine:
    ```sh
    git clone [YOUR_REPOSITORY_URL]
    ```
2.  Open the project in your IDE (e.g., IntelliJ IDEA).
3.  The IDE will automatically resolve the Maven/Gradle dependencies listed in the `pom.xml` or `build.gradle` file.

## Running the Application

You can run the project directly from your IDE by finding and running the `Main.java` class.

### Building an Executable

You can build a distributable `.jar` file using your IDE's build tools (e.g., in IntelliJ: `File` > `Project Structure` > `Artifacts` > `Build`).
