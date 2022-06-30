package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.common.model.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Helper class with useful methods to switch from elements ID to their information (position, color,
 * index, number, etc...) related to the Model's way to store data
 * GS = Game Scene
 *
 * @author Sebastiano Meneghin
 */

// Warnings for unused fields or methods have been disabled, since many methods now not used, could become useful in the future
// Warnings for classes that are not exported has been suppressed because are intentionally not exported
@SuppressWarnings({"unused", "ClassEscapesDefinedScope"})

public class InfoHelper {

    // region GameSceneInfo

    // region IslandInfo

    /**
     * Gets the IslandIndex related to the IslandPane (ID)
     *
     * @param islandPaneID the specified IslandAnchorPane of the desired islandIndex
     * @return the index of the specified IslandPane in the GUI interface
     */
    public static int gsFindIslandIndex(AnchorPane islandPaneID) {
        return switch(islandPaneID.getId()) {
            case "isl0_pane"  -> 0;
            case "isl1_pane"  -> 1;
            case "isl2_pane"  -> 2;
            case "isl3_pane"  -> 3;
            case "isl4_pane"  -> 4;
            case "isl5_pane"  -> 5;
            case "isl6_pane"  -> 6;
            case "isl7_pane"  -> 7;
            case "isl8_pane"  -> 8;
            case "isl9_pane"  -> 9;
            case "isl10_pane" -> 10;
            case "isl11_pane" -> 11;
            default           -> throw new IllegalStateException("The islandPaneID you have inserted is not contemplated. You've inserted: " + islandPaneID.getId());
        };
    }

    /**
     * Gets the IslandIndex related to the IslandImage (ID)
     *
     * @param islandImageID the specified IslandImageView of the desired islandIndex
     * @return the index of the specified IslandImage in the GUI interface
     */
    public static int gsFindIslandIndex(ImageView islandImageID) {
        return switch(islandImageID.getId()) {
            case "isl0_img"  -> 0;
            case "isl1_img"  -> 1;
            case "isl2_img"  -> 2;
            case "isl3_img"  -> 3;
            case "isl4_img"  -> 4;
            case "isl5_img"  -> 5;
            case "isl6_img"  -> 6;
            case "isl7_img"  -> 7;
            case "isl8_img"  -> 8;
            case "isl9_img"  -> 9;
            case "isl10_img" -> 10;
            case "isl11_img" -> 11;
            default          -> throw new IllegalStateException("The islandImageID you have inserted is not contemplated. You've inserted: " + islandImageID.getId());
        };
    }

    // endregion IslandInfo

    // region CloudInfo

    /**
     * Gets the CloudTileIndex related to the CloudTilePane (ID)
     *
     * @param cloudTilePaneID the specified CloudTileAnchorPane of the desired cloudTileIndex
     * @return the index of the specified CloudTilePane in the GUI interface
     */
    public static int gsFindCloudTileIndex(AnchorPane cloudTilePaneID) {
        return switch(cloudTilePaneID.getId()) {
            case "cloudTile0_pane" -> 0;
            case "cloudTile1_pane" -> 1;
            case "cloudTile2_pane" -> 2;
            case "cloudTile3_pane" -> 3;
            default                -> throw new IllegalStateException("The cloudTilePaneID you have inserted is not contemplated. You've inserted: " + cloudTilePaneID.getId());
        };
    }

    /**
     * Gets the CloudTileIndex related to the CloudTileImage (ID)
     *
     * @param cloudTileImageID the specified CloudTileImageView of the desired cloudTileIndex
     * @return the index of the specified CloudTileImage in the GUI interface
     */
    public static int gsFindCloudTileIndex(ImageView cloudTileImageID) {
        return switch(cloudTileImageID.getId()) {
            case "cloud0_img" -> 0;
            case "cloud1_img" -> 1;
            case "cloud2_img" -> 2;
            case "cloud3_img" -> 3;
            default           -> throw new IllegalStateException("The cloudTileImageID you have inserted is not contemplated. You've inserted: " + cloudTileImageID.getId());
        };
    }

    // endregion CloudInfo

    // region CharacterCardInfo

