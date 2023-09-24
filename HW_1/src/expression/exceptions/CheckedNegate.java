package expression.exceptions;

import expression.Exp;
import expression.Negate;

public class CheckedNegate extends Negate {
    public CheckedNegate(Exp inside) {
        super(inside);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int arg1 = getInside().evaluate(x, y, z);
        if (arg1 == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("overflow");
        }
        return -arg1;

    }
}
