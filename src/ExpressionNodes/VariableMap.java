package ExpressionNodes;

import java.util.HashMap;
import java.util.Set;

import Expression.InvalidExpressionException;

/**
 * Created by Lawrence on 7/8/2015.
 */
public class VariableMap<K, V> {
    private HashMap<K, V> variables;

    public VariableMap() {
        variables = new HashMap<>();
    }

    private boolean variableExists(K key) {
        return variables.containsKey(key);
    }

    /**
     * As long as the key is inside the map, it returns the value associated with it
     *
     * @param key Key
     * @return Value of stored with it
     * @throws Exception
     */
    public V get(K key) throws InvalidExpressionException {
        if (variableExists(key)) {
            return variables.get(key);
        } else {
            throw new InvalidExpressionException("Error: Variable '" + key + "' does not exist -- (Can't access non-existent variables)");
        }
    }

    /**
     * Adds a value into the map assuming that it doesn't already exist (to enforce immutability)
     *
     * @param key Key
     * @param value Value
     * @throws Exception
     */
    public void add(K key, V value) throws InvalidExpressionException {
        if (!variableExists(key)) {
            variables.put(key, value);
        } else {
            throw new InvalidExpressionException("Error: Variable '" + key + "' already exists -- (Immutable)");
        }
    }

    /**
     * Lists out all the keys and values inside the HashMap
     *
     * @return String representation of the variables and values
     */
    @Override
    public String toString() {
        Set<K> keys = variables.keySet();
        if (keys.isEmpty()) {
            return "No variable names set";
        } else {
            String expression = "";
            for (K k : keys) {
                expression += "Key: " + k + ", Value: " + variables.get(k) + "\n";
            }
            return expression;
        }
    }
}
