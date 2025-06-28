## 4. Maps

### HashMap

**Overview:** Hash table implementation of Map interface.

**Key Characteristics:**

-   Key-value pairs
-   No duplicate keys
-   Allows one null key and multiple null values
-   No guaranteed order
-   Not thread-safe

**Time Complexity:**

-   Get: O(1) average, O(n) worst case
-   Put: O(1) average, O(n) worst case
-   Remove: O(1) average, O(n) worst case

**Implementation Example:**

```java
import java.util.*;

public class HashMapExample {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        // Adding key-value pairs
        map.put("Apple", 5);
        map.put("Banana", 3);
        map.put("Cherry", 8);
        map.put("Apple", 7); // Updates existing key

        // Getting values
        Integer appleCount = map.get("Apple"); // 7
        Integer defaultValue = map.getOrDefault("Orange", 0); // 0

        // Checking existence
        boolean hasKey = map.containsKey("Banana");
        boolean hasValue = map.containsValue(3);

        // Removing entries
        Integer removed = map.remove("Cherry");
        boolean removedKeyValue = map.remove("Banana", 3);

        // Iteration methods
        // 1. Iterate through keys
        for (String key : map.keySet()) {
            System.out.println(key + " = " + map.get(key));
        }

        // 2. Iterate through values
        for (Integer value : map.values()) {
            System.out.println(value);
        }

        // 3. Iterate through entries
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        // 4. Using forEach (Java 8+)
        map.forEach((key, value) -> System.out.println(key + " = " + value));

        // Advanced operations (Java 8+)
        map.putIfAbsent("Date", 2);
        map.compute("Apple", (key, value) -> value == null ? 1 : value + 1);
        map.computeIfAbsent("Elderberry", key -> key.length());
        map.computeIfPresent("Banana", (key, value) -> value * 2);
        map.merge("Fig", 1, Integer::sum);

        // Bulk operations
        HashMap<String, Integer> otherMap = new HashMap<>();
        otherMap.put("Grape", 4);
        otherMap.put("Apple", 10);
        map.putAll(otherMap);

        // Size and clear
        int size = map.size();
        boolean isEmpty = map.isEmpty();
        map.clear();
    }
}

// Custom HashMap Implementation (Simplified)
class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry<K, V>[] buckets;
    private int size;
    private int capacity;

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        this.capacity = DEFAULT_CAPACITY;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }

    public V put(K key, V value) {
        if (size >= capacity * LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(key);
        Entry<K, V> entry = buckets[index];

        while (entry != null) {
            if (Objects.equals(entry.key, key)) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
            entry = entry.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
        return null;
    }

    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = buckets[index];

        while (entry != null) {
            if (Objects.equals(entry.key, key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = buckets[index];
        Entry<K, V> prev = null;

        while (entry != null) {
            if (Objects.equals(entry.key, key)) {
                if (prev == null) {
                    buckets[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return entry.value;
            }
            prev = entry;
            entry = entry.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int getIndex(K key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % capacity;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldBuckets = buckets;
        capacity *= 2;
        buckets = new Entry[capacity];
        size = 0;

        for (Entry<K, V> entry : oldBuckets) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }
}
```

---

### LinkedHashMap

**Overview:** Hash table with linked list maintaining insertion or access order.

**Key Characteristics:**

-   Maintains insertion order (default) or access order
-   Slightly slower than HashMap due to maintaining links
-   Can be used to implement LRU cache

**Implementation Example:**

```java
import java.util.*;

public class LinkedHashMapExample {
    public static void main(String[] args) {
        // Insertion-order LinkedHashMap
        LinkedHashMap<String, Integer> insertionOrder = new LinkedHashMap<>();

        // Access-order LinkedHashMap (for LRU cache)
        LinkedHashMap<String, Integer> accessOrder = new LinkedHashMap<>(16, 0.75f, true);

        insertionOrder.put("First", 1);
        insertionOrder.put("Second", 2);
        insertionOrder.put("Third", 3);

        // Iteration maintains insertion order
        for (Map.Entry<String, Integer> entry : insertionOrder.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        // LRU Cache implementation
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.put("A", "ValueA");
        cache.put("B", "ValueB");
        cache.put("C", "ValueC");
        cache.get("A"); // A becomes most recently used
        cache.put("D", "ValueD"); // B gets evicted (least recently used)
    }
}

class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(16, 0.75f, true); // access-order
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
```

