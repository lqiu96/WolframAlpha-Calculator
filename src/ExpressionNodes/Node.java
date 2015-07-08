package ExpressionNodes;

import java.math.BigDecimal;

import ExpressionVariables.VariableTree;

/**
 * @author Lawrence
 */
public interface Node {
    BigDecimal evaluate(VariableTree tree);
}
