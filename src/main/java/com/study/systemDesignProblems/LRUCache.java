package com.study.systemDesignProblems;

import java.util.HashMap;
import java.util.Map;

class Node {
    int key;
    int value;
    Node prev;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

/**
 * LRU Cache Implementation
 */
public class LRUCache {

    private final Map<Integer, Node> cache;
    private final int capacity;
    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Returns the value associated with the given key if present in the cache,
     * otherwise -1.
     * If the key is present, the associated node is removed from its current
     * position and
     * inserted at the front of the cache.
     * 
     * @param key the key to look up in the cache
     * @return the value associated with the key if present, otherwise -1
     */
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        Node keyNode = cache.get(key);
        remove(keyNode);
        insertAtFront(keyNode);
        return keyNode.value;
    }

    /**
     * Adds a new key-value pair to the cache, or updates the value associated with
     * a key if it already exists.
     * If the cache is at capacity, the least recently used node is removed from the
     * cache and its associated key is removed from the cache map.
     * The new node is inserted at the front of the cache.
     * 
     * @param key   the key to add or update in the cache
     * @param value the value associated with the key
     */
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node keyNode = cache.get(key);
            keyNode.value = value;
            remove(keyNode);
            insertAtFront(keyNode);
        } else {
            if (cache.size() == capacity) {
                Node lru = tail.prev;
                remove(lru);
                cache.remove(lru.key);
            }

            Node newNode = new Node(key, value);
            insertAtFront(newNode);
            cache.put(key, newNode);
        }
    }

    /**
     * Removes a node from the doubly linked list.
     * 
     * @param node the node to remove from the list
     */
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /**
     * Inserts a node at the front of the doubly linked list.
     * 
     * @param node the node to insert at the front of the list
     */
    private void insertAtFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        System.out.println(lruCache.get(1));
        lruCache.put(3, 3);
        System.out.println(lruCache.get(2));
        lruCache.put(4, 4);
        System.out.println(lruCache.get(1));
        System.out.println(lruCache.get(3));
        System.out.println(lruCache.get(4));
    }
}