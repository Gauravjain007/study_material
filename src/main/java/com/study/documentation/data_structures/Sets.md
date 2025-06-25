## Sets

### HashSet

**Overview:** Hash table implementation of Set interface.

**Key Characteristics:**

-   No duplicate elements
-   No guaranteed order
-   Allows one null element
-   Not thread-safe
-   Based on HashMap

**Time Complexity:**

-   Add: O(1) average, O(n) worst case
-   Remove: O(1) average, O(n) worst case
-   Contains: O(1) average, O(n) worst case

**Implementation Example:**

```java
import java.util.*;

public class HashSetExample {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();

        // Adding elements
        set.add("Apple");
        set.add("Banana");
        set.add("Cherry");
        set.add("Apple"); // Duplicate, won't be added

        // Checking existence
        boolean contains = set.contains("Apple");

        // Removing elements
        boolean removed = set.remove("Banana");

        // Size and empty check
        int size = set.size();
        boolean isEmpty = set.isEmpty();

        // Iteration
        for (String fruit : set) {
            System.out.println(fruit);
        }

        // Set operations
        HashSet<String> set2 = new HashSet<>(Arrays.asList("Cherry", "Date", "Elderberry"));

        // Union
        HashSet<String> union = new HashSet<>(set);
        union.addAll(set2);

        // Intersection
        HashSet<String> intersection = new HashSet<>(set);
        intersection.retainAll(set2);

        // Difference
        HashSet<String> difference = new HashSet<>(set);
        difference.removeAll(set2);

        // Convert to array
        String[] array = set.toArray(new String[0]);

        // Clear all elements
        set.clear();
    }
}

// Custom HashSet Implementation (Simplified)
class CustomHashSet<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private LinkedList<T>[] buckets;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public CustomHashSet() {
        this.capacity = DEFAULT_CAPACITY;
        this.buckets = new LinkedList[capacity];
        this.size = 0;

        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public boolean add(T element) {
        if (contains(element)) {
            return false;
        }

        if (size >= capacity * LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(element);
        buckets[index].add(element);
        size++;
        return true;
    }

    public boolean contains(T element) {
        int index = getIndex(element);
        return buckets[index].contains(element);
    }

    public boolean remove(T element) {
        int index = getIndex(element);
        boolean removed = buckets[index].remove(element);
        if (removed) {
            size--;
        }
        return removed;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int getIndex(T element) {
        return Math.abs(element.hashCode()) % capacity;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedList<T>[] oldBuckets = buckets;
        capacity *= 2;
        buckets = new LinkedList[capacity];
        size = 0;

        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }

        for (LinkedList<T> bucket : oldBuckets) {
            for (T element : bucket) {
                add(element);
            }
        }
    }
}
```

---

### LinkedHashSet

**Overview:** Hash table with linked list maintaining insertion order.

**Key Characteristics:**

-   Maintains insertion order
-   Slightly slower than HashSet due to maintaining links
-   All other characteristics same as HashSet

**Implementation Example:**

```java
import java.util.*;

public class LinkedHashSetExample {
    public static void main(String[] args) {
        LinkedHashSet<String> set = new LinkedHashSet<>();

        // Adding elements (order maintained)
        set.add("Third");
        set.add("First");
        set.add("Second");

        // Iteration maintains insertion order
        for (String element : set) {
            System.out.println(element); // Prints: Third, First, Second
        }

        // All HashSet operations work the same way
        boolean contains = set.contains("First");
        boolean removed = set.remove("Second");
    }
}
```

---

### TreeSet

**Overview:** NavigableSet implementation based on Red-Black tree.

**Key Characteristics:**

-   Sorted order (natural ordering or custom Comparator)
-   No duplicates
-   NavigableSet operations (floor, ceiling, etc.)
-   Not thread-safe
-   Doesn't allow null elements

**Time Complexity:**

-   Add: O(log n)
-   Remove: O(log n)
-   Contains: O(log n)
-   First/Last: O(log n)

**Implementation Example:**

```java
import java.util.*;

public class TreeSetExample {
    public static void main(String[] args) {
        // Natural ordering
        TreeSet<Integer> numbers = new TreeSet<>();

        // Custom ordering
        TreeSet<String> words = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        // Adding elements
        numbers.add(30);
        numbers.add(10);
        numbers.add(20);
        numbers.add(40);

        // Elements are automatically sorted
        System.out.println(numbers); // [10, 20, 30, 40]

        // NavigableSet operations
        Integer first = numbers.first(); // 10
        Integer last = numbers.last(); // 40
        Integer lower = numbers.lower(25); // 20 (largest element < 25)
        Integer floor = numbers.floor(25); // 20 (largest element <= 25)
        Integer ceiling = numbers.ceiling(25); // 30 (smallest element >= 25)
        Integer higher = numbers.higher(25); // 30 (smallest element > 25)

        // Poll operations (remove and return)
        Integer pollFirst = numbers.pollFirst(); // Removes and returns 10
        Integer pollLast = numbers.pollLast(); // Removes and returns 40

        // Subset operations
        SortedSet<Integer> headSet = numbers.headSet(25); // Elements < 25
        SortedSet<Integer> tailSet = numbers.tailSet(25); // Elements >= 25
        SortedSet<Integer> subSet = numbers.subSet(15, 35); // Elements [15, 35)

        // Descending operations
        NavigableSet<Integer> descendingSet = numbers.descendingSet();
        Iterator<Integer> descendingIterator = numbers.descendingIterator();

        // Custom objects
        TreeSet<Person> people = new TreeSet<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));
    }
}

class Person implements Comparable<Person> {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.age);
    }

    @Override
    public String toString() {
        return name + "(" + age + ")";
    }
}
```
