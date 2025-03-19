package com.study.designpatterns.singleton;

public class SingletonCaller {
    public static void main(String[] args) {
        SingletonClassImpl instance1;
        // Creating first instance of the SingletonClassImpl class
        instance1 = SingletonClassImpl.getInstance();
        System.out.println(instance1);

        // Trying to create second instance of the SingletonClassImpl class
        // But will return the same instance as of first
        SingletonClassImpl instance2 = SingletonClassImpl.getInstance();
        System.out.println(instance2);

        if (instance1 == instance2)
            System.out.println("Both the instances are same.");
    }

}
