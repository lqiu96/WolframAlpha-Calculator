package Expression;

import ExpressionList.ListIterator;
import ExpressionList.Token;
import ExpressionList.TokenList;
import ExpressionNodes.*;
import ExpressionNodes.Boolean;
import Math.Trigonometry;
import Math.Constants;

import java.math.BigDecimal;

/**
 * @author Lawrence
 *         Expression is evaluated in a Recursive Descent Parsing method
 *         In terms of order of operations:
 *         1. Parenthesis, Variables, Trig Expressions, Boolean
 *         2. Log, Negation
 *         3. Operations (*, /, %)
 *         4. Addition and Subtraction
 *         5. Shifting Bits (>>, <<)
 *         6. Relational Operations (>, <, >=, <=, ==, !=)
 *         7. BitWise Operations (|, &, ^)
 *         8. Logical Operations (&&, ||)
 *         9. Conditional Operation ((True/False Expression) ? TrueCase : FalseCase)
 *         10. Assignment (=): Using let bindings
 *         <p>
 *         Then is is all evaluated in ExpressionEvalute/Operation.java
 */
public class ExpressionEvaluate {
    private VariableMap<String, BigDecimal> variableMap;
    private TokenList tokenList;
    private ListIterator listIterator;
    private String expression;
    private BigDecimal answer;              //Unfortunately not the fastest, but it's necessary for calculating floating

    public ExpressionEvaluate(VariableMap<String, BigDecimal> variableMap) {
        this.variableMap = variableMap;
    }

    public void setExpression(String expression) {
        this.expression = expression;
        this.tokenList = new TokenList(expression);
        this.listIterator = tokenList.begin();
    }

    /**
     * Evaluates the tree that was built
     *
     * @param iterator Iterator to iterate through the list of tokens at the correct spot
     * @return The answer that the tree gives
     */
    public BigDecimal evaluate(ListIterator iterator) throws InvalidExpressionException {
        Node node = handleConditional(iterator);             //Remove cases where there are lots of 0's
        return node.evaluate(variableMap).stripTrailingZeros();    //e.g. 595 / 34 = 17.5000000000000000 -> becomes 17.5
    }

    /**
     * Checks for the assignment operator and assigns the variable name the value
     *
     * @param iterator Iterator to iterate through the list of tokens at the correct spot
     * @return The root of the node that the other nodes are built around
    private Node handleAssignment(ListIterator iterator) {
        Node node = handleConditional(iterator);
        if (iterator.tokenChar() == '=') {
            Token token = iterator.getToken();
            iterator.advance();
            node = new Operation(node, token.tokenText(), handleConditional(iterator));
        }
        return node;
    }*/

    /**
     * Checks for the conditional operator by checking for the question mark.
     * It creates the Conditional object by passing it the test, trueCase, and falseCase
     *
     * @param iterator Iterator to iterate through the list of tokens at the correct spot.
     * @return The root of the tree in which all the other nodes are build around
     */
    private Node handleConditional(ListIterator iterator) throws InvalidExpressionException {
        Node node = handleLogicalOperators(iterator);
        if (iterator.tokenChar() == '?') {
            iterator.advance();
            Node trueCase = handleLogicalOperators(iterator);
            iterator.advance();
            Node falseCase = handleLogicalOperators(iterator);
            iterator.advance();
            node = new Conditional(node, trueCase, falseCase);
        }
        return node;
    }

    /**
     * Checks the expression for logical operations dealing with Or and And
     * Expressions to the left and right that are evaluated as Not 0 are True and 0 as False
     *
     * @param iterator Iterator to iterate through the list of tokens
     * @return Root of tree in which all other nodes are build around
     */
    private Node handleLogicalOperators(ListIterator iterator) throws InvalidExpressionException {
        Node node = handleBitWiseOperations(iterator);
        while (iterator.tokenChar() != 0
                && (iterator.getToken().tokenText().equals("||") || iterator.getToken().tokenText().equals("&&"))) {
            Token operation = iterator.getToken();
            iterator.advance();
            if (operation.tokenChar() != 0) {
                node = new Operation(node, operation.tokenText(), handleBitWiseOperations((iterator)));
            }
        }
        return node;
    }

