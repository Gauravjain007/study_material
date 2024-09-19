package com.study.oops.concepts;

class Area {
    long length;
    long width;
    long height;

    /**
     * Constructor to initialize members
     * 
     * @param length
     * @param width
     * @param height
     */
    Area(long length, long width, long height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    /**
     * Calculate Area
     * 
     * @return Long
     */
    public long findArea() {
        return this.length * this.height * this.width;
    }
}

class SquareArea extends Area {
    /**
     * Constructor
     * 
     * @param side
     */
    SquareArea(long side) {
        // It calls the constructor of the Parent
        super(side, 0, 0);
    }

    /**
     * Calculates Area of Square
     */
    @Override
    public long findArea() {
        return super.length * super.length;
    }
}

class RectArea extends Area {
    /**
     * Constructor
     * 
     * @param length
     * @param breadth
     */
    RectArea(long length, long breadth) {
        // It calls the constructor of the Parent
        super(length, breadth, 0);
    }

    /**
     * Calculates Area of Rectangle
     */
    @Override
    public long findArea() {
        return super.length * super.width;
    }
}

public class InheritanceImpl {
    public static void main(String[] args) {
        Area area = new Area(10, 15, 20);
        System.out.println(area.findArea());

        SquareArea sq = new SquareArea(15);
        System.out.println(sq.findArea());

        RectArea rect = new RectArea(10, 20);
        System.out.println(rect.findArea());

        Area sqArea = new SquareArea(12);
        System.out.println(sqArea.findArea());
    }

}
