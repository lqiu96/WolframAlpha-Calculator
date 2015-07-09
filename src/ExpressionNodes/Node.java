package ExpressionNodes;

import Expression.InvalidExpressionException;

import java.math.BigDecimal;
/**
 * @author Lawrence
 */
public interface Node {
    BigDecimal evaluate(VariableMap<String, BigDecimal> map) throws InvalidExpressionException;
}
