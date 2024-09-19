package com.study.oops.access_modifiers.source_package;

public class SourceClass {
    // Private Members
    private int id;
    private String name;

    // Protected Member
    protected String address;

    // Default/No-modifier Member
    float gpa;

    // Public Members
    public String city;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    /**
     * Every data member is accessible inside the class
     * irrespective of the access modifier type
     */
    public SourceClass(int id, String name, String address, float gpa, String city) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gpa = gpa;
        this.city = city;
    }

}
