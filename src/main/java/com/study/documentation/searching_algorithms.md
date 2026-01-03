# Searching Algorithms Guide

## 1. Linear Search (Sequential Search)

**Concept**: Search through each element one by one until the target is found.

### Java Implementation:

```java
public class LinearSearch {
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Return index
            }
        }
        return -1; // Not found
    }

    // Generic version
    public static <T> int linearSearch(T[] arr, T target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }
}
```

### JavaScript Implementation:

```javascript
function linearSearch(arr, target) {
    for (let i = 0; i < arr.length; i++) {
        if (arr[i] === target) {
            return i;
        }
    }
    return -1;
}

// ES6 version
const linearSearchES6 = (arr, target) => {
    const index = arr.findIndex((element) => element === target);
    return index;
};
```

**Time Complexity**: O(n)  
**Space Complexity**: O(1)  
**When to Use**: Small datasets, unsorted arrays

---

## 2. Binary Search

**Concept**: Divide and conquer approach for sorted arrays. Compare target with middle element and eliminate half of the search space.

### Java Implementation:

```java
public class BinarySearch {
    // Iterative approach
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // Avoid overflow

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    // Recursive approach
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;

        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, right);
        } else {
            return binarySearchRecursive(arr, target, left, mid - 1);
        }
    }

    // Find first occurrence
    public static int findFirst(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                result = mid;
                right = mid - 1; // Continue searching in left half
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }
}
```

### JavaScript Implementation:

```javascript
function binarySearch(arr, target) {
    let left = 0;
    let right = arr.length - 1;

    while (left <= right) {
        const mid = Math.floor((left + right) / 2);

        if (arr[mid] === target) {
            return mid;
        } else if (arr[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return -1;
}

// Recursive version
function binarySearchRecursive(arr, target, left = 0, right = arr.length - 1) {
    if (left > right) return -1;

    const mid = Math.floor((left + right) / 2);

    if (arr[mid] === target) return mid;
    if (arr[mid] < target)
        return binarySearchRecursive(arr, target, mid + 1, right);
    return binarySearchRecursive(arr, target, left, mid - 1);
}
```

**Time Complexity**: O(log n)  
**Space Complexity**: O(1) iterative, O(log n) recursive  
**When to Use**: Sorted arrays, large datasets

---

## 3. Jump Search

**Concept**: Jump ahead by fixed steps, then do linear search in the identified block.

### Java Implementation:

```java
public class JumpSearch {
    public static int jumpSearch(int[] arr, int target) {
        int n = arr.length;
        int step = (int) Math.floor(Math.sqrt(n));
        int prev = 0;

        // Finding the block where element is present
        while (arr[Math.min(step, n) - 1] < target) {
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) {
                return -1;
            }
        }

        // Linear search in identified block
        while (arr[prev] < target) {
            prev++;
            if (prev == Math.min(step, n)) {
                return -1;
            }
        }

        if (arr[prev] == target) {
            return prev;
        }

        return -1;
    }
}
```

### JavaScript Implementation:

```javascript
function jumpSearch(arr, target) {
    const n = arr.length;
    let step = Math.floor(Math.sqrt(n));
    let prev = 0;

    // Find the block where element is present
    while (arr[Math.min(step, n) - 1] < target) {
        prev = step;
        step += Math.floor(Math.sqrt(n));
        if (prev >= n) return -1;
    }

    // Linear search in the block
    while (arr[prev] < target) {
        prev++;
        if (prev === Math.min(step, n)) return -1;
    }

    return arr[prev] === target ? prev : -1;
}
```

**Time Complexity**: O(√n)  
**Space Complexity**: O(1)  
**When to Use**: Large sorted arrays where binary search overhead is concern

---

## 4. Interpolation Search

**Concept**: Improved binary search for uniformly distributed data. Estimates position based on value.

### Java Implementation:

```java
public class InterpolationSearch {
    public static int interpolationSearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;

        while (low <= high && target >= arr[low] && target <= arr[high]) {
            if (low == high) {
                return arr[low] == target ? low : -1;
            }

            // Estimate position
            int pos = low + (((target - arr[low]) * (high - low)) / (arr[high] - arr[low]));

            if (arr[pos] == target) {
                return pos;
            } else if (arr[pos] < target) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }
        return -1;
    }
}
```

### JavaScript Implementation:

```javascript
function interpolationSearch(arr, target) {
    let low = 0;
    let high = arr.length - 1;

    while (low <= high && target >= arr[low] && target <= arr[high]) {
        if (low === high) {
            return arr[low] === target ? low : -1;
        }

        const pos =
            low +
            Math.floor(
                ((target - arr[low]) * (high - low)) / (arr[high] - arr[low])
            );

        if (arr[pos] === target) return pos;
        if (arr[pos] < target) low = pos + 1;
        else high = pos - 1;
    }
    return -1;
}
```

**Time Complexity**: O(log log n) best case, O(n) worst case  
**Space Complexity**: O(1)  
**When to Use**: Uniformly distributed sorted data

---

## 5. Exponential Search

