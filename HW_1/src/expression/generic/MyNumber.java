package expression.generic;

public abstract class MyNumber {
    public abstract MyNumber add(MyNumber arg);
    public abstract MyNumber subtract(MyNumber arg);
    public abstract MyNumber divide(MyNumber arg);
    public abstract MyNumber multiply(MyNumber arg);
    public abstract MyNumber mod(MyNumber arg);
    public abstract MyNumber negate();
    public abstract MyNumber abs();
    public abstract MyNumber square();
    public abstract Number getValue();
}
