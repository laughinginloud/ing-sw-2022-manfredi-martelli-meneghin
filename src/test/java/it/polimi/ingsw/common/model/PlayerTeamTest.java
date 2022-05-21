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
    private Player     teamMemberTest;
    private Field      teamMemberField;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        teamMemberTest = new Player(0, null, null, null);
        playerTeamTest = new PlayerTeam(0, null, null, null);

        teamMemberField = playerTeamTest.getClass().getDeclaredField("teamMember");
        teamMemberField.setAccessible(true);
        teamMemberField.set(playerTeamTest, teamMemberTest);
    }

    /**
     * Test for teamMember's getter
     */
    @Test
    void getTeamMemberTest() {
        if (playerTeamTest.getTeamMember() != teamMemberTest)
            throw new AssertionError("Returned wrong team member");
    }

    /**
     *
     */
    @Test
    void setTeamMemberTest() throws IllegalAccessException {
        Player localTest = new Player(0, null, null, null);
        playerTeamTest.setTeamMember(localTest);

        Player fieldValue = (Player) teamMemberField.get(playerTeamTest);

        if (fieldValue.equals(teamMemberTest))
            throw new AssertionError("Setter did not set value");

        if (!fieldValue.equals(localTest))
            throw new AssertionError("Setter set wrong value");
    }
}