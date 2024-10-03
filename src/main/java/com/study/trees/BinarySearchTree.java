package com.study.trees;

/**
 * Implements Unbalanced Binary Search Tree
 */
public class BinarySearchTree {
    private static final String ARROW = " --> ";

    private static final class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Inserts value into the Tree in such a way that -
     * Each node in a BST has at most two children, a left child and a right child,
     * with the left child containing values less than the parent node and the right
     * child containing values greater than the parent node.
     * 
     * @param value Integer
     */
    private void insert(int value) {
        this.root = insert(this.root, value);
    }

    /**
     * Finds and places the value at the correct position
     * based on the BST algorithm
     * 
     * @param node  Node
     * @param value Integer
     * @return Node
     */
    private Node insert(Node node, int value) {
        // If node is null then add the value to the Node and return it
        if (null == node) {
            node = new Node(value);
            return node;
        }
        // If value is less than the Node value
        // Search in the Left Sub-Nodes
        else if (value < node.value) {
            node.left = insert(node.left, value);
        }
        // If value is greater-than or equal-to than the Node value
        // Search in the Right Sub-Nodes
        else {
            node.right = insert(node.right, value);
        }
        return node;
    }

    /**
     * Preety-Prints the tree [Vertical Tree Format]
     */
    public void display() {
        if (null == this.root)
            System.out.println("Empty Tree!");
        else
            display(this.root, 0);
    }

    /**
     * Preety-Prints the tree [Vertical Tree Format]
     * It requires a root node.
     * Prints the Tree from the right-most Leaf Node till the Left-Most Leaf
     * 
     * @param node  Node
     * @param level Integer
     */
    private void display(Node node, int level) {
        // Check if the node is not present
        if (node == null) {
            return;
        }

        // Traverses to the Right Node
        display(node.right, level + 1);

        // Prints the Nodes with indentation based on levels
        if (level != 0) {
            for (int i = 0; i < level - 1; i++) {
                System.out.print("|\t");
            }
            System.out.println("|----> " + node.value);
        } else {
            System.out.println(node.value);
        }

        // Traverses to the Left Node
        display(node.left, level + 1);
    }

    /**
     * Implements In-order Tree Traversal
     * Left -> Node -> Right
     */
    public void inOrderTraversal() {
        System.out.println("\nIn-Order Traversal: ");
        inOrderTraversal(this.root);
    }

    private void inOrderTraversal(Node node) {
        if (null == node)
            return;
        inOrderTraversal(node.left);
        System.out.print(ARROW + node.value);
        inOrderTraversal(node.right);
    }

    /**
     * Implements Pre-order Tree Traversal
     * Node -> Left -> Right
     */
    public void preOrderTraversal() {
        System.out.println("\nPre-Order Traversal: ");
        preOrderTraversal(this.root);
    }

    private void preOrderTraversal(Node node) {
        if (null == node)
            return;
        System.out.print(ARROW + node.value);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    /**
     * Implements Post-order Tree Traversal
     * Left -> Right -> Node
     */
    public void postOrderTraversal() {
        System.out.println("\nPost-Order Traversal: ");
        postOrderTraversal(this.root);
    }

    private void postOrderTraversal(Node node) {
        if (null == node)
            return;
        postOrderTraversal(node.left);
        postOrderTraversal(node.right);
        System.out.print(ARROW + node.value);
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(7);
        tree.insert(12);
        tree.insert(23);
        tree.insert(1);
        tree.insert(14);
        tree.display();
        tree.inOrderTraversal();
        tree.preOrderTraversal();
        tree.postOrderTraversal();
    }
}
