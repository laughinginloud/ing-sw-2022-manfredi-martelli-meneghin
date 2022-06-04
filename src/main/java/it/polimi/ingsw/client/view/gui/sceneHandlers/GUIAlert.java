package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.Pages;
import javafx.scene.control.Alert;

public enum GUIAlert {
    // Used when the user press the exitButton or press ALT+F4 in order to exit the game
    EXIT,

    // Alert showed when the user entered a wrong IP Address during the connection phase
    INVALID_IP,

    // Alert showed when the user entered a wrong Server Port during the connection phase
    INVALID_PORT,

    // Alert showed when the user entered a wrong username (null or greater than 20)
    INVALID_USERNAME,

    // Alert showed when the user entered a wrong magicAge (negative)
    INVALID_MAGICAGE,

    // Alert showed when the user has to select an AssistantCard during the PlanPhase
    CHOOSEASSISTANT,

    PLAY_CHARACTERCARD;


    /**
     * Creates an Alert according to the GUIAlertType is needed
     * @param guiAlterType The GUIAlertType we want to create
     * @param dynamicString A string representing a dynamicMessage we want to show to the user
     *                      looking at the Alert
     * @return The Alert that has been created
     */
    public static Alert getAlert (GUIAlert guiAlterType, String dynamicString) {
        Alert.AlertType alertType   = null;
        String          title       = "";
        String          headerText  = "";
        String          contentText = "";

        // Sets the Alert fields to specific values depending on the
        switch(guiAlterType) {
            case EXIT -> {
                title       = "Exiting the game";
                headerText  = "You're about to exit the game!";
                contentText = "You will close Eriantys. Do you want to exit anyways?";
                alertType   = Alert.AlertType.CONFIRMATION;
            }
            case INVALID_IP -> {
                title       = "Invalid Server IP Address";
                headerText  = "You have entered an invalid Server IP Address: " + dynamicString;
                contentText = "Re-enter a valid Server IP Address\n" + "NB! Valid Server IP Addresses are: localhost or 127.0.0.1";
                alertType   = Alert.AlertType.WARNING;
            }
            case INVALID_PORT -> {
                title       = "Invalid Port";
                headerText  = "You have entered an invalid Port: " + dynamicString;
                contentText = "Re-enter a valid Port\n" + "NB! Default Ports is 3307";
                alertType   = Alert.AlertType.WARNING;
            }
            case INVALID_USERNAME -> {
                title       = "Invalid username";
                headerText  = "You have entered and invalid Username: " + dynamicString;
                contentText = "Re-enter a valid username\n" + "NB! A valid username is between 1 and 10 characters";
                alertType   = Alert.AlertType.WARNING;
            }
            case INVALID_MAGICAGE -> {
                title       = "Invalid magicAge";
                headerText  = "You have entered and invalid number of years: " + dynamicString;
                contentText = "Re-enter a valid number of years\n" + "NB! A valid number of years is positive";
                alertType   = Alert.AlertType.WARNING;
            }

            case CHOOSEASSISTANT -> {
                title       = "Select Assistant Card";
                headerText  = "It's time to select the AssistantCard you want to play!" + dynamicString;
                contentText = "Remember that the lower is the number of the card, the earlier will be your turn";
                alertType   = Alert.AlertType.INFORMATION;
            }

            case PLAY_CHARACTERCARD -> {
                title       = "Play Character Card";
                headerText  = "You may now select the CharacterCard you wish to play";
                contentText = "Remember that you can select only the CharacterCard you can play\n" +
                              "NB! The cost of the card will be automatically deducted from you pool";
                alertType   =  Alert.AlertType.INFORMATION;
            }
        }

        // Creates a new Alter and fill it fields with the chosen text values, depending on the guiAlterType
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }
}
