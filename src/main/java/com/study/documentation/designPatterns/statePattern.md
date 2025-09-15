# State Pattern in Java - Complete Documentation

## Table of Contents

1. [Overview](#overview)
2. [Problem Statement](#problem-statement)
3. [Solution](#solution)
4. [Structure](#structure)
5. [Implementation](#implementation)
6. [Real-World Examples](#real-world-examples)
7. [Advantages and Disadvantages](#advantages-and-disadvantages)
8. [Best Practices](#best-practices)
9. [Common Pitfalls](#common-pitfalls)
10. [Related Patterns](#related-patterns)

## Overview

The State Pattern is a behavioral design pattern that allows an object to alter its behavior when its internal state changes. The object appears to change its class by delegating state-specific behavior to separate state objects.

### Intent

-   Allow an object to alter its behavior when its internal state changes
-   Encapsulate state-specific behavior in separate classes
-   Eliminate complex conditional statements based on object state
-   Make state transitions explicit

### Classification

-   **Type**: Behavioral Pattern
-   **Complexity**: Medium
-   **Popularity**: High in state machine implementations

## Problem Statement

Consider scenarios where an object's behavior depends heavily on its current state:

### Common Problems

1. **Complex Conditional Logic**: Large switch/if-else statements based on state
2. **State Transition Management**: Difficult to manage state changes
3. **Code Duplication**: Similar state checks scattered throughout the code
4. **Poor Maintainability**: Adding new states requires modifying existing code

### Example Problem

```java
// Problematic approach without State Pattern
public class VendingMachine {
    private enum State { NO_COIN, HAS_COIN, SOLD, SOLD_OUT }
    private State currentState = State.NO_COIN;
    private int count = 0;

    public void insertCoin() {
        if (currentState == State.NO_COIN) {
            currentState = State.HAS_COIN;
            System.out.println("Coin inserted");
        } else if (currentState == State.HAS_COIN) {
            System.out.println("Coin already inserted");
        } else if (currentState == State.SOLD) {
            System.out.println("Please wait, dispensing item");
        } else if (currentState == State.SOLD_OUT) {
            System.out.println("Machine sold out");
        }
    }

    // Similar complex conditional logic for other methods...
}
```

## Solution

The State Pattern solves this by:

1. Creating a separate class for each state
2. Delegating state-specific behavior to these classes
3. Maintaining a reference to the current state object
4. Allowing states to transition to other states

## Structure

### UML Class Diagram Components

1. **Context**: Maintains reference to current state and delegates requests
2. **State Interface**: Defines interface for state-specific behavior
3. **Concrete States**: Implement behavior associated with a specific state

### Participants

-   **Context (VendingMachine)**: Maintains current state and delegates operations
-   **State (VendingMachineState)**: Interface for encapsulating state behavior
-   **ConcreteState (NoCoinState, HasCoinState, etc.)**: Implements state-specific behavior

## Implementation

### Basic State Pattern Implementation

#### 1. State Interface

```java
/**
 * State interface defining common operations for all states
 */
public interface VendingMachineState {
    void insertCoin(VendingMachine context);
    void ejectCoin(VendingMachine context);
    void selectProduct(VendingMachine context);
    void dispense(VendingMachine context);
    String getDescription();
}
```

#### 2. Context Class

```java
/**
 * Context class that maintains current state and delegates operations
 */
public class VendingMachine {
    private VendingMachineState noCoinState;
    private VendingMachineState hasCoinState;
    private VendingMachineState soldState;
    private VendingMachineState soldOutState;

    private VendingMachineState currentState;
    private int productCount;

    public VendingMachine(int productCount) {
        // Initialize all states
        noCoinState = new NoCoinState();
        hasCoinState = new HasCoinState();
        soldState = new SoldState();
        soldOutState = new SoldOutState();

        this.productCount = productCount;
        this.currentState = productCount > 0 ? noCoinState : soldOutState;
    }

    // Delegate operations to current state
    public void insertCoin() {
        currentState.insertCoin(this);
    }

    public void ejectCoin() {
        currentState.ejectCoin(this);
    }

    public void selectProduct() {
        currentState.selectProduct(this);
    }

    public void dispense() {
        currentState.dispense(this);
    }

    // State transition methods
    public void setState(VendingMachineState state) {
        this.currentState = state;
        System.out.println("State changed to: " + state.getDescription());
    }

    // Getters for states
    public VendingMachineState getNoCoinState() { return noCoinState; }
    public VendingMachineState getHasCoinState() { return hasCoinState; }
    public VendingMachineState getSoldState() { return soldState; }
    public VendingMachineState getSoldOutState() { return soldOutState; }

    // Product management
    public void releaseProduct() {
        if (productCount > 0) {
            productCount--;
            System.out.println("Product dispensed. Remaining: " + productCount);
        }
    }

    public int getProductCount() { return productCount; }

    public String getCurrentState() {
        return currentState.getDescription();
    }
}
```

#### 3. Concrete State Classes

```java
/**
 * State when no coin is inserted
 */
public class NoCoinState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine context) {
        System.out.println("Coin inserted successfully");
        context.setState(context.getHasCoinState());
    }

    @Override
    public void ejectCoin(VendingMachine context) {
        System.out.println("No coin to eject");
    }

    @Override
    public void selectProduct(VendingMachine context) {
        System.out.println("Please insert a coin first");
    }

    @Override
    public void dispense(VendingMachine context) {
        System.out.println("Please insert coin and select product");
    }

    @Override
    public String getDescription() {
        return "Waiting for coin";
    }
}

/**
 * State when coin is inserted but product not selected
 */
public class HasCoinState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine context) {
        System.out.println("Coin already inserted");
    }

    @Override
    public void ejectCoin(VendingMachine context) {
        System.out.println("Coin ejected");
        context.setState(context.getNoCoinState());
    }

    @Override
    public void selectProduct(VendingMachine context) {
        System.out.println("Product selected");
        context.setState(context.getSoldState());
    }

    @Override
    public void dispense(VendingMachine context) {
        System.out.println("Please select a product first");
    }

    @Override
    public String getDescription() {
        return "Coin inserted - Select product";
    }
}

/**
 * State during product dispensing
 */
public class SoldState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine context) {
        System.out.println("Please wait, dispensing product");
    }

    @Override
    public void ejectCoin(VendingMachine context) {
        System.out.println("Cannot eject coin, product being dispensed");
    }

    @Override
    public void selectProduct(VendingMachine context) {
        System.out.println("Product already selected, dispensing...");
    }

    @Override
    public void dispense(VendingMachine context) {
        context.releaseProduct();

        if (context.getProductCount() > 0) {
            context.setState(context.getNoCoinState());
        } else {
            System.out.println("Machine sold out!");
            context.setState(context.getSoldOutState());
        }
    }

    @Override
    public String getDescription() {
        return "Dispensing product";
    }
}

/**
 * State when machine is sold out
 */
public class SoldOutState implements VendingMachineState {

    @Override
    public void insertCoin(VendingMachine context) {
        System.out.println("Machine sold out - coin ejected");
    }

    @Override
    public void ejectCoin(VendingMachine context) {
        System.out.println("No coin inserted");
    }

    @Override
    public void selectProduct(VendingMachine context) {
        System.out.println("Machine sold out");
    }

    @Override
    public void dispense(VendingMachine context) {
        System.out.println("Machine sold out");
    }

    @Override
    public String getDescription() {
        return "Sold out";
    }
}
```

### Advanced Implementation with State Transitions

```java
/**
 * Enhanced state interface with entry and exit actions
 */
public interface EnhancedState {
    void enterState(StateMachine context);
    void exitState(StateMachine context);
    void handleEvent(StateMachine context, String event, Object data);
    String getStateName();
}

/**
 * Abstract base state with common functionality
 */
public abstract class BaseState implements EnhancedState {

    @Override
    public void enterState(StateMachine context) {
        System.out.println("Entering state: " + getStateName());
    }

    @Override
    public void exitState(StateMachine context) {
        System.out.println("Exiting state: " + getStateName());
    }

    protected void transitionTo(StateMachine context, EnhancedState newState) {
        this.exitState(context);
        context.setState(newState);
        newState.enterState(context);
    }

    protected void handleInvalidEvent(String event) {
        System.out.println("Event '" + event + "' not valid in state: " + getStateName());
    }
}

/**
 * Enhanced context with state history and event handling
 */
public class StateMachine {
    private EnhancedState currentState;
    private List<EnhancedState> stateHistory;
    private Map<String, EnhancedState> states;

    public StateMachine() {
        stateHistory = new ArrayList<>();
        states = new HashMap<>();
        registerStates();
    }

    private void registerStates() {
        // Register all available states
        states.put("IDLE", new IdleState());
        states.put("PROCESSING", new ProcessingState());
        states.put("COMPLETED", new CompletedState());
        states.put("ERROR", new ErrorState());
    }

    public void setState(EnhancedState newState) {
        if (currentState != null) {
            stateHistory.add(currentState);
        }
        this.currentState = newState;
    }

    public void handleEvent(String event, Object data) {
        if (currentState != null) {
            currentState.handleEvent(this, event, data);
        }
    }

    public EnhancedState getState(String stateName) {
        return states.get(stateName);
    }

    public String getCurrentStateName() {
        return currentState != null ? currentState.getStateName() : "NONE";
    }

    public List<String> getStateHistory() {
        return stateHistory.stream()
                .map(EnhancedState::getStateName)
                .collect(Collectors.toList());
    }
}
```

## Real-World Examples

### 1. TCP Connection State Machine

```java
/**
 * TCP Connection state management using State Pattern
 */
public interface TCPState {
    void open(TCPConnection connection);
    void close(TCPConnection connection);
    void acknowledge(TCPConnection connection);
    String getStateName();
}

public class TCPConnection {
    private TCPState closedState = new ClosedState();
    private TCPState listenState = new ListenState();
    private TCPState establishedState = new EstablishedState();

    private TCPState currentState;

    public TCPConnection() {
        currentState = closedState;
    }

    public void open() { currentState.open(this); }
    public void close() { currentState.close(this); }
    public void acknowledge() { currentState.acknowledge(this); }

    public void setState(TCPState state) {
        System.out.println("TCP State: " + currentState.getStateName() +
                          " -> " + state.getStateName());
        this.currentState = state;
    }

    // State getters
    public TCPState getClosedState() { return closedState; }
    public TCPState getListenState() { return listenState; }
    public TCPState getEstablishedState() { return establishedState; }
}

public class ClosedState implements TCPState {
    @Override
    public void open(TCPConnection connection) {
        connection.setState(connection.getListenState());
    }

    @Override
    public void close(TCPConnection connection) {
        System.out.println("Connection already closed");
    }

    @Override
    public void acknowledge(TCPConnection connection) {
        System.out.println("Cannot acknowledge - connection closed");
    }

    @Override
    public String getStateName() { return "CLOSED"; }
}

public class ListenState implements TCPState {
    @Override
    public void open(TCPConnection connection) {
        System.out.println("Connection already opening");
    }

    @Override
    public void close(TCPConnection connection) {
        connection.setState(connection.getClosedState());
    }

    @Override
    public void acknowledge(TCPConnection connection) {
        connection.setState(connection.getEstablishedState());
    }

    @Override
    public String getStateName() { return "LISTEN"; }
}
```

### 2. Document State Management

```java
/**
 * Document lifecycle management
 */
public interface DocumentState {
    void edit(Document document);
    void review(Document document);
    void publish(Document document);
    void archive(Document document);
    String getStatus();
}

public class Document {
    private DocumentState draftState = new DraftState();
    private DocumentState reviewState = new ReviewState();
    private DocumentState publishedState = new PublishedState();
    private DocumentState archivedState = new ArchivedState();

    private DocumentState currentState;
    private String content;
    private LocalDateTime lastModified;

    public Document(String content) {
        this.content = content;
        this.currentState = draftState;
        this.lastModified = LocalDateTime.now();
    }

    // Delegate operations to current state
    public void edit() {
        currentState.edit(this);
        updateModificationTime();
    }

    public void review() { currentState.review(this); }
    public void publish() { currentState.publish(this); }
    public void archive() { currentState.archive(this); }

    public void setState(DocumentState state) {
        this.currentState = state;
        System.out.println("Document status: " + state.getStatus());
    }

    private void updateModificationTime() {
        this.lastModified = LocalDateTime.now();
    }

    // Getters for states and properties
    public DocumentState getDraftState() { return draftState; }
    public DocumentState getReviewState() { return reviewState; }
    public DocumentState getPublishedState() { return publishedState; }
    public DocumentState getArchivedState() { return archivedState; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getLastModified() { return lastModified; }
}
```

## Advantages and Disadvantages

### Advantages

1. **Single Responsibility Principle**: Each state class handles one specific state
2. **Open/Closed Principle**: Easy to add new states without modifying existing code
3. **Eliminates Conditional Logic**: No more complex if-else or switch statements
4. **Clear State Transitions**: State changes are explicit and traceable
5. **Improved Maintainability**: State-specific code is isolated and organized
6. **Polymorphism**: Leverages object-oriented principles effectively

### Disadvantages

1. **Increased Complexity**: More classes and objects to manage
2. **Memory Overhead**: Each state might be a separate object
3. **Overkill for Simple Cases**: May be excessive for simple state machines
4. **State Explosion**: Can become unwieldy with many states
5. **Circular Dependencies**: Context and states reference each other

## Best Practices

### 1. State Instance Management

```java
// Singleton states (when states are stateless)
public class StateManager {
    private static final NoCoinState NO_COIN_STATE = new NoCoinState();
    private static final HasCoinState HAS_COIN_STATE = new HasCoinState();

    public static NoCoinState getNoCoinState() { return NO_COIN_STATE; }
    public static HasCoinState getHasCoinState() { return HAS_COIN_STATE; }
}
```

### 2. State Validation

```java
public abstract class ValidatedState implements State {
    protected void validateTransition(Context context, State newState) {
        Set<State> allowedStates = getAllowedTransitions();
        if (!allowedStates.contains(newState)) {
            throw new IllegalStateException(
                "Invalid transition from " + this.getClass().getSimpleName() +
                " to " + newState.getClass().getSimpleName());
        }
    }

    protected abstract Set<State> getAllowedTransitions();
}
```

### 3. Event-Driven State Machine

```java
public interface Event {
    String getType();
    Object getData();
    LocalDateTime getTimestamp();
}

public interface EventDrivenState {
    void handleEvent(Context context, Event event);
    boolean canHandle(Event event);
}
```

### 4. State Machine Configuration

```java
@Component
public class StateMachineConfig {

    @Bean
    public StateMachine createOrderStateMachine() {
        return StateMachineBuilder.newBuilder()
            .state("PENDING")
                .onEntry(this::logStateEntry)
                .onExit(this::logStateExit)
                .permit("APPROVE", "APPROVED")
                .permit("REJECT", "REJECTED")
            .state("APPROVED")
                .permit("SHIP", "SHIPPED")
                .permit("CANCEL", "CANCELLED")
            .state("SHIPPED")
                .permit("DELIVER", "DELIVERED")
            .state("DELIVERED")
                .permitReentry("RETURN")
            .build();
    }
}
```

## Common Pitfalls

### 1. Shared State Between State Objects

```java
// WRONG - States sharing mutable state
public class BadState implements State {
    private static int sharedCounter = 0; // Avoid this!

    @Override
    public void handle(Context context) {
        sharedCounter++; // States should be stateless
    }
}

// CORRECT - State-specific data in context
public class GoodState implements State {
    @Override
    public void handle(Context context) {
        context.incrementCounter(); // Context manages state data
    }
}
```

### 2. Tight Coupling Between States

```java
// WRONG - Direct state references
public class BadHasCoinState implements State {
    private NoCoinState noCoinState; // Tight coupling

    public void ejectCoin(Context context) {
        context.setState(noCoinState); // Direct reference
    }
}

// CORRECT - Use context methods
public class GoodHasCoinState implements State {
    public void ejectCoin(Context context) {
        context.setState(context.getNoCoinState()); // Through context
    }
}
```

### 3. Missing State Transition Validation

```java
public class ValidatingContext {
    private final Map<Class<? extends State>, Set<Class<? extends State>>>
        allowedTransitions = new HashMap<>();

    {
        allowedTransitions.put(NoCoinState.class,
            Set.of(HasCoinState.class, SoldOutState.class));
        allowedTransitions.put(HasCoinState.class,
            Set.of(NoCoinState.class, SoldState.class));
        // ... other transitions
    }

    public void setState(State newState) {
        Set<Class<? extends State>> allowed =
            allowedTransitions.get(currentState.getClass());

        if (allowed == null || !allowed.contains(newState.getClass())) {
            throw new IllegalStateException("Invalid state transition");
        }

        this.currentState = newState;
    }
}
```

## Related Patterns

### 1. Strategy Pattern

**Similarity**: Both use composition and delegation
**Difference**: Strategy focuses on algorithms, State focuses on behavior based on internal state

### 2. Command Pattern

**Integration**: Commands can trigger state transitions

```java
public class StateChangeCommand implements Command {
    private final Context context;
    private final State newState;

    @Override
    public void execute() {
        context.setState(newState);
    }
}
```

### 3. Observer Pattern

**Integration**: Notify observers when state changes

```java
public class ObservableContext extends Context {
    private List<StateObserver> observers = new ArrayList<>();

    @Override
    public void setState(State newState) {
        State oldState = getCurrentState();
        super.setState(newState);
        notifyObservers(oldState, newState);
    }
}
```

### 4. Template Method Pattern

**Integration**: Common state behavior in abstract base class

```java
public abstract class TemplateState implements State {

    public final void handle(Context context) {
        preHandle(context);
        doHandle(context);
        postHandle(context);
    }

    protected void preHandle(Context context) { /* default implementation */ }
    protected abstract void doHandle(Context context);
    protected void postHandle(Context context) { /* default implementation */ }
}
```

## Testing State Pattern

### Unit Testing States

```java
public class VendingMachineStateTest {

    private VendingMachine machine;
    private VendingMachineState noCoinState;

    @BeforeEach
    void setUp() {
        machine = new VendingMachine(5);
        noCoinState = machine.getNoCoinState();
    }

    @Test
    void testNoCoinStateInsertCoin() {
        // Given
        machine.setState(noCoinState);

        // When
        machine.insertCoin();

        // Then
        assertEquals("Coin inserted - Select product", machine.getCurrentState());
    }

    @Test
    void testStateTransitions() {
        // Test complete workflow
        machine.insertCoin();
        machine.selectProduct();
        machine.dispense();

        assertEquals("Waiting for coin", machine.getCurrentState());
        assertEquals(4, machine.getProductCount());
    }
}
```

### Integration Testing

```java
@SpringBootTest
public class StateMachineIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    void testOrderLifecycle() {
        // Create order
        Order order = orderService.createOrder();
        assertEquals(OrderState.PENDING, order.getState());

        // Process order
        orderService.approveOrder(order.getId());
        assertEquals(OrderState.APPROVED, order.getState());

        // Ship order
        orderService.shipOrder(order.getId());
        assertEquals(OrderState.SHIPPED, order.getState());

        // Deliver order
        orderService.deliverOrder(order.getId());
        assertEquals(OrderState.DELIVERED, order.getState());
    }
}
```

## Conclusion

The State Pattern is a powerful tool for managing complex state-dependent behavior. It promotes clean, maintainable code by:

-   Encapsulating state-specific behavior in separate classes
-   Making state transitions explicit and trackable
-   Eliminating complex conditional logic
-   Supporting the Open/Closed Principle for easy extension

Use the State Pattern when:

-   An object's behavior changes significantly based on its state
-   You have complex conditional statements based on object state
-   State transitions need to be explicit and controlled
-   You need to add new states without modifying existing code

The pattern works best with a moderate number of well-defined states and clear transition rules. For simple state management, consider whether the added complexity is justified by the benefits gained.
