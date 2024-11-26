public class Calc_runner {
    
    public static void main(String[] args) {
        String[] ogeq = args;
        System.out.println(ogeq);
        String[] post = CalcUtils.toPostfix(args);
        System.out.println(post);
        Equation eq = new Equation(post);
        try {
            System.out.println(eq.solve());
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}
