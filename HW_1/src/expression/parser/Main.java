package expression.parser;

import expression.TripleExpression;

public class Main {
    public static void main(String[] args) {
        TripleParser parser = new ExpressionParser();
        TripleExpression exp = parser.parse("10000");
        System.out.println(exp.toMiniString());
        System.out.println(exp.evaluate(1, 2, 3));
    }
}