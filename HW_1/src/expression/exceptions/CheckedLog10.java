package expression.exceptions;

import expression.Exp;
import expression.Log10;

public class CheckedLog10 extends Log10 {
    public CheckedLog10(Exp inside) {
        super(inside);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int arg = getInside().evaluate(x, y, z);
        if (arg <= 0) {
            throw new IllegalArgumentException("illegal string");
        }
        return super.evaluate(x, y, z);
    }
}
