package expression.generic;

public abstract class GenericUnaryOperation implements ExpGeneric{
    protected final ExpGeneric expression;

    public GenericUnaryOperation(ExpGeneric expression) {
        this.expression = expression;
    }
}
