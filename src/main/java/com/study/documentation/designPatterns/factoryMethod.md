# Factory Method Pattern in Java - Complete Documentation

## Table of Contents

1. [Introduction](#introduction)
2. [Problem Statement](#problem-statement)
3. [Solution Overview](#solution-overview)
4. [UML Structure](#uml-structure)
5. [Implementation Components](#implementation-components)
6. [Basic Implementation](#basic-implementation)
7. [Advanced Examples](#advanced-examples)
8. [Real-World Scenarios](#real-world-scenarios)
9. [Advantages and Disadvantages](#advantages-and-disadvantages)
10. [Best Practices](#best-practices)
11. [Common Pitfalls](#common-pitfalls)

## Introduction

The Factory Method Pattern is a creational design pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created. It encapsulates object creation logic and promotes loose coupling between classes.

**Intent**: Define an interface for creating an object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.

**Also Known As**: Virtual Constructor

## Problem Statement

Consider scenarios where:

-   A class cannot anticipate the class of objects it must create
-   A class wants its subclasses to specify the objects it creates
-   Classes delegate responsibility to one of several helper subclasses
-   You need to localize the knowledge of which helper subclass is the delegate

### Example Problem

```java
// Problematic approach - tight coupling
public class DocumentProcessor {
    public void processDocument(String type) {
        Document doc;
        if (type.equals("PDF")) {
            doc = new PDFDocument(); // Direct instantiation
        } else if (type.equals("Word")) {
            doc = new WordDocument(); // Direct instantiation
        } else if (type.equals("Excel")) {
            doc = new ExcelDocument(); // Direct instantiation
        }
        // What happens when we add new document types?
        doc.process();
    }
}
```

## Solution Overview

The Factory Method Pattern solves this by:

1. Creating an abstract creator class with a factory method
2. Letting concrete creators decide which product to instantiate
3. Eliminating the need for conditional logic in client code
4. Making the system more extensible and maintainable

## UML Structure

```
Creator (Abstract)
├── factoryMethod(): Product (abstract)
├── someOperation(): void
│
├── ConcreteCreatorA
│   └── factoryMethod(): ConcreteProductA
│
└── ConcreteCreatorB
    └── factoryMethod(): ConcreteProductB

Product (Interface/Abstract)
├── ConcreteProductA
└── ConcreteProductB
```

## Implementation Components

### 1. Product Interface/Abstract Class

Defines the interface of objects the factory method creates.

### 2. Concrete Products

Implement the Product interface with specific functionality.

### 3. Creator (Abstract Class)

-   Declares the factory method that returns a Product object
-   May provide default implementation of factory method
-   Usually contains core business logic that relies on Product objects

### 4. Concrete Creators

Override the factory method to return specific Concrete Product instances.

## Basic Implementation

### Step 1: Define the Product Interface

```java
// Product interface
public interface Vehicle {
    void start();
    void stop();
    String getType();
}
```

### Step 2: Create Concrete Products

```java
// Concrete Product 1
public class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car engine started with key ignition");
    }

    @Override
    public void stop() {
        System.out.println("Car engine stopped");
    }

    @Override
    public String getType() {
        return "Car";
    }
}

// Concrete Product 2
public class Motorcycle implements Vehicle {
    @Override
    public void start() {
        System.out.println("Motorcycle started with kick/button");
    }

    @Override
    public void stop() {
        System.out.println("Motorcycle engine stopped");
    }

    @Override
    public String getType() {
        return "Motorcycle";
    }
}

// Concrete Product 3
public class Truck implements Vehicle {
    @Override
    public void start() {
        System.out.println("Truck started with heavy-duty ignition");
    }

    @Override
    public void stop() {
        System.out.println("Truck engine stopped");
    }

    @Override
    public String getType() {
        return "Truck";
    }
}
```

### Step 3: Create Abstract Creator

```java
// Abstract Creator
public abstract class VehicleFactory {

    // Factory method - to be implemented by concrete creators
    public abstract Vehicle createVehicle();

    // Business logic that uses the factory method
    public void deliverVehicle() {
        Vehicle vehicle = createVehicle();
        System.out.println("Preparing " + vehicle.getType() + " for delivery");
        vehicle.start();
        System.out.println(vehicle.getType() + " delivered successfully");
        vehicle.stop();
    }

    // Additional business methods
    public void performMaintenance() {
        Vehicle vehicle = createVehicle();
        System.out.println("Performing maintenance on " + vehicle.getType());
    }
}
```

### Step 4: Implement Concrete Creators

```java
// Concrete Creator 1
public class CarFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }
}

// Concrete Creator 2
public class MotorcycleFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Motorcycle();
    }
}

// Concrete Creator 3
public class TruckFactory extends VehicleFactory {
    @Override
    public Vehicle createVehicle() {
        return new Truck();
    }
}
```

### Step 5: Client Usage

```java
public class FactoryMethodDemo {
    public static void main(String[] args) {
        // Client doesn't need to know about concrete products
        VehicleFactory carFactory = new CarFactory();
        VehicleFactory motorcycleFactory = new MotorcycleFactory();
        VehicleFactory truckFactory = new TruckFactory();

        System.out.println("=== Car Factory ===");
        carFactory.deliverVehicle();

        System.out.println("\n=== Motorcycle Factory ===");
        motorcycleFactory.deliverVehicle();

        System.out.println("\n=== Truck Factory ===");
        truckFactory.deliverVehicle();
    }
}
```

## Advanced Examples

### Example 1: Document Processing System

```java
// Product hierarchy
public abstract class Document {
    protected String fileName;

    public Document(String fileName) {
        this.fileName = fileName;
    }

    public abstract void open();
    public abstract void save();
    public abstract void close();
    public abstract String getFormat();
}

public class PDFDocument extends Document {
    public PDFDocument(String fileName) {
        super(fileName);
    }

    @Override
    public void open() {
        System.out.println("Opening PDF document: " + fileName);
    }

    @Override
    public void save() {
        System.out.println("Saving PDF document with compression");
    }

    @Override
    public void close() {
        System.out.println("Closing PDF document");
    }

    @Override
    public String getFormat() {
        return "PDF";
    }
}

public class WordDocument extends Document {
    public WordDocument(String fileName) {
        super(fileName);
    }

    @Override
    public void open() {
        System.out.println("Opening Word document: " + fileName);
    }

    @Override
    public void save() {
        System.out.println("Saving Word document with formatting");
    }

    @Override
    public void close() {
        System.out.println("Closing Word document");
    }

    @Override
    public String getFormat() {
        return "DOCX";
    }
}

// Creator hierarchy
public abstract class DocumentProcessor {
    public abstract Document createDocument(String fileName);

    public void processDocument(String fileName) {
        Document doc = createDocument(fileName);
        doc.open();

        // Common processing logic
        System.out.println("Processing " + doc.getFormat() + " document...");
        System.out.println("Applying security settings...");
        System.out.println("Validating document structure...");

        doc.save();
        doc.close();
    }
}

public class PDFProcessor extends DocumentProcessor {
    @Override
    public Document createDocument(String fileName) {
        return new PDFDocument(fileName);
    }
}

public class WordProcessor extends DocumentProcessor {
    @Override
    public Document createDocument(String fileName) {
        return new WordDocument(fileName);
    }
}
```

### Example 2: Database Connection Factory

```java
// Product interface
public interface DatabaseConnection {
    void connect();
    void executeQuery(String query);
    void disconnect();
    String getConnectionInfo();
}

// Concrete products
public class MySQLConnection implements DatabaseConnection {
    private String host;
    private int port;

    public MySQLConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void connect() {
        System.out.println("Connecting to MySQL database at " + host + ":" + port);
    }

    @Override
    public void executeQuery(String query) {
        System.out.println("Executing MySQL query: " + query);
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnecting from MySQL database");
    }

    @Override
    public String getConnectionInfo() {
        return "MySQL Connection - " + host + ":" + port;
    }
}

public class PostgreSQLConnection implements DatabaseConnection {
    private String host;
    private int port;

    public PostgreSQLConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void connect() {
        System.out.println("Connecting to PostgreSQL database at " + host + ":" + port);
    }

    @Override
    public void executeQuery(String query) {
        System.out.println("Executing PostgreSQL query: " + query);
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnecting from PostgreSQL database");
    }

    @Override
    public String getConnectionInfo() {
        return "PostgreSQL Connection - " + host + ":" + port;
    }
}

// Abstract creator with configuration
public abstract class DatabaseConnectionFactory {
    protected String host;
    protected int port;
    protected String database;

    public DatabaseConnectionFactory(String host, int port, String database) {
        this.host = host;
        this.port = port;
        this.database = database;
    }

    public abstract DatabaseConnection createConnection();

    public void performDatabaseOperation(String query) {
        DatabaseConnection connection = createConnection();
        try {
            connection.connect();
            System.out.println("Using database: " + database);
            connection.executeQuery(query);
        } finally {
            connection.disconnect();
        }
    }
}

// Concrete creators
public class MySQLConnectionFactory extends DatabaseConnectionFactory {
    public MySQLConnectionFactory(String host, int port, String database) {
        super(host, port, database);
    }

    @Override
    public DatabaseConnection createConnection() {
        return new MySQLConnection(host, port);
    }
}

public class PostgreSQLConnectionFactory extends DatabaseConnectionFactory {
    public PostgreSQLConnectionFactory(String host, int port, String database) {
        super(host, port, database);
    }

    @Override
    public DatabaseConnection createConnection() {
        return new PostgreSQLConnection(host, port);
    }
}
```

## Real-World Scenarios

### 1. GUI Framework Components

```java
// Cross-platform GUI components
public interface Button {
    void render();
    void onClick();
}

public class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Windows-style button");
    }

    @Override
    public void onClick() {
        System.out.println("Windows button clicked");
    }
}

public class MacButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Mac-style button");
    }

    @Override
    public void onClick() {
        System.out.println("Mac button clicked");
    }
}

public abstract class GUIFactory {
    public abstract Button createButton();

    public void createDialog() {
        Button button = createButton();
        button.render();
        // Additional dialog setup...
    }
}
```

### 2. Payment Processing System

```java
public interface PaymentProcessor {
    boolean processPayment(double amount);
    String getPaymentMethod();
    void sendConfirmation();
}

public class CreditCardProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing credit card payment: $" + amount);
        return true; // Simulate successful payment
    }

    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }

    @Override
    public void sendConfirmation() {
        System.out.println("Credit card payment confirmation sent via email");
    }
}

public class PayPalProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing PayPal payment: $" + amount);
        return true; // Simulate successful payment
    }

    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }

    @Override
    public void sendConfirmation() {
        System.out.println("PayPal payment confirmation sent via app notification");
    }
}

public abstract class PaymentFactory {
    public abstract PaymentProcessor createPaymentProcessor();

    public void processOrder(double amount) {
        PaymentProcessor processor = createPaymentProcessor();

        System.out.println("Processing order with " + processor.getPaymentMethod());

        if (processor.processPayment(amount)) {
            processor.sendConfirmation();
            System.out.println("Order completed successfully");
        } else {
            System.out.println("Payment failed");
        }
    }
}
```

## Advantages and Disadvantages

### Advantages

1. **Loose Coupling**: Eliminates dependency between client code and concrete product classes
2. **Extensibility**: Easy to add new product types without modifying existing code
3. **Single Responsibility**: Separates product creation from product usage
4. **Open/Closed Principle**: Open for extension, closed for modification

### Disadvantages

1. **Complexity**: Increases the number of classes in the system
2. **Indirection**: Can make code harder to follow due to additional abstraction layers
3. **Overkill**: May be unnecessary for simple scenarios with few product types

## Best Practices

### 1. Use Meaningful Names

```java
// Good
public abstract class VehicleFactory {
    public abstract Vehicle createVehicle();
}

// Avoid
public abstract class Factory {
    public abstract Object create();
}
```

### 2. Provide Default Implementation When Appropriate

```java
public abstract class DocumentFactory {
    // Default implementation
    public Document createDocument(String fileName) {
        return new PlainTextDocument(fileName);
    }

    // Subclasses can override when needed
}
```

### 3. Use Enums for Type Safety

```java
public enum VehicleType {
    CAR, MOTORCYCLE, TRUCK
}

public class VehicleFactoryProvider {
    public static VehicleFactory getFactory(VehicleType type) {
        switch (type) {
            case CAR: return new CarFactory();
            case MOTORCYCLE: return new MotorcycleFactory();
            case TRUCK: return new TruckFactory();
            default: throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}
```

### 4. Consider Using Generics

```java
public abstract class GenericFactory<T> {
    public abstract T createProduct();

    public List<T> createMultiple(int count) {
        List<T> products = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            products.add(createProduct());
        }
        return products;
    }
}
```

## Common Pitfalls

### 1. Overusing the Pattern

Don't use Factory Method for simple object creation that doesn't require abstraction.

### 2. Creating God Factories

Avoid factories that create too many different types of objects.

### 3. Forgetting to Make Creator Abstract

```java
// Wrong - concrete class with factory method
public class VehicleFactory {
    public Vehicle createVehicle() {
        return new Car(); // Always returns the same type
    }
}

// Correct - abstract class
public abstract class VehicleFactory {
    public abstract Vehicle createVehicle();
}
```

### 4. Not Following Single Responsibility

Keep factory methods focused on creation, not business logic.
