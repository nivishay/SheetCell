package expression.impl.numFunction;
import expression.api.Expression;
import expression.variantImpl.BinaryExpression;

public class Minus extends BinaryExpression {

    public Minus(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public String getOperationSign() {
        return "-";
    }

    @Override
    protected Object evaluate(Object e1, Object e2) {
        return (Double)e1 - (Double)e2;
    }
}