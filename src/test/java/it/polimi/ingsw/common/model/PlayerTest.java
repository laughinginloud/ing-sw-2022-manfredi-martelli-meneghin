package it.polimi.ingsw.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Tests for class "Player"
 * @author Mattia Martelli
 */
class PlayerTest {
    private       Player        playerTest;
    private       SchoolBoard   schoolBoardTest;
    private       Field         assistantDeckField;
    private       Field         lastPlayedCardField;
    private final AssistantCard assistantCardTest = new AssistantCard(1, 1);

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        schoolBoardTest = new SchoolBoard(null, 0, null, null);
        playerTest      = new Player(0, "test", Wizard.CLOUD, schoolBoardTest);

        assistantDeckField = playerTest.getClass().getDeclaredField("assistantDeck");
        assistantDeckField.setAccessible(true);

        lastPlayedCardField = playerTest.getClass().getDeclaredField("lastPlayedCard");
        lastPlayedCardField.setAccessible(true);
    }

    /**
     * Test for the getter of a singular assistant card
     */
    @Test
    void getAssistantCardTest() throws IllegalAccessException {
        AssistantCard[] assistantDeckTest = new AssistantCard[9];
        for (int i = 0; i < 9; ++i)
            assistantDeckTest[i] = new AssistantCard(i + 2, ((i + 1) / 2) + 1);

        if (!playerTest.getAssistantCard(0).equals(new AssistantCard(1, 1)))
            throw new AssertionError("Returned wrong assistant card");

        AssistantCard[] assistantDeckValue = (AssistantCard[]) assistantDeckField.get(playerTest);

        if (assistantDeckValue.length == assistantDeckTest.length + 1)
            throw new AssertionError("Assistant deck not shrank");

        if (!Arrays.equals(assistantDeckValue, assistantDeckTest))
            throw new AssertionError("Wrongly modified deck whilst removing assistant card");
    }

    /**
     * Test for the getter of the whole deck
     */
    @Test
    void getAssistantDeckTest() throws IllegalAccessException {
        AssistantCard[] assistantDeckTest = new AssistantCard[10];
        for (int i = 0; i < 10; ++i)
            assistantDeckTest[i] = new AssistantCard(i + 1, (i / 2) + 1);

        if (!Arrays.equals(assistantDeckTest, playerTest.getAssistantDeck()))
            throw new AssertionError("Returned malformed assistant deck");
    }

    /**
     * Test for the getter of the player's ID
     */
    @Test
    void getPlayerIDTest() {
        if (playerTest.getPlayerID() != 0)
            throw new AssertionError("Returned wrong ID");
    }

    /**
     * Test for the getter of the player's wizard
     */
    @Test
    void getPlayerWizardTest() {
        if (!playerTest.getPlayerWizard().equals(Wizard.CLOUD))
            throw new AssertionError("Returned wrong wizard");
    }

    /**
     * Test for the getter of the player's school board
     */
    @Test
    void getSchoolBoardTest() {
        if (playerTest.getSchoolBoard() != schoolBoardTest)
            throw new AssertionError("Returned wrong schoolBoard");
    }

    /**
     * Test for the getter of the player's username
     */
    @Test
    void getUsernameTest() {
        if (!playerTest.getUsername().equals("test"))
            throw new AssertionError("Returned wrong username");
    }

    /**
     * Test for the getter of the player's last played card
     */
    @Test
    void getLastPlayedCardTest() throws IllegalAccessException {
        lastPlayedCardField.set(playerTest, assistantCardTest);

        if (!playerTest.getLastPlayedCard().equals(assistantCardTest))
            throw new AssertionError("Getter returned wrong assistant card");
    }

    /**
     * Test for the getter of the player's last played card
     */
    @Test
    void setLastPlayedCardTest() throws IllegalAccessException {
        AssistantCard localTest = new AssistantCard(2, 1);
        lastPlayedCardField.set(playerTest, assistantCardTest);

        playerTest.setLastPlayedCard(localTest);
        AssistantCard retrievedValue = playerTest.getLastPlayedCard();

        if (retrievedValue.equals(assistantCardTest))
            throw new AssertionError("Last played card not set");

        if (!retrievedValue.equals(localTest))
            throw new AssertionError("Set wrong last played card");
    }
}
