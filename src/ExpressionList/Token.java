package ExpressionList;

import java.math.BigDecimal;

/**
 *
 * @author Lawrence
 */
public class Token {
    private BigDecimal value;
    private boolean isInteger;
    private String text;

    /**
     * Value:
     * Sets the value to be the number
     * It is an integer
     * It has no text (Blank string is default)
     * @param num Number to be stored
     */
    public Token(BigDecimal num) {
        value = num;
        isInteger = true;
        text = "";
    }
    
    /**
     * Variable or Operand:
     * Value is set to 0
     * Not an integer
     * Text is the name passed
     * @param name Variable name
     */
    public Token(String name) {
        value = new BigDecimal(0);
        isInteger = false;
        text = name;
    }

    /**
     * Checks if Value is null
     * @return True or False
     */
    public boolean isNull() {
        return !isInteger && text.equals("");
    }

    /**
     * Gives back if it is an integer
     * @return True of False if it is an integer
     */
    public boolean isInteger() {
        return isInteger;
    }

    /**
     * Gives back the number
     * @return Integer representation of the value
     */
    public BigDecimal value() {
        return value;
    }

    /**
     * Gives back the text
     * @return The text stored
     */
    public String tokenText() {
        return text;
    }

    /**
     * The first bit of the text stored
     * @return The first char of the text
     */
    public char tokenChar() {
        if (isInteger()) {          //Not evaluating correctly
            return 0;
        }
        return text.charAt(0);
    }

    /**
     * Gives back the text
     * @return String representation of the text stored.
     */
    @Override
    public String toString() {
        if (isInteger) {
            return value().toString();
        } else {
            return tokenText();
        }
    }
}