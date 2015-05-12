package ExpressionNodes;

import java.math.BigDecimal;

import ExpressionVariables.VariableTree;

/**
 * @author Lawrence
 */
public class Conditional implements Node {
    private Node test;
    private Node trueCase;
    private Node falseCase;

    /**
     * Stores all the cases for the Conditional operator
     *
     * @param test      Node to be tested for True or False
     * @param trueCase  Evaluated if test is True
     * @param falseCase Evaluated if test if False
     */
    public Conditional(Node test, Node trueCase, Node falseCase) {
        this.test = test;
        this.trueCase = trueCase;
        this.falseCase = falseCase;
    }

    /**
     * Determines which case to evaluated
     *
     * @param tree Variable tree to help with the cases to be evaluated
     * @return Value of the expression
     */
    public BigDecimal evaluate(VariableTree tree) {
        if (test.evaluate(tree).compareTo(new BigDecimal(1)) == 0) {
            return trueCase.evaluate(tree);
        } else {
            return falseCase.evaluate(tree);
        }
    }

    /**
     * Gives back picture of the conditional operation
     *
     * @return String representation of the Conditional operation
     */
    public String toString() {
        return test + " ? " + trueCase + " : " + falseCase;
    }
}
