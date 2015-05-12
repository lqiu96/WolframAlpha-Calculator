package ExpressionNodes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import ExpressionVariables.VariableTree;

/**
 *
 * @author Lawrence
 */
public class Operation implements Node {
    private String operation;
    private Node left;
    private Node right;

    /**
     * Creates a tree in which operation is the root and has left and right sub-trees
     * @param left Stored to the left of the root
     * @param operation Operation to be done
     * @param right Stored to the right of the root
     */
    public Operation(Node left, String operation, Node right) {
        this.left = left;
        this.operation = operation;
        this.right = right;
    }

    /**
     * Evaluates the expression
     * @param tree Checks to help evaluated variables
     * @return Evaluated expression
     */
    public BigDecimal evaluate(VariableTree tree) {
        if (operation.equals("+")) {
            return left.evaluate(tree).add(right.evaluate(tree));
        } else if (operation.equals("-")) {
            return left.evaluate(tree).subtract(right.evaluate(tree));
        } else if (operation.equals("*")) {
            return left.evaluate(tree).multiply(right.evaluate(tree));
        } else if (operation.equals("/")) {
            return left.evaluate(tree).divide(right.evaluate(tree), 25, RoundingMode.HALF_UP);
        } else if (operation.equals("%")) {
            return new BigDecimal(left.evaluate(tree).intValue() % right.evaluate(tree).intValue());  //Modulus only applies to integer values
        } else if (operation.equals(">")) {
            return (left.evaluate(tree).compareTo(right.evaluate(tree))) > 0 ? new BigDecimal(1) : new BigDecimal(0);
        } else if (operation.equals("<")) {
            return (left.evaluate(tree).compareTo(right.evaluate(tree))) < 0 ? new BigDecimal(1) : new BigDecimal(0);
        } else if (operation.equals(">=")) {
            return (left.evaluate(tree).compareTo(right.evaluate(tree))) >= 0 ? new BigDecimal(1) : new BigDecimal(0);
        } else if (operation.equals("<=")) {
            return (left.evaluate(tree).compareTo(right.evaluate(tree))) <= 0 ? new BigDecimal(1) : new BigDecimal(0);
        } else if (operation.equals("!=")) {
            return (left.evaluate(tree).compareTo(right.evaluate(tree))) != 0 ? new BigDecimal(1) : new BigDecimal(0);
        } else if (operation.equals("==")) {
            return (left.evaluate(tree).compareTo(right.evaluate(tree))) == 0 ? new BigDecimal(1) : new BigDecimal(0);
        } else if (operation.equals(">>")) {
            return new BigDecimal(left.evaluate(tree).intValue() >> right.evaluate(tree).intValue());
        } else if (operation.equals("<<")) {
            return new BigDecimal(left.evaluate(tree).intValue() << right.evaluate(tree).intValue());
        } else if (operation.equals("=")) {
            tree.assign(((Variable) left).getName(), right.evaluate(tree));
            return tree.lookup(((Variable) left).getName());
        } else {
            return new BigDecimal(0);
        }
    }

    /**
     * Gives back representation of the operation
     * @return String representation of the operation
     */
    public String toString() {
        return left + operation + right;
    }
}
