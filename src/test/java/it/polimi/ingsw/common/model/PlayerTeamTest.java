package it.polimi.ingsw.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * Tests for class "PlayerTeam"
 * @author Mattia Martelli
 */
class PlayerTeamTest {
    private PlayerTeam playerTeamTest;
    private Player     teamMemberIDTest;
    private Field      teamMemberIDField;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        teamMemberIDTest = new Player(0, null, null, null);
        playerTeamTest = new PlayerTeam(0, null, null, null);

        teamMemberIDField = playerTeamTest.getClass().getDeclaredField("teamMemberID");
        teamMemberIDField.setAccessible(true);
        teamMemberIDField.set(playerTeamTest, teamMemberIDTest.getPlayerID());
    }

    /**
     * Test for teamMember's getter
     */
    @Test
    void getTeamMemberIDTest() {
        if (playerTeamTest.getTeamMemberID() != teamMemberIDTest.getPlayerID())
            throw new AssertionError("Returned wrong team member");
    }

    /**
     *
     */
    @Test
    void setTeamMemberIDTest() throws IllegalAccessException {
        Player localTest = new Player(1, null, null, null);
        playerTeamTest.setTeamMemberID(localTest.getPlayerID());

        Integer fieldValue = (Integer) teamMemberIDField.get(playerTeamTest);

        if (fieldValue.equals(teamMemberIDTest.getPlayerID()))
            throw new AssertionError("Setter did not set value");

        if (!fieldValue.equals(localTest.getPlayerID()))
            throw new AssertionError("Setter set wrong value");
    }
}
