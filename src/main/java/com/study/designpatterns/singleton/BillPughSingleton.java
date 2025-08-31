package com.study.designpatterns.singleton;

public class BillPughSingleton {

    // Static inner class - loaded only when referenced
    private static class BillPughInstanceCreator {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    private BillPughSingleton() {
        // Preventing Reflection
        if (BillPughInstanceCreator.INSTANCE != null) {
            throw new IllegalStateException("Singleton instance already exists!");
        }
    }

    public static BillPughSingleton getInstance() {
        return BillPughInstanceCreator.INSTANCE;
    }
}
