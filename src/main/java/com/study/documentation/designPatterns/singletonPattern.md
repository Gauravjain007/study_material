# Singleton Pattern in Java - Complete Documentation

## Table of Contents

1. [Overview](#overview)
2. [Intent and Purpose](#intent-and-purpose)
3. [Key Characteristics](#key-characteristics)
4. [Implementation Approaches](#implementation-approaches)
5. [Solutions Summary](#solutions-summary)
6. [Advanced Implementation Example](#advanced-implementation-example)
7. [Pros and Cons](#pros-and-cons)
8. [Real-World Examples](#real-world-examples)
9. [Memory and Performance Considerations](#memory-and-performance-considerations)
10. [Alternative Patterns to Consider](#alternative-patterns-to-consider)

## Overview

The Singleton Pattern is a creational design pattern that ensures a class has only one instance throughout the application lifecycle and provides a global point of access to that instance. It's one of the most commonly used (and sometimes misused) design patterns in software development.

## Intent and Purpose

### Primary Goals

-   **Single Instance**: Guarantee that only one instance of a class exists
-   **Global Access**: Provide a well-defined access point to that instance
-   **Lazy Initialization**: Create the instance only when needed (optional)
-   **Resource Management**: Control access to shared resources like database connections, file systems, or configuration settings

### When to Use

-   Database connection pools
-   Configuration managers
-   Logging services
-   Cache implementations
-   Print spoolers
-   Thread pools
-   Device drivers

## Key Characteristics

1. **Private Constructor**: Prevents external instantiation
2. **Static Instance Variable**: Holds the single instance
3. **Static Access Method**: Provides global access point
4. **Instance Control**: Manages instance creation and lifecycle

## Implementation Approaches

### 1. Eager Initialization (Classic Singleton)

```java
public class EagerSingleton {
    // Instance created at class loading time
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    // Private constructor prevents instantiation
    private EagerSingleton() {
        // Prevent reflection-based instantiation
        if (INSTANCE != null) {
            throw new IllegalStateException("Singleton instance already exists!");
        }
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }

    // Business methods
    public void doSomething() {
        System.out.println("Performing singleton operation...");
    }
}
```

**Pros:**

-   Thread-safe by default
-   Simple implementation
-   No synchronization overhead

**Cons:**

-   Instance created even if never used
-   No lazy loading
-   Exception handling during construction is limited

### 2. Lazy Initialization (Not Thread-Safe)

```java
public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
        // Initialization code
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
```

**Pros:**

-   Instance created only when needed
-   Memory efficient

**Cons:**

-   Not thread-safe
-   Can create multiple instances in multithreaded environment

### 3. Thread-Safe Singleton (Synchronized Method)

```java
public class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {
        // Initialization code
    }

    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
}
```

**Pros:**

-   Thread-safe
-   Lazy initialization

**Cons:**

-   Performance overhead due to synchronization
-   All threads must wait even after instance is created

### 4. Double-Checked Locking

```java
public class DoubleCheckedLockingSingleton {
    // volatile ensures proper initialization in multithreaded environment
    private static volatile DoubleCheckedLockingSingleton instance;

    private DoubleCheckedLockingSingleton() {
        // Prevent reflection-based instantiation
        if (instance != null) {
            throw new IllegalStateException("Instance already exists!");
        }
    }

    public static DoubleCheckedLockingSingleton getInstance() {
        // First check without synchronization
        if (instance == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                // Second check with synchronization
                if (instance == null) {
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
        }
        return instance;
    }
}
```

**Pros:**

-   Thread-safe
-   Lazy initialization
-   Reduced synchronization overhead
-   Good performance

**Cons:**

-   Complex implementation
-   Requires `volatile` keyword
-   Potential issues with JVM optimizations (rare)

### 5. Bill Pugh Singleton (Initialization-on-Demand Holder)

```java
public class BillPughSingleton {

    private BillPughSingleton() {
        // Prevent reflection-based instantiation
        if (SingletonHelper.INSTANCE != null) {
            throw new IllegalStateException("Instance already exists!");
        }
    }

    // Static inner class - loaded only when referenced
    private static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void performOperation() {
        System.out.println("Performing operation in Bill Pugh Singleton");
    }
}
```

**Pros:**

-   Thread-safe without synchronization
-   Lazy initialization
-   No performance overhead
-   Leverages JVM's class loading mechanism

**Cons:**

-   Slightly more complex than basic implementations

### 6. Enum Singleton (Recommended)

```java
public enum EnumSingleton {
    INSTANCE;

    // Instance variables
    private String data;

    // Constructor (implicitly private)
    EnumSingleton() {
        this.data = "Singleton Data";
    }

    // Business methods
    public void performOperation() {
        System.out.println("Performing operation: " + data);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

// Usage
// EnumSingleton.INSTANCE.performOperation();
```

**Pros:**

-   Thread-safe by default
-   Serialization-safe
-   Reflection-proof
-   Concise implementation
-   Handles complex serialization scenarios automatically

**Cons:**

-   Cannot extend other classes (enums implicitly extend Enum)
-   Less flexible than class-based implementations
-   May be unfamiliar to some developers

## Solutions Summary

| Approach               | Thread Safety | Performance | Lazy Loading | Complexity |
| ---------------------- | ------------- | ----------- | ------------ | ---------- |
| Eager                  | ✅            | ✅          | ❌           | Low        |
| Lazy (Unsafe)          | ❌            | ✅          | ✅           | Low        |
| Synchronized Method    | ✅            | ❌          | ✅           | Low        |
| Double-Checked Locking | ✅            | ✅          | ✅           | High       |
| Bill Pugh              | ✅            | ✅          | ✅           | Medium     |
| Enum                   | ✅            | ✅          | ❌           | Low        |

## Advanced Implementation Example

```java
public class ConfigurationManager {
    private static volatile ConfigurationManager instance;
    private final Properties config;
    private final String configFile;

    private ConfigurationManager(String configFile) {
        this.configFile = configFile;
        this.config = new Properties();
        loadConfiguration();
    }

    public static ConfigurationManager getInstance() {
        return getInstance("default.properties");
    }

    public static ConfigurationManager getInstance(String configFile) {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager(configFile);
                }
            }
        }
        return instance;
    }

    private void loadConfiguration() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream(configFile)) {
            if (input != null) {
                config.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }

    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning not allowed for Singleton");
    }

    // Handle serialization
    protected Object readResolve() {
        return getInstance();
    }
}
```

## Pros and Cons

### Advantages

-   **Controlled Access**: Single point of control for instance creation
-   **Reduced Memory Footprint**: Only one instance exists
-   **Global Access**: Available throughout the application
-   **Lazy Initialization**: Can defer creation until needed
-   **Configuration Management**: Ideal for application-wide settings

### Disadvantages

-   **Global State**: Can make testing difficult
-   **Hidden Dependencies**: Classes using singleton have hidden dependency
-   **Scalability Issues**: Can become bottleneck in multithreaded applications
-   **Violates Single Responsibility**: Often manages both instance creation and business logic
-   **Difficult to Mock**: Makes unit testing challenging
-   **Tight Coupling**: Creates dependencies throughout codebase

## Real-World Examples

### Database Connection Manager

```java
public class DatabaseManager {
    private static volatile DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            // Initialize database connection
            this.connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydb",
                "username",
                "password"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create database connection", e);
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void executeQuery(String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
}
```

### Application Logger

```java
public enum ApplicationLogger {
    INSTANCE;

    private final Logger logger;

    ApplicationLogger() {
        logger = LoggerFactory.getLogger(ApplicationLogger.class);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public void debug(String message) {
        logger.debug(message);
    }
}

// Usage:
// ApplicationLogger.INSTANCE.info("Application started");
```

### Configuration Manager with File Watching

```java
public class ConfigManager {
    private static volatile ConfigManager instance;
    private Properties properties;
    private final String configPath;
    private long lastModified;

    private ConfigManager(String configPath) {
        this.configPath = configPath;
        loadProperties();
    }

    public static ConfigManager getInstance() {
        return getInstance("application.properties");
    }

    public static ConfigManager getInstance(String configPath) {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager(configPath);
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        try {
            File configFile = new File(configPath);
            this.lastModified = configFile.lastModified();

            properties = new Properties();
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot load configuration", e);
        }
    }

    public String getProperty(String key) {
        checkForConfigUpdates();
        return properties.getProperty(key);
    }

    private void checkForConfigUpdates() {
        File configFile = new File(configPath);
        if (configFile.lastModified() > lastModified) {
            synchronized (this) {
                if (configFile.lastModified() > lastModified) {
                    loadProperties();
                }
            }
        }
    }
}
```

## Memory and Performance Considerations

### Memory Leaks Prevention

```java
public class MemoryAwareSingleton {
    private static volatile MemoryAwareSingleton instance;
    private List<Object> resources;

    private MemoryAwareSingleton() {
        resources = new ArrayList<>();
    }

    public static MemoryAwareSingleton getInstance() {
        if (instance == null) {
            synchronized (MemoryAwareSingleton.class) {
                if (instance == null) {
                    instance = new MemoryAwareSingleton();
                }
            }
        }
        return instance;
    }

    public void cleanup() {
        synchronized (this) {
            if (resources != null) {
                resources.clear();
                resources = null;
            }
        }
    }

    // Shutdown hook for cleanup
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (instance != null) {
                instance.cleanup();
            }
        }));
    }
}
```

## Alternative Patterns to Consider

### 1. Dependency Injection

Instead of singleton, consider using dependency injection frameworks like Spring or Guice:

```java
@Component
@Scope("singleton")
public class ConfigService {
    // Spring manages as singleton
}
```

### 2. Static Utility Classes

For stateless operations:

```java
public final class MathUtils {
    private MathUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static double calculateDistance(Point a, Point b) {
        // Pure function - no state needed
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }
}
```

### 3. Factory Pattern with Instance Management

```java
public class ServiceFactory {
    private static final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> serviceClass) {
        return (T) instances.computeIfAbsent(serviceClass, clazz -> {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Cannot create instance", e);
            }
        });
    }
}
```
