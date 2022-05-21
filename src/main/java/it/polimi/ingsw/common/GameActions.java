package it.polimi.ingsw.common;

public enum GameActions {
    // Array of characterCard playable by the current player
    ENDTURN,

    // Contains the cloudTiles that can be chosen by the player, at the end of his ActonPhase
    CHOOSECLOUD,

    // CHOOSECLOUD + Array of characterCard playable by the current player
    CHOOSECLOUDORPLAYCARD,

    // Contains the students that can be moved and the DiningRoom where they can be moved
    MOVESTUDENT,

    // MOVESTUDENT + Array of characterCard playable by the current player
    MOVESTUDENTORPLAYCARD,

    // Contains the maximum range that motherNature can be moved in
    MOVEMOTHERNATURE,

    // MOVEMOTHERNATURE + Array of characterCard playable by the current player
    MOVEMOTHERNATUREORPLAYCARD,

    // Array of assistantCard playable by the current player
    PLAYASSISTANTCARD,

    // Fields related to the characterCard utilization
    CHARACTERCARDEFFECT,

    // Request whether a game should be loaded
    LOADGAME,

    // Setup phase requests
    RULES, USERNAME, WIZARD,
}