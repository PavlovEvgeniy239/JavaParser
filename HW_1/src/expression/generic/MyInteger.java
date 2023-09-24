package expression.generic;

public class MyInteger extends MyNumber {
    private final int inside;

    public MyInteger(int inside) {
        this.inside = inside;
    }

    @Override
    public MyNumber add(MyNumber arg) {
        int arg2 = (int) arg.getValue();
        return new MyInteger(inside + arg2);
    }

    @Override
    public MyNumber subtract(MyNumber arg) {
        int arg2 = (int) arg.getValue();
        return new MyInteger(inside - arg2);
    }

    @Override
    public MyNumber divide(MyNumber arg) {
        int arg2 = (int) arg.getValue();
        return new MyInteger(inside / arg2);
    }

    @Override
    public MyNumber multiply(MyNumber arg) {
        int arg2 = (int) arg.getValue();
        return new MyInteger(inside * arg2);
    }

    @Override
    public MyNumber mod(MyNumber arg) {
        return new MyInteger(inside % (int) arg.getValue());
    }

    @Override
    public MyNumber negate() {
        return new MyInteger(-inside);
    }

    @Override
    public MyNumber abs() {
        return new MyInteger(Math.abs(inside));
    }

    @Override
    public MyNumber square() {
        return new MyInteger(inside * inside);
    }


    @Override
    public Number getValue() {
        return inside;
    }
}
