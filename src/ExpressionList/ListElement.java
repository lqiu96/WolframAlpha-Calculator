/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExpressionList;

/**
 * @author Lawrence
 */
public class ListElement {
    private ListElement next = null;
    private Token token = null;

    /**
     * Sets the ExpressionList.Token value as long as token doesn't already have a value
     *
     * @param token ExpressionList.Token to be stored
     */
    public void setToken(Token token) {
        if (this.token != null) {
            return;
        }
        this.token = token;
    }

    /**
     * Gives back the token
     *
     * @return ExpressionList.Token stored
     */
    public Token getToken() {
        return token;
    }

    /**
     * Sets the next Element
     *
     * @param element This element's next value
     */
    public void setNext(ListElement element) {
        if (next != null) {
            return;
        }
        this.next = element;
    }

    /**
     * Gives back the next value
     *
     * @return Next ListElement
     */
    public ListElement getNext() {
        return next;
    }
}
