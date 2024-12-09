import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;

class CalcUtils {

    // Operator precedence map
    private static final Map<String, Integer> precedenceMap = Map.of(
        "+", 2,
        "-", 2,
        "*", 3,
        "/", 3,
        "^", 4,
        "log", 5
    );

    public static void main(String[] args) {
        // Create a scanner to input the equation
        Scanner scan = new Scanner(System.in);
        System.out.println("Input equation:");
        String[] input = scan.nextLine().split(" "); // Split the input into tokens
        scan.close();
        // Convert to postfix and display the result
        System.out.println("Postfix: " + String.join(" ", toPostfix(input)));
    }

    public static String[] toPostfix(String[] tokens) {
        ArrayList<String> output = new ArrayList<String>(); // List to store output
        ArrayList<String> stack = new ArrayList<String>(); // Stack to store operators and parentheses

        // Define the patterns for functions and operators
        Pattern functions = Pattern.compile("sin|cos|tan|arcsin|arccos|arctan|ln");
        Pattern operators = Pattern.compile("\\+|\\-|\\*|\\/|log|\\^");

        // Process each token in the equation
        for (int i = 0; i < tokens.length; i++) {
            try {
                // Try parsing the token as a number (operand)
                if (!"-".equals(tokens[i]) && "-".equals(tokens[i].substring(0, 1))) {
                    output.add(Double.toString(-1*Double.parseDouble(tokens[i].substring(1))));
                } else {
                    output.add(Double.toString(Double.parseDouble(tokens[i])));
                }
            }
            catch (Exception e) {
                // If it's not a number, check if it's a function or operator
                Matcher funcMatch = functions.matcher(tokens[i]);
                Matcher opsMatch = operators.matcher(tokens[i]);

                // Handle constants 'e' and 'pi'
                if (tokens[i].equals("e") || tokens[i].equals("pi")) {
                    output.add(tokens[i]);
                } 
                // If it's a function (e.g., sin, cos, arcsin, etc.), push it onto the stack
                else if (funcMatch.find()) {
                    stack.add(tokens[i]);
                } 
                // If it's an operator, handle precedence and associativity
                else if (opsMatch.find()) {
                    // Ensure precedence is respected
                    while (!stack.isEmpty() && !"(".equals(stack.get(stack.size()-1)) && 
                           precedenceMap.get(stack.get(stack.size()-1)) >= precedenceMap.get(tokens[i]) && 
                           !(tokens[i].equals("^") || tokens[i].equals("log"))) {
                        output.add(stack.remove(stack.size()-1));
                    }
                    stack.add(tokens[i]);
                } 
                // Handle left parenthesis '(' by pushing it onto the stack
                else if (tokens[i].equals("(")) {
                    stack.add(tokens[i]);
                } 
                // Handle right parenthesis ')'
                else if (tokens[i].equals(")")) {
                    // Pop operators from the stack until we reach the corresponding left parenthesis '('
                    while (!stack.isEmpty() && !stack.get(stack.size()-1).equals("(")) {
                        output.add(stack.remove(stack.size()-1));
                    }

                    // Remove the left parenthesis '(' from the stack
                    if (!stack.isEmpty() && stack.get(stack.size()-1).equals("(")) {
                        stack.remove(stack.size()-1);
                    }

                    // Check if there's a function on the stack that needs to be moved to the output
                    if (!stack.isEmpty() && funcMatch.reset(stack.get(stack.size()-1)).find()) {
                        output.add(stack.remove(stack.size()-1));
                    }
                }
            }
        }

        // Pop any remaining operators from the stack to the output
        while (!stack.isEmpty()) {
            if (!"(".equals(stack.get(stack.size()-1))) {
                output.add(stack.remove(stack.size()-1));
            } else {
                throw new ArithmeticException("Mismatched parentheses");
            }
        }

        // Convert the output ArrayList to a string array and return it
        String[] retStrings = new String[output.size()];
        retStrings = output.toArray(retStrings);
        return retStrings;
    }

    public static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isOperator(String s) {
        Pattern operators = Pattern.compile("[\\+|\\-|\\*|\\/|log|\\^]$");
        Matcher opsMatch = operators.matcher(s);
        return opsMatch.find();
    }

    public static boolean isFunction(String s) {
        Pattern functions = Pattern.compile("sin|cos|tan|arcsin|arccos|arctan|ln");
        Matcher funcMatch = functions.matcher(s);
        return funcMatch.find();
    }

}