    /**
     * Deals with all the bitwise shifts, Exclusive or (XOR), Or (|), And (&)
     *
     * @param iterator Iterator which iterates through the list of tokens to the correct spot
     * @return Root of the newly added tree
     */
    private Node handleBitWiseOperations(ListIterator iterator) throws InvalidExpressionException {
        Node node = handleRelational(iterator);
        while (iterator.tokenChar() != 0
                && (iterator.getToken().tokenText().equals("|") || iterator.getToken().tokenText().equals("&")
                || iterator.getToken().tokenText().equals("^"))) {
            Token operation = iterator.getToken();
            iterator.advance();
            if (operation.tokenChar() != 0) {
                node = new Operation(node, operation.tokenText(), handleRelational(iterator));
            }
        }
        return node;
    }

    /**
     * Deals will all the other relational operations and creates a new Operation object
     * at the root in which all the nodes are built around it
     *
     * @param iterator Iterator which iterates through the list of tokens at the correct spot
     * @return Root of the newly added tree
     */
    private Node handleRelational(ListIterator iterator) throws InvalidExpressionException {
        Node node = handleBitShift(iterator);
        while (iterator.tokenChar() != 0
                && (iterator.getToken().tokenText().equals(">") || iterator.getToken().tokenText().equals("<")
                || iterator.getToken().tokenText().equals(">=") || iterator.getToken().tokenText().equals("<=")
                || iterator.getToken().tokenText().equals("!=") || iterator.getToken().tokenText().equals("=="))) {
            Token operation = iterator.getToken();
            iterator.advance();
            if (operation.tokenChar() != 0) {
                node = new Operation(node, operation.tokenText(), handleBitShift(iterator));
            }
        }
        return node;
    }

    /**
     * Deals with operators that are bitwise operations (>> $ <<) which shift the bits of a number a certain amount
     *
     * @param iterator Iterator which goes through the expression
     * @return Either the node that contains the operation or null
     */
    private Node handleBitShift(ListIterator iterator) throws InvalidExpressionException {
        Node node = handleAddSub(iterator);
        while (iterator.tokenChar() != 0
                && (iterator.getToken().tokenText().equals(">>") || iterator.getToken().tokenText().equals("<<"))) {
            Token operation = iterator.getToken();
            iterator.advance();
            if (operation.tokenChar() != 0) {
                node = new Operation(node, operation.tokenText(), handleAddSub(iterator));
            }
        }
        return node;
    }

    /**
     * Evaluates the addition and subtraction operations
     *
     * @param iterator Iterator to pass through the list of tokens at the correct spot
     * @return New root built and all the nodes that built around it
     */
    private Node handleAddSub(ListIterator iterator) throws InvalidExpressionException {
        Node node = handleOperations(iterator);
        while (iterator.tokenChar() != 0 && (iterator.getToken().tokenText().equals("+") || iterator.getToken().tokenText().equals("-"))) {
            Token operation = iterator.getToken();
            iterator.advance();
            if (operation.tokenChar() != 0) {
                if (node == null) {
                    node = new Value(new BigDecimal(0));
                }
                node = new Operation(node, operation.tokenText(), handleOperations(iterator));
            }
        }
        return node;
    }

    /**
     * Handles the next order of operations which is multiplication, division, and modulo
     * Builds the tree this way
     *
     * @param iterator Iterator to iterate through the list of tokens at the correct spot
     * @return Newly formed tree and returns the root
     */
    private Node handleOperations(ListIterator iterator) throws InvalidExpressionException {
        Node node = handleNegation(iterator);
        while (iterator.tokenChar() != 0 && (iterator.getToken().tokenText().equals("*")
                || iterator.getToken().tokenText().equals("/") || iterator.getToken().tokenText().equals("%"))) {
            Token operation = iterator.getToken();
            iterator.advance();
            if (operation.tokenChar() != 0) {
                node = new Operation(node, operation.tokenText(), handleParenthesis(iterator));
            }
        }
        return node;
    }

    private Node handleNegation(ListIterator iterator) throws InvalidExpressionException {
        Node node = handleParenthesis(iterator);
        if (iterator.tokenChar() != 0 && iterator.getToken().tokenText().equals("!")) {
            iterator.advance();
            Token operation = iterator.getToken();
            if (operation.tokenText().equals("true")) {
                node = new Boolean(false);
            } else if (operation.tokenText().equals("false")){
                return new Boolean(true);
            } else {
                throw new InvalidExpressionException("Cannot negate a non boolean value");
            }
        }
        return node;
    }

