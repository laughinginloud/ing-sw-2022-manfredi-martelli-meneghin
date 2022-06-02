package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.gui.sceneHandlers.GameSceneHandler;
import it.polimi.ingsw.common.model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class IDHelper {

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
                }
            }
        }

        return studentDiningRoomID;
    }

    public static ImageView gsFindStudentEntranceID (GameSceneHandler gs, int studentEntrancePosition) {
        ImageView studentEntranceID = null;
        switch (studentEntrancePosition) {
            case 0 -> studentEntranceID = gs.E_student0_img;
            case 1 -> studentEntranceID = gs.E_student1_img;
            case 2 -> studentEntranceID = gs.E_student2_img;
            case 3 -> studentEntranceID = gs.E_student3_img;
            case 4 -> studentEntranceID = gs.E_student4_img;
            case 5 -> studentEntranceID = gs.E_student5_img;
            case 6 -> studentEntranceID = gs.E_student6_img;
            case 7 -> studentEntranceID = gs.E_student7_img;
            case 8 -> studentEntranceID = gs.E_student8_img;
        }

        return studentEntranceID;
    }

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
        }

        return islandID;
    }

    public static ImageView gsFindStudentOnIslandID (GameSceneHandler gs, int islandPosition, Color studentColor) { return null; }

    public static ImageView gsFindStudentOnIslandID (GameSceneHandler gs, ImageView islandID, Color studentColor) { return null; }
    public static Text gsFindStudentCounterOnIslandID (GameSceneHandler gs, int islandPosition, Color studentColor) { return null; }
    public static Text gsFindStudentCounterOnIslandID (GameSceneHandler gs, ImageView islandID, Color studentColor) { return null; }



    // TODO: [ToRemove] - E' solamente una classe di esempio per l'implementazione dei metodi
    public void updateEntrance (Entrance updatedEntrance) {
        // Dichiaro un GameSceneHandler per poterlo richiamare nella funzione sottostante
        GameSceneHandler gs = new GameSceneHandler();

        // Scorro tutti gli studenti della entrance
        for (int i = 0; i < updatedEntrance.getStudents().length; i ++) {

            // Se lo studente esiste:
            if (updatedEntrance.getStudents()[i] != null) {

                // Prendo il colore dello studente i-esimo
                Color     studentColor                = updatedEntrance.getStudents()[i];

                // Trovo l'ID dello studente i-esimo all'interno della entrance
                ImageView entranceStudenToUpdate      = gsFindStudentEntranceID(gs, i);

                // Trovo l'handlerPath dell'immagine corrispondente alla pedina studente che devo inserire graficamente nella entrance
                String    entranceStudenToUpdateImage = ImageTypes.fromStudentColorToHandlerPath(studentColor);

                // Setto la nuova immagine con il path ricavato prima
                entranceStudenToUpdate.setImage(new Image(getClass().getClassLoader().getResource(entranceStudenToUpdateImage).toString(), true));
                // Aggiungo altri effetti/clickabilità/qualsiasi cosa a questo ID
            }
        }
    }
}
