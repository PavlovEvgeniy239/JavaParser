package expression.exceptions;

import expression.Exp;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(Exp expression1, Exp expression2) {
        super(expression1, expression2);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int arg1 = getExpression1().evaluate(x, y, z);
        int arg2 = getExpression2().evaluate(x, y, z);
        if (arg1 >= 0 && arg2 <= 0 && Integer.MAX_VALUE + arg2 < arg1) {
            throw new IllegalArgumentException("overflow");
        }
        if (arg1 <= 0 && arg2 >= 0 && Integer.MIN_VALUE + arg2 > arg1) {
            throw new IllegalArgumentException("overflow");
        }
        return arg1 - arg2;
    }
}
