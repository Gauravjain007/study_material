# Observer Pattern in Java - Complete Documentation

## Table of Contents

1. [Pattern Overview](#pattern-overview)
2. [Core Components](#core-components)
3. [Traditional Implementation](#traditional-implementation)
4. [Java Built-in Observer/Observable](#java-built-in-observerobservable)
5. [Modern Approaches](#modern-approaches)
6. [Best Practices](#best-practices)
7. [Common Pitfalls](#common-pitfalls)
8. [Real-World Examples](#real-world-examples)
9. [Performance Considerations](#performance-considerations)

## Pattern Overview

The Observer Pattern is a behavioral design pattern that defines a one-to-many dependency between objects. When one object (the subject) changes state, all dependent objects (observers) are automatically notified and updated.

### Key Characteristics

-   **Loose Coupling**: Subjects and observers interact through well-defined interfaces
-   **Dynamic Relationships**: Observers can be added or removed at runtime
-   **Broadcast Communication**: One subject can notify multiple observers simultaneously
-   **Pull vs Push**: Observers can either receive data directly or pull it from the subject

### When to Use

-   When changes to one object require updating multiple dependent objects
-   When you want to decouple the subject from its observers
-   When you need to notify an unknown number of objects
-   When you want to add/remove observers dynamically

## Core Components

### 1. Subject (Observable)

The object being observed. It maintains a list of observers and provides methods to:

-   Add observers
-   Remove observers
-   Notify all observers of state changes

### 2. Observer

Defines the interface for objects that should be notified of changes in a subject.

### 3. ConcreteSubject

Implements the Subject interface and stores state that observers are interested in.

### 4. ConcreteObserver

Implements the Observer interface and maintains a reference to a ConcreteSubject.

## Traditional Implementation

### Basic Structure

```java
// Observer interface
interface Observer {
    void update(Subject subject);
}

// Subject interface
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

// Concrete Subject
class WeatherStation implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private float temperature;
    private float humidity;
    private float pressure;

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }

    // Getters
    public float getTemperature() { return temperature; }
    public float getHumidity() { return humidity; }
    public float getPressure() { return pressure; }
}

// Concrete Observer
class CurrentConditionsDisplay implements Observer {
    private float temperature;
    private float humidity;
    private Subject weatherStation;

    public CurrentConditionsDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.attach(this);
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof WeatherStation) {
            WeatherStation station = (WeatherStation) subject;
            this.temperature = station.getTemperature();
            this.humidity = station.getHumidity();
            display();
        }
    }

    public void display() {
        System.out.println("Current conditions: " + temperature + "°F and " + humidity + "% humidity");
    }
}
```

### Generic Implementation

```java
// Generic Observer interface
interface Observer<T> {
    void update(T data);
}

// Generic Subject
abstract class Subject<T> {
    private final List<Observer<T>> observers = new ArrayList<>();

    public final void attach(Observer<T> observer) {
        observers.add(observer);
    }

    public final void detach(Observer<T> observer) {
        observers.remove(observer);
    }

    protected final void notifyObservers(T data) {
        for (Observer<T> observer : observers) {
            observer.update(data);
        }
    }
}

// Usage example
class StockPrice extends Subject<StockPrice> {
    private String symbol;
    private double price;

    public void setPrice(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
        notifyObservers(this);
    }

    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
}

class StockDisplay implements Observer<StockPrice> {
    @Override
    public void update(StockPrice stock) {
        System.out.println(stock.getSymbol() + ": $" + stock.getPrice());
    }
}
```

## Java Built-in Observer/Observable

Java provides built-in support through `java.util.Observer` and `java.util.Observable` (deprecated since Java 9).

```java
import java.util.Observable;
import java.util.Observer;

// Using built-in Observable
class NewsAgency extends Observable {
    private String news;

    public void setNews(String news) {
        this.news = news;
        setChanged();           // Mark as changed
        notifyObservers(news);  // Notify with data (push)
        // or notifyObservers(); // Notify without data (pull)
    }

    public String getNews() {
        return news;
    }
}

// Using built-in Observer
class NewsChannel implements Observer {
    private String channelName;

    public NewsChannel(String name) {
        this.channelName = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        String news = (String) arg; // Push model
        // or String news = ((NewsAgency) o).getNews(); // Pull model
        System.out.println(channelName + " received news: " + news);
    }
}

// Usage
NewsAgency agency = new NewsAgency();
NewsChannel cnn = new NewsChannel("CNN");
NewsChannel bbc = new NewsChannel("BBC");

agency.addObserver(cnn);
agency.addObserver(bbc);
agency.setNews("Breaking: Observer Pattern Explained!");
```

## Modern Approaches

### 1. Using PropertyChangeListener (Swing/JavaBeans)

```java
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class Model {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String value;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void setValue(String newValue) {
        String oldValue = this.value;
        this.value = newValue;
        support.firePropertyChange("value", oldValue, newValue);
    }

    public String getValue() { return value; }
}

// Usage
Model model = new Model();
model.addPropertyChangeListener(evt -> {
    System.out.println("Property " + evt.getPropertyName() +
                      " changed from " + evt.getOldValue() +
                      " to " + evt.getNewValue());
});
```

### 2. Using CompletableFuture and Reactive Streams

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow.*;

// Simple reactive subject
class ReactiveSubject<T> implements Publisher<T> {
    private final List<Subscriber<? super T>> subscribers = new ArrayList<>();

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        subscribers.add(subscriber);
        subscriber.onSubscribe(new Subscription() {
            @Override
            public void request(long n) {}

            @Override
            public void cancel() {
                subscribers.remove(subscriber);
            }
        });
    }

    public void emit(T value) {
        subscribers.forEach(subscriber -> subscriber.onNext(value));
    }
}
```

### 3. Event-Driven Approach

```java
// Event class
class Event<T> {
    private final T data;
    private final String type;

    public Event(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public T getData() { return data; }
    public String getType() { return type; }
}

// Event handler interface
@FunctionalInterface
interface EventHandler<T> {
    void handle(Event<T> event);
}

// Event bus
class EventBus {
    private final Map<String, List<EventHandler<?>>> handlers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> void subscribe(String eventType, EventHandler<T> handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>())
                .add((EventHandler<Object>) handler);
    }

    @SuppressWarnings("unchecked")
    public <T> void publish(Event<T> event) {
        List<EventHandler<?>> eventHandlers = handlers.get(event.getType());
        if (eventHandlers != null) {
            eventHandlers.forEach(handler ->
                ((EventHandler<T>) handler).handle(event));
        }
    }
}
```

## Best Practices

### 1. Thread Safety

```java
class ThreadSafeSubject {
    private final List<Observer> observers = Collections.synchronizedList(new ArrayList<>());

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        List<Observer> observersCopy;
        synchronized (observers) {
            observersCopy = new ArrayList<>(observers);
        }

        for (Observer observer : observersCopy) {
            observer.update(this);
        }
    }
}
```

### 2. Weak References to Prevent Memory Leaks

```java
import java.lang.ref.WeakReference;

class WeakReferenceSubject {
    private final List<WeakReference<Observer>> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(new WeakReference<>(observer));
    }

    public void notifyObservers() {
        Iterator<WeakReference<Observer>> iterator = observers.iterator();
        while (iterator.hasNext()) {
            WeakReference<Observer> ref = iterator.next();
            Observer observer = ref.get();

            if (observer == null) {
                iterator.remove(); // Remove dead reference
            } else {
                observer.update(this);
            }
        }
    }
}
```

### 3. Exception Handling

```java
public void notifyObservers() {
    for (Observer observer : observers) {
        try {
            observer.update(this);
        } catch (Exception e) {
            // Log error but continue notifying other observers
            System.err.println("Error notifying observer: " + e.getMessage());
        }
    }
}
```

### 4. Asynchronous Notifications

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class AsyncSubject {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final List<Observer> observers = new ArrayList<>();

    public void notifyObserversAsync() {
        for (Observer observer : observers) {
            executor.submit(() -> {
                try {
                    observer.update(this);
                } catch (Exception e) {
                    System.err.println("Error in async notification: " + e.getMessage());
                }
            });
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
```

## Common Pitfalls

### 1. Memory Leaks

**Problem**: Observers holding references to subjects can prevent garbage collection.

**Solution**: Use weak references or ensure proper cleanup.

```java
// Bad - potential memory leak
subject.attach(observer);

// Good - with cleanup
subject.attach(observer);
// ... later
subject.detach(observer);
```

### 2. Notification Loops

**Problem**: Observer updating subject during notification can cause infinite loops.

**Solution**: Use flags or queuing to prevent recursive notifications.

```java
class SafeSubject {
    private boolean notifying = false;

    public void notifyObservers() {
        if (notifying) return;

        notifying = true;
        try {
            // Notify observers
        } finally {
            notifying = false;
        }
    }
}
```

### 3. Exception Propagation

**Problem**: Exception in one observer stops notification of others.

**Solution**: Catch and handle exceptions for each observer individually.

## Real-World Examples

### 1. Model-View-Controller (MVC)

```java
// Model
class UserModel extends Subject<UserModel> {
    private String name;
    private String email;

    public void setName(String name) {
        this.name = name;
        notifyObservers(this);
    }

    public void setEmail(String email) {
        this.email = email;
        notifyObservers(this);
    }

    // Getters...
}

// View
class UserView implements Observer<UserModel> {
    @Override
    public void update(UserModel user) {
        refreshDisplay(user);
    }

    private void refreshDisplay(UserModel user) {
        // Update UI components
    }
}
```

### 2. Document-Editor System

```java
class Document extends Subject<DocumentEvent> {
    private String content;

    public void insertText(int position, String text) {
        content = content.substring(0, position) + text + content.substring(position);
        notifyObservers(new DocumentEvent("INSERT", position, text));
    }

    public void deleteText(int start, int end) {
        String deleted = content.substring(start, end);
        content = content.substring(0, start) + content.substring(end);
        notifyObservers(new DocumentEvent("DELETE", start, deleted));
    }
}

class DocumentEvent {
    private final String type;
    private final int position;
    private final String text;

    // Constructor and getters...
}

class AutoSaver implements Observer<DocumentEvent> {
    @Override
    public void update(DocumentEvent event) {
        // Auto-save document
    }
}

class UndoManager implements Observer<DocumentEvent> {
    private final Stack<DocumentEvent> history = new Stack<>();

    @Override
    public void update(DocumentEvent event) {
        history.push(event);
    }
}
```

### 3. Stock Market System

```java
class Stock extends Subject<StockData> {
    private String symbol;
    private double price;
    private int volume;

    public void updatePrice(double newPrice, int volume) {
        this.price = newPrice;
        this.volume = volume;
        notifyObservers(new StockData(symbol, price, volume));
    }
}

class Portfolio implements Observer<StockData> {
    private final Map<String, Integer> holdings = new HashMap<>();

    @Override
    public void update(StockData stock) {
        if (holdings.containsKey(stock.getSymbol())) {
            calculatePortfolioValue();
        }
    }

    private void calculatePortfolioValue() {
        // Recalculate total portfolio value
    }
}

class AlertSystem implements Observer<StockData> {
    private final Map<String, Double> priceAlerts = new HashMap<>();

    @Override
    public void update(StockData stock) {
        Double alertPrice = priceAlerts.get(stock.getSymbol());
        if (alertPrice != null && stock.getPrice() >= alertPrice) {
            sendAlert(stock);
        }
    }

    private void sendAlert(StockData stock) {
        System.out.println("ALERT: " + stock.getSymbol() + " reached $" + stock.getPrice());
    }
}
```

## Performance Considerations

### 1. Large Number of Observers

For systems with many observers, consider:

-   Using separate thread pools for notifications
-   Implementing priority-based notification
-   Batching notifications

### 2. Frequent State Changes

-   Implement debouncing to reduce notification frequency
-   Use dirty flags to batch multiple changes
-   Consider using reactive streams for better backpressure handling

### 3. Memory Usage

-   Use weak references for observers that might not clean up properly
-   Implement observer lifecycle management
-   Monitor for memory leaks in long-running applications

## Conclusion

The Observer Pattern is a fundamental design pattern that enables loose coupling between subjects and observers. While Java's built-in Observable/Observer classes are deprecated, the pattern remains relevant and can be implemented in various modern ways depending on your specific needs. Consider factors like thread safety, memory management, and performance requirements when choosing your implementation approach.
