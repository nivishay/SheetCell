package engine.expression.impl.functions.numeric;

import dto.small_parts.ReturnedValueType;
import engine.expression.api.Expression;
import engine.expression.impl.functions.type.BinaryExpression;

import dto.small_parts.EffectiveValue;


public abstract class NumericBinaryOperation extends BinaryExpression {

    public NumericBinaryOperation(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    protected abstract Double applyOperation(Double value1, Double value2);

    @Override
    protected EffectiveValue evaluate(EffectiveValue e1, EffectiveValue e2) {

        try {
            Double result = applyOperation((Double) e1.getValue(), (Double) e2.getValue());
            return new EffectiveValue(ReturnedValueType.NUMERIC, result);
        } catch (ClassCastException e)
        {
            if(e1.getCellType() == ReturnedValueType.EMPTY || e2.getCellType() == ReturnedValueType.EMPTY)
                return new EffectiveValue(ReturnedValueType.NUMERIC,Double.NaN);

            if (e1.getCellType() == ReturnedValueType.UNKNOWN || e2.getCellType() == ReturnedValueType.UNKNOWN)
                return new EffectiveValue(ReturnedValueType.UNKNOWN, Double.NaN);

            else{
                return new EffectiveValue(ReturnedValueType.UNKNOWN, Double.NaN);
            }
        }
    }
}


