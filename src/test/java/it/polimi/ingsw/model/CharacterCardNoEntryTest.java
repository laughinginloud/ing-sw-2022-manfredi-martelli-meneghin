package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Test for class "CharacterCardNoEntry"
 * @author Sebastiano Meneghin
 */
class CharacterCardNoEntryTest {
    private CharacterCardNoEntry characterCardNoEntryTest;
    private Field noEntryCountField;
    private int noEntryCountTest = 10;
    private int cardIDTest = 100;
    private final int defaultValue = 1000;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException  {
        characterCardNoEntryTest = new CharacterCardNoEntry(cardIDTest);

        // Use reflection to get the private field "noEntryCount" and change its visibility
        noEntryCountField = characterCardNoEntryTest.getClass().getDeclaredField("noEntryCount");
        noEntryCountField.setAccessible(true);

        noEntryCountField.setInt(characterCardNoEntryTest, defaultValue);
    }

    /**
     * Test for the getter of the field "noEntryCount"
     */
    @Test
    void getNoEntryCountTest() throws IllegalAccessException {
        noEntryCountField.setInt(characterCardNoEntryTest, noEntryCountTest);

        if (noEntryCountTest != characterCardNoEntryTest.getNoEntryCount())
            throw new AssertionError("Getter returned wrong value");
    }

    /**
     * Test for the setter of the field "noEntryCount"
     */
    @Test
    void setNoEntryCountTest() throws IllegalAccessException {
        characterCardNoEntryTest.setNoEntryCount(noEntryCountTest);

        if (noEntryCountTest != ((int) noEntryCountField.get(characterCardNoEntryTest)))
            throw new AssertionError("Setter set wrong value");
    }

}