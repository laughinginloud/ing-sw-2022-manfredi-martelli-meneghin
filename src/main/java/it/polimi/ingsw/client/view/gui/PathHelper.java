package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;

/**
 * Class containing useful support method for FXMLPath and HandlerPath usage
 * @author Sebastiano
 */
public class PathHelper {

    // region Translators/Switches

    // region FXML/Handler Translator

    /**
     * Gets the FXMlPath required to set an Image in the FXML Page, starting from the HandlerPath
     * @param handlerPath The handlerPath we want to translate into a FXMLPath
     * @return A String representing the FXMLPath correspondent to the handlerPath
     */
    public static String fromHandlerPathToFXMLPath (String handlerPath) {
        String fxmlPath = "";

        switch(handlerPath) {

            // CharacterCards
            case "it/polimi/ingsw/images/characterCards/0_MONK.jpg"           -> fxmlPath = "@../images/characterCards/0_MONK.jpg";
            case "it/polimi/ingsw/images/characterCards/1_FARMER.jpg"         -> fxmlPath = "@../images/characterCards/1_FARMER.jpg";
            case "it/polimi/ingsw/images/characterCards/2_STANDARDBEARER.jpg" -> fxmlPath = "@../images/characterCards/2_STANDARDBEARER.jpg";
            case "it/polimi/ingsw/images/characterCards/3_MAGICIAN.jpg"       -> fxmlPath = "@../images/characterCards/3_MAGICIAN.jpg";
            case "it/polimi/ingsw/images/characterCards/4_HERBALIST.jpg"      -> fxmlPath = "@../images/characterCards/4_HERBALIST.jpg";
            case "it/polimi/ingsw/images/characterCards/5_CENTAUR.jpg"        -> fxmlPath = "@../images/characterCards/5_CENTAUR.jpg";
            case "it/polimi/ingsw/images/characterCards/6_JESTER.jpg"         -> fxmlPath = "@../images/characterCards/6_JESTER.jpg";
            case "it/polimi/ingsw/images/characterCards/7_CAVALIER.jpg"       -> fxmlPath = "@../images/characterCards/7_CAVALIER.jpg";
            case "it/polimi/ingsw/images/characterCards/8_MERCHANT.jpg"       -> fxmlPath = "@../images/characterCards/8_MERCHANT.jpg";
            case "it/polimi/ingsw/images/characterCards/9_BARD.jpg"           -> fxmlPath = "@../images/characterCards/9_BARD.jpg";
            case "it/polimi/ingsw/images/characterCards/10_PRINCESS.jpg"      -> fxmlPath = "@../images/characterCards/10_PRINCESS.jpg";
            case "it/polimi/ingsw/images/characterCards/11_THIEF.jpg"         -> fxmlPath = "@../images/characterCards/11_THIEF.jpg";


            // AssistantCards
            case "it/polimi/ingsw/images/assistantCards/assistantCard1.png"       -> fxmlPath = "@../images/assistantCards/assistantCard1.png";
            case "it/polimi/ingsw/images/assistantCards/assistantCard2.png"       -> fxmlPath = "@../images/assistantCards/assistantCard2.png";
            case "it/polimi/ingsw/images/assistantCards/assistantCard3.png"       -> fxmlPath = "@../images/assistantCards/assistantCard3.png";
            case "it/polimi/ingsw/images/assistantCards/assistantCard4.png"       -> fxmlPath = "@../images/assistantCards/assistantCard4.png";
            case "it/polimi/ingsw/images/assistantCards/assistantCard5.png"       -> fxmlPath = "@../images/assistantCards/assistantCard5.png";
            case "it/polimi/ingsw/images/assistantCards/assistantCard6.png"       -> fxmlPath = "@../images/assistantCards/assistantCard6.png";
            case "it/polimi/ingsw/images/assistantCards/assistantCard7.png"       -> fxmlPath = "@../images/assistantCards/assistantCard7.png";
            case "it/polimi/ingsw/images/assistantCards/assistantCard8.png"       -> fxmlPath = "@../images/assistantCards/assistantCard8.png";
            case "it/polimi/ingsw/images/assistantCards/assistantCard9.png"       -> fxmlPath = "@../images/assistantCards/assistantCard9.png";
            case "it/polimi/ingsw/images/assistantCards/assistantCard10.png"      -> fxmlPath = "@../images/assistantCards/assistantCard10.png";


            // Students
            case "it/polimi/ingsw/images/students/student_red.png"    -> fxmlPath = "@../images/students/student_red.png";
            case "it/polimi/ingsw/images/students/student_green.png"  -> fxmlPath = "@../images/students/student_green.png";
            case "it/polimi/ingsw/images/students/student_yellow.png" -> fxmlPath = "@../images/students/student_yellow.png";
            case "it/polimi/ingsw/images/students/student_pink.png"   -> fxmlPath = "@../images/students/student_pink.png";
            case "it/polimi/ingsw/images/students/student_blue.png"   -> fxmlPath = "@../images/students/student_blue.png";


            // Towers
            case "it/polimi/ingsw/images/towers/black_tower.png"      -> fxmlPath = "@../images/towers/black_tower.png";
            case "it/polimi/ingsw/images/towers/grey_tower.png"       -> fxmlPath = "@../images/towers/grey_tower.png";
            case "it/polimi/ingsw/images/towers/white_tower.png"      -> fxmlPath = "@../images/towers/white_tower.png";


            // Wizards
            case "it/polimi/ingsw/images/wizards/0_NATURE.jpg"      -> fxmlPath = "@../images/wizards/0_NATURE.jpg";
            case "it/polimi/ingsw/images/wizards/1_DESERT.jpg"      -> fxmlPath = "@../images/wizards/1_DESERT.jpg";
            case "it/polimi/ingsw/images/wizards/2_CLOUD.jpg"       -> fxmlPath = "@../images/wizards/2_CLOUD.jpg";
            case "it/polimi/ingsw/images/wizards/3_SNOW.jpg"        -> fxmlPath = "@../images/wizards/3_SNOW.jpg";


            // Islands
            case "it/polimi/ingsw/images/islands/island0.png"  -> fxmlPath = "@../images/islands/island0.png";
            case "it/polimi/ingsw/images/islands/island1.png"  -> fxmlPath = "@../images/islands/island1.png";
            case "it/polimi/ingsw/images/islands/island2.png"  -> fxmlPath = "@../images/islands/island2.png";


            // Professors
            case "it/polimi/ingsw/images/professors/professor_green.png"  -> fxmlPath = "@../images/professors/professor_green.png";
            case "it/polimi/ingsw/images/professors/professor_red.png"    -> fxmlPath = "@../images/professors/professor_red.png";
            case "it/polimi/ingsw/images/professors/professor_yellow.png" -> fxmlPath = "@../images/professors/professor_yellow.png";
            case "it/polimi/ingsw/images/professors/professor_pink.png"   -> fxmlPath = "@../images/professors/professor_pink.png";
            case "it/polimi/ingsw/images/professors/professor_blue.png"   -> fxmlPath = "@../images/professors/professor_blue.png";


            // Miscellaneous
            case "it/polimi/ingsw/images/cloudTile.png"        -> fxmlPath = "@../images/cloudTile.png";
            case "it/polimi/ingsw/images/coin.png"             -> fxmlPath = "@../images/coin.png";
            case "it/polimi/ingsw/images/cranio.png"           -> fxmlPath = "@../images/cranio.png";
            case "it/polimi/ingsw/images/mother_nature.png"    -> fxmlPath = "@../images/mother_nature.png";
            case "it/polimi/ingsw/images/noEntryTile.png"      -> fxmlPath = "@../images/noEntryTile.png";
            case "it/polimi/ingsw/images/players_icon.png"     -> fxmlPath = "@../images/players_icon.png";
            case "it/polimi/ingsw/images/polimi.png"           -> fxmlPath = "@../images/polimi.png";
            case "it/polimi/ingsw/images/return.png"           -> fxmlPath = "@../images/return.png";
            case "it/polimi/ingsw/images/school_board.png"     -> fxmlPath = "@../images/school_board.png";
            case "it/polimi/ingsw/images/home_page.jpg"        -> fxmlPath = "@../images/home_page.jpg";
        }

        return fxmlPath;
    }

