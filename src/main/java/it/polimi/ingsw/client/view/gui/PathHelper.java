package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;

import java.lang.String;
import java.util.Objects;

// TODO - JavaDocs (?)

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
        return switch(handlerPath) {

            // CharacterCards
            case "/it/polimi/ingsw/images/characterCards/0_MONK.jpg"           -> "@../images/characterCards/0_MONK.jpg";
            case "/it/polimi/ingsw/images/characterCards/1_FARMER.jpg"         -> "@../images/characterCards/1_FARMER.jpg";
            case "/it/polimi/ingsw/images/characterCards/2_STANDARDBEARER.jpg" -> "@../images/characterCards/2_STANDARDBEARER.jpg";
            case "/it/polimi/ingsw/images/characterCards/3_MAGICIAN.jpg"       -> "@../images/characterCards/3_MAGICIAN.jpg";
            case "/it/polimi/ingsw/images/characterCards/4_HERBALIST.jpg"      -> "@../images/characterCards/4_HERBALIST.jpg";
            case "/it/polimi/ingsw/images/characterCards/5_CENTAUR.jpg"        -> "@../images/characterCards/5_CENTAUR.jpg";
            case "/it/polimi/ingsw/images/characterCards/6_JESTER.jpg"         -> "@../images/characterCards/6_JESTER.jpg";
            case "/it/polimi/ingsw/images/characterCards/7_CAVALIER.jpg"       -> "@../images/characterCards/7_CAVALIER.jpg";
            case "/it/polimi/ingsw/images/characterCards/8_MERCHANT.jpg"       -> "@../images/characterCards/8_MERCHANT.jpg";
            case "/it/polimi/ingsw/images/characterCards/9_BARD.jpg"           -> "@../images/characterCards/9_BARD.jpg";
            case "/it/polimi/ingsw/images/characterCards/10_PRINCESS.jpg"      -> "@../images/characterCards/10_PRINCESS.jpg";
            case "/it/polimi/ingsw/images/characterCards/11_THIEF.jpg"         -> "@../images/characterCards/11_THIEF.jpg";

            // AssistantCards
            case "/it/polimi/ingsw/images/assistantCards/assistantCard1.png"  -> "@../images/assistantCards/assistantCard1.png";
            case "/it/polimi/ingsw/images/assistantCards/assistantCard2.png"  -> "@../images/assistantCards/assistantCard2.png";
            case "/it/polimi/ingsw/images/assistantCards/assistantCard3.png"  -> "@../images/assistantCards/assistantCard3.png";
            case "/it/polimi/ingsw/images/assistantCards/assistantCard4.png"  -> "@../images/assistantCards/assistantCard4.png";
            case "/it/polimi/ingsw/images/assistantCards/assistantCard5.png"  -> "@../images/assistantCards/assistantCard5.png";
            case "/it/polimi/ingsw/images/assistantCards/assistantCard6.png"  -> "@../images/assistantCards/assistantCard6.png";
            case "/it/polimi/ingsw/images/assistantCards/assistantCard7.png"  -> "@../images/assistantCards/assistantCard7.png";
            case "/it/polimi/ingsw/images/assistantCards/assistantCard8.png"  -> "@../images/assistantCards/assistantCard8.png";
            case "/it/polimi/ingsw/images/assistantCards/assistantCard9.png"  -> "@../images/assistantCards/assistantCard9.png";
            case "/it/polimi/ingsw/images/assistantCards/assistantCard10.png" -> "@../images/assistantCards/assistantCard10.png";

            // Students
            case "/it/polimi/ingsw/images/students/student_red.png"    -> "@../images/students/student_red.png";
            case "/it/polimi/ingsw/images/students/student_green.png"  -> "@../images/students/student_green.png";
            case "/it/polimi/ingsw/images/students/student_yellow.png" -> "@../images/students/student_yellow.png";
            case "/it/polimi/ingsw/images/students/student_pink.png"   -> "@../images/students/student_pink.png";
            case "/it/polimi/ingsw/images/students/student_blue.png"   -> "@../images/students/student_blue.png";

            // Towers
            case "/it/polimi/ingsw/images/towers/black_tower.png"  -> "@../images/towers/black_tower.png";
            case "/it/polimi/ingsw/images/towers/grey_tower.png"   -> "@../images/towers/grey_tower.png";
            case "/it/polimi/ingsw/images/towers/white_tower.png"  -> "@../images/towers/white_tower.png";

            // Wizards
            case "/it/polimi/ingsw/images/wizards/0_NATURE.jpg" -> "@../images/wizards/0_NATURE.jpg";
            case "/it/polimi/ingsw/images/wizards/1_DESERT.jpg" -> "@../images/wizards/1_DESERT.jpg";
            case "/it/polimi/ingsw/images/wizards/2_CLOUD.jpg"  -> "@../images/wizards/2_CLOUD.jpg";
            case "/it/polimi/ingsw/images/wizards/3_SNOW.jpg"   -> "@../images/wizards/3_SNOW.jpg";

            // Islands
            case "/it/polimi/ingsw/images/islands/island0.png" -> "@../images/islands/island0.png";
            case "/it/polimi/ingsw/images/islands/island1.png" -> "@../images/islands/island1.png";
            case "/it/polimi/ingsw/images/islands/island2.png" -> "@../images/islands/island2.png";

            // Professors
            case "/it/polimi/ingsw/images/professors/professor_green.png"  -> "@../images/professors/professor_green.png";
            case "/it/polimi/ingsw/images/professors/professor_red.png"    -> "@../images/professors/professor_red.png";
            case "/it/polimi/ingsw/images/professors/professor_yellow.png" -> "@../images/professors/professor_yellow.png";
            case "/it/polimi/ingsw/images/professors/professor_pink.png"   -> "@../images/professors/professor_pink.png";
            case "/it/polimi/ingsw/images/professors/professor_blue.png"   -> "@../images/professors/professor_blue.png";

            // Miscellaneous
            case "/it/polimi/ingsw/images/cloudTile.png"     -> "@../images/cloudTile.png";
            case "/it/polimi/ingsw/images/coin.png"          -> "@../images/coin.png";
            case "/it/polimi/ingsw/images/cranio.png"        -> "@../images/cranio.png";
            case "/it/polimi/ingsw/images/mother_nature.png" -> "@../images/mother_nature.png";
            case "/it/polimi/ingsw/images/noEntryTile.png"   -> "@../images/noEntryTile.png";
            case "/it/polimi/ingsw/images/players_icon.png"  -> "@../images/players_icon.png";
            case "/it/polimi/ingsw/images/polimi.png"        -> "@../images/polimi.png";
            case "/it/polimi/ingsw/images/return.png"        -> "@../images/return.png";
            case "/it/polimi/ingsw/images/school_board.png"  -> "@../images/school_board.png";
            case "/it/polimi/ingsw/images/home_page.jpg"     -> "@../images/home_page.jpg";

            default -> throw new IllegalStateException();
        };
    }

    /**
     * Gets the HandlerPath  from the FXMlPath
     * @param fxmlPath The fxmlPath we want to translate into a HandlerPath
     * @return A String representing the HandlerPath correspondent to the fxmlPath
     */
    public static String fromFXMLPathToHandlerPath (String fxmlPath) {
        fxmlPath = fromAbsoluteURLToRelativeURL(fxmlPath);
        return switch(fxmlPath) {

            // CharacterCards
            case "@../images/characterCards/0_MONK.jpg"           -> "/it/polimi/ingsw/images/characterCards/0_MONK.jpg";
            case "@../images/characterCards/1_FARMER.jpg"         -> "/it/polimi/ingsw/images/characterCards/1_FARMER.jpg";
            case "@../images/characterCards/2_STANDARDBEARER.jpg" -> "/it/polimi/ingsw/images/characterCards/2_STANDARDBEARER.jpg";
            case "@../images/characterCards/3_MAGICIAN.jpg"       -> "/it/polimi/ingsw/images/characterCards/3_MAGICIAN.jpg";
            case "@../images/characterCards/4_HERBALIST.jpg"      -> "/it/polimi/ingsw/images/characterCards/4_HERBALIST.jpg";
            case "@../images/characterCards/5_CENTAUR.jpg"        -> "/it/polimi/ingsw/images/characterCards/5_CENTAUR.jpg";
            case "@../images/characterCards/6_JESTER.jpg"         -> "/it/polimi/ingsw/images/characterCards/6_JESTER.jpg";
            case "@../images/characterCards/7_CAVALIER.jpg"       -> "/it/polimi/ingsw/images/characterCards/7_CAVALIER.jpg";
            case "@../images/characterCards/8_MERCHANT.jpg"       -> "/it/polimi/ingsw/images/characterCards/8_MERCHANT.jpg";
            case "@../images/characterCards/9_BARD.jpg"           -> "/it/polimi/ingsw/images/characterCards/9_BARD.jpg";
            case "@../images/characterCards/10_PRINCESS.jpg"      -> "/it/polimi/ingsw/images/characterCards/10_PRINCESS.jpg";
            case "@../images/characterCards/11_THIEF.jpg"         -> "/it/polimi/ingsw/images/characterCards/11_THIEF.jpg";

            // AssistantCards
            case "@../images/assistantCards/assistantCard1.png"  -> "/it/polimi/ingsw/images/assistantCards/assistantCard1.png";
            case "@../images/assistantCards/assistantCard2.png"  -> "/it/polimi/ingsw/images/assistantCards/assistantCard2.png";
            case "@../images/assistantCards/assistantCard3.png"  -> "/it/polimi/ingsw/images/assistantCards/assistantCard3.png";
            case "@../images/assistantCards/assistantCard4.png"  -> "/it/polimi/ingsw/images/assistantCards/assistantCard4.png";
            case "@../images/assistantCards/assistantCard5.png"  -> "/it/polimi/ingsw/images/assistantCards/assistantCard5.png";
            case "@../images/assistantCards/assistantCard6.png"  -> "/it/polimi/ingsw/images/assistantCards/assistantCard6.png";
            case "@../images/assistantCards/assistantCard7.png"  -> "/it/polimi/ingsw/images/assistantCards/assistantCard7.png";
            case "@../images/assistantCards/assistantCard8.png"  -> "/it/polimi/ingsw/images/assistantCards/assistantCard8.png";
            case "@../images/assistantCards/assistantCard9.png"  -> "/it/polimi/ingsw/images/assistantCards/assistantCard9.png";
            case "@../images/assistantCards/assistantCard10.png" -> "/it/polimi/ingsw/images/assistantCards/assistantCard10.png";

            // Students
            case "@../images/students/student_red.png"    -> "/it/polimi/ingsw/images/students/student_red.png";
            case "@../images/students/student_green.png"  -> "/it/polimi/ingsw/images/students/student_green.png";
            case "@../images/students/student_yellow.png" -> "/it/polimi/ingsw/images/students/student_yellow.png";
            case "@../images/students/student_pink.png"   -> "/it/polimi/ingsw/images/students/student_pink.png";
            case "@../images/students/student_blue.png"   -> "/it/polimi/ingsw/images/students/student_blue.png";

            // Towers
            case "@../images/towers/black_tower.png" -> "/it/polimi/ingsw/images/towers/black_tower.png";
            case "@../images/towers/grey_tower.png"  -> "/it/polimi/ingsw/images/towers/grey_tower.png";
            case "@../images/towers/white_tower.png" -> "/it/polimi/ingsw/images/towers/white_tower.png";

            // Wizards
            case "@../images/wizards/0_NATURE.jpg" -> "/it/polimi/ingsw/images/wizards/0_NATURE.jpg";
            case "@../images/wizards/1_DESERT.jpg" -> "/it/polimi/ingsw/images/wizards/1_DESERT.jpg";
            case "@../images/wizards/2_CLOUD.jpg"  -> "/it/polimi/ingsw/images/wizards/2_CLOUD.jpg";
            case "@../images/wizards/3_SNOW.jpg"   -> "/it/polimi/ingsw/images/wizards/3_SNOW.jpg";

            // Islands
            case "@../images/islands/island0.png" -> "/it/polimi/ingsw/images/islands/island0.png";
            case "@../images/islands/island1.png" -> "/it/polimi/ingsw/images/islands/island1.png";
            case "@../images/islands/island2.png" -> "/it/polimi/ingsw/images/islands/island2.png";

            // Professors
            case "@../images/professors/professor_green.png"  -> "/it/polimi/ingsw/images/professors/professor_green.png";
            case "@../images/professors/professor_red.png"    -> "/it/polimi/ingsw/images/professors/professor_red.png";
            case "@../images/professors/professor_yellow.png" -> "/it/polimi/ingsw/images/professors/professor_yellow.png";
            case "@../images/professors/professor_pink.png"   -> "/it/polimi/ingsw/images/professors/professor_pink.png";
            case "@../images/professors/professor_blue.png"   -> "/it/polimi/ingsw/images/professors/professor_blue.png";

            // Miscellaneous
            case "@../images/cloudTile.png"     -> "/it/polimi/ingsw/images/cloudTile.png";
            case "@../images/coin.png"          -> "/it/polimi/ingsw/images/coin.png";
            case "@../images/cranio.png"        -> "/it/polimi/ingsw/images/cranio.png";
            case "@../images/mother_nature.png" -> "/it/polimi/ingsw/images/mother_nature.png";
            case "@../images/noEntryTile.png"   -> "/it/polimi/ingsw/images/noEntryTile.png";
            case "@../images/players_icon.png"  -> "/it/polimi/ingsw/images/players_icon.png";
            case "@../images/polimi.png"        -> "/it/polimi/ingsw/images/polimi.png";
            case "@../images/return.png"        -> "/it/polimi/ingsw/images/return.png";
            case "@../images/school_board.png"  -> "/it/polimi/ingsw/images/school_board.png";
            case "@../images/home_page.jpg"     -> "/it/polimi/ingsw/images/home_page.jpg";

            default -> throw new IllegalStateException();
        };
    }

    // endregion FXML/Handler Translator

    // region ImageTypesTranslator
    /**
     * Gets the HandlerPath associated to a specific ImageType enum constant
     * @param imageType An enumeration constant of ImageType we want the associated HandlerPath of
     * @return A string representing the HandlerPath associated to the imageType
     */
    public static String fromImageTypesToHandlerPath (ImageTypes imageType) {
        return switch (imageType) {

            // CharacterCards
            case MONK_IMG      -> "/it/polimi/ingsw/images/characterCards/0_MONK.jpg";
            case FARMER_IMG    -> "/it/polimi/ingsw/images/characterCards/1_FARMER.jpg";
            case STBEARER_IMG  -> "/it/polimi/ingsw/images/characterCards/2_STANDARDBEARER.jpg";
            case MAGICIAN_IMG  -> "/it/polimi/ingsw/images/characterCards/3_MAGICIAN.jpg";
            case HERBALIST_IMG -> "/it/polimi/ingsw/images/characterCards/4_HERBALIST.jpg";
            case CENTAUR_IMG   -> "/it/polimi/ingsw/images/characterCards/5_CENTAUR.jpg";
            case JESTER_IMG    -> "/it/polimi/ingsw/images/characterCards/6_JESTER.jpg";
            case CAVALIER_IMG  -> "/it/polimi/ingsw/images/characterCards/7_CAVALIER.jpg";
            case MERCHANT_IMG  -> "/it/polimi/ingsw/images/characterCards/8_MERCHANT.jpg";
            case BARD_IMG      -> "/it/polimi/ingsw/images/characterCards/9_BARD.jpg";
            case PRINCESS_IMG  -> "/it/polimi/ingsw/images/characterCards/10_PRINCESS.jpg";
            case THIEF_IMG     -> "/it/polimi/ingsw/images/characterCards/11_THIEF.jpg";

            // AssistantCards
            case ASS_1_IMG  -> "/it/polimi/ingsw/images/assistantCards/assistantCard1.png";
            case ASS_2_IMG  -> "/it/polimi/ingsw/images/assistantCards/assistantCard2.png";
            case ASS_3_IMG  -> "/it/polimi/ingsw/images/assistantCards/assistantCard3.png";
            case ASS_4_IMG  -> "/it/polimi/ingsw/images/assistantCards/assistantCard4.png";
            case ASS_5_IMG  -> "/it/polimi/ingsw/images/assistantCards/assistantCard5.png";
            case ASS_6_IMG  -> "/it/polimi/ingsw/images/assistantCards/assistantCard6.png";
            case ASS_7_IMG  -> "/it/polimi/ingsw/images/assistantCards/assistantCard7.png";
            case ASS_8_IMG  -> "/it/polimi/ingsw/images/assistantCards/assistantCard8.png";
            case ASS_9_IMG  -> "/it/polimi/ingsw/images/assistantCards/assistantCard9.png";
            case ASS_10_IMG -> "/it/polimi/ingsw/images/assistantCards/assistantCard10.png";

            // Students
            case STUDENT_GREEN_IMG  -> "/it/polimi/ingsw/images/students/student_red.png";
            case STUDENT_RED_IMG    -> "/it/polimi/ingsw/images/students/student_green.png";
            case STUDENT_YELLOW_IMG -> "/it/polimi/ingsw/images/students/student_yellow.png";
            case STUDENT_PINK_IMG   -> "/it/polimi/ingsw/images/students/student_pink.png";
            case STUDENT_BLUE_IMG   -> "/it/polimi/ingsw/images/students/student_blue.png";

            // Towers
            case TOWER_WHITE_IMG -> "/it/polimi/ingsw/images/towers/white_tower.png";
            case TOWER_BLACK_IMG -> "/it/polimi/ingsw/images/towers/black_tower.png";
            case TOWER_GREY_IMG  -> "/it/polimi/ingsw/images/towers/grey_tower.png";

            // Wizards
            case WIZARD_NATURE_IMG -> "/it/polimi/ingsw/images/wizards/0_NATURE.jpg";
            case WIZARD_DESERT_IMG -> "/it/polimi/ingsw/images/wizards/1_DESERT.jpg";
            case WIZARD_CLOUD_IMG  -> "/it/polimi/ingsw/images/wizards/2_CLOUD.jpg";
            case WIZARD_SNOW_IMG   -> "/it/polimi/ingsw/images/wizards/3_SNOW.jpg";

            // Islands
            case ISLAND_0_IMG -> "/it/polimi/ingsw/images/islands/island0.png";
            case ISLAND_1_IMG -> "/it/polimi/ingsw/images/islands/island1.png";
            case ISLAND_2_IMG -> "/it/polimi/ingsw/images/islands/island2.png";

            // Professors
            case PROFESSOR_GREEN_IMG  -> "/it/polimi/ingsw/images/professors/professor_green.png";
            case PROFESSOR_RED_IMG    -> "/it/polimi/ingsw/images/professors/professor_red.png";
            case PROFESSOR_YELLOW_IMG -> "/it/polimi/ingsw/images/professors/professor_yellow.png";
            case PROFESSOR_PINK_IMG   -> "/it/polimi/ingsw/images/professors/professor_pink.png";
            case PROFESSOR_BLUE_IMG   -> "/it/polimi/ingsw/images/professors/professor_blue.png";

            // Miscellaneous
            case CLOUD_TILE_IMG    -> "/it/polimi/ingsw/images/cloudTile.png";
            case COIN_IMG          -> "/it/polimi/ingsw/images/coin.png";
            case CRANIO_IMG        -> "/it/polimi/ingsw/images/cranio.png";
            case MOTHER_NATURE_IMG -> "/it/polimi/ingsw/images/mother_nature.png";
            case NOENTRYTILE_IMG   -> "/it/polimi/ingsw/images/noEntryTile.png";
            case PLAYERS_ICON_IMG  -> "/it/polimi/ingsw/images/players_icon.png";
            case POLIMI_LOGO_IMG   -> "/it/polimi/ingsw/images/polimi.png";
            case RETURN_IMG        -> "/it/polimi/ingsw/images/return.png";
            case SCHOOL_BOARD_IMG  -> "/it/polimi/ingsw/images/school_board.png";
            case HOME_PAGE_IMG     -> "/it/polimi/ingsw/images/home_page.jpg";
        };
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
        return switch (handlerPath) {

            // CharacterCards
            case "/it/polimi/ingsw/images/characterCards/0_MONK.jpg"           -> ImageTypes.MONK_IMG;
            case "/it/polimi/ingsw/images/characterCards/1_FARMER.jpg"         -> ImageTypes.FARMER_IMG;
            case "/it/polimi/ingsw/images/characterCards/2_STANDARDBEARER.jpg" -> ImageTypes.STBEARER_IMG;
            case "/it/polimi/ingsw/images/characterCards/3_MAGICIAN.jpg"       -> ImageTypes.MAGICIAN_IMG;
            case "/it/polimi/ingsw/images/characterCards/4_HERBALIST.jpg"      -> ImageTypes.HERBALIST_IMG;
            case "/it/polimi/ingsw/images/characterCards/5_CENTAUR.jpg"        -> ImageTypes.CENTAUR_IMG;
            case "/it/polimi/ingsw/images/characterCards/6_JESTER.jpg"         -> ImageTypes.JESTER_IMG;
            case "/it/polimi/ingsw/images/characterCards/7_CAVALIER.jpg"       -> ImageTypes.CAVALIER_IMG;
            case "/it/polimi/ingsw/images/characterCards/8_MERCHANT.jpg"       -> ImageTypes.MERCHANT_IMG;
            case "/it/polimi/ingsw/images/characterCards/9_BARD.jpg"           -> ImageTypes.BARD_IMG;
            case "/it/polimi/ingsw/images/characterCards/10_PRINCESS.jpg"      -> ImageTypes.PRINCESS_IMG;
            case "/it/polimi/ingsw/images/characterCards/11_THIEF.jpg"         -> ImageTypes.THIEF_IMG;

            // AssistantCards
            case "/it/polimi/ingsw/images/assistantCards/assistantCard1.png"  -> ImageTypes.ASS_1_IMG;
            case "/it/polimi/ingsw/images/assistantCards/assistantCard2.png"  -> ImageTypes.ASS_2_IMG;
            case "/it/polimi/ingsw/images/assistantCards/assistantCard3.png"  -> ImageTypes.ASS_3_IMG;
            case "/it/polimi/ingsw/images/assistantCards/assistantCard4.png"  -> ImageTypes.ASS_4_IMG;
            case "/it/polimi/ingsw/images/assistantCards/assistantCard5.png"  -> ImageTypes.ASS_5_IMG;
            case "/it/polimi/ingsw/images/assistantCards/assistantCard6.png"  -> ImageTypes.ASS_6_IMG;
            case "/it/polimi/ingsw/images/assistantCards/assistantCard7.png"  -> ImageTypes.ASS_7_IMG;
            case "/it/polimi/ingsw/images/assistantCards/assistantCard8.png"  -> ImageTypes.ASS_8_IMG;
            case "/it/polimi/ingsw/images/assistantCards/assistantCard9.png"  -> ImageTypes.ASS_9_IMG;
            case "/it/polimi/ingsw/images/assistantCards/assistantCard10.png" -> ImageTypes.ASS_10_IMG;

            // Students
            case "/it/polimi/ingsw/images/students/student_green.png"  -> ImageTypes.STUDENT_GREEN_IMG;
            case "/it/polimi/ingsw/images/students/student_red.png"    -> ImageTypes.STUDENT_RED_IMG;
            case "/it/polimi/ingsw/images/students/student_yellow.png" -> ImageTypes.STUDENT_YELLOW_IMG;
            case "/it/polimi/ingsw/images/students/student_pink.png"   -> ImageTypes.STUDENT_PINK_IMG;
            case "/it/polimi/ingsw/images/students/student_blue.png"   -> ImageTypes.STUDENT_BLUE_IMG;

            // Towers
            case "/it/polimi/ingsw/images/towers/black_tower.png" -> ImageTypes.TOWER_BLACK_IMG;
            case "/it/polimi/ingsw/images/towers/grey_tower.png"  -> ImageTypes.TOWER_GREY_IMG;
            case "/it/polimi/ingsw/images/towers/white_tower.png" -> ImageTypes.TOWER_WHITE_IMG;

            // Wizards
            case "/it/polimi/ingsw/images/wizards/0_NATURE.jpg" -> ImageTypes.WIZARD_NATURE_IMG;
            case "/it/polimi/ingsw/images/wizards/1_DESERT.jpg" -> ImageTypes.WIZARD_DESERT_IMG;
            case "/it/polimi/ingsw/images/wizards/2_CLOUD.jpg"  -> ImageTypes.WIZARD_CLOUD_IMG;
            case "/it/polimi/ingsw/images/wizards/3_SNOW.jpg"   -> ImageTypes.WIZARD_SNOW_IMG;

            // Islands
            case "/it/polimi/ingsw/images/islands/island0.png" -> ImageTypes.ISLAND_0_IMG;
            case "/it/polimi/ingsw/images/islands/island1.png" -> ImageTypes.ISLAND_1_IMG;
            case "/it/polimi/ingsw/images/islands/island2.png" -> ImageTypes.ISLAND_2_IMG;

            // Professors
            case "/it/polimi/ingsw/images/professors/professor_green.png"  -> ImageTypes.PROFESSOR_GREEN_IMG;
            case "/it/polimi/ingsw/images/professors/professor_red.png"    -> ImageTypes.PROFESSOR_RED_IMG;
            case "/it/polimi/ingsw/images/professors/professor_yellow.png" -> ImageTypes.PROFESSOR_YELLOW_IMG;
            case "/it/polimi/ingsw/images/professors/professor_pink.png"   -> ImageTypes.PROFESSOR_PINK_IMG;
            case "/it/polimi/ingsw/images/professors/professor_blue.png"   -> ImageTypes.PROFESSOR_BLUE_IMG;

            // Miscellaneous
            case "/it/polimi/ingsw/images/cloudTile.png"     -> ImageTypes.CLOUD_TILE_IMG;
            case "/it/polimi/ingsw/images/coin.png"          -> ImageTypes.COIN_IMG;
            case "/it/polimi/ingsw/images/cranio.png"        -> ImageTypes.CRANIO_IMG;
            case "/it/polimi/ingsw/images/mother_nature.png" -> ImageTypes.MOTHER_NATURE_IMG;
            case "/it/polimi/ingsw/images/noEntryTile.png"   -> ImageTypes.NOENTRYTILE_IMG;
            case "/it/polimi/ingsw/images/players_icon.png"  -> ImageTypes.PLAYERS_ICON_IMG;
            case "/it/polimi/ingsw/images/polimi.png"        -> ImageTypes.POLIMI_LOGO_IMG;
            case "/it/polimi/ingsw/images/return.png"        -> ImageTypes.RETURN_IMG;
            case "/it/polimi/ingsw/images/school_board.png"  -> ImageTypes.SCHOOL_BOARD_IMG;
            case "/it/polimi/ingsw/images/home_page.jpg"     -> ImageTypes.HOME_PAGE_IMG;

            default -> throw new IllegalStateException();
        };
    }

    /**
     * Get the ImageType associated to a specific fxmlPath
     * @param fxmlPath A String representing the fxmlPath we want the associated ImageType of
     * @return The ImageType associated to the provided fxmlPath
     */
    public static ImageTypes fromFXMLPathToImageTypes (String fxmlPath) {
        return fromHandlerPathToImageTypes(fromFXMLPathToHandlerPath(fxmlPath));
    }

    // endregion ImageTypesTranslator

    // region StudentColorTranslator

    /**
     * Gets the ImageTypes associated to a specific StudentColor
     * @param color A Color representing the StudentColor we want the associated ImageType of
     * @return The ImageType associated to the provided StudentColor
     */
    public static ImageTypes fromStudentColorToImageTypes (Color color) {
        return switch (color) {
            case GREEN  -> ImageTypes.STUDENT_GREEN_IMG;
            case RED    -> ImageTypes.STUDENT_RED_IMG;
            case YELLOW -> ImageTypes.STUDENT_YELLOW_IMG;
            case PINK   -> ImageTypes.STUDENT_PINK_IMG;
            case BLUE   -> ImageTypes.STUDENT_BLUE_IMG;
        };
    }

    public static String fromStudentColorToHandlerPath (Color color) { return fromImageTypesToHandlerPath(fromStudentColorToImageTypes(color)); }

    public static String fromStudentColorToFXMLPath (Color color) { return fromImageTypesToFXMLPath(fromStudentColorToImageTypes(color)); }

    /**
     * Gets the StudentColor associated to a specific ImageType
     * @param imageType The ImageType we want the associated StudentColor of
     * @return A Color representing the StudentColor associated to the provided ImageType
     */
    public static Color fromImageTypesToStudentColor (ImageTypes imageType) {
        return switch (imageType) {
            case STUDENT_GREEN_IMG  -> Color.GREEN;
            case STUDENT_RED_IMG    -> Color.RED;
            case STUDENT_YELLOW_IMG -> Color.YELLOW;
            case STUDENT_PINK_IMG   -> Color.PINK;
            case STUDENT_BLUE_IMG   -> Color.BLUE;
            default                 -> throw new IllegalStateException("The ImageType passed as parameter does not correspond to a StudentColor");
        };
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
        return switch (color){
            case GREEN  -> ImageTypes.PROFESSOR_GREEN_IMG;
            case RED    -> ImageTypes.PROFESSOR_RED_IMG;
            case YELLOW -> ImageTypes.PROFESSOR_YELLOW_IMG;
            case PINK   -> ImageTypes.PROFESSOR_PINK_IMG;
            case BLUE   -> ImageTypes.PROFESSOR_BLUE_IMG;
        };
    }

    public static String fromProfessorColorToHandlerPath (Color color) { return fromImageTypesToHandlerPath(fromProfessorColorToImageTypes(color)); }

    public static String fromProfessorColorToFXMLPath (Color color) { return fromImageTypesToFXMLPath(fromProfessorColorToImageTypes(color)); }

    /**
     * Gets the ProfessorColor associated to a specific ImageType
     * @param imageType The ImageType we want the associated ProfessorColor of
     * @return A Color representing the ProfessorColor associated to the provided ImageType
     */
    public static Color fromImageTypesToProfessorColor (ImageTypes imageType) {
        return switch (imageType) {
            case PROFESSOR_GREEN_IMG  -> Color.GREEN;
            case PROFESSOR_RED_IMG    -> Color.RED;
            case PROFESSOR_YELLOW_IMG -> Color.YELLOW;
            case PROFESSOR_PINK_IMG   -> Color.PINK;
            case PROFESSOR_BLUE_IMG   -> Color.BLUE;
            default                   -> throw new IllegalStateException("The ImageType passed as parameter does not correspond to a ProfessorColor");
        };
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
        return switch (towerColor){
            case WHITE -> ImageTypes.TOWER_WHITE_IMG;
            case BLACK -> ImageTypes.TOWER_BLACK_IMG;
            case GREY  -> ImageTypes.TOWER_GREY_IMG;
        };
    }

    public static String fromTowerColorToHandlerPath (TowerColor towerColor) { return fromImageTypesToHandlerPath(fromTowerColorToImageTypes(towerColor)); }

    public static String fromTowerColorToFXMLPath (TowerColor towerColor) { return fromImageTypesToFXMLPath(fromTowerColorToImageTypes(towerColor)); }

    /**
     * Gets the TowerColor associated to a specific ImageType
     * @param imageType The ImageType we want the associated TowerColor of
     * @return A TowerColor representing the TowerColor associated to the provided ImageType
     */
    public static TowerColor fromImageTypesToTowerColor (ImageTypes imageType) {
        return switch (imageType) {
            case TOWER_WHITE_IMG -> TowerColor.WHITE;
            case TOWER_BLACK_IMG -> TowerColor.BLACK;
            case TOWER_GREY_IMG  -> TowerColor.GREY;
            default              -> throw new IllegalStateException("The ImageType passed as parameter does not correspond to a TowerColor");
        };
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
        return switch (assistantCardNumber) {
            case 1  -> ImageTypes.ASS_1_IMG;
            case 2  -> ImageTypes.ASS_2_IMG;
            case 3  -> ImageTypes.ASS_3_IMG;
            case 4  -> ImageTypes.ASS_4_IMG;
            case 5  -> ImageTypes.ASS_5_IMG;
            case 6  -> ImageTypes.ASS_6_IMG;
            case 7  -> ImageTypes.ASS_7_IMG;
            case 8  -> ImageTypes.ASS_8_IMG;
            case 9  -> ImageTypes.ASS_9_IMG;
            case 10 -> ImageTypes.ASS_10_IMG;
            default -> throw new IllegalStateException("The AssistantCardNumber must be a number between 1 and 10");
        };
    }

    public static String fromAssistantCardNumberToHandlerPath (int assistantCardNumber) { return fromImageTypesToHandlerPath(fromAssistantCardNumberToImageTypes(assistantCardNumber)); }

    public static String fromAssistantCardNumberToFXMLPath (int assistantCardNumber) { return fromImageTypesToFXMLPath(fromAssistantCardNumberToImageTypes(assistantCardNumber)); }

    /**
     * Gets the number of the AssistantCard associated to a specific ImageType
     * @param imageType The ImageType we want the associated AssistantCard of
     * @return An int representing the number of the AssistantCard associated to the provided ImageType
     */
    public static int fromImageTypesToAssistantCardNumber (ImageTypes imageType) {
        return switch (imageType) {
            case ASS_1_IMG  -> 1;
            case ASS_2_IMG  -> 2;
            case ASS_3_IMG  -> 3;
            case ASS_4_IMG  -> 4;
            case ASS_5_IMG  -> 5;
            case ASS_6_IMG  -> 6;
            case ASS_7_IMG  -> 7;
            case ASS_8_IMG  -> 8;
            case ASS_9_IMG  -> 9;
            case ASS_10_IMG -> 10;
            default         -> throw new IllegalStateException("The ImageType passed as parameter does not correspond to an AssistantCardNumber");
        };
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
        return switch (character) {
            case MONK            -> ImageTypes.MONK_IMG;
            case FARMER          -> ImageTypes.FARMER_IMG;
            case STANDARD_BEARER -> ImageTypes.STBEARER_IMG;
            case MAGICIAN        -> ImageTypes.MAGICIAN_IMG;
            case HERBALIST       -> ImageTypes.HERBALIST_IMG;
            case CENTAUR         -> ImageTypes.CENTAUR_IMG;
            case JESTER          -> ImageTypes.JESTER_IMG;
            case CAVALIER        -> ImageTypes.CAVALIER_IMG;
            case MERCHANT        -> ImageTypes.MERCHANT_IMG;
            case BARD            -> ImageTypes.BARD_IMG;
            case PRINCESS        -> ImageTypes.PRINCESS_IMG;
            case THIEF           -> ImageTypes.THIEF_IMG;
        };
    }

    public static String fromCharacterEnumToHandlerPath (Character character) { return fromImageTypesToHandlerPath(fromCharacterEnumToImageTypes(character)); }

    public static String fromCharacterEnumToFXMLPath (Character character) { return fromImageTypesToFXMLPath(fromCharacterEnumToImageTypes(character)); }

    /**
     * Gets the Character of the CharacterCard associated to a specific ImageType
     * @param imageType The ImageType we want the associated CharacterCard of
     * @return The Character of the CharacterCard associated to the provided ImageType
     */
    public static Character fromImageTypesToCharacterEnum (ImageTypes imageType) {
        return switch (imageType) {
            case MONK_IMG      -> Character.MONK;
            case FARMER_IMG    -> Character.FARMER;
            case STBEARER_IMG  -> Character.STANDARD_BEARER;
            case MAGICIAN_IMG  -> Character.MAGICIAN;
            case HERBALIST_IMG -> Character.HERBALIST;
            case CENTAUR_IMG   -> Character.CENTAUR;
            case JESTER_IMG    -> Character.JESTER;
            case CAVALIER_IMG  -> Character.CAVALIER;
            case MERCHANT_IMG  -> Character.MERCHANT;
            case BARD_IMG      -> Character.BARD;
            case PRINCESS_IMG  -> Character.PRINCESS;
            case THIEF_IMG     -> Character.THIEF;
            default            -> throw new IllegalStateException("The ImageType passed as parameter does not correspond to a Character");
        };
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
        return switch (wizard) {
            case NATURE -> ImageTypes.WIZARD_NATURE_IMG;
            case DESERT -> ImageTypes.WIZARD_DESERT_IMG;
            case CLOUD  -> ImageTypes.WIZARD_CLOUD_IMG;
            case SNOW   -> ImageTypes.WIZARD_SNOW_IMG;
        };
    }

    public static String fromWizardEnumToHandlerPath (Wizard wizard) { return fromImageTypesToHandlerPath(fromWizardEnumToImageTypes(wizard)); }

    public static String fromWizardEnumToFXMLPath (Wizard wizard) { return fromImageTypesToFXMLPath(fromWizardEnumToImageTypes(wizard)); }

    /**
     * Gets the Wizard associated to a specific ImageType
     * @param imageType The ImageType we want the associated Wizard of
     * @return The Wizard associated to the provided ImageType
     */
    public static Wizard fromImageTypesToWizardEnum (ImageTypes imageType) {
        return switch (imageType) {
            case WIZARD_NATURE_IMG -> Wizard.NATURE;
            case WIZARD_DESERT_IMG -> Wizard.DESERT;
            case WIZARD_CLOUD_IMG  -> Wizard.CLOUD;
            case WIZARD_SNOW_IMG   -> Wizard.SNOW;
            default                -> throw new IllegalStateException("The ImageType passed as parameter does not correspond to a Wizard");
        };
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
        return switch (islandIDNumber) {
            case 0  -> ImageTypes.ISLAND_0_IMG;
            case 1  -> ImageTypes.ISLAND_1_IMG;
            case 2  -> ImageTypes.ISLAND_2_IMG;
            default -> throw new IllegalStateException("The IslandIDNumber must be a number between 0 and 2");
        };
    }

    public static String fromIslandBackGroundNumberToHandlerPath (int islandBackGroundNumber) { return fromImageTypesToHandlerPath(fromIslandBackGroundNumberToImageTypes(islandBackGroundNumber)); }

    public static String fromIslandBackGroundNumberToFXMLPath (int islandBackGroundNumber) { return fromImageTypesToFXMLPath(fromIslandBackGroundNumberToImageTypes(islandBackGroundNumber)); }

    /**
     * Gets the IslandBackGroundNumber associated to a specific ImageType
     * @param imageType The ImageType we want the associated IslandBackGroundNumber of
     * @return An int representing the IslandBackGroundNumber associated to the provided ImageType
     */
    public static int fromImageTypesToIslandBackGroundNumber (ImageTypes imageType) {
        return switch (imageType) {
            case ISLAND_0_IMG -> 0;
            case ISLAND_1_IMG -> 1;
            case ISLAND_2_IMG -> 2;
            default           -> throw new IllegalStateException("The ImageType passed as parameter does not correspond to an IslandBackground");
        };
    }

    public static int fromHandlerPathToIslandBackGroundNumber (String handlerPath) { return fromImageTypesToIslandBackGroundNumber(fromHandlerPathToImageTypes(handlerPath)); }

    public static int fromFXMLPathToIslandBackGroundNumber (String fxmlPath) { return fromImageTypesToIslandBackGroundNumber(fromFXMLPathToImageTypes(fxmlPath)); }

    // endregion IslandBackgroundTranslator

    // endregion Translators/Switches

    /**
     * Converts the absolute URL, that depends on the devices, on the relative URL useful for the project
     * @param absoluteURL A String containing the absolute URL
     * @return A String representing the relative URL
     */
    public static String fromAbsoluteURLToRelativeURL (String absoluteURL) {
        if (absoluteURL.contains("/")) {
            absoluteURL = absoluteURL.replace("/", "\\");
        }

        int    slashPosition = absoluteURL.indexOf("\\");
        String beforeSlash  = absoluteURL.substring(slashPosition);
        String afterSlash;

        while (!Objects.equals(beforeSlash.substring(0, 7), "\\images")) {

            // Moves after the slash, since it was not followed by "images"
            afterSlash = beforeSlash.substring(1);

            // Searches for the first slash in the new substring
            slashPosition  = afterSlash.indexOf("\\");

            // Moves on the found slash
            beforeSlash = afterSlash.substring(slashPosition);
        }

        beforeSlash = beforeSlash.replace("\\", "/");
        beforeSlash = "@..".concat(beforeSlash);
        return beforeSlash;
    }
}
