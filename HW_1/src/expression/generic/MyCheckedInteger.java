package expression.generic;

public class MyCheckedInteger extends MyNumber {
    private final int inside;

    public MyCheckedInteger(int inside) {
        this.inside = inside;
    }

    @Override
    public MyNumber add(MyNumber arg) {
        int arg1 = inside;
        int arg2 = (int) arg.getValue();
        if (arg1 >= 0 && arg2 >= 0 && Integer.MAX_VALUE - arg2 < arg1) {
            throw new IllegalArgumentException("overflow");
        } else if (arg1 <= 0 && arg2 <= 0 && Integer.MIN_VALUE - arg2 > arg1) {
            throw new IllegalArgumentException("overflow");
        } else {
            return new MyCheckedInteger(arg1 + arg2);
        }
    }

    @Override
    public MyNumber subtract(MyNumber arg) {
        int arg1 = inside;
        int arg2 = (int) arg.getValue();
        if (arg1 >= 0 && arg2 <= 0 && Integer.MAX_VALUE + arg2 < arg1) {
            throw new IllegalArgumentException("overflow");
        }
        if (arg1 <= 0 && arg2 >= 0 && Integer.MIN_VALUE + arg2 > arg1) {
            throw new IllegalArgumentException("overflow");
        }
        return new MyCheckedInteger(arg1 - arg2);
    }

    @Override
    public MyNumber divide(MyNumber arg) {
        int arg1 = inside;
        int arg2 = (int) arg.getValue();
        if (arg2 == 0) {
            throw new IllegalArgumentException("division by zero");
        }
        if (arg1 == Integer.MIN_VALUE && arg2 == -1) {
            throw new IllegalArgumentException("overflow");
        }
        return new MyCheckedInteger(arg1 / arg2);
    }

    @Override
    public MyNumber multiply(MyNumber arg) {
        int arg1 = inside;
        int arg2 = (int) arg.getValue();
        if (arg2 == 0) {
            return new MyCheckedInteger(0);
        }
        if (arg1 >= 0 && arg2 >= 0 && Integer.MAX_VALUE / arg2 < arg1) {
            throw new IllegalArgumentException("overflow");
        }
        if (arg1 <= 0 && arg2 <= 0 && Integer.MAX_VALUE / arg2 > arg1) {
            throw new IllegalArgumentException("overflow");
        }
        if (arg1 <= 0 && arg2 >= 0 && Integer.MIN_VALUE / arg2 > arg1) {
            throw new IllegalArgumentException("overflow");
        }
        if (arg1 >= 0 && arg2 <= 0 && arg2 != -1 && Integer.MIN_VALUE / arg2 < arg1) {
            throw new IllegalArgumentException("overflow");
        }
        return new MyCheckedInteger(arg1 * arg2);
    }

    @Override
    public MyNumber mod(MyNumber arg) {
        return new MyCheckedInteger(inside % (int) arg.getValue());
    }

    @Override
    public MyNumber negate() {
        if (inside == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("overflow");
        }
        return new MyCheckedInteger(-inside);
    }

    @Override
    public MyNumber abs() {
        if (inside == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("overflow");
        }
        return new MyCheckedInteger(Math.abs(inside));
    }

    @Override
    public MyNumber square() {
        int arg1 = inside;
        int arg2 = (int) this.getValue();
        if (arg2 == 0) {
            return new MyCheckedInteger(0);
        }
        if (arg1 >= 0 && arg2 >= 0 && Integer.MAX_VALUE / arg2 < arg1) {
            throw new IllegalArgumentException("overflow");
        }
        if (arg1 <= 0 && arg2 <= 0 && Integer.MAX_VALUE / arg2 > arg1) {
            throw new IllegalArgumentException("overflow");
        }
        if (arg1 <= 0 && arg2 >= 0 && Integer.MIN_VALUE / arg2 > arg1) {
            throw new IllegalArgumentException("overflow");
        }
        if (arg1 >= 0 && arg2 <= 0 && arg2 != -1 && Integer.MIN_VALUE / arg2 < arg1) {
            throw new IllegalArgumentException("overflow");
        }
        return new MyCheckedInteger(arg1 * arg2);
    }


    @Override
    public Number getValue() {
        return inside;
    }
}