    /**
     * Gets the HandlerPath  from the FXMlPath
     * @param fxmlPath The fxmlPath we want to translate into a HandlerPath
     * @return A String representing the HandlerPath correspondent to the fxmlPath
     */
    public static String fromFXMLPathToHandlerPath (String fxmlPath) {
        String handlerPath = "";

        switch(fxmlPath) {

            // CharacterCards
            case "@../images/characterCards/0_MONK.jpg"           -> handlerPath = "it/polimi/ingsw/images/characterCards/0_MONK.jpg";
            case "@../images/characterCards/1_FARMER.jpg"         -> handlerPath = "it/polimi/ingsw/images/characterCards/1_FARMER.jpg";
            case "@../images/characterCards/2_STANDARDBEARER.jpg" -> handlerPath = "it/polimi/ingsw/images/characterCards/2_STANDARDBEARER.jpg";
            case "@../images/characterCards/3_MAGICIAN.jpg"       -> handlerPath = "it/polimi/ingsw/images/characterCards/3_MAGICIAN.jpg";
            case "@../images/characterCards/4_HERBALIST.jpg"      -> handlerPath = "it/polimi/ingsw/images/characterCards/4_HERBALIST.jpg";
            case "@../images/characterCards/5_CENTAUR.jpg"        -> handlerPath = "it/polimi/ingsw/images/characterCards/5_CENTAUR.jpg";
            case "@../images/characterCards/6_JESTER.jpg"         -> handlerPath = "it/polimi/ingsw/images/characterCards/6_JESTER.jpg";
            case "@../images/characterCards/7_CAVALIER.jpg"       -> handlerPath = "it/polimi/ingsw/images/characterCards/7_CAVALIER.jpg";
            case "@../images/characterCards/8_MERCHANT.jpg"       -> handlerPath = "it/polimi/ingsw/images/characterCards/8_MERCHANT.jpg";
            case "@../images/characterCards/9_BARD.jpg"           -> handlerPath = "it/polimi/ingsw/images/characterCards/9_BARD.jpg";
            case "@../images/characterCards/10_PRINCESS.jpg"      -> handlerPath = "it/polimi/ingsw/images/characterCards/10_PRINCESS.jpg";
            case "@../images/characterCards/11_THIEF.jpg"         -> handlerPath = "it/polimi/ingsw/images/characterCards/11_THIEF.jpg";


            // AssistantCards
            case "@../images/assistantCards/assistantCard1.png"       -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard1.png";
            case "@../images/assistantCards/assistantCard2.png"       -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard2.png";
            case "@../images/assistantCards/assistantCard3.png"       -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard3.png";
            case "@../images/assistantCards/assistantCard4.png"       -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard4.png";
            case "@../images/assistantCards/assistantCard5.png"       -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard5.png";
            case "@../images/assistantCards/assistantCard6.png"       -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard6.png";
            case "@../images/assistantCards/assistantCard7.png"       -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard7.png";
            case "@../images/assistantCards/assistantCard8.png"       -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard8.png";
            case "@../images/assistantCards/assistantCard9.png"       -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard9.png";
            case "@../images/assistantCards/assistantCard10.png"      -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard10.png";


            // Students
            case "@../images/students/student_red.png"    -> handlerPath = "it/polimi/ingsw/images/students/student_red.png";
            case "@../images/students/student_green.png"  -> handlerPath = "it/polimi/ingsw/images/students/student_green.png";
            case "@../images/students/student_yellow.png" -> handlerPath = "it/polimi/ingsw/images/students/student_yellow.png";
            case "@../images/students/student_pink.png"   -> handlerPath = "it/polimi/ingsw/images/students/student_pink.png";
            case "@../images/students/student_blue.png"   -> handlerPath = "it/polimi/ingsw/images/students/student_blue.png";


            // Towers
            case "@../images/towers/black_tower.png"      -> handlerPath = "it/polimi/ingsw/images/towers/black_tower.png";
            case "@../images/towers/grey_tower.png"       -> handlerPath = "it/polimi/ingsw/images/towers/grey_tower.png";
            case "@../images/towers/white_tower.png"      -> handlerPath = "it/polimi/ingsw/images/towers/white_tower.png";


            // Wizards
            case "@../images/wizards/0_NATURE.jpg"      -> handlerPath = "it/polimi/ingsw/images/wizards/0_NATURE.jpg";
            case "@../images/wizards/1_DESERT.jpg"      -> handlerPath = "it/polimi/ingsw/images/wizards/1_DESERT.jpg";
            case "@../images/wizards/2_CLOUD.jpg"       -> handlerPath = "it/polimi/ingsw/images/wizards/2_CLOUD.jpg";
            case "@../images/wizards/3_SNOW.jpg"        -> handlerPath = "it/polimi/ingsw/images/wizards/3_SNOW.jpg";


            // Islands
            case "@../images/islands/island0.png"  -> handlerPath = "it/polimi/ingsw/images/islands/island0.png";
            case "@../images/islands/island1.png"  -> handlerPath = "it/polimi/ingsw/images/islands/island1.png";
            case "@../images/islands/island2.png"  -> handlerPath = "it/polimi/ingsw/images/islands/island2.png";


            // Professors
            case "@../images/professors/professor_green.png"  -> handlerPath = "it/polimi/ingsw/images/professors/professor_green.png";
            case "@../images/professors/professor_red.png"    -> handlerPath = "it/polimi/ingsw/images/professors/professor_red.png";
            case "@../images/professors/professor_yellow.png" -> handlerPath = "it/polimi/ingsw/images/professors/professor_yellow.png";
            case "@../images/professors/professor_pink.png"   -> handlerPath = "it/polimi/ingsw/images/professors/professor_pink.png";
            case "@../images/professors/professor_blue.png"   -> handlerPath = "it/polimi/ingsw/images/professors/professor_blue.png";


            // Miscellaneous
            case "@../images/cloudTile.png"        -> handlerPath = "it/polimi/ingsw/images/cloudTile.png";
            case "@../images/coin.png"             -> handlerPath = "it/polimi/ingsw/images/coin.png";
            case "@../images/cranio.png"           -> handlerPath = "it/polimi/ingsw/images/cranio.png";
            case "@../images/mother_nature.png"    -> handlerPath = "it/polimi/ingsw/images/mother_nature.png";
            case "@../images/noEntryTile.png"      -> handlerPath = "it/polimi/ingsw/images/noEntryTile.png";
            case "@../images/players_icon.png"     -> handlerPath = "it/polimi/ingsw/images/players_icon.png";
            case "@../images/polimi.png"           -> handlerPath = "it/polimi/ingsw/images/polimi.png";
            case "@../images/return.png"           -> handlerPath = "it/polimi/ingsw/images/return.png";
            case "@../images/school_board.png"     -> handlerPath = "it/polimi/ingsw/images/school_board.png";
            case "@../images/home_page.jpg"        -> handlerPath = "it/polimi/ingsw/images/home_page.jpg";
        }

        return handlerPath;
    }

