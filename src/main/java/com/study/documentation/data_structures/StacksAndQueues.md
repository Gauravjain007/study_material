## Stacks and Queues

### Stack

**Overview:** LIFO (Last In, First Out) data structure.

**Key Characteristics:**

-   Extends Vector class (legacy implementation)
-   Thread-safe but performance overhead
-   Modern alternative: ArrayDeque

**Time Complexity:**

-   Push: O(1)
-   Pop: O(1)
-   Peek: O(1)
-   Search: O(n)

**Implementation Example:**

```java
import java.util.*;

public class StackExample {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();

        // Push elements
        stack.push("First");
        stack.push("Second");
        stack.push("Third");

        // Peek top element (don't remove)
        String top = stack.peek();

        // Pop elements
        String popped = stack.pop();

        // Check if empty
        boolean isEmpty = stack.empty();

        // Search for element (returns 1-based position from top)
        int position = stack.search("First");

        // Modern alternative using ArrayDeque
        Deque<String> modernStack = new ArrayDeque<>();
        modernStack.push("Element");
        String element = modernStack.pop();
    }
}

// Custom Stack Implementation
class CustomStack<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int top;
    private int capacity;

    public CustomStack() {
        this.capacity = DEFAULT_CAPACITY;
        this.array = new Object[capacity];
        this.top = -1;
    }

    public void push(T item) {
        if (top == capacity - 1) {
            resize();
        }
        array[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = (T) array[top];
        array[top--] = null; // Help GC
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) array[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    private void resize() {
        capacity *= 2;
        array = Arrays.copyOf(array, capacity);
    }
}
```

**Common Use Cases:**

-   Expression evaluation and syntax parsing
-   Function call management
-   Undo operations
-   Backtracking algorithms
-   Browser history

---

### Queue (LinkedList/ArrayDeque)

**Overview:** FIFO (First In, First Out) data structure.

**Key Characteristics:**

-   Interface implemented by LinkedList, ArrayDeque, PriorityQueue
-   ArrayDeque is preferred implementation
-   Essential for BFS algorithms

**Time Complexity:**

-   Enqueue (offer): O(1)
-   Dequeue (poll): O(1)
-   Peek: O(1)

**Implementation Example:**

```java
import java.util.*;

public class QueueExample {
    public static void main(String[] args) {
        // Using LinkedList as Queue
        Queue<Integer> queue1 = new LinkedList<>();

        // Using ArrayDeque as Queue (preferred)
        Queue<Integer> queue2 = new ArrayDeque<>();

        // Adding elements
        queue2.offer(10);
        queue2.offer(20);
        queue2.offer(30);

        // Peek front element
        Integer front = queue2.peek();

        // Remove and return front element
        Integer removed = queue2.poll();

        // Check if empty
        boolean isEmpty = queue2.isEmpty();

        // Size
        int size = queue2.size();

        // Iteration
        for (Integer num : queue2) {
            System.out.println(num);
        }
    }
}

// Custom Queue Implementation using Array
class CustomQueue<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int front, rear, size, capacity;

    public CustomQueue() {
        this.capacity = DEFAULT_CAPACITY;
        this.array = new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void offer(T item) {
        if (size == capacity) {
            resize();
        }
        rear = (rear + 1) % capacity;
        array[rear] = item;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T item = (T) array[front];
        array[front] = null;
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) array[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize() {
        Object[] newArray = new Object[capacity * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(front + i) % capacity];
        }
        array = newArray;
        front = 0;
        rear = size - 1;
        capacity *= 2;
    }
}
```

---

### PriorityQueue

**Overview:** Heap-based priority queue where elements are ordered by priority.

**Key Characteristics:**

-   Min-heap by default (smallest element has highest priority)
-   Not thread-safe
-   Doesn't allow null elements
-   Unbounded queue

**Time Complexity:**

-   Insertion (offer): O(log n)
-   Removal (poll): O(log n)
-   Peek: O(1)
-   Remove arbitrary element: O(n)

**Implementation Example:**

```java
import java.util.*;

public class PriorityQueueExample {
    public static void main(String[] args) {
        // Min-heap (default)
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();

        // Max-heap using custom comparator
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

        // Custom objects with Comparable
        PriorityQueue<Task> taskPQ = new PriorityQueue<>();

        // Custom objects with Comparator
        PriorityQueue<Task> customPQ = new PriorityQueue<>((a, b) ->
            Integer.compare(a.priority, b.priority));

        // Adding elements
        minPQ.offer(30);
        minPQ.offer(10);
        minPQ.offer(20);

        // Peek min element
        Integer min = minPQ.peek(); // Returns 10

        // Remove min element
        Integer removed = minPQ.poll(); // Returns 10

        // Bulk operations
        List<Integer> list = Arrays.asList(50, 40, 60);
        minPQ.addAll(list);

        // Convert to sorted array
        Integer[] sorted = minPQ.toArray(new Integer[0]);
        Arrays.sort(sorted); // Note: toArray() doesn't guarantee order

        // Proper way to get sorted elements
        List<Integer> sortedList = new ArrayList<>();
        while (!minPQ.isEmpty()) {
            sortedList.add(minPQ.poll());
        }
    }
}

class Task implements Comparable<Task> {
    String name;
    int priority;

    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return name + "(" + priority + ")";
    }
}
```

**Common Use Cases:**

-   Dijkstra's shortest path algorithm
-   Huffman coding
-   Job scheduling
-   Finding k largest/smallest elements

---

### Deque (ArrayDeque)

**Overview:** Double-ended queue allowing insertion and removal at both ends.

**Key Characteristics:**

-   Resizable array implementation
-   More efficient than LinkedList for queue operations
-   Not thread-safe
-   Prohibits null elements

**Time Complexity:**

-   Insert/Remove at ends: O(1) amortized
-   Random access: O(1)
-   Insert/Remove at middle: O(n)

**Implementation Example:**

```java
import java.util.*;

public class DequeExample {
    public static void main(String[] args) {
        Deque<String> deque = new ArrayDeque<>();

        // Adding elements
        deque.addFirst("First");
        deque.addLast("Last");
        deque.offerFirst("NewFirst");
        deque.offerLast("NewLast");

        // Accessing elements
        String first = deque.peekFirst();
        String last = deque.peekLast();

        // Removing elements
        String removedFirst = deque.removeFirst();
        String removedLast = deque.removeLast();
        String polledFirst = deque.pollFirst();
        String polledLast = deque.pollLast();

        // Using as Stack
        deque.push("StackElement");
        String popped = deque.pop();

        // Using as Queue
        deque.offer("QueueElement");
        String element = deque.poll();

        // Iteration
        Iterator<String> it = deque.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // Reverse iteration
        Iterator<String> reverseIt = deque.descendingIterator();
        while (reverseIt.hasNext()) {
            System.out.println(reverseIt.next());
        }
    }
}
```
