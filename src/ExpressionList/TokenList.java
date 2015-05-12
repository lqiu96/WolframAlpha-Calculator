package ExpressionList;

import java.math.BigDecimal;

/**
 *
 * @author Lawrence
 */
public class TokenList {
    private ListIterator listIterator;

    private ListElement head;
    private ListElement tail;

    private final String expression;

    /**
     * Head and Tail are both set to null in this singly - LinkedList
     * Expression is null
     */
    public TokenList() {
        head = null;
        tail = null;
        expression = null;
    }

    /**
     * Head and tail are both null. Creates the expression. Creates list of tokens
     * @param expression Expression be evaluated
     */
    public TokenList(String expression) {
        head = null;
        tail = null;
        this.expression = expression;
        createExpression(expression);
    }

    /**
     * Creates a list of tokens to be evaluated.
     * Tokens either store:
     * -Operand
     * -Variable
     * -Value
     * -Other (Parenthesis)
     * @param expression Expression to be evaluated
     */
    private void createExpression(String expression) {
        int i = 0;
        while (i < expression.length()) {
            if (Character.isDigit(expression.charAt(i))) {          //Checks if it is a digit
                String number = "";                                 //Adds numbers as long as they are digits
                number += expression.charAt(i);
                i++;
                while (i < expression.length() && (Character.isDigit(expression.charAt(i))
                        || expression.charAt(i) == '.')) {          //Allows for decimals
                    number += expression.charAt(i);
                    i++;
                }
                double num = Double.parseDouble(number);
                Token token = new Token(new BigDecimal(num));
                pushBack(token);
            } else if (Character.isLetter(expression.charAt(i))) {  //Checks if it is a letter
                String variable = "";                               //Adds in letters to form variables
                variable += expression.charAt(i);
                i++;
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    variable += expression.charAt(i);
                    i++;
                }
                Token token = new Token(variable);
                pushBack(token);
            } else if (!Character.isSpaceChar(expression.charAt(i))) {      //If not letter or number
                String s = "";                                              //Must be operator or parenthesis or period
                s += expression.charAt(i);
                i++;
                if (i < expression.length() && expression.charAt(i) == '=') {
                    s+= expression.charAt(i);
                    i++;
                }
                Token token = new Token(s);
                pushBack(token);
            }
            while (i < expression.length() && Character.isSpaceChar(expression.charAt(i))) {    //As long as there are
                i++;                                                                            //spaces, iterate through them
            }
        }
    }

    /**
     * If there is no tokens
     * @return if head is null
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Gets the first token
     * @return head's token
     */
    public Token first() {
        return head.getToken();
    }

    /**
     * Gives back Iterator that starts at the beginning
     * @return ListIterator that starts in the beginning
     */
    public ListIterator begin() {
        return new ListIterator(this, head);
    }

    /**
     * Gives back Iterator that starts at the end
     * @return ListIterator that starts at the end
     */
    public ListIterator end() {
        return new ListIterator(this, tail);
    }

    /**
     * In the list add the tokens to the end of the list
     * Has to check if list is already empty or not
     * -If empty, implementations are different
     * @param token ExpressionList.Token to be pushed back
     */
    public void pushBack(Token token) {
        ListElement element = new ListElement();
        element.setToken(token);
        element.setNext(null);
        if (isEmpty())
        {
            head = element;
            tail = element;
        }
        else
        {
            tail.setNext(element);
            tail = element;
        }
    }

    /**
     * Adds the token to the front of the list
     * Implementations differ if the list is empty
     * @param token ExpressionList.Token to be pushed to the front of the list
     */
    public void pushFront(Token token) {
        ListElement element = new ListElement();
        element.setToken(token);
        element.setNext(head);
        if (isEmpty())
        {
            head = element;
            tail = element;
        }
        else {
            head = element;
        }
    }

    /**
     * Removes the ListElement that is at the front of the list
     * Sets that value to be null
     * @return Either null or the token at the front
     */
    public Token popFront() {
        if (!isEmpty())
        {
            Token token = first();
            ListElement f = head;
            head = head.getNext();
            f = null;
            return token;
        }
        return null;
    }

    /**
     * Gives back an image of the series of tokens used to construct the list
     * @return String representation of the list with each of the tokens laid out.
     */
    @Override
    public String toString() {
        String list = "{ ";
        for (ListIterator listIterator = begin(); !listIterator.equals(end()); listIterator.advance()) {
            list += listIterator.getToken() + " ";
        }
        list += tail.getToken() + " }";
        return list;
    }
}
