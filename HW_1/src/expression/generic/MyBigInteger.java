package expression.generic;

import java.math.BigInteger;

public class MyBigInteger extends MyNumber {
    private final BigInteger inside;

    public MyBigInteger(BigInteger inside) {
        this.inside = inside;
    }

    @Override
    public MyNumber add(MyNumber arg) {
        return new MyBigInteger(inside.add((BigInteger) (arg.getValue())));
    }

    @Override
    public MyNumber subtract(MyNumber arg) {
        return new MyBigInteger(inside.subtract((BigInteger) (arg.getValue())));
    }

    @Override
    public MyNumber divide(MyNumber arg) {
        return new MyBigInteger(inside.divide((BigInteger) (arg.getValue())));
    }

    @Override
    public MyNumber multiply(MyNumber arg) {
        return new MyBigInteger(inside.multiply((BigInteger) (arg.getValue())));
    }

    @Override
    public MyNumber mod(MyNumber arg) {
        return new MyBigInteger(inside.mod((BigInteger) arg.getValue()));
    }

    @Override
    public MyNumber negate() {
        return new MyBigInteger(inside.multiply(new BigInteger(String.valueOf(-1))));
    }

    @Override
    public MyNumber abs() {
        if (inside.compareTo(BigInteger.ZERO) < 0) {
            return new MyBigInteger(inside.multiply(new BigInteger(String.valueOf(-1))));
        }
        return this;
    }

    @Override
    public MyNumber square() {
        return new MyBigInteger(inside.multiply(inside));
    }

    @Override
    public Number getValue() {
        return inside;
    }
}
