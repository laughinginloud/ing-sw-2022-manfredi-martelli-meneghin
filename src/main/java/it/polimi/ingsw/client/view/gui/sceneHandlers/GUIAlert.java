package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.common.model.Character;
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
    INVALID_MAGIC_AGE,

    // Alert showed when the user has to select an AssistantCard during the PlanPhase
    CHOOSEASSISTANT,

    // Alert showed when the user has to select which CharacterCard he wants to play
    SELECT_CHARACTER_CARD,

    // Alert showed when the user has to select a student from the Entrance
    SELECT_ENTRANCE_STUDENT,

    // Alert showed when the user has to select an Island where to move MotherNature or
    // to use the effect of a CharacterCard on
    SELECT_ISLAND,

    // Alert showed when the user has to select a DiningRoomTable where to move the
    // students or from which reduce the number of students on all the players' diningRooms
    SELECT_DININGROOM_TABLE,

    // Alert showed when the user has to select a student from a CharacterCard
    SELECT_CC_STUDENT,

    // Alert showed when the user has to select a Color
    SELECT_COLOR,

    // Alert showed when the user has to select a CloudTile
    SELECT_CLOUD,

    // Alert showed when the user has to select between a CharacterCard
    // and a CloudTile (used only in the expert mode)
    SELECT_CLOUD_OR_CC,

    // Alert showed when the user has to select how many players he
    // wants to move during the ActionPhase or using a CharacterCard's effect
    SELECT_NUM_STUDENTS,

    // Alert showed when the user has to select an element between a
    // CharacterCard or a student from the Entrance (used only in the expert mode)
    SELECT_ENTRANCE_STUDENT_OR_CC;



    // region Seba's Enum Constants

    // endregion Seba's Enum Constants



    //  region Giovanni's Enum Constants

    // endregion Giovanni's Enum Constants



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

            case INVALID_MAGIC_AGE -> {
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

            case SELECT_CHARACTER_CARD -> {
                title       = "Select a Character Card";

                headerText  = "You may now select the CharacterCard you wish to play";
                contentText = "Remember that you can select only the CharacterCards you can play\n" +
                              "NB! The cost of the card will be automatically deducted from you pool";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case SELECT_ENTRANCE_STUDENT -> {
                title       = "Select a Student from the Entrance";
                headerText  = "You may now select a student from the entrance you wish to move";
                contentText = "Remember that you can select only one student each time\n" +
                              "NB! You won't be able to undo the action";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case SELECT_ISLAND -> {
                title       = "Select an Island";
                headerText  = "You may now select the desired island";
                contentText = "Remember that you won't have have other chances during this ActionTurn!";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case SELECT_DININGROOM_TABLE -> {
                title       = "Select a DiningRoomTable";
                headerText  = "You may now select a DiningRoomTable";
                contentText = "Choosing it you'll decide with which DiningRoomTable interact";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case SELECT_CC_STUDENT -> {
                title       = "Select a Student";
                headerText  = "You may now select a student from the CharacterCard you played";
                contentText = "Choose only one student from the student present on the CharacterCard: " + dynamicString;
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case SELECT_COLOR -> {
                title       = "Select a Color";
                headerText  = "You may now select a Color";
                contentText = "Choose it clicking on the Dining Room Table of the desired Color!";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case SELECT_CLOUD -> {
                title       = "Select a CloudTile";
                headerText  = "You may now select a CloudTile";
                contentText = "Choose it in order to take the students from its and\n" + "move them on your entrance!";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case SELECT_CLOUD_OR_CC -> {
                title       = "Select a cloudTile or a CharacterCard";
                headerText  = "You can decide to play a CharacterCard or select a CloudTile";
                contentText = "Choose one of them clicking on the desired CharacterCard or on the\n" +
                              "CloudTile with the best students!";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case SELECT_NUM_STUDENTS -> {
                title       = "Select a number of students to move";
                headerText  = "You may now select a number of students you wish to move";
                contentText = "Use the input panel that just appeared to input you choice\n" +
                              "NB! You are able to choose from a limited set of students";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case SELECT_ENTRANCE_STUDENT_OR_CC -> {
                title       = "Select a student in the Entrance to move or select a CharacterCard";
                headerText  = "You may now select a student from the entrance you wish to move or select a CharacterCard";
                contentText = "After pressing a student in the entrance you won't be able to use a CC util the next phase";
                alertType   =  Alert.AlertType.INFORMATION;
            }



            // region Seba's Cases

            // endregion Seba's Cases




            // region Giovanni's Cases

            // endregion Giovanni's Cases


        }

        // Creates a new Alter and fill it fields with the chosen text values, depending on the guiAlterType
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }
}
