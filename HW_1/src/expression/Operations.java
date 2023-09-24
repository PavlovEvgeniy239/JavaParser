package expression;

public abstract class Operations implements Exp {
    private Exp expression1;
    private Exp expression2;

    public Operations(Exp expression1, Exp expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    public Exp getExpression1() {
        return this.expression1;
    }

    public Exp getExpression2() {
        return this.expression2;
    }

    @Override
    public String toString() {
        return "(" + expression1.toString() + " " + this.getOPERATION() + " " + expression2.toString() + ")";
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        if (expression1.getPRIORITY() < this.getPRIORITY()) {
            sb.append("(").append(expression1.toMiniString()).append(")");
        } else {
            sb.append(expression1.toMiniString());
        }

        sb.append(" ").append(this.getOPERATION()).append(" ");

        if (this instanceof Divide || this instanceof Subtract || this instanceof Set || this instanceof Clear) {
            if (expression2.getPRIORITY() > this.getPRIORITY()) {
                sb.append(expression2.toMiniString());
            } else {
                sb.append("(").append(expression2.toMiniString()).append(")");
            }
        } else if (this instanceof Multiply && expression2 instanceof Divide) {
            sb.append("(").append(expression2.toMiniString()).append(")");
        } else {
            if (expression2.getPRIORITY() < this.getPRIORITY()) {
                sb.append("(").append(expression2.toMiniString()).append(")");
            } else {
                sb.append(expression2.toMiniString());
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object expression) {
        if (expression != null) {
            if (this.getClass() == expression.getClass()) {
                return ((Operations) expression).expression1.equals(this.expression1) &&
                        ((Operations) expression).expression2.equals(this.expression2);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int c = 0;
        if (this instanceof Add) {
            c = 1;
        }
        if (this instanceof Subtract) {
            c = 2;
        }
        if (this instanceof Multiply) {
            c = 3;
        }
        if (this instanceof Divide) {
            c = 4;
        }
        return (expression1.hashCode() + expression2.hashCode() * 11) * 17 + c;
    }
}
