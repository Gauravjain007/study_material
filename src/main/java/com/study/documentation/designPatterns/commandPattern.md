# Command Pattern in Java - Complete Documentation

## Table of Contents

1. [Overview](#overview)
2. [Pattern Structure](#pattern-structure)
3. [Core Components](#core-components)
4. [Basic Implementation](#basic-implementation)
5. [Advanced Examples](#advanced-examples)
6. [Real-World Applications](#real-world-applications)
7. [Benefits and Drawbacks](#benefits-and-drawbacks)
8. [Best Practices](#best-practices)
9. [Common Variations](#common-variations)

## Overview

The Command Pattern is a behavioral design pattern that encapsulates a request as an object, allowing you to parameterize clients with different requests, queue operations, log requests, and support undo operations.

### Intent

-   Decouple the object that invokes the operation from the object that performs it
-   Transform requests into stand-alone objects containing all information about the request
-   Enable parameterization of methods with different requests
-   Support queuing, logging, and undoable operations

### Problem Solved

The Command Pattern addresses scenarios where you need to:

-   Issue requests without knowing the receiver
-   Queue or schedule operations
-   Support undo/redo functionality
-   Log operations for auditing
-   Create macro operations (composite commands)

## Pattern Structure

```
Client → Invoker → Command → Receiver
```

The pattern follows a clear flow where the client creates concrete commands, the invoker stores and executes them, and commands delegate work to receivers.

## Core Components

### 1. Command Interface

Declares the execution interface that all concrete commands must implement.

### 2. Concrete Command

Implements the Command interface and defines the binding between a Receiver and an action.

### 3. Receiver

Contains the business logic and knows how to perform operations.

### 4. Invoker

Asks the command to carry out the request without knowing implementation details.

### 5. Client

Creates concrete command objects and sets their receivers.

## Basic Implementation

### Command Interface

```java
/**
 * Command interface defining the contract for all commands
 */
public interface Command {
    /**
     * Execute the command
     */
    void execute();

    /**
     * Undo the command (optional but commonly implemented)
     */
    default void undo() {
        throw new UnsupportedOperationException("Undo not supported");
    }
}
```

### Receiver Classes

```java
/**
 * Light class - acts as a receiver
 */
public class Light {
    private String location;
    private boolean isOn;

    public Light(String location) {
        this.location = location;
        this.isOn = false;
    }

    public void turnOn() {
        isOn = true;
        System.out.println(location + " light is ON");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(location + " light is OFF");
    }

    public boolean isOn() {
        return isOn;
    }

    public String getLocation() {
        return location;
    }
}

/**
 * Fan class - another receiver
 */
public class Fan {
    public enum Speed { OFF, LOW, MEDIUM, HIGH }

    private String location;
    private Speed speed;

    public Fan(String location) {
        this.location = location;
        this.speed = Speed.OFF;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
        System.out.println(location + " fan speed set to " + speed);
    }

    public Speed getSpeed() {
        return speed;
    }

    public String getLocation() {
        return location;
    }
}
```

### Concrete Commands

```java
/**
 * Command to turn light on
 */
public class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff();
    }
}

/**
 * Command to turn light off
 */
public class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn();
    }
}

/**
 * Command to set fan speed
 */
public class FanSpeedCommand implements Command {
    private Fan fan;
    private Fan.Speed newSpeed;
    private Fan.Speed previousSpeed;

    public FanSpeedCommand(Fan fan, Fan.Speed speed) {
        this.fan = fan;
        this.newSpeed = speed;
    }

    @Override
    public void execute() {
        previousSpeed = fan.getSpeed();
        fan.setSpeed(newSpeed);
    }

    @Override
    public void undo() {
        fan.setSpeed(previousSpeed);
    }
}

/**
 * Null Object Pattern implementation for Command
 */
public class NoCommand implements Command {
    @Override
    public void execute() {
        // Do nothing
    }
}
```

### Invoker (Remote Control)

```java
/**
 * Remote Control - acts as invoker
 */
public class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    private Command undoCommand;

    public RemoteControl() {
        onCommands = new Command[7]; // 7 slots
        offCommands = new Command[7];

        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonPressed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonPressed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    public void undoButtonPressed() {
        undoCommand.undo();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n------ Remote Control ------\n");
        for (int i = 0; i < onCommands.length; i++) {
            sb.append("[slot ").append(i).append("] ")
              .append(onCommands[i].getClass().getSimpleName())
              .append("    ")
              .append(offCommands[i].getClass().getSimpleName())
              .append("\n");
        }
        sb.append("[undo] ").append(undoCommand.getClass().getSimpleName());
        return sb.toString();
    }
}
```

### Client Usage

```java
public class RemoteControlTest {
    public static void main(String[] args) {
        // Create invoker
        RemoteControl remote = new RemoteControl();

        // Create receivers
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        Fan bedroomFan = new Fan("Bedroom");

        // Create commands
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

        FanSpeedCommand fanHigh = new FanSpeedCommand(bedroomFan, Fan.Speed.HIGH);
        FanSpeedCommand fanOff = new FanSpeedCommand(bedroomFan, Fan.Speed.OFF);

        // Set commands on remote
        remote.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remote.setCommand(1, kitchenLightOn, kitchenLightOff);
        remote.setCommand(2, fanHigh, fanOff);

        System.out.println(remote);

        // Test commands
        remote.onButtonPressed(0);  // Living Room light ON
        remote.offButtonPressed(0); // Living Room light OFF
        remote.undoButtonPressed(); // Undo (Living Room light ON)

        remote.onButtonPressed(2);  // Bedroom fan HIGH
        remote.undoButtonPressed(); // Undo (Bedroom fan OFF)
    }
}
```

## Advanced Examples

### Macro Commands (Composite Pattern Integration)

```java
/**
 * MacroCommand executes multiple commands as a single unit
 */
public class MacroCommand implements Command {
    private Command[] commands;

    public MacroCommand(Command[] commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        // Undo in reverse order
        for (int i = commands.length - 1; i >= 0; i--) {
            commands[i].undo();
        }
    }
}

// Usage example
public class MacroCommandExample {
    public static void main(String[] args) {
        Light light = new Light("Living Room");
        Fan fan = new Fan("Living Room");

        Command[] partyOn = {
            new LightOnCommand(light),
            new FanSpeedCommand(fan, Fan.Speed.HIGH)
        };

        Command[] partyOff = {
            new LightOffCommand(light),
            new FanSpeedCommand(fan, Fan.Speed.OFF)
        };

        MacroCommand partyOnMacro = new MacroCommand(partyOn);
        MacroCommand partyOffMacro = new MacroCommand(partyOff);

        RemoteControl remote = new RemoteControl();
        remote.setCommand(0, partyOnMacro, partyOffMacro);

        remote.onButtonPressed(0);  // Execute party mode
        remote.undoButtonPressed(); // Undo party mode
    }
}
```

### Command Queue System

```java
/**
 * Command queue for batch processing and scheduling
 */
public class CommandQueue {
    private Queue<Command> commands = new LinkedList<>();
    private List<Command> executedCommands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.offer(command);
    }

    public void executeAll() {
        while (!commands.isEmpty()) {
            Command command = commands.poll();
            command.execute();
            executedCommands.add(command);
        }
    }

    public void undoLast() {
        if (!executedCommands.isEmpty()) {
            Command lastCommand = executedCommands.remove(executedCommands.size() - 1);
            lastCommand.undo();
        }
    }

    public void undoAll() {
        // Undo in reverse order
        for (int i = executedCommands.size() - 1; i >= 0; i--) {
            executedCommands.get(i).undo();
        }
        executedCommands.clear();
    }

    public int getPendingCount() {
        return commands.size();
    }

    public int getExecutedCount() {
        return executedCommands.size();
    }
}
```

### Command with Parameters and Return Values

```java
/**
 * Advanced command interface supporting parameters and return values
 */
public interface ParameterizedCommand<T, R> {
    R execute(T parameter);
    void undo();
}

/**
 * Calculator command with parameters
 */
public class CalculatorCommand implements ParameterizedCommand<Double, Double> {
    public enum Operation { ADD, SUBTRACT, MULTIPLY, DIVIDE }

    private Operation operation;
    private double operand;
    private double previousValue;
    private Calculator calculator;

    public CalculatorCommand(Calculator calculator, Operation operation, double operand) {
        this.calculator = calculator;
        this.operation = operation;
        this.operand = operand;
    }

    @Override
    public Double execute(Double currentValue) {
        previousValue = currentValue;

        switch (operation) {
            case ADD:
                return calculator.add(currentValue, operand);
            case SUBTRACT:
                return calculator.subtract(currentValue, operand);
            case MULTIPLY:
                return calculator.multiply(currentValue, operand);
            case DIVIDE:
                return calculator.divide(currentValue, operand);
            default:
                return currentValue;
        }
    }

    @Override
    public void undo() {
        calculator.setValue(previousValue);
    }
}

/**
 * Simple calculator receiver
 */
public class Calculator {
    private double value = 0;

    public double add(double a, double b) {
        value = a + b;
        return value;
    }

    public double subtract(double a, double b) {
        value = a - b;
        return value;
    }

    public double multiply(double a, double b) {
        value = a * b;
        return value;
    }

    public double divide(double a, double b) {
        if (b != 0) {
            value = a / b;
        }
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
```

### Command Logging and Replay

```java
/**
 * Command logger for audit trails and replay functionality
 */
public class CommandLogger {
    private List<LogEntry> commandLog = new ArrayList<>();

    public void logCommand(Command command) {
        LogEntry entry = new LogEntry(command, System.currentTimeMillis());
        commandLog.add(entry);
        System.out.println("Logged: " + command.getClass().getSimpleName() +
                         " at " + new Date(entry.getTimestamp()));
    }

    public void replayCommands() {
        System.out.println("Replaying " + commandLog.size() + " commands...");
        for (LogEntry entry : commandLog) {
            entry.getCommand().execute();
        }
    }

    public void replayCommandsFromTime(long fromTimestamp) {
        System.out.println("Replaying commands from " + new Date(fromTimestamp));
        commandLog.stream()
                  .filter(entry -> entry.getTimestamp() >= fromTimestamp)
                  .forEach(entry -> entry.getCommand().execute());
    }

    public List<LogEntry> getCommandLog() {
        return new ArrayList<>(commandLog);
    }

    public void clearLog() {
        commandLog.clear();
    }

    /**
     * Inner class for log entries
     */
    public static class LogEntry {
        private Command command;
        private long timestamp;

        public LogEntry(Command command, long timestamp) {
            this.command = command;
            this.timestamp = timestamp;
        }

        public Command getCommand() { return command; }
        public long getTimestamp() { return timestamp; }
    }
}

/**
 * Enhanced invoker with logging capability
 */
public class LoggingRemoteControl extends RemoteControl {
    private CommandLogger logger = new CommandLogger();

    @Override
    public void onButtonPressed(int slot) {
        super.onButtonPressed(slot);
        logger.logCommand(onCommands[slot]);
    }

    @Override
    public void offButtonPressed(int slot) {
        super.offButtonPressed(slot);
        logger.logCommand(offCommands[slot]);
    }

    public void replayAll() {
        logger.replayCommands();
    }

    public CommandLogger getLogger() {
        return logger;
    }
}
```

## Real-World Applications

### 1. GUI Button Actions

```java
/**
 * GUI button command example
 */
public class ButtonCommand implements Command {
    private JButton button;
    private ActionListener action;

    public ButtonCommand(JButton button, ActionListener action) {
        this.button = button;
        this.action = action;
    }

    @Override
    public void execute() {
        action.actionPerformed(new ActionEvent(button,
                              ActionEvent.ACTION_PERFORMED,
                              button.getActionCommand()));
    }
}
```

### 2. Thread Pool Task Execution

```java
/**
 * Runnable command adapter
 */
public class RunnableCommand implements Command {
    private Runnable task;

    public RunnableCommand(Runnable task) {
        this.task = task;
    }

    @Override
    public void execute() {
        task.run();
    }
}

/**
 * Command-based thread pool
 */
public class CommandThreadPool {
    private ExecutorService executor;
    private Queue<Command> commandQueue;

    public CommandThreadPool(int threadCount) {
        executor = Executors.newFixedThreadPool(threadCount);
        commandQueue = new ConcurrentLinkedQueue<>();
    }

    public void submitCommand(Command command) {
        commandQueue.offer(command);
        executor.submit(() -> {
            Command cmd = commandQueue.poll();
            if (cmd != null) {
                cmd.execute();
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}
```

### 3. Database Transaction Commands

```java
/**
 * Database operation command
 */
public abstract class DatabaseCommand implements Command {
    protected Connection connection;
    protected String sql;

    public DatabaseCommand(Connection connection, String sql) {
        this.connection = connection;
        this.sql = sql;
    }

    @Override
    public abstract void execute();

    @Override
    public abstract void undo();
}

/**
 * Insert command with undo support
 */
public class InsertCommand extends DatabaseCommand {
    private Object[] parameters;
    private Long generatedId;

    public InsertCommand(Connection connection, String sql, Object[] parameters) {
        super(connection, sql);
        this.parameters = parameters;
    }

    @Override
    public void execute() {
        try (PreparedStatement stmt = connection.prepareStatement(sql,
                                     Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < parameters.length; i++) {
                stmt.setObject(i + 1, parameters[i]);
            }
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Insert failed", e);
        }
    }

    @Override
    public void undo() {
        if (generatedId != null) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM table_name WHERE id = ?")) {
                stmt.setLong(1, generatedId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Undo failed", e);
            }
        }
    }
}
```

## Benefits and Drawbacks

### Benefits

1. **Decoupling**: Separates the object that invokes operations from the one that performs them
2. **Flexibility**: Easy to add new commands without changing existing code
3. **Undo/Redo Support**: Natural support for undoable operations
4. **Macro Operations**: Combine multiple commands into composite commands
5. **Queuing and Scheduling**: Commands can be queued, scheduled, or logged
6. **Remote Execution**: Commands can be transmitted over networks
7. **Transaction Support**: Can be used to implement transactional behavior

### Drawbacks

1. **Code Complexity**: Increases the number of classes in the system
2. **Memory Overhead**: Each command is a separate object
3. **Performance**: May introduce slight performance overhead
4. **Over-engineering**: Can be overkill for simple operations

## Best Practices

### 1. Use Null Object Pattern

Always provide a null command to avoid null checks:

```java
public class NoCommand implements Command {
    @Override
    public void execute() {
        // Do nothing
    }
}
```

### 2. Implement Proper Undo Support

Store necessary state for undo operations:

```java
public class StateAwareCommand implements Command {
    private Object previousState;

    @Override
    public void execute() {
        previousState = receiver.getState();
        receiver.performAction();
    }

    @Override
    public void undo() {
        receiver.setState(previousState);
    }
}
```

### 3. Use Builder Pattern for Complex Commands

```java
public class ComplexCommandBuilder {
    private Receiver receiver;
    private List<Parameter> parameters = new ArrayList<>();

    public ComplexCommandBuilder setReceiver(Receiver receiver) {
        this.receiver = receiver;
        return this;
    }

    public ComplexCommandBuilder addParameter(String key, Object value) {
        parameters.add(new Parameter(key, value));
        return this;
    }

    public Command build() {
        return new ComplexCommand(receiver, parameters);
    }
}
```

### 4. Exception Handling

Implement proper exception handling in commands:

```java
public class SafeCommand implements Command {
    private Command actualCommand;

    public SafeCommand(Command actualCommand) {
        this.actualCommand = actualCommand;
    }

    @Override
    public void execute() {
        try {
            actualCommand.execute();
        } catch (Exception e) {
            // Log error and handle gracefully
            System.err.println("Command execution failed: " + e.getMessage());
            // Optionally rethrow or handle
        }
    }
}
```

## Common Variations

### 1. Functional Interface Approach (Java 8+)

```java
@FunctionalInterface
public interface FunctionalCommand {
    void execute();

    default void undo() {
        throw new UnsupportedOperationException("Undo not supported");
    }
}

// Usage with lambdas
FunctionalCommand lightOn = () -> light.turnOn();
FunctionalCommand lightOff = () -> light.turnOff();
```

### 2. Command with Result

```java
public interface CommandWithResult<T> {
    T execute();
}

public class QueryCommand implements CommandWithResult<String> {
    private Database database;
    private String query;

    public QueryCommand(Database database, String query) {
        this.database = database;
        this.query = query;
    }

    @Override
    public String execute() {
        return database.executeQuery(query);
    }
}
```

### 3. Async Command

```java
public interface AsyncCommand {
    CompletableFuture<Void> executeAsync();
}

public class AsyncLightCommand implements AsyncCommand {
    private Light light;

    public AsyncLightCommand(Light light) {
        this.light = light;
    }

    @Override
    public CompletableFuture<Void> executeAsync() {
        return CompletableFuture.runAsync(() -> light.turnOn());
    }
}
```

The Command Pattern is a powerful tool for creating flexible, maintainable, and feature-rich applications. It's particularly useful in scenarios requiring undo functionality, macro operations, queuing, and decoupled architectures. When implemented correctly, it provides a clean separation of concerns and makes systems easier to extend and maintain.
