package expression.generic;

public class GenericConst implements ExpGeneric {
    protected final MyNumber expression;

    protected GenericConst(MyNumber num) {
        this.expression = num;
    }

    @Override
    public <T extends MyNumber> MyNumber evaluate(T x, T y, T z) {
        return expression;
    }
}
