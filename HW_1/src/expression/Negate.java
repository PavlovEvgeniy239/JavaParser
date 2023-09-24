package expression;

public class Negate implements Exp {
    private final String OPERATION = "-";
    private final int PRIORITY = 3;
    private final Exp inside;
    public Negate(Exp inside) {
        this.inside = inside;
    }
    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public int getPRIORITY() {
        return PRIORITY;
    }

    @Override
    public String getOPERATION() {
        return OPERATION;
    }

    @Override
    public int evaluate(int x) {
        return -inside.evaluate(x);
    }
    public Exp getInside() {
        return inside;
    }
    @Override
    public int evaluate(int x, int y, int z) {
        return -inside.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return OPERATION + "(" + inside + ")";
    }
    @Override
    public String toMiniString () {
        StringBuilder sb = new StringBuilder();
        sb.append(OPERATION);
        if (inside.getPRIORITY() == 3) {
            sb.append(" " + inside.toMiniString());
        } else {
            sb.append("(" + inside.toMiniString() + ")");
        }
        return sb.toString();
    }
}
