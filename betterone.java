import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

class CalcUtils {
    private static final Map<String, Integer> precedenceMap = Map.of(
        "+", 2,
        "-", 2,
        "*", 3,
        "/", 3,
        "^", 4,
        "log", 5
    );

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input equation:");
        String input = scan.nextLine();
        ArrayList<String> tokens = tokenize(input);
        String[] postfix = toPostfix(tokens.toArray(new String[0]));
        System.out.println("Postfix: " + String.join(" ", postfix));
        System.out.println("Result: " + evaluatePostfix(postfix));
    }

    private static ArrayList<String> tokenize(String input) {
        ArrayList<String> tokens = new ArrayList<>();
        Pattern tokenPattern = Pattern.compile("\\d+(\\.\\d+)?|[a-zA-Z]+|[+\\-*/^()]");
        Matcher matcher = tokenPattern.matcher(input);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    public static String[] toPostfix(String[] tokens) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<String> stack = new ArrayList<>();
        Pattern functions = Pattern.compile("sin|cos|tan|arcsin|arccos|arctan");
        Pattern operators = Pattern.compile("\\+|\\-|\\*|\\/|log");

        for (int i = 0; i < tokens.length; i++) {
            try {
                output.add(Float.toString(Float.parseFloat(tokens[i])));
            } catch (Exception e) {
                Matcher funcMatch = functions.matcher(tokens[i]);
                Matcher opsMatch = operators.matcher(tokens[i]);

                if (tokens[i].equals("e") || tokens[i].equals("pi")) {
                    output.add(tokens[i].equals("e") ? String.valueOf(Math.E) : String.valueOf(Math.PI));
                } else if (funcMatch.find()) {
                    stack.add(tokens[i]);
                } else if (opsMatch.find()) {
                    while (!stack.isEmpty() && !"(".equals(stack.get(stack.size() - 1)) &&
                           precedenceMap.containsKey(stack.get(stack.size() - 1)) &&
                           precedenceMap.containsKey(tokens[i]) &&
                           precedenceMap.get(stack.get(stack.size() - 1)) >= precedenceMap.get(tokens[i])) {
                        output.add(stack.remove(stack.size() - 1));
                    }
                    stack.add(tokens[i]);
                } else if (tokens[i].equals("(")) {
                    stack.add(tokens[i]);
                } else if (tokens[i].equals(")")) {
                    while (!stack.isEmpty() && !stack.get(stack.size() - 1).equals("(")) {
                        output.add(stack.remove(stack.size() - 1));
                    }
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Mismatched parentheses: no matching '(' for ')'");
                    }
                    stack.remove(stack.size() - 1);
                    if (!stack.isEmpty() && funcMatch.reset(stack.get(stack.size() - 1)).find()) {
                        output.add(stack.remove(stack.size() - 1));
                    }
                }
            }
        }

        while (!stack.isEmpty()) {
            if ("(".equals(stack.get(stack.size() - 1))) {
                throw new IllegalArgumentException("Mismatched parentheses: no matching ')' for '('");
            }
            output.add(stack.remove(stack.size() - 1));
        }

        String[] retStrings = new String[output.size()];
        retStrings = output.toArray(retStrings);
        return retStrings;
    }

    public static float evaluatePostfix(String[] postfix) {
        Stack<Float> stack = new Stack<>();

        for (String token : postfix) {
            try {
                stack.push(Float.parseFloat(token));
            } catch (Exception e) {
                float b = stack.pop();
                float a = stack.isEmpty() ? 0 : stack.pop();

                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                    case "^": stack.push((float) Math.pow(a, b)); break;
                    case "log": stack.push((float) Math.log10(b)); break;
                    case "sin": stack.push((float) Math.sin(b)); break;
                    case "cos": stack.push((float) Math.cos(b)); break;
                    case "tan": stack.push((float) Math.tan(b)); break;
                    case "arcsin": stack.push((float) Math.asin(b)); break;
                    case "arccos": stack.push((float) Math.acos(b)); break;
                    case "arctan": stack.push((float) Math.atan(b)); break;
                    default: throw new IllegalArgumentException("Unknown operator or function: " + token);
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }

        return stack.pop();
    }
}
