package expression;

public class Log10 implements Exp{
    private final int PRIORITY = 3;
    private final String OPERATION = "log10";

    private final Exp inside;

    public Log10(Exp inside) {
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
        return 0;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int arg = inside.evaluate(x, y, z);
        int i = 0;
        int ans = 1;
        while (ans <= arg) {
            ans *= 10;
            i++;
            if (i == 10) {
                break;
            }
        }
        return i - 1;
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
    public String toString() {
        return OPERATION + "(" + inside + ")";
    }
    public Exp getInside() {
        return inside;
    }
}
