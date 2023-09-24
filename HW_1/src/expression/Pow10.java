package expression;

public class Pow10 implements Exp{
    private final int PRIORITY = 3;
    private final String OPERATION = "pow10";

    private final Exp inside;

    public Pow10(Exp inside) {
        this.inside = inside;
    }
    @Override
    public double evaluate(double x) {
        return 0;
    }
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append(OPERATION);
        if (inside.getPRIORITY() == 3) {
            sb.append(" " + inside.toMiniString());
        } else {
            sb.append("(" + inside.toMiniString() + ")");
        }
        return sb.toString();
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
        return 0;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int arg = inside.evaluate(x, y, z);
        int ans = 0;
        if (arg >= 0) {
            ans = 1;
        }
        for(int i = 0; i < arg; i++) {
            ans *= 10;
        }
        return ans;
    }
    @Override
    public String toString() {
        return OPERATION + "(" + inside + ")";
    }
    public Exp getInside() {
        return inside;
    }
}
