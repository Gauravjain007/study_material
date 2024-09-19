package com.study.oops.access_modifiers.source_package;

public class SamePackageClass {
    public static void main(String[] args) {
        SourceClass sc = new SourceClass(1, "Gaurav", "HYD,IND", 8.56f, "HYD");

        // Private members are not allowed to be used directly outside the class
        // Hence to get the values we use getter/setters
        System.out.println(sc.getId());
        System.out.println(sc.getName());

        // All the other access-modifiers allows us to use the data members of
        // the same package outside classes
        System.out.println(sc.address);
        System.out.println(sc.gpa);
        System.out.println(sc.city);
    }
}
