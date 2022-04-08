package it.polimi.ingsw.model;

public class GlobalProfessorTable {
    private Player[] professorLocations;
    private static GlobalProfessorTable instance;

    private GlobalProfessorTable(){
        professorLocations = new Player[5];
    }

    /**
     * Return the instance of the GlobalProfessorTable. If the instance doesn't exist it creates a new GlobalProfessorTable.
     * @return The unique GlobalProfessorTable
     */
    public static GlobalProfessorTable getInstance(){
        if(instance == null){
            instance = new GlobalProfessorTable();
        }
        return instance;
    }

    /**
     * Return the player who is controlling the specific professor (or null if no one is controlling the professor)
     * @param color Color of the specific professor to locate
     * @return A player controlling a specific professor
     */
    public Player getProfessorLocation(Color color) {
        return professorLocations[color.ordinal()];
    }

    /**
     * Set a professour of the GlobalProfessorTable to a specific player
     * @param color Color of the professor to be assigned
     * @param player Player to assign the professor to
     */
    public void setProfessorLocation(Color color, Player player){
        professorLocations[color.ordinal()] = player;
    }
}
