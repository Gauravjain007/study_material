package com.study.streams;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Employee {
        String name;
        int age;
        double salary;
        List<String> skills;

        public Employee() {
        }

        public Employee(String name, int age, double salary, List<String> skills) {
                this.name = name;
                this.age = age;
                this.salary = salary;
                this.skills = skills;
        }

        @Override
        public String toString() {
                return "Employee [name=" + name + ", age=" + age + ", salary=" + salary + ", skills=" + skills + "]";
        }

}

/**
 * This program demonstrates the use of Java 8 Stream API. It creates a list of
 * Employee objects and uses the Stream API to filter, map, reduce, sort, count,
 * min, max, flatMap, collect and perform other operations on the list.
 */
public class StreamsImpl {

        /**
         * Demonstrates various ways to create streams in Java 8.
         */
        public static void demonstrateStreamCreation() {
                // 1. Creating a stream from a Collection using stream()
                List<String> stringList = List.of("Apple", "Banana", "Cherry");
                Stream<String> stringStream = stringList.stream();
                stringStream.forEach(System.out::println);

                // 2. Creating a stream from an array using Stream.of()
                String[] stringArray = { "Date", "Elderberry", "Fig" };
                Stream<String> stringArrayStream1 = Stream.of(stringArray);
                stringArrayStream1.forEach(System.out::println);

                Stream<String> stringArrayStream2 = Stream.of("Grape", "Orange", "Watermelon");
                stringArrayStream2.forEach(System.out::println);

                // 3. Creating a stream using Arrays.stream()
                Stream<String> arraysStream = Arrays.stream(stringArray);
                arraysStream.forEach(System.out::println);

                // 4. Creating an infinite stream using Stream.generate()
                Stream<Double> randomNumbers = Stream.generate(Math::random).limit(5);
                randomNumbers.forEach(System.out::println);

                Stream<Integer> constantNumbers = Stream.generate(() -> 10).limit(5);
                constantNumbers.forEach(System.out::println);

                // 5. Creating an infinite stream using Stream.iterate()
                Stream<Integer> naturalNumbers = Stream.iterate(1, n -> n + 1).limit(5);
                naturalNumbers.forEach(System.out::println);

                // 6. Creating a stream using Stream.builder()
                Stream<String> builderStream = Stream.<String>builder()
                                .add("Kiwi")
                                .add("Lemon")
                                .add("Mango")
                                .build();
                builderStream.forEach(System.out::println);

                // 7. Creating a stream using Stream.empty()
                Stream<String> emptyStream = Stream.empty();
                System.out.println("Empty stream count: " + emptyStream.count());

                // 8. Creating a stream using Stream.ofNullable()
                String nullString = null;
                Stream<String> nullStream = Stream.ofNullable(nullString); // Creates an empty stream if null
                System.out.println("Null stream count: " + nullStream.count());

                // 9. Creating a stream from a String using chars() or codePoints()
                String sample = "Hello";
                Stream<Integer> charStream = sample.chars().boxed();
                Stream<Integer> codePointStream = sample.codePoints().boxed();
                charStream.forEach(System.out::println);
                codePointStream.forEach(System.out::println);
        }

        /**
         * Intermediate operations transform a stream into another stream. They are lazy
         * and are not executed until a terminal operation is invoked.
         * 
         * Demonstrates various intermediate operations on streams.
         */
        public static void demonstrateIntermediateOperations() {
                List<Integer> numbers = List.of(6, 3, 4, 1, 5, 2);

                // 1. Filter --> Retains elements that match a given predicate
                Stream<Integer> evenNumbers = numbers.stream().filter(n -> n % 2 == 0); // Till here no operation is
                                                                                        // executed
                // Terminal operation to trigger the processing
                System.out.println("Even numbers: " + evenNumbers.toList());

                // 2. Map --> Transforms each element using a given function
                Stream<Integer> squaredNumbers = numbers.stream().map(n -> n * n);
                System.out.println("Squared numbers: " + squaredNumbers.toList());

                // 3. Sorted --> Sorts the elements in natural order or using a comparator
                Stream<Integer> sortedNumbers = numbers.stream().sorted();
                System.out.println("Sorted numbers: " + sortedNumbers.toList());

                // Using custom comparator for sorting in descending order
                Stream<Integer> sortedDescNumbers = numbers.stream().sorted((a, b) -> Integer.compare(b, a));
                System.out.println("Sorted descending numbers: " + sortedDescNumbers.toList());

                // 4. Distinct --> Removes duplicate elements
                System.out.println("Distinct numbers: " + Stream.of(1, 2, 2, 3, 4, 4, 5).distinct().toList());

                // 5. Limit --> Limits the stream to a given number of elements
                System.out.println("First 3 numbers: " + numbers.stream().limit(3).toList());

                // 6. Skip --> Skips a given number of elements
                System.out.println("After skipping 2 numbers: " + numbers.stream().skip(2).toList());

                // 7. Peek --> Performs an action on each element without modifying the stream
                // Similar to forEach but is an intermediate operation
                System.out.println("Peek example: ");
                List<Integer> peekedNumbers = numbers.stream()
                                .peek(n -> System.out.println("Processing number: " + n))
                                .toList();
                System.out.println("Peeked numbers: " + peekedNumbers);

                // 8. FlatMap --> Flattens a stream of streams into a single stream
                Stream<List<Integer>> nestedNumbers = Stream.of(List.of(1, 5, 2), List.of(6, 3, 4));
                System.out.println("Sorted Flat numbers: "
                                + nestedNumbers.flatMap(List::stream).sorted().toList());
        }