    /**
     * Deals with either creating the sub-tree of whatever is inside the parenthesis
     * Or if it is a value returns the new Value object
     * Or if it is a variable it creates a new Variable object
     * If none of the above, it simply returns null
     * <p>
     * Added support for Parenthesis: [ && {, so they're not recognized as parenthesis
     *
     * @param iterator Iterator to iterate through the list of tokens at the corret spot
     * @return The Node which other nodes will be built around
     */
    private Node handleParenthesis(ListIterator iterator) throws InvalidExpressionException {
        if (iterator.tokenChar() == '(' || iterator.tokenChar() == '{' || iterator.tokenChar() == '[') {
            iterator.advance();
            Node node = handleConditional(iterator);
            if (iterator.tokenChar() == ')' || iterator.tokenChar() == '}' || iterator.tokenChar() == ']') {
                iterator.advance();
            }
            return node;
        } else if (iterator.currentIsInteger()) {                           //Must be a number
            Node node = new Value(iterator.value());
            iterator.advance();
            return node;
        } else if (isTrig(iterator.getToken().tokenText())) {               //Check if it is a trig function
            BigDecimal decimal = handleTrig(iterator);
            return new Value(decimal);
        } else if (isMathConstants(iterator.getToken().tokenText())) {
            BigDecimal decimal = handleConstants(iterator);
            return new Value(decimal);
        } else if (iterator.getToken().tokenText().equals("let")) {
            iterator.advance();
            Node variableName = new Variable(iterator.getToken().tokenText());
            iterator.advance();
            iterator.advance();
            return new Operation(variableName, "=", handleConditional(iterator));
        } else if (iterator.getToken().tokenText().equals("log") || iterator.getToken().tokenText().equals("ln")) {
            String log = iterator.getToken().tokenText();
            iterator.advance();
            String expression = findInnerExpression(iterator);
            ExpressionEvaluate expressionEvaluate = new ExpressionEvaluate(variableMap);
            expressionEvaluate.setExpression(expression);
            return (log.equals("ln")) ? new Logarithmic(expressionEvaluate.getAnswer()) :
                    new Logarithmic(expressionEvaluate.getAnswer(), true);
        } else if (iterator.getToken().tokenText().equals("true") || iterator.getToken().tokenText().equals("false")) {
            if (iterator.getToken().tokenText().equals("true")) {
                iterator.advance();
                return new Boolean(true);
            } else {
                iterator.advance();
                return new Boolean(false);
            }
        } else if (!iterator.getToken().tokenText().equals("!") && iterator.getToken().tokenText().length() >= 1) {
            //Must be a variable name
            //Must check to make sure you didn't get !, since that would register as a variable name
            Node node = new Variable(iterator.getToken().tokenText());
            iterator.advance();
            return node;
        } else {
            return null;
        }
    }

    /**
     * Iterate through the enums to check if any of the Mathmatical constants are used
     * If it is use, it returns the value by BigDecimal
     *
     * @param iterator ListIterator through the expression
     * @return BigDecimal representation of the value
     */
    private BigDecimal handleConstants(ListIterator iterator) {
        String constant = iterator.getToken().tokenText();
        iterator.advance();
        Constants[] mathConstants = Constants.values();
        for (Constants constants : mathConstants) {
            if (constant.toLowerCase().equals(constants.toString().toLowerCase())) {    //Matches Pi/pI with PI
                return constants.getValue();
            }
        }
        return null;    //Should never happen
    }

    /**
     * Iterates through the enumeration to check if any of the enums are used
     *
     * @param constant Math representation of the constant
     * @return Whether the string constant
     */
    private boolean isMathConstants(String constant) {
        Constants[] mathConstants = Constants.values();
        for (Constants constants : mathConstants) {
            if (constant.toLowerCase().equals(constants.toString().toLowerCase())) {    //Matches Pi/pI with PI
                return true;
            }
        }
        return false;
    }

