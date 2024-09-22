package com.study.oops.interfaces;

public interface Engine {
    static final int PRICE = 1_00_000;

    // Normal method declaration
    void showEngine();

    /**
     * Default Method - which may/may not be overridden
     */
    default void engineType() {
        System.out.println("Has Petrol Engine");
    }

    /**
     * Static Method - cannot be overridden and
     * can only be called directly through the Interface
     */
    static void enginePrice() {
        System.out.println("Engine Price: " + PRICE);
    }
}