    // endregion FXML/Handler Translator

    // region ImageTypesTranslator
    /**
     * Gets the HandlerPath associated to a specific ImageType enum constant
     * @param imageType An enumeration constant of ImageType we want the associated HandlerPath of
     * @return A string representing the HandlerPath associated to the imageType
     */
    public static String fromImageTypesToHandlerPath (ImageTypes imageType) {
        String handlerPath = "";
        switch (imageType) {

            // CharacterCards
            case MONK_IMG      -> handlerPath = "it/polimi/ingsw/images/characterCards/0_MONK.jpg";
            case FARMER_IMG    -> handlerPath = "it/polimi/ingsw/images/characterCards/1_FARMER.jpg";
            case STBEARER_IMG  -> handlerPath = "it/polimi/ingsw/images/characterCards/2_STANDARDBEARER.jpg";
            case MAGICIAN_IMG  -> handlerPath = "it/polimi/ingsw/images/characterCards/3_MAGICIAN.jpg";
            case HERBALIST_IMG -> handlerPath = "it/polimi/ingsw/images/characterCards/4_HERBALIST.jpg";
            case CENTAUR_IMG   -> handlerPath = "it/polimi/ingsw/images/characterCards/5_CENTAUR.jpg";
            case JESTER_IMG    -> handlerPath = "it/polimi/ingsw/images/characterCards/6_JESTER.jpg";
            case CAVALIER_IMG  -> handlerPath = "it/polimi/ingsw/images/characterCards/7_CAVALIER.jpg";
            case MERCHANT_IMG  -> handlerPath = "it/polimi/ingsw/images/characterCards/8_MERCHANT.jpg";
            case BARD_IMG      -> handlerPath = "it/polimi/ingsw/images/characterCards/9_BARD.jpg";
            case PRINCESS_IMG  -> handlerPath = "it/polimi/ingsw/images/characterCards/10_PRINCESS.jpg";
            case THIEF_IMG     -> handlerPath = "it/polimi/ingsw/images/characterCards/11_THIEF.jpg";


            // AssistantCards
            case ASS_1_IMG  -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard1.png";
            case ASS_2_IMG  -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard2.png";
            case ASS_3_IMG  -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard3.png";
            case ASS_4_IMG  -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard4.png";
            case ASS_5_IMG  -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard5.png";
            case ASS_6_IMG  -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard6.png";
            case ASS_7_IMG  -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard7.png";
            case ASS_8_IMG  -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard8.png";
            case ASS_9_IMG  -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard9.png";
            case ASS_10_IMG -> handlerPath = "it/polimi/ingsw/images/assistantCards/assistantCard10.png";


            // Students
            case STUDENT_GREEN_IMG  -> handlerPath = "it/polimi/ingsw/images/students/student_red.png";
            case STUDENT_RED_IMG    -> handlerPath = "it/polimi/ingsw/images/students/student_green.png";
            case STUDENT_YELLOW_IMG -> handlerPath = "it/polimi/ingsw/images/students/student_yellow.png";
            case STUDENT_PINK_IMG   -> handlerPath = "it/polimi/ingsw/images/students/student_pink.png";
            case STUDENT_BLUE_IMG   -> handlerPath = "it/polimi/ingsw/images/students/student_blue.png";


            // Towers
            case TOWER_WHITE_IMG -> handlerPath = "it/polimi/ingsw/images/towers/white_tower.png";
            case TOWER_BLACK_IMG -> handlerPath = "it/polimi/ingsw/images/towers/black_tower.png";
            case TOWER_GREY_IMG  -> handlerPath = "it/polimi/ingsw/images/towers/grey_tower.png";


            // Wizards
            case WIZARD_NATURE_IMG -> handlerPath = "it/polimi/ingsw/images/wizards/0_NATURE.jpg";
            case WIZARD_DESERT_IMG -> handlerPath = "it/polimi/ingsw/images/wizards/1_DESERT.jpg";
            case WIZARD_CLOUD_IMG  -> handlerPath = "it/polimi/ingsw/images/wizards/2_CLOUD.jpg";
            case WIZARD_SNOW_IMG   -> handlerPath = "it/polimi/ingsw/images/wizards/3_SNOW.jpg";


            // Islands
            case ISLAND_0_IMG -> handlerPath = "it/polimi/ingsw/images/islands/island0.png";
            case ISLAND_1_IMG -> handlerPath = "it/polimi/ingsw/images/islands/island1.png";
            case ISLAND_2_IMG -> handlerPath = "it/polimi/ingsw/images/islands/island2.png";


            // Professors
            case PROFESSOR_GREEN_IMG  -> handlerPath = "it/polimi/ingsw/images/professors/professor_green.png";
            case PROFESSOR_RED_IMG    -> handlerPath = "it/polimi/ingsw/images/professors/professor_red.png";
            case PROFESSOR_YELLOW_IMG -> handlerPath = "it/polimi/ingsw/images/professors/professor_yellow.png";
            case PROFESSOR_PINK_IMG   -> handlerPath = "it/polimi/ingsw/images/professors/professor_pink.png";
            case PROFESSOR_BLUE_IMG   -> handlerPath = "it/polimi/ingsw/images/professors/professor_blue.png";


            // Miscellaneous
            case CLOUD_TILE_IMG    -> handlerPath = "it/polimi/ingsw/images/cloudTile.png";
            case COIN_IMG          -> handlerPath = "it/polimi/ingsw/images/coin.png";
            case CRANIO_IMG        -> handlerPath = "it/polimi/ingsw/images/cranio.png";
            case MOTHER_NATURE_IMG -> handlerPath = "it/polimi/ingsw/images/mother_nature.png";
            case NOENTRYTILE_IMG   -> handlerPath = "it/polimi/ingsw/images/noEntryTile.png";
            case PLAYERS_ICON_IMG  -> handlerPath = "it/polimi/ingsw/images/players_icon.png";
            case POLIMI_LOGO_IMG   -> handlerPath = "it/polimi/ingsw/images/polimi.png";
            case RETURN_IMG        -> handlerPath = "it/polimi/ingsw/images/return.png";
            case SCHOOL_BOARD_IMG  -> handlerPath = "it/polimi/ingsw/images/school_board.png";
            case HOME_PAGE_IMG     -> handlerPath = "it/polimi/ingsw/images/home_page.jpg";
        }

        return handlerPath;
    }

