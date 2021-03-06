package Expression;

import ExpressionNodes.VariableMap;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Stack;

/**
 * My rendition of a Wolfram Alpha Calculator. Supports many operations
 * Addition, Subtraction, Multiplication, Division, Modulus
 * Trig functions and many bitwise operations
 *
 * @author Lawrence
 */
public class WolframAlpha {
    private final static String BEGIN = "({[";
    private final static String END = ")}]";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final VariableMap<String, BigDecimal> variableMap = new VariableMap<>();
        final ExpressionEvaluate expressionEvaluate = new ExpressionEvaluate(variableMap);
        Scanner scanner = new Scanner(System.in);
        String expression;
        System.out.println("Type 'end' to exit out of the application");
        System.out.println("Type 'list' to view all the variable bindings set");
        System.out.println("Enter an expression to evaluate: ");
        while (true) {      //Continues forever, until the user enters 'end'
            expression = scanner.nextLine();
            if (expression.toLowerCase().equals("end")) {
                break;
            } else if (expression.toLowerCase().equals("list")) {
                System.out.println(variableMap);
                continue;
            }
            try {
                if (expression.length() == 0) {
                    throw new InvalidExpressionException("Error: No expression entered");
                } else if (checkParenthesisExpression(expression) && checkLetEqualityExpression(expression)) {
                    expressionEvaluate.setExpression(expression);
                    System.out.println("The answer is: " + expressionEvaluate.getAnswer().toPlainString());
                }
            } catch (InvalidExpressionException exception) {
                System.out.println(exception.getMessage());
            }
        }
        System.out.println("Exiting...");
    }

    /**
     * Checks to see if the inputted expression is a valid expression
     * Meaning that if it decides to have parenthesis inside, that it will have the correct number
     * of parenthesis.
     * <p>
     * Expressions like sin (50 will not work. Even though intuitively it will be known as sin 50,
     * the program will force the user to correctly input it as sin (50)
     *
     * @param expression Inputted expression that must be evaluated
     * @return Either an expression or the true value. No false value is given since it will throw an exception in that case
     * @throws InvalidExpressionException Expression highlighting what is wrong with the input. Usually a mismatch of the parenthesis
     */
    private static boolean checkParenthesisExpression(String expression) throws InvalidExpressionException {
        Stack<String> strings = new Stack<>();
        String currentLetter;
        for (int i = 0; i < expression.length(); i++) {
            currentLetter = expression.substring(i, i + 1);
            if (BEGIN.contains(currentLetter)) {
                strings.add(currentLetter);
            } else if (END.contains(currentLetter)) {
                if (strings.empty()) {
                    throw new InvalidExpressionException("Error: Unable to evaluate expression: No front matching parenthesis");
                } else {
                    if (strings.peek().equals("(") && !currentLetter.equals(")")) {
                        throw new InvalidExpressionException("Error: Unable to evaluate expression: Mismatched parenthesis: " + currentLetter);
                    } else if (strings.peek().equals("{") && !currentLetter.equals("}")) {
                        throw new InvalidExpressionException("Error: Unable to evaluate expression: Mismatched parenthesis: " + currentLetter);
                    } else if (strings.peek().equals("[") && !currentLetter.equals("]")) {
                        throw new InvalidExpressionException("Error: Unable to evaluate expression: Mismatched parenthesis: " + currentLetter);
                    }
                }
                strings.pop();
            }
        }
        if (!strings.empty()) {
            throw new InvalidExpressionException("Error: Unable to evaluate expression: Left over parenthesis.");
        }
        return true;
    }

    /**
     * Checks to make sure that with each variable declaration you have a let binding with it
     *
     * @param expression To check if variable is assigned
     * @return If each variable assigned has a let and '='
     * @throws InvalidExpressionException If there is no let with the variable
     */
    public static boolean checkLetEqualityExpression(String expression) throws InvalidExpressionException {
        if (expression.contains("=") && expression.contains("let")) {
            return true;
        } else if (!expression.contains("=") && !expression.contains("let")) {
            return true;
        } else {
            throw new InvalidExpressionException("Error: To assign a variable you must use let bindings");
        }
    }
}