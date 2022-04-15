package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Tests for class "PlayerExpert"
 * @author Mattia Martelli
 */
class PlayerExpertTest {
    private PlayerExpert playerExpertTest;
    private Field        coinCountField;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        playerExpertTest = new PlayerExpert(0, null, null, null);
        coinCountField = playerExpertTest.getClass().getDeclaredField("coinCount");
        coinCountField.setAccessible(true);
    }

    /**
     * Test for coinCount's getter
     */
    @Test
    void getCoinCountTest() throws IllegalAccessException {
        coinCountField.set(playerExpertTest, 5);

        if (playerExpertTest.getCoinCount() != 5)
            throw new AssertionError("Returned wrong coin count");
    }

    /**
     * Test for coinCount's setter
     */
    @Test
    void setCoinCountTest() throws IllegalAccessException {
        playerExpertTest.setCoinCount(5);

        if ((int) coinCountField.get(playerExpertTest) != 5)
            throw new AssertionError("Set wrong coin count");
    }
}
