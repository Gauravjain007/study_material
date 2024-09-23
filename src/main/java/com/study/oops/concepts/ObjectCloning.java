package com.study.oops.concepts;

import java.util.Arrays;

class Human implements Cloneable {
    String name;
    int age;
    int[] array;

    public Human(String name, int age, int[] array) {
        this.name = name;
        this.age = age;
        this.array = array;
    }

    /**
     * Creates a Shallow Copy of the Object
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected Object deepClone() throws CloneNotSupportedException {
        Human twin = (Human) super.clone();
        twin.array = Arrays.copyOf(this.array, this.array.length);
        return twin;
    }

    @Override
    public String toString() {
        return "Human [name=" + name + ", age=" + age + ", array=" + Arrays.toString(array) + "]";
    }
}

public class ObjectCloning {
    public static void main(String[] args) {
        Human human = new Human("Gaurav", 24, new int[] { 1, 5, 7, 3, 6 });
        System.out.println("Original Human: " + human);

        try {
            Human twin = (Human) human.clone();
            System.out.println("Twin: " + twin);
            twin.array[0] = 100;
            twin.age = 20;

            /**
             * The Human Array is also getting updated but not the
             * Human Age(Primitive type). This is because the clone method creates a
             * Shallow Copy of the Object
             */
            System.out.println("Updated Twin: " + twin);
            System.out.println("Human: " + human);

            Human deepTwin = (Human) human.deepClone();
            System.out.println("DeepTwin: " + deepTwin);
            deepTwin.array[0] = 200;
            deepTwin.name = "Gaurav Jain";

            /**
             * The Human Array is not getting updated because the clone method
             * used creates a Deep Copy of the Object
             */
            System.out.println("Updated DeepTwin: " + deepTwin);
            System.out.println("Human: " + human);

        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

}