    /**
     * Gets the CharacterCardIndex related to the CharacterCardImage (ID)
     *
     * @param characterCardImageID the specified CharacterCardImageView of the desired characterCardIndex
     * @return the index of the specified CharacterCardImage in the GUI interface
     */
    public static int gsFindCharacterCardIndex(ImageView characterCardImageID) {
        return switch(characterCardImageID.getId()) {
            case "characterCard0_img" -> 0;
            case "characterCard1_img" -> 1;
            case "characterCard2_img" -> 2;
            default                   -> throw new IllegalStateException("The characterCardImageID you have inserted is not contemplated. You've inserted: " + characterCardImageID.getId());
        };
    }

    // region CharacterCardElementInfo

    /**
     * Gets the CharacterCardIndex that contains the specified CharacterCardElementImage (ID)
     *
     * @param elementImageID the specified CharacterCardElementImageView of the desired characterCardIndex
     * @return the index of the CharacterCard that contains the specified CharacterCardElementImage in the GUI interface
     */
    public static int gsFindCharacterCardIndexFromSelectedElement (ImageView elementImageID) {
        return switch(elementImageID.getId()) {
            case "CC0_elem0_img", "CC0_elem1_img", "CC0_elem2_img", "CC0_elem3_img", "CC0_elem4_img", "CC0_elem5_img" -> 0;
            case "CC1_elem0_img", "CC1_elem1_img", "CC1_elem2_img", "CC1_elem3_img", "CC1_elem4_img", "CC1_elem5_img" -> 1;
            case "CC2_elem0_img", "CC2_elem1_img", "CC2_elem2_img", "CC2_elem3_img", "CC2_elem4_img", "CC2_elem5_img" -> 2;
            default                                                                                                   -> throw new IllegalStateException("The elementImageID you have inserted is not contemplated. You've inserted: " + elementImageID.getId());
        };
    }

    /**
     * Gets the CharacterCardElementIndex related to the CharacterCardElementImage (ID)
     *
     * @param elementImageID the specified CharacterCardElementImageView of the desired characterCardElementIndex
     * @return the index of the specified CharacterCardElementImage in the GUI interface
     */
    public static int gsFindCharacterCardElementIndex(ImageView elementImageID) {
        return switch(elementImageID.getId()) {
            case "CC0_elem0_img", "CC1_elem0_img", "CC2_elem0_img" -> 0;
            case "CC0_elem1_img", "CC1_elem1_img", "CC2_elem1_img" -> 1;
            case "CC0_elem2_img", "CC1_elem2_img", "CC2_elem2_img" -> 2;
            case "CC0_elem3_img", "CC1_elem3_img", "CC2_elem3_img" -> 3;
            case "CC0_elem4_img", "CC1_elem4_img", "CC2_elem4_img" -> 4;
            case "CC0_elem5_img", "CC1_elem5_img", "CC2_elem5_img" -> 5;
            default                                                -> throw new IllegalStateException("The elementImageID you have inserted is not contemplated. You've inserted: " + elementImageID.getId());
        };
    }

    /**
     * Gets the Color of the CharacterCardElement specified by the CharacterCardElementImage (ID)
     * This gets the Color only if the element is a student, it raises a IllegalStateException otherwise
     *
     * @param elementImageID the specified CharacterCardElementImageView of the desired color
     * @return the color of the student corresponding to the CharacterCardElementImage
     */
    public static Color gsFindCharacterCardElementColor(ImageView elementImageID) {
        String     elementImageFXMLPath = elementImageID.getImage().getUrl();
        ImageTypes elementImageType     = PathHelper.fromFXMLPathToImageTypes(elementImageFXMLPath);

        return switch(elementImageType) {
            case STUDENT_GREEN_IMG  -> Color.GREEN;
            case STUDENT_RED_IMG    -> Color.RED;
            case STUDENT_YELLOW_IMG -> Color.YELLOW;
            case STUDENT_PINK_IMG   -> Color.PINK;
            case STUDENT_BLUE_IMG   -> Color.BLUE;
            case NOENTRYTILE_IMG    -> throw new IllegalStateException("Error! You're trying to use this function on a noEntryTile! You can use it only with characterCards' students");
            default                 -> throw new IllegalStateException("The elementImageID you have inserted is not contemplated. You've inserted: " + elementImageID.getId());
        };
    }

