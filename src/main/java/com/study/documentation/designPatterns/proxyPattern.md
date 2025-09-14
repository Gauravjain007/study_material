# Proxy Pattern in Java - Complete Documentation

## Table of Contents

1. [Introduction](#introduction)
2. [Pattern Overview](#pattern-overview)
3. [Types of Proxies](#types-of-proxies)
4. [UML Structure](#uml-structure)
5. [Implementation Examples](#implementation-examples)
6. [Advanced Implementations](#advanced-implementations)
7. [Real-World Applications](#real-world-applications)
8. [Best Practices](#best-practices)
9. [Common Pitfalls](#common-pitfalls)
10. [Performance Considerations](#performance-considerations)

## Introduction

The Proxy Pattern is a structural design pattern that provides a surrogate or placeholder for another object to control access to it. This pattern creates a representative object that controls access to another object, which may be remote, expensive to create, or in need of securing.

### Key Concepts

-   **Surrogate Control**: Acts as an intermediary between client and target object
-   **Lazy Initialization**: Can defer object creation until actually needed
-   **Access Control**: Can implement security, caching, or logging mechanisms
-   **Transparency**: Client interacts with proxy as if it were the real object

## Pattern Overview

### Intent

Provide a placeholder for another object to control access to it, add functionality, or defer expensive operations.

### Motivation

-   Control access to sensitive resources
-   Add caching layer for expensive operations
-   Implement lazy loading for heavy objects
-   Add logging or monitoring capabilities
-   Provide remote object access (RMI, web services)

### Applicability

Use the Proxy pattern when:

-   You need lazy initialization of expensive objects
-   Access control is required for sensitive operations
-   You want to add functionality without modifying existing code
-   Remote object access is needed
-   Caching mechanisms are required

## Types of Proxies

### 1. Virtual Proxy

Controls access to expensive objects by deferring their creation.

### 2. Protection Proxy

Controls access based on authentication/authorization.

### 3. Remote Proxy

Provides local representation of remote objects.

### 4. Cache Proxy

Provides caching mechanism for expensive operations.

### 5. Smart Reference Proxy

Provides additional actions when object is accessed (logging, reference counting).

## UML Structure

```
Subject (Interface)
├── RealSubject (Concrete Implementation)
└── Proxy (Proxy Implementation)
    └── realSubject: RealSubject
```

**Participants:**

-   **Subject**: Common interface for RealSubject and Proxy
-   **RealSubject**: The real object that proxy represents
-   **Proxy**: Maintains reference to RealSubject and controls access to it

## Implementation Examples

### Basic Proxy Implementation

```java
// Subject interface
interface Image {
    void display();
    String getInfo();
}

// RealSubject - Expensive to create
class RealImage implements Image {
    private String filename;
    private byte[] imageData;

    public RealImage(String filename) {
        this.filename = filename;
        loadImageFromDisk();
    }

    private void loadImageFromDisk() {
        System.out.println("Loading image: " + filename);
        // Simulate expensive loading operation
        try {
            Thread.sleep(2000);
            imageData = new byte[1024]; // Simulated image data
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void display() {
        System.out.println("Displaying image: " + filename);
    }

    @Override
    public String getInfo() {
        return "Image: " + filename + " (Size: " + imageData.length + " bytes)";
    }
}

// Virtual Proxy - Lazy loading
class ImageProxy implements Image {
    private String filename;
    private RealImage realImage;

    public ImageProxy(String filename) {
        this.filename = filename;
        // RealImage is not created until needed
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }

    @Override
    public String getInfo() {
        if (realImage == null) {
            return "Image: " + filename + " (Not loaded)";
        }
        return realImage.getInfo();
    }
}
```

### Protection Proxy Implementation

```java
// Subject interface
interface BankAccount {
    void deposit(double amount);
    void withdraw(double amount);
    double getBalance();
}

// RealSubject
class RealBankAccount implements BankAccount {
    private double balance;
    private String accountNumber;

    public RealBankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: $" + amount + ". New balance: $" + balance);
    }

    @Override
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }
}

// User class for authentication
class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
}

// Protection Proxy
class ProtectedBankAccountProxy implements BankAccount {
    private RealBankAccount realAccount;
    private User currentUser;

    public ProtectedBankAccountProxy(RealBankAccount realAccount, User user) {
        this.realAccount = realAccount;
        this.currentUser = user;
    }

    private boolean isAuthorized(String operation) {
        if ("ADMIN".equals(currentUser.getRole())) {
            return true;
        }
        if ("USER".equals(currentUser.getRole()) &&
            ("deposit".equals(operation) || "getBalance".equals(operation))) {
            return true;
        }
        return false;
    }

    @Override
    public void deposit(double amount) {
        if (isAuthorized("deposit")) {
            realAccount.deposit(amount);
        } else {
            System.out.println("Access denied: Insufficient privileges for deposit");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (isAuthorized("withdraw")) {
            realAccount.withdraw(amount);
        } else {
            System.out.println("Access denied: Insufficient privileges for withdrawal");
        }
    }

    @Override
    public double getBalance() {
        if (isAuthorized("getBalance")) {
            return realAccount.getBalance();
        } else {
            System.out.println("Access denied: Cannot view balance");
            return -1;
        }
    }
}
```

### Cache Proxy Implementation

```java
import java.util.HashMap;
import java.util.Map;

// Subject interface
interface DataService {
    String fetchData(String key);
}

// RealSubject - Expensive database operations
class DatabaseService implements DataService {
    @Override
    public String fetchData(String key) {
        System.out.println("Fetching data from database for key: " + key);
        // Simulate expensive database operation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Data for " + key + " from database";
    }
}

// Cache Proxy
class CacheProxy implements DataService {
    private DatabaseService databaseService;
    private Map<String, String> cache;
    private Map<String, Long> cacheTimestamps;
    private static final long CACHE_EXPIRY = 5000; // 5 seconds

    public CacheProxy() {
        this.databaseService = new DatabaseService();
        this.cache = new HashMap<>();
        this.cacheTimestamps = new HashMap<>();
    }

    @Override
    public String fetchData(String key) {
        Long timestamp = cacheTimestamps.get(key);
        long currentTime = System.currentTimeMillis();

        // Check if data is in cache and not expired
        if (timestamp != null && (currentTime - timestamp) < CACHE_EXPIRY) {
            System.out.println("Returning cached data for key: " + key);
            return cache.get(key);
        }

        // Fetch from database and cache
        String data = databaseService.fetchData(key);
        cache.put(key, data);
        cacheTimestamps.put(key, currentTime);

        return data;
    }

    public void clearCache() {
        cache.clear();
        cacheTimestamps.clear();
        System.out.println("Cache cleared");
    }
}
```

## Advanced Implementations

### Dynamic Proxy with Logging

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// Service interface
interface CalculatorService {
    double add(double a, double b);
    double subtract(double a, double b);
    double multiply(double a, double b);
    double divide(double a, double b);
}

// Real implementation
class BasicCalculator implements CalculatorService {
    @Override
    public double add(double a, double b) {
        return a + b;
    }

    @Override
    public double subtract(double a, double b) {
        return a - b;
    }

    @Override
    public double multiply(double a, double b) {
        return a * b;
    }

    @Override
    public double divide(double a, double b) {
        if (b == 0) throw new IllegalArgumentException("Cannot divide by zero");
        return a / b;
    }
}

// Dynamic proxy with logging
class LoggingInvocationHandler implements InvocationHandler {
    private Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();

        System.out.println("Before method: " + method.getName());
        System.out.println("Arguments: " + java.util.Arrays.toString(args));

        try {
            Object result = method.invoke(target, args);

            long endTime = System.currentTimeMillis();
            System.out.println("After method: " + method.getName());
            System.out.println("Result: " + result);
            System.out.println("Execution time: " + (endTime - startTime) + "ms");

            return result;
        } catch (Exception e) {
            System.out.println("Exception in method: " + method.getName());
            System.out.println("Exception: " + e.getCause());
            throw e.getCause();
        }
    }

    public static <T> T createProxy(T target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
            interfaceType.getClassLoader(),
            new Class[]{interfaceType},
            new LoggingInvocationHandler(target)
        );
    }
}
```

### Composite Proxy (Multiple Concerns)

```java
// Composite proxy combining multiple concerns
class CompositeServiceProxy implements DataService {
    private DataService target;
    private Map<String, String> cache;
    private Map<String, Long> cacheTimestamps;
    private User currentUser;
    private static final long CACHE_EXPIRY = 5000;

    public CompositeServiceProxy(DataService target, User user) {
        this.target = target;
        this.currentUser = user;
        this.cache = new HashMap<>();
        this.cacheTimestamps = new HashMap<>();
    }

    @Override
    public String fetchData(String key) {
        // Authentication check
        if (!isAuthorized()) {
            throw new SecurityException("Access denied");
        }

        // Logging
        System.out.println("User " + currentUser.getUsername() +
                         " accessing data with key: " + key);

        // Caching logic
        Long timestamp = cacheTimestamps.get(key);
        long currentTime = System.currentTimeMillis();

        if (timestamp != null && (currentTime - timestamp) < CACHE_EXPIRY) {
            System.out.println("Returning cached data");
            return cache.get(key);
        }

        // Fetch from target
        String data = target.fetchData(key);

        // Cache the result
        cache.put(key, data);
        cacheTimestamps.put(key, currentTime);

        return data;
    }

    private boolean isAuthorized() {
        return currentUser != null &&
               ("USER".equals(currentUser.getRole()) ||
                "ADMIN".equals(currentUser.getRole()));
    }
}
```

## Real-World Applications

### 1. Spring Framework Proxies

Spring uses proxies extensively for:

-   AOP (Aspect-Oriented Programming)
-   Transaction management
-   Security
-   Caching

### 2. Hibernate Lazy Loading

Hibernate uses proxy objects for lazy loading of entities and collections.

### 3. Remote Method Invocation (RMI)

Java RMI uses proxy stubs to represent remote objects locally.

### 4. Web Service Clients

JAX-WS and other web service frameworks use proxies to represent remote services.

### 5. Collection Wrappers

```java
// Java Collections Framework uses proxies
List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
List<String> unmodifiableList = Collections.unmodifiableList(originalList);
```

## Best Practices

### 1. Interface Segregation

```java
// Good: Focused interfaces
interface ReadableResource {
    String read();
}

interface WritableResource {
    void write(String data);
}

// Better than one large interface
interface ResourceOperations extends ReadableResource, WritableResource {
    // Combines specific interfaces
}
```

### 2. Proper Resource Management

```java
class ResourceProxy implements AutoCloseable {
    private ExpensiveResource resource;

    @Override
    public void close() throws Exception {
        if (resource != null) {
            resource.cleanup();
        }
    }
}
```

### 3. Thread Safety

```java
class ThreadSafeProxy implements Service {
    private final Service target;
    private final Object lock = new Object();

    public ThreadSafeProxy(Service target) {
        this.target = target;
    }

    @Override
    public String performOperation(String input) {
        synchronized (lock) {
            return target.performOperation(input);
        }
    }
}
```

### 4. Configuration-Driven Proxies

```java
class ConfigurableProxy {
    private boolean cachingEnabled;
    private boolean loggingEnabled;
    private boolean securityEnabled;

    // Configure proxy behavior based on configuration
    public ConfigurableProxy(ProxyConfig config) {
        this.cachingEnabled = config.isCachingEnabled();
        this.loggingEnabled = config.isLoggingEnabled();
        this.securityEnabled = config.isSecurityEnabled();
    }
}
```

## Common Pitfalls

### 1. Over-Engineering

```java
// Avoid: Too many proxy layers
Service service = new LoggingProxy(
    new CachingProxy(
        new SecurityProxy(
            new ValidationProxy(
                new RealService()))));
```

### 2. Memory Leaks in Caching Proxies

```java
// Problem: Unbounded cache
class BadCacheProxy {
    private Map<String, Object> cache = new HashMap<>(); // Never cleaned
}

// Solution: Use WeakHashMap or implement expiration
class GoodCacheProxy {
    private Map<String, Object> cache = new WeakHashMap<>();
    // Or implement LRU cache with size limit
}
```

### 3. Inconsistent Interface Implementation

```java
// Ensure proxy implements ALL interface methods correctly
class IncompleteProxy implements ComplexService {
    private ComplexService target;

    @Override
    public void method1() {
        // Implemented
        target.method1();
    }

    @Override
    public void method2() {
        // Forgot to delegate!
        // This breaks the proxy contract
    }
}
```

## Performance Considerations

### 1. Proxy Overhead

-   Each method call goes through proxy layer
-   Consider direct access for performance-critical paths
-   Profile proxy performance impact

### 2. Memory Usage

-   Proxies hold references to real objects
-   Cache proxies consume additional memory
-   Monitor memory usage in production

### 3. Dynamic vs Static Proxies

```java
// Static proxy: Better performance, compile-time checking
class StaticProxy implements Service {
    private Service target;

    public String operation() {
        return target.operation(); // Direct method call
    }
}

// Dynamic proxy: More flexible, runtime overhead
Service proxy = (Service) Proxy.newProxyInstance(...);
```

## Testing Strategies

### 1. Unit Testing Proxies

```java
@Test
public void testCacheProxy() {
    DataService mockService = mock(DataService.class);
    when(mockService.fetchData("key1")).thenReturn("data1");

    CacheProxy proxy = new CacheProxy(mockService);

    // First call should hit the service
    String result1 = proxy.fetchData("key1");
    assertEquals("data1", result1);

    // Second call should use cache
    String result2 = proxy.fetchData("key1");
    assertEquals("data1", result2);

    // Verify service was called only once
    verify(mockService, times(1)).fetchData("key1");
}
```

### 2. Integration Testing

```java
@Test
public void testProxyChain() {
    Service realService = new RealService();
    Service securityProxy = new SecurityProxy(realService, adminUser);
    Service loggingProxy = new LoggingProxy(securityProxy);

    // Test the complete proxy chain
    String result = loggingProxy.performOperation("test");
    assertNotNull(result);
}
```

## Conclusion

The Proxy Pattern is a powerful structural pattern that provides controlled access to objects while maintaining transparency to clients. It's particularly useful for implementing cross-cutting concerns like caching, security, logging, and lazy loading. When implemented correctly, proxies can significantly improve application performance and maintainability while keeping code modular and focused on single responsibilities.

Key takeaways:

-   Use proxies to add functionality without modifying existing code
-   Consider performance implications of proxy layers
-   Implement proper resource management and thread safety
-   Test proxy behavior thoroughly
-   Avoid over-engineering with too many proxy layers
