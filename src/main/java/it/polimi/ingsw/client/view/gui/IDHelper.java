package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.gui.sceneHandlers.GameSceneHandler;
import it.polimi.ingsw.common.model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Helper class with useful methods to switch between model elements and corresponding id
 * GS = Game Scene; PSB = PlayersSchoolBoard
 * @author Sebastiano Meneghin
 */
public class IDHelper {

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

    /**
     * Gets the ImageView (ID) of a specific AssistantCard
     * @param gs The gameSceneHandler
     * @param assistantCardValue An int representing of the assistantCardValue the assistantCard of which we need to find the ImageView
     * @return The ImageView correspondent to the desired assistantCard
     */
    public static ImageView gsFindAssistantCardID (GameSceneHandler gs, int assistantCardValue) {
        ImageView assistantCardID = null;

        switch(assistantCardValue) {
            case 1  -> assistantCardID =gs.assistant1_game;
            case 2  -> assistantCardID =gs.assistant2_game;
            case 3  -> assistantCardID =gs.assistant3_game;
            case 4  -> assistantCardID =gs.assistant4_game;
            case 5  -> assistantCardID =gs.assistant5_game;
            case 6  -> assistantCardID =gs.assistant6_game;
            case 7  -> assistantCardID =gs.assistant7_game;
            case 8  -> assistantCardID =gs.assistant8_game;
            case 9  -> assistantCardID =gs.assistant9_game;
            case 10 -> assistantCardID =gs.assistant10_game;
            default -> throw new IllegalStateException("The assistantCard cardValue must be between 1 and 10. You've inserted: " + assistantCardValue);
        }

        return assistantCardID;
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

    /**
     * Gets the ImageView (ID) of a specific tower on the player SchoolBoard
     * @param gs The gameSceneHandler
     * @param towerPosition An int representing the index of the tower in the SchoolBoard
     * @return The ImageView correspondent to the desired tower
     *
     */
    public static ImageView gsFindSchoolBoardTower (GameSceneHandler gs, int towerPosition) { return null; }

    // CharacterCard
}
