import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

/**
 * @author Lawrence
 */
public enum Trigonometry {
    SIN {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            return new BigDecimal(sin(decimal.doubleValue()));
        }
    },
    COS {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            return new BigDecimal(cos(decimal.doubleValue()));
        }
    },
    TAN {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            return new BigDecimal(tan(decimal.doubleValue()));
        }
    },
    CSC {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            BigDecimal sinValue = new BigDecimal(sin(decimal.doubleValue()));
            return new BigDecimal(1).divide(sinValue, 25, RoundingMode.HALF_UP);
        }
    },
    SEC {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            BigDecimal cosValue = new BigDecimal(cos(decimal.doubleValue()));
            return new BigDecimal(1).divide(cosValue, 25, RoundingMode.HALF_UP);
        }
    },
    COT {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            BigDecimal tanValue = new BigDecimal(tan(decimal.doubleValue()));
            return new BigDecimal(1).divide(tanValue, 25, RoundingMode.HALF_UP);
        }
    },
    ARCSIN {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            return new BigDecimal(asin(decimal.doubleValue()));
        }
    },
    ARCCOS {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            return new BigDecimal(acos(decimal.doubleValue()));
        }
    },
    ARCTAN {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            return new BigDecimal(atan(decimal.doubleValue()));
        }
    },
    ARCCSC {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            BigDecimal asinValue = new BigDecimal(asin(decimal.doubleValue()));
            return new BigDecimal(1).divide(asinValue, 25, RoundingMode.HALF_UP);
        }
    },
    ARCSEC {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            BigDecimal acosValue = new BigDecimal(acos(decimal.doubleValue()));
            return new BigDecimal(1).divide(acosValue, 25, RoundingMode.HALF_UP);
        }
    },
    ACRCOT {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            decimal = convertToRadians(decimal);
            BigDecimal atanValue = new BigDecimal(atan(decimal.doubleValue()));
            return new BigDecimal(1).divide(atanValue, 25, RoundingMode.HALF_UP);
        }
    };

    Trigonometry() {

    }

    public abstract BigDecimal getTrigValue(BigDecimal decimal);

    public BigDecimal convertToRadians(BigDecimal degrees) {
        return degrees.multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180), 25, RoundingMode.HALF_UP);
    }
}