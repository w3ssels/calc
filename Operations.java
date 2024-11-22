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
        } else {
            throw new Exception("Invalid basicOperation() operator: " + operator);
        }
    }

    public static Double logBase(double base, double n1) {
        if (base == Math.E) {
            return Math.log(n1);
        } else {
            return Math.log(n1)/Math.log(base);
        }
    }

}
