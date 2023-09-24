package expression.generic;

import expression.exceptions.*;

import java.math.BigInteger;
import java.util.*;

public class GenericExpressionParser {
    public ExpGeneric parse(final String expression, String mode) {
        return parse(new StringSource(expression), mode);
    }

    public static ExpGeneric parse(final CharSource source, String mode) {
        return new TripleExpressionParser(source, mode).parseTripleExpression();
    }

    public static class TripleExpressionParser extends BaseParser {
        protected final Deque<Elements> operations = new ArrayDeque<>();
        protected final Deque<ExpGeneric> arguments = new ArrayDeque<>();
        boolean lastElementIsOperation = true;
        protected int openStaples = 0;

        protected static final Map<Elements, Integer> PRIORITIES;

        static {
            PRIORITIES = Map.of(Elements.BreakPoint, -1, Elements.Subtract, 1, Elements.Add, 1, Elements.Multiply, 2, Elements.Divide, 2, Elements.Mod, 2, Elements.UnaryMinus, 3, Elements.Abs, 3, Elements.Square, 3);
        }

        private final String mode;

        public TripleExpressionParser(CharSource source, String mode) {
            super(source);
            this.mode = mode;
        }

        public ExpGeneric parseTripleExpression() {
            final ExpGeneric result = parseElement();
            if (eof()) {
                return result;
            }
            throw new IllegalArgumentException();
        }

        private ExpGeneric parseElement() {
            skipWhitespace();
            final ExpGeneric result = parseExpression();
            skipWhitespace();
            return result;
        }

        private ExpGeneric parseExpression() {
            operations.push(Elements.BreakPoint);
            skipWhitespace();
            while (!eof()) {
                if (lastElementIsOperation) {
                    if (test('x') || test('y') || test('z')) {
                        parseVariable();
                    } else if (between('0', '9')) {
                        boolean minus = false;
                        parseConst(minus);
                    } else if (take('-')) {
                        parseMinus();
                    } else if (take('(')) {
                        lastElementIsOperation = !lastElementIsOperation;
                        openStaples++;
                        operations.push(Elements.BreakPoint);
                    } else if (take('c')) {
                        expect("ount");
                        addOperation(Elements.Count);
                        if (!isWS() && !test('(')) {
                            throw new IllegalArgumentException("illegal string");
                        }
                        lastElementIsOperation = !lastElementIsOperation;
                    } else if (take('a')) {
                        expect("bs");
                        addOperation(Elements.Abs);
                        if (!isWS() && !test('(')) {
                            throw new IllegalArgumentException("illegal string");
                        }
                        lastElementIsOperation = !lastElementIsOperation;
                    } else if (take('s')) {
                        expect("quare");
                        addOperation(Elements.Square);
                        if (!isWS() && !test('(')) {
                            throw new IllegalArgumentException("illegal string");
                        }
                        lastElementIsOperation = !lastElementIsOperation;
                    } else {
                        throw new IllegalArgumentException("illegal string");
                    }
                } else {
                    if (take('+')) {
                        addOperation(Elements.Add);
                    } else if (take('-')) {
                        parseMinus();
                    } else if (take('*')) {
                        addOperation(Elements.Multiply);
                    } else if (take('/')) {
                        addOperation(Elements.Divide);
                    } else if (take('m')) {
                        expect("od");
                        addOperation(Elements.Mod);
                    } else if (openStaples > 0 && take(')')) {
                        openStaples--;
                        lastElementIsOperation = !lastElementIsOperation;
                        addOperation(Elements.BreakPoint);
                        operations.pop();
                    } else {
                        throw new IllegalArgumentException("illegal string");
                    }
                }
                lastElementIsOperation = !lastElementIsOperation;
                skipWhitespace();
            }
            if (openStaples > 0) {
                throw new IllegalArgumentException();
            }
            addOperation(Elements.BreakPoint);
            return arguments.pop();
        }

        public void addOperation(Elements operation) {
            if (operations.getFirst() == Elements.BreakPoint && operation == Elements.BreakPoint) {
                return;
            }
            if (PRIORITIES.get(operation) > PRIORITIES.get(operations.getFirst()) || (PRIORITIES.get(operation) == 3 && PRIORITIES.get(operations.getFirst()) == 3)) {
                operations.push(operation);
            } else {
                ExpGeneric element = null;
                Elements previousOperation = operations.pop();
                if (previousOperation == Elements.UnaryMinus) {
                    ExpGeneric arg1 = arguments.pop();
                    element = new GenericNegate(arg1);
                } else if (previousOperation == Elements.Abs) {
                    ExpGeneric arg1 = arguments.pop();
                    element = new GenericAbs(arg1);
                } else if (previousOperation == Elements.Square) {
                    ExpGeneric arg1 = arguments.pop();
                    element = new GenericSquare(arg1);
                } else {
                    ExpGeneric arg2 = arguments.pop();
                    ExpGeneric arg1 = arguments.pop();
                    switch (previousOperation) {
                        case Add -> element = new GenericAdd(arg1, arg2);
                        case Subtract -> element = new GenericSubtract(arg1, arg2);
                        case Multiply -> element = new GenericMultiply(arg1, arg2);
                        case Divide -> element = new GenericDivide(arg1, arg2);
                        case Mod -> element = new GenericMod(arg1, arg2);
                    }
                }
                arguments.push(element);
                addOperation(operation);
            }
        }

        protected void parseVariable() {
            arguments.push(new GenericVariable(String.valueOf(take())));
        }

        public void parseMinus() {
            if (lastElementIsOperation) {
                if (between('0', '9')) {
                    boolean minus = true;
                    parseConst(minus);
                } else {
                    addOperation(Elements.UnaryMinus);
                    lastElementIsOperation = !lastElementIsOperation;
                }
            } else {
                addOperation(Elements.Subtract);
            }
        }

        public void parseConst(boolean haveMinus) {
            StringBuilder sb = new StringBuilder();
            if (haveMinus) {
                sb.append('-');
            }
            while (between('0', '9')) {
                sb.append(take());
            }
            int c;
            try {
                c = Integer.parseInt(sb.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
            switch (mode) {
                case "i" -> arguments.push(new GenericConst(new MyCheckedInteger(c)));
                case "bi" -> arguments.push(new GenericConst(new MyBigInteger(new BigInteger(String.valueOf(c)))));
                case "u" -> arguments.push(new GenericConst(new MyInteger(c)));
                case "l" -> arguments.push(new GenericConst(new MyLong(c)));
                case "s" -> arguments.push(new GenericConst(new MyShort((short) c)));
                default -> arguments.push(new GenericConst(new MyDouble(c)));
            }
        }

        private void skipWhitespace() {
            while (isWS()) {
                take();
            }
        }
    }
}