# Java Arrays and Lists

## Table of Contents

1. [Arrays in Java](#arrays-in-java)
2. [List Interface and Implementations](#list-interface-and-implementations)
3. [Performance Analysis](#performance-analysis)
4. [Modern Java Features](#modern-java-features)
5. [Best Practices](#best-practices)
6. [Common Use Cases](#common-use-cases)

## Arrays in Java

### Basic Array Types

#### Primitive Arrays

```java
// Declaration and initialization
int[] numbers = new int[5];
int[] values = {1, 2, 3, 4, 5};
int[] data = new int[]{10, 20, 30};

// Multi-dimensional arrays
int[][] matrix = new int[3][4];
int[][] grid = {{1, 2}, {3, 4}, {5, 6}};
```

#### Object Arrays

```java
String[] names = new String[3];
String[] cities = {"New York", "London", "Tokyo"};
Person[] people = new Person[10];
```

### Array Characteristics

-   **Fixed Size**: Size determined at creation time
-   **Homogeneous**: All elements must be of the same type
-   **Zero-indexed**: First element at index 0
-   **Memory Efficient**: Contiguous memory allocation
-   **Direct Access**: O(1) random access by index

### Array Operations

```java
public class ArrayOperations {
    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 1, 9};

        // Access
        int first = arr[0];

        // Modification
        arr[1] = 10;

        // Length
        int length = arr.length;

        // Iteration
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

        // Enhanced for loop (Java 5+)
        for (int value : arr) {
            System.out.println(value);
        }
    }
}
```

## List Interface and Implementations

### List Interface Hierarchy

```
Collection<E>
    └── List<E>
        ├── ArrayList<E>
        ├── LinkedList<E>
        ├── Vector<E>
        │   └── Stack<E>
        └── CopyOnWriteArrayList<E>
```

### 1. ArrayList

#### Characteristics

-   **Resizable Array**: Dynamic size management
-   **Random Access**: O(1) access by index
-   **Insertion Order**: Maintains insertion order
-   **Null Values**: Allows null elements
-   **Not Thread-Safe**: Requires external synchronization

#### Implementation Details

```java
public class ArrayListExample {
    public static void main(String[] args) {
        // Creation
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> listWithCapacity = new ArrayList<>(20);
        ArrayList<String> listFromCollection = new ArrayList<>(Arrays.asList("a", "b", "c"));

        // Basic operations
        list.add("Java");
        list.add(0, "Hello");
        list.set(1, "World");
        String item = list.get(0);
        list.remove(0);
        list.remove("World");

        // Size and capacity
        int size = list.size();
        boolean isEmpty = list.isEmpty();
        list.trimToSize(); // Reduce capacity to current size

        // Bulk operations
        list.addAll(Arrays.asList("One", "Two", "Three"));
        list.removeAll(Arrays.asList("One", "Two"));
        list.retainAll(Arrays.asList("Three"));
    }
}
```

#### Internal Structure

-   **Backing Array**: Uses `Object[]` array internally
-   **Initial Capacity**: Default capacity of 10
-   **Growth Strategy**: Increases by 50% when capacity exceeded
-   **Memory Overhead**: ~33% due to unused capacity

### 2. LinkedList

#### Characteristics

-   **Doubly Linked List**: Each node has references to next and previous
-   **Sequential Access**: O(n) access by index
-   **Efficient Insertion/Deletion**: O(1) at known positions
-   **Memory Overhead**: Extra memory for node pointers

#### Implementation Details

```java
public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        // List operations
        list.add("First");
        list.addFirst("Beginning");
        list.addLast("End");
        list.add(1, "Middle");

        // Deque operations
        list.push("Stack Top");
        String popped = list.pop();
        list.offer("Queue Rear");
        String polled = list.poll();

        // Access operations
        String first = list.peekFirst();
        String last = list.peekLast();
        String element = list.get(2); // O(n) operation
    }
}
```

#### Node Structure

```java
private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

### 3. Vector and Stack

#### Vector

-   **Legacy Class**: Present since Java 1.0
-   **Thread-Safe**: All methods synchronized
-   **Dynamic Array**: Similar to ArrayList but synchronized
-   **Performance Impact**: Synchronization overhead

```java
Vector<String> vector = new Vector<>();
vector.addElement("Element"); // Legacy method
vector.add("Modern method");
```

#### Stack

-   **LIFO Structure**: Last In, First Out
-   **Extends Vector**: Inherits synchronization
-   **Legacy Implementation**: Consider ArrayDeque for new code

```java
Stack<Integer> stack = new Stack<>();
stack.push(10);
stack.push(20);
Integer top = stack.pop();
Integer peek = stack.peek();
boolean empty = stack.empty();
```

### 4. CopyOnWriteArrayList

#### Characteristics

-   **Thread-Safe**: Safe for concurrent access
-   **Copy-on-Write**: Creates new array for modifications
-   **Read-Optimized**: Multiple readers without locking
-   **Write-Expensive**: High cost for modifications

```java
CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();
cowList.add("Thread-safe");
// Excellent for scenarios with many reads, few writes
```

## Performance Analysis

### Time Complexity Comparison

| Operation        | ArrayList | LinkedList | Vector | CopyOnWriteArrayList |
| ---------------- | --------- | ---------- | ------ | -------------------- |
| Access by index  | O(1)      | O(n)       | O(1)   | O(1)                 |
| Add at end       | O(1)\*    | O(1)       | O(1)\* | O(n)                 |
| Add at beginning | O(n)      | O(1)       | O(n)   | O(n)                 |
| Add at middle    | O(n)      | O(1)\*\*   | O(n)   | O(n)                 |
| Remove by index  | O(n)      | O(n)\*\*   | O(n)   | O(n)                 |
| Search           | O(n)      | O(n)       | O(n)   | O(n)                 |

\*Amortized O(1), worst case O(n) when resizing
\*\*O(1) if you have direct reference to the node

### Space Complexity

-   **ArrayList**: O(n) + capacity overhead
-   **LinkedList**: O(n) + pointer overhead (24 bytes per node on 64-bit JVM)
-   **Vector**: O(n) + capacity overhead
-   **CopyOnWriteArrayList**: O(n) per snapshot

## Modern Java Features

### Java 8+ Stream API Integration

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Filtering and collecting
List<String> longNames = names.stream()
    .filter(name -> name.length() > 4)
    .collect(Collectors.toList());

// Mapping and reducing
int totalLength = names.stream()
    .mapToInt(String::length)
    .sum();

// Parallel processing
names.parallelStream()
    .forEach(System.out::println);
```

### Java 9: List.of() Factory Methods

```java
// Immutable lists
List<String> immutableList = List.of("A", "B", "C");
List<Integer> numbers = List.of(1, 2, 3, 4, 5);

// Empty immutable list
List<String> empty = List.of();

// Note: No null values allowed, throws NullPointerException
```

### Java 10: Local Variable Type Inference

```java
var list = new ArrayList<String>();
var names = List.of("John", "Jane", "Jack");
var filtered = names.stream()
    .filter(name -> name.startsWith("J"))
    .collect(Collectors.toList());
```

### Java 11: Collection.toArray() Enhancement

```java
List<String> list = Arrays.asList("A", "B", "C");
// New method with generator function
String[] array = list.toArray(String[]::new);
```

### Java 14+: Pattern Matching and Records

```java
// Using records with lists
public record Person(String name, int age) {}

List<Person> people = List.of(
    new Person("Alice", 30),
    new Person("Bob", 25)
);

// Pattern matching in switch (Java 17+)
public String processCollection(Collection<?> collection) {
    return switch (collection) {
        case List<?> list -> "List with " + list.size() + " elements";
        case Set<?> set -> "Set with " + set.size() + " elements";
        default -> "Other collection type";
    };
}
```

### Java 16: Stream.toList()

```java
List<String> result = names.stream()
    .filter(name -> name.length() > 3)
    .toList(); // Returns immutable list directly
```

### Java 21: Sequenced Collections

```java
// New methods available in List interface
List<String> list = new ArrayList<>(List.of("A", "B", "C"));

// New methods
String first = list.getFirst();  // Alternative to get(0)
String last = list.getLast();    // Alternative to get(size()-1)
list.addFirst("Start");          // Add at beginning
list.addLast("End");             // Add at end
String removedFirst = list.removeFirst();
String removedLast = list.removeLast();

// Reversed view
List<String> reversed = list.reversed();
```

## Best Practices

### Choosing the Right Implementation

#### Use ArrayList when:

-   Frequent random access by index
-   More reads than writes
-   Need to minimize memory overhead
-   Single-threaded or externally synchronized

#### Use LinkedList when:

-   Frequent insertions/deletions at beginning or middle
-   Implementing queue or deque operations
-   Size varies significantly
-   No need for random access

#### Use Vector when:

-   Legacy code compatibility required
-   Simple thread-safety needed (though Collections.synchronizedList() preferred)

#### Use CopyOnWriteArrayList when:

-   Many concurrent readers, few writers
-   Iteration must not see intermediate states
-   Can afford memory overhead for thread-safety

### Performance Optimization Tips

```java
public class OptimizationTips {
    // 1. Specify initial capacity when size is known
    List<String> optimized = new ArrayList<>(1000);

    // 2. Use primitive collections for better performance
    // Consider libraries like Eclipse Collections or Trove

    // 3. Batch operations when possible
    public void batchAdd(List<String> target, Collection<String> source) {
        target.addAll(source); // Better than individual add() calls
    }

    // 4. Use iterator for safe removal during iteration
    public void safeRemoval(List<String> list, String toRemove) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(toRemove)) {
                iterator.remove(); // Safe removal
            }
        }
    }

    // 5. Use ListIterator for bidirectional traversal
    public void bidirectionalTraversal(List<String> list) {
        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }
    }
}
```

### Thread Safety Considerations

```java
// Synchronized wrapper
List<String> syncList = Collections.synchronizedList(new ArrayList<>());

// Manual synchronization for iteration
synchronized (syncList) {
    for (String item : syncList) {
        System.out.println(item);
    }
}

// Concurrent alternatives
List<String> concurrentList = new CopyOnWriteArrayList<>();
// Or use concurrent collections from java.util.concurrent
```

## Common Use Cases

### 1. Data Processing Pipeline

```java
public class DataProcessor {
    public List<ProcessedData> processData(List<RawData> rawData) {
        return rawData.stream()
            .filter(this::isValid)
            .map(this::transform)
            .collect(Collectors.toList());
    }
}
```

### 2. Cache Implementation

```java
public class LRUCache<K, V> {
    private final int capacity;
    private final LinkedHashMap<K, V> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        };
    }
}
```

### 3. Builder Pattern with Lists

```java
public class ReportBuilder {
    private List<String> sections = new ArrayList<>();

    public ReportBuilder addSection(String section) {
        sections.add(section);
        return this;
    }

    public Report build() {
        return new Report(List.copyOf(sections)); // Immutable copy
    }
}
```

### 4. Event Handling

```java
public class EventManager {
    private final List<EventListener> listeners = new CopyOnWriteArrayList<>();

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void fireEvent(Event event) {
        // Safe iteration even if listeners are added/removed concurrently
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
```

## Memory Management and GC Impact

### ArrayList Memory Layout

-   Backing array allocated on heap
-   Unused capacity creates memory overhead
-   Resizing creates temporary double memory usage
-   Old arrays eligible for garbage collection

### LinkedList Memory Layout

-   Each node is separate object on heap
-   Higher memory fragmentation
-   More GC pressure due to many small objects
-   No wasted capacity

### GC Considerations

```java
// Good: Reuse lists when possible
private final List<String> reusableList = new ArrayList<>();

public void processItems(Collection<String> items) {
    reusableList.clear(); // Reuse existing capacity
    reusableList.addAll(items);
    // Process...
}

// Better for temporary operations: use streams
public List<String> filterAndTransform(List<String> input) {
    return input.stream()
        .filter(this::shouldInclude)
        .map(this::transform)
        .collect(Collectors.toList());
}
```
