package com.study.trees;

/**
 * Implements AVL Tree which is a Self-Balancing Binary Tree
 */
public class AVLTree {
    private static final class Node {
        private int value;
        private Node left;
        private Node right;
        private int height = 0; // Maintains height of the Node

        public Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public AVLTree() {
        this.root = null;
    }

    public int height(Node node) {
        if (null == node)
            return -1;
        return node.height;
    }

    /**
     * Inserts value into the Tree in such a way that -
     * Each node in a BST has at most two children, a left child and a right child,
     * with the left child containing values less than the parent node and the right
     * child containing values greater than the parent node.
     * 
     * After the Insertion it also verifies whether the BST is balanced or not
     * If not, then Balance the Tree
     * 
     * @param value Integer
     */
    private void insert(int value) {
        this.root = insert(this.root, value);
        if (!isBalanced()) {
            System.out.println("Is Unbalanced! After insertion of " + value);
        } else {
            System.out.println("Is Balanced after insertion of " + value);
        }
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

        // Finds the height of the current Node
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }

    /**
     * Checks if the BST is Balanced or not
     * 
     * @return Boolean - is Balanced or not
     */
    private boolean isBalanced() {
        return isBalanced(this.root);
    }

    /**
     * Checks if the Node is balanced or not
     * 1. left child's and Right child's height difference should be less than 1
     * 2. Left sub-tree should be balanced
     * 3. Right sub-tree should be balanced
     * 
     * @param node Node
     * @return Boolean - is Balanced or not
     */
    private boolean isBalanced(Node node) {
        if (null == node)
            return true;
        return Math.abs(height(node.left) - height(node.right)) <= 1 && isBalanced(node.left) && isBalanced(node.right);
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

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(7);
        tree.insert(12);
        tree.insert(23);
        tree.insert(1);
        tree.insert(14);
        tree.insert(15);
        tree.display();
    }
}
