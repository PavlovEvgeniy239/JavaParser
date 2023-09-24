package expression.generic;

public class GenericAdd extends GenericBinaryOperation {
    public GenericAdd(ExpGeneric expression1, ExpGeneric expression2) {
        super(expression1, expression2);
    }

    @Override
    public <T extends MyNumber> MyNumber evaluate(T x, T y, T z) {
        return expression1.evaluate(x, y, z).add(expression2.evaluate(x, y, z));
    }
}
