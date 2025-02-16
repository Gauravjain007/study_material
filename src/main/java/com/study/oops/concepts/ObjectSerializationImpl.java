package com.study.oops.concepts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectSerializationImpl implements Serializable {
    Integer id;
    String name;
    Float amt;

    public ObjectSerializationImpl(int id, String name) {
        this.id = id;
        this.name = name;
        System.out.println("Const 1");
    }

    public ObjectSerializationImpl(int id, String name, Float amt) {
        this(id, name);
        this.amt = amt;
        System.out.println("Const 2");
    }

    @Override
    public String toString() {
        return "ObjectSerializationImpl [id=" + id + ", name=" + name + ", amt=" + amt + "]";
    }

    /**
     * The main method demonstrates the use of serialization by serializing and
     * deserializing an instance of ObjectSerializationImpl. The process showcases
     * how specific fields of the object are handled during serialization.
     */
    public static void main(String[] args) {
        ObjectSerializationImpl tt = new ObjectSerializationImpl(20, "Name1", 30.5f);
        System.out.println("Done! t = " + tt);

        try (FileOutputStream fos = new FileOutputStream("abc1.ser");
                FileInputStream fis = new FileInputStream("abc1.ser");) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // Method for serialization of object
            oos.writeObject(tt);
            System.out.println("Object has been serialized");

            ObjectInputStream ois = new ObjectInputStream(fis);
            // Method for deserialization of object
            ObjectSerializationImpl t2 = (ObjectSerializationImpl) ois.readObject();
            System.out.println("Object has been deserialized Obj: " + t2);
        } catch (Exception e) {
            System.out.println("IOException is caught" + e);
        }
    }

}
