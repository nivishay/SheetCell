package CoreParts;

import CoreParts.impl.EngineImpl;
import CoreParts.smallParts.CellLocation;

public class Main {
    public static void main(String[] args) {

        EngineImpl TestEngine1 = new EngineImpl();
        EngineImpl TestEngine2 = new EngineImpl();

        EngineTestOne(TestEngine1);
        EngineTestTwo(TestEngine2);

    }

    private static void EngineTestOne(EngineImpl testEngine1)
    {
        testEngine1.updateCell("10", 'A', '1');
        testEngine1.updateCell("8", 'A', '3');
        testEngine1.updateCell("5", 'A', '4');

        // Tests
        System.out.println("Test Engine 1:");
        System.out.println("---------------------------------------------------------------------------------");
        numbericRunTest("Calculating A2: (A1{10} + A3{8})", testEngine1, "{PLUS, {REF, A1}, {REF, A3}}", 'A', '2', 18);
        numbericRunTest("Calculating B1: (A2{18} + (A1{10} - A4{5}))", testEngine1, "{PLUS, {REF, A2}, {MINUS, {REF, A1}, {REF, A4}}}", 'B', '1', 23);

        // Exception tests
        runInvalidUpdateTest("Test invalid value in A4", testEngine1, "moshe", 'A', '4');
        runInvalidUpdateTest("Test invalid value in A2", testEngine1, "moshe", 'A', '2');

        System.out.println("---------------------------------------------------------------------------------");

    }

    public static void EngineTestTwo(EngineImpl testEngine2) {
        // Initial Setup: A1 -> 10, A2 -> {REF, A1}, A3 -> {REF, A2}, A4 -> {REF, A3}
        testEngine2.updateCell("10", 'A', '1');
        testEngine2.updateCell("{REF, A1}", 'A', '2');
        testEngine2.updateCell("{REF, A2}", 'A', '3');
        testEngine2.updateCell("{REF, A3}", 'A', '4');

        // Change A1 to a string: "Moshe"
        testEngine2.updateCell("Moshe", 'A', '1');

        // Tests
        System.out.println("Test Engine 2:");
        System.out.println("---------------------------------------------------------------------------------");

        // Ensure that A2, A3, A4 now hold the value "Moshe"
        stringRunTest("Check if A2 is 'Moshe' after A1 change", testEngine2, "{REF, A1}", 'A', '2', "Moshe");
        stringRunTest("Check if A3 is 'Moshe' after A1 change", testEngine2, "{REF, A2}", 'A', '3', "Moshe");
        stringRunTest("Check if A4 is 'Moshe' after A1 change", testEngine2, "{REF, A3}", 'A', '4', "Moshe");

        System.out.println("---------------------------------------------------------------------------------");

    }

    // Test function to check if the value of the cell matches the expected value
    private static void numbericRunTest(String testName, EngineImpl engine, String expression, char col, char row, double expectedValue) {
        engine.updateCell(expression, col, row);
        double actualValue = (double)engine.getCell(CellLocation.fromCellId(col, row)).getEffectiveValue().evaluate().getValue();

        System.out.println("\n" + testName);
        System.out.println("Expression: " + expression);
        System.out.println("Cell: " + col + row);
        System.out.println("Expected: " + expectedValue);
        System.out.println("Calculated: " + actualValue);

        if (actualValue == expectedValue) {
            System.out.println("Result: Pass\n");
        } else {
            System.out.println("Result: Fail\n(Expected: " + expectedValue + ", Calculated: " + actualValue + ")\n");
        }
    }

    private static void stringRunTest(String testName, EngineImpl engine, String expression, char col, char row, String expectedValue) {
        engine.updateCell(expression, col, row);
        String actualValue = (String)engine.getCell(CellLocation.fromCellId(col, row)).getEffectiveValue().evaluate().getValue();

        System.out.println("\n" + testName);
        System.out.println("Expression: " + expression);
        System.out.println("Cell: " + col + row);
        System.out.println("Expected: " + expectedValue);
        System.out.println("Calculated: " + actualValue);

        if (actualValue.equals(expectedValue)) {
            System.out.println("Result: Pass\n");
        } else {
            System.out.println("Result: Fail\n(Expected: " + expectedValue + ", Calculated: " + actualValue + ")\n");
        }
    }

    // Test function for invalid updates
    private static void runInvalidUpdateTest(String testName, EngineImpl engine, String expression, char col, char row) {
        System.out.println("\n" + testName);
        System.out.println("Expression: " + expression);
        System.out.println("Cell: " + col + row);

        try {
            engine.updateCell(expression, col, row);
            System.out.println("Result: Fail\n(Expected: Exception, Calculated: Success)\n");
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
            System.out.println("Result: Pass\n");
        }
    }

}