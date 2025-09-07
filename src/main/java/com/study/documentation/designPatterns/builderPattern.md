# Builder Pattern in Java - Complete Documentation

## Table of Contents

1. [Introduction](#introduction)
2. [Problem Statement](#problem-statement)
3. [Builder Pattern Overview](#builder-pattern-overview)
4. [Implementation Approaches](#implementation-approaches)
5. [Classic Builder Pattern](#classic-builder-pattern)
6. [Fluent Builder Pattern](#fluent-builder-pattern)
7. [Telescoping Constructor Anti-Pattern](#telescoping-constructor-anti-pattern)
8. [Builder with Director](#builder-with-director)
9. [Inner Static Builder Class](#inner-static-builder-class)
10. [Advanced Techniques](#advanced-techniques)
11. [Real-World Examples](#real-world-examples)
12. [Best Practices](#best-practices)
13. [Common Pitfalls](#common-pitfalls)

## Introduction

The Builder Pattern is a creational design pattern that provides a flexible solution for constructing complex objects step by step. It allows you to create different types and representations of an object using the same construction process. This pattern is particularly useful when dealing with objects that have many optional parameters or when the construction process is complex.

## Problem Statement

Consider scenarios where you need to create objects with:

-   Multiple optional parameters
-   Complex initialization logic
-   Immutable objects with many fields
-   Objects that require validation during construction
-   Different representations of the same object

Traditional approaches like telescoping constructors or JavaBeans setters have significant drawbacks in these situations.

## Builder Pattern Overview

The Builder Pattern separates the construction of a complex object from its representation, allowing the same construction process to create various representations. The pattern involves:

-   **Product**: The complex object being built
-   **Builder**: Abstract interface for creating parts of the Product
-   **ConcreteBuilder**: Implements the Builder interface and constructs specific representations
-   **Director**: Constructs objects using the Builder interface (optional)

## Implementation Approaches

### 1. Classic Builder Pattern

```java
// Product class
public class House {
    private String foundation;
    private String structure;
    private String roof;
    private String interior;
    private boolean hasGarage;
    private boolean hasGarden;
    private boolean hasPool;

    // Private constructor to enforce builder usage
    private House(HouseBuilder builder) {
        this.foundation = builder.foundation;
        this.structure = builder.structure;
        this.roof = builder.roof;
        this.interior = builder.interior;
        this.hasGarage = builder.hasGarage;
        this.hasGarden = builder.hasGarden;
        this.hasPool = builder.hasPool;
    }

    // Getters
    public String getFoundation() { return foundation; }
    public String getStructure() { return structure; }
    public String getRoof() { return roof; }
    public String getInterior() { return interior; }
    public boolean hasGarage() { return hasGarage; }
    public boolean hasGarden() { return hasGarden; }
    public boolean hasPool() { return hasPool; }

    @Override
    public String toString() {
        return "House{" +
                "foundation='" + foundation + '\'' +
                ", structure='" + structure + '\'' +
                ", roof='" + roof + '\'' +
                ", interior='" + interior + '\'' +
                ", hasGarage=" + hasGarage +
                ", hasGarden=" + hasGarden +
                ", hasPool=" + hasPool +
                '}';
    }

    // Builder interface
    public interface HouseBuilder {
        HouseBuilder buildFoundation(String foundation);
        HouseBuilder buildStructure(String structure);
        HouseBuilder buildRoof(String roof);
        HouseBuilder buildInterior(String interior);
        HouseBuilder addGarage();
        HouseBuilder addGarden();
        HouseBuilder addPool();
        House build();
    }

    // Concrete Builder
    public static class ConcreteHouseBuilder implements HouseBuilder {
        private String foundation;
        private String structure;
        private String roof;
        private String interior;
        private boolean hasGarage = false;
        private boolean hasGarden = false;
        private boolean hasPool = false;

        @Override
        public HouseBuilder buildFoundation(String foundation) {
            this.foundation = foundation;
            return this;
        }

        @Override
        public HouseBuilder buildStructure(String structure) {
            this.structure = structure;
            return this;
        }

        @Override
        public HouseBuilder buildRoof(String roof) {
            this.roof = roof;
            return this;
        }

        @Override
        public HouseBuilder buildInterior(String interior) {
            this.interior = interior;
            return this;
        }

        @Override
        public HouseBuilder addGarage() {
            this.hasGarage = true;
            return this;
        }

        @Override
        public HouseBuilder addGarden() {
            this.hasGarden = true;
            return this;
        }

        @Override
        public HouseBuilder addPool() {
            this.hasPool = true;
            return this;
        }

        @Override
        public House build() {
            // Validation can be added here
            if (foundation == null || structure == null || roof == null) {
                throw new IllegalStateException("Foundation, structure, and roof are required");
            }
            return new House(this);
        }
    }
}
```

**Usage:**

```java
public class BuilderPatternDemo {
    public static void main(String[] args) {
        House house = new House.ConcreteHouseBuilder()
                .buildFoundation("Concrete Foundation")
                .buildStructure("Steel Frame")
                .buildRoof("Tile Roof")
                .buildInterior("Modern Interior")
                .addGarage()
                .addGarden()
                .build();

        System.out.println(house);
    }
}
```

## Fluent Builder Pattern

The Fluent Builder Pattern emphasizes method chaining for a more readable API:

```java
public class Computer {
    private final String cpu;
    private final String ram;
    private final String storage;
    private final String gpu;
    private final boolean hasWifi;
    private final boolean hasBluetooth;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.hasWifi = builder.hasWifi;
        this.hasBluetooth = builder.hasBluetooth;
    }

    // Getters
    public String getCpu() { return cpu; }
    public String getRam() { return ram; }
    public String getStorage() { return storage; }
    public String getGpu() { return gpu; }
    public boolean hasWifi() { return hasWifi; }
    public boolean hasBluetooth() { return hasBluetooth; }

    public static class Builder {
        // Required parameters
        private final String cpu;
        private final String ram;

        // Optional parameters - initialized to default values
        private String storage = "256GB SSD";
        private String gpu = "Integrated";
        private boolean hasWifi = true;
        private boolean hasBluetooth = false;

        public Builder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }

        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder wifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }

        public Builder bluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

    @Override
    public String toString() {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", storage='" + storage + '\'' +
                ", gpu='" + gpu + '\'' +
                ", hasWifi=" + hasWifi +
                ", hasBluetooth=" + hasBluetooth +
                '}';
    }
}
```

**Usage:**

```java
Computer gamingPC = new Computer.Builder("Intel i9", "32GB")
        .storage("1TB NVMe SSD")
        .gpu("RTX 4080")
        .wifi(true)
        .bluetooth(true)
        .build();

Computer officePC = new Computer.Builder("Intel i5", "16GB")
        .storage("512GB SSD")
        .build();
```

## Telescoping Constructor Anti-Pattern

Before using the Builder pattern, developers often resort to telescoping constructors, which become unwieldy:

```java
// Anti-pattern: Telescoping Constructor
public class Pizza {
    private int size;
    private boolean cheese;
    private boolean pepperoni;
    private boolean bacon;
    private boolean mushrooms;
    private boolean olives;

    // This becomes unmanageable quickly
    public Pizza(int size) {
        this(size, false);
    }

    public Pizza(int size, boolean cheese) {
        this(size, cheese, false);
    }

    public Pizza(int size, boolean cheese, boolean pepperoni) {
        this(size, cheese, pepperoni, false);
    }

    public Pizza(int size, boolean cheese, boolean pepperoni, boolean bacon) {
        this(size, cheese, pepperoni, bacon, false);
    }

    // ... and so on
}
```

The Builder pattern eliminates this problem by providing a clean, readable alternative.

## Builder with Director

The Director class encapsulates the construction logic:

```java
public class CarDirector {
    private CarBuilder builder;

    public CarDirector(CarBuilder builder) {
        this.builder = builder;
    }

    public Car constructSportsCar() {
        return builder
                .setEngine("V8 Engine")
                .setSeats(2)
                .setTransmission("Manual")
                .setGPS(true)
                .setSunroof(true)
                .build();
    }

    public Car constructFamilyCar() {
        return builder
                .setEngine("V6 Engine")
                .setSeats(5)
                .setTransmission("Automatic")
                .setGPS(true)
                .setSunroof(false)
                .build();
    }

    public Car constructEconomyCar() {
        return builder
                .setEngine("4-Cylinder Engine")
                .setSeats(4)
                .setTransmission("Manual")
                .setGPS(false)
                .setSunroof(false)
                .build();
    }
}
```

## Inner Static Builder Class

This is the most common implementation in Java:

```java
public class Person {
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String phone;
    private final String address;
    private final String email;

    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
        this.email = builder.email;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }

    public static class Builder {
        // Required parameters
        private final String firstName;
        private final String lastName;

        // Optional parameters
        private int age = 0;
        private String phone = "";
        private String address = "";
        private String email = "";

        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder age(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            this.age = age;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder email(String email) {
            if (email != null && !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            this.email = email;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```

## Advanced Techniques

### Generic Builder

Create reusable builders with generics:

```java
public abstract class GenericBuilder<T> {
    protected T object;

    public abstract T build();

    protected void validate() {
        // Common validation logic
    }
}

public class DatabaseConnection {
    private String url;
    private String username;
    private String password;
    private int timeout;
    private boolean ssl;

    private DatabaseConnection() {}

    public static class Builder extends GenericBuilder<DatabaseConnection> {
        public Builder() {
            this.object = new DatabaseConnection();
        }

        public Builder url(String url) {
            object.url = url;
            return this;
        }

        public Builder username(String username) {
            object.username = username;
            return this;
        }

        public Builder password(String password) {
            object.password = password;
            return this;
        }

        public Builder timeout(int timeout) {
            object.timeout = timeout;
            return this;
        }

        public Builder ssl(boolean ssl) {
            object.ssl = ssl;
            return this;
        }

        @Override
        public DatabaseConnection build() {
            validate();
            if (object.url == null || object.username == null) {
                throw new IllegalStateException("URL and username are required");
            }
            return object;
        }
    }
}
```

### Step Builder Pattern

Enforce the order of method calls:

```java
public class StepBuilderPatternExample {

    private final String name;
    private final String email;
    private final String password;

    private StepBuilderPatternExample(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static NameStep builder() {
        return new Steps();
    }

    public interface NameStep {
        EmailStep name(String name);
    }

    public interface EmailStep {
        PasswordStep email(String email);
    }

    public interface PasswordStep {
        BuildStep password(String password);
    }

    public interface BuildStep {
        StepBuilderPatternExample build();
    }

    private static class Steps implements NameStep, EmailStep, PasswordStep, BuildStep {
        private String name;
        private String email;
        private String password;

        @Override
        public EmailStep name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public PasswordStep email(String email) {
            this.email = email;
            return this;
        }

        @Override
        public BuildStep password(String password) {
            this.password = password;
            return this;
        }

        @Override
        public StepBuilderPatternExample build() {
            return new StepBuilderPatternExample(name, email, password);
        }
    }
}
```

**Usage:**

```java
StepBuilderPatternExample user = StepBuilderPatternExample.builder()
        .name("John Doe")
        .email("john@example.com")
        .password("secretPassword")
        .build();
```

## Real-World Examples

### StringBuilder (Java Standard Library)

```java
StringBuilder sb = new StringBuilder()
        .append("Hello")
        .append(" ")
        .append("World")
        .append("!");
String result = sb.toString();
```

### HTTP Client Builder

```java
public class HttpClient {
    private String baseUrl;
    private Map<String, String> headers;
    private int timeout;
    private boolean followRedirects;

    private HttpClient(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.headers = builder.headers;
        this.timeout = builder.timeout;
        this.followRedirects = builder.followRedirects;
    }

    public static class Builder {
        private String baseUrl;
        private Map<String, String> headers = new HashMap<>();
        private int timeout = 30000; // 30 seconds default
        private boolean followRedirects = true;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder followRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        public HttpClient build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL is required");
            }
            return new HttpClient(this);
        }
    }
}
```

### Configuration Builder

```java
public class DatabaseConfig {
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private final int maxConnections;
    private final int connectionTimeout;
    private final boolean useSSL;
    private final String charset;

    private DatabaseConfig(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.database = builder.database;
        this.username = builder.username;
        this.password = builder.password;
        this.maxConnections = builder.maxConnections;
        this.connectionTimeout = builder.connectionTimeout;
        this.useSSL = builder.useSSL;
        this.charset = builder.charset;
    }

    public static class Builder {
        // Required parameters
        private final String host;
        private final String database;

        // Optional parameters with defaults
        private int port = 5432;
        private String username = "postgres";
        private String password = "";
        private int maxConnections = 10;
        private int connectionTimeout = 30;
        private boolean useSSL = false;
        private String charset = "UTF-8";

        public Builder(String host, String database) {
            this.host = host;
            this.database = database;
        }

        public Builder port(int port) {
            if (port <= 0 || port > 65535) {
                throw new IllegalArgumentException("Port must be between 1 and 65535");
            }
            this.port = port;
            return this;
        }

        public Builder credentials(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }

        public Builder maxConnections(int maxConnections) {
            if (maxConnections <= 0) {
                throw new IllegalArgumentException("Max connections must be positive");
            }
            this.maxConnections = maxConnections;
            return this;
        }

        public Builder connectionTimeout(int timeoutSeconds) {
            if (timeoutSeconds < 0) {
                throw new IllegalArgumentException("Timeout cannot be negative");
            }
            this.connectionTimeout = timeoutSeconds;
            return this;
        }

        public Builder useSSL(boolean useSSL) {
            this.useSSL = useSSL;
            return this;
        }

        public Builder charset(String charset) {
            this.charset = charset;
            return this;
        }

        public DatabaseConfig build() {
            return new DatabaseConfig(this);
        }
    }

    // Getters
    public String getHost() { return host; }
    public int getPort() { return port; }
    public String getDatabase() { return database; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getMaxConnections() { return maxConnections; }
    public int getConnectionTimeout() { return connectionTimeout; }
    public boolean isUseSSL() { return useSSL; }
    public String getCharset() { return charset; }
}
```

## Best Practices

### 1. Make the Product Immutable

Always make the final product immutable by:

-   Making all fields `final`
-   Providing only getters, no setters
-   Using private constructors that take the builder as parameter

### 2. Validate in the Builder

Perform validation in the builder's `build()` method:

```java
public Person build() {
    if (firstName == null || firstName.trim().isEmpty()) {
        throw new IllegalArgumentException("First name is required");
    }
    if (lastName == null || lastName.trim().isEmpty()) {
        throw new IllegalArgumentException("Last name is required");
    }
    if (age < 0 || age > 150) {
        throw new IllegalArgumentException("Age must be between 0 and 150");
    }
    return new Person(this);
}
```

### 3. Use Method Chaining

Return `this` from builder methods to enable fluent interface:

```java
public Builder name(String name) {
    this.name = name;
    return this; // Enable method chaining
}
```

### 4. Consider Required vs Optional Parameters

Clearly distinguish between required and optional parameters:

```java
// Required parameters in constructor
public Builder(String requiredParam1, String requiredParam2) {
    this.requiredParam1 = requiredParam1;
    this.requiredParam2 = requiredParam2;
}

// Optional parameters as methods with defaults
private String optionalParam = "default";
public Builder optionalParam(String value) {
    this.optionalParam = value;
    return this;
}
```

### 5. Provide Meaningful Method Names

Use descriptive method names that indicate what they do:

```java
// Good
public Builder enableSSL() { this.ssl = true; return this; }
public Builder withTimeout(int seconds) { this.timeout = seconds; return this; }

// Less clear
public Builder ssl(boolean ssl) { this.ssl = ssl; return this; }
public Builder timeout(int timeout) { this.timeout = timeout; return this; }
```

### 6. Consider Builder Inheritance

For complex hierarchies, consider builder inheritance:

```java
public abstract class Animal {
    protected String name;
    protected int age;

    protected Animal(Builder<?> builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public abstract static class Builder<T extends Builder<T>> {
        protected String name;
        protected int age;

        protected abstract T self();
        public abstract Animal build();

        public T name(String name) {
            this.name = name;
            return self();
        }

        public T age(int age) {
            this.age = age;
            return self();
        }
    }
}

public class Dog extends Animal {
    private String breed;

    private Dog(Builder builder) {
        super(builder);
        this.breed = builder.breed;
    }

    public static class Builder extends Animal.Builder<Builder> {
        private String breed;

        @Override
        protected Builder self() {
            return this;
        }

        public Builder breed(String breed) {
            this.breed = breed;
            return this;
        }

        @Override
        public Dog build() {
            return new Dog(this);
        }
    }
}
```

## Common Pitfalls

### 1. Not Making Objects Immutable

Failing to make the built object immutable defeats one of the main purposes:

```java
// Bad - mutable object
public class BadExample {
    public String name; // public field

    public void setName(String name) { // setter provided
        this.name = name;
    }
}

// Good - immutable object
public class GoodExample {
    private final String name; // private final field

    private GoodExample(Builder builder) {
        this.name = builder.name;
    }

    public String getName() { return name; } // only getter
}
```

### 2. Forgetting to Validate

Always validate inputs in the builder:

```java
public Builder email(String email) {
    if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        throw new IllegalArgumentException("Invalid email format");
    }
    this.email = email;
    return this;
}
```

### 3. Not Handling Null Values Properly

Be explicit about null handling:

```java
public Builder tags(List<String> tags) {
    this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
    return this;
}
```

### 4. Making the Builder Mutable After build()

Ensure the builder cannot be reused unsafely:

```java
private boolean built = false;

public Product build() {
    if (built) {
        throw new IllegalStateException("Builder can only be used once");
    }
    built = true;
    return new Product(this);
}
```

## Advantages and Disadvantages

### Advantages

1. **Readability**: Code is more readable and self-documenting
2. **Flexibility**: Easy to add new optional parameters
3. **Immutability**: Promotes creation of immutable objects
4. **Validation**: Centralized validation logic
5. **Extensibility**: Easy to extend without breaking existing code

### Disadvantages

1. **Verbosity**: Requires more code than simple constructors
2. **Complexity**: Can be overkill for simple objects
3. **Memory**: Creates additional objects (the builder)
4. **Learning Curve**: Developers need to understand the pattern

## When to Use Builder Pattern

Use the Builder pattern when:

-   Object has many parameters (4+ parameters is a good rule of thumb)
-   Many parameters are optional
-   You want to ensure object immutability
-   Object creation involves complex logic
-   You want to provide a fluent, readable API
-   You need to create different representations of the same object

Avoid the Builder pattern when:

-   Object has few parameters (1-3)
-   All parameters are required
-   Object creation is simple
-   Performance is critical and object creation happens frequently

The Builder Pattern is a powerful tool in Java development that promotes clean, readable, and maintainable code. When used appropriately, it can significantly improve the design and usability of your APIs while ensuring object immutability and proper validation.
