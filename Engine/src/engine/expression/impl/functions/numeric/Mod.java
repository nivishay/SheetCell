package engine.expression.impl.functions.numeric;


import engine.expression.api.Expression;

public class Mod extends NumericBinaryOperation {
    @Override
    protected Double applyOperation(Double value1, Double value2) {
        return value1 % value2;
    }

    public Mod(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public String getOperationSign() {
        return "%";
    }
}
