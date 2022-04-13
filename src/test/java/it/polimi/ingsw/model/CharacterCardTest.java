package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Test for class "CharacterCard"
 * @author Sebastiano Meneghin
 */
class CharacterCardTest {
    private CharacterCard characterCardTest;
    private int costTest = 2;
    private Field costField;
    private int cardIDTest = 99;
    private Field cardIdField;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException  {
        characterCardTest = new CharacterCard(99);

        // Use reflection to get the private field "cost" and change its visibility
        costField = characterCardTest.getClass().getDeclaredField("cost");
        costField.setAccessible(true);

        // Use reflection to get the private field "cardID" and change its visibility
        cardIdField = characterCardTest.getClass().getDeclaredField("cardID");
        cardIdField.setAccessible(true);

        costField.set(characterCardTest, null);
        cardIdField.set(characterCardTest, null);
    }

    // Hypothetical test for method "build"
    // ...............

    /**
     * Test for the getter of the field "cardID"
     */
    @Test
    public void getCardIDTest() throws IllegalAccessException {
        cardIdField.setInt(characterCardTest, cardIDTest);

        if (cardIDTest != characterCardTest.getCardID())
            throw new AssertionError("Getter of cardID returned wrong value");
    }

    /**
     * Test for the getter of the field "cost"
     */
    @Test
    public void getCostTest() throws IllegalAccessException {
        costField.setInt(characterCardTest, costTest);

        if (costTest != characterCardTest.getCost())
            throw new AssertionError("Getter of Cost returned wrong value");
    }

    /**
     * Test for the setter of the field "cost"
     */
    @Test
    public void setCostTest() throws IllegalAccessException {
        characterCardTest.setCost(costTest);

        if (costTest != ((int) costField.get(characterCardTest)))
            throw new AssertionError("Setter of Cost set wrong value");
    }
}
