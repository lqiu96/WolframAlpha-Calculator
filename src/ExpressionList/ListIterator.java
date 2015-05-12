package ExpressionList;

import java.math.BigDecimal;

/**
 *
 * @author Lawrence
 */
public class ListIterator {
    private TokenList list;
    private ListElement current;

    /**
     * Stores the current ListElement and tokenlist
     * @param tokenList TookenList that the iterator is using
     * @param element Current element
     */
    public ListIterator(TokenList tokenList, ListElement element) {
        list = tokenList;
        current = element;
    }

    /**
     * Gives back the current token
     * @return current token the iterator is on
     */
    public Token getToken() {
        return current.getToken();
    }

    /**
     * As long as the token is not null, it returns the first char of the tokenText
     * @return Either 0 or char depending if the current value is null or not
     */
    public char tokenChar()
    {
        if (current != null)
            return current.getToken().tokenChar();
        else
            return 0;
    }

    /**
     * Is the current ExpressionList.Token an integer?
     * @return if token is integer
     */
    public boolean currentIsInteger()
    {
        return current.getToken().isInteger();
    }

    /**
     * Integer representation of the current token
     * @return int version of the token
     */
    public BigDecimal value()
    {
        return current.getToken().value();
    }

    /**
     * Goes the next token by assigning the current token to the next one
     */
    public void advance()
    {
        if (current != null)
            current = current.getNext();
    }

    /**
     * Compares the two listIterator
     * @param object Other ListIterator
     * @return If the current values are the same and false if it does not or if it is not a ListIterator
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ListIterator)) {
            return  false;
        }
        ListIterator other = (ListIterator) object;
        return current == other.current;
    }

    /**
     * By reading Effective Java by Joshua Bloch, I'm reminded to modify my hashCode() method
     * whenevery I modify my equals(). It truely has no purpose regarding to the fact that I
     * am not using any data structure which requires a hashing (HashSet, HashMap, HashTable).
     * Nonetheless, I am overriding this method out of respect for Joshua Bloch.
     * @return
     */
    @Override
    public int hashCode() {
        return super.hashCode() + 1;        //Oh my! Look at that modification.
    }
}
