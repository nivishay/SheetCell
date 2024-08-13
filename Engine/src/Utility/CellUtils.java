package Utility;

import CoreParts.impl.CellImp;
import CoreParts.impl.SheetCellImp;
import CoreParts.interfaces.Cell;
import CoreParts.interfaces.SheetCell;
import CoreParts.smallParts.CellLocation;
import expressions.Expression;
import expressions.Operation;
import expressions.impl.BinaryExpression;
import expressions.impl.UnaryExpression;
import expressions.impl.numFunction.Num;
import expressions.impl.stringFunction.Str;

import java.util.ArrayList;
import java.util.List;



public class CellUtils {

    public static boolean trySetNumericValue(String value) {
        try {
            Double numericValue = Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static List<String> processFunction(String newValue) {
            return getCellAsStringRepresention(newValue);
    }

    public static boolean isPotentialOperation(String newValue) // TODO : implement function
    {
        if (newValue.startsWith("{") && newValue.endsWith("}")) {
            return true;
        }
        else{
            return false;
        }
    }


    public static String extractFunctionName(String input) {
        String content = input.substring(1, input.length() - 1).trim();
        // Split the content by comma or space
        String[] parts = content.split("[, ]");

        if (parts.length > 0) {
            return parts[0]; // The function name is the first part
        }

        throw new IllegalArgumentException("Invalid function format");
    }

    public static String removeParantecesFromString(String input){
        return input.substring(1, input.length() - 1).trim();
    }


    private static List<String> getCellAsStringRepresention(String newValue) {
        List<String> cells = new ArrayList<>();
        String content = newValue.substring(1, newValue.length() - 1).trim();
        // Split the content by space
        String[] parts = content.split(" ");
        for (int i = 1; i < parts.length; i++) {
            cells.add(parts[i]);
        }
        return cells;
    }

    public  static void checkIfCellsAreOfSameType(List<CellImp> cells) {
        Class clazz = cells.get(0).getEffectiveValue().getClass();
        for (CellImp cell : cells) {
            if (cell.getEffectiveValue().getClass() != clazz) {
                throw new IllegalArgumentException("Cells are not of the same type");
            }
        }
    }

    public static List<String> splitArguments(String content) {
        List<String> arguments = new ArrayList<>();
        int braceLevel = 0;
        StringBuilder currentArg = new StringBuilder();

        for (char c : content.toCharArray()) {
            if (c == '{') braceLevel++;
            if (c == '}') braceLevel--;

            if (c == ',' && braceLevel == 0) {
                arguments.add(currentArg.toString().trim());
                currentArg.setLength(0);  // Reset currentArg
            } else {
                currentArg.append(c);
            }
        }

        if (currentArg.length() > 0) {
            arguments.add(currentArg.toString().trim());  // Add the last argument
        }

        return arguments;
    }

    public static Expression processExpressionRec(String value, Cell targetCell, SheetCellImp sheetCell) {// this is a recursive function
        if (CellUtils.trySetNumericValue(value)) {  // base case: value is a number
            return new Num(Double.parseDouble(value));
        }

        if (!CellUtils.isPotentialOperation(value)) {  // base case: value is a string
            return new Str(value);
        }

        List<String> arguments = parseArguments(value);
        Operation operation = Operation.fromString(arguments.get(0)); // argument(0) = FUNCION_NAME

        if (operation == Operation.REF) {
            Cell cellThatBeenEffected = sheetCell.getCell(CellLocation.fromCellId(arguments.get(1)));
            return handleReferenceOperation(cellThatBeenEffected,targetCell);//argument(1) = CELL_ID
        }

        return operation.calculate(processArguments(arguments.subList(1, arguments.size()), targetCell, sheetCell));
    }

    private static List<String> parseArguments(String value) {
        String cellId = CellUtils.removeParantecesFromString(value);
        return CellUtils.splitArguments(cellId);//for example Plus 5 6
    }

    private static Expression handleReferenceOperation(Cell cellThatBeenEffected, Cell cellThatAffects) {
        validateCircularDependency(cellThatBeenEffected, cellThatAffects);

        if (cellThatBeenEffected.getEffectiveValue() == null) {
            throw new IllegalArgumentException("Invalid expression: cell referenced before being set");
        }

        return cellThatBeenEffected.getEffectiveValue();
    }

    public static void validateCircularDependency(Cell cell, Cell targetCell) {
        if (cell.isCellAffectedBy(targetCell)==false) {
            targetCell.addCellToAffectedBy(cell);
            cell.addCellToAffectingOn(targetCell);
        } else {
            throw new IllegalArgumentException("Invalid expression: circular dependency");
        }
    }

    public static List<Expression> processArguments(List<String> arguments, Cell targetCell, SheetCellImp sheetCell) {
        List<Expression> expressions = new ArrayList<>();
        for (String arg : arguments) {
            expressions.add(processExpressionRec(arg.trim(), targetCell, sheetCell));
        }
        return expressions;
    }

    public static void recalculateCellsHelper(Expression expTree, Expression toFind, Expression newValue) {
        if (expTree instanceof Num || expTree instanceof Str) {  // base case: value is a number
            return;
        }

        if (expTree instanceof BinaryExpression) {
            BinaryExpression expTree1 = (BinaryExpression) expTree;

            if (expTree1.getExpressionLeft() == toFind) {
                expTree1.setExpressionLeft(newValue);
            } else if (expTree1.getExpressionRight() == toFind) {
                expTree1.setExpressionRight(newValue);
            } else {

                recalculateCellsHelper(expTree1.getExpressionLeft(), toFind, newValue);
                recalculateCellsHelper(expTree1.getExpressionRight(), toFind, newValue);
            }
        } else if (expTree instanceof UnaryExpression) {
            UnaryExpression expTree1 = (UnaryExpression) expTree;
            if (expTree1.getExpression() == toFind)
                expTree1.setExpression(newValue);
            else
                recalculateCellsHelper(expTree1.getExpression(), toFind, newValue);
        }
        //TODO: ADD TRINARY EXPRESSION
    }
    public static void recalculateCellsRec(Cell targetCell, Expression oldExpression) {
        for (Cell cell : targetCell.getAffectingOn()) {

            Expression effectiveValue = cell.getEffectiveValue();
            recalculateCellsHelper(effectiveValue, oldExpression, targetCell.getEffectiveValue());
        }
    }


}
