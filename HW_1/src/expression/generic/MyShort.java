package expression.generic;

public class MyShort extends MyNumber {
    private final short inside;

    public MyShort(int inside) {
        this.inside = (short) inside;
    }

    @Override
    public MyNumber add(MyNumber arg) {
        short arg2 = (short) arg.getValue();
        return new MyShort((short) (inside + arg2));
    }

    @Override
    public MyNumber subtract(MyNumber arg) {
        short arg2 = (short) arg.getValue();
        return new MyShort((short) (inside - arg2));
    }

    @Override
    public MyNumber divide(MyNumber arg) {
        short arg2 = (short) arg.getValue();
        return new MyShort((short) (inside / arg2));
    }

    @Override
    public MyNumber multiply(MyNumber arg) {
        short arg2 = (short) arg.getValue();
        return new MyShort((short) (inside * arg2));
    }

    @Override
    public MyNumber mod(MyNumber arg) {
        return new MyShort((short) (inside % (short) arg.getValue()));
    }

    @Override
    public MyNumber negate() {
        return new MyShort((short) -this.inside);
    }

    @Override
    public MyNumber abs() {
        return new MyShort((short) Math.abs(inside));
    }

    @Override
    public MyNumber square() {
        return new MyShort((short) (inside * inside));
    }


    @Override
    public Number getValue() {
        return inside;
    }
}
