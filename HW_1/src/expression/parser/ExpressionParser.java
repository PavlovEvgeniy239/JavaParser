package expression.parser;

import expression.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class ExpressionParser implements TripleParser {
    @Override
    public TripleExpression parse(final String expression) {
//        System.out.println(expression);
        return parse(new StringSource(expression));
    }

    public static TripleExpression parse(final CharSource source) {
        return new TripleExpressionParser(source).parseTripleExpression();
    }

    public static class TripleExpressionParser extends BaseParser {
        private final Deque<Elements> operations = new ArrayDeque<>();
        private final Deque<TripleExpression> arguments = new ArrayDeque<>();
        boolean lastElementIsOperation = true;
        int openStaples = 0;

        private static final Map<Elements, Integer> PRIORITIES = Map.of(
                Elements.BreakPoint, -1,
                Elements.Set, 0,
                Elements.Clear, 0,
                Elements.Subtract, 1,
                Elements.Add, 1,
                Elements.Multiply, 2,
                Elements.Divide, 2,
                Elements.UnaryMinus, 3,
                Elements.Count, 3
        );

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
            skipWhitespace();
            while (!eof()) {
                if (lastElementIsOperation) {
                    if (test('x') || test('y') || test('z')) {
                        arguments.push(new Variable(String.valueOf(take())));
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
                        lastElementIsOperation = !lastElementIsOperation;
                    } else {
                        throw new IllegalArgumentException();
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
                    } else if (take('s')) {
                        expect("et");
                        addOperation(Elements.Set);
                    } else if (take('c')) {
                        expect("lear");
                        addOperation(Elements.Clear);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
                lastElementIsOperation = !lastElementIsOperation;
                skipWhitespace();
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
                            case Add -> element = new Add((Exp) arg1, (Exp) arg2);
                            case Subtract -> element = new Subtract((Exp) arg1, (Exp) arg2);
                            case Multiply -> element = new Multiply((Exp) arg1, (Exp) arg2);
                            case Divide -> element = new Divide((Exp) arg1, (Exp) arg2);
                            case Set -> element = new Set((Exp) arg1, (Exp) arg2);
                            case Clear -> element = new Clear((Exp) arg1, (Exp) arg2);
                        }
                    }
                    case UnaryMinus -> element = new Negate((Exp) arguments.pop());
                    case Count -> element = new Count((Exp) arguments.pop());
                }
                arguments.push(element);
                addOperation(operation);
            }
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
            arguments.push(new Const(c));
        }

        private void skipWhitespace() {
            while (isWS()) {
                take();
            }
        }
    }
}