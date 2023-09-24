package expression.exceptions;

import expression.Count;
import expression.Exp;

public class CheckedCount extends Count {
    public CheckedCount(Exp inside) {
        super(inside);
    }
}
