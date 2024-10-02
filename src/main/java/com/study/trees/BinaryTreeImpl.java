package com.study.trees;

import java.util.Scanner;

/**
 * Implements Basic Tree Data Structure
 */
public class BinaryTreeImpl {

    /**
     * Represents Node of a Tree
     */
    private static final class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public BinaryTreeImpl() {
        this.root = null;
    }

    /**
     * Populates the Tree by taking user inputs
     * 
     * @param scanner
     */
    public void populate(Scanner scanner) {
        System.out.print("Please enter the root value: ");
        int value = scanner.nextInt();
        this.root = new Node(value);
        populate(scanner, root);

    }

    /**
     * Prepares the Tree based on User Inputs
     * Inserted value should be of type Integer
     * 
     * @param scanner Scanner
     * @param parent  Node
     */
    private void populate(Scanner scanner, Node parent) {
        // Asks for Left Node Insertion
        System.out.print("Do you want to insert Left of " + parent.value + "(true/false): ");
        if (scanner.nextBoolean()) {
            System.out.print("Enter the Left Node Value: ");
            Node leftNode = new Node(scanner.nextInt());
            parent.left = leftNode;
            populate(scanner, leftNode);
        }

        // Asks for Right Node Insertion
        System.out.print("Do you want to insert Right of " + parent.value + ": ");
        if (scanner.nextBoolean()) {
            System.out.print("Enter the Right Node Value: ");
            Node rightNode = new Node(scanner.nextInt());
            parent.right = rightNode;
            populate(scanner, rightNode);
        }
    }

    /**
     * <pre>
     * Preety-Prints the tree [Vertical Tree Format]
     * E.g.
     *      |       |----> 15
     *      |----> 2
     *      5
     *      |       |----> 1
     *      |----> 7
     *      |       |----> 8
     * </pre>
     */
    public void displayTree() {
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
        BinaryTreeImpl tree = new BinaryTreeImpl();
        Scanner sc = new Scanner(System.in);
        tree.populate(sc);
        tree.displayTree();
    }
}
