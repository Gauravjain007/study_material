# Operators in Java

The operators in the following table are all the types of operators used in Java and are listed according to precedence order.

-   All binary operators except for the assignment operators are evaluated from left to right.
-   Assignment operators are evaluated right to left.
-   Operators with higher precedence are evaluated before operators with relatively lower precedence.
-   Operators on the same line have equal precedence. When operators of equal precedence appear in the same expression, a rule must govern which is evaluated first.

---

| **Operator Type**          | **Operators**                                      |
|----------------------------|----------------------------------------------------|
| **Postfix**                | `expr++`, `expr--`                                 |
| **Unary**                  | `++expr`, `--expr`, `+expr`, `-expr`, `~`, `!`     |
| **Multiplicative**         | `*`, `/`, `%`                                      |
| **Additive**               | `+`, `-`                                           |
| **Shift**                  | `<<`, `>>`, `>>>`                                  |
| **Relational**             | `<`, `>`, `<=`, `>=`, `instanceof`                 |
| **Equality**               | `==`, `!=`                                         |
| **Bitwise AND**            | `&`                                                |
| **Bitwise Exclusive OR**   | `^`                                                |
| **Bitwise Inclusive OR**   | `|`                                                |
| **Logical AND**            | `&&`                                               |
| **Logical OR**             | `||`                                               |
| **Ternary**                | `? :`                                              |
| **Assignment**             | `=`, `+=`, `-=`, `*=`, `/=`, `%=`, `&=`, `^=`, `|=`, `<<=`, `>>=`, `>>>=` |

---


## Simple Assignment Operator

-   `=`: Simple assignment operator

## Arithmetic Operators

-   `+`: Additive operator (also used for String concatenation)
-   `-`: Subtraction operator
-   `*`: Multiplication operator
-   `/`: Division operator
-   `%`: Remainder operator

## Unary Operators

-   `+`: Unary plus operator; indicates positive value (numbers are positive without this, however)
-   `-`: Unary minus operator; negates an expression
-   `++`: Increment operator; increments a value by 1
-   `--`: Decrement operator; decrements a value by 1
-   `!`: Logical complement operator; inverts the value of a boolean

## Equality and Relational Operators

-   `==`: Equal to
-   `!=`: Not equal to
-   `>`: Greater than
-   `>=`: Greater than or equal to
-   `<`: Less than
-   `<=`: Less than or equal to

## Conditional Operators

-   `&&`: Conditional-AND
-   `||`: Conditional-OR
-   `?:`: Ternary (shorthand for if-then-else statement)

## Type Comparison Operator

-   `instanceof`: Compares an object to a specified type

## Bitwise and Bit Shift Operators

-   `~`: Unary bitwise complement
-   `<<`: Signed left shift
-   `>>`: Signed right shift
-   `>>>`: Unsigned right shift
-   `&`: Bitwise AND
-   `^`: Bitwise exclusive OR
-   `|`: Bitwise inclusive OR
