package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PlayersSchoolBoardHandler implements GUIHandler {

    private ViewGUI gui;

    // region FXML_Ids

    @FXML
    public BorderPane playersBoards_BorderPane;

    @FXML
    public AnchorPane playersBoards_pane;

    @FXML
    public Rectangle playersBoards_background;

    @FXML
    public HBox playersBoards_hbox;

    // region Player0

    @FXML
    public AnchorPane p0_h0_pane;

    @FXML
    public AnchorPane p0_schoolBoard_pane;

    @FXML
    public ImageView p0_schoolBoard_img;

    @FXML
    public AnchorPane p0_towers_pane;

    // region TowersPlayer0

    @FXML
    public GridPane p0_tower_gridPane;

    @FXML
    public ImageView p0_tower0_img;


    @FXML
    public ImageView p0_tower1_img;


    @FXML
    public ImageView p0_tower2_img;


    @FXML
    public ImageView p0_tower3_img;


    @FXML
    public ImageView p0_tower4_img;


    @FXML
    public ImageView p0_tower5_img;


    @FXML
    public ImageView p0_tower6_img;


    @FXML
    public ImageView p0_tower7_img;

    // endregion TowersPlayer0

    // region InfoPlayer0

    @FXML
    public AnchorPane p0_addInfo_pane;

    @FXML
    public Rectangle p0_addInfo_background;

    @FXML
    public ImageView p0_lastAssistant;

    @FXML
    public Text p0_username_text;

    @FXML
    public Text p0_lastCardPlayed_text;

    // region CoinsPlayer0

    @FXML
    public AnchorPane p0_coin_pane;

    @FXML
    public ImageView p0_coin_img;

    @FXML
    public Text p0_coinsCount_text;

    // endregion CoinsPlayer0

    // endregion InfoPlayer0

    // region DiningRoomPlayer0

    @FXML
    public AnchorPane p0_diningRoom_pane;

    // region ProfessorTablePlayer0

    @FXML
    public AnchorPane p0_professorTable_pane;

    @FXML
    public ImageView p0_greenProfessor_img;

    @FXML
    public ImageView p0_redProfessor_img;

    @FXML
    public ImageView p0_yellowProfessor_img;

    @FXML
    public ImageView p0_pinkProfessor_img;

    @FXML
    public ImageView p0_blueProfessor_img;

    // endregion ProfessorTablePlayer0

    // region GreenTablePlayer0

    @FXML
    public AnchorPane p0_greenDiningRoom_pane;

    @FXML
    public ImageView p0_greenStudent0_img;

    @FXML
    public ImageView p0_greenStudent1_img;

    @FXML
    public ImageView p0_greenStudent2_img;

    @FXML
    public ImageView p0_greenStudent3_img;

    @FXML
    public ImageView p0_greenStudent4_img;

    @FXML
    public ImageView p0_greenStudent5_img;

    @FXML
    public ImageView p0_greenStudent6_img;

    @FXML
    public ImageView p0_greenStudent7_img;

    @FXML
    public ImageView p0_greenStudent8_img;

    @FXML
    public ImageView p0_greenStudent9_img;

    // endregion GreenTablePlayer0

    // region RedTablePlayer0

    @FXML
    public AnchorPane p0_redDiningRoom_pane;

    @FXML
    public ImageView p0_redStudent0_img;

    @FXML
    public ImageView p0_redStudent1_img;

    @FXML
    public ImageView p0_redStudent2_img;

    @FXML
    public ImageView p0_redStudent3_img;

    @FXML
    public ImageView p0_redStudent4_img;

    @FXML
    public ImageView p0_redStudent5_img;

    @FXML
    public ImageView p0_redStudent6_img;

    @FXML
    public ImageView p0_redStudent7_img;

    @FXML
    public ImageView p0_redStudent8_img;

    @FXML
    public ImageView p0_redStudent9_img;

    // endregion RedTablePlayer0

    // region YellowTablePlayer0

    @FXML
    public AnchorPane p0_yellowDiningRoom_pane;

    @FXML
    public ImageView p0_yellowStudent0_img;

    @FXML
    public ImageView p0_yellowStudent1_img;

    @FXML
    public ImageView p0_yellowStudent2_img;

    @FXML
    public ImageView p0_yellowStudent3_img;

    @FXML
    public ImageView p0_yellowStudent4_img;

    @FXML
    public ImageView p0_yellowStudent5_img;

    @FXML
    public ImageView p0_yellowStudent6_img;

    @FXML
    public ImageView p0_yellowStudent7_img;

    @FXML
    public ImageView p0_yellowStudent8_img;

    @FXML
    public ImageView p0_yellowStudent9_img;

    // endregion YellowTablePlayer0

    // region PinkTablePlayer0

    @FXML
    public AnchorPane p0_pinkDiningRoom_pane;

    @FXML
    public ImageView p0_pinkStudent0_img;

    @FXML
    public ImageView p0_pinkStudent1_img;

    @FXML
    public ImageView p0_pinkStudent2_img;

    @FXML
    public ImageView p0_pinkStudent3_img;

    @FXML
    public ImageView p0_pinkStudent4_img;

    @FXML
    public ImageView p0_pinkStudent5_img;

    @FXML
    public ImageView p0_pinkStudent6_img;

    @FXML
    public ImageView p0_pinkStudent7_img;

    @FXML
    public ImageView p0_pinkStudent8_img;

    @FXML
    public ImageView p0_pinkStudent9_img;

    // endregion PinkTablePlayer0

    // region BlueTablePlayer0

    @FXML
    public AnchorPane p0_blueDiningRoom_pane;

    @FXML
    public ImageView p0_blueStudent0_img;

    @FXML
    public ImageView p0_blueStudent1_img;

    @FXML
    public ImageView p0_blueStudent2_img;

    @FXML
    public ImageView p0_blueStudent3_img;

    @FXML
    public ImageView p0_blueStudent4_img;

    @FXML
    public ImageView p0_blueStudent5_img;

    @FXML
    public ImageView p0_blueStudent6_img;

    @FXML
    public ImageView p0_blueStudent7_img;

    @FXML
    public ImageView p0_blueStudent8_img;

    @FXML
    public ImageView p0_blueStudent9_img;

    // endregion BlueTablePlayer0

    // endregion DiningRoomPlayer0

    // region EntrancePlayer0

    @FXML
    public AnchorPane p0_entrance_pane;

    @FXML
    public ImageView p0_entranceStudent0_img;

    @FXML
    public ImageView p0_entranceStudent1_img;

    @FXML
    public ImageView p0_entranceStudent2_img;

    @FXML
    public ImageView p0_entranceStudent3_img;

    @FXML
    public ImageView p0_entranceStudent4_img;

    @FXML
    public ImageView p0_entranceStudent5_img;

    @FXML
    public ImageView p0_entranceStudent6_img;

    @FXML
    public ImageView p0_entranceStudent7_img;

    @FXML
    public ImageView p0_entranceStudent8_img;

    // endregion EntrancePlayer0

    // endregion Player0

    // region Player1

    @FXML
    public AnchorPane p1_h1_pane;

    @FXML
    public AnchorPane p1_schoolBoard_pane;

    @FXML
    public ImageView p1_schoolBoard_img;

    @FXML
    public AnchorPane p1_towers_pane;

    // region TowersPlayer1

    @FXML
    public GridPane p1_tower_gridPane;

    @FXML
    public ImageView p1_tower0_img;


    @FXML
    public ImageView p1_tower1_img;


    @FXML
    public ImageView p1_tower2_img;


    @FXML
    public ImageView p1_tower3_img;


    @FXML
    public ImageView p1_tower4_img;


    @FXML
    public ImageView p1_tower5_img;


    @FXML
    public ImageView p1_tower6_img;


    @FXML
    public ImageView p1_tower7_img;

    // endregion TowersPlayer1

    // region InfoPlayer1

    @FXML
    public AnchorPane p1_addInfo_pane;

    @FXML
    public Rectangle p1_addInfo_background;

    @FXML
    public ImageView p1_lastAssistant;

    @FXML
    public Text p1_username_text;

    @FXML
    public Text p1_lastCardPlayed_text;

    // region CoinsPlayer1

    @FXML
    public AnchorPane p1_coin_pane;

    @FXML
    public ImageView p1_coin_img;

    @FXML
    public Text p1_coinsCount_text;

    // endregion CoinsPlayer1

    // endregion InfoPlayer1

    // region DiningRoomPlayer1

    @FXML
    public AnchorPane p1_diningRoom_pane;

    // region ProfessorTablePlayer1

    @FXML
    public AnchorPane p1_professorTable_pane;

    @FXML
    public ImageView p1_greenProfessor_img;

    @FXML
    public ImageView p1_redProfessor_img;

    @FXML
    public ImageView p1_yellowProfessor_img;

    @FXML
    public ImageView p1_pinkProfessor_img;

    @FXML
    public ImageView p1_blueProfessor_img;

    // endregion ProfessorTablePlayer1

    // region GreenTablePlayer1

    @FXML
    public AnchorPane p1_greenDiningRoom_pane;

    @FXML
    public ImageView p1_greenStudent0_img;

    @FXML
    public ImageView p1_greenStudent1_img;

    @FXML
    public ImageView p1_greenStudent2_img;

    @FXML
    public ImageView p1_greenStudent3_img;

    @FXML
    public ImageView p1_greenStudent4_img;

    @FXML
    public ImageView p1_greenStudent5_img;

    @FXML
    public ImageView p1_greenStudent6_img;

    @FXML
    public ImageView p1_greenStudent7_img;

    @FXML
    public ImageView p1_greenStudent8_img;

    @FXML
    public ImageView p1_greenStudent9_img;

    // endregion GreenTablePlayer1

    // region RedTablePlayer1

    @FXML
    public AnchorPane p1_redDiningRoom_pane;

    @FXML
    public ImageView p1_redStudent0_img;

    @FXML
    public ImageView p1_redStudent1_img;

    @FXML
    public ImageView p1_redStudent2_img;

    @FXML
    public ImageView p1_redStudent3_img;

    @FXML
    public ImageView p1_redStudent4_img;

    @FXML
    public ImageView p1_redStudent5_img;

    @FXML
    public ImageView p1_redStudent6_img;

    @FXML
    public ImageView p1_redStudent7_img;

    @FXML
    public ImageView p1_redStudent8_img;

    @FXML
    public ImageView p1_redStudent9_img;

    // endregion RedTablePlayer1

    // region YellowTablePlayer1

    @FXML
    public AnchorPane p1_yellowDiningRoom_pane;

    @FXML
    public ImageView p1_yellowStudent0_img;

    @FXML
    public ImageView p1_yellowStudent1_img;

    @FXML
    public ImageView p1_yellowStudent2_img;

    @FXML
    public ImageView p1_yellowStudent3_img;

    @FXML
    public ImageView p1_yellowStudent4_img;

    @FXML
    public ImageView p1_yellowStudent5_img;

    @FXML
    public ImageView p1_yellowStudent6_img;

    @FXML
    public ImageView p1_yellowStudent7_img;

    @FXML
    public ImageView p1_yellowStudent8_img;

    @FXML
    public ImageView p1_yellowStudent9_img;

    // endregion YellowTablePlayer1

    // region PinkTablePlayer1

    @FXML
    public AnchorPane p1_pinkDiningRoom_pane;

    @FXML
    public ImageView p1_pinkStudent0_img;

    @FXML
    public ImageView p1_pinkStudent1_img;

    @FXML
    public ImageView p1_pinkStudent2_img;

    @FXML
    public ImageView p1_pinkStudent3_img;

    @FXML
    public ImageView p1_pinkStudent4_img;

    @FXML
    public ImageView p1_pinkStudent5_img;

    @FXML
    public ImageView p1_pinkStudent6_img;

    @FXML
    public ImageView p1_pinkStudent7_img;

    @FXML
    public ImageView p1_pinkStudent8_img;

    @FXML
    public ImageView p1_pinkStudent9_img;

    // endregion PinkTablePlayer1

    // region BlueTablePlayer1

    @FXML
    public AnchorPane p1_blueDiningRoom_pane;

    @FXML
    public ImageView p1_blueStudent0_img;

    @FXML
    public ImageView p1_blueStudent1_img;

    @FXML
    public ImageView p1_blueStudent2_img;

    @FXML
    public ImageView p1_blueStudent3_img;

    @FXML
    public ImageView p1_blueStudent4_img;

    @FXML
    public ImageView p1_blueStudent5_img;

    @FXML
    public ImageView p1_blueStudent6_img;

    @FXML
    public ImageView p1_blueStudent7_img;

    @FXML
    public ImageView p1_blueStudent8_img;

    @FXML
    public ImageView p1_blueStudent9_img;

    // endregion BlueTablePlayer1

    // endregion DiningRoomPlayer1

    // region EntrancePlayer1

    @FXML
    public AnchorPane p1_entrance_pane;

    @FXML
    public ImageView p1_entranceStudent0_img;

    @FXML
    public ImageView p1_entranceStudent1_img;

    @FXML
    public ImageView p1_entranceStudent2_img;

    @FXML
    public ImageView p1_entranceStudent3_img;

    @FXML
    public ImageView p1_entranceStudent4_img;

    @FXML
    public ImageView p1_entranceStudent5_img;

    @FXML
    public ImageView p1_entranceStudent6_img;

    @FXML
    public ImageView p1_entranceStudent7_img;

    @FXML
    public ImageView p1_entranceStudent8_img;

    // endregion EntrancePlayer1

    // endregion Player1

    // region Player2

    @FXML
    public AnchorPane p2_h2_pane;

    @FXML
    public AnchorPane p2_schoolBoard_pane;

    @FXML
    public ImageView p2_schoolBoard_img;

    @FXML
    public AnchorPane p2_towers_pane;

    // region TowersPlayer2

    @FXML
    public GridPane p2_tower_gridPane;

    @FXML
    public ImageView p2_tower0_img;


    @FXML
    public ImageView p2_tower1_img;


    @FXML
    public ImageView p2_tower2_img;


    @FXML
    public ImageView p2_tower3_img;


    @FXML
    public ImageView p2_tower4_img;


    @FXML
    public ImageView p2_tower5_img;


    @FXML
    public ImageView p2_tower6_img;


    @FXML
    public ImageView p2_tower7_img;

    // endregion TowersPlayer2

    // region InfoPlayer2

    @FXML
    public AnchorPane p2_addInfo_pane;

    @FXML
    public Rectangle p2_addInfo_background;

    @FXML
    public ImageView p2_lastAssistant;

    @FXML
    public Text p2_username_text;

    @FXML
    public Text p2_lastCardPlayed_text;

    // region CoinsPlayer2

    @FXML
    public AnchorPane p2_coin_pane;

    @FXML
    public ImageView p2_coin_img;

    @FXML
    public Text p2_coinsCount_text;

    // endregion CoinsPlayer2

    // endregion InfoPlayer2

    // region DiningRoomPlayer2

    @FXML
    public AnchorPane p2_diningRoom_pane;

    // region ProfessorTablePlayer2

    @FXML
    public AnchorPane p2_professorTable_pane;

    @FXML
    public ImageView p2_greenProfessor_img;

    @FXML
    public ImageView p2_redProfessor_img;

    @FXML
    public ImageView p2_yellowProfessor_img;

    @FXML
    public ImageView p2_pinkProfessor_img;

    @FXML
    public ImageView p2_blueProfessor_img;

    // endregion ProfessorTablePlayer2

    // region GreenTablePlayer2

    @FXML
    public AnchorPane p2_greenDiningRoom_pane;

    @FXML
    public ImageView p2_greenStudent0_img;

    @FXML
    public ImageView p2_greenStudent1_img;

    @FXML
    public ImageView p2_greenStudent2_img;

    @FXML
    public ImageView p2_greenStudent3_img;

    @FXML
    public ImageView p2_greenStudent4_img;

    @FXML
    public ImageView p2_greenStudent5_img;

    @FXML
    public ImageView p2_greenStudent6_img;

    @FXML
    public ImageView p2_greenStudent7_img;

    @FXML
    public ImageView p2_greenStudent8_img;

    @FXML
    public ImageView p2_greenStudent9_img;

    // endregion GreenTablePlayer2

    // region RedTablePlayer2

    @FXML
    public AnchorPane p2_redDiningRoom_pane;

    @FXML
    public ImageView p2_redStudent0_img;

    @FXML
    public ImageView p2_redStudent1_img;

    @FXML
    public ImageView p2_redStudent2_img;

    @FXML
    public ImageView p2_redStudent3_img;

    @FXML
    public ImageView p2_redStudent4_img;

    @FXML
    public ImageView p2_redStudent5_img;

    @FXML
    public ImageView p2_redStudent6_img;

    @FXML
    public ImageView p2_redStudent7_img;

    @FXML
    public ImageView p2_redStudent8_img;

    @FXML
    public ImageView p2_redStudent9_img;

    // endregion RedTablePlayer2

    // region YellowTablePlayer2

    @FXML
    public AnchorPane p2_yellowDiningRoom_pane;

    @FXML
    public ImageView p2_yellowStudent0_img;

    @FXML
    public ImageView p2_yellowStudent1_img;

    @FXML
    public ImageView p2_yellowStudent2_img;

    @FXML
    public ImageView p2_yellowStudent3_img;

    @FXML
    public ImageView p2_yellowStudent4_img;

    @FXML
    public ImageView p2_yellowStudent5_img;

    @FXML
    public ImageView p2_yellowStudent6_img;

    @FXML
    public ImageView p2_yellowStudent7_img;

    @FXML
    public ImageView p2_yellowStudent8_img;

    @FXML
    public ImageView p2_yellowStudent9_img;

    // endregion YellowTablePlayer2

    // region PinkTablePlayer2

    @FXML
    public AnchorPane p2_pinkDiningRoom_pane;

    @FXML
    public ImageView p2_pinkStudent0_img;

    @FXML
    public ImageView p2_pinkStudent1_img;

    @FXML
    public ImageView p2_pinkStudent2_img;

    @FXML
    public ImageView p2_pinkStudent3_img;

    @FXML
    public ImageView p2_pinkStudent4_img;

    @FXML
    public ImageView p2_pinkStudent5_img;

    @FXML
    public ImageView p2_pinkStudent6_img;

    @FXML
    public ImageView p2_pinkStudent7_img;

    @FXML
    public ImageView p2_pinkStudent8_img;

    @FXML
    public ImageView p2_pinkStudent9_img;

    // endregion PinkTablePlayer2

    // region BlueTablePlayer2

    @FXML
    public AnchorPane p2_blueDiningRoom_pane;

    @FXML
    public ImageView p2_blueStudent0_img;

    @FXML
    public ImageView p2_blueStudent1_img;

    @FXML
    public ImageView p2_blueStudent2_img;

    @FXML
    public ImageView p2_blueStudent3_img;

    @FXML
    public ImageView p2_blueStudent4_img;

    @FXML
    public ImageView p2_blueStudent5_img;

    @FXML
    public ImageView p2_blueStudent6_img;

    @FXML
    public ImageView p2_blueStudent7_img;

    @FXML
    public ImageView p2_blueStudent8_img;

    @FXML
    public ImageView p2_blueStudent9_img;

    // endregion BlueTablePlayer2

    // endregion DiningRoomPlayer2

    // region EntrancePlayer2

    @FXML
    public AnchorPane p2_entrance_pane;

    @FXML
    public ImageView p2_entranceStudent0_img;

    @FXML
    public ImageView p2_entranceStudent1_img;

    @FXML
    public ImageView p2_entranceStudent2_img;

    @FXML
    public ImageView p2_entranceStudent3_img;

    @FXML
    public ImageView p2_entranceStudent4_img;

    @FXML
    public ImageView p2_entranceStudent5_img;

    @FXML
    public ImageView p2_entranceStudent6_img;

    @FXML
    public ImageView p2_entranceStudent7_img;

    @FXML
    public ImageView p2_entranceStudent8_img;

    // endregion EntrancePlayer2

    // endregion Player2

    // region Player3

    @FXML
    public AnchorPane p3_h3_pane;

    @FXML
    public AnchorPane p3_schoolBoard_pane;

    @FXML
    public ImageView p3_schoolBoard_img;

    @FXML
    public AnchorPane p3_towers_pane;

    // region TowersPlayer3

    @FXML
    public GridPane p3_tower_gridPane;

    @FXML
    public ImageView p3_tower0_img;


    @FXML
    public ImageView p3_tower1_img;


    @FXML
    public ImageView p3_tower2_img;


    @FXML
    public ImageView p3_tower3_img;


    @FXML
    public ImageView p3_tower4_img;


    @FXML
    public ImageView p3_tower5_img;


    @FXML
    public ImageView p3_tower6_img;


    @FXML
    public ImageView p3_tower7_img;

    // endregion TowersPlayer3

    // region InfoPlayer3

    @FXML
    public AnchorPane p3_addInfo_pane;

    @FXML
    public Rectangle p3_addInfo_background;

    @FXML
    public ImageView p3_lastAssistant;

    @FXML
    public Text p3_username_text;

    @FXML
    public Text p3_lastCardPlayed_text;

    // region CoinsPlayer3

    @FXML
    public AnchorPane p3_coin_pane;

    @FXML
    public ImageView p3_coin_img;

    @FXML
    public Text p3_coinsCount_text;

    // endregion CoinsPlayer3

    // endregion InfoPlayer3

    // region DiningRoomPlayer3

    @FXML
    public AnchorPane p3_diningRoom_pane;

    // region ProfessorTablePlayer3

    @FXML
    public AnchorPane p3_professorTable_pane;

    @FXML
    public ImageView p3_greenProfessor_img;

    @FXML
    public ImageView p3_redProfessor_img;

    @FXML
    public ImageView p3_yellowProfessor_img;

    @FXML
    public ImageView p3_pinkProfessor_img;

    @FXML
    public ImageView p3_blueProfessor_img;

    // endregion ProfessorTablePlayer3

    // region GreenTablePlayer3

    @FXML
    public AnchorPane p3_greenDiningRoom_pane;

    @FXML
    public ImageView p3_greenStudent0_img;

    @FXML
    public ImageView p3_greenStudent1_img;

    @FXML
    public ImageView p3_greenStudent2_img;

    @FXML
    public ImageView p3_greenStudent3_img;

    @FXML
    public ImageView p3_greenStudent4_img;

    @FXML
    public ImageView p3_greenStudent5_img;

    @FXML
    public ImageView p3_greenStudent6_img;

    @FXML
    public ImageView p3_greenStudent7_img;

    @FXML
    public ImageView p3_greenStudent8_img;

    @FXML
    public ImageView p3_greenStudent9_img;

    // endregion GreenTablePlayer3

    // region RedTablePlayer3

    @FXML
    public AnchorPane p3_redDiningRoom_pane;

    @FXML
    public ImageView p3_redStudent0_img;

    @FXML
    public ImageView p3_redStudent1_img;

    @FXML
    public ImageView p3_redStudent2_img;

    @FXML
    public ImageView p3_redStudent3_img;

    @FXML
    public ImageView p3_redStudent4_img;

    @FXML
    public ImageView p3_redStudent5_img;

    @FXML
    public ImageView p3_redStudent6_img;

    @FXML
    public ImageView p3_redStudent7_img;

    @FXML
    public ImageView p3_redStudent8_img;

    @FXML
    public ImageView p3_redStudent9_img;

    // endregion RedTablePlayer3

    // region YellowTablePlayer3

    @FXML
    public AnchorPane p3_yellowDiningRoom_pane;

    @FXML
    public ImageView p3_yellowStudent0_img;

    @FXML
    public ImageView p3_yellowStudent1_img;

    @FXML
    public ImageView p3_yellowStudent2_img;

    @FXML
    public ImageView p3_yellowStudent3_img;

    @FXML
    public ImageView p3_yellowStudent4_img;

    @FXML
    public ImageView p3_yellowStudent5_img;

    @FXML
    public ImageView p3_yellowStudent6_img;

    @FXML
    public ImageView p3_yellowStudent7_img;

    @FXML
    public ImageView p3_yellowStudent8_img;

    @FXML
    public ImageView p3_yellowStudent9_img;

    // endregion YellowTablePlayer3

    // region PinkTablePlayer3

    @FXML
    public AnchorPane p3_pinkDiningRoom_pane;

    @FXML
    public ImageView p3_pinkStudent0_img;

    @FXML
    public ImageView p3_pinkStudent1_img;

    @FXML
    public ImageView p3_pinkStudent2_img;

    @FXML
    public ImageView p3_pinkStudent3_img;

    @FXML
    public ImageView p3_pinkStudent4_img;

    @FXML
    public ImageView p3_pinkStudent5_img;

    @FXML
    public ImageView p3_pinkStudent6_img;

    @FXML
    public ImageView p3_pinkStudent7_img;

    @FXML
    public ImageView p3_pinkStudent8_img;

    @FXML
    public ImageView p3_pinkStudent9_img;

    // endregion PinkTablePlayer3

    // region BlueTablePlayer3

    @FXML
    public AnchorPane p3_blueDiningRoom_pane;

    @FXML
    public ImageView p3_blueStudent0_img;

    @FXML
    public ImageView p3_blueStudent1_img;

    @FXML
    public ImageView p3_blueStudent2_img;

    @FXML
    public ImageView p3_blueStudent3_img;

    @FXML
    public ImageView p3_blueStudent4_img;

    @FXML
    public ImageView p3_blueStudent5_img;

    @FXML
    public ImageView p3_blueStudent6_img;

    @FXML
    public ImageView p3_blueStudent7_img;

    @FXML
    public ImageView p3_blueStudent8_img;

    @FXML
    public ImageView p3_blueStudent9_img;

    // endregion BlueTablePlayer3

    // endregion DiningRoomPlayer3

    // region EntrancePlayer3

    @FXML
    public AnchorPane p3_entrance_pane;

    @FXML
    public ImageView p3_entranceStudent0_img;

    @FXML
    public ImageView p3_entranceStudent1_img;

    @FXML
    public ImageView p3_entranceStudent2_img;

    @FXML
    public ImageView p3_entranceStudent3_img;

    @FXML
    public ImageView p3_entranceStudent4_img;

    @FXML
    public ImageView p3_entranceStudent5_img;

    @FXML
    public ImageView p3_entranceStudent6_img;

    @FXML
    public ImageView p3_entranceStudent7_img;

    @FXML
    public ImageView p3_entranceStudent8_img;

    // endregion EntrancePlayer3

    // endregion Player3

    // region ReturnButton

    @FXML
    public Rectangle return_background;

    @FXML
    public ImageView return_img;

    // endregion ReturnButton

    // endregion FXML_Ids

    // TODO JavaDocs + Regions
    // TODO FXML ids
    // TODO Implementations


    /**
     * Sets the ViewGUI at which the ServerInfoHandler is related
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;

        // Usata solo per testare la funzione:
        // p0_entranceStudent1_img.setImage(new Image(getClass().getClassLoader().getResource("it/polimi/ingsw/images/students/student_red.png").toString(), true));

    }
}
