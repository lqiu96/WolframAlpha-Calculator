package ExpressionNodes;

import Expression.InvalidExpressionException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Lawrence
 */
public class Operation implements Node {
    private String operation;
    private Node left;
    private Node right;

    /**
     * Creates a tree in which operation is the root and has left and right sub-trees
     *
     * @param left      Stored to the left of the root
     * @param operation Operation to be done
     * @param right     Stored to the right of the root
     */
    public Operation(Node left, String operation, Node right) {
        this.left = left;
        this.operation = operation;
        this.right = right;
    }

    /**
     * Evaluates the expression
     *
     * @param map Checks to help evaluated variables
     * @return Evaluated expression
     */
    public BigDecimal evaluate(VariableMap<String, BigDecimal> map) throws InvalidExpressionException {
        switch (operation) {
            case "+":
                return left.evaluate(map).add(right.evaluate(map));
            case "-":
                return left.evaluate(map).subtract(right.evaluate(map));
            case "*":
                return left.evaluate(map).multiply(right.evaluate(map));
            case "/":
                return left.evaluate(map).divide(right.evaluate(map), 25, RoundingMode.HALF_UP);
            case "%":
                return new BigDecimal(left.evaluate(map).intValue() % right.evaluate(map).intValue());  //Modulus only applies to integer values
            case ">>":
                return new BigDecimal(left.evaluate(map).intValue() >> right.evaluate(map).intValue());   //The following bitwise operations are also integer only
            case "<<":
                return new BigDecimal(left.evaluate(map).intValue() << right.evaluate(map).intValue());
            case "|":
                return new BigDecimal(left.evaluate(map).intValue() | right.evaluate(map).intValue());
            case "&":
                return new BigDecimal(left.evaluate(map).intValue() & right.evaluate(map).intValue());
            case "^":
                return new BigDecimal(left.evaluate(map).intValue() ^ right.evaluate(map).intValue());
            case ">":
                return (left.evaluate(map).compareTo(right.evaluate(map))) > 0 ? new BigDecimal(1) : new BigDecimal(0);
            case "<":
                return (left.evaluate(map).compareTo(right.evaluate(map))) < 0 ? new BigDecimal(1) : new BigDecimal(0);
            case ">=":
                return (left.evaluate(map).compareTo(right.evaluate(map))) >= 0 ? new BigDecimal(1) : new BigDecimal(0);
            case "<=":
                return (left.evaluate(map).compareTo(right.evaluate(map))) <= 0 ? new BigDecimal(1) : new BigDecimal(0);
            case "!=":
                return (left.evaluate(map).compareTo(right.evaluate(map))) != 0 ? new BigDecimal(1) : new BigDecimal(0);
            case "==":
                return (left.evaluate(map).compareTo(right.evaluate(map))) == 0 ? new BigDecimal(1) : new BigDecimal(0);
            case "||":
                return (left.evaluate(map).doubleValue() != 0 || right.evaluate(map).doubleValue() != 0) ? new BigDecimal(1) : new BigDecimal(0);
            case "&&":
                return (left.evaluate(map).doubleValue() != 0 && right.evaluate(map).doubleValue() != 0) ? new BigDecimal(1) : new BigDecimal(0);
            case "=":
                map.add(((Variable) left).getName(), right.evaluate(map));
                return map.get(((Variable) left).getName());
            default:
                return new BigDecimal(0);
        }
    }

    /**
     * Gives back representation of the operation
     *
     * @return String representation of the operation
     */
    public String toString() {
        return left + operation + right;
    }
}