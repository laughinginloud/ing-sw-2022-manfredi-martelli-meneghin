package it.polimi.ingsw.client.view.gui;

/**
 * Enum of all scenes of the GUI.
 * Each element corresponds to a different scene
 *
 * @author Giovanni Manfredi
 */
public enum Pages {

    /**
     * Initial Scene:
     * The client is shown a landing page with little information (authors and logos)
     * and a play button. If the button is pressed the application starts notifying
     * the Client.java otherwise if the window is closed the application is closed
     */
    INITIAL_PAGE,

    /**
     * Collecting information to connect to the server scene:
     * The client is asked for an IP Address and a port to connect to.
     * The information is then sent to the Client.java
     */
    SERVER_INFO,

    /**
     * Unable to join to the server scene:
     * The client is informed that the game is still in progress, and
     * he won't be able to join. It also suggests him to close the application
     * and try again.
     */
    UNABLE_JOIN,

    /**
     * Connection error scene:
     * The client is informed that the connection to the server was lost
     * and advises him to close the application and try again
     */
    CONNECTION_ERROR,

    /**
     * Ask information to the player scene:
     * The client is asked for a username (different from the forbiddenUsernames)
     * and for how many years has he know magic (MagicAge) and sends it to the
     * VirtualController
     */
    CLIENT_INFO,

    /**
     * Choice between continuing or starting a new game scene:
     * The client is asked whether he wants to play a new game or continue
     * the saved game. The information is then sent to the VirtualController
     */
    GAME_CHOICE,

    /**
     * Ask the player the rules of the game scene:
     * The client is asked for the rules of the game the player wants to play with
     * (2 to 4 player, ExpertMode true or false) and sends the information to
     * the VirtualController
     */
    ASK_RULES,

    /**
     * Waiting room scene:
     * The client is put in a waiting room screen informing him/her
     * that the game will start once enough players are connected
     */
    WAITING_ROOM,

    /**
     * Choose a wizard scene:
     * The client is asked what wizard he/she wants to play with
     * from the one available
     */
    WIZARD_CHOICE,

    /**
     * The game's main scene
     */
    GAME_SCENE,

    /**
     * The game's secondary scene in which each player school board is visible
     */
    SCHOOL_BOARDS,

    /**
     * End of the game scene:
     * The client is shown the game's winner(s) or of a draw
     */
    END_GAME;

    /**
     * Gets the .fxml path of the file corresponding to the page enum
     *
     * @param page the page of which it gets the path of
     * @return the file of the .fxml file corresponding to the page enum
     */
    public static String getPathOf (Pages page) {
        String path = "";

        // Saves the correct page's path into the variable "path"
        switch(page) {
            case INITIAL_PAGE     -> path = "/it/polimi/ingsw/fxml/initialPage.fxml";

            case SERVER_INFO      -> path = "/it/polimi/ingsw/fxml/serverInfoPage.fxml";

            case UNABLE_JOIN      -> path = "/it/polimi/ingsw/fxml/unableToJoinPage.fxml";

            case CONNECTION_ERROR -> path = "/it/polimi/ingsw/fxml/connectionErrorPage.fxml";

            case CLIENT_INFO      -> path = "/it/polimi/ingsw/fxml/clientInfoPage.fxml";

            case GAME_CHOICE      -> path = "/it/polimi/ingsw/fxml/gameChoicePage.fxml";

            case ASK_RULES        -> path = "/it/polimi/ingsw/fxml/askForRulesPage.fxml";

            case WAITING_ROOM     -> path = "/it/polimi/ingsw/fxml/waitingRoomPage.fxml";

            case WIZARD_CHOICE    -> path = "/it/polimi/ingsw/fxml/wizardChoicePage.fxml";

            case GAME_SCENE       -> path = "/it/polimi/ingsw/fxml/gameScenePage.fxml";

            case SCHOOL_BOARDS    -> path = "/it/polimi/ingsw/fxml/playersSchoolBoardPage.fxml";

            case END_GAME         -> path = "/it/polimi/ingsw/fxml/endGamePage.fxml";
        }

        return path;
    }
}

