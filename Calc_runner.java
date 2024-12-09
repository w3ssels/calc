import java.util.Scanner;

public class Calc_runner {
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Simple Java Scientific Calculator");
        System.out.println("NOTE: Enter equations with a space between each operator. Example: 3 * ( 4 + 2 ) / sin pi");
        System.out.println("Enter equation or exit:");
        String input = scan.nextLine();
        String prec = "";
        while (!"exit".equals(input.toLowerCase())) {
            if ("c".equals(input.toLowerCase()) || "ac".equals(input.toLowerCase()) || "clear".equals(input.toLowerCase())) {
                prec = "";
            } else {
                Equation eq;
                if (!" ".equals(prec) && ! "".equals(prec)) {
                    eq = new Equation(CalcUtils.toPostfix((prec + input).split(" ")));
                } else {
                    eq = new Equation(CalcUtils.toPostfix(input.split(" ")));
                }
                try {
                    prec = Double.toString(eq.solve()) + " ";
                    System.out.println(prec);
                } catch (Exception e) {
                    System.out.println("ERROR: Invalid Input");
                    System.out.println(e);
                    prec = "";
                }
            }
            System.out.println("Enter equation, c/ac/clear to clear, or exit:");
            System.out.print(prec);
            input = scan.nextLine();
        }
    }

}