    /**
     * Gets the FXMLPath associated to a specific ImageType enum constant
     * @param imageTypes An enumeration constant of ImageType we want the associated FXMLPath of
     * @return A string representing the FXMLPath associated to the imageType
     */
    public static String fromImageTypesToFXMLPath (ImageTypes imageTypes) { return fromHandlerPathToFXMLPath(fromImageTypesToHandlerPath(imageTypes)); }

    /**
     * Gets the ImageTypes associated to a specific HandlerPath
     * @param handlerPath A String representing the HandlerPath we want the associated ImageType of
     * @return The ImageType associated to the provided HandlerPath
     */
    public static ImageTypes fromHandlerPathToImageTypes (String handlerPath) {
        ImageTypes imageType = null;
        switch (handlerPath) {

            // CharacterCards
            case "it/polimi/ingsw/images/characterCards/0_MONK.jpg"           -> imageType = ImageTypes.MONK_IMG;
            case "it/polimi/ingsw/images/characterCards/1_FARMER.jpg"         -> imageType = ImageTypes.FARMER_IMG;
            case "it/polimi/ingsw/images/characterCards/2_STANDARDBEARER.jpg" -> imageType = ImageTypes.STBEARER_IMG;
            case "it/polimi/ingsw/images/characterCards/3_MAGICIAN.jpg"       -> imageType = ImageTypes.MAGICIAN_IMG;
            case "it/polimi/ingsw/images/characterCards/4_HERBALIST.jpg"      -> imageType = ImageTypes.HERBALIST_IMG;
            case "it/polimi/ingsw/images/characterCards/5_CENTAUR.jpg"        -> imageType = ImageTypes.CENTAUR_IMG;
            case "it/polimi/ingsw/images/characterCards/6_JESTER.jpg"         -> imageType = ImageTypes.JESTER_IMG;
            case "it/polimi/ingsw/images/characterCards/7_CAVALIER.jpg"       -> imageType = ImageTypes.CAVALIER_IMG;
            case "it/polimi/ingsw/images/characterCards/8_MERCHANT.jpg"       -> imageType = ImageTypes.MERCHANT_IMG;
            case "it/polimi/ingsw/images/characterCards/9_BARD.jpg"           -> imageType = ImageTypes.BARD_IMG;
            case "it/polimi/ingsw/images/characterCards/10_PRINCESS.jpg"      -> imageType = ImageTypes.PRINCESS_IMG;
            case "it/polimi/ingsw/images/characterCards/11_THIEF.jpg"         -> imageType = ImageTypes.THIEF_IMG;


            // AssistantCards
            case "it/polimi/ingsw/images/assistantCards/assistantCard1.png"  -> imageType = ImageTypes.ASS_1_IMG;
            case "it/polimi/ingsw/images/assistantCards/assistantCard2.png"  -> imageType = ImageTypes.ASS_2_IMG;
            case "it/polimi/ingsw/images/assistantCards/assistantCard3.png"  -> imageType = ImageTypes.ASS_3_IMG;
            case "it/polimi/ingsw/images/assistantCards/assistantCard4.png"  -> imageType = ImageTypes.ASS_4_IMG;
            case "it/polimi/ingsw/images/assistantCards/assistantCard5.png"  -> imageType = ImageTypes.ASS_5_IMG;
            case "it/polimi/ingsw/images/assistantCards/assistantCard6.png"  -> imageType = ImageTypes.ASS_6_IMG;
            case "it/polimi/ingsw/images/assistantCards/assistantCard7.png"  -> imageType = ImageTypes.ASS_7_IMG;
            case "it/polimi/ingsw/images/assistantCards/assistantCard8.png"  -> imageType = ImageTypes.ASS_8_IMG;
            case "it/polimi/ingsw/images/assistantCards/assistantCard9.png"  -> imageType = ImageTypes.ASS_9_IMG;
            case "it/polimi/ingsw/images/assistantCards/assistantCard10.png" -> imageType = ImageTypes.ASS_10_IMG;


            // Students
            case "it/polimi/ingsw/images/students/student_green.png"  -> imageType = ImageTypes.STUDENT_GREEN_IMG;
            case "it/polimi/ingsw/images/students/student_red.png"    -> imageType = ImageTypes.STUDENT_RED_IMG;
            case "it/polimi/ingsw/images/students/student_yellow.png" -> imageType = ImageTypes.STUDENT_YELLOW_IMG;
            case "it/polimi/ingsw/images/students/student_pink.png"   -> imageType = ImageTypes.STUDENT_PINK_IMG;
            case "it/polimi/ingsw/images/students/student_blue.png"   -> imageType = ImageTypes.STUDENT_BLUE_IMG;


            // Towers
            case "it/polimi/ingsw/images/towers/black_tower.png" -> imageType = ImageTypes.TOWER_BLACK_IMG;
            case "it/polimi/ingsw/images/towers/grey_tower.png"  -> imageType = ImageTypes.TOWER_GREY_IMG;
            case "it/polimi/ingsw/images/towers/white_tower.png" -> imageType = ImageTypes.TOWER_WHITE_IMG;


            // Wizards
            case "it/polimi/ingsw/images/wizards/0_NATURE.jpg" -> imageType = ImageTypes.WIZARD_NATURE_IMG;
            case "it/polimi/ingsw/images/wizards/1_DESERT.jpg" -> imageType = ImageTypes.WIZARD_DESERT_IMG;
            case "it/polimi/ingsw/images/wizards/2_CLOUD.jpg"  -> imageType = ImageTypes.WIZARD_CLOUD_IMG;
            case "it/polimi/ingsw/images/wizards/3_SNOW.jpg"   -> imageType = ImageTypes.WIZARD_SNOW_IMG;



            // Islands
            case "it/polimi/ingsw/images/islands/island0.png" -> imageType = ImageTypes.ISLAND_0_IMG;
            case "it/polimi/ingsw/images/islands/island1.png" -> imageType = ImageTypes.ISLAND_1_IMG;
            case "it/polimi/ingsw/images/islands/island2.png" -> imageType = ImageTypes.ISLAND_2_IMG;



            // Professors
            case "it/polimi/ingsw/images/professors/professor_green.png"  -> imageType = ImageTypes.PROFESSOR_GREEN_IMG;
            case "it/polimi/ingsw/images/professors/professor_red.png"    -> imageType = ImageTypes.PROFESSOR_RED_IMG;
            case "it/polimi/ingsw/images/professors/professor_yellow.png" -> imageType = ImageTypes.PROFESSOR_YELLOW_IMG;
            case "it/polimi/ingsw/images/professors/professor_pink.png"   -> imageType = ImageTypes.PROFESSOR_PINK_IMG;
            case "it/polimi/ingsw/images/professors/professor_blue.png"   -> imageType = ImageTypes.PROFESSOR_BLUE_IMG;



            // Miscellaneous
            case "it/polimi/ingsw/images/cloudTile.png"     -> imageType = ImageTypes.CLOUD_TILE_IMG;
            case "it/polimi/ingsw/images/coin.png"          -> imageType = ImageTypes.COIN_IMG;
            case "it/polimi/ingsw/images/cranio.png"        -> imageType = ImageTypes.CRANIO_IMG;
            case "it/polimi/ingsw/images/mother_nature.png" -> imageType = ImageTypes.MOTHER_NATURE_IMG;
            case "it/polimi/ingsw/images/noEntryTile.png"   -> imageType = ImageTypes.NOENTRYTILE_IMG;
            case "it/polimi/ingsw/images/players_icon.png"  -> imageType = ImageTypes.PLAYERS_ICON_IMG;
            case "it/polimi/ingsw/images/polimi.png"        -> imageType = ImageTypes.POLIMI_LOGO_IMG;
            case "it/polimi/ingsw/images/return.png"        -> imageType = ImageTypes.RETURN_IMG;
            case "it/polimi/ingsw/images/school_board.png"  -> imageType = ImageTypes.SCHOOL_BOARD_IMG;
            case "it/polimi/ingsw/images/home_page.jpg"     -> imageType = ImageTypes.HOME_PAGE_IMG;
        }

        return imageType;
    }

