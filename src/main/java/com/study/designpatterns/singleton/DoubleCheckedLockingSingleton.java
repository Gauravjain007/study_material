package com.study.designpatterns.singleton;

public class DoubleCheckedLockingSingleton {

    // volatile ensures proper initialization in multithreaded environment
    private static volatile DoubleCheckedLockingSingleton instance;

    private DoubleCheckedLockingSingleton() {
        // Prevent reflection-based instantiation
        if (instance != null) {
            throw new IllegalStateException("Singleton instance already exists!");
        }
    }

    public static DoubleCheckedLockingSingleton getInstance() {
        // First check without synchronization
        if (instance == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                // Second check with synchronization
                if (instance == null) {
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
        }
        return instance;
    }
}
