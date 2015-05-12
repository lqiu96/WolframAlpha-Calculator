package ExpressionVariables;

import java.math.BigDecimal;

/**
 *
 * @author Lawrence
 */
public class VariableTree {
    private TreeNode root;

    /**
     * By Default set's the root to be null
     */
    public VariableTree() {
        root = new TreeNode(null, null);
    }

    /**
     * Finds the location of the node if it exists and assigns it to be the value
     * @param name Name of the variable
     * @param value Value of the variable
     */
    public void assign(String name, BigDecimal value) {
        TreeNode node = recursiveSearch(name);
        node.setValue(value);
    }

    /**
     * Finds the node if it exists and gets its value
     * @param name Name of the variable
     * @return Value of the variable
     */
    public BigDecimal lookup(String name) {
        TreeNode node = recursiveSearch(name);
        return node.getValue();
    }

    /**
     * Searches through the tree for the variable
     * Goes through it recursively:
     * -Left if less
     * -Right if more
     * @param name Name of the variable
     * @return Either new node if variable doesn't exist or the found node
     */
    private TreeNode recursiveSearch(String name) {
        TreeNode node = root;
        while (true) {
            if (!node.isValid()) {
                node.setName(name);
                return node;
            }
            if (name.equals(node.getName())) {
                return node;
            } else if (name.compareTo(node.getName()) < 0) {
                if (node.getLeft() == null) {
                    node.setLeft(new TreeNode(null, null));
                }
                node = node.getLeft();
            } else {
                if (node.getRight() == null) {
                    node.setRight(new TreeNode(null, null));
                }
                node = node.getRight();
            }
        }
    }

    /**
     * Prints out the tree by starting at the root
     * -If null, there is nothing in the tree
     * @return String representation of the tree
     */
    @Override
    public String toString() {
        String line = "";
        if (root == null) {
            line += "There is nothing in the tree";
        } else {
            line += root;
        }
        return line;
    }
}