**Concept**: Find range for binary search by repeated doubling.

### Java Implementation:

```java
public class ExponentialSearch {
    public static int exponentialSearch(int[] arr, int target) {
        if (arr[0] == target) {
            return 0;
        }

        int i = 1;
        while (i < arr.length && arr[i] <= target) {
            i = i * 2;
        }

        return binarySearch(arr, target, i / 2, Math.min(i, arr.length - 1));
    }

    private static int binarySearch(int[] arr, int target, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
```

### JavaScript Implementation:

```javascript
function exponentialSearch(arr, target) {
    if (arr[0] === target) return 0;

    let i = 1;
    while (i < arr.length && arr[i] <= target) {
        i *= 2;
    }

    return binarySearch(
        arr,
        target,
        Math.floor(i / 2),
        Math.min(i, arr.length - 1)
    );
}

function binarySearch(arr, target, left, right) {
    while (left <= right) {
        const mid = left + Math.floor((right - left) / 2);
        if (arr[mid] === target) return mid;
        if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

**Time Complexity**: O(log n)  
**Space Complexity**: O(1)  
**When to Use**: Unbounded/infinite arrays

---

## 6. Ternary Search

**Concept**: Divide search space into three parts instead of two.

### Java Implementation:

```java
public class TernarySearch {
    public static int ternarySearch(int[] arr, int target, int left, int right) {
        if (right >= left) {
            int mid1 = left + (right - left) / 3;
            int mid2 = right - (right - left) / 3;

            if (arr[mid1] == target) {
                return mid1;
            }
            if (arr[mid2] == target) {
                return mid2;
            }

            if (target < arr[mid1]) {
                return ternarySearch(arr, target, left, mid1 - 1);
            } else if (target > arr[mid2]) {
                return ternarySearch(arr, target, mid2 + 1, right);
            } else {
                return ternarySearch(arr, target, mid1 + 1, mid2 - 1);
            }
        }
        return -1;
    }
}
```

### JavaScript Implementation:

```javascript
function ternarySearch(arr, target, left = 0, right = arr.length - 1) {
    if (right >= left) {
        const mid1 = left + Math.floor((right - left) / 3);
        const mid2 = right - Math.floor((right - left) / 3);

        if (arr[mid1] === target) return mid1;
        if (arr[mid2] === target) return mid2;

        if (target < arr[mid1]) {
            return ternarySearch(arr, target, left, mid1 - 1);
        } else if (target > arr[mid2]) {
            return ternarySearch(arr, target, mid2 + 1, right);
        } else {
            return ternarySearch(arr, target, mid1 + 1, mid2 - 1);
        }
    }
    return -1;
}
```

**Time Complexity**: O(log₃ n)  
**Space Complexity**: O(log n)  
**When to Use**: Sorted arrays, theoretical interest (binary search is generally preferred)

---

## 7. Hash Table Search

**Concept**: Use hash function to directly access elements.

### Java Implementation:

```java
import java.util.*;

public class HashSearch {
    private Map<Integer, Integer> hashTable;

    public HashSearch(int[] arr) {
        hashTable = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            hashTable.put(arr[i], i);
        }
    }

    public int search(int target) {
        return hashTable.getOrDefault(target, -1);
    }

    // For multiple occurrences
    public List<Integer> searchAll(int[] arr, int target) {
        Map<Integer, List<Integer>> hashTable = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            hashTable.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }

        return hashTable.getOrDefault(target, new ArrayList<>());
    }
}
```

### JavaScript Implementation:

```javascript
class HashSearch {
    constructor(arr) {
        this.hashTable = new Map();
        arr.forEach((value, index) => {
            if (!this.hashTable.has(value)) {
                this.hashTable.set(value, []);
            }
            this.hashTable.get(value).push(index);
        });
    }

    search(target) {
        return this.hashTable.has(target) ? this.hashTable.get(target)[0] : -1;
    }

    searchAll(target) {
        return this.hashTable.get(target) || [];
    }
}

// Using JavaScript objects
function createHashTable(arr) {
    const hash = {};
    arr.forEach((value, index) => {
        if (!(value in hash)) hash[value] = [];
        hash[value].push(index);
    });
    return hash;
}
```

**Time Complexity**: O(1) average, O(n) worst case  
**Space Complexity**: O(n)  
**When to Use**: Fast lookups, multiple searches on same data

---

## 8. Depth-First Search (DFS) in Trees/Graphs

### Java Implementation:

```java
// Tree DFS
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

public class TreeDFS {
    public boolean searchDFS(TreeNode root, int target) {
        if (root == null) return false;
        if (root.val == target) return true;

        return searchDFS(root.left, target) || searchDFS(root.right, target);
    }

    // Iterative DFS
    public boolean searchDFSIterative(TreeNode root, int target) {
        if (root == null) return false;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.val == target) return true;

            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        return false;
    }
}

