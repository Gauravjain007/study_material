package com.study.oops.concepts;

/**
 * Abstract Class as it contains an abstract method
 */
abstract class Career {
    /**
     * Abstract Method
     * * It never contains implementation
     * 
     * @param name
     * @return
     */
    abstract void myCareer(String name);

    protected Career() {
        System.out.println("Career Constructor");
    }
}

/**
 * Sub-class of the Career Class (Abstract)
 */
public class AbstractImpl extends Career {

    /**
     * It is mandatory to Override abstract methods
     * * Provides Implementation
     */
    @Override
    void myCareer(String name) {
        System.out.println("Career: " + name);
    }

    public static void main(String[] args) {
        AbstractImpl ob = new AbstractImpl();
        ob.myCareer("Coder");

        // Can't create an object of abstract class in the normal way
        // Career c = new Career();
    }

}
