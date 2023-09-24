package expression.generic;

public interface ExpGeneric {
    <T extends MyNumber> MyNumber evaluate(T x, T y, T z);
}
