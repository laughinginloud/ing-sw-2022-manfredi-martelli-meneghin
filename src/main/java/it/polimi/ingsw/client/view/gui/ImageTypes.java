package it.polimi.ingsw.client.view.gui;

/**
 * Enum used to have an easier access to the images through theirs url path
 * for the ImageViews (ID) that changes their linked Image (then their places in the pages)
 * @author Sebastiano Meneghin
 */
public enum ImageTypes {

    // region EnumImageTypes

    // region CharacterCardImageTypes

    // Enum used for the CharacterCard in the handlers:
    //      - GamePage
    //      - .....
    MONK_IMG,
    FARMER_IMG,
    STBEARER_IMG,
    MAGICIAN_IMG,
    HERBALIST_IMG,
    CENTAUR_IMG,
    JESTER_IMG,
    CAVALIER_IMG,
    MERCHANT_IMG,
    BARD_IMG,
    PRINCESS_IMG,
    THIEF_IMG,

    // endregion CharacterCardImageTypes

    // region AssistantCardImageTypes

    // Enum used for the AssistantCard in the handlers:
    //      - PlayersSchoolBoard
    //      - .....
    ASS_1_IMG,
    ASS_2_IMG,
    ASS_3_IMG,
    ASS_4_IMG,
    ASS_5_IMG,
    ASS_6_IMG,
    ASS_7_IMG,
    ASS_8_IMG,
    ASS_9_IMG,
    ASS_10_IMG,

    // endregion AssistantCardImageTypes

    // region StudentImageTypes

    // Enum used for the Students in the handlers:
    //      - PlayersSchoolBoard
    //      - GamePage
    //      - .....
    STUDENT_GREEN_IMG,
    STUDENT_RED_IMG,
    STUDENT_YELLOW_IMG,
    STUDENT_PINK_IMG,
    STUDENT_BLUE_IMG,

    // endregion StudentImageTypes

    // region TowerImageTypes

    // Enum used for the Tower in the handlers:
    //      - GamePage
    //      - .....
    TOWER_WHITE_IMG,
    TOWER_BLACK_IMG,
    TOWER_GREY_IMG,

    // endregion TowerImageTypes

    // region WizardImageTypes

    // Enum used for the WizardBack in the handlers:
    //      - GamePage
    //      - .....
    WIZARD_NATURE_IMG,
    WIZARD_DESERT_IMG,
    WIZARD_CLOUD_IMG,
    WIZARD_SNOW_IMG,

    // endregion WizardImageTypes

    // region IslandImageTypes

    // Enum used for the Island in the handlers:    theoretically no-one
    ISLAND_0_IMG,
    ISLAND_1_IMG,
    ISLAND_2_IMG,

    // endregion IslandImageTypes

    // region ProfessorImageTypes

    // Enum used for the Professor in the handlers: theoretically no-one
    PROFESSOR_GREEN_IMG,
    PROFESSOR_RED_IMG,
    PROFESSOR_YELLOW_IMG,
    PROFESSOR_PINK_IMG,
    PROFESSOR_BLUE_IMG,

    // endregion ProfessorImageTypes

    // region MiscellaneousImageTypes

    // Enum used for the Miscellaneous Others in the handlers: theoretically no-one
    CLOUD_TILE_IMG,
    COIN_IMG,
    CRANIO_IMG,
    HOME_PAGE_IMG,
    MOTHER_NATURE_IMG,
    NOENTRYTILE_IMG,
    PLAYERS_ICON_IMG,
    POLIMI_LOGO_IMG,
    RETURN_IMG,
    SCHOOL_BOARD_IMG;

    // endregion MiscellaneousImageTypes

    // endregion EnumImageTypes

}
