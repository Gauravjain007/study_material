package com.study.oops.concepts;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class ObjectExternalizationImpl implements Externalizable {
    Integer id;
    String name;
    Float amt;

    public ObjectExternalizationImpl() {
    }

    public ObjectExternalizationImpl(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ObjectExternalizationImpl(int id, String name, Float amt) {
        this(id, name);
        this.amt = amt;
    }

    /**
     * Writes the state of the object to the stream.
     * The state of the object is written to the stream by writing the
     * name and amt fields in order.
     * 
     * @param out the stream to write the state to
     * @throws IOException if an error occurs while writing to the stream
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.name);
        out.writeFloat(this.amt);
    }

    /**
     * Reads the state of the object from the stream.
     * The state of the object is read from the stream by reading the
     * name and amt fields in order.
     * 
     * @param in the stream to read the state from
     * @throws IOException            if an error occurs while reading from the
     *                                stream
     * @throws ClassNotFoundException if the class of a serialized object
     *                                could not be found during deserialization
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = (String) in.readObject();
        this.amt = in.readFloat();
    }

    @Override
    public String toString() {
        return "ObjectExternalizationImpl [id=" + id + ", name=" + name + ", amt=" + amt + "]";
    }

    /**
     * The main method demonstrates the use of externalization by serializing and
     * deserializing an instance of ObjectExternalizationImpl. The process showcases
     * how specific fields of the object are handled during externalization.
     */
    public static void main(String[] args) {
        ObjectExternalizationImpl tt = new ObjectExternalizationImpl(20, "Name1", 30.5f);
        System.out.println("Done! t = " + tt);

        try (FileOutputStream fos = new FileOutputStream("abc1.ser");
                FileInputStream fis = new FileInputStream("abc1.ser");) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // Method for serialization of object
            oos.writeObject(tt);
            System.out.println("Object has been serialized");

            ObjectInputStream ois = new ObjectInputStream(fis);
            // Method for deserialization of object
            ObjectExternalizationImpl t2 = (ObjectExternalizationImpl) ois.readObject();
            System.out.println("Object has been deserialized Obj: " + t2);
        } catch (Exception e) {
            System.out.println("IOException is caught" + e);
        }
    }

}
