package ExpressionVariables;

import java.math.BigDecimal;

/**
 *
 * @author Lawrence
 */
public class TreeNode {
    private boolean isValid;
    private String name;
    private BigDecimal value;
    private TreeNode left;
    private TreeNode right;

    /**
     * TreeNode constructor for each Node inside the VariableTRee
     * Sets left and right values to be null as default
     * @param newName Sets the node's name
     * @param val Sets the node's value
     */
    public TreeNode(String newName, BigDecimal val) {
        isValid = false;
        name = newName;
        value = val;
        left = null;
        right = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gives back the name of the node
     * @return The variable's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gives back the Node on the left
     * @return The TreeNode's left sibling
     */
    public TreeNode getLeft() {
        return left;
    }

    /**
     * Gives back the Node on the right
     * @return The TreeNode's right sibling
     */
    public TreeNode getRight() {
        return right;
    }

    /**
     * Gives back the variables value
     * @return TreeNode's value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Sets the value correctly
     * @param value Sets TreeNode to be this
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public  boolean isValid() {
        return isValid;
    }

    /**
     * Prints out the Node and its siblings
     * @return String of the current node and it's left and right values
     */
    @Override
    public String toString() {
        String nodes = "";
        if (left != null) {
            nodes += left + "\n";
        }
        nodes += "Name " + name + " has a value of: " + value + "\n";
        if (right != null) {
            nodes += right + "\n";
        }
        return nodes;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}