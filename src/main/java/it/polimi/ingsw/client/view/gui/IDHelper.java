package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.gui.sceneHandlers.EndGameHandler;
import it.polimi.ingsw.client.view.gui.sceneHandlers.GameSceneHandler;
import it.polimi.ingsw.client.view.gui.sceneHandlers.PlayersSchoolBoardHandler;
import it.polimi.ingsw.common.model.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

// TODO - JavaDocs (?)

/**
 * Helper class with useful methods to switch between model elements and corresponding id
 * GS = Game Scene; PSB = PlayersSchoolBoard
 * @author Sebastiano Meneghin
 */
public class IDHelper {

    // region GameSceneID

    // region IslandID

    /**
     * GS Method that given the position of an island, returns the corresponding id
     * @param gs the GameSceneHandler
     * @param islandPosition the island position
     * @return the corresponding id
     */
    public static ImageView gsFindIslandID (GameSceneHandler gs, int islandPosition) {
        ImageView islandID = null;
        switch (islandPosition) {
            case 0  -> islandID = gs.isl0_img;
            case 1  -> islandID = gs.isl1_img;
            case 2  -> islandID = gs.isl2_img;
            case 3  -> islandID = gs.isl3_img;
            case 4  -> islandID = gs.isl4_img;
            case 5  -> islandID = gs.isl5_img;
            case 6  -> islandID = gs.isl6_img;
            case 7  -> islandID = gs.isl7_img;
            case 8  -> islandID = gs.isl8_img;
            case 9  -> islandID = gs.isl9_img;
            case 10 -> islandID = gs.isl10_img;
            case 11 -> islandID = gs.isl11_img;
            default -> throw new IllegalStateException("The islandPosition must be between 0 and 11, has been inserted: " + islandPosition);
        }

        return islandID;
    }

    // region IslandAnchorPaneID

    /**
     * Gets the AnchorPane (ID) of a specific Island
     * @param gs The gameSceneHandler
     * @param islandID An ImageView correspondent to the islandID of the specified island
     * @return The AnchorPane correspondent to the desired tower
     */
    public static AnchorPane gsFindIslandAnchorPaneID (GameSceneHandler gs, ImageView islandID) {
        AnchorPane islandAnchorPaneID = null;

        switch(islandID.getId()) {
            case "isl0_img"   -> islandAnchorPaneID = gs.isl0_pane;
            case "isl1_img"   -> islandAnchorPaneID = gs.isl1_pane;
            case "isl2_img"   -> islandAnchorPaneID = gs.isl2_pane;
            case "isl3_img"   -> islandAnchorPaneID = gs.isl3_pane;
            case "isl4_img"   -> islandAnchorPaneID = gs.isl4_pane;
            case "isl5_img"   -> islandAnchorPaneID = gs.isl5_pane;
            case "isl6_img"   -> islandAnchorPaneID = gs.isl6_pane;
            case "isl7_img"   -> islandAnchorPaneID = gs.isl7_pane;
            case "isl8_img"   -> islandAnchorPaneID = gs.isl8_pane;
            case "isl9_img"   -> islandAnchorPaneID = gs.isl9_pane;
            case "isl10_img"  -> islandAnchorPaneID = gs.isl10_pane;
            case "isl11_img"  -> islandAnchorPaneID = gs.isl11_pane;
            default -> throw new IllegalStateException("The islandID you have inserted is not contemplated. What you've inserted: " + islandID.getId());
        }

        return islandAnchorPaneID;
    }

    /**
     * Gets the AnchorPane (ID) of a specific Island
     * @param gs The gameSceneHandler
     * @param islandPosition An int representing the islandNumber of the specified island
     * @return The AnchorPane correspondent to the desired tower
     */
    public static AnchorPane gsFindIslandAnchorPaneID (GameSceneHandler gs, int islandPosition) {
        ImageView islandID = gsFindIslandID(gs, islandPosition);
        return gsFindIslandAnchorPaneID(gs, islandID);
    }

    // endregion IslandAnchorPaneID

    // region IslandStudentID

    // region IslandStudentSingleID

    /**
     * Gets the ImageView (ID) of a specific student on a specific Island
     * @param gs The gameSceneHandler
     * @param islandID An ImageView correspondent to the islandID of the island where the student is located
     * @param studentColor The color of the student to identify
     * @return The ImageView correspondent to the desired student
     */
    public static ImageView gsFindStudentOnIslandID (GameSceneHandler gs, ImageView islandID, Color studentColor) {
        ImageView studentOnIslandID = null;

        switch(islandID.getId()) {
            case "isl0_img"  -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl0_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl0_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl0_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl0_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl0_blueStud_img;
                }
            }