    // endregion CharacterCardElementInfo

    // endregion CharacterCardInfo

    // region DiningRoomInfo

    /**
     * Gets the Color of the diningRoomTable specified by the DiningRoomTablePane (ID)
     *
     * @param diningRoomTablePaneID the specified DiningRoomTableAnchorPane of the desired color
     * @return the color of the diningRoomTable corresponding to the DiningRoomTablePane
     */
    public static Color gsFindDiningRoomTableColor(AnchorPane diningRoomTablePaneID) {
        return switch(diningRoomTablePaneID.getId()) {
            case "greenDiningRoom_pane"  -> Color.GREEN;
            case "redDiningRoom_pane"    -> Color.RED;
            case "yellowDiningRoom_pane" -> Color.YELLOW;
            case "pinkDiningRoom_pane"   -> Color.PINK;
            case "blueDiningRoom_pane"   -> Color.BLUE;
            default                      -> throw new IllegalStateException("The diningRoomTableID you have inserted is not contemplated. You've inserted: " + diningRoomTablePaneID.getId());
        };
    }

    // endregion DiningRoomInfo

    // region EntranceInfo

    /**
     * Gets the index of the student in the entrance specified by the EntranceStudentImage (ID)
     *
     * @param entranceStudentImageID the specified EntranceStudentImageView of the desired studentIndex
     * @return the studentIndex corresponding to the entranceStudentImage
     */
    public static int gsFindEntranceStudentIndex (ImageView entranceStudentImageID) {
        return switch(entranceStudentImageID.getId()) {
            case "E_student0_img" -> 0;
            case "E_student1_img" -> 1;
            case "E_student2_img" -> 2;
            case "E_student3_img" -> 3;
            case "E_student4_img" -> 4;
            case "E_student5_img" -> 5;
            case "E_student6_img" -> 6;
            case "E_student7_img" -> 7;
            case "E_student8_img" -> 8;
            default               -> throw new IllegalStateException("The entranceStudentImageID you have inserted is not contemplated. You've inserted: " + entranceStudentImageID.getId());
        };
    }

    /**
     * Gets the Color of the student in the entrance specified by the EntranceStudentImage (ID)
     *
     * @param studentImageID the specified EntranceStudentImageView of the desired student (color)
     * @return the color of the student corresponding to the entranceStudentImage
     */
    public static Color gsFindEntranceStudentColor(ImageView studentImageID) {
        String     studentImageFXMLPath = studentImageID.getImage().getUrl();
        ImageTypes studentImageType     = PathHelper.fromFXMLPathToImageTypes(studentImageFXMLPath);

        return switch(studentImageType) {
            case STUDENT_GREEN_IMG  -> Color.GREEN;
            case STUDENT_RED_IMG    -> Color.RED;
            case STUDENT_YELLOW_IMG -> Color.YELLOW;
            case STUDENT_PINK_IMG   -> Color.PINK;
            case STUDENT_BLUE_IMG   -> Color.BLUE;
            default                 -> throw new IllegalStateException("The studentImageID you have inserted is not contemplated. You've inserted: " + studentImageID.getId());
        };
    }

    // endregion EntranceInfo

    // region AssistantCardInfo

    /**
     * Gets the index of the assistantCard from the specified AssistantCardImage (ID)
     *
     * @param assistantCardImageID the specified AssistantCardImageView of the desired ACIndex
     * @return the index of the assistantCard corresponding to the assistantCardImage
     */
    public static int gsFindAssistantCardIndex (ImageView assistantCardImageID) {
        String     assistantCardImageFXMLPath = assistantCardImageID.getImage().getUrl();
        ImageTypes assistantCardImageType     = PathHelper.fromFXMLPathToImageTypes(assistantCardImageFXMLPath);

        return PathHelper.fromImageTypesToAssistantCardNumber(assistantCardImageType);
    }

    // endregion AssistantCardInfo

    // endregion GameSceneInfo

}
