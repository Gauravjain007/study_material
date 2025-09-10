# Decorator Pattern in Java - Complete Documentation

## Table of Contents

1. [Overview](#overview)
2. [Intent and Problem](#intent-and-problem)
3. [Structure](#structure)
4. [Key Participants](#key-participants)
5. [Implementation](#implementation)
6. [Real-World Examples](#real-world-examples)
7. [Java Standard Library Usage](#java-standard-library-usage)
8. [Advantages and Disadvantages](#advantages-and-disadvantages)
9. [Best Practices](#best-practices)
10. [Common Pitfalls](#common-pitfalls)

## Overview

The Decorator Pattern is a structural design pattern that allows behavior to be added to objects dynamically without altering their class structure. It provides a flexible alternative to subclassing for extending functionality by wrapping objects in a series of decorator classes.

**Pattern Type:** Structural  
**Complexity:** Medium  
**Popularity:** High (widely used in Java I/O streams, GUI components, etc.)

## Intent and Problem

### Problem

-   You need to add responsibilities to objects dynamically and transparently
-   Subclassing would result in an explosion of classes for every combination of features
-   You want to add or remove responsibilities at runtime
-   Extension by subclassing is impractical due to large number of independent extensions

### Solution

The Decorator pattern attaches additional responsibilities to an object dynamically by placing these objects inside special wrapper objects that contain the behaviors.

### Real-World Analogy

Think of getting dressed in layers. You start with basic clothes (the core object) and add layers like jackets, scarves, hats (decorators) depending on weather conditions. Each layer adds functionality without changing the underlying clothes.

## Structure

```
Component (interface)
├── ConcreteComponent (basic implementation)
└── BaseDecorator (abstract decorator)
    ├── ConcreteDecoratorA
    ├── ConcreteDecoratorB
    └── ConcreteDecoratorC
```

### UML Class Diagram Representation

```
+------------------+
|   Component      |
|------------------|
| +operation()     |
+------------------+
         ↑
         |
+------------------+     +------------------+
|ConcreteComponent |     | BaseDecorator    |
|------------------|     |------------------|
| +operation()     |     | -component       |
+------------------+     | +operation()     |
                         +------------------+
                                  ↑
                                  |
                         +------------------+
                         |ConcreteDecorator |
                         |------------------|
                         | +operation()     |
                         | +addedBehavior() |
                         +------------------+
```

## Key Participants

1. **Component:** Defines the interface for objects that can have responsibilities added dynamically
2. **ConcreteComponent:** The original object to which additional responsibilities can be attached
3. **BaseDecorator:** Maintains a reference to a Component object and defines an interface that conforms to Component's interface
4. **ConcreteDecorator:** Adds responsibilities to the component

## Implementation

### Basic Coffee Shop Example

```java
// Component interface
public interface Coffee {
    double getCost();
    String getDescription();
}

// ConcreteComponent
public class SimpleCoffee implements Coffee {
    @Override
    public double getCost() {
        return 2.0;
    }

    @Override
    public String getDescription() {
        return "Simple coffee";
    }
}

// Base Decorator
public abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public double getCost() {
        return coffee.getCost();
    }

    @Override
    public String getDescription() {
        return coffee.getDescription();
    }
}

// Concrete Decorators
public class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 0.5;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", milk";
    }
}

public class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 0.2;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", sugar";
    }
}

public class WhipDecorator extends CoffeeDecorator {
    public WhipDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 0.7;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", whip";
    }
}

// Usage Example
public class CoffeeShop {
    public static void main(String[] args) {
        // Simple coffee
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        // Coffee with milk
        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        // Coffee with milk and sugar
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        // Coffee with milk, sugar, and whip
        coffee = new WhipDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());
    }
}
```

### Advanced Text Processing Example

```java
// Component interface
public interface TextProcessor {
    String process(String text);
}

// ConcreteComponent
public class PlainTextProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text;
    }
}

// Base Decorator
public abstract class TextProcessorDecorator implements TextProcessor {
    protected TextProcessor processor;

    public TextProcessorDecorator(TextProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(String text) {
        return processor.process(text);
    }
}

// Concrete Decorators
public class UpperCaseDecorator extends TextProcessorDecorator {
    public UpperCaseDecorator(TextProcessor processor) {
        super(processor);
    }

    @Override
    public String process(String text) {
        return processor.process(text).toUpperCase();
    }
}

public class TrimDecorator extends TextProcessorDecorator {
    public TrimDecorator(TextProcessor processor) {
        super(processor);
    }

    @Override
    public String process(String text) {
        return processor.process(text).trim();
    }
}

public class EncryptionDecorator extends TextProcessorDecorator {
    private final int shift;

    public EncryptionDecorator(TextProcessor processor, int shift) {
        super(processor);
        this.shift = shift;
    }

    @Override
    public String process(String text) {
        String processedText = processor.process(text);
        StringBuilder encrypted = new StringBuilder();

        for (char c : processedText.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                encrypted.append((char) ((c - base + shift) % 26 + base));
            } else {
                encrypted.append(c);
            }
        }

        return encrypted.toString();
    }
}

// Usage
public class TextProcessingDemo {
    public static void main(String[] args) {
        String input = "  Hello World!  ";

        TextProcessor processor = new PlainTextProcessor();
        processor = new TrimDecorator(processor);
        processor = new UpperCaseDecorator(processor);
        processor = new EncryptionDecorator(processor, 3);

        String result = processor.process(input);
        System.out.println("Original: '" + input + "'");
        System.out.println("Processed: '" + result + "'");
    }
}
```

## Real-World Examples

### 1. File Compression and Encryption System

```java
public interface DataSource {
    void writeData(String data);
    String readData();
}

public class FileDataSource implements DataSource {
    private String filename;
    private String data = "";

    public FileDataSource(String filename) {
        this.filename = filename;
    }

    @Override
    public void writeData(String data) {
        this.data = data;
        System.out.println("Writing to file: " + filename);
    }

    @Override
    public String readData() {
        System.out.println("Reading from file: " + filename);
        return data;
    }
}

public abstract class DataSourceDecorator implements DataSource {
    protected DataSource wrapper;

    public DataSourceDecorator(DataSource source) {
        this.wrapper = source;
    }

    @Override
    public void writeData(String data) {
        wrapper.writeData(data);
    }

    @Override
    public String readData() {
        return wrapper.readData();
    }
}

public class CompressionDecorator extends DataSourceDecorator {
    public CompressionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        String compressed = compress(data);
        super.writeData(compressed);
    }

    @Override
    public String readData() {
        String data = super.readData();
        return decompress(data);
    }

    private String compress(String data) {
        // Simulate compression
        return "COMPRESSED[" + data + "]";
    }

    private String decompress(String data) {
        // Simulate decompression
        if (data.startsWith("COMPRESSED[") && data.endsWith("]")) {
            return data.substring(11, data.length() - 1);
        }
        return data;
    }
}

public class EncryptionDecorator extends DataSourceDecorator {
    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        String encrypted = encrypt(data);
        super.writeData(encrypted);
    }

    @Override
    public String readData() {
        String data = super.readData();
        return decrypt(data);
    }

    private String encrypt(String data) {
        // Simulate encryption
        return "ENCRYPTED[" + data + "]";
    }

    private String decrypt(String data) {
        // Simulate decryption
        if (data.startsWith("ENCRYPTED[") && data.endsWith("]")) {
            return data.substring(10, data.length() - 1);
        }
        return data;
    }
}
```

### 2. Notification System

```java
public interface Notifier {
    void send(String message);
}

public class EmailNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}

public abstract class NotifierDecorator implements Notifier {
    protected Notifier notifier;

    public NotifierDecorator(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void send(String message) {
        notifier.send(message);
    }
}

public class SMSDecorator extends NotifierDecorator {
    public SMSDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending SMS: " + message);
    }
}

public class SlackDecorator extends NotifierDecorator {
    public SlackDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending Slack message: " + message);
    }
}

public class FacebookDecorator extends NotifierDecorator {
    public FacebookDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Posting on Facebook: " + message);
    }
}
```

## Java Standard Library Usage

### 1. I/O Streams

The Java I/O library extensively uses the Decorator pattern:

```java
// Basic file input
FileInputStream fis = new FileInputStream("file.txt");

// Add buffering capability
BufferedInputStream bis = new BufferedInputStream(fis);

// Add data type reading capability
DataInputStream dis = new DataInputStream(bis);

// Reading data
int value = dis.readInt();
dis.close();

// Equivalent one-liner
DataInputStream dis = new DataInputStream(
    new BufferedInputStream(
        new FileInputStream("file.txt")
    )
);
```

### 2. Collections

```java
List<String> list = new ArrayList<>();
list.add("item1");
list.add("item2");

// Synchronize the list
List<String> synchronizedList = Collections.synchronizedList(list);

// Make it unmodifiable
List<String> unmodifiableList = Collections.unmodifiableList(synchronizedList);
```

### 3. Servlet API

```java
// HttpServletRequestWrapper and HttpServletResponseWrapper
// are examples of decorators in the Servlet API
public class LoggingRequestWrapper extends HttpServletRequestWrapper {
    public LoggingRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        System.out.println("Parameter: " + name + " = " + value);
        return value;
    }
}
```

## Advantages and Disadvantages

### Advantages

1. **Flexibility:** More flexible than inheritance for extending functionality
2. **Runtime Composition:** Can add/remove responsibilities at runtime
3. **Single Responsibility Principle:** Each decorator has a single purpose
4. **Open/Closed Principle:** Open for extension, closed for modification
5. **Avoids Feature-Laden Classes:** Prevents monolithic classes with many features
6. **Mix and Match:** Can combine decorators in any order

### Disadvantages

1. **Complexity:** Can result in many small classes and complex object composition
2. **Identity Issues:** Decorated object is not identical to the original
3. **Debugging Difficulty:** Stack traces can become long and confusing
4. **Order Dependency:** Sometimes the order of decorators matters
5. **Interface Limitations:** Limited to the methods defined in the component interface

## Best Practices

### 1. Use Interfaces Instead of Abstract Classes

```java
// Preferred
public interface Component {
    void operation();
}

// Less flexible
public abstract class Component {
    public abstract void operation();
}
```

### 2. Keep Decorators Lightweight

```java
public class TimingDecorator extends ComponentDecorator {
    public TimingDecorator(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        long start = System.currentTimeMillis();
        super.operation();
        long end = System.currentTimeMillis();
        System.out.println("Operation took: " + (end - start) + "ms");
    }
}
```

### 3. Consider Using Builder Pattern for Complex Compositions

```java
public class ComponentBuilder {
    private Component component;

    public ComponentBuilder(Component base) {
        this.component = base;
    }

    public ComponentBuilder withLogging() {
        this.component = new LoggingDecorator(this.component);
        return this;
    }

    public ComponentBuilder withTiming() {
        this.component = new TimingDecorator(this.component);
        return this;
    }

    public ComponentBuilder withCaching() {
        this.component = new CachingDecorator(this.component);
        return this;
    }

    public Component build() {
        return this.component;
    }
}

// Usage
Component component = new ComponentBuilder(new ConcreteComponent())
    .withLogging()
    .withTiming()
    .withCaching()
    .build();
```

### 4. Implement equals() and hashCode() Carefully

```java
public abstract class ComponentDecorator implements Component {
    protected final Component component;

    public ComponentDecorator(Component component) {
        this.component = Objects.requireNonNull(component);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ComponentDecorator that = (ComponentDecorator) obj;
        return Objects.equals(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), component);
    }
}
```

## Common Pitfalls

### 1. Decorator Order Matters

```java
// These may produce different results
Component c1 = new TimingDecorator(new LoggingDecorator(base));
Component c2 = new LoggingDecorator(new TimingDecorator(base));
```

### 2. Breaking Interface Contracts

```java
// Bad: Adding methods not in the interface
public class BadDecorator extends ComponentDecorator {
    // This method breaks the Decorator pattern
    public void newMethod() {
        // Implementation
    }
}
```

### 3. Performance Overhead

```java
// Each decorator adds method call overhead
Component heavily_decorated =
    new DecoratorA(
        new DecoratorB(
            new DecoratorC(
                new DecoratorD(
                    new ConcreteComponent()
                )
            )
        )
    );
```

### 4. Memory Leaks with Circular References

```java
// Avoid circular references
public class BadCircularDecorator extends ComponentDecorator {
    public BadCircularDecorator(Component component) {
        super(component);
        // Don't do this - creates circular reference
        if (component instanceof ComponentDecorator) {
            ((ComponentDecorator) component).component = this;
        }
    }
}
```

## Conclusion

The Decorator Pattern is a powerful structural pattern that provides a flexible alternative to subclassing for extending object functionality. It's particularly useful when you need to add responsibilities to objects dynamically and when inheritance would result in too many classes. The pattern is heavily used in Java's standard library, especially in I/O streams and collections.

When implementing the Decorator pattern, focus on maintaining a clean interface, keeping decorators lightweight, and being mindful of composition order and performance implications. With proper implementation, the Decorator pattern can significantly improve the flexibility and maintainability of your code.
