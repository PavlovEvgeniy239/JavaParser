package expression;

import java.util.Objects;

public class Variable implements Exp {
    private final String var;
    private final int PRIORITY = 3;

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public int evaluate(int var) {
            return var;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (var.equals("x")) {
            return evaluate(x);
        } else if (var.equals("y")) {
            return evaluate(y);
        } else {
            return evaluate(z);
        }
    }

    @Override
    public String toString() {
            return var;
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    public String getVar() {
        return var;
    }

    @Override
    public String getOPERATION() {
        return null;
    }

    @Override
    public int getPRIORITY() {
        return PRIORITY;
    }

    @Override
    public boolean equals(Object expression) {
        if (expression instanceof Variable) {
            return Objects.equals(((Variable) expression).getVar(), this.var);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return var.hashCode();
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

}