    /**
     * Get the ImageType associated to a specific fxmlPath
     * @param fxmlPath A String representing the fxmlPath we want the associated ImageType of
     * @return The ImageType associated to the provided fxmlPath
     */
    public static ImageTypes fromFXMLPathToImageTypes (String fxmlPath) { return fromHandlerPathToImageTypes(fromFXMLPathToHandlerPath(fxmlPath)); }

    // endregion ImageTypesTranslator

    // region StudentColorTranslator

    /**
     * Gets the ImageTypes associated to a specific StudentColor
     * @param color A Color representing the StudentColor we want the associated ImageType of
     * @return The ImageType associated to the provided StudentColor
     */
    public static ImageTypes fromStudentColorToImageTypes (Color color) {
        ImageTypes imageTypes = null;
        switch (color){
            case GREEN  -> imageTypes = ImageTypes.STUDENT_GREEN_IMG;
            case RED    -> imageTypes = ImageTypes.STUDENT_RED_IMG;
            case YELLOW -> imageTypes = ImageTypes.STUDENT_YELLOW_IMG;
            case PINK   -> imageTypes = ImageTypes.STUDENT_PINK_IMG;
            case BLUE   -> imageTypes = ImageTypes.STUDENT_BLUE_IMG;
        }

        return imageTypes;
    }

