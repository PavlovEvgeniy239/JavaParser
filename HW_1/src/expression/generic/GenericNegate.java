package expression.generic;

public class GenericNegate extends GenericUnaryOperation {
    public GenericNegate(ExpGeneric expression) {
        super(expression);
    }

    @Override
    public <T extends MyNumber> MyNumber evaluate(T x, T y, T z) {
        return expression.evaluate(x, y, z).negate();
    }
}
