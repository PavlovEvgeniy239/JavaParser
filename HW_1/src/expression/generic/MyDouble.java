package expression.generic;

public class MyDouble extends MyNumber {
    private final double inside;

    public MyDouble(double inside) {
        this.inside = inside;
    }

    @Override
    public MyNumber add(MyNumber arg) {
        return new MyDouble(inside + (double) (arg.getValue()));
    }

    @Override
    public MyNumber subtract(MyNumber arg) {
        return new MyDouble(inside - (double) (arg.getValue()));
    }

    @Override
    public MyNumber divide(MyNumber arg) {
        return new MyDouble(inside / (double) (arg.getValue()));
    }

    @Override
    public MyNumber multiply(MyNumber arg) {
        return new MyDouble(inside * (double) (arg.getValue()));
    }

    @Override
    public MyNumber mod(MyNumber arg) {
        return new MyDouble(inside % (double) arg.getValue());
    }

    @Override
    public MyNumber negate() {
        return new MyDouble(-this.inside);
    }

    @Override
    public MyNumber abs() {
        return new MyDouble(Math.abs(inside));
    }

    @Override
    public MyNumber square() {
        return new MyDouble(inside * inside);
    }

    @Override
    public Number getValue() {
        return inside;
    }
}
