package com.study.streams;

import java.util.List;
import java.util.stream.Collectors;

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
    public static void main(String[] args) {
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

}
