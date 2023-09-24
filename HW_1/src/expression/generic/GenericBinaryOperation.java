package expression.generic;

public abstract class GenericBinaryOperation implements ExpGeneric {
    protected final ExpGeneric expression1;
    protected final ExpGeneric expression2;

    public GenericBinaryOperation(ExpGeneric expression1, ExpGeneric expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }
}
