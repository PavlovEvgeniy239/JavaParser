package expression.generic;

public class GenericMultiply extends GenericBinaryOperation {
    public GenericMultiply(ExpGeneric expression1, ExpGeneric expression2) {
        super(expression1, expression2);
    }

    @Override
    public <T extends MyNumber> MyNumber evaluate(T x, T y, T z) {
        return expression1.evaluate(x, y, z).multiply(expression2.evaluate(x, y, z));
    }
}
