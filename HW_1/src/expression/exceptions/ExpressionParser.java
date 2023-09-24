package expression.exceptions;

import expression.*;

import java.util.*;

public class ExpressionParser implements TripleParser {
    @Override
    public TripleExpression parse(final String expression) {
        return parse(new StringSource(expression));
    }

    public static TripleExpression parse(final CharSource source) {
        return new TripleExpressionParser(source).parseTripleExpression();
    }

    public static class TripleExpressionParser extends BaseParser {
        protected final Deque<Elements> operations = new ArrayDeque<>();
        protected final Deque<TripleExpression> arguments = new ArrayDeque<>();
        boolean lastElementIsOperation = true;
        protected int openStaples = 0;

        protected static final Map<Elements, Integer> PRIORITIES;

        static {
            Map<Elements, Integer> aMap = new HashMap<>();
            aMap.put(Elements.BreakPoint, -1);
            aMap.put(Elements.Set, 0);
            aMap.put(Elements.Clear, 0);
            aMap.put(Elements.Subtract, 1);
            aMap.put(Elements.Add, 1);
            aMap.put(Elements.Multiply, 2);
            aMap.put(Elements.Divide, 2);
            aMap.put(Elements.UnaryMinus, 3);
            aMap.put(Elements.Count, 3);
            aMap.put(Elements.Pow, 3);
            aMap.put(Elements.Log, 3);
            PRIORITIES = Collections.unmodifiableMap(aMap);
        }


        public TripleExpressionParser(CharSource source) {
            super(source);
        }

        public TripleExpression parseTripleExpression() {
            final TripleExpression result = parseElement();
            if (eof()) {
                return result;
            }
            throw new IllegalArgumentException();
        }

        private TripleExpression parseElement() {
            skipWhitespace();
            final TripleExpression result = parseExpression();
            skipWhitespace();
            return result;
        }

        private TripleExpression parseExpression() {
            operations.push(Elements.BreakPoint);
            boolean lastIsWS = isWS();
            int lastIsCloseST = 0;
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
                    } else if (take('l')) {
                        expect("og10");
                        addOperation(Elements.Log);
                        if (!isWS() && !test('(')) {
                            throw new IllegalArgumentException("illegal string");
                        }
                        lastElementIsOperation = !lastElementIsOperation;
                    } else if (take('p')) {
                        expect("ow10");
                        addOperation(Elements.Pow);
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
                    } else if (openStaples > 0 && take(')')) {
                        openStaples--;
                        lastElementIsOperation = !lastElementIsOperation;
                        addOperation(Elements.BreakPoint);
                        operations.pop();
                        lastIsCloseST = 2;
                    } else if (take('s')) {
                        expect("et");
                        if (!lastIsWS && lastIsCloseST <= 0) {
                            throw new IllegalArgumentException("illegal string");
                        }
                        addOperation(Elements.Set);
                        if (!isWS() && !test('(') && !test('-')) {
                            throw new IllegalArgumentException("illegal string");
                        }
                    } else if (take('c')) {
                        expect("lear");
                        if (!lastIsWS && lastIsCloseST <= 0) {
                            throw new IllegalArgumentException("illegal string");
                        }
                        addOperation(Elements.Clear);
                        if (!isWS() && !test('(') && !test('-')) {
                            throw new IllegalArgumentException("illegal string");
                        }
                    } else {
                        throw new IllegalArgumentException("illegal string");
                    }
                }
                lastElementIsOperation = !lastElementIsOperation;
                lastIsWS = isWS();
                lastIsCloseST--;
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
                TripleExpression element = null;
                Elements previousOperation = operations.pop();
                switch (previousOperation) {
                    case Add, Subtract, Multiply, Divide, Set, Clear -> {
                        TripleExpression arg2 = arguments.pop();
                        TripleExpression arg1 = arguments.pop();
                        switch (previousOperation) {
                            case Add -> element = new CheckedAdd((Exp) arg1, (Exp) arg2);
                            case Subtract -> element = new CheckedSubtract((Exp) arg1, (Exp) arg2);
                            case Multiply -> element = new CheckedMultiply((Exp) arg1, (Exp) arg2);
                            case Divide -> element = new CheckedDivide((Exp) arg1, (Exp) arg2);
                            case Set -> element = new CheckedSet((Exp) arg1, (Exp) arg2);
                            case Clear -> element = new CheckedClear((Exp) arg1, (Exp) arg2);
                        }
                    }
                    case UnaryMinus -> element = new CheckedNegate((Exp) arguments.pop());
                    case Count -> element = new CheckedCount((Exp) arguments.pop());
                    case Pow -> element = new CheckedPow10((Exp) arguments.pop());
                    case Log -> element = new CheckedLog10((Exp) arguments.pop());
                }
                arguments.push((TripleExpression) element);
                addOperation(operation);
            }
        }

        protected void parseVariable() {
            arguments.push((TripleExpression) new Variable(String.valueOf(take())));
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
            arguments.push((TripleExpression) new Const(c));
        }

        private void skipWhitespace() {
            while (isWS()) {
                take();
            }
        }
    }
}