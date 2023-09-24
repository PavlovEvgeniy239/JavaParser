package expression.exceptions;

import expression.Exp;
import expression.Pow10;

public class CheckedPow10 extends Pow10 {
    public CheckedPow10(Exp inside) {
        super(inside);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int arg = getInside().evaluate(x, y, z);
        if (arg < 0 || arg > 9) {
            throw new IllegalArgumentException("overflow");
        }
        return super.evaluate(x, y, z);
    }
}
