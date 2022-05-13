package it.polimi.ingsw.controller.command;

public enum GameCommandValues {
    ISLANDARRAY,
    PLAYERARRAY,
    CLOUD,
    CLOUDARRAY,
    CHARACTERCARDARRAY,
    MODEL,

    // An array of color (students or professor)
    COLORARRAY,

    // An array of boolean of various lenght
    BOOLARRAY,

    // An integer representing this turn MotherNature's movement-range
    MOTHERNATUREMOVEMENT,

    // Position of MotherNature
    MOTHERNATURE,

    // Generic map <GameCommandValues, Object>
    MAP,

    // A specified player's entrance
    ENTRANCE,

    // A specified player's DiningRoom
    DININGROOM,

    // The globalProfessorTable linked to the class GameModel
    GLOBALPROFESSORTABLE,

    // The value of the global CoinPool, present only in ExpertMode
    COINPOOL,

    // Num of remaining tower that can be placed on a conquered island too big to be completely filled
    REMAININGTOWER,

    // Num of the Island can't be completely filled
    ISLANDNUM,

    // The player who won the Game
    WINNER,

    // The team that won the Game
    WINNINGTEAM,

    // An array of players that "drawn" the Game
    DRAWERS,

    // CharacterCard that has been played by the player (response Client -> Server)
    PLAYEDCHARACTERCARD,
}
