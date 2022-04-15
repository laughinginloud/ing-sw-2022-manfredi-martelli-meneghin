package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for class "PlayerDecoratorTeam"
 */
class PlayerDecoratorTeamTest {
    private PlayerDecoratorTeam playerDecoratorTeamTest;
    private Player              teamMemberTest;

    @BeforeEach
    void setUp() {
        teamMemberTest = new Player(0, null, null, null);
        playerDecoratorTeamTest = new PlayerDecoratorTeam(new Player(0, null, null, null), teamMemberTest);
    }

    /**
     * Test for teamMember's getter
     */
    @Test
    void getTeamMemberTest() {
        if (playerDecoratorTeamTest.getTeamMember() != teamMemberTest)
            throw new AssertionError("Returned wrong team member");
    }
}
