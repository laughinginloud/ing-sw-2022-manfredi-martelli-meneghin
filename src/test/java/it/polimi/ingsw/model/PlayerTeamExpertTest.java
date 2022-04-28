package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Tests for class "PlayerTeamExpert",
 * adapted from the siblings
 * @author Mattia Martelli
 */
public class PlayerTeamExpertTest {
    private PlayerTeamExpert playerTeamExpertTest;
    private Field            coinCountField;
    private Player           teamMemberTest;
    private Field            teamMemberField;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        playerTeamExpertTest = new PlayerTeamExpert(0, null, null, null);
        coinCountField = playerTeamExpertTest.getClass().getDeclaredField("coinCount");
        coinCountField.setAccessible(true);

        teamMemberTest = new Player(0, null, null, null);

        teamMemberField = playerTeamExpertTest.getClass().getDeclaredField("teamMember");
        teamMemberField.setAccessible(true);
        teamMemberField.set(playerTeamExpertTest, teamMemberTest);
    }

    /**
     * Test for coinCount's getter
     */
    @Test
    void getCoinCountTest() throws IllegalAccessException {
        coinCountField.set(playerTeamExpertTest, 5);

        if (playerTeamExpertTest.getCoinCount() != 5)
            throw new AssertionError("Returned wrong coin count");
    }

    /**
     * Test for coinCount's setter
     */
    @Test
    void setCoinCountTest() throws IllegalAccessException {
        playerTeamExpertTest.setCoinCount(5);

        if ((int) coinCountField.get(playerTeamExpertTest) != 5)
            throw new AssertionError("Set wrong coin count");
    }

    /**
     * Test for teamMember's getter
     */
    @Test
    void getTeamMemberTest() {
        if (playerTeamExpertTest.getTeamMember() != teamMemberTest)
            throw new AssertionError("Returned wrong team member");
    }

    /**
     *
     */
    @Test
    void setTeamMemberTest() throws IllegalAccessException {
        Player localTest = new Player(0, null, null, null);
        playerTeamExpertTest.setTeamMember(localTest);

        Player fieldValue = (Player) teamMemberField.get(playerTeamExpertTest);

        if (fieldValue.equals(teamMemberTest))
            throw new AssertionError("Setter did not set value");

        if (!fieldValue.equals(localTest))
            throw new AssertionError("Setter set wrong value");
    }
}