        /**
         * Terminal operations are operations that produce a result or a side-effect and
         * mark the end of the stream processing. Once a terminal operation is invoked,
         * the stream pipeline is executed, and no further operations can be performed
         * on the stream.
         */
        public static void demonstrateTerminalOperations() {
                // 1. ForEach --> Performs an action for each element
                List<String> fruits = List.of("Apple", "Banana", "Cherry");
                fruits.forEach(System.out::println);

                // 2. Count --> Returns the number of elements in the stream
                System.out.println("Number of fruits: " + fruits.stream().count());

                // 3. Collect --> Collects the elements into a collection or a summary result
                List<String> upperFruits = fruits.stream()
                                .map(String::toUpperCase)
                                .collect(Collectors.toList());
                System.out.println("Uppercase fruits: " + upperFruits);

                // 4. toList or toArray --> Collects the elements into a List or Array
                String[] fruitArray = fruits.stream().toArray(String[]::new);
                System.out.println("Fruit Array: " + Arrays.toString(fruitArray));
                List<String> fruitList = fruits.stream().toList();
                System.out.println("Fruit list: " + fruitList);

                // 5. Reduce --> Reduces the elements to a single value using an accumulator
                List<Integer> numbers = List.of(1, 2, 3, 4, 5);
                int sum = numbers.stream().reduce(0, Integer::sum);
                System.out.println("Sum of numbers: " + sum);
                Optional<Integer> optionalProduct = numbers.stream().reduce((a, b) -> a * b);
                System.out.println("Product of numbers: " + optionalProduct.orElse(0));

                // 6. Min and Max --> Returns the minimum or maximum element based on a
                Optional<Integer> minNumber = numbers.stream().min(Integer::compareTo);
                System.out.println("Minimum number: " + minNumber.orElse(null));
                Optional<Integer> maxNumber = numbers.stream().max(Integer::compareTo);
                System.out.println("Maximum number: " + maxNumber.orElse(null));

                /**
                 * Short-Circuiting Operations: These operations can terminate the processing of
                 * a stream early based on certain conditions, potentially improving
                 * performance.
                 * Examples include: anyMatch, allMatch, noneMatch, findFirst, findAny, limit,
                 * and skip.
                 */

                // 7. AnyMatch, AllMatch, NoneMatch --> Returns true if any/all/no elements
                // match a predicate
                System.out.println("Any number is even: " + numbers.stream().anyMatch(n -> n % 2 == 0));
                System.out.println("All numbers are even: " + numbers.stream().allMatch(n -> n % 2 == 0));
                System.out.println("No number is negative: " + numbers.stream().noneMatch(n -> n < 0));

                // 8. FindFirst and FindAny --> Returns the first/any element in the stream
                Optional<Integer> firstNumber = numbers.stream().filter(x -> x % 2 == 0).findFirst();
                System.out.println("First even number: " + firstNumber.orElse(null));
                Optional<Integer> anyNumber = numbers.stream().filter(x -> x % 2 != 0).findAny();
                System.out.println("Any odd number: " + anyNumber.orElse(null));
        }

