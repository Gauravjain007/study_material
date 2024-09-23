package com.study.exception_handling;

public class ExceptionHandling {
    public static void main(String[] args) {
        try {
            int a = 5;
            int b = 6;
            int res = divide(a, b);
            processData(res);
            // Catches ArithmeticException thrown from divide()
        } catch (ArithmeticException e) {
            System.out.println("Found an ArithmeticException: " + e.getMessage());
            // Here it catches any Exception occuring apart from ArithmeticException
        } catch (Exception e) {
            System.out.println("Found an Exception: " + e.getMessage());
        } finally {
            /**
             * This block will always be reached at the end
             * no matter any Exception occurred or not
             */
            System.out.println("In the Finally Block");
        }
    }

    /**
     * This throws a Custom Exception - MyException
     * when the provided res is < 1. Else it simply prints
     * a message.
     * 
     * @param res
     * @throws MyException
     */
    public static void processData(int res) throws MyException {
        if (res < 1)
            throw new MyException("Got Wrong Result (less than 1)");
        else {
            System.out.println("The result is: " + res);
        }
    }

    /**
     * Divides the a - numerator by b - denominator
     * and throws Exception if b is 0.
     * 
     * @param a
     * @param b
     * @return
     * @throws ArithmeticException
     */
    public static int divide(int a, int b) throws ArithmeticException {
        if (b == 0)
            throw new ArithmeticException("Divisor is 0");
        return a / b;
    }
}
