package it.polimi.ingsw.common.model;

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
    private Player           teamMemberIDTest;
    private Field            teamMemberIDField;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        playerTeamExpertTest = new PlayerTeamExpert(0, null, null, null);
        coinCountField = playerTeamExpertTest.getClass().getDeclaredField("coinCount");
        coinCountField.setAccessible(true);

        teamMemberIDTest = new Player(0, null, null, null);

        teamMemberIDField = playerTeamExpertTest.getClass().getDeclaredField("teamMemberID");
        teamMemberIDField.setAccessible(true);
        teamMemberIDField.set(playerTeamExpertTest, teamMemberIDTest.getPlayerID());
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
    void getTeamMemberIDTest() {
        if (playerTeamExpertTest.getTeamMemberID() != teamMemberIDTest.getPlayerID())
            throw new AssertionError("Returned wrong team member");
    }

    /**
     * Test for teamMember's setter
     */
    @Test
    void setTeamMemberIDTest() throws IllegalAccessException {
        Player localTest = new Player(1, null, null, null);
        playerTeamExpertTest.setTeamMemberID(localTest.getPlayerID());

        Integer fieldValue = (Integer) teamMemberIDField.get(playerTeamExpertTest);

        if (fieldValue.equals(teamMemberIDTest.getPlayerID()))
            throw new AssertionError("Setter did not set value");

        if (!fieldValue.equals(localTest.getPlayerID()))
            throw new AssertionError("Setter set wrong value");
    }
}
