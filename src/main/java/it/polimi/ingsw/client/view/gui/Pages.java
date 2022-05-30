package it.polimi.ingsw.client.view.gui;

public enum Pages {
    SERVER_INFO,

    UNABLE_JOIN,

    CLIENT_INFO,

    GAME_CHOICE,

    ASK_RULES,

    WAITING_ROOM,

    WIZARD_CHOICE,

    GAME,

    SCHOOL_BOARDS,

    END_GAME;

    public static String getPathOf (Pages page) {
        String path = "";

        // Saves the correct page's path into the variable "path"
        switch(page) {
            case SERVER_INFO   -> path = "/it/polimi/ingsw/fxml/serverInfoPage.fxml";

            case UNABLE_JOIN   -> path = "/it/polimi/ingsw/fxml/unableToJoinPage.fxml";

            case CLIENT_INFO   -> path = "/it/polimi/ingsw/fxml/clientInfoPage.fxml";

            case GAME_CHOICE   -> path = "/it/polimi/ingsw/fxml/gameChoicePage.fxml";

            case ASK_RULES     -> path = "/it/polimi/ingsw/fxml/askForRulesPage.fxml";

            case WAITING_ROOM  -> path = "/it/polimi/ingsw/fxml/waitingRoomPage.fxml";

            case WIZARD_CHOICE -> path = "/it/polimi/ingsw/fxml/wizardChoicePage.fxml";

            case GAME          -> path = "/it/polimi/ingsw/fxml/gamePage.fxml";

            case SCHOOL_BOARDS -> path = "/it/polimi/ingsw/fxml/playersSchoolBoardPage.fxml";

            case END_GAME      -> path = "/it/polimi/ingsw/fxml/endGamePage.fxml";
        }

        return path;
    }
}