            case "isl1_img"  -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl1_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl1_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl1_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl1_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl1_blueStud_img;
                }
            }

            case "isl2_img"  -> {
                switch (studentColor){
                case GREEN  -> studentOnIslandID = gs.isl2_greenStud_img;
                case RED    -> studentOnIslandID = gs.isl2_redStud_img;
                case YELLOW -> studentOnIslandID = gs.isl2_yellowStud_img;
                case PINK   -> studentOnIslandID = gs.isl2_pinkStud_img;
                case BLUE   -> studentOnIslandID = gs.isl2_blueStud_img;
                }
            }

            case "isl3_img"  -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl3_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl3_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl3_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl3_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl3_blueStud_img;
                }
            }

            case "isl4_img"  -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl4_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl4_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl4_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl4_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl4_blueStud_img;
                }
            }

            case "isl5_img"  -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl5_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl5_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl5_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl5_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl5_blueStud_img;
                }
            }

            case "isl6_img"  -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl6_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl6_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl6_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl6_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl6_blueStud_img;
                }
            }

            case "isl7_img"  -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl7_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl7_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl7_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl7_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl7_blueStud_img;
                }
            }

            case "isl8_img"  -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl8_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl8_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl8_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl8_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl8_blueStud_img;
                }
            }

            case "isl9_img"  -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl9_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl9_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl9_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl9_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl9_blueStud_img;
                }
            }

            case "isl10_img" -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl10_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl10_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl10_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl10_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl10_blueStud_img;
                }
            }

            case "isl11_img" -> {
                switch (studentColor){
                    case GREEN  -> studentOnIslandID = gs.isl11_greenStud_img;
                    case RED    -> studentOnIslandID = gs.isl11_redStud_img;
                    case YELLOW -> studentOnIslandID = gs.isl11_yellowStud_img;
                    case PINK   -> studentOnIslandID = gs.isl11_pinkStud_img;
                    case BLUE   -> studentOnIslandID = gs.isl11_blueStud_img;
                }
            }

            default -> throw new IllegalStateException("The islandID you have inserted is not contemplated. What you've inserted: " + islandID.getId());
        }

        return studentOnIslandID;
    }

    /**
     * Gets the ImageView (ID) of a specific student on a specific Island
     * @param gs The gameSceneHandler
     * @param islandPosition An int representing the islandNumber where the student is located
     * @param studentColor The color of the student to identify
     * @return The ImageView correspondent to the desired student
     */
    public static ImageView gsFindStudentOnIslandID (GameSceneHandler gs, int islandPosition, Color studentColor) {
        ImageView islandID = gsFindIslandID(gs, islandPosition);
        return gsFindStudentOnIslandID(gs, islandID, studentColor);
    }

    // endregion IslandStudentSingleID

    // region IslandStudentCounterID

    /**
     * Gets the Text (ID) of a specific studentCounter on a specific Island
     * @param gs The gameSceneHandler
     * @param islandID An ImageView correspondent to the islandID of the island where the counterStudent is located
     * @param studentCounterColor The color of the studentCounter we want to access to
     * @return The Text (ID) correspondent to the desired studentCounter
     */
    public static Text gsFindStudentCounterOnIslandID (GameSceneHandler gs, ImageView islandID, Color studentCounterColor) {
        Text studentCounterOnIslandID = null;

        switch(islandID.getId()) {
            case "isl0_img"  -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl0_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl0_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl0_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl0_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl0_blueStudCount_text;
                }
            }

            case "isl1_img"  -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl1_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl1_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl1_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl1_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl1_blueStudCount_text;
                }
            }

            case "isl2_img"  -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl2_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl2_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl2_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl2_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl2_blueStudCount_text;
                }
            }

            case "isl3_img"  -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl3_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl3_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl3_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl3_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl3_blueStudCount_text;
                }
            }

            case "isl4_img"  -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl4_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl4_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl4_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl4_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl4_blueStudCount_text;
                }
            }

            case "isl5_img"  -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl5_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl5_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl5_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl5_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl5_blueStudCount_text;
                }
            }

            case "isl6_img"  -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl6_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl6_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl6_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl6_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl6_blueStudCount_text;
                }
            }

            case "isl7_img"  -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl7_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl7_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl7_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl7_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl7_blueStudCount_text;
                }
            }

            case "isl8_img"  -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl8_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl8_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl8_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl8_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl8_blueStudCount_text;
                }
            }

            case "isl9_img"  -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl9_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl9_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl9_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl9_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl9_blueStudCount_text;
                }
            }

            case "isl10_img" -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl10_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl10_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl10_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl10_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl10_blueStudCount_text;
                }
            }

            case "isl11_img" -> {
                switch (studentCounterColor){
                    case GREEN  -> studentCounterOnIslandID = gs.isl11_greenStudCount_text;
                    case RED    -> studentCounterOnIslandID = gs.isl11_redStudCount_text;
                    case YELLOW -> studentCounterOnIslandID = gs.isl11_yellowStudCount_text;
                    case PINK   -> studentCounterOnIslandID = gs.isl11_pinkStudCount_text;
                    case BLUE   -> studentCounterOnIslandID = gs.isl11_blueStudCount_text;
                }
            }

            default -> throw new IllegalStateException("The islandID you have inserted is not contemplated. What you've inserted: " + islandID.getId());
        }

        return studentCounterOnIslandID;
    }

    /**
     * Gets the Text (ID) of a specific studentCounter on a specific Island
     * @param gs The gameSceneHandler
     * @param islandPosition An int representing the islandNumber where the studentCounter is located
     * @param studentCounterColor The color of the studentCounter we want to access to
     * @return The Text (ID) correspondent to the desired studentCounter
     */
    public static Text gsFindStudentCounterOnIslandID (GameSceneHandler gs, int islandPosition, Color studentCounterColor) {
        ImageView islandID = gsFindIslandID(gs, islandPosition);
        return gsFindStudentCounterOnIslandID(gs, islandID, studentCounterColor);
    }

    // endregion IslandStudentCounterID

    // endregion IslandStudentID

    // region IslandTowerID

    // region IslandTowerSingleID

    /**
     * Gets the ImageView (ID) of the tower on a specific Island
     * @param gs The gameSceneHandler
     * @param islandID An ImageView correspondent to the islandID of the island where the tower is located
     * @return The ImageView correspondent to the desired tower
     */
    public static ImageView gsFindTowerOnIslandID (GameSceneHandler gs, ImageView islandID) {
        ImageView towerOnIslandID = null;

        switch(islandID.getId()) {
            case "isl0_img"   -> towerOnIslandID = gs.isl0_tower_img;
            case "isl1_img"   -> towerOnIslandID = gs.isl1_tower_img;
            case "isl2_img"   -> towerOnIslandID = gs.isl2_tower_img;
            case "isl3_img"   -> towerOnIslandID = gs.isl3_tower_img;
            case "isl4_img"   -> towerOnIslandID = gs.isl4_tower_img;
            case "isl5_img"   -> towerOnIslandID = gs.isl5_tower_img;
            case "isl6_img"   -> towerOnIslandID = gs.isl6_tower_img;
            case "isl7_img"   -> towerOnIslandID = gs.isl7_tower_img;
            case "isl8_img"   -> towerOnIslandID = gs.isl8_tower_img;
            case "isl9_img"   -> towerOnIslandID = gs.isl9_tower_img;
            case "isl10_img"  -> towerOnIslandID = gs.isl10_tower_img;
            case "isl11_img"  -> towerOnIslandID = gs.isl11_tower_img;
            default -> throw new IllegalStateException("The islandID you have inserted is not contemplated. What you've inserted: " + islandID.getId());
        }

        return towerOnIslandID;
    }

    /**
     * Gets the ImageView (ID) of the tower on a specific Island
     * @param gs The gameSceneHandler
     * @param islandPosition An int representing the islandNumber of the island where the tower is located
     * @return The ImageView correspondent to the desired tower
     */
    public static ImageView gsFindTowerOnIslandID (GameSceneHandler gs, int islandPosition) {
        ImageView islandID = gsFindIslandID(gs, islandPosition);
        return gsFindTowerOnIslandID(gs, islandID);
    }

    // endregion IslandTowerSingleID

    // region IslandTowerCounterID

    /**
     * Gets the Text (ID) of the towerCounter on a specific Island
     * @param gs The gameSceneHandler
     * @param islandID An ImageView correspondent to the islandID of the island where the towerCounter is located
     * @return The Text (ID) correspondent to the desired towerCounter
     */
    public static Text gsFindTowerCountOnIslandID (GameSceneHandler gs, ImageView islandID) {
        Text towerCountOnIslandID = null;

        switch(islandID.getId()) {
            case "isl0_img"   -> towerCountOnIslandID = gs.isl0_towersCount_text;
            case "isl1_img"   -> towerCountOnIslandID = gs.isl1_towersCount_text;
            case "isl2_img"   -> towerCountOnIslandID = gs.isl2_towersCount_text;
            case "isl3_img"   -> towerCountOnIslandID = gs.isl3_towersCount_text;
            case "isl4_img"   -> towerCountOnIslandID = gs.isl4_towersCount_text;
            case "isl5_img"   -> towerCountOnIslandID = gs.isl5_towersCount_text;
            case "isl6_img"   -> towerCountOnIslandID = gs.isl6_towersCount_text;
            case "isl7_img"   -> towerCountOnIslandID = gs.isl7_towersCount_text;
            case "isl8_img"   -> towerCountOnIslandID = gs.isl8_towersCount_text;
            case "isl9_img"   -> towerCountOnIslandID = gs.isl9_towersCount_text;
            case "isl10_img"  -> towerCountOnIslandID = gs.isl10_towersCount_text;
            case "isl11_img"  -> towerCountOnIslandID = gs.isl11_towersCount_text;
            default -> throw new IllegalStateException("The islandID you have inserted is not contemplated. What you've inserted: " + islandID.getId());
        }

        return towerCountOnIslandID;
    }

    /**
     * Gets the Text (ID) of the towerCounter on a specific Island
     * @param gs The gameSceneHandler
     * @param islandPosition An int representing the islandNumber of the island where the towerCounter is located
     * @return The Text (ID) correspondent to the desired towerCounter
     */
    public static Text gsFindTowerCountOnIslandID (GameSceneHandler gs, int islandPosition) {
        ImageView islandID = gsFindIslandID(gs, islandPosition);
        return gsFindTowerCountOnIslandID(gs, islandID);
    }

    // endregion IslandTowerCounterID

    // endregion IslandTowerID

    // region IslandNoEntryTileID

    // region IslandNoEntryTileSingleID

    /**
     * Gets the ImageView (ID) of the noEntryTile on a specific Island
     * @param gs The gameSceneHandler
     * @param islandID An ImageView correspondent to the islandID of the island where the noEntryTile is located
     * @return The ImageView correspondent to the desired noEntryTile
     */
    public static ImageView gsFindNoEntryTileImgOnIslandID (GameSceneHandler gs, ImageView islandID) {
        ImageView tileOnIslandID = null;

        switch(islandID.getId()) {
            case "isl0_img"   -> tileOnIslandID = gs.isl0_noEntryTile_img;
            case "isl1_img"   -> tileOnIslandID = gs.isl1_noEntryTile_img;
            case "isl2_img"   -> tileOnIslandID = gs.isl2_noEntryTile_img;
            case "isl3_img"   -> tileOnIslandID = gs.isl3_noEntryTile_img;
            case "isl4_img"   -> tileOnIslandID = gs.isl4_noEntryTile_img;
            case "isl5_img"   -> tileOnIslandID = gs.isl5_noEntryTile_img;
            case "isl6_img"   -> tileOnIslandID = gs.isl6_noEntryTile_img;
            case "isl7_img"   -> tileOnIslandID = gs.isl7_noEntryTile_img;
            case "isl8_img"   -> tileOnIslandID = gs.isl8_noEntryTile_img;
            case "isl9_img"   -> tileOnIslandID = gs.isl9_noEntryTile_img;
            case "isl10_img"  -> tileOnIslandID = gs.isl10_noEntryTile_img;
            case "isl11_img"  -> tileOnIslandID = gs.isl11_noEntryTile_img;
            default -> throw new IllegalStateException("The islandID you have inserted is not contemplated. What you've inserted: " + islandID.getId());
        }

        return tileOnIslandID;
    }

    /**
     * Gets the ImageView (ID) of the noEntryTile on a specific Island
     * @param gs The gameSceneHandler
     * @param islandPosition An int representing the islandNumber of the island where the noEntryTile is located
     * @return The ImageView correspondent to the desired noEntryTile
     */
    public static ImageView gsFindNoEntryTileImgOnIslandID (GameSceneHandler gs, int islandPosition) {
        ImageView islandID = gsFindIslandID(gs, islandPosition);
        return gsFindNoEntryTileImgOnIslandID(gs, islandID);
    }

    // endregion IslandNoEntryTileSingleID

    // region IslandNoEntryTileCounterID

    /**
     * Gets the Text (ID) of the noEntryTileCount on a specific Island
     * @param gs The gameSceneHandler
     * @param islandID An ImageView correspondent to the islandID of the island where the noEntryTileCount is located
     * @return The Text correspondent to the desired noEntryTileCount
     */
    public static Text gsFindNoEntryTileCountOnIslandID (GameSceneHandler gs, ImageView islandID) {
        Text tileCountOnIslandID = null;

        switch(islandID.getId()) {
            case "isl0_img"   -> tileCountOnIslandID = gs.isl0_noEntryTilesCount_text;
            case "isl1_img"   -> tileCountOnIslandID = gs.isl1_noEntryTilesCount_text;
            case "isl2_img"   -> tileCountOnIslandID = gs.isl2_noEntryTilesCount_text;
            case "isl3_img"   -> tileCountOnIslandID = gs.isl3_noEntryTilesCount_text;
            case "isl4_img"   -> tileCountOnIslandID = gs.isl4_noEntryTilesCount_text;
            case "isl5_img"   -> tileCountOnIslandID = gs.isl5_noEntryTilesCount_text;
            case "isl6_img"   -> tileCountOnIslandID = gs.isl6_noEntryTilesCount_text;
            case "isl7_img"   -> tileCountOnIslandID = gs.isl7_noEntryTilesCount_text;
            case "isl8_img"   -> tileCountOnIslandID = gs.isl8_noEntryTilesCount_text;
            case "isl9_img"   -> tileCountOnIslandID = gs.isl9_noEntryTilesCount_text;
            case "isl10_img"  -> tileCountOnIslandID = gs.isl10_noEntryTilesCount_text;
            case "isl11_img"  -> tileCountOnIslandID = gs.isl11_noEntryTilesCount_text;
            default           -> throw new IllegalStateException("The islandID you have inserted is not contemplated. What you've inserted: " + islandID.getId());
        }

        return tileCountOnIslandID;
    }

    /**
     * Gets the Text (ID) of the noEntryTileCount on a specific Island
     * @param gs The gameSceneHandler
     * @param islandPosition An int representing the islandNumber of the island where the noEntryTileCount is located
     * @return The Text correspondent to the desired noEntryTileCount
     */
    public static Text gsFindNoEntryTileCountOnIslandID (GameSceneHandler gs, int islandPosition) {
        ImageView islandID = gsFindIslandID(gs, islandPosition);
        return gsFindNoEntryTileCountOnIslandID(gs, islandID);
    }

    // endregion IslandNoEntryTileCounterID

    // endregion IslandNoEntryTileID

    // region IslandMotherNatureID

    /**
     * Gets the ImageView (ID) of MotherNature on a specific Island
     * @param gs The gameSceneHandler
     * @param islandID An ImageView correspondent to the islandID of the island from which we need
     *                 to retrive the MotherNature ImageView
     * @return The ImageView of motherNature on the desired island
     */
    public static ImageView gsFindMotherNatureOnIslandID (GameSceneHandler gs, ImageView islandID) {
        ImageView motherNatureOnIslandID = null;

        switch(islandID.getId()) {
            case "isl0_img"   -> motherNatureOnIslandID = gs.isl0_motherNature_img;
            case "isl1_img"   -> motherNatureOnIslandID = gs.isl1_motherNature_img;
            case "isl2_img"   -> motherNatureOnIslandID = gs.isl2_motherNature_img;
            case "isl3_img"   -> motherNatureOnIslandID = gs.isl3_motherNature_img;
            case "isl4_img"   -> motherNatureOnIslandID = gs.isl4_motherNature_img;
            case "isl5_img"   -> motherNatureOnIslandID = gs.isl5_motherNature_img;
            case "isl6_img"   -> motherNatureOnIslandID = gs.isl6_motherNature_img;
            case "isl7_img"   -> motherNatureOnIslandID = gs.isl7_motherNature_img;
            case "isl8_img"   -> motherNatureOnIslandID = gs.isl8_motherNature_img;
            case "isl9_img"   -> motherNatureOnIslandID = gs.isl9_motherNature_img;
            case "isl10_img"  -> motherNatureOnIslandID = gs.isl10_motherNature_img;
            case "isl11_img"  -> motherNatureOnIslandID = gs.isl11_motherNature_img;
            default -> throw new IllegalStateException("The islandID you have inserted is not contemplated. What you've inserted: " + islandID.getId());
        }

        return motherNatureOnIslandID;
    }

    /**
     * Gets the ImageView (ID) of MotherNature on a specific Island
     * @param gs The gameSceneHandler
     * @param islandPosition An int representing the islandNumber of the island from which we need
     *                       to retrive the MotherNature ImageView
     * @return The ImageView of motherNature on the desired island
     */
    public static ImageView gsFindMotherNatureOnIslandID (GameSceneHandler gs, int islandPosition) {
        ImageView islandID = gsFindIslandID(gs, islandPosition);
        return gsFindMotherNatureOnIslandID(gs, islandID);
    }

    // endregion IslandMotherNatureID

    // endregion IslandID

    // region SchoolBoardID

    /**
     * GS Method that given the position of a student in the dining room, returns the corresponding id
     * @param gs the GameSceneHandler
     * @param studentEntrancePosition the student position
     * @return the corresponding id
     */
    public static ImageView gsFindStudentEntranceID (GameSceneHandler gs, int studentEntrancePosition) {
        ImageView studentEntranceID = null;
        switch (studentEntrancePosition) {
            case 0  -> studentEntranceID = gs.E_student0_img;
            case 1  -> studentEntranceID = gs.E_student1_img;
            case 2  -> studentEntranceID = gs.E_student2_img;
            case 3  -> studentEntranceID = gs.E_student3_img;
            case 4  -> studentEntranceID = gs.E_student4_img;
            case 5  -> studentEntranceID = gs.E_student5_img;
            case 6  -> studentEntranceID = gs.E_student6_img;
            case 7  -> studentEntranceID = gs.E_student7_img;
            case 8  -> studentEntranceID = gs.E_student8_img;
            default -> throw new IllegalStateException("The studentEntrancePosition must be between 0 and 8, has been inserted: " + studentEntrancePosition);
        }

        return studentEntranceID;
    }

    public static AnchorPane gsFindDiningRoomTablePaneID (GameSceneHandler gs, Color color) {
        AnchorPane tablePaneID = null;

        switch (color) {
            case GREEN  -> tablePaneID = gs.greenDiningRoom_pane;
            case RED    -> tablePaneID = gs.redDiningRoom_pane;
            case YELLOW -> tablePaneID = gs.yellowDiningRoom_pane;
            case PINK   -> tablePaneID = gs.pinkDiningRoom_pane;
            case BLUE   -> tablePaneID = gs.blueDiningRoom_pane;
        }

        return tablePaneID;
    }

    /**
     * GS Method that given the Color and the position of a student in the dining room, returns
     * the corresponding id
     * @param gs the GameSceneHandler
     * @param color the student's color
     * @param studentDRPosition the student position
     * @return the corresponding id
     */
    public static ImageView gsFindStudentDiningRoomID (GameSceneHandler gs, Color color, int studentDRPosition) {
        ImageView studentDiningRoomID = null;
        switch (color) {

            case GREEN -> {
                switch (studentDRPosition) {
                    case 0 -> studentDiningRoomID = gs.greenDR_student0_img;
                    case 1 -> studentDiningRoomID = gs.greenDR_student1_img;
                    case 2 -> studentDiningRoomID = gs.greenDR_student2_img;
                    case 3 -> studentDiningRoomID = gs.greenDR_student3_img;
                    case 4 -> studentDiningRoomID = gs.greenDR_student4_img;
                    case 5 -> studentDiningRoomID = gs.greenDR_student5_img;
                    case 6 -> studentDiningRoomID = gs.greenDR_student6_img;
                    case 7 -> studentDiningRoomID = gs.greenDR_student7_img;
                    case 8 -> studentDiningRoomID = gs.greenDR_student8_img;
                    case 9 -> studentDiningRoomID = gs.greenDR_student9_img;
                    default -> throw new IllegalStateException("The studentDRPosition must be between 0 and 9, has been inserted: " + studentDRPosition);
                }
            }

            case RED -> {
                switch (studentDRPosition) {
                    case 0 -> studentDiningRoomID = gs.redDR_student0_img;
                    case 1 -> studentDiningRoomID = gs.redDR_student1_img;
                    case 2 -> studentDiningRoomID = gs.redDR_student2_img;
                    case 3 -> studentDiningRoomID = gs.redDR_student3_img;
                    case 4 -> studentDiningRoomID = gs.redDR_student4_img;
                    case 5 -> studentDiningRoomID = gs.redDR_student5_img;
                    case 6 -> studentDiningRoomID = gs.redDR_student6_img;
                    case 7 -> studentDiningRoomID = gs.redDR_student7_img;
                    case 8 -> studentDiningRoomID = gs.redDR_student8_img;
                    case 9 -> studentDiningRoomID = gs.redDR_student9_img;
                    default -> throw new IllegalStateException("The studentDRPosition must be between 0 and 9, has been inserted: " + studentDRPosition);
                }
            }

            case YELLOW -> {
                switch (studentDRPosition) {
                    case 0 -> studentDiningRoomID = gs.yellowDR_student0_img;
                    case 1 -> studentDiningRoomID = gs.yellowDR_student1_img;
                    case 2 -> studentDiningRoomID = gs.yellowDR_student2_img;
                    case 3 -> studentDiningRoomID = gs.yellowDR_student3_img;
                    case 4 -> studentDiningRoomID = gs.yellowDR_student4_img;
                    case 5 -> studentDiningRoomID = gs.yellowDR_student5_img;
                    case 6 -> studentDiningRoomID = gs.yellowDR_student6_img;
                    case 7 -> studentDiningRoomID = gs.yellowDR_student7_img;
                    case 8 -> studentDiningRoomID = gs.yellowDR_student8_img;
                    case 9 -> studentDiningRoomID = gs.yellowDR_student9_img;
                    default -> throw new IllegalStateException("The studentDRPosition must be between 0 and 9, has been inserted: " + studentDRPosition);
                }
            }

            case PINK -> {
                switch (studentDRPosition) {
                    case 0 -> studentDiningRoomID = gs.pinkDR_student0_img;
                    case 1 -> studentDiningRoomID = gs.pinkDR_student1_img;
                    case 2 -> studentDiningRoomID = gs.pinkDR_student2_img;
                    case 3 -> studentDiningRoomID = gs.pinkDR_student3_img;
                    case 4 -> studentDiningRoomID = gs.pinkDR_student4_img;
                    case 5 -> studentDiningRoomID = gs.pinkDR_student5_img;
                    case 6 -> studentDiningRoomID = gs.pinkDR_student6_img;
                    case 7 -> studentDiningRoomID = gs.pinkDR_student7_img;
                    case 8 -> studentDiningRoomID = gs.pinkDR_student8_img;
                    case 9 -> studentDiningRoomID = gs.pinkDR_student9_img;
                    default -> throw new IllegalStateException("The studentDRPosition must be between 0 and 9, has been inserted: " + studentDRPosition);
                }
            }

            case BLUE -> {
                switch (studentDRPosition) {
                    case 0 -> studentDiningRoomID = gs.blueDR_student0_img;
                    case 1 -> studentDiningRoomID = gs.blueDR_student1_img;
                    case 2 -> studentDiningRoomID = gs.blueDR_student2_img;
                    case 3 -> studentDiningRoomID = gs.blueDR_student3_img;
                    case 4 -> studentDiningRoomID = gs.blueDR_student4_img;
                    case 5 -> studentDiningRoomID = gs.blueDR_student5_img;
                    case 6 -> studentDiningRoomID = gs.blueDR_student6_img;
                    case 7 -> studentDiningRoomID = gs.blueDR_student7_img;
                    case 8 -> studentDiningRoomID = gs.blueDR_student8_img;
                    case 9 -> studentDiningRoomID = gs.blueDR_student9_img;
                    default -> throw new IllegalStateException("The studentDRPosition must be between 0 and 9, has been inserted: " + studentDRPosition);
                }
            }
        }

        return studentDiningRoomID;
    }

    /**
     * Gets the ImageView (ID) of a specific Professor on the player's GlobalProfessorTable
     * @param gs The gameSceneHandler
     * @param color The color of the professor we want to find the ImageView of
     * @return The ImageView correspondent to the desired professor
     */
    public static ImageView gsFindProfessorOnProfTableID (GameSceneHandler gs, Color color) {
        ImageView professorOnProfTableID = null;

        switch (color) {
            case GREEN  -> professorOnProfTableID = gs.greenProfessor_img;
            case RED    -> professorOnProfTableID = gs.redProfessor_img;
            case YELLOW -> professorOnProfTableID = gs.yellowProfessor_img;
            case PINK   -> professorOnProfTableID = gs.pinkProfessor_img;
            case BLUE   -> professorOnProfTableID = gs.blueProfessor_img;
        }

        return professorOnProfTableID;
    }

    /**
     * Gets the ImageView (ID) of a specific tower on the player SchoolBoard
     * @param gs The gameSceneHandler
     * @param towerPosition An int representing the index of the tower in the SchoolBoard
     * @return The ImageView correspondent to the desired tower
     *
     */
    public static ImageView gsFindSchoolBoardTowerID (GameSceneHandler gs, int towerPosition) {
        ImageView schoolBoardTowerID = null;

        switch (towerPosition) {
            case 0  -> schoolBoardTowerID = gs.tower0_img;
            case 1  -> schoolBoardTowerID = gs.tower1_img;
            case 2  -> schoolBoardTowerID = gs.tower2_img;
            case 3  -> schoolBoardTowerID = gs.tower3_img;
            case 4  -> schoolBoardTowerID = gs.tower4_img;
            case 5  -> schoolBoardTowerID = gs.tower5_img;
            case 6  -> schoolBoardTowerID = gs.tower6_img;
            case 7  -> schoolBoardTowerID = gs.tower7_img;
            default -> throw new IllegalStateException("The towerPosition must be between 0 and 7. You've inserted: " + towerPosition);
        }

        return schoolBoardTowerID;
    }

    // endregion SchoolBoardID

    // region AssistantCardID

    /**
     * Gets the ImageView (ID) of a specific AssistantCard
     * @param gs The gameSceneHandler
     * @param assistantCardValue An int representing of the assistantCardValue the assistantCard of which we need to find the ImageView
     * @return The ImageView correspondent to the desired assistantCard
     */
    public static ImageView gsFindAssistantCardID (GameSceneHandler gs, int assistantCardValue) {
        return switch (assistantCardValue) {
            case 1  -> gs.assistant1_game;
            case 2  -> gs.assistant2_game;
            case 3  -> gs.assistant3_game;
            case 4  -> gs.assistant4_game;
            case 5  -> gs.assistant5_game;
            case 6  -> gs.assistant6_game;
            case 7  -> gs.assistant7_game;
            case 8  -> gs.assistant8_game;
            case 9  -> gs.assistant9_game;
            case 10 -> gs.assistant10_game;
            default -> throw new IllegalStateException("The assistantCard cardValue must be between 1 and 10. You've inserted: " + assistantCardValue);
        };
    }

    /**
     * Gets the ImageView (ID) of a specific AssistantCard
     * @param gs The gameSceneHandler
     * @param assistantCard The assistantCard of which we need to find the ImageView
     * @return The ImageView correspondent to the desired assistantCard
     */
    public static ImageView gsFindAssistantCardID (GameSceneHandler gs, AssistantCard assistantCard) {
        int assistantCardValue = assistantCard.cardValue();
        return gsFindAssistantCardID(gs, assistantCardValue);
    }

    // endregion AssistantCardID

    // region CharacterCardID

    /**
     * Gets the ImageView (ID) of a specific characterCard on the characterCards_pane
     * @param gs The gameSceneHandler
     * @param characterCardPosition An integer representing the position of the characterCard on the characterCards_pane
     * @return The ImageView correspondent to the desired characterCard
     */
    public static ImageView gsFindCharacterCardImageID (GameSceneHandler gs, int characterCardPosition) {
        ImageView characterCardImageID = null;

        switch (characterCardPosition) {
            case 0  -> characterCardImageID = gs.characterCard0_img;
            case 1  -> characterCardImageID = gs.characterCard1_img;
            case 2  -> characterCardImageID = gs.characterCard2_img;
            default -> throw new IllegalStateException("The characterCardPosition must be between 0 and 2. You've inserted: " + characterCardPosition);
        }

        return characterCardImageID;
    }

    // region CharacterCardCoinID

    /**
     * Gets the ImageView (ID) of the characterCardCoin  of a specific characterCard on the characterCards_pane
     * @param gs The gameSceneHandler
     * @param characterCardImageID An ImageView representing the ID of the characterCard on the characterCards_pane
     * @return The ImageView correspondent to the desired characterCardCoin
     */
    public static ImageView gsFindCharacterCardCoinID (GameSceneHandler gs, ImageView characterCardImageID) {
        ImageView characterCardCoinID = null;

        switch (characterCardImageID.getId()) {
            case "characterCard0_img" -> characterCardCoinID = gs.CC0_hasCoin_img;
            case "characterCard1_img" -> characterCardCoinID = gs.CC1_hasCoin_img;
            case "characterCard2_img" -> characterCardCoinID = gs.CC2_hasCoin_img;
            default                   -> throw new IllegalStateException("The characterCardImageID you have inserted is not contemplated. You've inserted: " + characterCardImageID.getId());
        }

        return characterCardCoinID;
    }

    /**
     * Gets the ImageView (ID) of the characterCardCoin  of a specific characterCard on the characterCards_pane
     * @param gs The gameSceneHandler
     * @param characterCardPosition An integer representing the position of the characterCard on the characterCards_pane
     * @return The ImageView correspondent to the desired characterCardCoin
     */
    public static ImageView gsFindCharacterCardCoinID (GameSceneHandler gs, int characterCardPosition) {
        ImageView characterCardImageID = gsFindCharacterCardImageID(gs, characterCardPosition);
        return gsFindCharacterCardCoinID(gs, characterCardImageID);
    }

    // endregion CharacterCardCoinID

    // region CharacterCardStudentID

    // region CharacterCardElementsRectangleID

    /**
     *
     * @param gs
     * @param characterCardImageID
     * @return
     */
    public static Rectangle gsFindCharacterCardRectangleElementsID(GameSceneHandler gs, ImageView characterCardImageID) {
        Rectangle characterCardRectangleID = null;

        switch (characterCardImageID.getId()) {
            case "characterCard0_img" -> characterCardRectangleID = gs.CC0_elem_background;
            case "characterCard1_img" -> characterCardRectangleID = gs.CC1_elem_background;
            case "characterCard2_img" -> characterCardRectangleID = gs.CC2_elem_background;
            default                   -> throw new IllegalStateException("The characterCardImageID you have inserted is not contemplated. You've inserted: " + characterCardImageID.getId());
        }

        return characterCardRectangleID;
    }

    /**
     *
     * @param gs
     * @param characterCardPosition
     * @return
     */
    public static Rectangle gsFindCharacterCardRectangleElementsID(GameSceneHandler gs, int characterCardPosition) {
        ImageView characterCardImageID = gsFindCharacterCardImageID(gs, characterCardPosition);
        return gsFindCharacterCardRectangleElementsID(gs, characterCardImageID);
    }

    // endregion CharacterCardElementsRectangleID

    // region CharacterCardElementsGridPaneID

    /**
     * Gets the GridPane (ID) of the characterCardPane of a specific characterCard on the characterCards_pane
     * @param gs The gameSceneHandler
     * @param characterCardImageID An ImageView representing the ID of the characterCard on the characterCards_pane
     * @return The GridPane correspondent to the desired characterCardPane
     */
    public static GridPane gsFindCharacterCardPaneElementsID(GameSceneHandler gs, ImageView characterCardImageID) {
        GridPane characterCardPaneID = null;

        switch (characterCardImageID.getId()) {
            case "characterCard0_img" -> characterCardPaneID = gs.CC0_gridPane;
            case "characterCard1_img" -> characterCardPaneID = gs.CC1_gridPane;
            case "characterCard2_img" -> characterCardPaneID = gs.CC2_gridPane;
            default                   -> throw new IllegalStateException("The characterCardImageID you have inserted is not contemplated. You've inserted: " + characterCardImageID.getId());
        }

        return characterCardPaneID;
    }

    /**
     * Gets the GridPane (ID) of the characterCardPane of a specific characterCard on the characterCards_pane
     * @param gs The gameSceneHandler
     * @param characterCardPosition An integer representing the position of the characterCard on the characterCards_pane
     * @return The GridPane correspondent to the desired characterCardPane
     */
    public static GridPane gsFindCharacterCardPaneElementsID(GameSceneHandler gs, int characterCardPosition) {
        ImageView characterCardImageID = gsFindCharacterCardImageID(gs, characterCardPosition);
        return gsFindCharacterCardPaneElementsID(gs, characterCardImageID);
    }

    // endregion CharacterCardElementsGridPaneID

    // region CharacterCardElementSingleID

    /**
     * Gets the ImageView (ID) of a characterCardStudent of a specific characterCard on the characterCards_pane
     * @param gs The gameSceneHandler
     * @param characterCardImageID An ImageView representing the ID of the characterCard on the characterCards_pane
     * @param elementPosOnCC An int representing the position of the student on the characterCard gridPane
     * @return The ImageView correspondent to the desired characterCardStudent
     */
    public static ImageView gsFindCharacterCardElementID(GameSceneHandler gs, ImageView characterCardImageID, int elementPosOnCC) {
        ImageView characterCardStudentID = null;

        switch (characterCardImageID.getId()) {

            case "characterCard0_img" -> {
                switch (elementPosOnCC) {
                    case 0  -> characterCardStudentID = gs.CC0_elem0_img;
                    case 1  -> characterCardStudentID = gs.CC0_elem1_img;
                    case 2  -> characterCardStudentID = gs.CC0_elem2_img;
                    case 3  -> characterCardStudentID = gs.CC0_elem3_img;
                    case 4  -> characterCardStudentID = gs.CC0_elem4_img;
                    case 5  -> characterCardStudentID = gs.CC0_elem5_img;
                    default -> throw new IllegalStateException("The elementPosOnCC must be between 0 and 5. You've inserted: " + elementPosOnCC);
                }
            }

            case "characterCard1_img" -> {
                switch (elementPosOnCC) {
                    case 0  -> characterCardStudentID = gs.CC1_elem0_img;
                    case 1  -> characterCardStudentID = gs.CC1_elem1_img;
                    case 2  -> characterCardStudentID = gs.CC1_elem2_img;
                    case 3  -> characterCardStudentID = gs.CC1_elem3_img;
                    case 4  -> characterCardStudentID = gs.CC1_elem4_img;
                    case 5  -> characterCardStudentID = gs.CC1_elem5_img;
                    default -> throw new IllegalStateException("The elementPosOnCC must be between 0 and 5. You've inserted: " + elementPosOnCC);
                }
            }

            case "characterCard2_img" -> {
                switch (elementPosOnCC) {
                    case 0  -> characterCardStudentID = gs.CC2_elem0_img;
                    case 1  -> characterCardStudentID = gs.CC2_elem1_img;
                    case 2  -> characterCardStudentID = gs.CC2_elem2_img;
                    case 3  -> characterCardStudentID = gs.CC2_elem3_img;
                    case 4  -> characterCardStudentID = gs.CC2_elem4_img;
                    case 5  -> characterCardStudentID = gs.CC2_elem5_img;
                    default -> throw new IllegalStateException("The elementPosOnCC must be between 0 and 5. You've inserted: " + elementPosOnCC);
                }
            }
        }

        return characterCardStudentID;
    }

    /**
     * Gets the ImageView (ID) of a characterCardStudent of a specific characterCard on the characterCards_pane
     * @param gs The gameSceneHandler
     * @param characterCardPosition An integer representing the position of the characterCard on the characterCards_pane
     * @param elementPosOnCC An int representing the position of the student on the characterCard gridPane
     * @return The ImageView correspondent to the desired characterCardStudent
     */
    public static ImageView gsFindCharacterCardElementID(GameSceneHandler gs, int characterCardPosition, int elementPosOnCC) {
        ImageView characterCardImageID = gsFindCharacterCardImageID(gs, characterCardPosition);
        return gsFindCharacterCardElementID(gs, characterCardImageID, elementPosOnCC);

    }

    // endregion CharacterCardElementSingleID

    // endregion CharacterCardStudentID

    // endregion CharacterCardID

    // region CloudID

    /**
     * Gets the ImageView (ID) of a specific CloudTile
     * @param gs The gameSceneHandler
     * @param cloudPosition An int representing the cloudNumber of the specified cloud
     * @return The ImageView correspondent to the desired CloudTile
     */
    public static ImageView gsFindCloudImageID (GameSceneHandler gs, int cloudPosition) {
        ImageView cloudImageID = null;

        switch (cloudPosition) {
            case 0  -> cloudImageID = gs.cloud0_img;
            case 1  -> cloudImageID = gs.cloud1_img;
            case 2  -> cloudImageID = gs.cloud2_img;
            case 3  -> cloudImageID = gs.cloud3_img;
            default -> throw new IllegalStateException("The cloudPosition must be between 0 and 3. You've inserted: " + cloudPosition);
        }

        return cloudImageID;
    }

    /**
     * Gets the AnchorPane (ID) of a specific CloudTile
     * @param gs The gameSceneHandler
     * @param cloudPosition An int representing the cloudNumber of the specified cloud
     * @return The AnchorPane correspondent to the desired CloudTile's anchorPane
     */
    public static AnchorPane gsFindCloudAnchorPaneID (GameSceneHandler gs, int cloudPosition) {
        ImageView cloudImageID = gsFindCloudImageID (gs, cloudPosition);
        return gsFindCloudAnchorPaneID(gs, cloudImageID);
    }

    /**
     * Gets the AnchorPane (ID) of a specific CloudTile
     * @param gs The gameSceneHandler
     * @param cloudImageID An ImageView correspondent to the cloudTileID of the specified cloud
     * @return The AnchorPane correspondent to the desired CloudTile's anchorPane
     */
    public static AnchorPane gsFindCloudAnchorPaneID (GameSceneHandler gs, ImageView cloudImageID) {
        AnchorPane cloudAnchorPaneID = null;

        switch (cloudImageID.getId()) {
            case "cloud0_img" -> cloudAnchorPaneID = gs.cloudTile0_pane;
            case "cloud1_img" -> cloudAnchorPaneID = gs.cloudTile1_pane;
            case "cloud2_img" -> cloudAnchorPaneID = gs.cloudTile2_pane;
            case "cloud3_img" -> cloudAnchorPaneID = gs.cloudTile3_pane;
            default -> throw new IllegalStateException("The cloudImageID you have inserted is not contemplated. You've inserted: " + cloudImageID.getId());
        }

        return cloudAnchorPaneID;
    }

    // region CloudStudentID

    /**
     * Gets the ImageView (ID) of the tower on a specific Island
     * @param gs The gameSceneHandler
     * @param cloudImageID An ImageView correspondent to the cloudTileID of the specified cloud
     * @param studentPositionOnCloud An int representing the studentPosition of the specific students on
     *                               the specified cloud
     * @return The ImageView correspondent to the desired student on the CloudTile
     */
    public static ImageView gsFindCloudStudentID (GameSceneHandler gs, ImageView cloudImageID, int studentPositionOnCloud) {
        ImageView cloudStudentID = null;

        switch (cloudImageID.getId()) {
            case "cloud0_img" -> {
                switch (studentPositionOnCloud) {
                    case 0  -> cloudStudentID = gs.CT0_student0_img;
                    case 1  -> cloudStudentID = gs.CT0_student1_img;
                    case 2  -> cloudStudentID = gs.CT0_student2_img;
                    case 3  -> cloudStudentID = gs.CT0_student3_img;
                    default -> throw new IllegalStateException("The studentPositionOnCloud must be between 0 and 3. You've inserted: " + studentPositionOnCloud);
                }
            }

            case "cloud1_img" -> {
                switch (studentPositionOnCloud) {
                    case 0  -> cloudStudentID = gs.CT1_student0_img;
                    case 1  -> cloudStudentID = gs.CT1_student1_img;
                    case 2  -> cloudStudentID = gs.CT1_student2_img;
                    case 3  -> cloudStudentID = gs.CT1_student3_img;
                    default -> throw new IllegalStateException("The studentPositionOnCloud must be between 0 and 3. You've inserted: " + studentPositionOnCloud);
                }
            }

            case "cloud2_img" -> {
                switch (studentPositionOnCloud) {
                    case 0  -> cloudStudentID = gs.CT2_student0_img;
                    case 1  -> cloudStudentID = gs.CT2_student1_img;
                    case 2  -> cloudStudentID = gs.CT2_student2_img;
                    case 3  -> cloudStudentID = gs.CT2_student3_img;
                    default -> throw new IllegalStateException("The studentPositionOnCloud must be between 0 and 3. You've inserted: " + studentPositionOnCloud);
                }
            }

            case "cloud3_img" -> {
                switch (studentPositionOnCloud) {
                    case 0  -> cloudStudentID = gs.CT3_student0_img;
                    case 1  -> cloudStudentID = gs.CT3_student1_img;
                    case 2  -> cloudStudentID = gs.CT3_student2_img;
                    case 3  -> cloudStudentID = gs.CT3_student3_img;
                    default -> throw new IllegalStateException("The studentPositionOnCloud must be between 0 and 3. You've inserted: " + studentPositionOnCloud);
                }
            }
        }

        return cloudStudentID;
    }

    /**
     * Gets the ImageView (ID) of the
     * tower on a specific Island
     * @param gs The gameSceneHandler
     * @param cloudPosition An int representing the cloudNumber of the specified cloud
     * @param studentPositionOnCloud An int representing the studentPosition of the specific students on
     *                               the specified cloud
     * @return The ImageView correspondent to the desired student on the CloudTile
     */
    public static ImageView gsFindCloudStudentID (GameSceneHandler gs, int cloudPosition, int studentPositionOnCloud) {
        ImageView cloudImageID = gsFindCloudImageID (gs, cloudPosition);
        return gsFindCloudStudentID(gs, cloudImageID, studentPositionOnCloud);
    }

    // endregion CloudStudentID

    // endregion CloudID

    // endregion GameSceneID

    // region PlayersSchoolBoardID

    // region PlayerPaneID

    public static AnchorPane psbFindPlayerPaneID (PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = null;

        switch (playerIndex) {
            case 0  -> playerPaneID = psb.p0_h0_pane;
            case 1  -> playerPaneID = psb.p1_h1_pane;
            case 2  -> playerPaneID = psb.p2_h2_pane;
            case 3  -> playerPaneID = psb.p3_h3_pane;
            default -> throw new IllegalStateException("The playerIndex must be between 0 and 3. You've inserted: " + playerIndex);
        }

        return playerPaneID;
    }

    // endregion PlayerPaneID

    // region AdditionalInfo

    public static AnchorPane psbFindPlayerAdditionalInfoPaneID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID) {
        AnchorPane playerAddInfoPaneID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> playerAddInfoPaneID = psb.p0_addInfo_pane;
            case "p1_h1_pane" -> playerAddInfoPaneID = psb.p1_addInfo_pane;
            case "p2_h2_pane" -> playerAddInfoPaneID = psb.p2_addInfo_pane;
            case "p3_h3_pane" -> playerAddInfoPaneID = psb.p3_addInfo_pane;
            default           -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return playerAddInfoPaneID;
    }

    public static AnchorPane psbFindPlayerAdditionalInfoPaneID (PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindPlayerAdditionalInfoPaneID(psb, playerPaneID);
    }

    // region PlayerCoinID

    // region PlayerCoinPaneID

    public static AnchorPane psbFindPlayerCoinPaneID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID) {
        AnchorPane playerCoinPaneID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> playerCoinPaneID = psb.p0_coin_pane;
            case "p1_h1_pane" -> playerCoinPaneID = psb.p1_coin_pane;
            case "p2_h2_pane" -> playerCoinPaneID = psb.p2_coin_pane;
            case "p3_h3_pane" -> playerCoinPaneID = psb.p3_coin_pane;
            default           -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return playerCoinPaneID;
    }

    public static AnchorPane psbFindPlayerCoinPaneID (PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindPlayerCoinPaneID(psb, playerPaneID);
    }

    // endregion PlayerCoinPaneID

    // region PlayerCoinImageID

    public static ImageView psbFindPlayerCoinImageID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID) {
        ImageView playerCoinImageID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> playerCoinImageID = psb.p0_coin_img;
            case "p1_h1_pane" -> playerCoinImageID = psb.p1_coin_img;
            case "p2_h2_pane" -> playerCoinImageID = psb.p2_coin_img;
            case "p3_h3_pane" -> playerCoinImageID = psb.p3_coin_img;
            default           -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return playerCoinImageID;
    }

    public static ImageView psbFindPlayerCoinImageID (PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindPlayerCoinImageID(psb, playerPaneID);
    }

    // endregion PlayerCoinImageID

    // region PlayerCoinCountID

    public static Text psbFindPlayerCoinCountID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID) {
        Text playerCoinCountID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> playerCoinCountID = psb.p0_coinsCount_text;
            case "p1_h1_pane" -> playerCoinCountID = psb.p1_coinsCount_text;
            case "p2_h2_pane" -> playerCoinCountID = psb.p2_coinsCount_text;
            case "p3_h3_pane" -> playerCoinCountID = psb.p3_coinsCount_text;
            default           -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return playerCoinCountID;
    }

    public static Text psbFindPlayerCoinCountID (PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindPlayerCoinCountID(psb, playerPaneID);
    }

    // endregion PlayerCoinCountID

    // endregion PlayerCoinID

    // region LastPlayerCardID

    public static ImageView psbFindPlayerLastPlayedCardID(PlayersSchoolBoardHandler psb, AnchorPane playerPaneID) {
        ImageView playerLastPlayedCardID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> playerLastPlayedCardID = psb.p0_lastAssistant;
            case "p1_h1_pane" -> playerLastPlayedCardID = psb.p1_lastAssistant;
            case "p2_h2_pane" -> playerLastPlayedCardID = psb.p2_lastAssistant;
            case "p3_h3_pane" -> playerLastPlayedCardID = psb.p3_lastAssistant;
            default           -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return playerLastPlayedCardID;
    }

    public static ImageView psbFindPlayerLastPlayedCardID(PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindPlayerLastPlayedCardID(psb, playerPaneID);
    }

    // endregion LastPlayerCardID

    // region UsernameID

    public static Text psbFindPlayerUsernameID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID) {
        Text playerUsernameID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> playerUsernameID = psb.p0_username_text;
            case "p1_h1_pane" -> playerUsernameID = psb.p1_username_text;
            case "p2_h2_pane" -> playerUsernameID = psb.p2_username_text;
            case "p3_h3_pane" -> playerUsernameID = psb.p3_username_text;
            default           -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return playerUsernameID;
    }

    public static Text psbFindPlayerUsernameID (PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindPlayerUsernameID(psb, playerPaneID);
    }

    // endregion UsernameID

    // endregion AdditionalInfo

    // region TowerID

    // region TowerPaneID

    public static AnchorPane psbFindTowerPaneID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID) {
        AnchorPane towerPaneID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> towerPaneID = psb.p0_towers_pane;
            case "p1_h1_pane" -> towerPaneID = psb.p1_towers_pane;
            case "p2_h2_pane" -> towerPaneID = psb.p2_towers_pane;
            case "p3_h3_pane" -> towerPaneID = psb.p3_towers_pane;
            default           -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return towerPaneID;
    }

    public static AnchorPane psbFindTowerPaneID (PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindTowerPaneID(psb, playerPaneID);
    }

    // endregion TowerPaneID

    // region TowerSingleImageID

    public static ImageView psbFindTowerSingleImageID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID, int towerPosition) {
        ImageView towerSingleImageID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> {
                switch (towerPosition) {
                    case 0 -> towerSingleImageID = psb.p0_tower0_img;
                    case 1 -> towerSingleImageID = psb.p0_tower1_img;
                    case 2 -> towerSingleImageID = psb.p0_tower2_img;
                    case 3 -> towerSingleImageID = psb.p0_tower3_img;
                    case 4 -> towerSingleImageID = psb.p0_tower4_img;
                    case 5 -> towerSingleImageID = psb.p0_tower5_img;
                    case 6 -> towerSingleImageID = psb.p0_tower6_img;
                    case 7 -> towerSingleImageID = psb.p0_tower7_img;
                    default -> throw new IllegalStateException("The towerPosition must be between 0 and 7. You've inserted: " + towerPosition);
                }
            }

            case "p1_h1_pane" -> {
                switch (towerPosition) {
                    case 0 -> towerSingleImageID = psb.p1_tower0_img;
                    case 1 -> towerSingleImageID = psb.p1_tower1_img;
                    case 2 -> towerSingleImageID = psb.p1_tower2_img;
                    case 3 -> towerSingleImageID = psb.p1_tower3_img;
                    case 4 -> towerSingleImageID = psb.p1_tower4_img;
                    case 5 -> towerSingleImageID = psb.p1_tower5_img;
                    case 6 -> towerSingleImageID = psb.p1_tower6_img;
                    case 7 -> towerSingleImageID = psb.p1_tower7_img;
                    default -> throw new IllegalStateException("The towerPosition must be between 0 and 7. You've inserted: " + towerPosition);
                }
            }

            case "p2_h2_pane" -> {
                switch (towerPosition) {
                    case 0 -> towerSingleImageID = psb.p2_tower0_img;
                    case 1 -> towerSingleImageID = psb.p2_tower1_img;
                    case 2 -> towerSingleImageID = psb.p2_tower2_img;
                    case 3 -> towerSingleImageID = psb.p2_tower3_img;
                    case 4 -> towerSingleImageID = psb.p2_tower4_img;
                    case 5 -> towerSingleImageID = psb.p2_tower5_img;
                    case 6 -> towerSingleImageID = psb.p2_tower6_img;
                    case 7 -> towerSingleImageID = psb.p2_tower7_img;
                    default -> throw new IllegalStateException("The towerPosition must be between 0 and 7. You've inserted: " + towerPosition);
                }
            }

            case "p3_h3_pane" -> {
                switch (towerPosition) {
                    case 0 -> towerSingleImageID = psb.p3_tower0_img;
                    case 1 -> towerSingleImageID = psb.p3_tower1_img;
                    case 2 -> towerSingleImageID = psb.p3_tower2_img;
                    case 3 -> towerSingleImageID = psb.p3_tower3_img;
                    case 4 -> towerSingleImageID = psb.p3_tower4_img;
                    case 5 -> towerSingleImageID = psb.p3_tower5_img;
                    case 6 -> towerSingleImageID = psb.p3_tower6_img;
                    case 7 -> towerSingleImageID = psb.p3_tower7_img;
                    default -> throw new IllegalStateException("The towerPosition must be between 0 and 7. You've inserted: " + towerPosition);
                }
            }

            default -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return towerSingleImageID;
    }

    public static ImageView psbFindTowerSingleImageID (PlayersSchoolBoardHandler psb, int playerIndex, int towerPosition) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindTowerSingleImageID(psb, playerPaneID, towerPosition);
    }

    // endregion TowerSingleImageID

    // endregion TowerID

    // region DiningRoomID

    // region DiningRoomPaneID

    public static AnchorPane psbFindDiningRoomPaneID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID) {
        AnchorPane diningRoomPaneID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> diningRoomPaneID = psb.p0_diningRoom_pane;
            case "p1_h1_pane" -> diningRoomPaneID = psb.p1_diningRoom_pane;
            case "p2_h2_pane" -> diningRoomPaneID = psb.p2_diningRoom_pane;
            case "p3_h3_pane" -> diningRoomPaneID = psb.p3_diningRoom_pane;
            default           -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return diningRoomPaneID;
    }

    public static AnchorPane psbFindDiningRoomPaneID (PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindDiningRoomPaneID(psb, playerPaneID);
    }

    // endregion DiningRoomPaneID

    // region DiningRoomProfessorTableID

    // region DiningRoomProfessorTablePaneID

    public static AnchorPane psbFindProfessorTablePaneID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID) {
        AnchorPane professorTablePaneID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> professorTablePaneID = psb.p0_professorTable_pane;
            case "p1_h1_pane" -> professorTablePaneID = psb.p1_professorTable_pane;
            case "p2_h2_pane" -> professorTablePaneID = psb.p2_professorTable_pane;
            case "p3_h3_pane" -> professorTablePaneID = psb.p3_professorTable_pane;
            default           -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return professorTablePaneID;
    }

    public static AnchorPane psbFindProfessorTablePaneID (PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindProfessorTablePaneID(psb, playerPaneID);
    }

    // endregion DiningRoomProfessorTablePaneID

    // region DiningRoomProfessorSingleImageID

    public static ImageView psbFindProfessorSingleImageID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID, Color professorColor) {
        ImageView professorSingleImageID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> {
                switch (professorColor) {
                    case GREEN  -> professorSingleImageID = psb.p0_greenProfessor_img;
                    case RED    -> professorSingleImageID = psb.p0_redProfessor_img;
                    case YELLOW -> professorSingleImageID = psb.p0_yellowProfessor_img;
                    case PINK   -> professorSingleImageID = psb.p0_pinkProfessor_img;
                    case BLUE   -> professorSingleImageID = psb.p0_blueProfessor_img;
                }
            }

            case "p1_h1_pane" -> {
                switch (professorColor) {
                    case GREEN  -> professorSingleImageID = psb.p1_greenProfessor_img;
                    case RED    -> professorSingleImageID = psb.p1_redProfessor_img;
                    case YELLOW -> professorSingleImageID = psb.p1_yellowProfessor_img;
                    case PINK   -> professorSingleImageID = psb.p1_pinkProfessor_img;
                    case BLUE   -> professorSingleImageID = psb.p1_blueProfessor_img;
                }
            }

            case "p2_h2_pane" -> {
                switch (professorColor) {
                    case GREEN  -> professorSingleImageID = psb.p2_greenProfessor_img;
                    case RED    -> professorSingleImageID = psb.p2_redProfessor_img;
                    case YELLOW -> professorSingleImageID = psb.p2_yellowProfessor_img;
                    case PINK   -> professorSingleImageID = psb.p2_pinkProfessor_img;
                    case BLUE   -> professorSingleImageID = psb.p2_blueProfessor_img;
                }
            }

            case "p3_h3_pane" -> {
                switch (professorColor) {
                    case GREEN  -> professorSingleImageID = psb.p3_greenProfessor_img;
                    case RED    -> professorSingleImageID = psb.p3_redProfessor_img;
                    case YELLOW -> professorSingleImageID = psb.p3_yellowProfessor_img;
                    case PINK   -> professorSingleImageID = psb.p3_pinkProfessor_img;
                    case BLUE   -> professorSingleImageID = psb.p3_blueProfessor_img;
                }
            }

            default -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return professorSingleImageID;
    }

    public static ImageView psbFindProfessorSingleImageID (PlayersSchoolBoardHandler psb, int playerIndex, Color professorColor) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindProfessorSingleImageID(psb, playerPaneID, professorColor);
    }

    // endregion DiningRoomProfessorSingleImageID

    // endregion DiningRoomProfessorTableID

    // region DiningRoomTablePaneID

    public static AnchorPane psbFindDiningRoomSingleTablePaneID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID, Color tableColor) {
        AnchorPane diningRoomSingleTablePaneID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> {
                switch (tableColor) {
                    case GREEN  -> diningRoomSingleTablePaneID = psb.p0_greenDiningRoom_pane;
                    case RED    -> diningRoomSingleTablePaneID = psb.p0_redDiningRoom_pane;
                    case YELLOW -> diningRoomSingleTablePaneID = psb.p0_yellowDiningRoom_pane;
                    case PINK   -> diningRoomSingleTablePaneID = psb.p0_pinkDiningRoom_pane;
                    case BLUE   -> diningRoomSingleTablePaneID = psb.p0_blueDiningRoom_pane;
                }
            }

            case "p1_h1_pane" -> {
                switch (tableColor) {
                    case GREEN  -> diningRoomSingleTablePaneID = psb.p1_greenDiningRoom_pane;
                    case RED    -> diningRoomSingleTablePaneID = psb.p1_redDiningRoom_pane;
                    case YELLOW -> diningRoomSingleTablePaneID = psb.p1_yellowDiningRoom_pane;
                    case PINK   -> diningRoomSingleTablePaneID = psb.p1_pinkDiningRoom_pane;
                    case BLUE   -> diningRoomSingleTablePaneID = psb.p1_blueDiningRoom_pane;
                }
            }

            case "p2_h2_pane" -> {
                switch (tableColor) {
                    case GREEN  -> diningRoomSingleTablePaneID = psb.p2_greenDiningRoom_pane;
                    case RED    -> diningRoomSingleTablePaneID = psb.p2_redDiningRoom_pane;
                    case YELLOW -> diningRoomSingleTablePaneID = psb.p2_yellowDiningRoom_pane;
                    case PINK   -> diningRoomSingleTablePaneID = psb.p2_pinkDiningRoom_pane;
                    case BLUE   -> diningRoomSingleTablePaneID = psb.p2_blueDiningRoom_pane;
                }
            }

            case "p3_h3_pane" -> {
                switch (tableColor) {
                    case GREEN  -> diningRoomSingleTablePaneID = psb.p3_greenDiningRoom_pane;
                    case RED    -> diningRoomSingleTablePaneID = psb.p3_redDiningRoom_pane;
                    case YELLOW -> diningRoomSingleTablePaneID = psb.p3_yellowDiningRoom_pane;
                    case PINK   -> diningRoomSingleTablePaneID = psb.p3_pinkDiningRoom_pane;
                    case BLUE   -> diningRoomSingleTablePaneID = psb.p3_blueDiningRoom_pane;
                }
            }

            default -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return diningRoomSingleTablePaneID;
    }

    public static AnchorPane psbFindDiningRoomSingleTablePaneID (PlayersSchoolBoardHandler psb, int playerIndex, Color tableColor) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindDiningRoomSingleTablePaneID(psb, playerPaneID, tableColor);
    }

    // endregion DiningRoomTablePaneID

    // region DiningRoomStudentSingleImageID

    public static ImageView psbFindDiningRoomStudentSingleImageID (PlayersSchoolBoardHandler psb, AnchorPane singleTablePaneID, int studentPosition) {
        ImageView studentSingleImageID = null;

        switch (singleTablePaneID.getId()) {
            case "p0_greenDiningRoom_pane"  -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p0_greenStudent0_img;
                    case 1  -> studentSingleImageID = psb.p0_greenStudent1_img;
                    case 2  -> studentSingleImageID = psb.p0_greenStudent2_img;
                    case 3  -> studentSingleImageID = psb.p0_greenStudent3_img;
                    case 4  -> studentSingleImageID = psb.p0_greenStudent4_img;
                    case 5  -> studentSingleImageID = psb.p0_greenStudent5_img;
                    case 6  -> studentSingleImageID = psb.p0_greenStudent6_img;
                    case 7  -> studentSingleImageID = psb.p0_greenStudent7_img;
                    case 8  -> studentSingleImageID = psb.p0_greenStudent8_img;
                    case 9  -> studentSingleImageID = psb.p0_greenStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p0_redDiningRoom_pane"    -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p0_redStudent0_img;
                    case 1  -> studentSingleImageID = psb.p0_redStudent1_img;
                    case 2  -> studentSingleImageID = psb.p0_redStudent2_img;
                    case 3  -> studentSingleImageID = psb.p0_redStudent3_img;
                    case 4  -> studentSingleImageID = psb.p0_redStudent4_img;
                    case 5  -> studentSingleImageID = psb.p0_redStudent5_img;
                    case 6  -> studentSingleImageID = psb.p0_redStudent6_img;
                    case 7  -> studentSingleImageID = psb.p0_redStudent7_img;
                    case 8  -> studentSingleImageID = psb.p0_redStudent8_img;
                    case 9  -> studentSingleImageID = psb.p0_redStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p0_yellowDiningRoom_pane" -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p0_yellowStudent0_img;
                    case 1  -> studentSingleImageID = psb.p0_yellowStudent1_img;
                    case 2  -> studentSingleImageID = psb.p0_yellowStudent2_img;
                    case 3  -> studentSingleImageID = psb.p0_yellowStudent3_img;
                    case 4  -> studentSingleImageID = psb.p0_yellowStudent4_img;
                    case 5  -> studentSingleImageID = psb.p0_yellowStudent5_img;
                    case 6  -> studentSingleImageID = psb.p0_yellowStudent6_img;
                    case 7  -> studentSingleImageID = psb.p0_yellowStudent7_img;
                    case 8  -> studentSingleImageID = psb.p0_yellowStudent8_img;
                    case 9  -> studentSingleImageID = psb.p0_yellowStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p0_pinkDiningRoom_pane"   -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p0_pinkStudent0_img;
                    case 1  -> studentSingleImageID = psb.p0_pinkStudent1_img;
                    case 2  -> studentSingleImageID = psb.p0_pinkStudent2_img;
                    case 3  -> studentSingleImageID = psb.p0_pinkStudent3_img;
                    case 4  -> studentSingleImageID = psb.p0_pinkStudent4_img;
                    case 5  -> studentSingleImageID = psb.p0_pinkStudent5_img;
                    case 6  -> studentSingleImageID = psb.p0_pinkStudent6_img;
                    case 7  -> studentSingleImageID = psb.p0_pinkStudent7_img;
                    case 8  -> studentSingleImageID = psb.p0_pinkStudent8_img;
                    case 9  -> studentSingleImageID = psb.p0_pinkStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p0_blueDiningRoom_pane"   -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p0_blueStudent0_img;
                    case 1  -> studentSingleImageID = psb.p0_blueStudent1_img;
                    case 2  -> studentSingleImageID = psb.p0_blueStudent2_img;
                    case 3  -> studentSingleImageID = psb.p0_blueStudent3_img;
                    case 4  -> studentSingleImageID = psb.p0_blueStudent4_img;
                    case 5  -> studentSingleImageID = psb.p0_blueStudent5_img;
                    case 6  -> studentSingleImageID = psb.p0_blueStudent6_img;
                    case 7  -> studentSingleImageID = psb.p0_blueStudent7_img;
                    case 8  -> studentSingleImageID = psb.p0_blueStudent8_img;
                    case 9  -> studentSingleImageID = psb.p0_blueStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p1_greenDiningRoom_pane"  -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p1_greenStudent0_img;
                    case 1  -> studentSingleImageID = psb.p1_greenStudent1_img;
                    case 2  -> studentSingleImageID = psb.p1_greenStudent2_img;
                    case 3  -> studentSingleImageID = psb.p1_greenStudent3_img;
                    case 4  -> studentSingleImageID = psb.p1_greenStudent4_img;
                    case 5  -> studentSingleImageID = psb.p1_greenStudent5_img;
                    case 6  -> studentSingleImageID = psb.p1_greenStudent6_img;
                    case 7  -> studentSingleImageID = psb.p1_greenStudent7_img;
                    case 8  -> studentSingleImageID = psb.p1_greenStudent8_img;
                    case 9  -> studentSingleImageID = psb.p1_greenStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p1_redDiningRoom_pane"    -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p1_redStudent0_img;
                    case 1  -> studentSingleImageID = psb.p1_redStudent1_img;
                    case 2  -> studentSingleImageID = psb.p1_redStudent2_img;
                    case 3  -> studentSingleImageID = psb.p1_redStudent3_img;
                    case 4  -> studentSingleImageID = psb.p1_redStudent4_img;
                    case 5  -> studentSingleImageID = psb.p1_redStudent5_img;
                    case 6  -> studentSingleImageID = psb.p1_redStudent6_img;
                    case 7  -> studentSingleImageID = psb.p1_redStudent7_img;
                    case 8  -> studentSingleImageID = psb.p1_redStudent8_img;
                    case 9  -> studentSingleImageID = psb.p1_redStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p1_yellowDiningRoom_pane" -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p1_yellowStudent0_img;
                    case 1  -> studentSingleImageID = psb.p1_yellowStudent1_img;
                    case 2  -> studentSingleImageID = psb.p1_yellowStudent2_img;
                    case 3  -> studentSingleImageID = psb.p1_yellowStudent3_img;
                    case 4  -> studentSingleImageID = psb.p1_yellowStudent4_img;
                    case 5  -> studentSingleImageID = psb.p1_yellowStudent5_img;
                    case 6  -> studentSingleImageID = psb.p1_yellowStudent6_img;
                    case 7  -> studentSingleImageID = psb.p1_yellowStudent7_img;
                    case 8  -> studentSingleImageID = psb.p1_yellowStudent8_img;
                    case 9  -> studentSingleImageID = psb.p1_yellowStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p1_pinkDiningRoom_pane"   -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p1_pinkStudent0_img;
                    case 1  -> studentSingleImageID = psb.p1_pinkStudent1_img;
                    case 2  -> studentSingleImageID = psb.p1_pinkStudent2_img;
                    case 3  -> studentSingleImageID = psb.p1_pinkStudent3_img;
                    case 4  -> studentSingleImageID = psb.p1_pinkStudent4_img;
                    case 5  -> studentSingleImageID = psb.p1_pinkStudent5_img;
                    case 6  -> studentSingleImageID = psb.p1_pinkStudent6_img;
                    case 7  -> studentSingleImageID = psb.p1_pinkStudent7_img;
                    case 8  -> studentSingleImageID = psb.p1_pinkStudent8_img;
                    case 9  -> studentSingleImageID = psb.p1_pinkStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p1_blueDiningRoom_pane"   -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p1_blueStudent0_img;
                    case 1  -> studentSingleImageID = psb.p1_blueStudent1_img;
                    case 2  -> studentSingleImageID = psb.p1_blueStudent2_img;
                    case 3  -> studentSingleImageID = psb.p1_blueStudent3_img;
                    case 4  -> studentSingleImageID = psb.p1_blueStudent4_img;
                    case 5  -> studentSingleImageID = psb.p1_blueStudent5_img;
                    case 6  -> studentSingleImageID = psb.p1_blueStudent6_img;
                    case 7  -> studentSingleImageID = psb.p1_blueStudent7_img;
                    case 8  -> studentSingleImageID = psb.p1_blueStudent8_img;
                    case 9  -> studentSingleImageID = psb.p1_blueStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p2_greenDiningRoom_pane"  -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p2_greenStudent0_img;
                    case 1  -> studentSingleImageID = psb.p2_greenStudent1_img;
                    case 2  -> studentSingleImageID = psb.p2_greenStudent2_img;
                    case 3  -> studentSingleImageID = psb.p2_greenStudent3_img;
                    case 4  -> studentSingleImageID = psb.p2_greenStudent4_img;
                    case 5  -> studentSingleImageID = psb.p2_greenStudent5_img;
                    case 6  -> studentSingleImageID = psb.p2_greenStudent6_img;
                    case 7  -> studentSingleImageID = psb.p2_greenStudent7_img;
                    case 8  -> studentSingleImageID = psb.p2_greenStudent8_img;
                    case 9  -> studentSingleImageID = psb.p2_greenStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p2_redDiningRoom_pane"    -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p2_redStudent0_img;
                    case 1  -> studentSingleImageID = psb.p2_redStudent1_img;
                    case 2  -> studentSingleImageID = psb.p2_redStudent2_img;
                    case 3  -> studentSingleImageID = psb.p2_redStudent3_img;
                    case 4  -> studentSingleImageID = psb.p2_redStudent4_img;
                    case 5  -> studentSingleImageID = psb.p2_redStudent5_img;
                    case 6  -> studentSingleImageID = psb.p2_redStudent6_img;
                    case 7  -> studentSingleImageID = psb.p2_redStudent7_img;
                    case 8  -> studentSingleImageID = psb.p2_redStudent8_img;
                    case 9  -> studentSingleImageID = psb.p2_redStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p2_yellowDiningRoom_pane" -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p2_yellowStudent0_img;
                    case 1  -> studentSingleImageID = psb.p2_yellowStudent1_img;
                    case 2  -> studentSingleImageID = psb.p2_yellowStudent2_img;
                    case 3  -> studentSingleImageID = psb.p2_yellowStudent3_img;
                    case 4  -> studentSingleImageID = psb.p2_yellowStudent4_img;
                    case 5  -> studentSingleImageID = psb.p2_yellowStudent5_img;
                    case 6  -> studentSingleImageID = psb.p2_yellowStudent6_img;
                    case 7  -> studentSingleImageID = psb.p2_yellowStudent7_img;
                    case 8  -> studentSingleImageID = psb.p2_yellowStudent8_img;
                    case 9  -> studentSingleImageID = psb.p2_yellowStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p2_pinkDiningRoom_pane"   -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p2_pinkStudent0_img;
                    case 1  -> studentSingleImageID = psb.p2_pinkStudent1_img;
                    case 2  -> studentSingleImageID = psb.p2_pinkStudent2_img;
                    case 3  -> studentSingleImageID = psb.p2_pinkStudent3_img;
                    case 4  -> studentSingleImageID = psb.p2_pinkStudent4_img;
                    case 5  -> studentSingleImageID = psb.p2_pinkStudent5_img;
                    case 6  -> studentSingleImageID = psb.p2_pinkStudent6_img;
                    case 7  -> studentSingleImageID = psb.p2_pinkStudent7_img;
                    case 8  -> studentSingleImageID = psb.p2_pinkStudent8_img;
                    case 9  -> studentSingleImageID = psb.p2_pinkStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p2_blueDiningRoom_pane"   -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p2_blueStudent0_img;
                    case 1  -> studentSingleImageID = psb.p2_blueStudent1_img;
                    case 2  -> studentSingleImageID = psb.p2_blueStudent2_img;
                    case 3  -> studentSingleImageID = psb.p2_blueStudent3_img;
                    case 4  -> studentSingleImageID = psb.p2_blueStudent4_img;
                    case 5  -> studentSingleImageID = psb.p2_blueStudent5_img;
                    case 6  -> studentSingleImageID = psb.p2_blueStudent6_img;
                    case 7  -> studentSingleImageID = psb.p2_blueStudent7_img;
                    case 8  -> studentSingleImageID = psb.p2_blueStudent8_img;
                    case 9  -> studentSingleImageID = psb.p2_blueStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p3_greenDiningRoom_pane"  -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p3_greenStudent0_img;
                    case 1  -> studentSingleImageID = psb.p3_greenStudent1_img;
                    case 2  -> studentSingleImageID = psb.p3_greenStudent2_img;
                    case 3  -> studentSingleImageID = psb.p3_greenStudent3_img;
                    case 4  -> studentSingleImageID = psb.p3_greenStudent4_img;
                    case 5  -> studentSingleImageID = psb.p3_greenStudent5_img;
                    case 6  -> studentSingleImageID = psb.p3_greenStudent6_img;
                    case 7  -> studentSingleImageID = psb.p3_greenStudent7_img;
                    case 8  -> studentSingleImageID = psb.p3_greenStudent8_img;
                    case 9  -> studentSingleImageID = psb.p3_greenStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p3_redDiningRoom_pane"    -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p3_redStudent0_img;
                    case 1  -> studentSingleImageID = psb.p3_redStudent1_img;
                    case 2  -> studentSingleImageID = psb.p3_redStudent2_img;
                    case 3  -> studentSingleImageID = psb.p3_redStudent3_img;
                    case 4  -> studentSingleImageID = psb.p3_redStudent4_img;
                    case 5  -> studentSingleImageID = psb.p3_redStudent5_img;
                    case 6  -> studentSingleImageID = psb.p3_redStudent6_img;
                    case 7  -> studentSingleImageID = psb.p3_redStudent7_img;
                    case 8  -> studentSingleImageID = psb.p3_redStudent8_img;
                    case 9  -> studentSingleImageID = psb.p3_redStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p3_yellowDiningRoom_pane" -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p3_yellowStudent0_img;
                    case 1  -> studentSingleImageID = psb.p3_yellowStudent1_img;
                    case 2  -> studentSingleImageID = psb.p3_yellowStudent2_img;
                    case 3  -> studentSingleImageID = psb.p3_yellowStudent3_img;
                    case 4  -> studentSingleImageID = psb.p3_yellowStudent4_img;
                    case 5  -> studentSingleImageID = psb.p3_yellowStudent5_img;
                    case 6  -> studentSingleImageID = psb.p3_yellowStudent6_img;
                    case 7  -> studentSingleImageID = psb.p3_yellowStudent7_img;
                    case 8  -> studentSingleImageID = psb.p3_yellowStudent8_img;
                    case 9  -> studentSingleImageID = psb.p3_yellowStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p3_pinkDiningRoom_pane"   -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p3_pinkStudent0_img;
                    case 1  -> studentSingleImageID = psb.p3_pinkStudent1_img;
                    case 2  -> studentSingleImageID = psb.p3_pinkStudent2_img;
                    case 3  -> studentSingleImageID = psb.p3_pinkStudent3_img;
                    case 4  -> studentSingleImageID = psb.p3_pinkStudent4_img;
                    case 5  -> studentSingleImageID = psb.p3_pinkStudent5_img;
                    case 6  -> studentSingleImageID = psb.p3_pinkStudent6_img;
                    case 7  -> studentSingleImageID = psb.p3_pinkStudent7_img;
                    case 8  -> studentSingleImageID = psb.p3_pinkStudent8_img;
                    case 9  -> studentSingleImageID = psb.p3_pinkStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            case "p3_blueDiningRoom_pane"   -> {
                switch (studentPosition) {
                    case 0  -> studentSingleImageID = psb.p3_blueStudent0_img;
                    case 1  -> studentSingleImageID = psb.p3_blueStudent1_img;
                    case 2  -> studentSingleImageID = psb.p3_blueStudent2_img;
                    case 3  -> studentSingleImageID = psb.p3_blueStudent3_img;
                    case 4  -> studentSingleImageID = psb.p3_blueStudent4_img;
                    case 5  -> studentSingleImageID = psb.p3_blueStudent5_img;
                    case 6  -> studentSingleImageID = psb.p3_blueStudent6_img;
                    case 7  -> studentSingleImageID = psb.p3_blueStudent7_img;
                    case 8  -> studentSingleImageID = psb.p3_blueStudent8_img;
                    case 9  -> studentSingleImageID = psb.p3_blueStudent9_img;
                    default -> throw new IllegalStateException("The studentPosition must be between 0 and 7. You've inserted: " + studentPosition);
                }
            }

            default -> throw new IllegalStateException("The singleTablePaneID you have inserted is not contemplated. You've inserted: " + singleTablePaneID.getId());
        }

        return studentSingleImageID;
    }

    public static ImageView psbFindDiningRoomStudentSingleImageID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID, Color tableColor, int studentPosition) {
        AnchorPane singleTablePaneID = psbFindDiningRoomSingleTablePaneID(psb, playerPaneID, tableColor);
        return psbFindDiningRoomStudentSingleImageID(psb, singleTablePaneID, studentPosition);
    }

    public static ImageView psbFindDiningRoomStudentSingleImageID (PlayersSchoolBoardHandler psb, int playerIndex, Color tableColor, int studentPosition) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindDiningRoomStudentSingleImageID(psb, playerPaneID, tableColor, studentPosition);
    }

    // endregion DiningRoomStudentSingleImageID

    // endregion DiningRoomID

    // region EntranceID

    // region EntrancePaneID

    public static AnchorPane psbFindEntrancePaneID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID) {
        AnchorPane entrancePaneID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> entrancePaneID = psb.p0_entrance_pane;
            case "p1_h1_pane" -> entrancePaneID = psb.p1_entrance_pane;
            case "p2_h2_pane" -> entrancePaneID = psb.p2_entrance_pane;
            case "p3_h3_pane" -> entrancePaneID = psb.p3_entrance_pane;
            default           -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return entrancePaneID;
    }

    public static AnchorPane psbFindEntrancePaneID (PlayersSchoolBoardHandler psb, int playerIndex) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindEntrancePaneID(psb, playerPaneID);
    }

    // endregion EntrancePaneID

    // region EntranceStudentImageID

    public static ImageView psbFindEntranceStudentImageID (PlayersSchoolBoardHandler psb, AnchorPane playerPaneID, int entranceStudentPos) {
        ImageView entranceStudentImageID = null;

        switch (playerPaneID.getId()) {
            case "p0_h0_pane" -> {
                switch (entranceStudentPos) {
                    case 0 -> entranceStudentImageID = psb.p0_entranceStudent0_img;
                    case 1 -> entranceStudentImageID = psb.p0_entranceStudent1_img;
                    case 2 -> entranceStudentImageID = psb.p0_entranceStudent2_img;
                    case 3 -> entranceStudentImageID = psb.p0_entranceStudent3_img;
                    case 4 -> entranceStudentImageID = psb.p0_entranceStudent4_img;
                    case 5 -> entranceStudentImageID = psb.p0_entranceStudent5_img;
                    case 6 -> entranceStudentImageID = psb.p0_entranceStudent6_img;
                    case 7 -> entranceStudentImageID = psb.p0_entranceStudent7_img;
                    case 8 -> entranceStudentImageID = psb.p0_entranceStudent8_img;
                    default -> throw new IllegalStateException("The entranceStudentPos must be between 0 and 8. You've inserted: " + entranceStudentPos);
                }
            }

            case "p1_h1_pane" -> {
                switch (entranceStudentPos) {
                    case 0 -> entranceStudentImageID = psb.p1_entranceStudent0_img;
                    case 1 -> entranceStudentImageID = psb.p1_entranceStudent1_img;
                    case 2 -> entranceStudentImageID = psb.p1_entranceStudent2_img;
                    case 3 -> entranceStudentImageID = psb.p1_entranceStudent3_img;
                    case 4 -> entranceStudentImageID = psb.p1_entranceStudent4_img;
                    case 5 -> entranceStudentImageID = psb.p1_entranceStudent5_img;
                    case 6 -> entranceStudentImageID = psb.p1_entranceStudent6_img;
                    case 7 -> entranceStudentImageID = psb.p1_entranceStudent7_img;
                    case 8 -> entranceStudentImageID = psb.p1_entranceStudent8_img;
                    default -> throw new IllegalStateException("The entranceStudentPos must be between 0 and 8. You've inserted: " + entranceStudentPos);
                }
            }

            case "p2_h2_pane" -> {
                switch (entranceStudentPos) {
                    case 0 -> entranceStudentImageID = psb.p2_entranceStudent0_img;
                    case 1 -> entranceStudentImageID = psb.p2_entranceStudent1_img;
                    case 2 -> entranceStudentImageID = psb.p2_entranceStudent2_img;
                    case 3 -> entranceStudentImageID = psb.p2_entranceStudent3_img;
                    case 4 -> entranceStudentImageID = psb.p2_entranceStudent4_img;
                    case 5 -> entranceStudentImageID = psb.p2_entranceStudent5_img;
                    case 6 -> entranceStudentImageID = psb.p2_entranceStudent6_img;
                    case 7 -> entranceStudentImageID = psb.p2_entranceStudent7_img;
                    case 8 -> entranceStudentImageID = psb.p2_entranceStudent8_img;
                    default -> throw new IllegalStateException("The entranceStudentPos must be between 0 and 8. You've inserted: " + entranceStudentPos);
                }
            }

            case "p3_h3_pane" -> {
                switch (entranceStudentPos) {
                    case 0 -> entranceStudentImageID = psb.p3_entranceStudent0_img;
                    case 1 -> entranceStudentImageID = psb.p3_entranceStudent1_img;
                    case 2 -> entranceStudentImageID = psb.p3_entranceStudent2_img;
                    case 3 -> entranceStudentImageID = psb.p3_entranceStudent3_img;
                    case 4 -> entranceStudentImageID = psb.p3_entranceStudent4_img;
                    case 5 -> entranceStudentImageID = psb.p3_entranceStudent5_img;
                    case 6 -> entranceStudentImageID = psb.p3_entranceStudent6_img;
                    case 7 -> entranceStudentImageID = psb.p3_entranceStudent7_img;
                    case 8 -> entranceStudentImageID = psb.p3_entranceStudent8_img;
                    default -> throw new IllegalStateException("The entranceStudentPos must be between 0 and 8. You've inserted: " + entranceStudentPos);
                }
            }

            default -> throw new IllegalStateException("The playerPaneID you have inserted is not contemplated. You've inserted: " + playerPaneID.getId());
        }

        return entranceStudentImageID;
    }

    public static ImageView psbFindEntranceStudentImageID (PlayersSchoolBoardHandler psb, int playerIndex, int entranceStudentPos) {
        AnchorPane playerPaneID = psbFindPlayerPaneID(psb, playerIndex);
        return psbFindEntranceStudentImageID(psb, playerPaneID, entranceStudentPos);
    }

    // endregion EntranceStudentImageID

    // endregion EntranceID

    // endregion PlayersSchoolBoardID


    // region EndGameID

    public static ImageView egFindWizardID (EndGameHandler eg, int wizardIndex) {
        ImageView wizardID = null;
        switch (wizardIndex) {
            case 0 -> wizardID = eg.wizard0_img;
            case 1 -> wizardID = eg.wizard1_img;
            case 2 -> wizardID = eg.wizard2_img;
            case 3 -> wizardID = eg.wizard3_img;
            default -> throw new IllegalStateException("The wizardIndex must be between 0 and 3, has been inserted: " + wizardIndex);
        }

        return wizardID;
    }

    public static Label egFindUsernameID (EndGameHandler eg, int usernameIndex) {
        Label usernameID = null;
        switch (usernameIndex) {
            case 0 -> usernameID = eg.user0_label;
            case 1 -> usernameID = eg.user1_label;
            case 2 -> usernameID = eg.user2_label;
            case 3 -> usernameID = eg.user3_label;
            default -> throw new IllegalStateException("The usernameIndex must be between 0 and 3, has been inserted: " + usernameIndex);
        }

        return usernameID;
    }

    // endregion EndGameID
}
