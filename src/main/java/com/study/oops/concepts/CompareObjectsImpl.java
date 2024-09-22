package com.study.oops.concepts;

import java.util.Arrays;
import java.util.List;

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
    }
}
