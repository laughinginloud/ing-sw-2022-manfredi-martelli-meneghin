package it.polimi.ingsw.common.message;

import it.polimi.ingsw.common.GameValues;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A wrapper for <code>Map&lt;GameValues, Object&gt;</code>
 * @author Mattia Martelli
 */
public final class InfoMap {

    // region Concrete map and constructors

    /**
     * The concrete map
     */
    private final EnumMap<GameValues, Object> map;

    /**
     * Create an empty map
     */
    public InfoMap() {
        map = new EnumMap<>(GameValues.class);
    }

    /**
     * Create a map from a previous one
     * @param map The map used to initialize
     */
    public InfoMap(InfoMap map) {
        this.map = map.map;
    }

    /**
     * Create a map with an initial value-object tuple
     * @param val The key of the tuple
     * @param obj The value of the tuple
     */
    public InfoMap(GameValues val, Object obj) {
        map = new EnumMap<>(GameValues.class);
        map.put(val, obj);
    }

    // endregion

    // region Getters

    /**
     * Returns an entry set that mirrors the map
     * @return The set
     */
    public Set<Map.Entry<GameValues, Object>> entrySet() {
        return map.entrySet();
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
     * Check whether the map is empty
     * @return <code>true</code> if the map is empty, <code>false</code> otherwise
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    // endregion

    // region Setters

    /**
     * Add an element to the map
     * @param key The key that specifies the type of the value
     * @param value An object of the type specified by the key
     */
    public void put(GameValues key, Object value) {
        map.put(key, value);
    }

    /**
     * Remove a value from the map
     * @param key The key to search for
     */
    public void remove(GameValues key) {
        map.remove(key);
    }

    // endregion

    // region Functional interfaces

    /**
     * Apply an action to the associated value, if it exists
     * @param key    The key to search for
     * @param action The action to perform
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void ifPresent(GameValues key, Consumer action) {
        if (map.containsKey(key))
            action.accept(map.get(key));
    }

    /**
     * Execute an action for each element of the map
     * @param action The action to perform
     */
    public void forEach(BiConsumer<GameValues, Object> action) {
        map.forEach(action);
    }

    // endregion

}
