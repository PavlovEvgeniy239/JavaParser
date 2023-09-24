package expression.generic;

public class MyLong extends MyNumber {
    private final long inside;

    public MyLong(long inside) {
        this.inside = inside;
    }

    @Override
    public MyNumber add(MyNumber arg) {
        long arg2 = (long) arg.getValue();
        return new MyLong(inside + arg2);
    }

    @Override
    public MyNumber subtract(MyNumber arg) {
        long arg2 = (long) arg.getValue();
        return new MyLong(inside - arg2);
    }

    @Override
    public MyNumber divide(MyNumber arg) {
        long arg2 = (long) arg.getValue();
        return new MyLong(inside / arg2);
    }

    @Override
    public MyNumber multiply(MyNumber arg) {
        long arg2 = (long) arg.getValue();
        return new MyLong(inside * arg2);
    }

    @Override
    public MyNumber mod(MyNumber arg) {
        return new MyLong(inside % (long) arg.getValue());
    }

    @Override
    public MyNumber negate() {
        return new MyLong(-this.inside);
    }

    @Override
    public MyNumber abs() {
        return new MyLong(Math.abs(inside));
    }

    @Override
    public MyNumber square() {
        long arg2 = (long) this.getValue();
        return new MyLong(inside * arg2);
    }


    @Override
    public Number getValue() {
        return inside;
    }
}
