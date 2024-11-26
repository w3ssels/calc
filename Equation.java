import java.util.ArrayList;
import java.util.Arrays;

public class Equation {
    private ArrayList<String> e;

    public Equation(String[] equation) {
        e = new ArrayList<String>(Arrays.asList(equation));
    }

    public double solve() throws Exception {
        while (e.size() > 1) {
            int i = 1;
            while (!(CalcUtils.isOperator(e.get(i)) || CalcUtils.isFunction(e.get(i)))) {
                i++;
            }
            if (CalcUtils.isFunction(e.get(i))) {
                e.set(i-1, Operations.evalFunction(e.get(i), Double.parseDouble(e.get(i-1))).toString());
                e.remove(i);
            } else {
                e.set(i-2, Operations.basicOperation(e.get(i), Double.parseDouble(e.get(i-2)), Double.parseDouble(e.get(i-1))).toString());
                e.remove(i);
                e.remove(i-1);
            }
        }
        return Double.parseDouble(e.get(0));
    }

    public int getLength() {
        return e.size();
    }

    public String getItem(int index) {
        return e.get(index);
    }

}
