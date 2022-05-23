package it.polimi.ingsw.common;

public enum GameActions {
    // Contains an array of characterCard playable by the current player
    ENDTURN,

    // A signal sent by the client if the player decide to end his turn instead of play a characterCard
    ENDTHISTURN,

    // Contains the cloudTiles that can be chosen by the player, at the end of his ActonPhase
    CHOOSECLOUD,

    // A cloudTile representing the cloudTile chosen by the player during GameStateChooseCloud
    STUDENTSOFSELECTEDCLOUD,

    // CHOOSECLOUD + Array of characterCard playable by the current player
    CHOOSECLOUDORPLAYCARD,

    // Contains the students that can be moved and the DiningRoom where they can be moved
    MOVESTUDENT,

    // MOVESTUDENT + Array of characterCard playable by the current player
    MOVESTUDENTORPLAYCARD,

    // Information about the movement of the student
    MOVESTUDENTINFO,

    // Contains the maximum range that motherNature can be moved in
    MOVEMOTHERNATURE,

    // MOVEMOTHERNATURE + Array of characterCard playable by the current player
    MOVEMOTHERNATUREORPLAYCARD,

    // The mother nature movement selected by the player
    CHOSENMOTHERNATUREMOVEMENT,

    // Array of assistantCard playable by the current player
    PLAYASSISTANTCARD,

    // The AssistantCard chosen by the player during his PlanPhase turn
    CHOSENASSISTANTCARD,

    // Character of the characterCard that has been played by the player
    CHOSENCHARACTER,

    // Fields related to the characterCard utilization
    CHARACTERCARDEFFECT,

    // Request whether a game should be loaded
    LOADGAME,

    // A boolean representing the player's choice to resume or not resume an old game
    LOADGAMECHOICE,

    // Used to ask to the player his username
    USERNAMEANDMAGICAGE,

    // The username inserted by the player
    INSERTEDUSERNAMEANDAGE,

    // Setup phase requests
    RULES,

    WIZARD,

    // A map(GameValues, Object) containing all the fields' values chosen by the player
    CHOSENFIELDSMAP,
}