---

### TreeMap

**Overview:** Red-Black tree implementation of NavigableMap interface.

**Key Characteristics:**

-   Sorted order (natural ordering or custom Comparator)
-   No duplicate keys
-   NavigableMap operations
-   Not thread-safe
-   Doesn't allow null keys (but allows null values)

**Time Complexity:**

-   Get: O(log n)
-   Put: O(log n)
-   Remove: O(log n)
-   FirstKey/LastKey: O(log n)

**Implementation Example:**

```java
import java.util.*;

public class TreeMapExample {
    public static void main(String[] args) {
        // Natural ordering
        TreeMap<Integer, String> numbers = new TreeMap<>();

        // Custom ordering
        TreeMap<String, Integer> words = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        // Adding key-value pairs
        numbers.put(30, "Thirty");
        numbers.put(10, "Ten");
        numbers.put(20, "Twenty");
        numbers.put(40, "Forty");

        // Keys are automatically sorted
        System.out.println(numbers); // {10=Ten, 20=Twenty, 30=Thirty, 40=Forty}

        // NavigableMap operations
        Integer firstKey = numbers.firstKey(); // 10
        Integer lastKey = numbers.lastKey(); // 40
        Map.Entry<Integer, String> firstEntry = numbers.firstEntry();
        Map.Entry<Integer, String> lastEntry = numbers.lastEntry();

        // Floor, ceiling operations
        Integer floorKey = numbers.floorKey(25); // 20 (largest key <= 25)
        Integer ceilingKey = numbers.ceilingKey(25); // 30 (smallest key >= 25)
        Integer lowerKey = numbers.lowerKey(25); // 20 (largest key < 25)
        Integer higherKey = numbers.higherKey(25); // 30 (smallest key > 25)

        // Poll operations
        Map.Entry<Integer, String> pollFirst = numbers.pollFirstEntry();
        Map.Entry<Integer, String> pollLast = numbers.pollLastEntry();

        // SubMap operations
        SortedMap<Integer, String> headMap = numbers.headMap(25); // Keys < 25
        SortedMap<Integer, String> tailMap = numbers.tailMap(25); // Keys >= 25
        SortedMap<Integer, String> subMap = numbers.subMap(15, 35); // Keys [15, 35)

        // Descending operations
        NavigableMap<Integer, String> descendingMap = numbers.descendingMap();
        NavigableSet<Integer> descendingKeys = numbers.descendingKeySet();

        // Iteration (in sorted order)
        for (Map.Entry<Integer, String> entry : numbers.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
```

---

### Hashtable

**Overview:** Legacy synchronized hash table implementation of Map interface.

**Key Characteristics:**

-   Thread-safe (synchronized methods)
-   No null keys or values allowed
-   Legacy class from Java 1.0
-   Generally avoided in favor of HashMap or ConcurrentHashMap

**Implementation Example:**

```java
import java.util.*;

public class HashtableExample {
    public static void main(String[] args) {
        Hashtable<String, Integer> table = new Hashtable<>();

        // Basic operations (similar to HashMap)
        table.put("Key1", 100);
        table.put("Key2", 200);

        // Thread-safe operations
        Integer value = table.get("Key1");

        // Enumeration (legacy iteration)
        Enumeration<String> keys = table.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println(key + " = " + table.get(key));
        }

        Enumeration<Integer> values = table.elements();
        while (values.hasMoreElements()) {
            System.out.println(values.nextElement());
        }

        // Modern alternative: ConcurrentHashMap
        Map<String, Integer> concurrentMap = new java.util.concurrent.ConcurrentHashMap<>();
        concurrentMap.put("Key1", 100);
        concurrentMap.put("Key2", 200);
    }
}
```
