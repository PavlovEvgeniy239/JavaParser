package expression.exceptions;

import expression.TripleExpression;


public class Main {
    public static void main(String[] args) throws Exception {
        TripleParser parser = new ExpressionParser();
        TripleExpression exp1 = parser.parse("pow10(0)");
        TripleExpression exp = parser.parse("1000000*x*x*x*x*x/(x-1)");
        for(int i = 0; i < 11; i++) {
            System.out.print(i + "    ");
            try {
                System.out.println(exp.evaluate(i, 0, 0));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}