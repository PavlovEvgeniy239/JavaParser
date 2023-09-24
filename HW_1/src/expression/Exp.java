package expression;

public interface Exp extends TripleExpression, Expression, DoubleExpression{
    int getPRIORITY();
    String getOPERATION();
}
