package com.study.oops.concepts;

import java.util.Arrays;
import java.util.Objects;

class Student implements Comparable<Student> {
    private String name;
    private int marks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    /**
     * Compares the two Students Marks and returns the difference between them
     * 
     * @return Integer - Difference between the Student Marks
     */
    @Override
    public int compareTo(Student student) {
        return (this.marks - student.marks);
    }

    /**
     * Compares this Student object to another object for equality.
     * 
     * @param obj the object to compare with this Student
     * @return true if the specified object is equal to this Student,
     *         otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Student student = (Student) obj;
        return marks == student.marks && Objects.equals(name, student.name);
    }

    /**
     * Generates a hash code for this Student object.
     * The hash code is computed based on the name and marks of the student.
     * 
     * @return a hash code value for this Student object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, marks);
    }

    @Override
    public String toString() {
        return "[" + name + " = " + marks + "]";
    }

}

public class CompareObjectsImpl {
    public static void main(String[] args) {
        Student s1 = new Student("Ryan", 67);
        Student s2 = new Student("John", 88);
        Student s3 = new Student("Henna", 11);
        Student s4 = new Student("Stuart", 56);
        Student s5 = new Student("Bill", 95);
        Student s6 = new Student("Zen", 67);

        if (s1.compareTo(s2) > 0) {
            System.out.println(String.format("%s has more marks than %s.", s1.getName(), s2.getName()));
        } else {
            System.out.println(String.format("%s has more marks than %s.", s2.getName(), s1.getName()));
        }

        Student[] arr = { s1, s2, s3, s4, s5, s6 };
        // Using the compareTo overidden function - Ascending Order
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        // Using the comparison - Descending Order
        Arrays.sort(arr, (a, b) -> -(a.getMarks() - b.getMarks()));
        System.out.println(Arrays.toString(arr));

        // Compare the objects
        if (s1.equals(s2)) {
            System.out.println("S1: " + s1 + " & S2: " + s2 + " are same.");
        } else {
            System.out.println("S1: " + s1 + " & S2: " + s2 + " are not same.");
        }
        Student s7 = new Student("Ryan", 67);
        if (s1.equals(s7)) {
            System.out.println("S1: " + s1 + " & S7: " + s7 + " are same.");
        } else {
            System.out.println("S1: " + s1 + " & S7: " + s7 + " are not same.");
        }

    }
}
