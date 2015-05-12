package ExpressionNodes;

import java.math.BigDecimal;

import ExpressionVariables.VariableTree;

/**
 * @author Lawrence
 */
public class Variable implements Node {
    private String name;

    /**
     * Stores the name of the variable
     *
     * @param name Variable name
     */
    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Evaluates the tree
     *
     * @param tree Where the variable name is stored
     * @return Value associated with the variable name
     */
    public BigDecimal evaluate(VariableTree tree) {
        return tree.lookup(name);
    }

    /**
     * Gives back name and value associated with it
     *
     * @return Variable's value
     */
    public String toString() {
        return "The variable has a name of: " + name;
    }
}