    /**
     * It gets the trig function and determines the inside bit of which the trig function will call it on
     * To evaluated the inside, it calls a new Expression.ExpressionEvaluate object and evalutes the inside that way
     * <p>
     * P.S. The inputted angle should be in degrees since each of the Trig enums convert the BigDecimal
     * parameter they get to radians.
     * <p>
     * e.g. It could be tan(5 * 5 + 9 % 4), in which case it must be reduced into tan(26)
     * <p>
     * Even though Intellij says that there is a change of this returning null...
     * There really is no chance of this returning null since the only way this function was called in the first place
     * was because the token was found to be a trig function. If it weren't a trig function, then yes, it would return
     * null
     *
     * @param iterator Iterator to go through the expression
     * @return BigDecimal object that contains the correct value
     */
    private BigDecimal handleTrig(ListIterator iterator) throws InvalidExpressionException {
        String trig = iterator.getToken().tokenText().toLowerCase();
        iterator.advance();
        String subExpression = findInnerExpression(iterator);
        ExpressionEvaluate subEvaluated = new ExpressionEvaluate(variableMap);
        subEvaluated.setExpression(subExpression);
        Node node = new Value(subEvaluated.getAnswer());
        Trigonometry trigUsed = null;
        Trigonometry[] allTrigValues = Trigonometry.values();
        for (Trigonometry trigonometry : allTrigValues) {
            if (trig.toLowerCase().equals(trigonometry.toString().toLowerCase())) {
                trigUsed = trigonometry;
                break;      //Once finds correct trig function, stops looking through all of them
            }
        }
        return trigUsed.getTrigValue(node.evaluate(variableMap));      //Refer to JavaDoc if Intellij shows that it will be null
    }

    /**
     * It finds the value that the trig function will use it on by either looking for a parenthesis
     * or the first number it sees.
     * <p>
     * e.g. tan(5 * 5) will be evaluated as tan(25)
     * tan 5 * 5 will be evaluated as tan(5) * 5
     * <p>
     * Added support for the following: ( { [ ) { [ Are now recognized as parenthesis
     *
     * @param iterator Iterator which iterates through to find the parameters
     * @return String containing the inside parameters of the function
     */
    private String findInnerExpression(ListIterator iterator) {
        String expression = "";
        if (iterator.tokenChar() == '(' || iterator.tokenChar() == '{' || iterator.tokenChar() == '[') {
            iterator.advance();
            while (iterator.tokenChar() != ')' && iterator.tokenChar() != '}' && iterator.tokenChar() != ']') {     //Has to be && since it must make sure
                if (iterator.currentIsInteger()) {                                                                  //it is none of them. If it is one of the parenthesis
                    expression += iterator.value().doubleValue() + " ";                                             //it can't work. Surprisingly, it took me a long time to figure
                } else {                                                                                            //that out.
                    expression += iterator.getToken().tokenText() + " ";
                }
                iterator.advance();
            }
            iterator.advance();
        } else {
            if (isMathConstants(iterator.getToken().tokenText())) {
                return handleConstants(iterator).toPlainString();
            }
            expression += iterator.value().doubleValue() + " ";
            iterator.advance();
        }
        return expression;
    }

    /**
     * Checks to see if the token is a Trigonometric value. Compares it
     * with the String representation of each Enumeration value
     *
     * @param s String value of the tokenText
     * @return Boolean depending if it is a trig function or not
     */
    private boolean isTrig(String s) {
        Trigonometry[] trigNames = Trigonometry.values();
        for (Trigonometry trigonometry : trigNames) {
            if (s.toLowerCase().equals(trigonometry.toString().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Simples gives back what expression is equivalent to
     *
     * @return The integer value of the expression
     */
    public BigDecimal getAnswer() throws InvalidExpressionException {
        answer = evaluate(listIterator);
        return answer;
    }

    /**
     * Prints out the details of the expression
     * expression was declared a global variable just for this method
     * answers default value is 0, but should be the correct value when evaluated
     *
     * @return String of information dealing with the expression, ExpressionList.Token list, and the answer
     */
    @Override
    public String toString() {
        return "The expression is: \n" + expression +
                " and the tokens are split: \n" + tokenList +
                " and the answer is: " + ((answer == null) ? "NOT SOLVED YET" : answer.toPlainString());
    }
}