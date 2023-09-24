package expression.generic;

public class GenericVariable implements ExpGeneric {
    private final String var;
    public GenericVariable(String var) {
        this.var = var;
    }

    @Override
    public <T extends MyNumber> MyNumber evaluate(T x, T y, T z) {
        if (var.equals("x")) {
            return x;
        } else if (var.equals("y")) {
            return y;
        } else {
            return z;
        }
    }
}
