import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

/**
 * CSC, SEC, COT are now represented as 1/SIN, 1/COS, 1/TAN respectively
 * ARCCSC, ARCSEC, ARCCOT are now represented as 1/ARCSIN, 1/ARCCOS, !/ARCTAN respectively
 *
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
            BigDecimal sinValue = SIN.getTrigValue(decimal);
            return new BigDecimal(1).divide(sinValue, 25, RoundingMode.HALF_UP);
        }
    },
    SEC {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            BigDecimal cosValue = COS.getTrigValue(decimal);
            return new BigDecimal(1).divide(cosValue, 25, RoundingMode.HALF_UP);
        }
    },
    COT {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            BigDecimal tanValue = TAN.getTrigValue(decimal);
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
            BigDecimal asinValue = ARCSIN.getTrigValue(decimal);
            return new BigDecimal(1).divide(asinValue, 25, RoundingMode.HALF_UP);
        }
    },
    ARCSEC {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            BigDecimal acosValue = ARCCOS.getTrigValue(decimal);
            return new BigDecimal(1).divide(acosValue, 25, RoundingMode.HALF_UP);
        }
    },
    ACRCOT {
        @Override
        public BigDecimal getTrigValue(BigDecimal decimal) {
            BigDecimal atanValue = ARCTAN.getTrigValue(decimal);
            return new BigDecimal(1).divide(atanValue, 25, RoundingMode.HALF_UP);
        }
    };

    Trigonometry() {

    }

    /**
     * For each of the Trig Function is gets the trig values
     * Converts the decimal into radians then evaluates
     *
     * @param decimal Angle in degrees
     * @return Angle in radians
     */
    public abstract BigDecimal getTrigValue(BigDecimal decimal);

    /**
     * Degrees is multiplied by pi/180 to get the radians representation
     *
     * @param degrees Angle in degrees
     * @return Angle in radians, rounded up with 25 decimal points
     */
    public BigDecimal convertToRadians(BigDecimal degrees) {
        return degrees.multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180), 25, RoundingMode.HALF_UP);
    }
}