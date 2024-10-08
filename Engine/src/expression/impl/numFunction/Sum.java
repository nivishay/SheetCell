package expression.impl.numFunction;

import CoreParts.api.sheet.SheetCellViewOnly;
import expression.ReturnedValueType;
import expression.api.Expression;
import expression.impl.Range;
import expression.impl.Ref;
import smallParts.EffectiveValue;


import java.util.Set;

public class Sum implements Expression {

    Range range;

    public Sum(Range range) {
        this.range = range;
    }

    @Override
    public EffectiveValue evaluate(SheetCellViewOnly sheet) throws IllegalArgumentException {

        Set<Ref> refOfRange = range.getRangeRefs();
        double sum = 0.0;

        for(Ref ref : refOfRange){

            EffectiveValue value = ref.evaluate(sheet);
            try{
                sum += (double) value.getValue();
            }catch (Exception e){
            }
        }

        return new EffectiveValue(ReturnedValueType.NUMERIC, sum);
    }


    @Override
    public String getOperationSign() {
        return "";
    }

    public Range getRange(){
        return range;
    }
}



