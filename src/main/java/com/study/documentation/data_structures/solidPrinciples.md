# SOLID Principles - Complete Java Documentation

## Table of Contents

1. [Introduction to SOLID](#introduction-to-solid)
2. [Single Responsibility Principle (SRP)](#single-responsibility-principle-srp)
3. [Open/Closed Principle (OCP)](#openclosed-principle-ocp)
4. [Liskov Substitution Principle (LSP)](#liskov-substitution-principle-lsp)
5. [Interface Segregation Principle (ISP)](#interface-segregation-principle-isp)
6. [Dependency Inversion Principle (DIP)](#dependency-inversion-principle-dip)
7. [SOLID in Practice](#solid-in-practice)
8. [Common Violations and Solutions](#common-violations-and-solutions)

---

## Introduction to SOLID

SOLID is an acronym for five design principles that make software designs more understandable, flexible, and maintainable. These principles were introduced by Robert C. Martin (Uncle Bob) and form the foundation of clean code and good object-oriented design.

### Why SOLID Matters

-   **Maintainability**: Code is easier to modify and extend
-   **Testability**: Components can be tested in isolation
-   **Flexibility**: System can adapt to changing requirements
-   **Reusability**: Components can be reused in different contexts
-   **Reduced Coupling**: Components are less dependent on each other

---

## Single Responsibility Principle (SRP)

> _"A class should have only one reason to change."_

### Definition

A class should have only one job or responsibility. If a class has multiple responsibilities, changes to one responsibility may affect the others, making the code fragile and hard to maintain.

### ❌ Violation Example

```java
// BAD: UserManager handles multiple responsibilities
public class UserManager {
    public void createUser(String name, String email) {
        // User creation logic
        User user = new User(name, email);

        // Database persistence (different responsibility)
        Connection conn = DriverManager.getConnection("...");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users...");
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.executeUpdate();

        // Email notification (different responsibility)
        EmailService emailService = new EmailService();
        emailService.sendWelcomeEmail(email);

        // Logging (different responsibility)
        System.out.println("User created: " + name);
    }
}
```

### ✅ Correct Implementation

```java
// GOOD: Each class has a single responsibility

public class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and setters
}

public class UserRepository {
    public void save(User user) {
        Connection conn = DriverManager.getConnection("...");
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users...");
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        stmt.executeUpdate();
    }
}

public class EmailService {
    public void sendWelcomeEmail(String email) {
        // Email sending logic
    }
}

public class Logger {
    public void log(String message) {
        System.out.println(message);
    }
}

public class UserService {
    private UserRepository userRepository;
    private EmailService emailService;
    private Logger logger;

    public UserService(UserRepository userRepository,
                      EmailService emailService,
                      Logger logger) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.logger = logger;
    }

    public void createUser(String name, String email) {
        User user = new User(name, email);
        userRepository.save(user);
        emailService.sendWelcomeEmail(email);
        logger.log("User created: " + name);
    }
}
```

### Benefits of SRP

-   **Easier testing**: Each class can be tested independently
-   **Reduced coupling**: Changes in one area don't affect others
-   **Better organization**: Clear separation of concerns
-   **Improved reusability**: Single-purpose classes are more reusable

---

## Open/Closed Principle (OCP)

> _"Software entities should be open for extension but closed for modification."_

### Definition

You should be able to extend a class's behavior without modifying its existing code. This is typically achieved through inheritance, composition, and polymorphism.

### ❌ Violation Example

```java
// BAD: Adding new shapes requires modifying existing code
public class AreaCalculator {
    public double calculateArea(Object shape) {
        if (shape instanceof Rectangle) {
            Rectangle rect = (Rectangle) shape;
            return rect.getWidth() * rect.getHeight();
        } else if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            return Math.PI * circle.getRadius() * circle.getRadius();
        }
        // Adding Triangle would require modifying this method
        return 0;
    }
}
```

### ✅ Correct Implementation

```java
// GOOD: Open for extension, closed for modification

public abstract class Shape {
    public abstract double calculateArea();
}

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// New shapes can be added without modifying existing code
public class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}

public class AreaCalculator {
    public double calculateTotalArea(List<Shape> shapes) {
        return shapes.stream()
                    .mapToDouble(Shape::calculateArea)
                    .sum();
    }
}
```

### Alternative Approach with Strategy Pattern

```java
public interface AreaCalculationStrategy {
    double calculate();
}

public class RectangleAreaStrategy implements AreaCalculationStrategy {
    private double width, height;

    public RectangleAreaStrategy(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculate() {
        return width * height;
    }
}

public class Shape {
    private AreaCalculationStrategy strategy;

    public Shape(AreaCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public double getArea() {
        return strategy.calculate();
    }
}
```

---

## Liskov Substitution Principle (LSP)

> _"Objects of a superclass should be replaceable with objects of a subclass without breaking the application."_

### Definition

Derived classes must be substitutable for their base classes. This means subclasses should strengthen, not weaken, the contract defined by the base class.

### ❌ Violation Example

```java
// BAD: Square violates LSP when inheriting from Rectangle
public class Rectangle {
    protected double width;
    protected double height;

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getArea() {
        return width * height;
    }
}

public class Square extends Rectangle {
    @Override
    public void setWidth(double width) {
        this.width = width;
        this.height = width; // Violates LSP - unexpected side effect
    }

    @Override
    public void setHeight(double height) {
        this.width = height;  // Violates LSP - unexpected side effect
        this.height = height;
    }
}

// This code breaks when Square is used instead of Rectangle
public void testRectangle(Rectangle rect) {
    rect.setWidth(5);
    rect.setHeight(4);
    // Expected area: 20, but if rect is Square, area will be 16
    assert rect.getArea() == 20; // Fails for Square!
}
```

### ✅ Correct Implementation

```java
// GOOD: Proper abstraction that doesn't violate LSP

public abstract class Shape {
    public abstract double getArea();
    public abstract double getPerimeter();
}

public class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
}

public class Square extends Shape {
    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getPerimeter() {
        return 4 * side;
    }

    public double getSide() { return side; }
}

// Now both can be used interchangeably
public double calculateTotalArea(List<Shape> shapes) {
    return shapes.stream()
                .mapToDouble(Shape::getArea)
                .sum();
}
```

### LSP Guidelines

-   **Preconditions**: Cannot be strengthened in subclasses
-   **Postconditions**: Cannot be weakened in subclasses
-   **Invariants**: Must be preserved in subclasses
-   **History constraint**: Subclasses shouldn't introduce unexpected state changes

---

## Interface Segregation Principle (ISP)

> _"Clients should not be forced to depend upon interfaces they do not use."_

### Definition

No client should be forced to implement methods it doesn't use. Large interfaces should be split into smaller, more specific interfaces.

### ❌ Violation Example

```java
// BAD: Fat interface forces unnecessary implementations
public interface Worker {
    void work();
    void eat();
    void sleep();
    void attendMeeting();
    void takeBreak();
}

public class Robot implements Worker {
    @Override
    public void work() {
        // Robot can work
    }

    @Override
    public void eat() {
        // Robots don't eat - forced to implement unused method
        throw new UnsupportedOperationException("Robots don't eat");
    }

    @Override
    public void sleep() {
        // Robots don't sleep - forced to implement unused method
        throw new UnsupportedOperationException("Robots don't sleep");
    }

    @Override
    public void attendMeeting() {
        // Maybe robots can attend meetings
    }

    @Override
    public void takeBreak() {
        // Robots don't take breaks
        throw new UnsupportedOperationException("Robots don't take breaks");
    }
}
```

### ✅ Correct Implementation

```java
// GOOD: Segregated interfaces

public interface Workable {
    void work();
}

public interface Eatable {
    void eat();
}

public interface Sleepable {
    void sleep();
}

public interface Attendable {
    void attendMeeting();
}

public interface Breakable {
    void takeBreak();
}

// Human implements all interfaces
public class Human implements Workable, Eatable, Sleepable, Attendable, Breakable {
    @Override
    public void work() {
        System.out.println("Human working");
    }

    @Override
    public void eat() {
        System.out.println("Human eating");
    }

    @Override
    public void sleep() {
        System.out.println("Human sleeping");
    }

    @Override
    public void attendMeeting() {
        System.out.println("Human attending meeting");
    }

    @Override
    public void takeBreak() {
        System.out.println("Human taking break");
    }
}

// Robot only implements relevant interfaces
public class Robot implements Workable, Attendable {
    @Override
    public void work() {
        System.out.println("Robot working");
    }

    @Override
    public void attendMeeting() {
        System.out.println("Robot attending meeting");
    }
}

// Usage with specific interface types
public class WorkManager {
    public void manageWork(List<Workable> workers) {
        workers.forEach(Workable::work);
    }

    public void manageMeeting(List<Attendable> attendees) {
        attendees.forEach(Attendable::attendMeeting);
    }
}
```

### Real-World Example: File Operations

```java
// BAD: Monolithic interface
public interface FileManager {
    void read();
    void write();
    void compress();
    void encrypt();
    void backup();
}

// GOOD: Segregated interfaces
public interface Readable {
    void read();
}

public interface Writable {
    void write();
}

public interface Compressible {
    void compress();
}

public interface Encryptable {
    void encrypt();
}

public interface Backupable {
    void backup();
}

// Specific implementations
public class ReadOnlyFile implements Readable {
    @Override
    public void read() {
        // Implementation
    }
}

public class SecureFile implements Readable, Writable, Encryptable {
    @Override
    public void read() { /* Implementation */ }

    @Override
    public void write() { /* Implementation */ }

    @Override
    public void encrypt() { /* Implementation */ }
}
```

---

## Dependency Inversion Principle (DIP)

> _"High-level modules should not depend on low-level modules. Both should depend on abstractions."_

### Definition

-   High-level modules should not depend on low-level modules
-   Both should depend on abstractions (interfaces)
-   Abstractions should not depend on details
-   Details should depend on abstractions

### ❌ Violation Example

```java
// BAD: High-level class depends on low-level implementation
public class EmailService {
    public void sendEmail(String message) {
        // Direct email sending logic
    }
}

public class SMSService {
    public void sendSMS(String message) {
        // Direct SMS sending logic
    }
}

public class NotificationManager {
    private EmailService emailService; // Direct dependency on concrete class
    private SMSService smsService;     // Direct dependency on concrete class

    public NotificationManager() {
        this.emailService = new EmailService(); // Tight coupling
        this.smsService = new SMSService();     // Tight coupling
    }

    public void sendNotification(String message, String type) {
        if ("email".equals(type)) {
            emailService.sendEmail(message);
        } else if ("sms".equals(type)) {
            smsService.sendSMS(message);
        }
    }
}
```

### ✅ Correct Implementation

```java
// GOOD: Depend on abstractions

public interface NotificationService {
    void send(String message, String recipient);
}

public class EmailService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Sending email to " + recipient + ": " + message);
    }
}

public class SMSService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }
}

public class PushNotificationService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Sending push notification to " + recipient + ": " + message);
    }
}

public class NotificationManager {
    private List<NotificationService> notificationServices;

    // Dependency injection through constructor
    public NotificationManager(List<NotificationService> notificationServices) {
        this.notificationServices = notificationServices;
    }

    public void sendNotification(String message, String recipient) {
        notificationServices.forEach(service ->
            service.send(message, recipient));
    }
}

// Usage with dependency injection
public class Application {
    public static void main(String[] args) {
        List<NotificationService> services = Arrays.asList(
            new EmailService(),
            new SMSService(),
            new PushNotificationService()
        );

        NotificationManager manager = new NotificationManager(services);
        manager.sendNotification("Hello World", "user@example.com");
    }
}
```

### Dependency Injection Patterns

```java
// Constructor Injection (Preferred)
public class OrderService {
    private final PaymentProcessor paymentProcessor;
    private final InventoryService inventoryService;

    public OrderService(PaymentProcessor paymentProcessor,
                       InventoryService inventoryService) {
        this.paymentProcessor = paymentProcessor;
        this.inventoryService = inventoryService;
    }
}

// Setter Injection
public class OrderService {
    private PaymentProcessor paymentProcessor;
    private InventoryService inventoryService;

    public void setPaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
}

// Interface Injection
public interface PaymentProcessorAware {
    void setPaymentProcessor(PaymentProcessor paymentProcessor);
}

public class OrderService implements PaymentProcessorAware {
    private PaymentProcessor paymentProcessor;

    @Override
    public void setPaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
}
```

---

## SOLID in Practice

### Real-World Example: E-commerce Order Processing

```java
// Abstractions (High-level modules depend on these)
public interface PaymentProcessor {
    PaymentResult process(PaymentRequest request);
}

public interface InventoryService {
    boolean isAvailable(String productId, int quantity);
    void reserve(String productId, int quantity);
}

public interface NotificationService {
    void notify(String recipient, String message);
}

public interface OrderRepository {
    void save(Order order);
    Order findById(String orderId);
}

// Low-level implementations
public class CreditCardProcessor implements PaymentProcessor {
    @Override
    public PaymentResult process(PaymentRequest request) {
        // Credit card processing logic
        return new PaymentResult(true, "Payment successful");
    }
}

public class DatabaseInventoryService implements InventoryService {
    @Override
    public boolean isAvailable(String productId, int quantity) {
        // Database query logic
        return true;
    }

    @Override
    public void reserve(String productId, int quantity) {
        // Database update logic
    }
}

// High-level module
public class OrderService {
    private final PaymentProcessor paymentProcessor;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;
    private final OrderRepository orderRepository;

    // Constructor injection - depends on abstractions
    public OrderService(PaymentProcessor paymentProcessor,
                       InventoryService inventoryService,
                       NotificationService notificationService,
                       OrderRepository orderRepository) {
        this.paymentProcessor = paymentProcessor;
        this.inventoryService = inventoryService;
        this.notificationService = notificationService;
        this.orderRepository = orderRepository;
    }

    public OrderResult processOrder(OrderRequest request) {
        // Single responsibility: orchestrating order processing

        // Check inventory
        if (!inventoryService.isAvailable(request.getProductId(), request.getQuantity())) {
            return new OrderResult(false, "Product not available");
        }

        // Process payment
        PaymentResult paymentResult = paymentProcessor.process(request.getPayment());
        if (!paymentResult.isSuccessful()) {
            return new OrderResult(false, "Payment failed");
        }

        // Reserve inventory
        inventoryService.reserve(request.getProductId(), request.getQuantity());

        // Save order
        Order order = new Order(request);
        orderRepository.save(order);

        // Send notification
        notificationService.notify(request.getCustomerEmail(),
                                 "Order confirmed: " + order.getId());

        return new OrderResult(true, "Order processed successfully");
    }
}
```

### Configuration and Assembly

```java
// Application configuration (composition root)
public class ApplicationConfig {
    public OrderService createOrderService() {
        PaymentProcessor paymentProcessor = new CreditCardProcessor();
        InventoryService inventoryService = new DatabaseInventoryService();
        NotificationService notificationService = new EmailNotificationService();
        OrderRepository orderRepository = new DatabaseOrderRepository();

        return new OrderService(
            paymentProcessor,
            inventoryService,
            notificationService,
            orderRepository
        );
    }
}
```

---

## Common Violations and Solutions

### 1. God Class (SRP Violation)

```java
// BAD: God class doing everything
public class UserController {
    public void handleUserRegistration(String name, String email, String password) {
        // Validation
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name required");
        }

        // Password hashing
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Database operations
        Connection conn = getConnection();
        // SQL operations...

        // Email sending
        sendWelcomeEmail(email);

        // Logging
        logger.info("User registered: " + name);

        // Audit trail
        auditService.log("USER_REGISTERED", name);
    }
}

// GOOD: Separated responsibilities
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<User> registerUser(CreateUserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(user);
    }
}

public class UserService {
    private final UserValidator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuditService auditService;

    // Constructor and implementation...
}
```

### 2. Switch Statement Proliferation (OCP Violation)

```java
// BAD: Switch statements that need modification for new types
public class DiscountCalculator {
    public double calculateDiscount(Customer customer, double amount) {
        switch (customer.getType()) {
            case "REGULAR":
                return amount * 0.05;
            case "PREMIUM":
                return amount * 0.10;
            case "VIP":
                return amount * 0.15;
            // Adding new customer type requires modifying this method
            default:
                return 0;
        }
    }
}

// GOOD: Strategy pattern for extensibility
public interface DiscountStrategy {
    double calculateDiscount(double amount);
}

public class RegularCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.05;
    }
}

public class PremiumCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.10;
    }
}

public class Customer {
    private DiscountStrategy discountStrategy;

    public Customer(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculateDiscount(double amount) {
        return discountStrategy.calculateDiscount(amount);
    }
}
```

### 3. Tight Coupling (DIP Violation)

```java
// BAD: Tight coupling to concrete implementations
public class OrderProcessor {
    private MySQLDatabase database = new MySQLDatabase(); // Tight coupling
    private EmailSender emailSender = new EmailSender();   // Tight coupling

    public void processOrder(Order order) {
        database.save(order);
        emailSender.sendConfirmation(order.getCustomerEmail());
    }
}

// GOOD: Loose coupling through abstractions
public class OrderProcessor {
    private final Database database;
    private final NotificationSender notificationSender;

    public OrderProcessor(Database database, NotificationSender notificationSender) {
        this.database = database;
        this.notificationSender = notificationSender;
    }

    public void processOrder(Order order) {
        database.save(order);
        notificationSender.send(order.getCustomerEmail(), "Order confirmed");
    }
}
```

## Benefits of Following SOLID

### Code Quality Improvements

-   **Maintainability**: Changes are localized and predictable
-   **Testability**: Each component can be tested in isolation
-   **Flexibility**: Easy to adapt to changing requirements
-   **Reusability**: Components can be reused in different contexts
-   **Readability**: Code structure is clear and logical

### Development Benefits

-   **Faster development**: Well-structured code is easier to work with
-   **Reduced bugs**: Smaller, focused classes have fewer edge cases
-   **Better collaboration**: Team members can work on different components independently
-   **Easier debugging**: Issues are isolated to specific responsibilities

### Long-term Benefits

-   **Lower maintenance costs**: Changes don't ripple through the system
-   **Easier feature additions**: New functionality can be added without breaking existing code
-   **Better system evolution**: Architecture can evolve without major rewrites
-   **Improved team productivity**: Developers spend less time fighting the codebase

## Practical Application Tips

### 1. Start with SRP

-   Identify classes that are doing too much
-   Look for classes with multiple reasons to change
-   Extract responsibilities into separate classes

### 2. Apply OCP Gradually

-   Identify areas where you frequently add new functionality
-   Replace conditional logic with polymorphism
-   Use design patterns like Strategy, Template Method, or State

### 3. Check LSP with Contracts

-   Define clear contracts for your base classes
-   Ensure subclasses strengthen, not weaken, these contracts
-   Use automated tests to verify substitutability

### 4. Review Interfaces Regularly

-   Look for interfaces with many methods
-   Check if all implementers use all methods
-   Split large interfaces into smaller, cohesive ones

### 5. Invert Dependencies Systematically

-   Identify dependencies on concrete classes
-   Create abstractions for these dependencies
-   Use dependency injection frameworks (Spring, Guice) for complex applications

## Assessment Questions

Test your understanding with these questions:

1. **SRP**: If a class changes for multiple reasons, what principle is violated?
2. **OCP**: How can you add new functionality without modifying existing code?
3. **LSP**: What happens when a subclass weakens the contract of its parent?
4. **ISP**: Why is it bad to force clients to implement unused methods?
5. **DIP**: What's the difference between dependency injection and dependency inversion?

## Next Steps

After mastering SOLID principles:

1. Study **Design Patterns** (they often implement SOLID principles)
2. Learn **GRASP principles** for additional design guidance
3. Explore **Clean Architecture** and **Hexagonal Architecture**
4. Practice **Test-Driven Development** (TDD) which naturally leads to SOLID designs
5. Study **Domain-Driven Design** for complex business logic

Remember: SOLID principles are guidelines, not rigid rules. Use judgment to apply them appropriately based on your specific context and requirements.
