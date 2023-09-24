package expression.generic;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        GenericExpressionParser parser = new GenericExpressionParser();
        ExpGeneric exp = parser.parse(expression, mode);
        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        table[i - x1][j - y1][k - z1] = exp.evaluate(getMyNumber(i, mode), getMyNumber(j, mode), getMyNumber(k, mode)).getValue();
                    } catch (ArithmeticException | IllegalArgumentException e) {
                        table[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return table;
    }

    private MyNumber getMyNumber(int num, String mode) {
        return switch (mode) {
            case "i" -> new MyCheckedInteger(num);
            case "bi" -> new MyBigInteger(new BigInteger(String.valueOf(num)));
            case "d" -> new MyDouble(num);
            case "u" -> new MyInteger(num);
            case "l" -> new MyLong(num);
            case "s" -> new MyShort(num);
            default -> null;
        };
    }
}
