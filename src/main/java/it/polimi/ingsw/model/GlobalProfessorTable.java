package it.polimi.ingsw.model;

/**
 * Class representing a unique ProfessorTable
 * @author Sebastiano Meneghin
 */
public class GlobalProfessorTable {
    private final Player[] professorLocations;

    public GlobalProfessorTable(){ professorLocations = new Player[Color.values().length]; }

    /**
     * Return the player who is controlling the specific professor (or null if no one is controlling the professor)
     * @param color Color of the specific professor to locate
     * @return A player controlling a specific professor
     */
    public Player getProfessorLocation(Color color) {
        return professorLocations[color.ordinal()];
    }

    /**
     * Set a professor of the GlobalProfessorTable to a specific player
     * @param color Color of the professor to be assigned
     * @param player Player to assign the professor to
     */
    public void setProfessorLocation(Color color, Player player){
        professorLocations[color.ordinal()] = player;
    }
}
