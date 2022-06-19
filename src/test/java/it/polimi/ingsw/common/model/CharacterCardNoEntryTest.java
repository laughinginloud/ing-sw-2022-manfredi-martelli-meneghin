package it.polimi.ingsw.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Test for class "CharacterCardNoEntry"
 * @author Sebastiano Meneghin
 */
class CharacterCardNoEntryTest {
    private       CharacterCardNoEntry characterCardNoEntryTest;
    private       Field                noEntryCountField;
    private final int                  noEntryCountTest = 4;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException  {
        characterCardNoEntryTest = new CharacterCardNoEntry(Character.HERBALIST);

        // Use reflection to get the private field "noEntryCount" and change its visibility
        noEntryCountField = characterCardNoEntryTest.getClass().getDeclaredField("noEntryCount");
        noEntryCountField.setAccessible(true);

        noEntryCountField.setInt(characterCardNoEntryTest, 1);
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
