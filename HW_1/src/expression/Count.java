package expression;

public class Count implements Exp {
    private final int PRIORITY = 3;
    private final String OPERATION = "count";

    private final Exp inside;

    public Count(Exp inside) {
        this.inside = inside;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        String BS = Integer.toBinaryString(inside.evaluate(x, y, z));
        int count = 0;
        for (int i = 0; i < BS.length(); i++) {
            if (BS.charAt(i) == '1') {
                count++;
            }
        }
        return count;
    }

    @Override
    public double evaluate(double x) {
        throw new IllegalArgumentException();
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
        String BS = Integer.toBinaryString(inside.evaluate(x));
        int count = 0;
        for (int i = 0; i < BS.length(); i++) {
            if (BS.charAt(i) == '1') {
                count++;
            }
        }
        return count;
    }
    @Override
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
}
