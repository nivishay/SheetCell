package engine.expression.impl.functions.string;

import dto.small_parts.ReturnedValueType;
import engine.expression.api.Expression;
import engine.expression.impl.functions.type.BinaryExpression;
import dto.small_parts.EffectiveValue;


public class Concat extends BinaryExpression {


    public Concat(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public String getOperationSign() {
        return "";
    }

    @Override
    protected EffectiveValue evaluate(EffectiveValue evaluate1, EffectiveValue evaluate2) {

        try {
            if (isEmptyOrUndefined(evaluate1) || isEmptyOrUndefined(evaluate2)) {
                return createEffectiveValue(ReturnedValueType.STRING, "UNDEFINED");
            }

            String result = evaluate1.getValue() + " " + evaluate2.getValue();
            return createEffectiveValue(ReturnedValueType.STRING, result);

        } catch (ClassCastException e) {

            if (isUnknown(evaluate1) || isUnknown(evaluate2)) {
                return createEffectiveValue(ReturnedValueType.UNKNOWN, "UNDEFINED");
            }
            else{
                return createEffectiveValue(ReturnedValueType.UNDEFINED, "UNDEFINED");
            }

          //  throw new IllegalArgumentException("Invalid type of arguments: Both arguments must be of type String", e);
        }
    }

    private boolean isUndefined(EffectiveValue value) {
        return value.getCellType() == ReturnedValueType.UNDEFINED;
    }

    private boolean isEmptyOrUndefined(EffectiveValue value) {
        String val = (String) value.getValue();
        return val.isEmpty() || val.equals("UNDEFINED");
    }

    private boolean isEmpty(EffectiveValue value) {
        return value.getCellType() == ReturnedValueType.EMPTY;
    }

    private boolean isUnknown(EffectiveValue value) {
        return value.getCellType() == ReturnedValueType.UNKNOWN;
    }

    private EffectiveValue createEffectiveValue(ReturnedValueType type, String value) {
        return new EffectiveValue(type, value);
    }
}

