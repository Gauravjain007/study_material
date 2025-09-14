# Facade Pattern in Java - Complete Documentation

## Table of Contents

1. [Overview](#overview)
2. [Intent and Purpose](#intent-and-purpose)
3. [Structure and Components](#structure-and-components)
4. [Implementation Guidelines](#implementation-guidelines)
5. [Basic Example](#basic-example)
6. [Real-World Example: Home Theater System](#real-world-example-home-theater-system)
7. [Advanced Implementation](#advanced-implementation)
8. [Best Practices](#best-practices)
9. [Common Pitfalls](#common-pitfalls)
10. [When to Use](#when-to-use)
11. [Comparison with Other Patterns](#comparison-with-other-patterns)
12. [Testing Strategies](#testing-strategies)

## Overview

The Facade Pattern is a structural design pattern that provides a simplified interface to a complex subsystem. It acts as a front-facing interface that masks the more complex underlying or structural code, making it easier to use the subsystem.

**Pattern Type:** Structural Design Pattern  
**Difficulty Level:** Beginner to Intermediate  
**Gang of Four Classification:** Structural Pattern

## Intent and Purpose

### Primary Intent

-   **Simplify Complex Interfaces**: Provide a unified interface to a set of interfaces in a subsystem
-   **Reduce Dependencies**: Minimize coupling between clients and subsystem classes
-   **Improve Usability**: Make subsystems easier to use by providing a higher-level interface

### Key Benefits

-   **Simplified Client Code**: Clients interact with a single facade instead of multiple subsystem classes
-   **Loose Coupling**: Reduces dependencies between client code and subsystem implementation
-   **Layered Architecture**: Promotes clean separation between different layers of the application
-   **Backward Compatibility**: Changes to subsystem don't affect client code if facade interface remains stable

### Real-World Analogies

-   **Restaurant Waiter**: You don't interact directly with the kitchen, chef, or cashier - the waiter is your facade
-   **Computer Startup**: Pressing the power button triggers hundreds of complex operations behind the scenes
-   **ATM Machine**: Provides simple interface for complex banking operations

## Structure and Components

### UML Class Diagram Representation

```
Client --> Facade --> SubsystemA
              |  --> SubsystemB
              |  --> SubsystemC
```

### Core Components

1. **Facade Class**

    - Provides simplified methods that delegate to appropriate subsystem classes
    - Knows which subsystem classes are responsible for a request
    - May perform additional coordination logic

2. **Subsystem Classes**

    - Implement subsystem functionality
    - Handle work assigned by the facade object
    - Have no knowledge of the facade

3. **Client**
    - Uses the facade instead of calling subsystem objects directly

## Implementation Guidelines

### Design Principles

-   **Single Responsibility**: Facade should have one reason to change
-   **Interface Segregation**: Don't force clients to depend on methods they don't use
-   **Dependency Inversion**: Depend on abstractions, not concretions

### Implementation Steps

1. Identify the complex subsystem that needs simplification
2. Create a facade class that encapsulates the subsystem
3. Implement simplified methods that coordinate subsystem operations
4. Ensure the facade doesn't expose subsystem complexity
5. Make subsystem classes optional for direct access if needed

## Basic Example

Let's start with a simple example of a computer system facade:

```java
// Subsystem classes
class CPU {
    public void freeze() {
        System.out.println("CPU: Freezing processor");
    }

    public void jump(long position) {
        System.out.println("CPU: Jumping to position " + position);
    }

    public void execute() {
        System.out.println("CPU: Executing instructions");
    }
}

class Memory {
    public void load(long position, String data) {
        System.out.println("Memory: Loading data '" + data + "' at position " + position);
    }
}

class HardDrive {
    public String read(long lba, int size) {
        String data = "Boot data from sector " + lba;
        System.out.println("HardDrive: Reading " + size + " bytes: " + data);
        return data;
    }
}

// Facade class
class ComputerFacade {
    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;

    public ComputerFacade() {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = hardDrive();
    }

    public void start() {
        System.out.println("Starting computer...");
        cpu.freeze();
        memory.load(0, hardDrive.read(0, 1024));
        cpu.jump(0);
        cpu.execute();
        System.out.println("Computer started successfully!");
    }
}

// Client code
public class BasicFacadeExample {
    public static void main(String[] args) {
        ComputerFacade computer = new ComputerFacade();
        computer.start(); // Simple interface for complex operation
    }
}
```

## Real-World Example: Home Theater System

Here's a comprehensive example of a home theater system that demonstrates the Facade pattern in action:

```java
// Subsystem classes for home theater components
class Amplifier {
    private String description;

    public Amplifier(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + " on");
    }

    public void off() {
        System.out.println(description + " off");
    }

    public void setVolume(int level) {
        System.out.println(description + " setting volume to " + level);
    }

    public void setSurroundSound() {
        System.out.println(description + " surround sound on (5.1)");
    }
}

class DvdPlayer {
    private String description;

    public DvdPlayer(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + " on");
    }

    public void off() {
        System.out.println(description + " off");
    }

    public void play(String movie) {
        System.out.println(description + " playing \"" + movie + "\"");
    }

    public void stop() {
        System.out.println(description + " stopped");
    }

    public void eject() {
        System.out.println(description + " eject");
    }
}

class Projector {
    private String description;

    public Projector(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + " on");
    }

    public void off() {
        System.out.println(description + " off");
    }

    public void wideScreenMode() {
        System.out.println(description + " in widescreen mode (16x9 aspect ratio)");
    }
}

class Screen {
    private String description;

    public Screen(String description) {
        this.description = description;
    }

    public void up() {
        System.out.println(description + " going up");
    }

    public void down() {
        System.out.println(description + " going down");
    }
}

class PopcornPopper {
    private String description;

    public PopcornPopper(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + " on");
    }

    public void off() {
        System.out.println(description + " off");
    }

    public void pop() {
        System.out.println(description + " popping popcorn!");
    }
}

class TheaterLights {
    private String description;

    public TheaterLights(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + " on");
    }

    public void off() {
        System.out.println(description + " off");
    }

    public void dim(int level) {
        System.out.println(description + " dimming to " + level + "%");
    }
}

// Facade class that simplifies the home theater operations
class HomeTheaterFacade {
    private Amplifier amp;
    private DvdPlayer dvd;
    private Projector projector;
    private Screen screen;
    private PopcornPopper popper;
    private TheaterLights lights;

    public HomeTheaterFacade(Amplifier amp, DvdPlayer dvd,
                           Projector projector, Screen screen,
                           PopcornPopper popper, TheaterLights lights) {
        this.amp = amp;
        this.dvd = dvd;
        this.projector = projector;
        this.screen = screen;
        this.popper = popper;
        this.lights = lights;
    }

    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        popper.on();
        popper.pop();
        lights.dim(10);
        screen.down();
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setVolume(5);
        amp.setSurroundSound();
        dvd.on();
        dvd.play(movie);
        System.out.println("Movie experience started!\n");
    }

    public void endMovie() {
        System.out.println("Shutting movie theater down...");
        popper.off();
        lights.on();
        screen.up();
        projector.off();
        amp.off();
        dvd.stop();
        dvd.eject();
        dvd.off();
        System.out.println("Movie theater is now off.\n");
    }

    // Additional convenience methods
    public void listenToMusic(String album) {
        System.out.println("Get ready for some music...");
        lights.dim(30);
        amp.on();
        amp.setVolume(7);
        dvd.on();
        dvd.play(album);
        System.out.println("Music experience started!\n");
    }
}

// Client code
public class HomeTheaterExample {
    public static void main(String[] args) {
        // Create all the components
        Amplifier amp = new Amplifier("Top-O-Line Amplifier");
        DvdPlayer dvd = new DvdPlayer("Top-O-Line DVD Player");
        Projector projector = new Projector("Top-O-Line Projector");
        Screen screen = new Screen("Theater Screen");
        PopcornPopper popper = new PopcornPopper("Popcorn Popper");
        TheaterLights lights = new TheaterLights("Theater Ceiling Lights");

        // Create the facade
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(
            amp, dvd, projector, screen, popper, lights
        );

        // Use the simplified interface
        homeTheater.watchMovie("Raiders of the Lost Ark");
        homeTheater.endMovie();

        homeTheater.listenToMusic("The Dark Side of the Moon");
    }
}
```

## Advanced Implementation

### Interface-Based Facade with Dependency Injection

```java
// Define interfaces for better testability and flexibility
interface MediaPlayer {
    void on();
    void off();
    void play(String content);
    void stop();
}

interface AudioSystem {
    void on();
    void off();
    void setVolume(int level);
    void setSurroundSound();
}

interface DisplaySystem {
    void on();
    void off();
    void setDisplayMode(String mode);
}

// Concrete implementations
class AdvancedDvdPlayer implements MediaPlayer {
    @Override
    public void on() { System.out.println("DVD Player powered on"); }

    @Override
    public void off() { System.out.println("DVD Player powered off"); }

    @Override
    public void play(String content) {
        System.out.println("Playing: " + content);
    }

    @Override
    public void stop() { System.out.println("DVD Player stopped"); }
}

class SurroundSoundSystem implements AudioSystem {
    @Override
    public void on() { System.out.println("Sound system activated"); }

    @Override
    public void off() { System.out.println("Sound system deactivated"); }

    @Override
    public void setVolume(int level) {
        System.out.println("Volume set to " + level);
    }

    @Override
    public void setSurroundSound() {
        System.out.println("7.1 Surround sound enabled");
    }
}

class SmartProjector implements DisplaySystem {
    @Override
    public void on() { System.out.println("Projector warming up"); }

    @Override
    public void off() { System.out.println("Projector cooling down"); }

    @Override
    public void setDisplayMode(String mode) {
        System.out.println("Display mode set to: " + mode);
    }
}

// Advanced facade with dependency injection
class AdvancedHomeTheaterFacade {
    private final MediaPlayer mediaPlayer;
    private final AudioSystem audioSystem;
    private final DisplaySystem displaySystem;
    private final List<String> setupSequence;

    public AdvancedHomeTheaterFacade(MediaPlayer mediaPlayer,
                                   AudioSystem audioSystem,
                                   DisplaySystem displaySystem) {
        this.mediaPlayer = mediaPlayer;
        this.audioSystem = audioSystem;
        this.displaySystem = displaySystem;
        this.setupSequence = new ArrayList<>();
    }

    public void startMovieExperience(String movie, String displayMode) {
        System.out.println("=== Starting Movie Experience ===");

        // Record setup sequence for potential rollback
        executeAndRecord(() -> displaySystem.on(), "Display system startup");
        executeAndRecord(() -> displaySystem.setDisplayMode(displayMode),
                        "Display mode configuration");
        executeAndRecord(() -> audioSystem.on(), "Audio system startup");
        executeAndRecord(() -> audioSystem.setSurroundSound(),
                        "Surround sound configuration");
        executeAndRecord(() -> audioSystem.setVolume(6), "Volume setting");
        executeAndRecord(() -> mediaPlayer.on(), "Media player startup");
        executeAndRecord(() -> mediaPlayer.play(movie), "Content playback");

        System.out.println("=== Movie Experience Ready ===\n");
    }

    public void endMovieExperience() {
        System.out.println("=== Ending Movie Experience ===");

        mediaPlayer.stop();
        mediaPlayer.off();
        audioSystem.off();
        displaySystem.off();
        setupSequence.clear();

        System.out.println("=== All Systems Shutdown ===\n");
    }

    private void executeAndRecord(Runnable action, String description) {
        try {
            action.run();
            setupSequence.add(description + " - SUCCESS");
        } catch (Exception e) {
            setupSequence.add(description + " - FAILED: " + e.getMessage());
            throw new RuntimeException("Setup failed at: " + description, e);
        }
    }

    public List<String> getSetupSequence() {
        return new ArrayList<>(setupSequence);
    }
}

// Usage example with dependency injection
public class AdvancedFacadeExample {
    public static void main(String[] args) {
        // Dependency injection (could be done with a DI framework)
        MediaPlayer dvdPlayer = new AdvancedDvdPlayer();
        AudioSystem soundSystem = new SurroundSoundSystem();
        DisplaySystem projector = new SmartProjector();

        AdvancedHomeTheaterFacade theater = new AdvancedHomeTheaterFacade(
            dvdPlayer, soundSystem, projector
        );

        // Simple client interface
        theater.startMovieExperience("Inception", "IMAX");

        // Optional: View setup sequence
        System.out.println("Setup sequence:");
        theater.getSetupSequence().forEach(System.out::println);

        theater.endMovieExperience();
    }
}
```

## Best Practices

### 1. Keep the Facade Simple

```java
// Good: Simple, focused methods
public class PaymentFacade {
    public PaymentResult processPayment(PaymentRequest request) {
        // Coordinate subsystems without exposing complexity
        return paymentProcessor.process(request);
    }
}

// Avoid: Exposing subsystem complexity
public class BadPaymentFacade {
    public CreditCardValidator getValidator() { return validator; }
    public BankingService getBankingService() { return bankingService; }
    // This defeats the purpose of the facade
}
```

### 2. Use Composition Over Inheritance

```java
// Good: Composition-based facade
public class ShoppingCartFacade {
    private final InventoryService inventory;
    private final PricingService pricing;
    private final ShippingService shipping;

    // Constructor injection
    public ShoppingCartFacade(InventoryService inventory,
                             PricingService pricing,
                             ShippingService shipping) {
        this.inventory = inventory;
        this.pricing = pricing;
        this.shipping = shipping;
    }
}
```

### 3. Implement Proper Error Handling

```java
public class OrderProcessingFacade {
    public OrderResult processOrder(Order order) {
        try {
            // Validate order
            ValidationResult validation = validator.validate(order);
            if (!validation.isValid()) {
                return OrderResult.failure(validation.getErrors());
            }

            // Process payment
            PaymentResult payment = paymentService.charge(order.getPayment());
            if (!payment.isSuccessful()) {
                return OrderResult.failure("Payment failed: " + payment.getError());
            }

            // Update inventory
            inventory.reserve(order.getItems());

            // Schedule shipping
            ShippingResult shipping = shippingService.schedule(order);

            return OrderResult.success(shipping.getTrackingNumber());

        } catch (Exception e) {
            // Log the error and return a user-friendly message
            logger.error("Order processing failed", e);
            return OrderResult.failure("Unable to process order. Please try again.");
        }
    }
}
```

### 4. Make the Facade Stateless When Possible

```java
// Good: Stateless facade
@Component
public class StatelessEmailFacade {
    private final EmailService emailService;
    private final TemplateService templateService;
    private final ValidationService validationService;

    public void sendWelcomeEmail(User user) {
        // All state is passed as parameters
        String template = templateService.getWelcomeTemplate();
        String content = templateService.render(template, user);
        emailService.send(user.getEmail(), "Welcome!", content);
    }
}
```

### 5. Provide Both Simple and Advanced Interfaces

```java
public class DatabaseFacade {
    // Simple interface for common use cases
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Advanced interface for complex scenarios
    public List<User> findUsers(UserSearchCriteria criteria) {
        QueryBuilder query = QueryBuilder.create()
            .withCriteria(criteria)
            .withPaging(criteria.getPaging())
            .withSorting(criteria.getSorting());

        return userRepository.findByCriteria(query.build());
    }
}
```

## Common Pitfalls

### 1. Creating a "God" Facade

```java
// Bad: Facade doing too much
public class SystemFacade {
    public void handleUserManagement() { /* ... */ }
    public void handlePayments() { /* ... */ }
    public void handleInventory() { /* ... */ }
    public void handleShipping() { /* ... */ }
    public void handleReporting() { /* ... */ }
    // This facade has too many responsibilities
}

// Good: Focused facades
public class UserManagementFacade { /* ... */ }
public class PaymentFacade { /* ... */ }
public class InventoryFacade { /* ... */ }
```

### 2. Tight Coupling to Concrete Classes

```java
// Bad: Tightly coupled to concrete implementations
public class BadFacade {
    private ConcreteServiceA serviceA = new ConcreteServiceA();
    private ConcreteServiceB serviceB = new ConcreteServiceB();
}

// Good: Depend on abstractions
public class GoodFacade {
    private final ServiceA serviceA;
    private final ServiceB serviceB;

    public GoodFacade(ServiceA serviceA, ServiceB serviceB) {
        this.serviceA = serviceA;
        this.serviceB = serviceB;
    }
}
```

### 3. Exposing Internal State

```java
// Bad: Exposing internal components
public class BadFacade {
    private DatabaseService database;

    public DatabaseService getDatabase() {
        return database; // Breaks encapsulation
    }
}

// Good: Keep internals hidden
public class GoodFacade {
    private DatabaseService database;

    public UserData getUserData(String userId) {
        return database.findUser(userId); // Expose behavior, not structure
    }
}
```

## When to Use

### Use the Facade Pattern When:

-   **Complex Subsystems**: You have a complex subsystem that's difficult for clients to use directly
-   **Multiple Dependencies**: Client code needs to interact with many different classes
-   **Layered Architecture**: You want to create clean layers in your application
-   **Legacy Integration**: You need to wrap legacy code with a modern interface
-   **Third-Party Libraries**: You want to simplify or standardize access to external libraries

### Don't Use the Facade Pattern When:

-   **Simple Subsystems**: The subsystem is already simple and easy to use
-   **Performance Critical**: The additional layer introduces unacceptable performance overhead
-   **Frequent Changes**: The facade interface changes frequently, defeating the purpose
-   **Over-Engineering**: You're adding complexity without clear benefits

### Decision Matrix

| Scenario                    | Use Facade? | Reason                                                       |
| --------------------------- | ----------- | ------------------------------------------------------------ |
| E-commerce checkout process | ✅ Yes      | Coordinates multiple services (payment, inventory, shipping) |
| Simple CRUD operations      | ❌ No       | Already simple, facade adds unnecessary complexity           |
| Legacy system integration   | ✅ Yes      | Provides modern interface for old systems                    |
| Single service call         | ❌ No       | No complexity to hide                                        |
| Multi-step business process | ✅ Yes      | Simplifies workflow coordination                             |

## Comparison with Other Patterns

### Facade vs Adapter

```java
// Adapter: Changes interface of a single class
class LegacyPrinter {
    public void oldPrintMethod(String text) { /* ... */ }
}

class PrinterAdapter implements ModernPrinter {
    private LegacyPrinter legacyPrinter;

    @Override
    public void print(Document doc) {
        legacyPrinter.oldPrintMethod(doc.getText()); // Adapts interface
    }
}

// Facade: Simplifies interface to multiple classes
class DocumentProcessingFacade {
    private Scanner scanner;
    private Printer printer;
    private EmailService email;

    public void processDocument(String filePath) {
        Document doc = scanner.scan(filePath);
        printer.print(doc);
        email.send(doc.getRecipient(), doc);
    }
}
```

### Facade vs Mediator

```java
// Mediator: Defines how objects interact
interface ChatMediator {
    void sendMessage(String message, User user);
}

class ChatRoom implements ChatMediator {
    private List<User> users = new ArrayList<>();

    @Override
    public void sendMessage(String message, User sender) {
        users.stream()
             .filter(user -> !user.equals(sender))
             .forEach(user -> user.receive(message));
    }
}

// Facade: Provides simplified interface
class ChatFacade {
    private UserManager userManager;
    private MessageService messageService;
    private NotificationService notificationService;

    public void sendMessage(String userId, String message) {
        User user = userManager.getUser(userId);
        messageService.send(message, user);
        notificationService.notify(user.getContacts(), message);
    }
}
```

## Testing Strategies

### 1. Unit Testing with Mocks

```java
@ExtendWith(MockitoExtension.class)
class HomeTheaterFacadeTest {

    @Mock private Amplifier amplifier;
    @Mock private DvdPlayer dvdPlayer;
    @Mock private Projector projector;

    @InjectMocks
    private HomeTheaterFacade facade;

    @Test
    void testWatchMovie_CallsAllSubsystems() {
        // Given
        String movie = "Test Movie";

        // When
        facade.watchMovie(movie);

        // Then
        verify(amplifier).on();
        verify(amplifier).setVolume(anyInt());
        verify(dvdPlayer).on();
        verify(dvdPlayer).play(movie);
        verify(projector).on();
    }

    @Test
    void testWatchMovie_HandlesSubsystemFailure() {
        // Given
        doThrow(new RuntimeException("Projector failed"))
            .when(projector).on();

        // When/Then
        assertThrows(RuntimeException.class, () -> facade.watchMovie("Test"));
    }
}
```

### 2. Integration Testing

```java
@SpringBootTest
@TestPropertySource(properties = {
    "subsystem.timeout=1000",
    "subsystem.retries=3"
})
class PaymentFacadeIntegrationTest {

    @Autowired
    private PaymentFacade paymentFacade;

    @Test
    void testPaymentProcessing_EndToEnd() {
        // Given
        PaymentRequest request = PaymentRequest.builder()
            .amount(new BigDecimal("99.99"))
            .cardNumber("4111111111111111")
            .build();

        // When
        PaymentResult result = paymentFacade.processPayment(request);

        // Then
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getTransactionId()).isNotNull();
    }
}
```

### 3. Testing Facade Behavior

```java
class OrderProcessingFacadeTest {

    @Test
    void testOrderProcessing_SuccessfulFlow() {
        // Test the successful path through all subsystems
        Order order = createTestOrder();

        OrderResult result = facade.processOrder(order);

        assertThat(result.isSuccessful()).isTrue();
        // Verify the facade coordinated all subsystems correctly
    }

    @Test
    void testOrderProcessing_RollbackOnFailure() {
        // Test that facade handles failures and performs rollback
        Order order = createInvalidOrder();

        OrderResult result = facade.processOrder(order);

        assertThat(result.isSuccessful()).isFalse();
        // Verify rollback was performed
    }
}
```

## Conclusion

The Facade Pattern is a powerful tool for simplifying complex subsystems and creating clean, maintainable code. By providing a unified interface to multiple subsystem classes, it reduces coupling, improves usability, and creates natural boundaries in your application architecture.

Key takeaways:

-   Use facades to hide complexity, not to add it
-   Keep facades focused and avoid creating "god" objects
-   Design for testability with dependency injection
-   Provide appropriate error handling and logging
-   Consider both simple and advanced interfaces for different use cases

The pattern is particularly valuable in enterprise applications, API design, and when integrating with legacy systems or third-party libraries. When implemented correctly, it significantly improves code maintainability and developer experience.
