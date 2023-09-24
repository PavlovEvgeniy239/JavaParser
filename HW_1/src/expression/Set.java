package expression;

public class Set extends Operations {
    private final String OPERATION = "set";
    private final int PRIORITY = 0;

    public Set(Exp expression1, Exp expression2) {
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
        return getExpression1().evaluate(var) | 1 << getExpression2().evaluate(var);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getExpression1().evaluate(x, y, z) | 1 << getExpression2().evaluate(x, y, z);
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }
}
