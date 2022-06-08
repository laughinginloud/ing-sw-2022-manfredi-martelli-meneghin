package it.polimi.ingsw.common;

public enum GameValues {
    // An array of Islands
    // USED IN MAP
    ISLANDARRAY,

    // An array of Players
    // USED IN MAP
    PLAYERARRAY,

    // An array of CloudTiles
    // USED IN MAP
    CLOUDARRAY,

    // An array of CharacterCards
    // USED IN MAP
    CHARACTERCARDARRAY,

    // A GameModel representing the GameModel present on the Server's Controller
    // USED IN MAP
    MODEL,

    // An int representing the position of the characterCard into the model's characterCardArray
    // USED IN MAP
    CHARACTERCARDPOSITION,

    // An enumeration constant of Character correspondent to the character on a CharacterCard
    // USED IN MAP
    CHARACTERVALUE,

    // An array of color (students or professor)
    // USED IN MAP
    COLORARRAY,

    // An array of boolean of various lengths
    // USED IN MAP
    BOOLARRAY,

    // An array of Islands, where MotherNature can be moved on
    // USED IN MAP
    MNPOSSIBLEMOVEMENTS,

    // An int representing the position of MotherNature
    // USED IN MAP
    MOTHERNATURE,

    // A tuple containing:
    //  - left: a player's ID
    //  - right: the entrance of the player
    // USED IN MAP
    ENTRANCE,

    // An array of Entrances, containing all the players' entrances
    // USED IN MAP
    ENTRANCEARRAY,

    // A tuple containing:
    //  - left: a player's ID
    //  - right: the dining room of the player
    // USED IN MAP
    DININGROOM,

    // An array of DiningRooms, containing all the players' diningRooms
    // USED IN MAP
    DININGROOMARRAY,

    // An array of SchoolBoard, containing all the players' schoolBoards
    // USED IN MAP
    SCHOOLBOARDARRAY,

    // The GlobalProfessorTable, linked to the class GameModel
    // USED IN MAP
    GLOBALPROFESSORTABLE,

    // The value of the global CoinPool, present only in ExpertMode
    // USED IN MAP
    COINPOOL,

    // An int indicating the index of the student the client selected - CharacterCard 'MONK', 'PRINCESS',
    // USED IN MAP
    STUDENTINDEX,

    // An int that indicates the index of the island that client selected - CharacterCard 'MONK', 'HERBALIST',
    // 'STANDARD_BEARER'
    // USED IN MAP
    ISLANDINDEX,

    // An int representing the max number of students that can be moved using the characterCard Jester
    // USED IN MAP
    MAXMOVEMENTJESTER,

    // An int representing the number of students that will be moved by the player using the characterCard Jester
    // USED IN MAP
    MOVEMENTJESTER,

    // An Array of Color representing the students present on the Entrance of a player
    // USED IN MAP
    ENTRANCESTUDENTS,

    // An Array of Color representing the students present on a characterCard - CharacterCard 'PRINCESS'
    // USED IN MAP
    CARDSTUDENTS,

    // An int that indicates the index of the student the player selected from the entrance - CharacterCard 'JESTER'
    // USED IN MAP
    ENTRANCESTUDENTINDEX,

    // An int representing the index of the student the player selected from the card's students - CharacterCard 'JESTER'
    // USED IN MAP
    CARDSTUDENTINDEX,

    // An int representing the max number of students that can be moved using the characterCard Bard
    // USED IN MAP
    MAXMOVEMENTBARD,

    // An int representing the number of students that will be moved by the player using the characterCard Bard
    // USED IN MAP
    MOVEMENTBARD,

    // An array of Color representing the entrances' students that can be swapped with diningRoom's students
    // by the player currently playing the characterCard 'BARD'
    // USED IN MAP
    BARDSWAPPABLESTUDENTS,

    // A Map(Color, Boolean[]) that representing which diningRoom's student can be swapped with a selected entrance's student (which is the key)
    // USED IN MAP
    BARDSWAPMAP,

    // A Color representing the Color of the diningRoomTable containing the student that the player
    // want to swap the swappableStudent with - CharacterCard 'BARD'
    // USED IN MAP
    DININGROOMTABLECOLOR,

    // A Color representing the color the player wants to inhibit - CharacterCard 'MERCHANT'
    // USED IN MAP
    MERCHANTCOLOR,

    // An array of color that represent the color of the diningRoomTable from where students can be reduced - CharacterCard 'THIEF'
    REDUCIBLECOLOR,

    // A color, chosen by the player, representing the student of the students that will be
    // reduced in the player's diningRooms - CharacterCard 'THIEF'
    // USED IN MAP
    REDUCECOLOR,

    // A String representing the string that the server will send to the client as a confirmation that
    // the CharacterCard effect has been correctly applied - CharacterCard 'CAVALIER', 'CENTAUR', 'MAGICIAN', 'MERCHANT'
    // USED IN MAP
    CONFIRMATIONSTRING,
}
