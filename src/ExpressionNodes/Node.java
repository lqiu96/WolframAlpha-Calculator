package ExpressionNodes;

import java.math.BigDecimal;
/**
 * @author Lawrence
 */
public interface Node {
    BigDecimal evaluate(VariableMap<String, BigDecimal> map) throws Exception;
}
