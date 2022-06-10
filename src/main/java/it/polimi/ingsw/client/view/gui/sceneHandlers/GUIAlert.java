package it.polimi.ingsw.client.view.gui.sceneHandlers;

import javafx.scene.control.Alert;

// TODO - JavaDocs

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
    CHOOSE_ASSISTANT,

    // Alert showed when the user has to select which CharacterCard he wants to play
    SELECT_CHARACTER_CARD,

    // Alert showed when the user has to select a student from the Entrance
    SELECT_ENTRANCE_STUDENT,

    // Alert showed when the user has to select an Island where to move MotherNature or
    // to use the effect of a CharacterCard on
    SELECT_ISLAND,

    // Alert showed when the user has to select a DiningRoomTable where to move the
    // students or from which reduce the number of students on all the players' diningRooms
    SELECT_DINING_ROOM_TABLE,

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
    SELECT_ENTRANCE_STUDENT_OR_CC,

    // Alert showed when the user has to decide where to move the entranceStudent he selected
    // He can choose to move it on an Island or in one of the compatible DiningRoomTables
    MOVE_ENTRANCE_STUDENT_DR_OR_ISL,

    // Alert showed when the user has to decide on which Island move MotherNature,
    // according to the movementPoints of the AssistantCard he selected during the planPhase
    MOVE_MOTHER_NATURE,

    // Alert showed when the user has to select an element between a CharacterCard
    // or an Island where to move MotherNature (used only in the expert mode)
    MOVE_MOTHER_NATURE_OR_CC,

    // Alert showed when the user has to decided whether to play a characterCard or
    // ends his turn
    ASK_END_OF_TURN,

    // Alert showed when a player leave the Game or get disconnected from the Server
    NOTIFY_PLAYER_DISCONNECTION,

    // Alert showed when a player, different by the player is receiving the message, start his turn
    START_ANOTHER_PLAYER_TURN,

    // Alert showed to the player when his turn is ended (when he can't play a CharacterCard and when he
    // can't choose between play a CC or end his turn with a specific alert)
    NOTIFY_END_THIS_PLAYER_TURN;




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
                contentText = "Re-enter a valid Port\n" + "NB! Default Ports is 6556";
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

            case CHOOSE_ASSISTANT -> {
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

            case SELECT_DINING_ROOM_TABLE -> {
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
                title       = "Select a Student or a CharacterCard";
                headerText  = "You may now select a student from the Entrance you wish to move or select a CharacterCard";
                contentText = "After pressing a student in the entrance you won't be able to use a CC util the next phase";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case MOVE_ENTRANCE_STUDENT_DR_OR_ISL -> {
                title       = "Decide where to move the selected Student";
                headerText  = "You may now decide where to move the " + dynamicString + " Student you have\n" +
                              "selected from the entrance";
                contentText = "You can move it on an Island or in the " + dynamicString +  " diningRoomTables,\n" +
                              " if there is still a free seat for the selected student";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case MOVE_MOTHER_NATURE              -> {
                title       = "Select in Island";
                headerText  = "You may now decide where you want to more MotherNature ";
                contentText = "You can move MotherNature as far as the AssistantCards you played in the" +
                              "PlanPhase allows you to do";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case MOVE_MOTHER_NATURE_OR_CC        -> {
                title       = "Select an Island or a CharacterCard!";
                headerText  = "You may now decide if move MotherNature or play before a CharacterCard ";
                contentText = "You can move MotherNature as far as the AssistantCards you played in the" +
                              "PlanPhase allows you to do or choose a CharacterCard which is not too expensive!";
                alertType   =  Alert.AlertType.INFORMATION;

            }

            case ASK_END_OF_TURN                 -> {
                title       = "Ending the turn";
                headerText  = "You may choose to end the turn now by pressing OK or play a CharacterCard by pressing cancel";
                contentText = "If you press OK you will end you turn and pass to the next player. Do you wish to continue?";
                alertType   =  Alert.AlertType.CONFIRMATION;
            }

            case NOTIFY_PLAYER_DISCONNECTION     -> {
                title       = "A player left the game";
                headerText  = "You now will disconnected!";
                contentText = "If you want to resume the game that you were playing, try to reconnect to the Server!\n" +
                              "If the other participants want too, you'll restart from just this point!";
                alertType   =  Alert.AlertType.WARNING;
            }

            case START_ANOTHER_PLAYER_TURN       -> {
                title       = "It's " + dynamicString + " turn!";
                headerText  = "Now you won't be able to play, since it's the turn of" + dynamicString + " !";
                contentText = "Pay attention to the player's moves and plan your best strategy";
                alertType   =  Alert.AlertType.INFORMATION;
            }

            case NOTIFY_END_THIS_PLAYER_TURN     -> {
                title       = "End of your turn";
                headerText  = "This is the end of your own turn!";
                contentText = "Now I can really see the magician that is in you!";
                alertType   =  Alert.AlertType.WARNING;
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
