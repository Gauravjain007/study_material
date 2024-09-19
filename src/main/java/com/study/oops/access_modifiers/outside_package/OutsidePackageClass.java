package com.study.oops.access_modifiers.outside_package;

import com.study.oops.access_modifiers.source_package.SourceClass;

public class OutsidePackageClass {
    public static void main(String[] args) {
        SourceClass sc = new SourceClass(1, "Gaurav", "HYD,IND", 8.56f, "HYD");

        // Private members are not allowed to be used directly outside the class
        // Hence to get the values we use getter/setters
        System.out.println(sc.getId());
        System.out.println(sc.getName());

        // Default and Protected access-modifiers doesn't allows us to
        // use the members outside the same package classes
        // System.out.println(sc.address);
        // System.out.println(sc.gpa);
        System.out.println(sc.getAddress());
        System.out.println(sc.getGpa());

        // But Public allows us to access the member from anywhere
        System.out.println(sc.city);
    }
}
