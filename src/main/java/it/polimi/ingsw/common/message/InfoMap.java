package it.polimi.ingsw.common.message;

import it.polimi.ingsw.common.GameValues;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A wrapper for <code>Map(GameValues, Object)</code>
 * @author Mattia Martelli
 */
public final class InfoMap {
    /**
     * The concrete map
     */
    private final EnumMap<GameValues, Object> map;

    /**
     * Create an empty map
     */
    public InfoMap() {
        map = new EnumMap<GameValues, Object>(GameValues.class);
    }

    /**
     * Create a map from a previous one
     * @param map The map used to initialize
     */
    public InfoMap(InfoMap map) {
        this.map = map.map;
    }

    /**
     * Add an element to the map
     * @param key The key that specifies the type of the value
     * @param value An object of the type specified by the key
     */
    public void put(GameValues key, Object value) {
        map.put(key, value);
    }

    /**
     * Retrieve an element from the map, without deleting it
     * @param key The value to search for
     * @return The object searched
     */
    public Object get(GameValues key) {
        return map.get(key);
    }

    /**
     * Returns an entry set that mirrors the map
     * @return The set
     */
    public Set<Map.Entry<GameValues, Object>> entrySet() {
        return map.entrySet();
    }

    /**
     * remove all elements from the map
     */
    public void clear() {
        map.clear();
    }

    /**
     * Search whether the map contains a value for the specified key
     * @param key The key to search for
     * @return <code>true</code> if a value is present, <code>false</code> otherwise
     */
    public boolean containsKey(GameValues key) {
        return map.containsKey(key);
    }

    /**
     * Returns an <code>Optional</code> containing the value searched, if present
     * @param key The key to search for
     * @return An <code>Optional</code> containing the value, if it exists
     */
    public void ifPresent(GameValues key, Consumer action) {
        if (map.containsKey(key))
            action.accept(map.get(key));
    }

    /**
     * Remove a value from the map
     * @param key The key to search for
     */
    public void remove(GameValues key) {
        map.remove(key);
    }

    /**
     * Execute an action for each element of the map
     * @param action The action to perform
     */
    public void forEach(BiConsumer<GameValues, Object> action) {
        map.forEach(action);
    }
}
