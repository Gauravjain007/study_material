# Types of Linked Lists with Java Implementations

## Table of Contents

1. [Introduction to Linked Lists](#introduction-to-linked-lists)
2. [Singly Linked List](#singly-linked-list)
3. [Doubly Linked List](#doubly-linked-list)
4. [Circular Linked List](#circular-linked-list)
5. [Doubly Circular Linked List](#doubly-circular-linked-list)
6. [Self-Organizing Lists](#self-organizing-lists)
7. [Skip List](#skip-list)
8. [XOR Linked List](#xor-linked-list)
9. [Performance Comparison](#performance-comparison)
10. [Use Cases and Applications](#use-cases-and-applications)

## Introduction to Linked Lists

Linked lists are linear data structures where elements are stored in nodes, and each node contains data and reference(s) to other nodes. Unlike arrays, linked lists don't store elements in contiguous memory locations.

### Basic Node Structure

```java
public class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}
```

## Singly Linked List

A singly linked list is the simplest form where each node points to the next node in the sequence.

### Structure and Implementation

```java
public class SinglyLinkedList<T> {
    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Add element at the beginning
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // Add element at the end
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Add element at specific index
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            addFirst(data);
            return;
        }

        Node<T> newNode = new Node<>(data);
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    // Remove first element
    public T removeFirst() {
        if (head == null) {
            throw new RuntimeException("List is empty");
        }
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    // Remove element at specific index
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            return removeFirst();
        }

        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        T data = current.next.data;
        current.next = current.next.next;
        size--;
        return data;
    }

    // Get element at specific index
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // Check if list contains element
    public boolean contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Get size of the list
    public int size() {
        return size;
    }

    // Check if list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Display the list
    public void display() {
        Node<T> current = head;
        System.out.print("[");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println("]");
    }

    // Reverse the list
    public void reverse() {
        Node<T> prev = null;
        Node<T> current = head;
        Node<T> next;

        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }

    // Find middle element (Floyd's algorithm)
    public T findMiddle() {
        if (head == null) return null;

        Node<T> slow = head;
        Node<T> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow.data;
    }
}
```

### Usage Example

```java
public class SinglyLinkedListExample {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        list.addLast(10);
        list.addLast(20);
        list.addFirst(5);
        list.add(2, 15);

        list.display(); // [5 -> 10 -> 15 -> 20]

        System.out.println("Middle element: " + list.findMiddle()); // 10

        list.reverse();
        list.display(); // [20 -> 15 -> 10 -> 5]
    }
}
```

## Doubly Linked List

Each node contains references to both the next and previous nodes, allowing bidirectional traversal.

### Structure and Implementation

```java
public class DoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Add element at the beginning
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Add element at the end
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);

        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // Add element at specific index
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            addFirst(data);
            return;
        }

        if (index == size) {
            addLast(data);
            return;
        }

        Node<T> newNode = new Node<>(data);
        Node<T> current;

        // Optimize by choosing direction based on index
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    // Remove first element
    public T removeFirst() {
        if (head == null) {
            throw new RuntimeException("List is empty");
        }

        T data = head.data;

        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;
    }

    // Remove last element
    public T removeLast() {
        if (tail == null) {
            throw new RuntimeException("List is empty");
        }

        T data = tail.data;

        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }

    // Remove element at specific index
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        Node<T> current;

        // Optimize by choosing direction
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        T data = current.data;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return data;
    }

    // Get element at specific index
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current;

        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current.data;
    }

    // Display forward
    public void displayForward() {
        Node<T> current = head;
        System.out.print("Forward: [");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            current = current.next;
        }
        System.out.println("]");
    }

    // Display backward
    public void displayBackward() {
        Node<T> current = tail;
        System.out.print("Backward: [");
        while (current != null) {
            System.out.print(current.data);
            if (current.prev != null) {
                System.out.print(" <-> ");
            }
            current = current.prev;
        }
        System.out.println("]");
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
```

## Circular Linked List

The last node points back to the first node, creating a circular structure.

### Singly Circular Linked List

```java
public class CircularLinkedList<T> {
    private Node<T> tail; // We keep reference to tail instead of head
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public CircularLinkedList() {
        this.tail = null;
        this.size = 0;
    }

    // Add element at the beginning
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);

        if (tail == null) {
            tail = newNode;
            tail.next = tail; // Point to itself
        } else {
            newNode.next = tail.next;
            tail.next = newNode;
        }
        size++;
    }

    // Add element at the end
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);

        if (tail == null) {
            tail = newNode;
            tail.next = tail;
        } else {
            newNode.next = tail.next;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // Remove first element
    public T removeFirst() {
        if (tail == null) {
            throw new RuntimeException("List is empty");
        }

        Node<T> head = tail.next;
        T data = head.data;

        if (tail == head) { // Only one element
            tail = null;
        } else {
            tail.next = head.next;
        }
        size--;
        return data;
    }

    // Remove last element
    public T removeLast() {
        if (tail == null) {
            throw new RuntimeException("List is empty");
        }

        T data = tail.data;

        if (tail.next == tail) { // Only one element
            tail = null;
        } else {
            Node<T> current = tail.next;
            while (current.next != tail) {
                current = current.next;
            }
            current.next = tail.next;
            tail = current;
        }
        size--;
        return data;
    }

    // Display the circular list
    public void display() {
        if (tail == null) {
            System.out.println("[]");
            return;
        }

        Node<T> current = tail.next; // Start from head
        System.out.print("[");
        do {
            System.out.print(current.data);
            current = current.next;
            if (current != tail.next) {
                System.out.print(" -> ");
            }
        } while (current != tail.next);
        System.out.println(" -> (circular)]");
    }

    // Check if list contains element
    public boolean contains(T data) {
        if (tail == null) return false;

        Node<T> current = tail.next;
        do {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        } while (current != tail.next);

        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
```

## Doubly Circular Linked List

Combines features of doubly linked list and circular linked list.

```java
public class DoublyCircularLinkedList<T> {
    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    public DoublyCircularLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Add element at the beginning
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            Node<T> tail = head.prev;
            newNode.next = head;
            newNode.prev = tail;
            head.prev = newNode;
            tail.next = newNode;
            head = newNode;
        }
        size++;
    }

    // Add element at the end
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            Node<T> tail = head.prev;
            newNode.next = head;
            newNode.prev = tail;
            tail.next = newNode;
            head.prev = newNode;
        }
        size++;
    }

    // Remove first element
    public T removeFirst() {
        if (head == null) {
            throw new RuntimeException("List is empty");
        }

        T data = head.data;

        if (head.next == head) { // Only one element
            head = null;
        } else {
            Node<T> tail = head.prev;
            head = head.next;
            head.prev = tail;
            tail.next = head;
        }
        size--;
        return data;
    }

    // Display forward
    public void displayForward() {
        if (head == null) {
            System.out.println("[]");
            return;
        }

        Node<T> current = head;
        System.out.print("Forward: [");
        do {
            System.out.print(current.data);
            current = current.next;
            if (current != head) {
                System.out.print(" <-> ");
            }
        } while (current != head);
        System.out.println(" <-> (circular)]");
    }

    // Display backward
    public void displayBackward() {
        if (head == null) {
            System.out.println("[]");
            return;
        }

        Node<T> current = head.prev; // Start from tail
        System.out.print("Backward: [");
        do {
            System.out.print(current.data);
            current = current.prev;
            if (current != head.prev) {
                System.out.print(" <-> ");
            }
        } while (current != head.prev);
        System.out.println(" <-> (circular)]");
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
```

## Self-Organizing Lists

Lists that reorganize themselves based on access patterns to improve performance.

### Move-to-Front (MTF) List

```java
public class MoveToFrontList<T> {
    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        int accessCount;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.accessCount = 0;
        }
    }

    public MoveToFrontList() {
        this.head = null;
        this.size = 0;
    }

    // Add element
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // Search and move to front if found
    public boolean search(T data) {
        if (head == null) return false;

        // If first element is the target
        if (head.data.equals(data)) {
            head.accessCount++;
            return true;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                // Found the element, move it to front
                Node<T> target = current.next;
                current.next = target.next;
                target.next = head;
                head = target;
                target.accessCount++;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    // Display with access counts
    public void display() {
        Node<T> current = head;
        System.out.print("[");
        while (current != null) {
            System.out.print(current.data + "(" + current.accessCount + ")");
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println("]");
    }
}
```

### Frequency-Based Self-Organizing List

```java
public class FrequencyBasedList<T> {
    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        int frequency;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.frequency = 1;
        }
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public boolean search(T data) {
        Node<T> current = head;

        while (current != null) {
            if (current.data.equals(data)) {
                current.frequency++;
                reorganize(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void reorganize(Node<T> target) {
        // Move node based on frequency
        if (target == head) return;

        // Remove target from current position
        Node<T> prev = head;
        while (prev.next != target) {
            prev = prev.next;
        }
        prev.next = target.next;

        // Find correct position based on frequency
        if (target.frequency >= head.frequency) {
            target.next = head;
            head = target;
        } else {
            Node<T> current = head;
            while (current.next != null &&
                   current.next.frequency > target.frequency) {
                current = current.next;
            }
            target.next = current.next;
            current.next = target;
        }
    }

    public void display() {
        Node<T> current = head;
        System.out.print("[");
        while (current != null) {
            System.out.print(current.data + "(" + current.frequency + ")");
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println("]");
    }
}
```

## Skip List

A probabilistic data structure that allows fast search, insertion, and deletion.

```java
import java.util.Random;

public class SkipList<T extends Comparable<T>> {
    private static final int MAX_LEVEL = 16;
    private static final double P = 0.5;

    private Node<T> header;
    private int level;
    private Random random;

    private static class Node<T> {
        T data;
        Node<T>[] forward;

        @SuppressWarnings("unchecked")
        Node(T data, int level) {
            this.data = data;
            this.forward = new Node[level + 1];
        }
    }

    @SuppressWarnings("unchecked")
    public SkipList() {
        this.header = new Node<>(null, MAX_LEVEL);
        this.level = 0;
        this.random = new Random();
    }

    private int randomLevel() {
        int level = 0;
        while (random.nextDouble() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public void insert(T data) {
        Node<T>[] update = new Node[MAX_LEVEL + 1];
        Node<T> current = header;

        // Find position to insert
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null &&
                   current.forward[i].data.compareTo(data) < 0) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        // If data doesn't exist, insert it
        if (current == null || !current.data.equals(data)) {
            int newLevel = randomLevel();

            if (newLevel > level) {
                for (int i = level + 1; i <= newLevel; i++) {
                    update[i] = header;
                }
                level = newLevel;
            }

            Node<T> newNode = new Node<>(data, newLevel);

            for (int i = 0; i <= newLevel; i++) {
                newNode.forward[i] = update[i].forward[i];
                update[i].forward[i] = newNode;
            }
        }
    }

    public boolean search(T data) {
        Node<T> current = header;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null &&
                   current.forward[i].data.compareTo(data) < 0) {
                current = current.forward[i];
            }
        }

        current = current.forward[0];
        return current != null && current.data.equals(data);
    }

    public boolean delete(T data) {
        Node<T>[] update = new Node[MAX_LEVEL + 1];
        Node<T> current = header;

        // Find the node to delete
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null &&
                   current.forward[i].data.compareTo(data) < 0) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        if (current != null && current.data.equals(data)) {
            // Remove the node
            for (int i = 0; i <= level; i++) {
                if (update[i].forward[i] != current) break;
                update[i].forward[i] = current.forward[i];
            }

            // Update level
            while (level > 0 && header.forward[level] == null) {
                level--;
            }
            return true;
        }
        return false;
    }

    public void display() {
        System.out.println("Skip List:");
        for (int i = level; i >= 0; i--) {
            Node<T> current = header.forward[i];
            System.out.print("Level " + i + ": ");
            while (current != null) {
                System.out.print(current.data + " ");
                current = current.forward[i];
            }
            System.out.println();
        }
    }
}
```

## XOR Linked List

A memory-efficient doubly linked list that uses XOR operation to store both previous and next pointers in a single field.

```java
public class XORLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> npx; // XOR of next and previous

        Node(T data) {
            this.data = data;
            this.npx = null;
        }
    }

    public XORLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // XOR operation for nodes
    private Node<T> xor(Node<T> a, Node<T> b) {
        // In a real implementation, this would use memory addresses
        // For Java simulation, we'll use a different approach
        return (a == null) ? b : (b == null) ? a : null;
    }

    // Add at the beginning
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.npx = head;
            head.npx = xor(newNode, head.npx);
            head = newNode;
        }
        size++;
    }

    // Add at the end
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);

        if (tail == null) {
            head = tail = newNode;
        } else {
            newNode.npx = tail;
            tail.npx = xor(tail.npx, newNode);
            tail = newNode;
        }
        size++;
    }

    // Note: XOR linked list implementation in Java is limited
    // due to lack of direct memory address manipulation
    // This is a conceptual representation

    public void display() {
        System.out.println("XOR Linked List (conceptual): Size = " + size);
        // Actual traversal would require XOR operations on addresses
    }

    public int size() {
        return size;
    }
}
```

## Performance Comparison

| Operation               | Singly    | Doubly     | Circular  | Skip List      | XOR List  |
| ----------------------- | --------- | ---------- | --------- | -------------- | --------- |
| Insert at beginning     | O(1)      | O(1)       | O(1)      | O(log n)       | O(1)      |
| Insert at end           | O(n)      | O(1)       | O(1)      | O(log n)       | O(1)      |
| Insert at middle        | O(n)      | O(n)       | O(n)      | O(log n)       | O(n)      |
| Delete at beginning     | O(1)      | O(1)       | O(1)      | O(log n)       | O(1)      |
| Delete at end           | O(n)      | O(1)       | O(n)      | O(log n)       | O(1)      |
| Delete at middle        | O(n)      | O(n)       | O(n)      | O(log n)       | O(n)      |
| Search                  | O(n)      | O(n)       | O(n)      | O(log n)       | O(n)      |
| Access by index         | O(n)      | O(n)       | O(n)      | N/A            | O(n)      |
| Memory per node         | 1 pointer | 2 pointers | 1 pointer | Level pointers | 1 pointer |
| Bidirectional traversal | No        | Yes        | No        | No             | Yes       |

### Space Complexity Analysis

-   **Singly Linked List**: O(n) space, 8-16 bytes overhead per node
-   **Doubly Linked List**: O(n) space, 16-24 bytes overhead per node
-   **Circular Lists**: Same as their non-circular counterparts
-   **Skip List**: O(n log n) expected space due to multiple levels
-   **XOR List**: O(n) space, same as singly linked list

## Use Cases and Applications

### Singly Linked List Applications

```java
// 1. Implementation of Stack
public class LinkedStack<T> {
    private SinglyLinkedList<T> list = new SinglyLinkedList<>();

    public void push(T data) {
        list.addFirst(data);
    }

    public T pop() {
        return list.removeFirst();
    }

    public T peek() {
        return list.get(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}

// 2. Implementation of Queue
public class LinkedQueue<T> {
    private Node<T> front, rear;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public T dequeue() {
        if (front == null) {
            throw new RuntimeException("Queue is empty");
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        return data;
    }
}

// 3. Polynomial Representation
public class Polynomial {
    private SinglyLinkedList<Term> terms;

    private static class Term {
        int coefficient;
        int exponent;

        Term(int coefficient, int exponent) {
            this.coefficient = coefficient;
            this.exponent = exponent;
        }

        @Override
        public String toString() {
            return coefficient + "x^" + exponent;
        }
    }

    public Polynomial() {
        terms = new SinglyLinkedList<>();
    }

    public void addTerm(int coefficient, int exponent) {
        terms.addLast(new Term(coefficient, exponent));
    }

    public void display() {
        System.out.print("Polynomial: ");
        for (int i = 0; i < terms.size(); i++) {
            System.out.print(terms.get(i));
            if (i < terms.size() - 1) {
                System.out.print(" + ");
            }
        }
        System.out.println();
    }
}
```

### Doubly Linked List Applications

```java
// 1. Browser History Implementation
public class BrowserHistory {
    private DoublyLinkedList<String> history;
    private int currentIndex;

    public BrowserHistory() {
        history = new DoublyLinkedList<>();
        currentIndex = -1;
    }

    public void visit(String url) {
        // Remove all forward history
        while (history.size() > currentIndex + 1) {
            history.removeLast();
        }

        history.addLast(url);
        currentIndex++;
    }

    public String back() {
        if (currentIndex > 0) {
            currentIndex--;
            return history.get(currentIndex);
        }
        return null;
    }

    public String forward() {
        if (currentIndex < history.size() - 1) {
            currentIndex++;
            return history.get(currentIndex);
        }
        return null;
    }

    public String getCurrentUrl() {
        return currentIndex >= 0 ? history.get(currentIndex) : null;
    }
}

// 2. LRU Cache Implementation
public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> head;
    private final Node<K, V> tail;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) return null;

        moveToHead(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node<K, V> node = cache.get(key);

        if (node != null) {
            node.value = value;
            moveToHead(node);
        } else {
            Node<K, V> newNode = new Node<>(key, value);

            if (cache.size() >= capacity) {
                Node<K, V> removed = removeTail();
                cache.remove(removed.key);
            }

            cache.put(key, newNode);
            addToHead(newNode);
        }
    }

    private void addToHead(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addToHead(node);
    }

    private Node<K, V> removeTail() {
        Node<K, V> last = tail.prev;
        removeNode(last);
        return last;
    }
}

// 3. Undo/Redo Functionality
public class UndoRedoManager<T> {
    private DoublyLinkedList<T> states;
    private int currentStateIndex;

    public UndoRedoManager() {
        states = new DoublyLinkedList<>();
        currentStateIndex = -1;
    }

    public void saveState(T state) {
        // Remove all states after current
        while (states.size() > currentStateIndex + 1) {
            states.removeLast();
        }

        states.addLast(state);
        currentStateIndex++;
    }

    public T undo() {
        if (currentStateIndex > 0) {
            currentStateIndex--;
            return states.get(currentStateIndex);
        }
        return null;
    }

    public T redo() {
        if (currentStateIndex < states.size() - 1) {
            currentStateIndex++;
            return states.get(currentStateIndex);
        }
        return null;
    }

    public T getCurrentState() {
        return currentStateIndex >= 0 ? states.get(currentStateIndex) : null;
    }
}
```

### Circular Linked List Applications

```java
// 1. Round Robin Scheduling
public class RoundRobinScheduler<T> {
    private CircularLinkedList<T> processes;
    private Node<T> current;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public RoundRobinScheduler() {
        processes = new CircularLinkedList<>();
    }

    public void addProcess(T process) {
        processes.addLast(process);
        if (current == null) {
            // Set current to the first process added
            // This is a simplified representation
        }
    }

    public T getNextProcess() {
        if (current == null) return null;

        T process = current.data;
        current = current.next;
        return process;
    }
}

// 2. Josephus Problem Solution
public class JosephusProblem {
    public static int josephus(int n, int k) {
        CircularLinkedList<Integer> circle = new CircularLinkedList<>();

        // Add people to circle
        for (int i = 1; i <= n; i++) {
            circle.addLast(i);
        }

        // Simulate the elimination process
        int position = 0;
        while (circle.size() > 1) {
            position = (position + k - 1) % circle.size();
            circle.remove(position);
            if (position == circle.size()) {
                position = 0;
            }
        }

        return circle.get(0); // Last survivor
    }

    public static void main(String[] args) {
        System.out.println("Josephus(7, 3) = " + josephus(7, 3));
    }
}

// 3. Music Playlist (Continuous Play)
public class MusicPlaylist {
    private CircularLinkedList<String> playlist;
    private String currentSong;

    public MusicPlaylist() {
        playlist = new CircularLinkedList<>();
    }

    public void addSong(String song) {
        playlist.addLast(song);
    }

    public String nextSong() {
        // Implementation would track current position
        // and move to next song in circular fashion
        return "Next Song";
    }

    public String previousSong() {
        // Move backward in circular playlist
        return "Previous Song";
    }
}
```

### Skip List Applications

```java
// 1. Database Indexing
public class DatabaseIndex<K extends Comparable<K>, V> {
    private SkipList<IndexEntry<K, V>> index;

    private static class IndexEntry<K extends Comparable<K>, V>
            implements Comparable<IndexEntry<K, V>> {
        K key;
        V value;

        IndexEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(IndexEntry<K, V> other) {
            return this.key.compareTo(other.key);
        }
    }

    public DatabaseIndex() {
        index = new SkipList<>();
    }

    public void insert(K key, V value) {
        index.insert(new IndexEntry<>(key, value));
    }

    public boolean contains(K key) {
        return index.search(new IndexEntry<>(key, null));
    }

    public void delete(K key) {
        index.delete(new IndexEntry<>(key, null));
    }
}

// 2. Range Query Implementation
public class RangeQuerySkipList<T extends Comparable<T>> extends SkipList<T> {

    public List<T> rangeQuery(T start, T end) {
        List<T> result = new ArrayList<>();
        // Implementation would traverse skip list
        // collecting elements in range [start, end]
        return result;
    }

    public T findPredecessor(T target) {
        // Find largest element smaller than target
        return null; // Simplified
    }

    public T findSuccessor(T target) {
        // Find smallest element larger than target
        return null; // Simplified
    }
}
```

## Advanced Operations and Algorithms

### Merging Two Sorted Linked Lists

```java
public class LinkedListOperations {

    public static SinglyLinkedList<Integer> mergeSorted(
            SinglyLinkedList<Integer> list1,
            SinglyLinkedList<Integer> list2) {

        SinglyLinkedList<Integer> result = new SinglyLinkedList<>();
        int i = 0, j = 0;

        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i) <= list2.get(j)) {
                result.addLast(list1.get(i));
                i++;
            } else {
                result.addLast(list2.get(j));
                j++;
            }
        }

        while (i < list1.size()) {
            result.addLast(list1.get(i));
            i++;
        }

        while (j < list2.size()) {
            result.addLast(list2.get(j));
            j++;
        }

        return result;
    }

    // Detect cycle in linked list (Floyd's algorithm)
    public static <T> boolean hasCycle(Node<T> head) {
        if (head == null) return false;

        Node<T> slow = head;
        Node<T> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

    // Find intersection of two linked lists
    public static <T> Node<T> findIntersection(Node<T> head1, Node<T> head2) {
        if (head1 == null || head2 == null) return null;

        Node<T> ptr1 = head1;
        Node<T> ptr2 = head2;

        while (ptr1 != ptr2) {
            ptr1 = (ptr1 == null) ? head2 : ptr1.next;
            ptr2 = (ptr2 == null) ? head1 : ptr2.next;
        }

        return ptr1;
    }
}
```

### Memory Pool for Linked List Nodes

```java
public class NodePool<T> {
    private final Stack<Node<T>> pool;
    private final int maxSize;

    public NodePool(int maxSize) {
        this.maxSize = maxSize;
        this.pool = new Stack<>();
    }

    public Node<T> getNode(T data) {
        Node<T> node;
        if (pool.isEmpty()) {
            node = new Node<>(data);
        } else {
            node = pool.pop();
            node.data = data;
            node.next = null;
        }
        return node;
    }

    public void returnNode(Node<T> node) {
        if (pool.size() < maxSize) {
            node.data = null;
            node.next = null;
            pool.push(node);
        }
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }
}
```

## Testing and Examples

### Comprehensive Test Suite

```java
public class LinkedListTester {
    public static void main(String[] args) {
        testSinglyLinkedList();
        testDoublyLinkedList();
        testCircularLinkedList();
        testSkipList();
        testSelfOrganizingList();
    }

    private static void testSinglyLinkedList() {
        System.out.println("=== Testing Singly Linked List ===");
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        // Test insertions
        list.addLast(10);
        list.addLast(20);
        list.addFirst(5);
        list.add(2, 15);

        list.display(); // [5 -> 10 -> 15 -> 20]

        // Test removals
        System.out.println("Removed: " + list.remove(1)); // 10
        list.display(); // [5 -> 15 -> 20]

        // Test search
        System.out.println("Contains 15: " + list.contains(15)); // true
        System.out.println("Contains 100: " + list.contains(100)); // false

        // Test middle element
        System.out.println("Middle element: " + list.findMiddle()); // 15

        // Test reverse
        list.reverse();
        list.display(); // [20 -> 15 -> 5]
    }

    private static void testDoublyLinkedList() {
        System.out.println("\n=== Testing Doubly Linked List ===");
        DoublyLinkedList<String> list = new DoublyLinkedList<>();

        list.addLast("World");
        list.addFirst("Hello");
        list.add(1, "Beautiful");

        list.displayForward(); // Forward: [Hello <-> Beautiful <-> World]
        list.displayBackward(); // Backward: [World <-> Beautiful <-> Hello]

        // Test LRU Cache
        LRUCache<String, Integer> cache = new LRUCache<>(3);
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);

        System.out.println("Get A: " + cache.get("A")); // 1
        cache.put("D", 4); // Evicts B
        System.out.println("Get B: " + cache.get("B")); // null
    }

    private static void testCircularLinkedList() {
        System.out.println("\n=== Testing Circular Linked List ===");
        CircularLinkedList<Integer> list = new CircularLinkedList<>();

        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addFirst(0);

        list.display(); // [0 -> 1 -> 2 -> 3 -> (circular)]

        // Test Josephus problem
        System.out.println("Josephus(7, 3): " + JosephusProblem.josephus(7, 3));
    }

    private static void testSkipList() {
        System.out.println("\n=== Testing Skip List ===");
        SkipList<Integer> skipList = new SkipList<>();

        int[] values = {3, 6, 7, 9, 12, 19, 17, 26, 21, 25};
        for (int value : values) {
            skipList.insert(value);
        }

        skipList.display();

        System.out.println("Search 19: " + skipList.search(19)); // true
        System.out.println("Search 15: " + skipList.search(15)); // false

        skipList.delete(19);
        System.out.println("After deleting 19:");
        skipList.display();
    }

    private static void testSelfOrganizingList() {
        System.out.println("\n=== Testing Self-Organizing List ===");
        MoveToFrontList<String> mtfList = new MoveToFrontList<>();

        mtfList.add("Java");
        mtfList.add("Python");
        mtfList.add("C++");
        mtfList.add("JavaScript");

        System.out.println("Initial list:");
        mtfList.display(); // [JavaScript(0) -> C++(0) -> Python(0) -> Java(0)]

        System.out.println("Search Python: " + mtfList.search("Python"));
        mtfList.display(); // [Python(1) -> JavaScript(0) -> C++(0) -> Java(0)]

        System.out.println("Search Java: " + mtfList.search("Java"));
        mtfList.display(); // [Java(1) -> Python(1) -> JavaScript(0) -> C++(0)]

        System.out.println("Search Python again: " + mtfList.search("Python"));
        mtfList.display(); // [Python(2) -> Java(1) -> JavaScript(0) -> C++(0)]
    }
}
```
