package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for class "PlayerDecoratorExpert"
 * @author Mattia Martelli
 */
class PlayerDecoratorExpertTest {
    private PlayerDecoratorExpert playerDecoratorExpertTest;
    Field coinCountField;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        playerDecoratorExpertTest = new PlayerDecoratorExpert(new Player(0, null, null, null));
        coinCountField = playerDecoratorExpertTest.getClass().getDeclaredField("coinCount");
        coinCountField.setAccessible(true);
    }

    /**
     * Test for coinCount's getter
     */
    @Test
    void getCoinCountTest() throws IllegalAccessException {
        coinCountField.set(playerDecoratorExpertTest, 5);

        if (playerDecoratorExpertTest.getCoinCount() != 5)
            throw new AssertionError("Returned wrong coin count");
    }

    /**
     * Test for coinCount's setter
     */
    @Test
    void setCoinCountTest() throws IllegalAccessException {
        playerDecoratorExpertTest.setCoinCount(5);

        if ((int) coinCountField.get(playerDecoratorExpertTest) != 5)
            throw new AssertionError("Set wrong coin count");
    }
}
