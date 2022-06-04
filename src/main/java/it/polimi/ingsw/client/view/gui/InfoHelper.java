package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.gui.sceneHandlers.GameSceneHandler;
import it.polimi.ingsw.client.view.gui.sceneHandlers.PlayersSchoolBoardHandler;
import it.polimi.ingsw.common.model.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Helper class with useful methods to switch from elements ID to their information (position, color,
 * index, number, etc...) related to the Model's way to store data
 * GS = Game Scene
 * @author Sebastiano Meneghin
 */
public class InfoHelper {

    // region GameSceneInfo

    // region IslandInfo

    public static int gsFindIslandIndex(AnchorPane islandPaneID) {
        int islandIndex;

        switch(islandPaneID.getId()) {
            case "isl0_pane"  -> islandIndex = 0;
            case "isl1_pane"  -> islandIndex = 1;
            case "isl2_pane"  -> islandIndex = 2;
            case "isl3_pane"  -> islandIndex = 3;
            case "isl4_pane"  -> islandIndex = 4;
            case "isl5_pane"  -> islandIndex = 5;
            case "isl6_pane"  -> islandIndex = 6;
            case "isl7_pane"  -> islandIndex = 7;
            case "isl8_pane"  -> islandIndex = 8;
            case "isl9_pane"  -> islandIndex = 9;
            case "isl10_pane" -> islandIndex = 10;
            case "isl11_pane" -> islandIndex = 11;
            default           -> throw new IllegalStateException("The islandPaneID you have inserted is not contemplated. You've inserted: " + islandPaneID.getId());
        }

        return islandIndex;
    }

    public static int gsFindIslandIndex(ImageView islandImageID) {
        int islandIndex;

        switch(islandImageID.getId()) {
            case "isl0_img"  -> islandIndex = 0;
            case "isl1_img"  -> islandIndex = 1;
            case "isl2_img"  -> islandIndex = 2;
            case "isl3_img"  -> islandIndex = 3;
            case "isl4_img"  -> islandIndex = 4;
            case "isl5_img"  -> islandIndex = 5;
            case "isl6_img"  -> islandIndex = 6;
            case "isl7_img"  -> islandIndex = 7;
            case "isl8_img"  -> islandIndex = 8;
            case "isl9_img"  -> islandIndex = 9;
            case "isl10_img" -> islandIndex = 10;
            case "isl11_img" -> islandIndex = 11;
            default           -> throw new IllegalStateException("The islandImageID you have inserted is not contemplated. You've inserted: " + islandImageID.getId());
        }

        return islandIndex;
    }

    // endregion IslandInfo

    // region CloudInfo

    public static int gsFindCloudTileIndex(AnchorPane cloudTilePaneID) {
        int cloudTileIndex;

        switch(cloudTilePaneID.getId()) {
            case "cloudTile0_pane"  -> cloudTileIndex = 0;
            case "cloudTile1_pane"  -> cloudTileIndex = 1;
            case "cloudTile2_pane"  -> cloudTileIndex = 2;
            case "cloudTile3_pane"  -> cloudTileIndex = 3;
            default           -> throw new IllegalStateException("The cloudTilePaneID you have inserted is not contemplated. You've inserted: " + cloudTilePaneID.getId());
        }

        return cloudTileIndex;
    }

    public static int gsFindCloudTileIndex(ImageView cloudTileImageID) {
        int cloudTileIndex;

        switch(cloudTileImageID.getId()) {
            case "cloud0_img"  -> cloudTileIndex = 0;
            case "cloud1_img"  -> cloudTileIndex = 1;
            case "cloud2_img"  -> cloudTileIndex = 2;
            case "cloud3_img"  -> cloudTileIndex = 3;
            default           -> throw new IllegalStateException("The cloudTileImageID you have inserted is not contemplated. You've inserted: " + cloudTileImageID.getId());
        }

        return cloudTileIndex;
    }

    // endregion CloudInfo

    // region CharacterCardInfo

    public static int gsFindCharacterCardIndex(ImageView characterCardImageID) {
        int characterCardIndex;

        switch(characterCardImageID.getId()) {
            case "characterCard0_img"  -> characterCardIndex = 0;
            case "characterCard1_img"  -> characterCardIndex = 1;
            case "characterCard2_img"  -> characterCardIndex = 2;
            default           -> throw new IllegalStateException("The characterCardImageID you have inserted is not contemplated. You've inserted: " + characterCardImageID.getId());
        }

        return characterCardIndex;
    }

    // region CharacterCardElementInfo

    public static int gsFindCharacterCardElementIndex(ImageView elementImageID) {
        int elementIndex;

        switch(elementImageID.getId()) {
            case "CC0_elem0_img", "CC1_elem0_img", "CC2_elem0_img" -> elementIndex = 0;
            case "CC0_elem1_img", "CC1_elem1_img", "CC2_elem1_img" -> elementIndex = 1;
            case "CC0_elem2_img", "CC1_elem2_img", "CC2_elem2_img" -> elementIndex = 2;
            case "CC0_elem3_img", "CC1_elem3_img", "CC2_elem3_img" -> elementIndex = 3;
            case "CC0_elem4_img", "CC1_elem4_img", "CC2_elem4_img" -> elementIndex = 4;
            case "CC0_elem5_img", "CC1_elem5_img", "CC2_elem5_img" -> elementIndex = 5;

            default -> throw new IllegalStateException("The elementImageID you have inserted is not contemplated. You've inserted: " + elementImageID.getId());
        }

        return elementIndex;
    }