        /**
         * Demonstrates the use of Stream API with a custom Employee class.
         */
        public static void demonstrateUsingEmployeeExample() {
                Employee emp1 = new Employee("Gaurav", 24, 50000, List.of("Java", "Python"));
                Employee emp2 = new Employee("Saurav", 20, 10000, List.of("Java", "SQL", "Javascript"));
                Employee emp3 = new Employee("Bhairav", 27, 110000, List.of("React", "Javascript"));

                List<Employee> employees = List.of(emp1, emp2, emp3);

                // Stream forEach
                System.out.println("Employees: ");
                employees.forEach(System.out::println);

                // Stream filter
                System.out.println("Employees with age > 25: ");
                employees.stream().filter(e -> e.age > 25).forEach(System.out::println);

                // Stream map
                List<Employee> emps = employees.stream()
                                .map(e -> new Employee(e.name, e.age, e.salary * 1.15, e.skills))
                                .toList();
                System.out.println("Salary after 15% hike: " + emps);

                // Stream reduce
                double totalSalary = employees.stream().map(e -> e.salary).reduce(0.0, (a, b) -> a + b);
                System.out.println("Total salary: " + totalSalary);
                double totalSalary2 = employees.stream().map(e -> e.salary).reduce(0.0, Double::sum);
                System.out.println("Total salary 2: " + totalSalary2);

                // Stream sorted
                System.out.println("Employees sorted by salary: ");
                employees.stream().sorted((e1, e2) -> (int) (e1.salary - e2.salary)).forEach(System.out::println);

                // Stream count
                System.out.println("Number of employees: " + employees.stream().count());

                // Stream min
                System.out.println(
                                "Employee with min salary: "
                                                + employees.stream().min((e1, e2) -> (int) (e1.salary - e2.salary)));

                // Stream max
                System.out.println(
                                "Employee with max salary: "
                                                + employees.stream().max((e1, e2) -> (int) (e1.salary - e2.salary)));

                // Stream flatMap, collect
                System.out.println("Skills of employees: "
                                + employees.stream().flatMap(e -> e.skills.stream()).collect(Collectors.toSet()));

                // Stream anyMatch
                System.out.println(
                                "Is there any employee with age > 25: " + employees.stream().anyMatch(e -> e.age > 25));

                // Stream findFirst with orElse
                System.out.println(
                                "First employee with age > 25: "
                                                + employees.stream().filter(e -> e.age > 25).findFirst().orElse(null));

                // Stream findFirst with orElseThrow
                try {
                        System.out.println(
                                        "First employee with age > 29: "
                                                        + employees.stream().filter(e -> e.age > 29).findFirst()
                                                                        .orElseThrow());
                } catch (Exception e) {
                        System.out.println("Exception: " + e.getMessage());
                }

                // Stream shortCircuiting

                // Stream skip
                System.out.println("Skip first employee: " + employees.stream().skip(1).toList());

                // Stream limit
                System.out.println("Limit to First employee: " + employees.stream().limit(1).toList());

                // Parallel stream
                System.out.println("Parallel stream: ");
                employees.parallelStream().map(e -> e.salary).forEach(System.out::println);
        }

        public static void demonstrateCollectors() {
                List<Integer> nums = List.of(1, 2, 2, 4, 3, 6, 7, 1, 5, 7);

                // Collectors.toSet()
                System.out.println("Set: " + nums.stream().collect(Collectors.toSet()));

                // Collectors.toList()
                System.out.println("List: " + nums.stream().filter(x -> x % 2 == 0).collect(Collectors.toList()));

                // Collectors.toMap()
                System.out.println("Map: " + nums.stream().collect(Collectors.toMap(x -> x, x -> 1, Integer::sum)));

                // Collection to a specific collection type
                System.out.println("Specific Collection (ArrayDeque): "
                                + nums.stream().collect(Collectors.toCollection(ArrayDeque::new)));

                // Collectors.joining()
                List<String> words = List.of("Java", "Stream", "API", "Example");
                System.out.println("Joining: "
                                + words.stream().map(String::toUpperCase)
                                                .collect(Collectors.joining(" - ")));

                // Collectors.summarizingInt()
                // Will return count, sum, min, average, max
                IntSummaryStatistics stats = nums.stream().collect(Collectors.summarizingInt(Integer::intValue));
                System.out.println("Summary Statistics: " + stats);

                // Collectors individual operations
                System.out.println("Average: " + nums.stream().collect(Collectors.averagingInt(Integer::intValue)));

                System.out.println("Count: " + nums.stream().collect(Collectors.counting()));

                System.out.println("Max: " + nums.stream().collect(Collectors.maxBy(Integer::compareTo)).orElse(null));

                // GroupingBy
                List<String> items = List.of("apple", "banana", "apricot", "blueberry", "cherry", "avocado");
                System.out.println("Grouping by string length: "
                                + items.stream().collect(Collectors.groupingBy(String::length)));
                System.out.println("Grouping by String length and joining: "
                                + items.stream().collect(Collectors.groupingBy(String::length,
                                                Collectors.joining(", "))));
                System.out.println("Grouping by String length and counting: "
                                + items.stream().collect(Collectors.groupingBy(String::length,
                                                Collectors.counting())));

                // PartitioningBy
                System.out.println("Partitioning by String length: "
                                + items.stream().collect(Collectors.partitioningBy(x -> x.length() > 6)));

                // Mapping and collecting
                System.out.println("Mapping and Collecting: "
                                + items.stream().collect(Collectors.mapping(String::toUpperCase,
                                                Collectors.joining(", "))));

        }

        public static void main(String[] args) {
                demonstrateStreamCreation();
                demonstrateIntermediateOperations();
                demonstrateTerminalOperations();
                demonstrateUsingEmployeeExample();
                demonstrateCollectors();
        }

}
