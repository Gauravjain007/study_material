package com.study.designpatterns.singleton;

public class SingletonClassImpl {

    // Private Static Instance of the Singleton class
    private static SingletonClassImpl instance;

    // Private Constructor
    private SingletonClassImpl() {
    }

    /**
     * Public Static Accessor to create/return the instance (only 1).
     * The {@code synchronized} keyword will make it Thread-safe.
     * 
     * @return SingletonClassImpl - instance
     */
    public static synchronized SingletonClassImpl getInstance() {
        if (instance == null) {
            instance = new SingletonClassImpl();
        }

        return instance;
    }

}
