package com.study.streams;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class DefaultFunctionalInterfacesImpl {

    public static void demonstratePredicate() {
        Predicate<Integer> isEven = a -> a % 2 == 0;
        Predicate<Integer> isPositive = a -> a > 0;

        System.out.println("Is 6 even? " + isEven.test(6)); // true
        System.out.println("Is 6 positive? " + isPositive.test(6)); // true

        Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
        System.out.println("Is -2 even and positive? " + isEvenAndPositive.test(-2)); // false

        Predicate<Integer> isEvenOrPositive = isEven.or(isPositive);
        System.out.println("Is -2 even or positive? " + isEvenOrPositive.test(-2)); // true

        Predicate<Object> isNotEqualTo7 = Predicate.isEqual(7).negate();
        System.out.println("Is -7 not equal to 7? " + isNotEqualTo7.test(-7)); // true
    }

    public static void demonstrateFunction() {
        Function<Integer, Integer> square = a -> a * a;
        Function<Integer, Integer> increment = a -> a + 1;

        System.out.println("Square of 4: " + square.apply(4)); // 16
        System.out.println("Increment 4: " + increment.apply(4)); // 5

        Function<Integer, Integer> squareThenIncrement = square.andThen(increment);
        System.out.println("Square then increment 4: " + squareThenIncrement.apply(4)); // 17

        Function<Integer, Integer> incrementThenSquare = square.compose(increment);
        System.out.println("Increment then square 4: " + incrementThenSquare.apply(4)); // 25

        // Acts similarly to Function but specifically for same-type operand and result
        UnaryOperator<Integer> doubleValue = a -> a * 2;
        System.out.println("Double of 5: " + doubleValue.apply(5)); // 10

        Function<Integer, Integer> identityFunction = Function.identity();
        System.out.println("Identity function on 5: " + identityFunction.apply(5)); // 5
    }

    public static void demonstrateConsumerAndSupplier() {
        Consumer<String> printConsumer = s -> System.out.println("Consumed: " + s);
        printConsumer.accept("Hello, World!"); // Consumed: Hello, World!

        Supplier<Double> randomSupplier = () -> Math.random();
        System.out.println("Random value: " + randomSupplier.get()); // Random value: <some random number>
    }

    public static void demonstrateBiFunctions() {
        BiPredicate<Integer, Integer> isFirstGreater = (a, b) -> a > b;
        System.out.println("Is 10 greater than 5? " + isFirstGreater.test(10, 5)); // true

        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("Sum of 5 and 10: " + add.apply(5, 10)); // 15

        // Acts similarly to BiFunction but specifically for same-type operands and
        // result
        BinaryOperator<Integer> add2 = (a, b) -> a + b;
        System.out.println("Sum of 20 and 30 using BinaryOperator: " + add2.apply(20, 30)); // 50

        BiConsumer<String, Integer> printNameAndAge = (name, age) -> System.out
                .println("Name: " + name + ", Age: " + age);
        printNameAndAge.accept("Alice", 30); // Name: Alice, Age: 30
    }

    public static void demonstrateMethodReferences() {
        // Method reference --> Use methods without invoking & in place of lambda
        // expressions
        Function<String, Integer> stringToInteger = Integer::valueOf;
        System.out.println("Integer value of \"123\": " + stringToInteger.apply("123")); // 123

        List<String> names = List.of("Bob", "Alice", "Charlie");
        names.forEach(System.out::println); // Prints each name

        // Constructor reference --> Use constructors without invoking & in place of
        // lambda expressions
        names.stream()
                .map(StringBuilder::new)
                .map(x -> x.append("X").toString())
                .forEach(System.out::println); // Prints each name with 'X' appended
    }

    public static void main(String[] args) {
        demonstratePredicate();
        demonstrateFunction();
        demonstrateConsumerAndSupplier();
        demonstrateBiFunctions();
        demonstrateMethodReferences();
    }
}
