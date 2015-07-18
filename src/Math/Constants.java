package Math;

import java.math.BigDecimal;

/**
 * Created by Lawrence on 7/8/2015.
 */
public enum Constants {
    PI(Math.PI),
    E(Math.E),
    GAMMA(0.57721566490153286060651209008240243104215933593992),
    PHI(1.6180339887498948482);

    private BigDecimal value;

    Constants(double value) {
        this.value = new BigDecimal(value);
    }

    public BigDecimal getValue() {
        return value;
    }
}
