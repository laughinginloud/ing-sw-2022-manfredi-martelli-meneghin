package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for class "PlayerTeam"
 */
class PlayerTeamTest {
    private PlayerTeam playerTeamTest;
    private Player     teamMemberTest;

    @BeforeEach
    void setUp() {
        teamMemberTest = new Player(0, null, null, null);
        playerTeamTest = new PlayerTeam(0, null, null, null, teamMemberTest);
    }

    /**
     * Test for teamMember's getter
     */
    @Test
    void getTeamMemberTest() {
        if (playerTeamTest.getTeamMember() != teamMemberTest)
            throw new AssertionError("Returned wrong team member");
    }
}
