package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Test for class "CharacterCard"
 * @author Sebastiano Meneghin
 */
class CharacterCardTest {
    private       CharacterCard characterCardTest;
    private final int           costTest = 2;
    private       Field         costField;
    private final Character     characterTest = Character.FARMER;
    private       Field         characterField;
    private final boolean       hasCoinTest = false;
    private       Field         hasCoinField;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException  {
        characterCardTest = new CharacterCard(characterTest);

        // Use reflection to get the private field "cost" and change its visibility
        costField = characterCardTest.getClass().getDeclaredField("cost");
        costField.setAccessible(true);

        // Use reflection to get the private field "character" and change its visibility
        characterField = characterCardTest.getClass().getDeclaredField("character");
        characterField.setAccessible(true);

        //Use reflection to get the private field "hasCoin" and change its visibility
        hasCoinField = characterCardTest.getClass().getDeclaredField("hasCoin");
        hasCoinField.setAccessible(true);

        //Use reflection to set private field "cardID" and "cost" to a default value
        costField.setInt(characterCardTest, 1);
        characterField.set(characterCardTest, Character.STANDARD_BEARER);
        hasCoinField.setBoolean(characterCardTest, false);
    }

    /**
     * Test for the method "build"
     */
    @Test
    void buildTest() throws NullPointerException {
        CharacterCard temp;

        //Check first conditional branch in method "build"
        temp = CharacterCard.build(Character.MONK);
        if (!temp.getClass().equals(CharacterCardStudent.class))
            throw new AssertionError("Builder has not build a 'CharacterCardStudent' object as requested");

        //Check second conditional branch in method "build"
        temp = CharacterCard.build(Character.HERBALIST);
        if (!temp.getClass().equals(CharacterCardNoEntry.class))
            throw new AssertionError("Builder has not build a 'CharacterCardNoEntry' object as requested");

        //Check default conditional branch in method "build"
        temp = CharacterCard.build(Character.FARMER);
        if (!temp.getClass().equals(CharacterCard.class))
            throw new AssertionError("Builder has not build a 'CharacterCard' object as requested");
    }

    /**
     * Test for the getter of the field "character"
     */
    @Test
    void getCharacterTest() throws IllegalAccessException {
        characterField.set(characterCardTest, characterTest);

        if (characterTest != characterCardTest.getCharacter())
            throw new AssertionError("Getter of character returned wrong value");
    }

    /**
     * Test for the getter of the field "cost"
     */
    @Test
    void getCostTest() throws IllegalAccessException {
        costField.setInt(characterCardTest, costTest);

        if (costTest != characterCardTest.getCost())
            throw new AssertionError("Getter of cost returned wrong value");
    }

    /**
     * Test for the setter of the field "cost"
     */
    @Test
    void setCostTest() throws IllegalAccessException {
        characterCardTest.setCost(costTest);

        if (costTest != ((int) costField.get(characterCardTest)))
            throw new AssertionError("Setter of cost set wrong value");
    }

    /**
     * Test for the getter of the field "hasCoin"
     */
    @Test
    void getHasCoinTest() throws IllegalAccessException {
        hasCoinField.setBoolean(characterCardTest, hasCoinTest);

        if ((hasCoinTest && !characterCardTest.getHasCoin()) || (!hasCoinTest && characterCardTest.getHasCoin()))
            throw new AssertionError("Getter of hasCoin returned wrong value");
    }

    /**
     * Test for the setter of the field "hasCoin"
     */
    @Test
    void setHasCoinTest() throws IllegalAccessException {
        characterCardTest.setHasCoin(hasCoinTest);

        if ((hasCoinTest && !hasCoinField.getBoolean(characterCardTest)) || (!hasCoinTest && hasCoinField.getBoolean(characterCardTest)))
            throw new AssertionError("Setter of hasCoin set wrong value");
    }
}
