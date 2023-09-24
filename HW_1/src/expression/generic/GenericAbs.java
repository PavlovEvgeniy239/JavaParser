package expression.generic;

public class GenericAbs extends GenericUnaryOperation {
    public GenericAbs(ExpGeneric expression) {
        super(expression);
    }

    @Override
    public <T extends MyNumber> MyNumber evaluate(T x, T y, T z) {
        return expression.evaluate(x, y, z).abs();
    }
}