    public static String fromStudentColorToHandlerPath (Color color) { return fromImageTypesToHandlerPath(fromStudentColorToImageTypes(color)); }

    public static String fromStudentColorToFXMLPath (Color color) { return fromImageTypesToFXMLPath(fromStudentColorToImageTypes(color)); }

    /**
     * Gets the StudentColor associated to a specific ImageType
     * @param imageType The ImageType we want the associated StudentColor of
     * @return A Color representing the StudentColor associated to the provided ImageType
     */
    public static Color fromImageTypesToStudentColor (ImageTypes imageType) {
        Color studentColor = null;
        switch (imageType) {
            case STUDENT_GREEN_IMG -> studentColor = Color.GREEN;
            case STUDENT_RED_IMG -> studentColor = Color.RED;
            case STUDENT_YELLOW_IMG  -> studentColor = Color.YELLOW;
            case STUDENT_PINK_IMG  -> studentColor = Color.PINK;
            case STUDENT_BLUE_IMG  -> studentColor = Color.BLUE;
            default              -> throw new IllegalStateException("In fromImageTypesToStudentColor can be passed only imageType referring to studentColors!");
        }

        return studentColor;
    }

    public static Color fromHandlerPathToStudentColor (String handlerPath) { return fromImageTypesToStudentColor(fromHandlerPathToImageTypes(handlerPath)); }

    public static Color fromFXMLPathToStudentColor (String fxmlPath) { return fromImageTypesToStudentColor(fromFXMLPathToImageTypes(fxmlPath)); }

    // endregion StudentColorTranslator

    // region ProfessorColorTranslator

    /**
     * Gets the ImageTypes associated to a specific ProfessorColor
     * @param color A Color representing the ProfessorColor we want the associated ImageType of
     * @return The ImageType associated to the provided ProfessorColor
     */
    public static ImageTypes fromProfessorColorToImageTypes (Color color) {
        ImageTypes imageType = null;
        switch (color){
            case GREEN  -> imageType = ImageTypes.PROFESSOR_GREEN_IMG;
            case RED    -> imageType = ImageTypes.PROFESSOR_RED_IMG;
            case YELLOW -> imageType = ImageTypes.PROFESSOR_YELLOW_IMG;
            case PINK   -> imageType = ImageTypes.PROFESSOR_PINK_IMG;
            case BLUE   -> imageType = ImageTypes.PROFESSOR_BLUE_IMG;
        }

        return imageType;
    }

    public static String fromProfessorColorToHandlerPath (Color color) { return fromImageTypesToHandlerPath(fromProfessorColorToImageTypes(color)); }

    public static String fromProfessorColorToFXMLPath (Color color) { return fromImageTypesToFXMLPath(fromProfessorColorToImageTypes(color)); }

    /**
     * Gets the ProfessorColor associated to a specific ImageType
     * @param imageType The ImageType we want the associated ProfessorColor of
     * @return A Color representing the ProfessorColor associated to the provided ImageType
     */
    public static Color fromImageTypesToProfessorColor (ImageTypes imageType) {
        Color professorColor = null;
        switch (imageType) {
            case PROFESSOR_GREEN_IMG  -> professorColor = Color.GREEN;
            case PROFESSOR_RED_IMG    -> professorColor = Color.RED;
            case PROFESSOR_YELLOW_IMG -> professorColor = Color.YELLOW;
            case PROFESSOR_PINK_IMG   -> professorColor = Color.PINK;
            case PROFESSOR_BLUE_IMG   -> professorColor = Color.BLUE;
            default              -> throw new IllegalStateException("In fromImageTypesToProfessorColor can be passed only imageType referring to professorColors!");
        }

        return professorColor;
    }

    public static Color fromHandlerPathToProfessorColor (String handlerPath) { return fromImageTypesToProfessorColor(fromHandlerPathToImageTypes(handlerPath)); }

    public static Color fromFXMLPathToProfessorColor (String fxmlPath) { return fromImageTypesToProfessorColor(fromFXMLPathToImageTypes(fxmlPath)); }

    // endregion ProfessorColorTranslator

    // region TowerColorTranslator

    /**
     * Gets the ImageTypes associated to a specific TowerColor
     * @param towerColor A TowerColor representing the TowerColor we want the associated ImageType of
     * @return The ImageType associated to the provided TowerColor
     */
    public static ImageTypes fromTowerColorToImageTypes (TowerColor towerColor) {
        ImageTypes imageType = null;
        switch (towerColor){
            case WHITE -> imageType = ImageTypes.TOWER_WHITE_IMG;
            case BLACK  -> imageType = ImageTypes.TOWER_BLACK_IMG;
            case GREY   -> imageType = ImageTypes.TOWER_GREY_IMG;
        }

        return imageType;
    }

    public static String fromTowerColorToHandlerPath (TowerColor towerColor) { return fromImageTypesToHandlerPath(fromTowerColorToImageTypes(towerColor)); }

    public static String fromTowerColorToFXMLPath (TowerColor towerColor) { return fromImageTypesToFXMLPath(fromTowerColorToImageTypes(towerColor)); }

    /**
     * Gets the TowerColor associated to a specific ImageType
     * @param imageType The ImageType we want the associated TowerColor of
     * @return A TowerColor representing the TowerColor associated to the provided ImageType
     */
    public static TowerColor fromImageTypesToTowerColor (ImageTypes imageType) {
        TowerColor towerColor = null;
        switch (imageType) {
            case TOWER_WHITE_IMG -> towerColor = TowerColor.WHITE;
            case TOWER_BLACK_IMG -> towerColor = TowerColor.BLACK;
            case TOWER_GREY_IMG  -> towerColor = TowerColor.GREY;
            default              -> throw new IllegalStateException("In fromImageTypesToTowerColor can be passed only imageType referring to towerColors!");
        }

        return towerColor;
    }

