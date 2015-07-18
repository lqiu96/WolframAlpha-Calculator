package ExpressionNodes;

import Expression.InvalidExpressionException;

import java.math.BigDecimal;

/**
 * Created by Lawrence on 7/18/2015.
 */
public class Boolean implements Node {
    private boolean yesNo;

    public Boolean(boolean yesNo) {
        this.yesNo = yesNo;
    }

    @Override
    public BigDecimal evaluate(VariableMap<String, BigDecimal> map) throws InvalidExpressionException {
        if (yesNo) {
            return new BigDecimal(1);
        } else {
            return new BigDecimal(0);
        }
    }

    @Override
    public String toString() {
        return "Boolean is " + yesNo;
    }
}
