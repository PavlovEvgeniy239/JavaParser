package expression.generic;

public class GenericSquare extends GenericUnaryOperation {
    public GenericSquare(ExpGeneric expression) {
        super(expression);
    }

    @Override
    public <T extends MyNumber> MyNumber evaluate(T x, T y, T z) {
        return expression.evaluate(x, y, z).square();
    }
}
