# Strategy Pattern in Java - Complete Documentation

## Table of Contents

1. [Overview](#overview)
2. [Intent and Problem](#intent-and-problem)
3. [Structure](#structure)
4. [Implementation](#implementation)
5. [Real-World Examples](#real-world-examples)
6. [Advanced Usage](#advanced-usage)
7. [Best Practices](#best-practices)
8. [Comparison with Other Patterns](#comparison-with-other-patterns)
9. [Testing Strategies](#testing-strategies)
10. [Common Pitfalls](#common-pitfalls)

## Overview

The Strategy Pattern is a behavioral design pattern that defines a family of algorithms, encapsulates each one, and makes them interchangeable. It lets the algorithm vary independently from clients that use it.

**Key Characteristics:**

-   Defines a family of algorithms
-   Encapsulates each algorithm
-   Makes algorithms interchangeable at runtime
-   Follows the Open/Closed Principle
-   Eliminates conditional statements

## Intent and Problem

### Problem Statement

Consider a navigation app that needs to calculate routes using different algorithms:

-   Car navigation (fastest route)
-   Walking navigation (shortest distance)
-   Public transport navigation (cheapest route)

Without the Strategy pattern, you might end up with code like this:

```java
// Anti-pattern: Violates Open/Closed Principle
public class Navigator {
    public void calculateRoute(String transportType, String origin, String destination) {
        if (transportType.equals("car")) {
            // Car routing logic
        } else if (transportType.equals("walking")) {
            // Walking routing logic
        } else if (transportType.equals("public_transport")) {
            // Public transport logic
        }
        // Adding new transport types requires modifying this method
    }
}
```

### Solution with Strategy Pattern

The Strategy pattern solves this by encapsulating each algorithm in separate classes and making them interchangeable.

## Structure

### UML Class Diagram Components

1. **Strategy Interface**: Declares a common interface for all concrete strategies
2. **Concrete Strategies**: Implement different variations of an algorithm
3. **Context**: Maintains a reference to a Strategy object and delegates algorithm execution

### Basic Structure in Java

```java
// 1. Strategy Interface
public interface Strategy {
    void execute();
}

// 2. Concrete Strategies
public class ConcreteStrategyA implements Strategy {
    @Override
    public void execute() {
        // Algorithm A implementation
    }
}

public class ConcreteStrategyB implements Strategy {
    @Override
    public void execute() {
        // Algorithm B implementation
    }
}

// 3. Context
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy() {
        strategy.execute();
    }
}
```

## Implementation

### Complete Navigation Example

```java
// Strategy Interface
public interface RouteCalculationStrategy {
    String calculateRoute(String origin, String destination);
    int estimateTime(String origin, String destination);
}

// Concrete Strategy 1: Car Navigation
public class CarRouteStrategy implements RouteCalculationStrategy {
    @Override
    public String calculateRoute(String origin, String destination) {
        return String.format("Car route from %s to %s via highways", origin, destination);
    }

    @Override
    public int estimateTime(String origin, String destination) {
        // Simulate fastest route calculation
        return 45; // minutes
    }
}

// Concrete Strategy 2: Walking Navigation
public class WalkingRouteStrategy implements RouteCalculationStrategy {
    @Override
    public String calculateRoute(String origin, String destination) {
        return String.format("Walking route from %s to %s via pedestrian paths", origin, destination);
    }

    @Override
    public int estimateTime(String origin, String destination) {
        // Simulate walking time calculation
        return 120; // minutes
    }
}

// Concrete Strategy 3: Public Transport
public class PublicTransportRouteStrategy implements RouteCalculationStrategy {
    @Override
    public String calculateRoute(String origin, String destination) {
        return String.format("Public transport route from %s to %s via buses and trains", origin, destination);
    }

    @Override
    public int estimateTime(String origin, String destination) {
        // Simulate public transport calculation
        return 75; // minutes
    }
}

// Context Class
public class Navigator {
    private RouteCalculationStrategy strategy;

    public Navigator(RouteCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setRouteCalculationStrategy(RouteCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public String planRoute(String origin, String destination) {
        if (strategy == null) {
            throw new IllegalStateException("Route calculation strategy not set");
        }

        String route = strategy.calculateRoute(origin, destination);
        int time = strategy.estimateTime(origin, destination);

        return String.format("%s (Estimated time: %d minutes)", route, time);
    }
}

// Usage Example
public class NavigationApp {
    public static void main(String[] args) {
        String origin = "Downtown";
        String destination = "Airport";

        // Create navigator with car strategy
        Navigator navigator = new Navigator(new CarRouteStrategy());
        System.out.println("Car: " + navigator.planRoute(origin, destination));

        // Switch to walking strategy
        navigator.setRouteCalculationStrategy(new WalkingRouteStrategy());
        System.out.println("Walking: " + navigator.planRoute(origin, destination));

        // Switch to public transport strategy
        navigator.setRouteCalculationStrategy(new PublicTransportRouteStrategy());
        System.out.println("Public Transport: " + navigator.planRoute(origin, destination));
    }
}
```

**Output:**

```
Car: Car route from Downtown to Airport via highways (Estimated time: 45 minutes)
Walking: Walking route from Downtown to Airport via pedestrian paths (Estimated time: 120 minutes)
Public Transport: Public transport route from Downtown to Airport via buses and trains (Estimated time: 75 minutes)
```

## Real-World Examples

### 1. Payment Processing System

```java
// Payment Strategy Interface
public interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentDetails();
}

// Credit Card Payment
public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String holderName;
    private String expiryDate;

    public CreditCardPayment(String cardNumber, String holderName, String expiryDate) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Processing credit card payment of $%.2f%n", amount);
        // Credit card processing logic
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return String.format("Credit Card ending in %s", cardNumber.substring(cardNumber.length() - 4));
    }
}

// PayPal Payment
public class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Processing PayPal payment of $%.2f%n", amount);
        // PayPal processing logic
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return "PayPal account: " + email;
    }
}

// Cryptocurrency Payment
public class CryptocurrencyPayment implements PaymentStrategy {
    private String walletAddress;
    private String currency;

    public CryptocurrencyPayment(String walletAddress, String currency) {
        this.walletAddress = walletAddress;
        this.currency = currency;
    }

    @Override
    public boolean pay(double amount) {
        System.out.printf("Processing %s payment of $%.2f%n", currency, amount);
        // Cryptocurrency processing logic
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return String.format("%s wallet: %s", currency, walletAddress);
    }
}

// Payment Processor Context
public class PaymentProcessor {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processPayment(double amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy not set");
        }

        System.out.println("Payment method: " + paymentStrategy.getPaymentDetails());
        return paymentStrategy.pay(amount);
    }
}
```

### 2. Sorting Algorithms

```java
// Sorting Strategy Interface
public interface SortingStrategy<T extends Comparable<T>> {
    void sort(T[] array);
    String getAlgorithmName();
}

// Bubble Sort Strategy
public class BubbleSortStrategy<T extends Comparable<T>> implements SortingStrategy<T> {
    @Override
    public void sort(T[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    @Override
    public String getAlgorithmName() {
        return "Bubble Sort";
    }
}

// Quick Sort Strategy
public class QuickSortStrategy<T extends Comparable<T>> implements SortingStrategy<T> {
    @Override
    public void sort(T[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(T[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private int partition(T[] array, int low, int high) {
        T pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        T temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }

    @Override
    public String getAlgorithmName() {
        return "Quick Sort";
    }
}

// Sorting Context
public class ArraySorter<T extends Comparable<T>> {
    private SortingStrategy<T> sortingStrategy;

    public void setSortingStrategy(SortingStrategy<T> strategy) {
        this.sortingStrategy = strategy;
    }

    public void sort(T[] array) {
        if (sortingStrategy == null) {
            throw new IllegalStateException("Sorting strategy not set");
        }

        long startTime = System.nanoTime();
        sortingStrategy.sort(array);
        long endTime = System.nanoTime();

        System.out.printf("%s completed in %.2f ms%n",
            sortingStrategy.getAlgorithmName(),
            (endTime - startTime) / 1_000_000.0);
    }
}
```

## Advanced Usage

### 1. Strategy with Parameters

```java
// Strategy interface with configuration
public interface CompressionStrategy {
    byte[] compress(byte[] data, CompressionConfig config);
    byte[] decompress(byte[] compressedData);
}

// Configuration class
public class CompressionConfig {
    private int compressionLevel;
    private boolean preserveMetadata;

    // Constructor and getters/setters
    public CompressionConfig(int compressionLevel, boolean preserveMetadata) {
        this.compressionLevel = compressionLevel;
        this.preserveMetadata = preserveMetadata;
    }

    // Getters and setters...
}

// ZIP compression strategy
public class ZipCompressionStrategy implements CompressionStrategy {
    @Override
    public byte[] compress(byte[] data, CompressionConfig config) {
        // ZIP compression implementation with config
        System.out.printf("ZIP compression with level %d%n", config.getCompressionLevel());
        return data; // Simplified
    }

    @Override
    public byte[] decompress(byte[] compressedData) {
        // ZIP decompression logic
        return compressedData; // Simplified
    }
}
```

### 2. Strategy Factory Pattern

```java
// Strategy Factory
public class RouteCalculationStrategyFactory {
    public static RouteCalculationStrategy createStrategy(String transportType) {
        switch (transportType.toLowerCase()) {
            case "car":
                return new CarRouteStrategy();
            case "walking":
                return new WalkingRouteStrategy();
            case "public_transport":
                return new PublicTransportRouteStrategy();
            default:
                throw new IllegalArgumentException("Unknown transport type: " + transportType);
        }
    }
}

// Usage with factory
public class EnhancedNavigator {
    private RouteCalculationStrategy strategy;

    public void setTransportType(String transportType) {
        this.strategy = RouteCalculationStrategyFactory.createStrategy(transportType);
    }

    public String planRoute(String origin, String destination) {
        return strategy.calculateRoute(origin, destination);
    }
}
```

### 3. Strategy with Functional Interfaces (Java 8+)

```java
// Using functional interface for simple strategies
@FunctionalInterface
public interface DiscountStrategy {
    double applyDiscount(double originalPrice);
}

public class ShoppingCart {
    private DiscountStrategy discountStrategy = price -> price; // No discount by default

    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
    }

    public double calculateTotal(double originalPrice) {
        return discountStrategy.applyDiscount(originalPrice);
    }
}

// Usage with lambda expressions
public class DiscountExample {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        double price = 100.0;

        // Student discount (10%)
        cart.setDiscountStrategy(originalPrice -> originalPrice * 0.9);
        System.out.println("Student price: $" + cart.calculateTotal(price));

        // Senior discount (15%)
        cart.setDiscountStrategy(originalPrice -> originalPrice * 0.85);
        System.out.println("Senior price: $" + cart.calculateTotal(price));

        // VIP discount (20%)
        cart.setDiscountStrategy(originalPrice -> originalPrice * 0.8);
        System.out.println("VIP price: $" + cart.calculateTotal(price));
    }
}
```

## Best Practices

### 1. Strategy Selection Guidelines

```java
// Good: Strategy selection based on runtime conditions
public class DynamicRouteCalculator {
    public RouteCalculationStrategy selectStrategy(String weather, String traffic, String time) {
        if ("heavy".equals(traffic)) {
            return new PublicTransportRouteStrategy();
        } else if ("rain".equals(weather)) {
            return new CarRouteStrategy();
        } else if ("morning".equals(time)) {
            return new WalkingRouteStrategy();
        }
        return new CarRouteStrategy(); // Default
    }
}
```

### 2. Strategy Validation

```java
public class ValidatedNavigator {
    private RouteCalculationStrategy strategy;

    public void setRouteCalculationStrategy(RouteCalculationStrategy strategy) {
        Objects.requireNonNull(strategy, "Strategy cannot be null");
        this.strategy = strategy;
    }

    public String planRoute(String origin, String destination) {
        validateInputs(origin, destination);
        return strategy.calculateRoute(origin, destination);
    }

    private void validateInputs(String origin, String destination) {
        if (origin == null || origin.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin cannot be null or empty");
        }
        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be null or empty");
        }
    }
}
```

### 3. Strategy Caching

```java
public class CachedStrategyNavigator {
    private final Map<String, RouteCalculationStrategy> strategyCache = new HashMap<>();
    private RouteCalculationStrategy currentStrategy;

    public void setStrategy(String strategyType) {
        currentStrategy = strategyCache.computeIfAbsent(strategyType, type ->
            RouteCalculationStrategyFactory.createStrategy(type)
        );
    }
}
```

## Comparison with Other Patterns

### Strategy vs State Pattern

| Aspect            | Strategy Pattern                 | State Pattern              |
| ----------------- | -------------------------------- | -------------------------- |
| Purpose           | Algorithm selection              | Behavior based on state    |
| Context awareness | Strategies are independent       | States know about context  |
| Transitions       | Client controls strategy changes | States control transitions |
| Number of classes | Usually many strategies          | Usually fewer states       |

### Strategy vs Template Method

| Aspect      | Strategy Pattern          | Template Method            |
| ----------- | ------------------------- | -------------------------- |
| Structure   | Composition-based         | Inheritance-based          |
| Flexibility | Runtime algorithm change  | Compile-time customization |
| Coupling    | Loose coupling            | Tight coupling             |
| Extension   | Add new strategies easily | Override specific methods  |

### Strategy vs Command Pattern

| Aspect       | Strategy Pattern           | Command Pattern       |
| ------------ | -------------------------- | --------------------- |
| Purpose      | How to do something        | What to do            |
| Execution    | Immediate execution        | Can be queued/delayed |
| Parameters   | Algorithms with parameters | Encapsulate requests  |
| Undo support | Not typically supported    | Often supports undo   |

## Testing Strategies

### 1. Unit Testing Individual Strategies

```java
public class CarRouteStrategyTest {
    private CarRouteStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new CarRouteStrategy();
    }

    @Test
    void testCalculateRoute() {
        String result = strategy.calculateRoute("A", "B");
        assertThat(result).contains("Car route").contains("A").contains("B");
    }

    @Test
    void testEstimateTime() {
        int time = strategy.estimateTime("A", "B");
        assertThat(time).isPositive();
    }
}
```

### 2. Integration Testing with Context

```java
public class NavigatorIntegrationTest {
    private Navigator navigator;

    @Test
    void testStrategyChanges() {
        navigator = new Navigator(new CarRouteStrategy());
        String carRoute = navigator.planRoute("A", "B");
        assertThat(carRoute).contains("Car route");

        navigator.setRouteCalculationStrategy(new WalkingRouteStrategy());
        String walkingRoute = navigator.planRoute("A", "B");
        assertThat(walkingRoute).contains("Walking route");
    }

    @Test
    void testNullStrategyThrowsException() {
        navigator = new Navigator(null);
        assertThatThrownBy(() -> navigator.planRoute("A", "B"))
            .isInstanceOf(IllegalStateException.class);
    }
}
```

### 3. Mock Testing

```java
public class NavigatorMockTest {
    @Mock
    private RouteCalculationStrategy mockStrategy;

    private Navigator navigator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        navigator = new Navigator(mockStrategy);
    }

    @Test
    void testStrategyIsCalled() {
        when(mockStrategy.calculateRoute("A", "B")).thenReturn("Mock route");
        when(mockStrategy.estimateTime("A", "B")).thenReturn(30);

        String result = navigator.planRoute("A", "B");

        verify(mockStrategy).calculateRoute("A", "B");
        verify(mockStrategy).estimateTime("A", "B");
        assertThat(result).contains("Mock route").contains("30");
    }
}
```

## Common Pitfalls

### 1. Strategy Proliferation

**Problem:** Creating too many strategies for minor variations
**Solution:** Use parameterized strategies or template methods within strategies

```java
// Bad: Too many similar strategies
public class FastCarRouteStrategy implements RouteCalculationStrategy { /* ... */ }
public class EconomicCarRouteStrategy implements RouteCalculationStrategy { /* ... */ }
public class ScenicCarRouteStrategy implements RouteCalculationStrategy { /* ... */ }

// Good: Parameterized strategy
public class CarRouteStrategy implements RouteCalculationStrategy {
    private final RoutePreference preference;

    public CarRouteStrategy(RoutePreference preference) {
        this.preference = preference;
    }

    @Override
    public String calculateRoute(String origin, String destination) {
        switch (preference) {
            case FAST: return calculateFastRoute(origin, destination);
            case ECONOMIC: return calculateEconomicRoute(origin, destination);
            case SCENIC: return calculateScenicRoute(origin, destination);
            default: throw new IllegalStateException("Unknown preference: " + preference);
        }
    }
}
```

### 2. Context Becoming Too Complex

**Problem:** Context class accumulates too much logic
**Solution:** Keep context simple, delegate complex logic to strategies

### 3. Tight Coupling Between Strategies

**Problem:** Strategies depending on each other
**Solution:** Make strategies independent, use composition if needed

### 4. Not Handling Null Strategies

**Problem:** Runtime exceptions due to null strategy references
**Solution:** Always validate strategy assignment and provide defaults

## Summary

The Strategy Pattern is a powerful tool for creating flexible, maintainable code that follows SOLID principles. It's particularly useful when you have multiple ways to perform a task and want to be able to switch between them dynamically.

**Key Benefits:**

-   Eliminates conditional statements
-   Makes code more maintainable and extensible
-   Follows Open/Closed Principle
-   Enables runtime algorithm selection
-   Improves testability

**When to Use:**

-   Multiple algorithms for the same problem
-   Need to switch algorithms at runtime
-   Want to avoid large conditional statements
-   Algorithms are likely to change or expand

**When Not to Use:**

-   Only one algorithm exists
-   Algorithms are unlikely to change
-   Simple conditional logic is sufficient
-   Performance overhead of indirection is critical

The Strategy Pattern promotes clean, maintainable code and is an essential pattern in any developer's toolkit.
