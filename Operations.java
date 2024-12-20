public class Operations {

    public static Double basicOperation(String operator, double n1, double n2) throws Exception {
        if ("+".equals(operator)) {
            return n1 + n2;
        } else if ("-".equals(operator)) {
            return n1 - n2;
        } else if ("/".equals(operator)) {
            return n1 / n2;
        } else if ("*".equals(operator)) {
            return n1 * n2;
        } else if ("^".equals(operator)) {
            return Math.pow(n1, n2);
        } else if ("log".equals(operator)) {
            return Logarithm.logBase(n1, n2);
        } else {
            throw new Exception("Invalid basicOperation() operator: " + operator);
        }
    }

    public static Double evalFunction(String function, Double num) throws Exception {
        if ("sin".equals(function)) {
            return Math.sin(num);
        } else if ("cos".equals(function)) {
            return Math.cos(num);
        } else if ("tan".equals(function)) {
            return Math.tan(num);
        } else if ("arcsin".equals(function)) {
            return Math.asin(num);
        } else if ("arccos".equals(function)) {
            return Math.acos(num);
        } else if ("arctan".equals(function)) {
            return Math.atan(num);
        } else if ("ln".equals(function)) {
            return Math.log(num);
        } else {
            throw new Exception("Unknown function: " + function);
        }
    }

}
