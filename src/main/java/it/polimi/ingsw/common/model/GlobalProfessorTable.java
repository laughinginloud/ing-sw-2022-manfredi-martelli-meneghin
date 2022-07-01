package it.polimi.ingsw.common.model;

import java.util.Optional;

/**
 * Class representing the ProfessorTable
 * @author Sebastiano Meneghin
 */
public final class GlobalProfessorTable {
    private final Player[] professorLocations;

    /**
     * Constructor of the class 'GlobalProfessorTable'
     */
    public GlobalProfessorTable(){ professorLocations = new Player[Color.values().length]; }

    /**
     * Gets the player who is controlling the specific professor
     * @param color Color of the specific professor to locate (not null)
     * @return An optional containing the controlling player, if they exist
     */
    public Optional<Player> getProfessorLocation(Color color) {
        return Optional.ofNullable(professorLocations[color.ordinal()]);
    }

    /**
     * Sets a professor of the GlobalProfessorTable to a specific player
     * @param color Color of the professor to be assigned (not null)
     * @param player Player to assign the professor to (not null)
     */
    public void setProfessorLocation(Color color, Player player) {
        assert player != null: "The player to set professor to is null";

        professorLocations[color.ordinal()] = player;
    }
}
