package expression.exceptions;

import expression.Divide;
import expression.Exp;

public class CheckedDivide extends Divide {
    public CheckedDivide(Exp expression1, Exp expression2) {
        super(expression1, expression2);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int arg1 = getExpression1().evaluate(x, y, z);
        int arg2 = getExpression2().evaluate(x, y, z);
        if (arg2 == 0) {
            throw new IllegalArgumentException("division by zero");
        }
        if (arg1 == Integer.MIN_VALUE && arg2 == -1) {
            throw new IllegalArgumentException("overflow");
        }
        return arg1 / arg2;
    }
}
