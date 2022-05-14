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

    // An array of entrance containing all the players' entrances
    ENTRANCEARRAY,

    // A specified player's DiningRoom
    DININGROOM,

    // An array of diningRoom containing all the players' diningRooms
    DININGROOMARRAY,

    // An array of schoolBoard containing all the players' schoolBoards
    SCHOOLBOARDARRAY,

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

    // An integer that indicates the index of the student the client selected - CharacterCard 'MONK', 'PRINCESS',
    STUDENTINDEX,

    // An integer that indicates the index of the island that client selected - CharacterCard 'MONK', 'HERBALIST',
    // 'STANDARD_BEARER'
    ISLANDINDEX,

    // An integer representing the max number of students that can be moved using the characterCard Jester
    MAXMOVEMENTJESTER,

    // An integer representing the number of students that will be moved by the player using the characterCard Jester
    MOVEMENTJESTER,

    // An Array of color representing the students present on the Entrance of a player
    ENTRANCESTUDENTS,

    // An Array of color representing the students present on a characterCard
    CARDSTUDENTS,

    // An integer that indicates the index of the student the player selected from the entrance - CharacterCard 'JESTER'
    ENTRANCESTUDENTINDEX,

    // An integer that indicates the index of the student the player selected from the card's students - CharacterCard 'JESTER'
    CARDSTUDENTINDEX,

    // An integer representing the max number of students that can be moved using the characterCard Bard
    MAXMOVEMENTBARD,

    // An integer representing the number of students that will be moved by the player using the characterCard Bard
    MOVEMENTBARD,

    // An array of entrances' students that can be swapped with diningRoom's students by the player using the characterCard 'BARD'
    BARDSWAPPABLESTUDENTS,

    // A Map(Color, Boolean[]) that representing which diningRoom's student can be swapped with a selected entrance's student (which is the key)
    BARDSWAPMAP,

    // An integer that indicates the index of the student the player selected from the swappableStudents - CharacterCard 'BARD'
    SWAPPABLESTUDENTINDEX,

    // The color of the diningRoomTable containing the student that the player want to swap the swappableStudent with - CharacterCard 'BARD'
    DININGROOMTABLECOLOR,
}
