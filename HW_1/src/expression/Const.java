package expression;

public class Const implements Exp {
    private int c;
    private final int PRIORITY = 3;
    private boolean isDouble = false;
    private double dc;


    public Const(double c) {
        this.dc = c;
        isDouble = true;
    }

    public Const(int c) {
        this.c = c;
    }

    @Override
    public int getPRIORITY() {
        return PRIORITY;
    }

    @Override
    public String getOPERATION() {
        return "";
    }

    @Override
    public int evaluate(int var) {
        return this.c;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.c;
    }

    @Override
    public String toString() {
        if (isDouble) {
            return String.valueOf(dc);
        } else {
            return String.valueOf(c);

        }
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public double evaluate(double x) {
        return dc;
    }

    @Override
    public boolean equals(Object expression) {
        if (expression instanceof Const) {
            if (isDouble) {
                return ((Const) expression).dc == this.dc;
            } else {
                return ((Const) expression).c == this.c;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (isDouble) {
            return Double.hashCode(dc);
        } else {
            return Integer.hashCode(c);
        }
    }
}
