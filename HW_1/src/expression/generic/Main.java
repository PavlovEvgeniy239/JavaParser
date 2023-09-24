package expression.generic;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        GenericExpressionParser parser = new GenericExpressionParser();
        ExpGeneric exp = parser.parse("abs((abs(z) / (y + y)))", "d");
        System.out.println(exp.evaluate(new MyDouble(-7), new MyDouble(-8), new MyDouble(0)).getValue());
    }

    static <T> int func(T num) {
        int i = (int) num;
        return i;
    }
}
