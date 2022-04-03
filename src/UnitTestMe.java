// Author: Erik Macik
// CS3331 - SP22
// Unit Testing Assignment

// A class with methods that you will unit test using JUnit.
public class UnitTestMe {


    // Given any String, return a reversed version of the String.
    public static String reverseString(String input) {
        String result = "";
        for (int i = input.length() - 1; i >= 0; i--) {
            result = result + input.charAt(i);
        }
        return result;
    }

    // Given any String, return the same String with all white spaces removed.
    public static String removeWhiteSpace(String input) {
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isWhitespace(input.charAt(i))) {
                result = result + input.charAt(i);
            }
        }
        return result;
    }

    // Given any number, return the sum of all number from 0 to n.
    // For example if n = 4, return the sum 0 + 1 + 2 + 3 + 4.
    public static int recursiveSum(int n) {
        if (n == 0) {
            return 0;
        }
        return n + recursiveSum(n - 1);
    }

    // Given any number n, return the corresponding nth fibonacci number.
    public static int fibonacci(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}