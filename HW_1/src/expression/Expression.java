package expression;

public interface Expression extends ToMiniString {
    int evaluate(int x);
    private static Const c(final int c) {
        return new Const(c);
    }
}