// Graph DFS
public class GraphDFS {
    public boolean searchDFS(List<List<Integer>> graph, int start, int target, boolean[] visited) {
        if (start == target) return true;

        visited[start] = true;

        for (int neighbor : graph.get(start)) {
            if (!visited[neighbor]) {
                if (searchDFS(graph, neighbor, target, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
}
```

### JavaScript Implementation:

```javascript
// Tree DFS
function searchTreeDFS(root, target) {
    if (!root) return false;
    if (root.val === target) return true;

    return (
        searchTreeDFS(root.left, target) || searchTreeDFS(root.right, target)
    );
}

// Iterative Tree DFS
function searchTreeDFSIterative(root, target) {
    if (!root) return false;

    const stack = [root];

    while (stack.length > 0) {
        const node = stack.pop();
        if (node.val === target) return true;

        if (node.right) stack.push(node.right);
        if (node.left) stack.push(node.left);
    }
    return false;
}

// Graph DFS
function searchGraphDFS(graph, start, target, visited = new Set()) {
    if (start === target) return true;

    visited.add(start);

    for (const neighbor of graph[start] || []) {
        if (!visited.has(neighbor)) {
            if (searchGraphDFS(graph, neighbor, target, visited)) {
                return true;
            }
        }
    }
    return false;
}
```

**Time Complexity**: O(V + E) for graphs, O(n) for trees  
**Space Complexity**: O(h) recursive, O(w) iterative (h=height, w=width)

---

## 9. Breadth-First Search (BFS)

### Java Implementation:

```java
// Tree BFS
public class TreeBFS {
    public boolean searchBFS(TreeNode root, int target) {
        if (root == null) return false;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.val == target) return true;

            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        return false;
    }
}

// Graph BFS
public class GraphBFS {
    public boolean searchBFS(List<List<Integer>> graph, int start, int target) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == target) return true;

            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
        return false;
    }
}
```

### JavaScript Implementation:

```javascript
// Tree BFS
function searchTreeBFS(root, target) {
    if (!root) return false;

    const queue = [root];

    while (queue.length > 0) {
        const node = queue.shift();
        if (node.val === target) return true;

        if (node.left) queue.push(node.left);
        if (node.right) queue.push(node.right);
    }
    return false;
}

// Graph BFS
function searchGraphBFS(graph, start, target) {
    const visited = new Set();
    const queue = [start];
    visited.add(start);

    while (queue.length > 0) {
        const node = queue.shift();
        if (node === target) return true;

        for (const neighbor of graph[node] || []) {
            if (!visited.has(neighbor)) {
                visited.add(neighbor);
                queue.push(neighbor);
            }
        }
    }
    return false;
}
```

**Time Complexity**: O(V + E) for graphs, O(n) for trees  
**Space Complexity**: O(w) where w is maximum width

---

## Common Interview Questions & Variations

### 1. **Find Peak Element**

```java
public int findPeakElement(int[] nums) {
    int left = 0, right = nums.length - 1;

    while (left < right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] > nums[mid + 1]) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}
```

### 2. **Search in Rotated Sorted Array**

```java
public int searchRotated(int[] nums, int target) {
    int left = 0, right = nums.length - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;

        if (nums[mid] == target) return mid;

        if (nums[left] <= nums[mid]) { // Left half is sorted
            if (nums[left] <= target && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else { // Right half is sorted
            if (nums[mid] < target && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    return -1;
}
```

### 3. **Search 2D Matrix**

```java
public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length;
    int left = 0, right = m * n - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        int midValue = matrix[mid / n][mid % n];

        if (midValue == target) return true;
        if (midValue < target) left = mid + 1;
        else right = mid - 1;
    }
    return false;
}
```

## Time & Space Complexity Comparison

| Algorithm     | Time (Average) | Time (Worst) | Space    | Prerequisites         |
| ------------- | -------------- | ------------ | -------- | --------------------- |
| Linear Search | O(n)           | O(n)         | O(1)     | None                  |
| Binary Search | O(log n)       | O(log n)     | O(1)     | Sorted array          |
| Jump Search   | O(√n)          | O(√n)        | O(1)     | Sorted array          |
| Interpolation | O(log log n)   | O(n)         | O(1)     | Uniformly distributed |
| Exponential   | O(log n)       | O(log n)     | O(1)     | Sorted array          |
| Ternary       | O(log₃ n)      | O(log₃ n)    | O(log n) | Sorted array          |
| Hash Table    | O(1)           | O(n)         | O(n)     | Extra space           |
| DFS           | O(V + E)       | O(V + E)     | O(h)     | Graph/Tree            |
| BFS           | O(V + E)       | O(V + E)     | O(w)     | Graph/Tree            |

## Key Interview Tips

1. **Always ask about constraints**: Array size, sorted/unsorted, duplicates allowed?
2. **Consider edge cases**: Empty array, single element, target not found
3. **Discuss trade-offs**: Time vs Space complexity
4. **Know when to use each algorithm**: Based on data characteristics
5. **Practice variations**: 2D arrays, rotated arrays, finding ranges
6. **Implement both recursive and iterative versions**: Especially for binary search
7. **Handle integer overflow**: Use `left + (right - left) / 2` instead of `(left + right) / 2`
