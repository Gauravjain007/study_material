package com.study.optionals;

import java.util.List;
import java.util.Optional;

/**
 * 
 * <pre>
 * Best Practices:
 * * Do not use Optional as a method parameter or field; use it only as a return type.
 * * Avoid calling get() without checking isPresent() first - use the safer methods instead.
 * * Use map() and flatMap() for transformations rather than extracting the value.
 * * Choose orElseGet() over orElse() when the default value is expensive to compute.
 * * Prefer method chaining to multiple conditional statements.
 * </pre>
 */
public class OptionalsImpl {

    static class Printer {
        String model;
        float price;

        public Printer() {
        }

        public Printer(String model, float price) {
            this.model = model;
            this.price = price;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Printer [model=" + model + ", price=" + price + "]";
        }
    }

    /**
     * Performs basic checks on the given Optional objects.
     * 
     * @param printer1 An Optional containing a Printer object
     * @param printer2 An Optional containing a Printer object
     */
    public static void doBasicChecks(Optional<Printer> printer1, Optional<Printer> printer2) {
        System.out.println("\n----- Basic checks");
        boolean isPresent = printer1.isPresent(); // true if value exists
        boolean isEmpty = printer2.isEmpty(); // true if no value (added in Java 11)
        Printer p1 = isPresent ? printer1.get() : null; // Retrieves value (throws NoSuchElementException if empty)

        System.out.println("Is printer1 present: " + isPresent);
        System.out.println("Is printer2 empty: " + isEmpty);
        System.out.println("Value of printer1: " + p1);
    }

    /**
     * Performs various functional operations on the given Optional Printer objects.
     * 
     * This method demonstrates the use of map, flatMap, and filter operations on
     * Optional objects. It performs the following operations:
     * 
     * - Uses map to transform the value within printer1 and printer2 to the length
     * of their model strings, if present.
     * - Uses flatMap on printer4 to transform and return an Optional of the model
     * in uppercase.
     * - Uses filter to check if the model of printer1 and printer2 starts with "HP"
     * and prints the result.
     * 
     * @param printer1 An Optional containing a Printer object
     * @param printer2 An Optional containing a Printer object
     * @param printer4 An Optional containing a Printer object
     */
    public static void doFunctionalOperations(Optional<Printer> printer1, Optional<Printer> printer2,
            Optional<Printer> printer4) {
        System.out.println("\n----- Functional Operations");
        // map - transform the value if present
        Optional<Integer> length = printer1.map(p -> p.getModel().length());
        System.out.println("Length of printer1: " + length);
        length = printer2.map(p -> p.getModel().length());
        System.out.println("Length of printer2: " + length);

        // flatMap - when the transformation itself returns an Optional [similar to map]
        Optional<String> transformed = printer4.flatMap(p -> Optional.of(p.getModel().toUpperCase()));
        System.out.println("Transformed printer4: " + transformed);

        // filter - keep value only if it satisfies the predicate
        boolean isHP = printer1.filter(p -> p.getModel().startsWith("HP")).isPresent();
        System.out.println("Printer1 is from HP: " + isHP);
        isHP = printer2.filter(p -> p.getModel().startsWith("HP")).isPresent();
        System.out.println("Printer2 is from HP: " + isHP);
    }

    /**
     * Performs operations that provide default values and execute actions on the
     * given Optional objects.
     * 
     * This method demonstrates the use of orElse, orElseGet, orElseThrow,
     * ifPresent, and ifPresentOrElse operations on
     * Optional objects. It performs the following operations:
     * 
     * - Uses orElse to return the value of printer1 and printer2 if present, else
     * returns a default Printer object.
     * - Uses orElseGet to return the value of printer3 and printer4 if present,
     * else computes a default Printer object using a Supplier.
     * - Uses orElseThrow to return the value of printer1 and printer2 if present,
     * else throws an exception.
     * - Uses ifPresent to execute an action if printer4 is present.
     * - Uses ifPresentOrElse to execute an action if printer3 is present, else
     * executes another action.
     * 
     * @param printer1 An Optional containing a Printer object
     * @param printer2 An Optional containing a Printer object
     * @param printer3 An Optional containing a Printer object
     * @param printer4 An Optional containing a Printer object
     */
    public static void doDefaultValueAndActions(Optional<Printer> printer1, Optional<Printer> printer2,
            Optional<Printer> printer3, Optional<Printer> printer4) {
        System.out.println("\n----- Default value and actions");
        // orElse - returns the value if present, else returns the default value
        String model = printer1.orElse(new Printer()).getModel();
        System.out.println("Printer1 Model: " + model);
        model = printer2.orElse(new Printer()).getModel();
        System.out.println("Printer2 Model: " + model);

        // orElseGet - returns the value or computes a default (lazy evaluation)
        // Only runs if empty
        model = printer3.orElseGet(Printer::new).getModel();
        System.out.println("Printer3 Model: " + model);
        model = printer4.orElseGet(Printer::new).getModel();
        System.out.println("Printer4 Model: " + model);

        // orElseThrow - returns the value or throws an exception
        try {
            Printer printer = printer1.orElseThrow(() -> new RuntimeException("Printer1 is empty"));
            System.out.println("Printer1 Model: " + printer);
            printer = printer2.orElseThrow(() -> new RuntimeException("Printer2 is empty"));
            System.out.println("Printer2 Model: " + printer);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // ifPresent - executes an action if a value is present
        printer4.ifPresent(System.out::println);

        // ifPresentOrElse (added in Java 9) - handles both cases
        printer3.ifPresentOrElse(System.out::println, () -> System.out.println("Printer3 is empty"));
    }

    /**
     * Demonstrates the use of stream operations on Optional objects.
     * 
     * This method shows how to use the stream() method to create a stream from an
     * Optional and then perform operations on that stream.
     * 
     * @param printer1 An Optional containing a Printer object
     * @param printer2 An Optional containing a Printer object
     */
    public static void doStreamOperations(Optional<Printer> printer1, Optional<Printer> printer2) {
        System.out.println("\n----- Stream Operations");
        List<Printer> results = printer1.stream().toList(); // List containing "Hello"
        List<Printer> emptyResults = printer2.stream().toList(); // Empty list
        System.out.println("Results: " + results);
        System.out.println("Empty results: " + emptyResults);
    }

    public static void main(String[] args) {

        // Non-Empty Optional but will throw NPE if null is passed
        Optional<Printer> printer1 = Optional.of(new Printer("HP001", 1000f));
        Optional<Printer> printer2 = Optional.empty(); // Empty Optional
        Optional<Printer> printer3 = Optional.ofNullable(null); // Safe for null values
        Optional<Printer> printer4 = Optional.of(new Printer("Ink707", 1500f));

        // ----- Basic checks
        doBasicChecks(printer1, printer2);

        // ----- Functional Operations
        doFunctionalOperations(printer1, printer2, printer4);

        // ----- Default value and actions
        doDefaultValueAndActions(printer1, printer2, printer3, printer4);

        // ----- Stream Operations
        doStreamOperations(printer1, printer2);
    }
}
