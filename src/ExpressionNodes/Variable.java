package ExpressionNodes;

import Expression.InvalidExpressionException;

import java.math.BigDecimal;

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
     * @param map Where the variable name is stored
     * @return Value associated with the variable name
     */
    public BigDecimal evaluate(VariableMap<String, BigDecimal> map) throws InvalidExpressionException {
        return map.get(name);
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