    public static TowerColor fromHandlerPathToTowerColor (String handlerPath) { return fromImageTypesToTowerColor(fromHandlerPathToImageTypes(handlerPath)); }

    public static TowerColor fromFXMLPathToTowerColor (String fxmlPath) { return fromImageTypesToTowerColor(fromFXMLPathToImageTypes(fxmlPath)); }

    // endregion TowerColorTranslator

    // region AssistantCardTranslator

    /**
     * Gets the ImageTypes associated to a specific AssistantCard
     * @param assistantCardNumber An int representing the cardValue of the AssistantCard we want the associated ImageType of
     * @return The ImageType associated to the provided AssistantCard
     */
    public static ImageTypes fromAssistantCardNumberToImageTypes (int assistantCardNumber) {
        ImageTypes imageType = null;
        switch (assistantCardNumber) {
            case 1  -> imageType = ImageTypes.ASS_1_IMG;
            case 2  -> imageType = ImageTypes.ASS_2_IMG;
            case 3  -> imageType = ImageTypes.ASS_3_IMG;
            case 4  -> imageType = ImageTypes.ASS_4_IMG;
            case 5  -> imageType = ImageTypes.ASS_5_IMG;
            case 6  -> imageType = ImageTypes.ASS_6_IMG;
            case 7  -> imageType = ImageTypes.ASS_7_IMG;
            case 8  -> imageType = ImageTypes.ASS_8_IMG;
            case 9  -> imageType = ImageTypes.ASS_9_IMG;
            case 10 -> imageType = ImageTypes.ASS_10_IMG;
            default -> throw new IllegalStateException("The AssistantCardNumber can be a number only between 1 and 10");
        }

        return imageType;
    }

    public static String fromAssistantCardNumberToHandlerPath (int assistantCardNumber) { return fromImageTypesToHandlerPath(fromAssistantCardNumberToImageTypes(assistantCardNumber)); }

    public static String fromAssistantCardNumberToFXMLPath (int assistantCardNumber) { return fromImageTypesToFXMLPath(fromAssistantCardNumberToImageTypes(assistantCardNumber)); }

    /**
     * Gets the number of the AssistantCard associated to a specific ImageType
     * @param imageType The ImageType we want the associated AssistantCard of
     * @return An int representing the number of the AssistantCard associated to the provided ImageType
     */
    public static int fromImageTypesToAssistantCardNumber (ImageTypes imageType) {
        int assistantCardNumber;
        switch (imageType) {
            case ASS_1_IMG  -> assistantCardNumber = 1;
            case ASS_2_IMG  -> assistantCardNumber = 2;
            case ASS_3_IMG  -> assistantCardNumber = 3;
            case ASS_4_IMG  -> assistantCardNumber = 4;
            case ASS_5_IMG  -> assistantCardNumber = 5;
            case ASS_6_IMG  -> assistantCardNumber = 6;
            case ASS_7_IMG  -> assistantCardNumber = 7;
            case ASS_8_IMG  -> assistantCardNumber = 8;
            case ASS_9_IMG  -> assistantCardNumber = 9;
            case ASS_10_IMG -> assistantCardNumber = 10;
            default         -> throw new IllegalStateException("Can be passed as parameter only and ImageType corresponding to an assistantCardNumber");
        }

        return assistantCardNumber;
    }

    public static int fromHandlerPathToAssistantCardNumber (String handlerPath) { return fromImageTypesToAssistantCardNumber(fromHandlerPathToImageTypes(handlerPath)); }

    public static int fromFXMLPathToAssistantCardNumber (String fxmlPath) { return fromImageTypesToAssistantCardNumber(fromFXMLPathToImageTypes(fxmlPath)); }

    // endregion AssistantCardTranslator

    // region CharacterEnumTranslator

    /**
     * Gets the ImageTypes associated to a specific CharacterCard
     * @param character The character Enumeration constant of the CharacterCard we want the associated ImageType of
     * @return The ImageType associated to the provided CharacterCard
     */
    public static ImageTypes fromCharacterEnumToImageTypes (Character character) {
        ImageTypes imageType = null;
        switch (character) {
            case MONK            -> imageType = ImageTypes.MONK_IMG;
            case FARMER          -> imageType = ImageTypes.FARMER_IMG;
            case STANDARD_BEARER -> imageType = ImageTypes.STBEARER_IMG;
            case MAGICIAN        -> imageType = ImageTypes.MAGICIAN_IMG;
            case HERBALIST       -> imageType = ImageTypes.HERBALIST_IMG;
            case CENTAUR         -> imageType = ImageTypes.CENTAUR_IMG;
            case JESTER          -> imageType = ImageTypes.JESTER_IMG;
            case CAVALIER        -> imageType = ImageTypes.CAVALIER_IMG;
            case MERCHANT        -> imageType = ImageTypes.MERCHANT_IMG;
            case BARD            -> imageType = ImageTypes.BARD_IMG;
            case PRINCESS        -> imageType = ImageTypes.PRINCESS_IMG;
            case THIEF           -> imageType = ImageTypes.THIEF_IMG;
            default              -> throw new IllegalStateException("The CharacterEnum can be a number only an enumeration constant of Enum 'Character'");
        }

        return imageType;
    }

    public static String fromCharacterEnumToHandlerPath (Character character) { return fromImageTypesToHandlerPath(fromCharacterEnumToImageTypes(character)); }

    public static String fromCharacterEnumToFXMLPath (Character character) { return fromImageTypesToFXMLPath(fromCharacterEnumToImageTypes(character)); }

