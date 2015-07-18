package ExpressionNodes;

import Expression.InvalidExpressionException;

import java.math.BigDecimal;

/**
 * Created by Lawrence on 7/9/2015.
 */
public class Logarithmic implements Node {
    private BigDecimal value;
    private boolean isLog;

    public Logarithmic(BigDecimal value) {
        this(value, false);
    }

    public Logarithmic(BigDecimal value, boolean b) {
        this.value = value;
        this.isLog = b;
    }

    @Override
    public BigDecimal evaluate(VariableMap<String, BigDecimal> map) throws InvalidExpressionException {
        if (isLog) {
            return new BigDecimal(Math.log10(value.doubleValue()));
        } else {
            return new BigDecimal(Math.log(value.doubleValue()));
        }
    }

    @Override
    public String toString() {
        return ((isLog) ? "Log(" : "Ln(") + value.toPlainString() + ")";
    }
}
