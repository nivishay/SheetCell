package engine.expression.impl.functions.bool;

import engine.expression.api.Expression;

public class Or extends BooleanBinaryOperation<Boolean> {

    public Or(Expression expression1, Expression expression2) {
        super(expression1, expression2, Boolean.class);
    }

    @Override
    protected Boolean applyOperation(Boolean value1, Boolean value2) {
        return value1 || value2;
    }

    @Override
    public String getOperationSign() {
        return "||";
    }
}
