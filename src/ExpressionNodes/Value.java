package ExpressionNodes;

import java.math.BigDecimal;

import ExpressionVariables.VariableTree;

/**
 * @author Lawrence
 */
public class Value implements Node {
    private BigDecimal value;

    /**
     * Stores the value
     *
     * @param v Values to be stored
     */
    public Value(BigDecimal v) {
        value = v;
    }

    /**
     * Evaluates the value
     *
     * @param tree Does nothing -- Overridden method from the Node interface
     * @return Value
     */
    public BigDecimal evaluate(VariableTree tree) {
        return value;
    }

    /**
     * Prints out the value
     *
     * @return String of the value
     */
    public String toString() {
        return "Value of: " + value;
    }
}
