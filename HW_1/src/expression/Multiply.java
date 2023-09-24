package expression;

public class Multiply extends Operations {
    private final String OPERATION = "*";
    private final int PRIORITY = 2;

    public Multiply(Exp expression1, Exp expression2) {
        super(expression1, expression2);
    }

    @Override
    public int getPRIORITY() {
        return PRIORITY;
    }

    @Override
    public String getOPERATION() {
        return OPERATION;
    }

    @Override
    public int evaluate(int var) {
        return getExpression1().evaluate(var) * getExpression2().evaluate(var);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getExpression1().evaluate(x, y, z) * getExpression2().evaluate(x, y, z);
    }

    @Override
    public double evaluate(double x) {
        return getExpression1().evaluate(x) * getExpression2().evaluate(x);
    }

}
