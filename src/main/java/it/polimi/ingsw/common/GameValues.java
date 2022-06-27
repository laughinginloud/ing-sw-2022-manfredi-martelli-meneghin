package it.polimi.ingsw.common;

public enum GameValues {
    /**
    * An array of Islands
    */
    ISLANDARRAY,

    /**
    * An array of Players
    */
    PLAYERARRAY,

    /**
    * An array of CloudTiles
    */
    CLOUDARRAY,

    /**
    * An array of CharacterCards
    */
    CHARACTERCARDARRAY,

    /**
    * A GameModel representing the GameModel present on the Server's Controller
    */
    MODEL,

    /**
    * An int representing the position of the characterCard into the model's characterCardArray
    */
    CHARACTERCARDPOSITION,

    /**
    * An enumeration constant of PlayCharacterAction correspondent to the character on a CharacterCard
    */
    CHARACTERVALUE,

    /**
    * An array of color (students or professor)
    */
    COLORARRAY,

    /**
    * An array of boolean of various lengths
    */
    BOOLARRAY,

    /**
    * An array of Islands, where MotherNature can be moved on
    */
    MNPOSSIBLEMOVEMENTS,

    /**
    * An int representing the position of MotherNature
    */
    MOTHERNATURE,

    /**
    * A tuple containing:
    *  - left: a player's ID
    *  - right: the entrance of the player
    */
    ENTRANCE,

    /**
    * An array of Entrances, containing all the players' entrances
    */
    ENTRANCEARRAY,

    /**
    * A tuple containing:
    *  - left: a player's ID
    *  - right: the dining room of the player
    */
    DININGROOM,

    /**
    * An array of DiningRooms, containing all the players' diningRooms
    */
    DININGROOMARRAY,

    /**
    * An array of SchoolBoard, containing all the players' schoolBoards
    */
    SCHOOLBOARDARRAY,

    /**
    * The GlobalProfessorTable, linked to the class GameModel
    */
    GLOBALPROFESSORTABLE,

    /**
    * The value of the global CoinPool, present only in ExpertMode
    */
    COINPOOL,

    /**
    * An int indicating the index of the student the client selected - CharacterCard 'MONK', 'PRINCESS',
    */
    STUDENTINDEX,

    /**
    * An int that indicates the index of the island that client selected - CharacterCard 'MONK', 'HERBALIST',
    * 'STANDARD_BEARER'
    */
    ISLANDINDEX,

    /**
    * An int representing the max number of students that can be moved using the characterCard Jester
    */
    MAXMOVEMENTJESTER,

    /**
    * An int representing the number of students that will be moved by the player using the characterCard Jester
    */
    MOVEMENTJESTER,

    /**
    * An Array of Color representing the students present on the Entrance of a player
    */
    ENTRANCESTUDENTS,

    /**
    * An Array of Color representing the students present on a characterCard - CharacterCard 'PRINCESS'
    */
    CARDSTUDENTS,

    /**
    * An int that indicates the index of the student the player selected from the entrance - CharacterCard 'JESTER'
    */
    ENTRANCESTUDENTINDEX,

    /**
    * An int representing the index of the student the player selected from the card's students - CharacterCard 'JESTER'
    */
    CARDSTUDENTINDEX,

    /**
    * An int representing the max number of students that can be moved using the characterCard Bard
    */
    MAXMOVEMENTBARD,

    /**
    * An int representing the number of students that will be moved by the player using the characterCard Bard
    */
    MOVEMENTBARD,

    /**
    * An array of Color representing the entrances' students that can be swapped with diningRoom's students
    * by the player currently playing the characterCard 'BARD'
    */
    BARDSWAPPABLESTUDENTS,

    /**
    * A Map(Color, Boolean[]) that representing which diningRoom's student can be swapped with a selected entrance's student (which is the key)
    */
    BARDSWAPMAP,

    /**
    * A Color representing the Color of the diningRoomTable containing the student that the player
    * want to swap the swappableStudent with - CharacterCard 'BARD'
    */
    DININGROOMTABLECOLOR,

    /**
    * A Color representing the color the player wants to inhibit - CharacterCard 'MERCHANT'
    */
    MERCHANTCOLOR,

    /**
    * An array of color that represent the color of the diningRoomTable from where students can be reduced - CharacterCard 'THIEF'
    */
    REDUCIBLECOLOR,

    /**
    * A color, chosen by the player, representing the student of the students that will be
    * reduced in the player's diningRooms - CharacterCard 'THIEF'
    */
    REDUCECOLOR,

    /**
    * A String representing the string that the server will send to the client as a confirmation that
    * the CharacterCard effect has been correctly applied - CharacterCard 'CAVALIER', 'CENTAUR', 'MAGICIAN', 'MERCHANT'
    */
    CONFIRMATIONSTRING
}
