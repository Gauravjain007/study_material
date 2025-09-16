# Template Pattern in Java - Comprehensive Documentation

## Table of Contents

1. [Introduction](#introduction)
2. [Pattern Definition](#pattern-definition)
3. [Structure and Components](#structure-and-components)
4. [Implementation Examples](#implementation-examples)
5. [Hook Methods](#hook-methods)
6. [Real-World Examples](#real-world-examples)
7. [Advanced Techniques](#advanced-techniques)
8. [Best Practices](#best-practices)
9. [Common Pitfalls](#common-pitfalls)
10. [Comparison with Other Patterns](#comparison-with-other-patterns)

## Introduction

The Template Pattern is a behavioral design pattern that defines the skeleton of an algorithm in a base class, letting subclasses override specific steps without changing the algorithm's structure. It's one of the most commonly used patterns in object-oriented programming and is particularly powerful in Java due to its inheritance model.

### When to Use Template Pattern

-   When you have multiple classes with similar algorithms that differ only in certain steps
-   When you want to control the extension points of an algorithm
-   When you need to avoid code duplication in similar algorithms
-   When you want to implement the "Don't call us, we'll call you" principle (Hollywood Principle)

## Pattern Definition

**Intent**: Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.

**Key Characteristics**:

-   Uses inheritance to vary part of an algorithm
-   Promotes code reuse through common algorithm structure
-   Provides inversion of control through hook methods
-   Implements the principle of "Don't Repeat Yourself" (DRY)

## Structure and Components

### Core Components

1. **Abstract Class**: Defines the template method and abstract operations
2. **Concrete Classes**: Implement the abstract operations
3. **Template Method**: Defines the algorithm skeleton
4. **Primitive Operations**: Steps that must be implemented by subclasses
5. **Hook Operations**: Optional steps with default implementations

### UML Structure

```
AbstractClass
├── templateMethod() [final]
├── primitiveOperation1() [abstract]
├── primitiveOperation2() [abstract]
└── hook() [concrete, optional override]

ConcreteClass extends AbstractClass
├── primitiveOperation1() [implemented]
├── primitiveOperation2() [implemented]
└── hook() [optionally overridden]
```

## Implementation Examples

### Basic Example: Data Processing

```java
// Abstract base class defining the template
public abstract class DataProcessor {

    // Template method - defines the algorithm skeleton
    public final void processData() {
        readData();
        validateData();
        if (shouldTransform()) {
            transformData();
        }
        saveData();
        cleanup();
    }

    // Abstract methods that must be implemented by subclasses
    protected abstract void readData();
    protected abstract void validateData();
    protected abstract void transformData();
    protected abstract void saveData();

    // Hook method - provides default behavior but can be overridden
    protected boolean shouldTransform() {
        return true;
    }

    // Concrete method with default implementation
    protected void cleanup() {
        System.out.println("Performing cleanup operations...");
    }
}

// Concrete implementation for CSV processing
public class CsvDataProcessor extends DataProcessor {

    private String filename;
    private List<String[]> data;

    public CsvDataProcessor(String filename) {
        this.filename = filename;
    }

    @Override
    protected void readData() {
        System.out.println("Reading CSV data from: " + filename);
        // Simulate reading CSV data
        data = Arrays.asList(
            new String[]{"Name", "Age", "City"},
            new String[]{"John", "25", "New York"},
            new String[]{"Jane", "30", "Los Angeles"}
        );
    }

    @Override
    protected void validateData() {
        System.out.println("Validating CSV data format...");
        if (data == null || data.isEmpty()) {
            throw new IllegalStateException("No data to validate");
        }
        System.out.println("CSV data validation completed");
    }

    @Override
    protected void transformData() {
        System.out.println("Transforming CSV data...");
        // Convert to uppercase
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                row[i] = row[i].toUpperCase();
            }
        }
    }

    @Override
    protected void saveData() {
        System.out.println("Saving processed CSV data...");
        for (String[] row : data) {
            System.out.println(String.join(",", row));
        }
    }
}

// Concrete implementation for JSON processing
public class JsonDataProcessor extends DataProcessor {

    private String endpoint;
    private Map<String, Object> data;

    public JsonDataProcessor(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    protected void readData() {
        System.out.println("Fetching JSON data from: " + endpoint);
        // Simulate API call
        data = new HashMap<>();
        data.put("users", Arrays.asList(
            Map.of("name", "Alice", "age", 28),
            Map.of("name", "Bob", "age", 32)
        ));
    }

    @Override
    protected void validateData() {
        System.out.println("Validating JSON schema...");
        if (data == null || !data.containsKey("users")) {
            throw new IllegalStateException("Invalid JSON structure");
        }
        System.out.println("JSON validation completed");
    }

    @Override
    protected void transformData() {
        System.out.println("Transforming JSON data...");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> users = (List<Map<String, Object>>) data.get("users");
        users.forEach(user ->
            user.replaceAll((key, value) ->
                value instanceof String ? ((String) value).toUpperCase() : value
            )
        );
    }

    @Override
    protected void saveData() {
        System.out.println("Saving processed JSON data...");
        System.out.println(data.toString());
    }

    // Override hook method to skip transformation for JSON
    @Override
    protected boolean shouldTransform() {
        return false;
    }
}

// Usage example
public class DataProcessingExample {
    public static void main(String[] args) {
        System.out.println("=== Processing CSV Data ===");
        DataProcessor csvProcessor = new CsvDataProcessor("data.csv");
        csvProcessor.processData();

        System.out.println("\n=== Processing JSON Data ===");
        DataProcessor jsonProcessor = new JsonDataProcessor("https://api.example.com/users");
        jsonProcessor.processData();
    }
}
```

### Advanced Example: Game AI Behavior

```java
// Abstract game AI template
public abstract class GameAI {

    // Template method defining AI turn sequence
    public final void takeTurn() {
        collectResources();
        buildStructures();
        buildUnits();
        sendScouts();
        if (shouldAttack()) {
            sendWarriors();
        } else {
            sendArchers();
        }
        afterTurn();
    }

    // Abstract methods - must be implemented
    protected abstract void collectResources();
    protected abstract void buildStructures();
    protected abstract void buildUnits();

    // Concrete methods with default behavior
    protected void sendScouts() {
        System.out.println("Sending scouts to explore...");
    }

    protected void sendWarriors() {
        System.out.println("Sending warriors to attack!");
    }

    protected void sendArchers() {
        System.out.println("Sending archers for defense...");
    }

    // Hook methods
    protected boolean shouldAttack() {
        return Math.random() > 0.5; // 50% chance to attack
    }

    protected void afterTurn() {
        System.out.println("Turn completed.\n");
    }
}

// Aggressive AI implementation
public class AggressiveAI extends GameAI {

    @Override
    protected void collectResources() {
        System.out.println("Aggressively collecting resources for military units");
    }

    @Override
    protected void buildStructures() {
        System.out.println("Building barracks and weapon factories");
    }

    @Override
    protected void buildUnits() {
        System.out.println("Mass producing military units");
    }

    @Override
    protected boolean shouldAttack() {
        return true; // Always attack!
    }

    @Override
    protected void sendArchers() {
        // Aggressive AI prefers warriors even for defense
        sendWarriors();
    }
}

// Defensive AI implementation
public class DefensiveAI extends GameAI {

    @Override
    protected void collectResources() {
        System.out.println("Steadily collecting resources with focus on sustainability");
    }

    @Override
    protected void buildStructures() {
        System.out.println("Building walls, towers, and defensive structures");
    }

    @Override
    protected void buildUnits() {
        System.out.println("Building balanced army with emphasis on defense");
    }

    @Override
    protected boolean shouldAttack() {
        return false; // Never initiate attacks
    }

    @Override
    protected void afterTurn() {
        super.afterTurn();
        System.out.println("Strengthening defenses...");
    }
}

// Economic AI implementation
public class EconomicAI extends GameAI {

    private int economicStrength = 0;

    @Override
    protected void collectResources() {
        System.out.println("Maximizing resource collection efficiency");
        economicStrength += 2;
    }

    @Override
    protected void buildStructures() {
        System.out.println("Building markets, farms, and economic buildings");
        economicStrength += 1;
    }

    @Override
    protected void buildUnits() {
        System.out.println("Building minimal defensive units and many workers");
    }

    @Override
    protected boolean shouldAttack() {
        return economicStrength > 10; // Attack only when economically strong
    }

    @Override
    protected void sendWarriors() {
        if (economicStrength > 15) {
            System.out.println("Sending well-funded elite warriors!");
        } else {
            super.sendWarriors();
        }
    }
}
```

## Hook Methods

Hook methods are a powerful feature of the Template Pattern that provide points of customization without requiring implementation.

### Types of Hook Methods

```java
public abstract class DocumentProcessor {

    // Template method
    public final void processDocument() {
        openDocument();

        // Conditional hook
        if (needsPreProcessing()) {
            preProcess();
        }

        parseDocument();

        // Optional customization hook
        customizeProcessing();

        generateOutput();

        // Notification hook
        onProcessingComplete();

        closeDocument();
    }

    // Abstract methods
    protected abstract void openDocument();
    protected abstract void parseDocument();
    protected abstract void generateOutput();
    protected abstract void closeDocument();

    // Conditional hook - controls algorithm flow
    protected boolean needsPreProcessing() {
        return false; // Default: no preprocessing
    }

    // Optional operation hook - empty default implementation
    protected void preProcess() {
        // Default: do nothing
    }

    // Customization hook - allows additional processing
    protected void customizeProcessing() {
        // Default: do nothing
    }

    // Notification hook - informs about events
    protected void onProcessingComplete() {
        // Default: do nothing
    }
}

// Example implementation using hooks
public class PdfDocumentProcessor extends DocumentProcessor {

    private boolean requiresOcr = false;

    public PdfDocumentProcessor(boolean requiresOcr) {
        this.requiresOcr = requiresOcr;
    }

    @Override
    protected void openDocument() {
        System.out.println("Opening PDF document...");
    }

    @Override
    protected void parseDocument() {
        System.out.println("Parsing PDF content...");
    }

    @Override
    protected void generateOutput() {
        System.out.println("Generating formatted output...");
    }

    @Override
    protected void closeDocument() {
        System.out.println("Closing PDF document...");
    }

    // Use conditional hook
    @Override
    protected boolean needsPreProcessing() {
        return requiresOcr;
    }

    // Implement optional hook
    @Override
    protected void preProcess() {
        System.out.println("Performing OCR on scanned PDF...");
    }

    // Use customization hook
    @Override
    protected void customizeProcessing() {
        System.out.println("Applying PDF-specific formatting rules...");
    }

    // Use notification hook
    @Override
    protected void onProcessingComplete() {
        System.out.println("PDF processing completed successfully!");
    }
}
```

## Real-World Examples

### 1. HTTP Request Processing Framework

```java
public abstract class HttpRequestHandler {

    public final Response handleRequest(Request request) {
        // Authentication
        if (!authenticate(request)) {
            return new Response(401, "Unauthorized");
        }

        // Authorization
        if (!authorize(request)) {
            return new Response(403, "Forbidden");
        }

        // Validation
        ValidationResult validation = validateRequest(request);
        if (!validation.isValid()) {
            return new Response(400, validation.getErrorMessage());
        }

        // Pre-processing hook
        preProcess(request);

        // Main processing
        Response response = processRequest(request);

        // Post-processing hook
        postProcess(request, response);

        return response;
    }

    // Abstract methods
    protected abstract Response processRequest(Request request);

    // Concrete methods with default implementations
    protected boolean authenticate(Request request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader != null && !authHeader.isEmpty();
    }

    protected boolean authorize(Request request) {
        return true; // Default: allow all authenticated requests
    }

    protected ValidationResult validateRequest(Request request) {
        return ValidationResult.valid(); // Default: no validation
    }

    // Hook methods
    protected void preProcess(Request request) {
        // Default: do nothing
    }

    protected void postProcess(Request request, Response response) {
        // Default: do nothing
    }
}

// Concrete implementation for user management
public class UserRequestHandler extends HttpRequestHandler {

    private UserService userService;

    public UserRequestHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Response processRequest(Request request) {
        String method = request.getMethod();
        String path = request.getPath();

        switch (method) {
            case "GET":
                if (path.startsWith("/users/")) {
                    String userId = extractUserId(path);
                    User user = userService.findUser(userId);
                    return new Response(200, user.toJson());
                }
                break;
            case "POST":
                if (path.equals("/users")) {
                    User newUser = User.fromJson(request.getBody());
                    User created = userService.createUser(newUser);
                    return new Response(201, created.toJson());
                }
                break;
        }

        return new Response(404, "Not Found");
    }

    @Override
    protected boolean authorize(Request request) {
        String userRole = request.getHeader("X-User-Role");
        return "ADMIN".equals(userRole) || "USER".equals(userRole);
    }

    @Override
    protected ValidationResult validateRequest(Request request) {
        if ("POST".equals(request.getMethod())) {
            String body = request.getBody();
            if (body == null || body.trim().isEmpty()) {
                return ValidationResult.invalid("Request body is required for POST requests");
            }
        }
        return ValidationResult.valid();
    }

    @Override
    protected void preProcess(Request request) {
        System.out.println("Logging user request: " + request.getPath());
    }

    @Override
    protected void postProcess(Request request, Response response) {
        System.out.println("User request completed with status: " + response.getStatusCode());
    }

    private String extractUserId(String path) {
        return path.substring("/users/".length());
    }
}
```

### 2. Testing Framework Template

```java
public abstract class TestRunner {

    private List<String> failures = new ArrayList<>();

    public final TestResult runTests() {
        System.out.println("Starting test execution...");

        setUp();

        try {
            executeTests();
        } finally {
            tearDown();
        }

        return new TestResult(failures.isEmpty(), failures);
    }

    // Template method for individual test execution
    protected final void executeTests() {
        List<String> testMethods = getTestMethods();

        for (String testMethod : testMethods) {
            try {
                beforeEach();
                runTest(testMethod);
                afterEach();
                System.out.println("✓ " + testMethod + " passed");
            } catch (Exception e) {
                failures.add(testMethod + ": " + e.getMessage());
                System.out.println("✗ " + testMethod + " failed: " + e.getMessage());
                onTestFailed(testMethod, e);
            }
        }
    }

    // Abstract methods
    protected abstract List<String> getTestMethods();
    protected abstract void runTest(String testMethod) throws Exception;

    // Hook methods with default implementations
    protected void setUp() {
        System.out.println("Setting up test environment...");
    }

    protected void tearDown() {
        System.out.println("Cleaning up test environment...");
    }

    protected void beforeEach() {
        // Override if needed
    }

    protected void afterEach() {
        // Override if needed
    }

    protected void onTestFailed(String testMethod, Exception e) {
        // Override for custom failure handling
    }
}

// Concrete test implementation
public class CalculatorTestRunner extends TestRunner {

    private Calculator calculator;

    @Override
    protected List<String> getTestMethods() {
        return Arrays.asList("testAddition", "testSubtraction", "testDivision", "testDivisionByZero");
    }

    @Override
    protected void setUp() {
        super.setUp();
        calculator = new Calculator();
        System.out.println("Calculator instance created");
    }

    @Override
    protected void beforeEach() {
        calculator.clear();
    }

    @Override
    protected void runTest(String testMethod) throws Exception {
        switch (testMethod) {
            case "testAddition":
                testAddition();
                break;
            case "testSubtraction":
                testSubtraction();
                break;
            case "testDivision":
                testDivision();
                break;
            case "testDivisionByZero":
                testDivisionByZero();
                break;
            default:
                throw new IllegalArgumentException("Unknown test method: " + testMethod);
        }
    }

    private void testAddition() throws Exception {
        int result = calculator.add(2, 3);
        if (result != 5) {
            throw new AssertionException("Expected 5, but got " + result);
        }
    }

    private void testSubtraction() throws Exception {
        int result = calculator.subtract(5, 3);
        if (result != 2) {
            throw new AssertionException("Expected 2, but got " + result);
        }
    }

    private void testDivision() throws Exception {
        double result = calculator.divide(10, 2);
        if (result != 5.0) {
            throw new AssertionException("Expected 5.0, but got " + result);
        }
    }

    private void testDivisionByZero() throws Exception {
        try {
            calculator.divide(10, 0);
            throw new AssertionException("Expected ArithmeticException");
        } catch (ArithmeticException e) {
            // Expected exception - test passes
        }
    }

    @Override
    protected void onTestFailed(String testMethod, Exception e) {
        System.out.println("Capturing screenshot for failed test: " + testMethod);
        System.out.println("Calculator state: " + calculator.getCurrentState());
    }
}
```

## Advanced Techniques

### 1. Parameterized Template Methods

```java
public abstract class ReportGenerator<T> {

    public final String generateReport(List<T> data, ReportConfig config) {
        StringBuilder report = new StringBuilder();

        // Header
        report.append(generateHeader(config));

        // Filter data
        List<T> filteredData = filterData(data, config.getFilterCriteria());

        // Group data if needed
        Map<String, List<T>> groupedData = config.isGroupingEnabled()
            ? groupData(filteredData, config.getGroupingKey())
            : Map.of("default", filteredData);

        // Process each group
        for (Map.Entry<String, List<T>> group : groupedData.entrySet()) {
            if (config.isGroupingEnabled()) {
                report.append(generateGroupHeader(group.getKey()));
            }

            // Sort data within group
            List<T> sortedData = sortData(group.getValue(), config.getSortCriteria());

            // Generate content
            report.append(generateContent(sortedData, config));

            if (config.isGroupingEnabled()) {
                report.append(generateGroupFooter(group.getKey(), group.getValue().size()));
            }
        }

        // Footer
        report.append(generateFooter(data.size(), config));

        return report.toString();
    }

    // Abstract methods
    protected abstract String generateHeader(ReportConfig config);
    protected abstract String generateContent(List<T> data, ReportConfig config);
    protected abstract String generateFooter(int totalRecords, ReportConfig config);
    protected abstract List<T> filterData(List<T> data, Map<String, Object> criteria);
    protected abstract List<T> sortData(List<T> data, String sortCriteria);

    // Hook methods
    protected Map<String, List<T>> groupData(List<T> data, String groupingKey) {
        // Default implementation - override if needed
        return data.stream().collect(
            Collectors.groupingBy(item -> getGroupingValue(item, groupingKey))
        );
    }

    protected String generateGroupHeader(String groupName) {
        return "\n--- " + groupName + " ---\n";
    }

    protected String generateGroupFooter(String groupName, int count) {
        return "\nTotal in " + groupName + ": " + count + "\n";
    }

    protected abstract String getGroupingValue(T item, String groupingKey);
}

// Concrete implementation for employee reports
public class EmployeeReportGenerator extends ReportGenerator<Employee> {

    @Override
    protected String generateHeader(ReportConfig config) {
        return String.format("EMPLOYEE REPORT - %s\n%s\n",
            config.getTitle(), "=".repeat(50));
    }

    @Override
    protected String generateContent(List<Employee> employees, ReportConfig config) {
        StringBuilder content = new StringBuilder();

        for (Employee emp : employees) {
            content.append(String.format("ID: %d | Name: %s | Department: %s | Salary: $%.2f\n",
                emp.getId(), emp.getName(), emp.getDepartment(), emp.getSalary()));
        }

        return content.toString();
    }

    @Override
    protected String generateFooter(int totalRecords, ReportConfig config) {
        return String.format("\nTotal Employees: %d\nGenerated on: %s\n",
            totalRecords, new Date());
    }

    @Override
    protected List<Employee> filterData(List<Employee> employees, Map<String, Object> criteria) {
        return employees.stream()
            .filter(emp -> {
                if (criteria.containsKey("department")) {
                    return emp.getDepartment().equals(criteria.get("department"));
                }
                if (criteria.containsKey("minSalary")) {
                    return emp.getSalary() >= (Double) criteria.get("minSalary");
                }
                return true;
            })
            .collect(Collectors.toList());
    }

    @Override
    protected List<Employee> sortData(List<Employee> employees, String sortCriteria) {
        return employees.stream()
            .sorted((e1, e2) -> {
                switch (sortCriteria) {
                    case "name": return e1.getName().compareTo(e2.getName());
                    case "salary": return Double.compare(e1.getSalary(), e2.getSalary());
                    case "department": return e1.getDepartment().compareTo(e2.getDepartment());
                    default: return Integer.compare(e1.getId(), e2.getId());
                }
            })
            .collect(Collectors.toList());
    }

    @Override
    protected String getGroupingValue(Employee employee, String groupingKey) {
        switch (groupingKey) {
            case "department": return employee.getDepartment();
            case "salaryRange":
                double salary = employee.getSalary();
                if (salary < 50000) return "Entry Level";
                else if (salary < 100000) return "Mid Level";
                else return "Senior Level";
            default: return "Unknown";
        }
    }
}
```

### 2. Multi-Level Template Hierarchy

```java
// Base template
public abstract class DataMigrator {

    public final MigrationResult migrate() {
        MigrationResult result = new MigrationResult();

        try {
            validatePrerequisites();
            Connection source = connectToSource();
            Connection target = connectToTarget();

            performMigration(source, target, result);

            if (shouldValidateMigration()) {
                validateMigration(source, target, result);
            }

            cleanup(source, target);
            result.setSuccess(true);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            handleMigrationError(e);
        }

        return result;
    }

    // Abstract methods
    protected abstract void validatePrerequisites() throws Exception;
    protected abstract Connection connectToSource() throws Exception;
    protected abstract Connection connectToTarget() throws Exception;
    protected abstract void performMigration(Connection source, Connection target, MigrationResult result) throws Exception;

    // Hook methods
    protected boolean shouldValidateMigration() {
        return true;
    }

    protected void validateMigration(Connection source, Connection target, MigrationResult result) throws Exception {
        // Default: basic validation
    }

    protected void cleanup(Connection source, Connection target) {
        // Default: close connections
        try {
            if (source != null) source.close();
            if (target != null) target.close();
        } catch (Exception e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }

    protected void handleMigrationError(Exception e) {
        System.err.println("Migration failed: " + e.getMessage());
    }
}

// Intermediate template for database migrations
public abstract class DatabaseMigrator extends DataMigrator {

    protected String sourceUrl;
    protected String targetUrl;
    protected String username;
    protected String password;

    public DatabaseMigrator(String sourceUrl, String targetUrl, String username, String password) {
        this.sourceUrl = sourceUrl;
        this.targetUrl = targetUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    protected Connection connectToSource() throws Exception {
        return DriverManager.getConnection(sourceUrl, username, password);
    }

    @Override
    protected Connection connectToTarget() throws Exception {
        return DriverManager.getConnection(targetUrl, username, password);
    }

    @Override
    protected void validatePrerequisites() throws Exception {
        if (sourceUrl == null || targetUrl == null) {
            throw new IllegalStateException("Database URLs must be provided");
        }

        validateDatabaseConnectivity();
        checkDatabaseVersions();
    }

    // New template method for database-specific operations
    protected final void performMigration(Connection source, Connection target, MigrationResult result) throws Exception {
        List<String> tables = getTableList(source);

        for (String table : tables) {
            if (shouldMigrateTable(table)) {
                migrateTable(source, target, table, result);

                if (shouldValidateTable()) {
                    validateTableMigration(source, target, table);
                }
            }
        }
    }

    // Abstract methods for database operations
    protected abstract List<String> getTableList(Connection source) throws Exception;
    protected abstract void migrateTable(Connection source, Connection target, String table, MigrationResult result) throws Exception;

    // Hook methods
    protected void validateDatabaseConnectivity() throws Exception {
        // Default: do nothing
    }

    protected void checkDatabaseVersions() throws Exception {
        // Default: do nothing
    }

    protected boolean shouldMigrateTable(String tableName) {
        return true; // Default: migrate all tables
    }

    protected boolean shouldValidateTable() {
        return true;
    }

    protected void validateTableMigration(Connection source, Connection target, String table) throws Exception {
        // Default: basic row count validation
        try (Statement sourceStmt = source.createStatement();
             Statement targetStmt = target.createStatement()) {

            ResultSet sourceRs = sourceStmt.executeQuery("SELECT COUNT(*) FROM " + table);
            ResultSet targetRs = targetStmt.executeQuery("SELECT COUNT(*) FROM " + table);

            sourceRs.next();
            targetRs.next();

            int sourceCount = sourceRs.getInt(1);
            int targetCount = targetRs.getInt(1);

            if (sourceCount != targetCount) {
                throw new Exception("Row count mismatch for table " + table +
                    ": source=" + sourceCount + ", target=" + targetCount);
            }
        }
    }
}

// Concrete implementation
public class MySqlToPostgresMigrator extends DatabaseMigrator {

    private Set<String> excludedTables = new HashSet<>();

    public MySqlToPostgresMigrator(String sourceUrl, String targetUrl, String username, String password) {
        super(sourceUrl, targetUrl, username, password);
        excludedTables.add("mysql_system_table");
        excludedTables.add("information_schema");
    }

    @Override
    protected void validateDatabaseConnectivity() throws Exception {
        // Test connections
        try (Connection testConn = DriverManager.getConnection(sourceUrl, username, password)) {
            if (!testConn.isValid(5)) {
                throw new Exception("Cannot connect to source database");
            }
        }

        try (Connection testConn = DriverManager.getConnection(targetUrl, username, password)) {
            if (!testConn.isValid(5)) {
                throw new Exception("Cannot connect to target database");
            }
        }
    }

    @Override
    protected void checkDatabaseVersions() throws Exception {
        try (Connection sourceConn = DriverManager.getConnection(sourceUrl, username, password)) {
            DatabaseMetaData metaData = sourceConn.getMetaData();
            System.out.println("Source DB: " + metaData.getDatabaseProductName() +
                " " + metaData.getDatabaseProductVersion());
        }
    }

    @Override
    protected List<String> getTableList(Connection source) throws Exception {
        List<String> tables = new ArrayList<>();
        DatabaseMetaData metaData = source.getMetaData();

        try (ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tables.add(tableName);
            }
        }

        return tables;
    }

    @Override
    protected void migrateTable(Connection source, Connection target, String table, MigrationResult result) throws Exception {
        System.out.println("Migrating table: " + table);

        // Get table structure
        String createTableSql = generatePostgresCreateTable(source, table);

        // Create table in target database
        try (Statement stmt = target.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS " + table);
            stmt.executeUpdate(createTableSql);
        }

        // Copy data
        String selectSql = "SELECT * FROM " + table;
        String insertSql = generateInsertStatement(source, table);

        try (PreparedStatement selectStmt = source.prepareStatement(selectSql);
             PreparedStatement insertStmt = target.prepareStatement(insertSql);
             ResultSet rs = selectStmt.executeQuery()) {

            int batchSize = 1000;
            int count = 0;

            while (rs.next()) {
                setInsertParameters(rs, insertStmt);
                insertStmt.addBatch();
                count++;

                if (count % batchSize == 0) {
                    insertStmt.executeBatch();
                    insertStmt.clearBatch();
                }
            }

            // Execute remaining batch
            if (count % batchSize != 0) {
                insertStmt.executeBatch();
            }

            result.addMigratedTable(table, count);
            System.out.println("Migrated " + count + " rows for table: " + table);
        }
    }

    @Override
    protected boolean shouldMigrateTable(String tableName) {
        return !excludedTables.contains(tableName.toLowerCase());
    }

    private String generatePostgresCreateTable(Connection source, String tableName) throws Exception {
        StringBuilder createSql = new StringBuilder("CREATE TABLE " + tableName + " (");
        DatabaseMetaData metaData = source.getMetaData();

        try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
            boolean first = true;
            while (rs.next()) {
                if (!first) createSql.append(", ");

                String columnName = rs.getString("COLUMN_NAME");
                String mysqlType = rs.getString("TYPE_NAME");
                int columnSize = rs.getInt("COLUMN_SIZE");
                boolean nullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable;

                createSql.append(columnName).append(" ")
                         .append(convertMySqlTypeToPostgres(mysqlType, columnSize));

                if (!nullable) {
                    createSql.append(" NOT NULL");
                }

                first = false;
            }
        }

        createSql.append(")");
        return createSql.toString();
    }

    private String convertMySqlTypeToPostgres(String mysqlType, int size) {
        switch (mysqlType.toUpperCase()) {
            case "INT": return "INTEGER";
            case "BIGINT": return "BIGINT";
            case "VARCHAR": return "VARCHAR(" + size + ")";
            case "TEXT": return "TEXT";
            case "DATETIME": return "TIMESTAMP";
            case "DECIMAL": return "DECIMAL";
            default: return mysqlType;
        }
    }

    private String generateInsertStatement(Connection source, String tableName) throws Exception {
        DatabaseMetaData metaData = source.getMetaData();
        List<String> columnNames = new ArrayList<>();

        try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                columnNames.add(rs.getString("COLUMN_NAME"));
            }
        }

        String columns = String.join(", ", columnNames);
        String placeholders = columnNames.stream().map(c -> "?").collect(Collectors.joining(", "));

        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }

    private void setInsertParameters(ResultSet rs, PreparedStatement insertStmt) throws Exception {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            Object value = rs.getObject(i);
            insertStmt.setObject(i, value);
        }
    }
}
```

## Best Practices

### 1. Design Guidelines

```java
// GOOD: Clear separation of concerns
public abstract class OrderProcessor {

    // Template method is final to prevent override
    public final OrderResult processOrder(Order order) {
        validate(order);
        calculateTotal(order);
        applyDiscounts(order);
        processPayment(order);
        updateInventory(order);
        sendConfirmation(order);
        return new OrderResult(order);
    }

    // Abstract methods have clear responsibilities
    protected abstract void processPayment(Order order);
    protected abstract void updateInventory(Order order);

    // Concrete methods provide common functionality
    protected void validate(Order order) {
        if (order == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Invalid order");
        }
    }

    // Hook methods provide extension points
    protected void applyDiscounts(Order order) {
        // Default: no discounts
    }
}

// BAD: Unclear responsibilities and missing final keyword
public abstract class BadOrderProcessor {

    // Should be final but isn't
    public OrderResult processOrder(Order order) {
        doEverything(order); // Unclear what this does
        return new OrderResult(order);
    }

    // Too broad responsibility
    protected abstract void doEverything(Order order);
}
```

### 2. Error Handling Best Practices

```java
public abstract class RobustDataProcessor {

    public final ProcessingResult process(DataSet dataSet) {
        ProcessingResult result = new ProcessingResult();

        try {
            validateInput(dataSet, result);
            if (result.hasErrors()) {
                return result;
            }

            preprocessData(dataSet, result);
            if (result.hasCriticalErrors()) {
                return result;
            }

            performProcessing(dataSet, result);

        } catch (Exception e) {
            handleUnexpectedError(e, result);
        } finally {
            cleanup(result);
        }

        return result;
    }

    protected abstract void performProcessing(DataSet dataSet, ProcessingResult result) throws Exception;

    protected void validateInput(DataSet dataSet, ProcessingResult result) {
        if (dataSet == null) {
            result.addError("DataSet cannot be null");
        }
    }

    protected void preprocessData(DataSet dataSet, ProcessingResult result) {
        // Default: do nothing
    }

    protected void handleUnexpectedError(Exception e, ProcessingResult result) {
        result.addCriticalError("Unexpected error: " + e.getMessage());
        logError(e);
    }

    protected void cleanup(ProcessingResult result) {
        // Override if resources need cleanup
    }

    protected void logError(Exception e) {
        System.err.println("Error in data processing: " + e.getMessage());
        e.printStackTrace();
    }
}
```

### 3. Configuration and Flexibility

```java
public abstract class ConfigurableFileProcessor {

    private ProcessingConfig config;

    public ConfigurableFileProcessor(ProcessingConfig config) {
        this.config = config;
    }

    public final void processFile(String filename) {
        if (config.isLoggingEnabled()) {
            log("Starting processing of: " + filename);
        }

        FileData data = loadFile(filename);

        if (config.shouldValidate()) {
            validateFile(data);
        }

        if (config.shouldBackup()) {
            createBackup(filename);
        }

        processFileData(data);

        if (config.shouldCleanup()) {
            cleanup(filename);
        }

        if (config.isLoggingEnabled()) {
            log("Completed processing of: " + filename);
        }
    }

    // Abstract methods
    protected abstract FileData loadFile(String filename);
    protected abstract void processFileData(FileData data);

    // Hook methods with configuration-driven behavior
    protected void validateFile(FileData data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
    }

    protected void createBackup(String filename) {
        String backupName = filename + ".backup." + System.currentTimeMillis();
        // Create backup logic
        log("Created backup: " + backupName);
    }

    protected void cleanup(String filename) {
        // Default cleanup logic
    }

    protected void log(String message) {
        if (config.isLoggingEnabled()) {
            System.out.println("[" + getClass().getSimpleName() + "] " + message);
        }
    }

    // Protected access to configuration
    protected ProcessingConfig getConfig() {
        return config;
    }
}

// Configuration class
public class ProcessingConfig {
    private boolean loggingEnabled = true;
    private boolean shouldValidate = true;
    private boolean shouldBackup = false;
    private boolean shouldCleanup = true;

    // Getters and setters
    public boolean isLoggingEnabled() { return loggingEnabled; }
    public void setLoggingEnabled(boolean loggingEnabled) { this.loggingEnabled = loggingEnabled; }

    public boolean shouldValidate() { return shouldValidate; }
    public void setShouldValidate(boolean shouldValidate) { this.shouldValidate = shouldValidate; }

    public boolean shouldBackup() { return shouldBackup; }
    public void setShouldBackup(boolean shouldBackup) { this.shouldBackup = shouldBackup; }

    public boolean shouldCleanup() { return shouldCleanup; }
    public void setShouldCleanup(boolean shouldCleanup) { this.shouldCleanup = shouldCleanup; }

    // Fluent interface for easier configuration
    public ProcessingConfig withLogging(boolean enabled) {
        this.loggingEnabled = enabled;
        return this;
    }

    public ProcessingConfig withValidation(boolean enabled) {
        this.shouldValidate = enabled;
        return this;
    }

    public ProcessingConfig withBackup(boolean enabled) {
        this.shouldBackup = enabled;
        return this;
    }

    public ProcessingConfig withCleanup(boolean enabled) {
        this.shouldCleanup = enabled;
        return this;
    }
}
```

## Common Pitfalls

### 1. Violating the Open-Closed Principle

```java
// BAD: Template method is not final, can be overridden
public abstract class BadTemplate {
    // This should be final!
    public ProcessResult process() {
        step1();
        step2();
        return new ProcessResult();
    }

    protected abstract void step1();
    protected abstract void step2();
}

// Subclass can break the algorithm
public class BreakingSubclass extends BadTemplate {
    @Override
    public ProcessResult process() {
        // Completely different algorithm - breaks contract
        return new ProcessResult("shortcut");
    }

    protected void step1() { /* implementation */ }
    protected void step2() { /* implementation */ }
}

// GOOD: Template method is final
public abstract class GoodTemplate {
    public final ProcessResult process() {
        step1();
        step2();
        return new ProcessResult();
    }

    protected abstract void step1();
    protected abstract void step2();
}
```

### 2. Too Many Abstract Methods

```java
// BAD: Too many abstract methods make implementation difficult
public abstract class OverComplexTemplate {
    public final void process() {
        method1(); method2(); method3(); method4(); method5();
        method6(); method7(); method8(); method9(); method10();
    }

    // Too many abstract methods!
    protected abstract void method1();
    protected abstract void method2();
    protected abstract void method3();
    protected abstract void method4();
    protected abstract void method5();
    protected abstract void method6();
    protected abstract void method7();
    protected abstract void method8();
    protected abstract void method9();
    protected abstract void method10();
}

// GOOD: Reasonable number of abstract methods with defaults
public abstract class WellDesignedTemplate {
    public final void process() {
        initialize();
        performMainTask();
        if (shouldDoOptionalTask()) {
            performOptionalTask();
        }
        finalize();
    }

    // Only essential abstract methods
    protected abstract void performMainTask();

    // Reasonable defaults for other methods
    protected void initialize() { /* default implementation */ }
    protected void finalize() { /* default implementation */ }

    // Hook methods with defaults
    protected boolean shouldDoOptionalTask() { return false; }
    protected void performOptionalTask() { /* default: do nothing */ }
}
```

### 3. Inappropriate Use of Template Pattern

```java
// BAD: Using Template Pattern when composition would be better
public abstract class BadFileHandler {
    public final void handleFile(String filename) {
        File file = openFile(filename);
        String content = readContent(file);
        processContent(content);
        closeFile(file);
    }

    // These are implementation details, not algorithm variants
    protected abstract File openFile(String filename);
    protected abstract String readContent(File file);
    protected abstract void closeFile(File file);
    protected abstract void processContent(String content);
}

// GOOD: Use composition for implementation details, template for algorithm variants
public abstract class GoodFileProcessor {
    private FileHandler fileHandler;

    public GoodFileProcessor(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public final ProcessResult processFile(String filename) {
        String content = fileHandler.readFile(filename);

        if (shouldValidateContent()) {
            validateContent(content);
        }

        String processed = transformContent(content);

        if (shouldSaveResult()) {
            saveResult(processed, filename);
        }

        return createResult(processed);
    }

    // Focus on algorithm variants, not implementation details
    protected abstract String transformContent(String content);

    // Meaningful hooks
    protected boolean shouldValidateContent() { return true; }
    protected boolean shouldSaveResult() { return true; }
    protected void validateContent(String content) { /* default validation */ }
    protected void saveResult(String result, String originalFilename) { /* default save */ }
    protected ProcessResult createResult(String processed) { return new ProcessResult(processed); }
}
```

## Comparison with Other Patterns

### Template Pattern vs Strategy Pattern

```java
// Template Pattern - Inheritance-based, algorithm structure fixed
public abstract class SortingTemplate {
    public final void sort(int[] array) {
        if (array.length <= 1) return;

        preProcess(array);
        performSort(array, 0, array.length - 1);
        postProcess(array);
    }

    protected abstract void performSort(int[] array, int start, int end);
    protected void preProcess(int[] array) { /* optional */ }
    protected void postProcess(int[] array) { /* optional */ }
}

// Strategy Pattern - Composition-based, algorithm completely replaceable
public class SortingContext {
    private SortingStrategy strategy;

    public SortingContext(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(int[] array) {
        strategy.sort(array);
    }

    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }
}

interface SortingStrategy {
    void sort(int[] array);
}
```

### Template Pattern vs Command Pattern

```java
// Template Pattern - Defines algorithm structure
public abstract class BatchJobTemplate {
    public final JobResult executeJob() {
        initializeJob();
        JobResult result = processJob();
        cleanupJob();
        return result;
    }

    protected abstract JobResult processJob();
    protected void initializeJob() { /* default */ }
    protected void cleanupJob() { /* default */ }
}

// Command Pattern - Encapsulates requests
public interface Command {
    void execute();
}

public class BatchJobCommand implements Command {
    private JobProcessor processor;
    private JobData data;

    public BatchJobCommand(JobProcessor processor, JobData data) {
        this.processor = processor;
        this.data = data;
    }

    @Override
    public void execute() {
        processor.process(data);
    }
}
```

### When to Use Each Pattern

| Pattern      | Use When                                     | Benefits                                          | Drawbacks                                 |
| ------------ | -------------------------------------------- | ------------------------------------------------- | ----------------------------------------- |
| **Template** | Algorithm structure is stable but steps vary | Code reuse, controlled extension points           | Inheritance coupling, limited flexibility |
| **Strategy** | Need to switch algorithms at runtime         | Runtime flexibility, composition over inheritance | More complex setup, potential overhead    |
| **Command**  | Need to parameterize objects with operations | Decoupling, undo capability, macro recording      | Can lead to many small classes            |

## Summary

The Template Pattern is a powerful tool for creating reusable algorithm frameworks while maintaining control over the overall structure. Key takeaways:

### Strengths

-   **Code Reuse**: Common algorithm structure shared across implementations
-   **Controlled Extension**: Subclasses can only vary specific steps
-   **Inversion of Control**: Framework calls subclass methods (Hollywood Principle)
-   **Easy to Understand**: Clear separation between fixed and variable parts

### When to Use

-   Multiple classes share similar algorithm structures
-   Need to control which parts of an algorithm can be customized
-   Want to eliminate code duplication in related classes
-   Framework development where extension points are well-defined

### Best Practices Recap

1. Make template methods `final` to prevent overriding
2. Minimize the number of abstract methods
3. Provide reasonable defaults for hook methods
4. Use meaningful method names that describe their purpose
5. Consider configuration objects for complex scenarios
6. Handle errors gracefully with proper cleanup
7. Document the intended algorithm flow clearly

### Common Use Cases in Java

-   Servlet processing in web frameworks
-   Database connection and transaction management
-   File processing pipelines
-   Testing frameworks
-   GUI application frameworks
-   Data migration tools

The Template Pattern strikes an excellent balance between flexibility and control, making it an essential pattern for any Java developer's toolkit.