    /**
     * Gets the Character of the CharacterCard associated to a specific ImageType
     * @param imageType The ImageType we want the associated CharacterCard of
     * @return The Character of the CharacterCard associated to the provided ImageType
     */
    public static Character fromImageTypesToCharacterEnum (ImageTypes imageType) {
        Character character;
        switch (imageType) {
            case MONK_IMG      -> character = Character.MONK;
            case FARMER_IMG    -> character = Character.FARMER;
            case STBEARER_IMG  -> character = Character.STANDARD_BEARER;
            case MAGICIAN_IMG  -> character = Character.MAGICIAN;
            case HERBALIST_IMG -> character = Character.HERBALIST;
            case CENTAUR_IMG   -> character = Character.CENTAUR;
            case JESTER_IMG    -> character = Character.JESTER;
            case CAVALIER_IMG  -> character = Character.CAVALIER;
            case MERCHANT_IMG  -> character = Character.MERCHANT;
            case BARD_IMG      -> character = Character.BARD;
            case PRINCESS_IMG  -> character = Character.PRINCESS;
            case THIEF_IMG     -> character = Character.THIEF;
            default            -> throw new IllegalStateException("Can be passed as parameter only and ImageType corresponding to a character");
        }

        return character;
    }

    public static Character fromHandlerPathToCharacterEnum (String handlerPath) { return fromImageTypesToCharacterEnum(fromHandlerPathToImageTypes(handlerPath)); }

    public static Character fromFXMLPathToCharacterEnum (String fxmlPath) { return fromImageTypesToCharacterEnum(fromFXMLPathToImageTypes(fxmlPath)); }

    // endregion CharacterEnumTranslator

    // region WizardEnumTranslator

    /**
     * Gets the ImageTypes associated to a specific Wizard
     * @param wizard The Wizard we want the associated ImageType of
     * @return The ImageType associated to the provided Wizard
     */
    public static ImageTypes fromWizardEnumToImageTypes (Wizard wizard) {
        ImageTypes imageType = null;
        switch (wizard) {
            case NATURE -> imageType = ImageTypes.WIZARD_NATURE_IMG;
            case DESERT -> imageType = ImageTypes.WIZARD_DESERT_IMG;
            case CLOUD  -> imageType = ImageTypes.WIZARD_CLOUD_IMG;
            case SNOW   -> imageType = ImageTypes.WIZARD_SNOW_IMG;
            default     -> throw new IllegalStateException("The WizardEnum can be a number only an enumeration constant of Enum 'Wizard'");
        }

        return imageType;
    }

    public static String fromWizardEnumToHandlerPath (Wizard wizard) { return fromImageTypesToHandlerPath(fromWizardEnumToImageTypes(wizard)); }

    public static String fromWizardEnumToFXMLPath (Wizard wizard) { return fromImageTypesToFXMLPath(fromWizardEnumToImageTypes(wizard)); }

    /**
     * Gets the Wizard associated to a specific ImageType
     * @param imageType The ImageType we want the associated Wizard of
     * @return The Wizard associated to the provided ImageType
     */
    public static Wizard fromImageTypesToWizardEnum (ImageTypes imageType) {
        Wizard wizard;
        switch (imageType) {
            case WIZARD_NATURE_IMG -> wizard = Wizard.NATURE;
            case WIZARD_DESERT_IMG -> wizard = Wizard.DESERT;
            case WIZARD_CLOUD_IMG  -> wizard = Wizard.CLOUD;
            case WIZARD_SNOW_IMG   -> wizard = Wizard.SNOW;
            default                -> throw new IllegalStateException("Can be passed as parameter only and ImageType corresponding to a wizard");
        }

        return wizard;
    }

    public static Wizard fromHandlerPathToWizardEnum (String handlerPath) { return fromImageTypesToWizardEnum(fromHandlerPathToImageTypes(handlerPath)); }

    public static Wizard fromFXMLPathToWizardEnum (String fxmlPath) { return fromImageTypesToWizardEnum(fromFXMLPathToImageTypes(fxmlPath)); }

    // endregion WizardEnumTranslator

    // region IslandBackgroundTranslator

    /**
     * Gets the ImageTypes associated to a specific IslandBackGroundNumber
     * @param islandIDNumber The IslandBackGroundNumber we want the associated ImageType of
     * @return The ImageType associated to the provided IslandBackGroundNumber
     */
    public static ImageTypes fromIslandBackGroundNumberToImageTypes (int islandIDNumber) {
        ImageTypes imageType = null;
        switch (islandIDNumber) {
            case 0  -> imageType = ImageTypes.ISLAND_0_IMG;
            case 1  -> imageType = ImageTypes.ISLAND_1_IMG;
            case 2  -> imageType = ImageTypes.ISLAND_2_IMG;
            default -> throw new IllegalStateException("The IslandIDNumber can be a number only an number between 0 and 2");
        }

        return imageType;
    }

    public static String fromIslandBackGroundNumberToHandlerPath (int islandBackGroundNumber) { return fromImageTypesToHandlerPath(fromIslandBackGroundNumberToImageTypes(islandBackGroundNumber)); }

    public static String fromIslandBackGroundNumberToFXMLPath (int islandBackGroundNumber) { return fromImageTypesToFXMLPath(fromIslandBackGroundNumberToImageTypes(islandBackGroundNumber)); }

    /**
     * Gets the IslandBackGroundNumber associated to a specific ImageType
     * @param imageType The ImageType we want the associated IslandBackGroundNumber of
     * @return An int representing the IslandBackGroundNumber associated to the provided ImageType
     */
    public static int fromImageTypesToIslandBackGroundNumber (ImageTypes imageType) {
        int islandBackGroundNumber;
        switch (imageType) {
            case ISLAND_0_IMG -> islandBackGroundNumber = 0;
            case ISLAND_1_IMG -> islandBackGroundNumber = 1;
            case ISLAND_2_IMG -> islandBackGroundNumber = 2;
            default           -> throw new IllegalStateException("Can be passed as parameter only and ImageType corresponding to a IslandBackground");
        }

        return islandBackGroundNumber;
    }

    public static int fromHandlerPathToIslandBackGroundNumber (String handlerPath) { return fromImageTypesToIslandBackGroundNumber(fromHandlerPathToImageTypes(handlerPath)); }

    public static int fromFXMLPathToIslandBackGroundNumber (String fxmlPath) { return fromImageTypesToIslandBackGroundNumber(fromFXMLPathToImageTypes(fxmlPath)); }

    // endregion IslandBackgroundTranslator

    // endregion Translators/Switches

}