    public static Color gsFindCharacterCardElementColor(ImageView elementImageID) {
        String     elementImageFXMLPath = elementImageID.getImage().getUrl();
        ImageTypes elementImageType     = PathHelper.fromFXMLPathToImageTypes(elementImageFXMLPath);
        Color      elementColor;

        switch(elementImageType) {
            case STUDENT_GREEN_IMG  -> elementColor = Color.GREEN;
            case STUDENT_RED_IMG    -> elementColor = Color.RED;
            case STUDENT_YELLOW_IMG -> elementColor = Color.YELLOW;
            case STUDENT_PINK_IMG   -> elementColor = Color.PINK;
            case STUDENT_BLUE_IMG   -> elementColor = Color.BLUE;
            case NOENTRYTILE_IMG    -> throw new IllegalStateException("Error! You're trying to use this function on a noEntryTile! You can use it only with characterCards' students");
            default                 -> throw new IllegalStateException("The elementImageID you have inserted is not contemplated. You've inserted: " + elementImageID.getId());
        }

        return elementColor;
    }

    // endregion CharacterCardElementInfo

    // endregion CharacterCardInfo

    // region DiningRoomInfo

    public static Color gsFindDiningRoomTableColor(AnchorPane diningRoomTablePaneID) {
        Color tableColor;

        switch(diningRoomTablePaneID.getId()) {
            case "greenDiningRoom_Pane"  -> tableColor = Color.GREEN;
            case "redDiningRoom_Pane"    -> tableColor = Color.RED;
            case "yellowDiningRoom_Pane" -> tableColor = Color.YELLOW;
            case "pinkDiningRoom_Pane"   -> tableColor = Color.PINK;
            case "blueDiningRoom_Pane"   -> tableColor = Color.BLUE;

            default -> throw new IllegalStateException("The diningRoomTableID you have inserted is not contemplated. You've inserted: " + diningRoomTablePaneID.getId());
        }

        return tableColor;
    }

    // endregion DiningRoomInfo

    // region EntranceInfo

    public static int gsFindEntranceStudentIndex (ImageView entranceStudentImageID) {
        int entranceStudentIndex;

        switch(entranceStudentImageID.getId()) {
            case "E_student0_img"  -> entranceStudentIndex = 0;
            case "E_student1_img"  -> entranceStudentIndex = 1;
            case "E_student2_img"  -> entranceStudentIndex = 2;
            case "E_student3_img"  -> entranceStudentIndex = 3;
            case "E_student4_img"  -> entranceStudentIndex = 4;
            case "E_student5_img"  -> entranceStudentIndex = 5;
            case "E_student6_img"  -> entranceStudentIndex = 6;
            case "E_student7_img"  -> entranceStudentIndex = 7;
            case "E_student8_img"  -> entranceStudentIndex = 8;
            default           -> throw new IllegalStateException("The entranceStudentImageID you have inserted is not contemplated. You've inserted: " + entranceStudentImageID.getId());
        }

        return entranceStudentIndex;
    }

    public static Color gsFindEntranceStudentColor(ImageView studentImageID) {
        String     studentImageFXMLPath = studentImageID.getImage().getUrl();
        ImageTypes studentImageType     = PathHelper.fromFXMLPathToImageTypes(studentImageFXMLPath);
        Color      studentColor;

        switch(studentImageType) {
            case STUDENT_GREEN_IMG  -> studentColor = Color.GREEN;
            case STUDENT_RED_IMG    -> studentColor = Color.RED;
            case STUDENT_YELLOW_IMG -> studentColor = Color.YELLOW;
            case STUDENT_PINK_IMG   -> studentColor = Color.PINK;
            case STUDENT_BLUE_IMG   -> studentColor = Color.BLUE;
            default                 -> throw new IllegalStateException("The studentImageID you have inserted is not contemplated. You've inserted: " + studentImageID.getId());
        }

        return studentColor;
    }

    // endregion EntranceInfo

    // region AssistantCardInfo

    public static int gsFindAssistantCardIndex (ImageView assistantCardImageID) {
        String     assistantCardImageFXMLPath = assistantCardImageID.getImage().getUrl();
        ImageTypes assistantCardImageType     = PathHelper.fromFXMLPathToImageTypes(assistantCardImageFXMLPath);
        Integer assistantCardIndex = null;

        switch (assistantCardImageType) {
            case ASS_1_IMG  -> assistantCardIndex = 1;
            case ASS_2_IMG  -> assistantCardIndex = 2;
            case ASS_3_IMG  -> assistantCardIndex = 3;
            case ASS_4_IMG  -> assistantCardIndex = 4;
            case ASS_5_IMG  -> assistantCardIndex = 5;
            case ASS_6_IMG  -> assistantCardIndex = 6;
            case ASS_7_IMG  -> assistantCardIndex = 7;
            case ASS_8_IMG  -> assistantCardIndex = 8;
            case ASS_9_IMG  -> assistantCardIndex = 9;
            case ASS_10_IMG -> assistantCardIndex = 10;
            default         -> throw new IllegalStateException("The assistantCardImageID you have inserted is not contemplated. You've inserted: " + assistantCardImageID.getId());
        }

        return assistantCardIndex;
    }

    // endregion AssistantCardInfo

    // endregion GameSceneInfo

}
