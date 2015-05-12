package ExpressionNodes;

import java.math.BigDecimal;
import ExpressionVariables.VariableTree;

/**
 *
 * @author Lawrence
 */
public interface Node {
    public BigDecimal evaluate(VariableTree tree);
}
