package expression.generic;

public class GenericDivide extends GenericBinaryOperation {
    public GenericDivide(ExpGeneric expression1, ExpGeneric expression2) {
        super(expression1, expression2);
    }

    @Override
    public <T extends MyNumber> MyNumber evaluate(T x, T y, T z) {
        return expression1.evaluate(x, y, z).divide(expression2.evaluate(x, y, z));
    }
}
