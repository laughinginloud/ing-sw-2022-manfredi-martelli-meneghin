package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.*;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Handler (or Controller) of the scene GameScene (gameScenePage.fxml)
 * The game's main scene
 * @author Giovanni Manfredi
 */
public class GameSceneHandler implements GUIHandler {

    public ViewGUI gui;

    // region FXML_Ids

    @FXML
    public BorderPane border_pane;

    @FXML
    public AnchorPane game_pane;

    // region Sky

    @FXML
    public AnchorPane sky_pane;

    @FXML
    public Rectangle sky_rectangle;

    // region Islands

    @FXML
    public AnchorPane islands_pane;

    // region Island0

    @FXML
    public AnchorPane isl0_pane;

    @FXML
    public ImageView isl0_img;

    @FXML
    public ImageView isl0_tower_img;

    @FXML
    public Text isl0_towersCount_text;

    @FXML
    public ImageView isl0_yellowStud_img;

    @FXML
    public Text isl0_yellowStudCount_text;

    @FXML
    public ImageView isl0_redStud_img;

    @FXML
    public Text isl0_redStudCount_text;

    @FXML
    public ImageView isl0_blueStud_img;

    @FXML
    public Text isl0_blueStudCount_text;

    @FXML
    public ImageView isl0_pinkStud_img;

    @FXML
    public Text isl0_pinkStudCount_text;

    @FXML
    public ImageView isl0_greenStud_img;

    @FXML
    public Text isl0_greenStudCount_text;

    @FXML
    public ImageView isl0_motherNature_img;

    @FXML
    public ImageView isl0_noEntryTile_img;

    @FXML
    public Text isl0_noEntryTilesCount_text;

    // endregion Island0

    // region Island1

    @FXML
    public AnchorPane isl1_pane;

    @FXML
    public ImageView isl1_img;

    @FXML
    public ImageView isl1_tower_img;

    @FXML
    public Text isl1_towersCount_text;

    @FXML
    public ImageView isl1_yellowStud_img;

    @FXML
    public Text isl1_yellowStudCount_text;

    @FXML
    public ImageView isl1_redStud_img;

    @FXML
    public Text isl1_redStudCount_text;

    @FXML
    public ImageView isl1_blueStud_img;

    @FXML
    public Text isl1_blueStudCount_text;

    @FXML
    public ImageView isl1_pinkStud_img;

    @FXML
    public Text isl1_pinkStudCount_text;

    @FXML
    public ImageView isl1_greenStud_img;

    @FXML
    public Text isl1_greenStudCount_text;

    @FXML
    public ImageView isl1_motherNature_img;

    @FXML
    public ImageView isl1_noEntryTile_img;

    @FXML
    public Text isl1_noEntryTilesCount_text;

    // endregion Island1

    // region Island2

    @FXML
    public AnchorPane isl2_pane;

    @FXML
    public ImageView isl2_img;

    @FXML
    public ImageView isl2_tower_img;

    @FXML
    public Text isl2_towersCount_text;

    @FXML
    public ImageView isl2_yellowStud_img;

    @FXML
    public Text isl2_yellowStudCount_text;

    @FXML
    public ImageView isl2_redStud_img;

    @FXML
    public Text isl2_redStudCount_text;

    @FXML
    public ImageView isl2_blueStud_img;

    @FXML
    public Text isl2_blueStudCount_text;

    @FXML
    public ImageView isl2_pinkStud_img;

    @FXML
    public Text isl2_pinkStudCount_text;

    @FXML
    public ImageView isl2_greenStud_img;

    @FXML
    public Text isl2_greenStudCount_text;

    @FXML
    public ImageView isl2_motherNature_img;

    @FXML
    public ImageView isl2_noEntryTile_img;

    @FXML
    public Text isl2_noEntryTilesCount_text;

    // endregion Island2

    // region Island3

    @FXML
    public AnchorPane isl3_pane;

    @FXML
    public ImageView isl3_img;

    @FXML
    public ImageView isl3_tower_img;

    @FXML
    public Text isl3_towersCount_text;

    @FXML
    public ImageView isl3_yellowStud_img;

    @FXML
    public Text isl3_yellowStudCount_text;

    @FXML
    public ImageView isl3_redStud_img;

    @FXML
    public Text isl3_redStudCount_text;

    @FXML
    public ImageView isl3_blueStud_img;

    @FXML
    public Text isl3_blueStudCount_text;

    @FXML
    public ImageView isl3_pinkStud_img;

    @FXML
    public Text isl3_pinkStudCount_text;

    @FXML
    public ImageView isl3_greenStud_img;

    @FXML
    public Text isl3_greenStudCount_text;

    @FXML
    public ImageView isl3_motherNature_img;

    @FXML
    public ImageView isl3_noEntryTile_img;

    @FXML
    public Text isl3_noEntryTilesCount_text;

    // endregion Island3

    // region Island4

    @FXML
    public AnchorPane isl4_pane;

    @FXML
    public ImageView isl4_img;

    @FXML
    public ImageView isl4_tower_img;

    @FXML
    public Text isl4_towersCount_text;

    @FXML
    public ImageView isl4_yellowStud_img;

    @FXML
    public Text isl4_yellowStudCount_text;

    @FXML
    public ImageView isl4_redStud_img;

    @FXML
    public Text isl4_redStudCount_text;

    @FXML
    public ImageView isl4_blueStud_img;

    @FXML
    public Text isl4_blueStudCount_text;

    @FXML
    public ImageView isl4_pinkStud_img;

    @FXML
    public Text isl4_pinkStudCount_text;

    @FXML
    public ImageView isl4_greenStud_img;

    @FXML
    public Text isl4_greenStudCount_text;

    @FXML
    public ImageView isl4_motherNature_img;

    @FXML
    public ImageView isl4_noEntryTile_img;

    @FXML
    public Text isl4_noEntryTilesCount_text;

    // endregion Island4

    // region Island5

    @FXML
    public AnchorPane isl5_pane;

    @FXML
    public ImageView isl5_img;

    @FXML
    public ImageView isl5_tower_img;

    @FXML
    public Text isl5_towersCount_text;

    @FXML
    public ImageView isl5_yellowStud_img;

    @FXML
    public Text isl5_yellowStudCount_text;

    @FXML
    public ImageView isl5_redStud_img;

    @FXML
    public Text isl5_redStudCount_text;

    @FXML
    public ImageView isl5_blueStud_img;

    @FXML
    public Text isl5_blueStudCount_text;

    @FXML
    public ImageView isl5_pinkStud_img;

    @FXML
    public Text isl5_pinkStudCount_text;

    @FXML
    public ImageView isl5_greenStud_img;

    @FXML
    public Text isl5_greenStudCount_text;

    @FXML
    public ImageView isl5_motherNature_img;

    @FXML
    public ImageView isl5_noEntryTile_img;

    @FXML
    public Text isl5_noEntryTilesCount_text;

    // endregion Island5

    // region Island6

    @FXML
    public AnchorPane isl6_pane;

    @FXML
    public ImageView isl6_img;

    @FXML
    public ImageView isl6_tower_img;

    @FXML
    public Text isl6_towersCount_text;

    @FXML
    public ImageView isl6_yellowStud_img;

    @FXML
    public Text isl6_yellowStudCount_text;

    @FXML
    public ImageView isl6_redStud_img;

    @FXML
    public Text isl6_redStudCount_text;

    @FXML
    public ImageView isl6_blueStud_img;

    @FXML
    public Text isl6_blueStudCount_text;

    @FXML
    public ImageView isl6_pinkStud_img;

    @FXML
    public Text isl6_pinkStudCount_text;

    @FXML
    public ImageView isl6_greenStud_img;

    @FXML
    public Text isl6_greenStudCount_text;

    @FXML
    public ImageView isl6_motherNature_img;

    @FXML
    public ImageView isl6_noEntryTile_img;

    @FXML
    public Text isl6_noEntryTilesCount_text;

    // endregion Island6

    // region Island7

    @FXML
    public AnchorPane isl7_pane;

    @FXML
    public ImageView isl7_img;

    @FXML
    public ImageView isl7_tower_img;

    @FXML
    public Text isl7_towersCount_text;

    @FXML
    public ImageView isl7_yellowStud_img;

    @FXML
    public Text isl7_yellowStudCount_text;

    @FXML
    public ImageView isl7_redStud_img;

    @FXML
    public Text isl7_redStudCount_text;

    @FXML
    public ImageView isl7_blueStud_img;

    @FXML
    public Text isl7_blueStudCount_text;

    @FXML
    public ImageView isl7_pinkStud_img;

    @FXML
    public Text isl7_pinkStudCount_text;

    @FXML
    public ImageView isl7_greenStud_img;

    @FXML
    public Text isl7_greenStudCount_text;

    @FXML
    public ImageView isl7_motherNature_img;

    @FXML
    public ImageView isl7_noEntryTile_img;

    @FXML
    public Text isl7_noEntryTilesCount_text;

    // endregion Island7

    // region Island8

    @FXML
    public AnchorPane isl8_pane;

    @FXML
    public ImageView isl8_img;

    @FXML
    public ImageView isl8_tower_img;

    @FXML
    public Text isl8_towersCount_text;

    @FXML
    public ImageView isl8_yellowStud_img;

    @FXML
    public Text isl8_yellowStudCount_text;

    @FXML
    public ImageView isl8_redStud_img;

    @FXML
    public Text isl8_redStudCount_text;

    @FXML
    public ImageView isl8_blueStud_img;

    @FXML
    public Text isl8_blueStudCount_text;

    @FXML
    public ImageView isl8_pinkStud_img;

    @FXML
    public Text isl8_pinkStudCount_text;

    @FXML
    public ImageView isl8_greenStud_img;

    @FXML
    public Text isl8_greenStudCount_text;

    @FXML
    public ImageView isl8_motherNature_img;

    @FXML
    public ImageView isl8_noEntryTile_img;

    @FXML
    public Text isl8_noEntryTilesCount_text;

    // endregion Island8

    // region Island9

    @FXML
    public AnchorPane isl9_pane;

    @FXML
    public ImageView isl9_img;

    @FXML
    public ImageView isl9_tower_img;

    @FXML
    public Text isl9_towersCount_text;

    @FXML
    public ImageView isl9_yellowStud_img;

    @FXML
    public Text isl9_yellowStudCount_text;

    @FXML
    public ImageView isl9_redStud_img;

    @FXML
    public Text isl9_redStudCount_text;

    @FXML
    public ImageView isl9_blueStud_img;

    @FXML
    public Text isl9_blueStudCount_text;

    @FXML
    public ImageView isl9_pinkStud_img;

    @FXML
    public Text isl9_pinkStudCount_text;

    @FXML
    public ImageView isl9_greenStud_img;

    @FXML
    public Text isl9_greenStudCount_text;

    @FXML
    public ImageView isl9_motherNature_img;

    @FXML
    public ImageView isl9_noEntryTile_img;

    @FXML
    public Text isl9_noEntryTilesCount_text;

    // endregion Island9

    // region Island10

    @FXML
    public AnchorPane isl10_pane;

    @FXML
    public ImageView isl10_img;

    @FXML
    public ImageView isl10_tower_img;

    @FXML
    public Text isl10_towersCount_text;

    @FXML
    public ImageView isl10_yellowStud_img;

    @FXML
    public Text isl10_yellowStudCount_text;

    @FXML
    public ImageView isl10_redStud_img;

    @FXML
    public Text isl10_redStudCount_text;

    @FXML
    public ImageView isl10_blueStud_img;

    @FXML
    public Text isl10_blueStudCount_text;

    @FXML
    public ImageView isl10_pinkStud_img;

    @FXML
    public Text isl10_pinkStudCount_text;

    @FXML
    public ImageView isl10_greenStud_img;

    @FXML
    public Text isl10_greenStudCount_text;

    @FXML
    public ImageView isl10_motherNature_img;

    @FXML
    public ImageView isl10_noEntryTile_img;

    @FXML
    public Text isl10_noEntryTilesCount_text;

    // endregion Island10

    // region Island11

    @FXML
    public AnchorPane isl11_pane;

    @FXML
    public ImageView isl11_img;

    @FXML
    public ImageView isl11_tower_img;

    @FXML
    public Text isl11_towersCount_text;

    @FXML
    public ImageView isl11_yellowStud_img;

    @FXML
    public Text isl11_yellowStudCount_text;

    @FXML
    public ImageView isl11_redStud_img;

    @FXML
    public Text isl11_redStudCount_text;

    @FXML
    public ImageView isl11_blueStud_img;

    @FXML
    public Text isl11_blueStudCount_text;

    @FXML
    public ImageView isl11_pinkStud_img;

    @FXML
    public Text isl11_pinkStudCount_text;

    @FXML
    public ImageView isl11_greenStud_img;

    @FXML
    public Text isl11_greenStudCount_text;

    @FXML
    public ImageView isl11_motherNature_img;

    @FXML
    public ImageView isl11_noEntryTile_img;

    @FXML
    public Text isl11_noEntryTilesCount_text;

    // endregion Island11

    // endregion Islands

    // region CloudTiles

    @FXML
    public GridPane cloudTiles_grid;

    // region CloudTile0

    @FXML
    public AnchorPane grid00_pane;

    @FXML
    public AnchorPane cloudTile0_pane;

    @FXML
    public ImageView cloud0_img;

    @FXML
    public ImageView CT0_student0_img;

    @FXML
    public ImageView CT0_student1_img;

    @FXML
    public ImageView CT0_student2_img;

    @FXML
    public ImageView CT0_student3_img;

    // endregion CloudTile0

    // region CloudTile1

    @FXML
    public AnchorPane grid01_pane;

    @FXML
    public AnchorPane cloudTile1_pane;

    @FXML
    public ImageView cloud1_img;

    @FXML
    public ImageView CT1_student0_img;

    @FXML
    public ImageView CT1_student1_img;

    @FXML
    public ImageView CT1_student2_img;

    @FXML
    public ImageView CT1_student3_img;

    // endregion CloudTile1

    // region CloudTile2

    @FXML
    public AnchorPane grid10_pane;

    @FXML
    public AnchorPane cloudTile2_pane;

    @FXML
    public ImageView cloud2_img;

    @FXML
    public ImageView CT2_student0_img;

    @FXML
    public ImageView CT2_student1_img;

    @FXML
    public ImageView CT2_student2_img;

    @FXML
    public ImageView CT2_student3_img;

    // endregion CloudTile2

    // region CloudTile3

    @FXML
    public AnchorPane grid11_pane;

    @FXML
    public AnchorPane cloudTile3_pane;

    @FXML
    public ImageView cloud3_img;

    @FXML
    public ImageView CT3_student0_img;

    @FXML
    public ImageView CT3_student1_img;

    @FXML
    public ImageView CT3_student2_img;

    @FXML
    public ImageView CT3_student3_img;

    // endregion CloudTile3

    // endregion CloudTiles

    // region Input

    @FXML
    public AnchorPane input_pane;

    @FXML
    public Rectangle input_background;

    @FXML
    public Label input_label;

    @FXML
    public Spinner<Integer> input_spinner;

    @FXML
    public Button input_button;

    // endregion Input

    // endregion Sky

    // region ExpertMode

    @FXML
    public AnchorPane characterCards_pane;

    // region CharacterCards

    @FXML
    public Rectangle characterBackground_game;

    // region CharacterCard0

    @FXML
    public ImageView characterCard0_img;

    @FXML
    public ImageView CC0_hasCoin_img;

    @FXML
    public Rectangle CC0_elem_background;

    @FXML
    public GridPane CC0_gridPane;

    @FXML
    public ImageView CC0_elem0_img;

    @FXML
    public ImageView CC0_elem1_img;

    @FXML
    public ImageView CC0_elem2_img;

    @FXML
    public ImageView CC0_elem3_img;

    @FXML
    public ImageView CC0_elem4_img;

    @FXML
    public ImageView CC0_elem5_img;

    // endregion CharacterCard0

    // region CharacterCard1

    @FXML
    public ImageView characterCard1_img;

    @FXML
    public ImageView CC1_hasCoin_img;

    @FXML
    public Rectangle CC1_elem_background;

    @FXML
    public GridPane CC1_gridPane;

    @FXML
    public ImageView CC1_elem0_img;

    @FXML
    public ImageView CC1_elem1_img;

    @FXML
    public ImageView CC1_elem2_img;

    @FXML
    public ImageView CC1_elem3_img;

    @FXML
    public ImageView CC1_elem4_img;

    @FXML
    public ImageView CC1_elem5_img;

    // endregion CharacterCard1

    // region CharacterCard2

    @FXML
    public ImageView characterCard2_img;

    @FXML
    public ImageView CC2_hasCoin_img;

    @FXML
    public Rectangle CC2_elem_background;

    @FXML
    public GridPane CC2_gridPane;

    @FXML
    public ImageView CC2_elem0_img;

    @FXML
    public ImageView CC2_elem1_img;

    @FXML
    public ImageView CC2_elem2_img;

    @FXML
    public ImageView CC2_elem3_img;

    @FXML
    public ImageView CC2_elem4_img;

    @FXML
    public ImageView CC2_elem5_img;

    // endregion CharacterCard3

    // endregion CharacterCards

    // region Coins

    @FXML
    public Rectangle coin_background_img;

    @FXML
    public ImageView coinCount_img;

    @FXML
    public Text coinCount_text;

    // endregion Coins

    // endregion ExpertMode

    // region AssistantCards

    @FXML
    public AnchorPane assistantCards_pane;

    @FXML
    public Rectangle assistantBackground_game;

    @FXML
    public GridPane AC_gridPane;

    @FXML
    public ImageView assistant1_game;

    @FXML
    public ImageView assistant2_game;

    @FXML
    public ImageView assistant3_game;

    @FXML
    public ImageView assistant4_game;

    @FXML
    public ImageView assistant5_game;

    @FXML
    public ImageView assistant6_game;

    @FXML
    public ImageView assistant7_game;

    @FXML
    public ImageView assistant8_game;

    @FXML
    public ImageView assistant9_game;

    @FXML
    public ImageView assistant10_game;

    // endregion AssistantCards

    // region PlayersSchoolBoardsButton

    @FXML
    public Rectangle playerButton_game;

    @FXML
    public ImageView playersIcon_game;

    // endregion PlayersSchoolBoardsButton

    // region SchoolBoard

    @FXML
    public AnchorPane schoolBoard_pane;

    @FXML
    public ImageView schoolBoard_img;

    // region Towers

    @FXML
    public AnchorPane towerArea_pane;

    @FXML
    public GridPane towers_gridPane;

    @FXML
    public ImageView tower0_img;

    @FXML
    public ImageView tower1_img;

    @FXML
    public ImageView tower2_img;

    @FXML
    public ImageView tower3_img;

    @FXML
    public ImageView tower4_img;

    @FXML
    public ImageView tower5_img;

    @FXML
    public ImageView tower6_img;

    @FXML
    public ImageView tower7_img;

    // endregion Towers

    // region DiningRoom

    @FXML
    public AnchorPane diningRoom_pane;

    // region ProfessorTable

    @FXML
    public AnchorPane professorTable_pane;

    @FXML
    public ImageView greenProfessor_img;

    @FXML
    public ImageView redProfessor_img;

    @FXML
    public ImageView yellowProfessor_img;

    @FXML
    public ImageView pinkProfessor_img;

    @FXML
    public ImageView blueProfessor_img;

    // endregion ProfessorTable

    // region GreenDiningRoom

    @FXML
    public AnchorPane greenDiningRoom_pane;

    @FXML
    public ImageView greenDR_student0_img;

    @FXML
    public ImageView greenDR_student1_img;

    @FXML
    public ImageView greenDR_student2_img;

    @FXML
    public ImageView greenDR_student3_img;

    @FXML
    public ImageView greenDR_student4_img;

    @FXML
    public ImageView greenDR_student5_img;

    @FXML
    public ImageView greenDR_student6_img;

    @FXML
    public ImageView greenDR_student7_img;

    @FXML
    public ImageView greenDR_student8_img;

    @FXML
    public ImageView greenDR_student9_img;

    // endregion GreenDiningRoom

    // region RedDiningRoom

    @FXML
    public AnchorPane redDiningRoom_pane;

    @FXML
    public ImageView redDR_student0_img;

    @FXML
    public ImageView redDR_student1_img;

    @FXML
    public ImageView redDR_student2_img;

    @FXML
    public ImageView redDR_student3_img;

    @FXML
    public ImageView redDR_student4_img;

    @FXML
    public ImageView redDR_student5_img;

    @FXML
    public ImageView redDR_student6_img;

    @FXML
    public ImageView redDR_student7_img;

    @FXML
    public ImageView redDR_student8_img;

    @FXML
    public ImageView redDR_student9_img;

    // endregion RedDiningRoom

    // region YellowDiningRoom

    @FXML
    public AnchorPane yellowDiningRoom_pane;

    @FXML
    public ImageView yellowDR_student0_img;

    @FXML
    public ImageView yellowDR_student1_img;

    @FXML
    public ImageView yellowDR_student2_img;

    @FXML
    public ImageView yellowDR_student3_img;

    @FXML
    public ImageView yellowDR_student4_img;

    @FXML
    public ImageView yellowDR_student5_img;

    @FXML
    public ImageView yellowDR_student6_img;

    @FXML
    public ImageView yellowDR_student7_img;

    @FXML
    public ImageView yellowDR_student8_img;

    @FXML
    public ImageView yellowDR_student9_img;

    // endregion RedDiningRoom

    // region PinkDiningRoom

    @FXML
    public AnchorPane pinkDiningRoom_pane;

    @FXML
    public ImageView pinkDR_student0_img;

    @FXML
    public ImageView pinkDR_student1_img;

    @FXML
    public ImageView pinkDR_student2_img;

    @FXML
    public ImageView pinkDR_student3_img;

    @FXML
    public ImageView pinkDR_student4_img;

    @FXML
    public ImageView pinkDR_student5_img;

    @FXML
    public ImageView pinkDR_student6_img;

    @FXML
    public ImageView pinkDR_student7_img;

    @FXML
    public ImageView pinkDR_student8_img;

    @FXML
    public ImageView pinkDR_student9_img;

    // endregion PinkDiningRoom

    // region BlueDiningRoom

    @FXML
    public AnchorPane blueDiningRoom_pane;

    @FXML
    public ImageView blueDR_student0_img;

    @FXML
    public ImageView blueDR_student1_img;

    @FXML
    public ImageView blueDR_student2_img;

    @FXML
    public ImageView blueDR_student3_img;

    @FXML
    public ImageView blueDR_student4_img;

    @FXML
    public ImageView blueDR_student5_img;

    @FXML
    public ImageView blueDR_student6_img;

    @FXML
    public ImageView blueDR_student7_img;

    @FXML
    public ImageView blueDR_student8_img;

    @FXML
    public ImageView blueDR_student9_img;

    // endregion BlueDiningRoom

    // endregion DiningRoom

    // region Entrance

    @FXML
    public AnchorPane entrance_pane;

    @FXML
    public ImageView E_student0_img;

    @FXML
    public ImageView E_student1_img;

    @FXML
    public ImageView E_student2_img;

    @FXML
    public ImageView E_student3_img;

    @FXML
    public ImageView E_student4_img;

    @FXML
    public ImageView E_student5_img;

    @FXML
    public ImageView E_student6_img;

    @FXML
    public ImageView E_student7_img;

    @FXML
    public ImageView E_student8_img;

    // endregion Entrance

    // endregion SchoolBoard

    // endregion FXML_Ids

    // TODO Full implementation

    // region GSUpdateModel

    /**
     * Updates the GameScene according to the updated model
     * @param model the model to update
     * @param player the localPlayer
     */
    public void gsUpdateModel(GameModel model, Player player, Set<GameValues> updatedValues) {
        boolean containsModel = updatedValues.contains(GameValues.MODEL);

        if (containsModel || updatedValues.contains(GameValues.ISLANDARRAY) || updatedValues.contains(GameValues.MOTHERNATURE)) {
            // Updates the islands
            gsUpdateIslands(model.getIslands(), model.getMotherNaturePosition());
        }

        if (containsModel || updatedValues.contains(GameValues.CLOUDARRAY)) {
            // Updates the cloudTiles
            gsUpdateCloudTiles(model.getCloudTile(), model.getPlayersCount());
        }

        if (containsModel || updatedValues.contains(GameValues.PLAYERARRAY)) {
            // Updates the AssistantCards deck
            gsUpdateAssistantDeck(player.getAssistantDeck(), player.getPlayerWizard());
        }

        if (containsModel || updatedValues.contains(GameValues.SCHOOLBOARDARRAY) || updatedValues.contains(GameValues.ENTRANCE)
                          || updatedValues.contains(GameValues.ENTRANCEARRAY)    || updatedValues.contains(GameValues.DININGROOM)
                          || updatedValues.contains(GameValues.DININGROOMARRAY)  || updatedValues.contains(GameValues.PLAYERARRAY)
                          || updatedValues.contains(GameValues.GLOBALPROFESSORTABLE)                                              ){
            // Updates the SchoolBoard
            gsUpdateSchoolBoard(model, player, updatedValues);
        }

        // If the game is in expertMode
        if (model.getExpertMode()) {
            // Updates the expertMode elements
            gsUpdateExpertModeElements(model, player, updatedValues);

            // Shows the ExpertMode pane
            characterCards_pane.setVisible(true);
        }
        else {
            // Hides the ExpertMode pane
            characterCards_pane.setVisible(false);
        }
    }

    // region GSUpdateSky

    /**
     * Updates the Islands in the GameScene according to the array of islands given
     * @param islands the array of islands to update
     * @param motherNaturePosition the position of motherNature
     */
    public void gsUpdateIslands(Island[] islands, int motherNaturePosition){
        // Tower img
        ImageView  islandTower;
        TowerColor islandTowerColor;
        String     islandTowerPath;
        // TowerCounter
        Text       islandsTowersText;
        int        islandTowersCount;
        // Student img
        ImageView  islandStudent;
        // StudentCounter
        Text       islandStudentText;
        int        islandStudentCount;
        // MotherNature
        ImageView  islandMotherNature;
        // NoEntryTile
        ImageView  islandNoEntryTile;
        // NoEntryTilesCount
        Text       islandNoEntryTilesCountText;
        int        islandNoEntryTilesCount;

        // For each island present in the islands array
        for (int i = 0; i < islands.length; i++) {
            // Getting the ids and the data from the model
            islandTower       = IDHelper.gsFindTowerOnIslandID(this, i);
            islandsTowersText = IDHelper.gsFindTowerCountOnIslandID(this, i);
            islandTowerColor  = islands[i].getTowerColor();
            islandTowersCount = islands[i].getMultiplicity();
            if (islandTowerColor != null){
                // Set the tower img
                islandTowerPath = PathHelper.fromTowerColorToHandlerPath(islands[i].getTowerColor());
                islandTower.setImage(new Image(getClass().getResourceAsStream(islandTowerPath)));

                // Set the towersCount
                islandsTowersText.setText(String.valueOf(islandTowersCount));
                islandsTowersText.setVisible(true);
            }
            else {
                // If the island does not exist, the islandTower is removed and its count is hidden
                islandTower.setImage(null);
                islandsTowersText.setVisible(false);
            }

            // For each student on the island
            for (Color student : Color.values()) {
                // Set Student img and the studentsCount
                islandStudent      = IDHelper.gsFindStudentOnIslandID(this, i, student);
                islandStudentText  = IDHelper.gsFindStudentCounterOnIslandID(this, i, student);
                islandStudentCount = islands[i].getStudentCounters(student);
                if (islandStudentCount > 0) {
                    islandStudent.setVisible(true);
                    islandStudentText.setText(String.valueOf(islandStudentCount));
                    islandStudentText.setVisible(true);
                }
                else {
                    islandStudent.setVisible(false);
                    islandStudentText.setVisible(false);
                }
            }

            // Set MotherNature img
            islandMotherNature = IDHelper.gsFindMotherNatureOnIslandID(this, i);
            islandMotherNature.setVisible(i == motherNaturePosition);

            // Set NoEntryTile img and NoEntryTilesCount
            islandNoEntryTile = IDHelper.gsFindNoEntryTileImgOnIslandID(this, i);
            islandNoEntryTilesCountText = IDHelper.gsFindNoEntryTileCountOnIslandID(this, i);
            islandNoEntryTilesCount  = islands[i].getNoEntryTileCount();
            if (islandNoEntryTilesCount > 0) {
                islandNoEntryTile.setVisible(true);
                islandNoEntryTilesCountText.setText(String.valueOf(islandNoEntryTilesCount));
                islandNoEntryTilesCountText.setVisible(true);
            }
            else {
                islandNoEntryTile.setVisible(false);
                islandNoEntryTilesCountText.setVisible(false);
            }
        }

        AnchorPane islandPane;
        // For each island absent in the islands array
        for (int i = islands.length; i < ViewGUI.MAX_ISLANDS; i++) {
            islandPane = IDHelper.gsFindIslandAnchorPaneID(this, i);
            islandPane.setVisible(false);
        }
    }

    /**
     * Updates the CloudTiles in the GameScene according to the array of cloudTiles given
     * @param cloudTiles the array of cloudTiles to update
     * @param numOfPlayers the number of players in the game
     */
    public void gsUpdateCloudTiles(CloudTile[] cloudTiles, int numOfPlayers) {
        ImageView cloudTileStudent;
        Color     cloudTileStudentColor;
        String    cloudTileStudentPath;
        int       cloudTileMaxNumOfStudents = 0;
        // For each cloudTile (one per player)
        for (int i = 0; i < numOfPlayers; i++) {

            // The number of students on the cloudTiles depends on the number of players
            switch (numOfPlayers) {
                case 2, 4 -> cloudTileMaxNumOfStudents = 3;
                case 3    -> cloudTileMaxNumOfStudents = 4;
                default   -> throw new IllegalStateException("The number of players must be between 2 and 4. Here the players are " + numOfPlayers);
            }

            // For each student on the cloudTile
            for (int j = 0; j < cloudTileMaxNumOfStudents; j++) {
                // Gets the id and the path of the image
                cloudTileStudent      = IDHelper.gsFindCloudStudentID(this, i, j);
                cloudTileStudentColor = cloudTiles[i].getStudents()[i];

                // For each student present on the island
                if (cloudTileStudentColor != null){
                    // Sets the image to the specified studentColor
                    cloudTileStudentPath = PathHelper.fromStudentColorToHandlerPath(cloudTileStudentColor);
                    cloudTileStudent.setImage(new Image(getClass().getResourceAsStream(cloudTileStudentPath)));
                }
                else {
                    // For each student non-present remove the student
                    cloudTileStudent.setImage(null);
                }
            }
        }

        AnchorPane cloudAnchorPaneID;
        // For each not iterated cloud hide it from the board
        for (int i = numOfPlayers; i < ViewGUI.MAX_CLOUD_TILES; i++) {
            cloudAnchorPaneID = IDHelper.gsFindCloudAnchorPaneID(this, i);
            cloudAnchorPaneID.setVisible(false);
        }
    }

    // endregion GSUpdateSky

    /**
     * Updates the assistantCard deck in the GameScene of the player by "flipping" cards if not present
     * @param assistantDeck the deck of assistantCards to update
     * @param wizard the wizard associated with the player
     */
    public void gsUpdateAssistantDeck(AssistantCard[] assistantDeck, Wizard wizard) {
        // A deck with all the possible assistantCards
        AssistantCard[] fullDeck = new AssistantCard[10];

        // Initializing the full deck
        for (int i = 0; i < ViewGUI.MAX_ASSISTANT_CARDS; ++i) {
            fullDeck[i] = new AssistantCard(i + 1, (i / 2) + 1);
        }

        // Changing the assistantCard deck of the player into a Set
        Set<AssistantCard> availableAssistantCardsSet = new HashSet<>();
        Collections.addAll(availableAssistantCardsSet, assistantDeck);

        ImageView assistantCardID;
        String    assistantCardWizardPath;
        // For each assistantCard in a fullDeck (i.e. for each card existent)
        for (AssistantCard assistantCard : fullDeck) {
            // If the card isn't in the availableDeck -> Flip the card
            if (!availableAssistantCardsSet.contains(assistantCard)){
                // Gets the ID of the assistantCard from the assistantCard enum
                assistantCardID         = IDHelper.gsFindAssistantCardID(this, assistantCard);

                // Finds the handlerPath of the corresponding image that I need to put (wizard)
                assistantCardWizardPath = PathHelper.fromWizardEnumToHandlerPath(wizard);

                // Sets the image using the path just found
                assistantCardID.setImage(new Image(getClass().getResourceAsStream(assistantCardWizardPath)));
            }
        }
    }

    // region GSUpdateSchoolBoard

    /**
     * Updates the SchoolBoard in the GameScene according to the updated model
     * @param model the model to update
     * @param player the localPlayer
     */
    public void gsUpdateSchoolBoard(GameModel model, Player player, Set<GameValues> updatedValues) {
        SchoolBoard schoolBoard = player.getSchoolBoard();
        boolean containsModel = updatedValues.contains(GameValues.MODEL);

        if (containsModel || updatedValues.contains(GameValues.SCHOOLBOARDARRAY) || updatedValues.contains(GameValues.PLAYERARRAY)) {
            // Updates the towers of the player
            gsUpdateTowers(schoolBoard.getTowerColor(), schoolBoard.getTowerCount(), model.getPlayersCount());
        }

        if (containsModel || updatedValues.contains(GameValues.SCHOOLBOARDARRAY) || updatedValues.contains(GameValues.DININGROOM)
                          || updatedValues.contains(GameValues.DININGROOMARRAY)  || updatedValues.contains(GameValues.PLAYERARRAY)
                          || updatedValues.contains(GameValues.GLOBALPROFESSORTABLE)                                              ) {
            // Updates the diningRoom
            gsUpdateDiningRoom(player.getSchoolBoard().getDiningRoom(), player, model.getGlobalProfessorTable(), updatedValues);
        }

        if (containsModel || updatedValues.contains(GameValues.SCHOOLBOARDARRAY) || updatedValues.contains(GameValues.ENTRANCE)
                          || updatedValues.contains(GameValues.ENTRANCEARRAY)    || updatedValues.contains(GameValues.PLAYERARRAY)) {
            // Updates the entrance
            gsUpdateEntrance(schoolBoard.getEntrance());
        }
    }

    /**
     * Updates the towers in the GameScene of the player (SchoolBoard)
     * @param towerColor the color of the towers (BLACK, GREY, WHITE)
     * @param towerCount the number of towers present
     * @param numOfPlayers the number of players in the game
     */
    public void gsUpdateTowers(TowerColor towerColor, int towerCount, int numOfPlayers) {
        ImageView schoolBoardTowerID;
        String    towerPath;
        // Adding the towers
        for (int i = 0; i < towerCount; i++) {
            //Update towers (by adding them if not present) by setting the correct directory for each ImageView
            schoolBoardTowerID = IDHelper.gsFindSchoolBoardTowerID(this, i);
            towerPath          = PathHelper.fromTowerColorToHandlerPath(towerColor);
            schoolBoardTowerID.setImage(new Image(getClass().getClassLoader().getResource(towerPath).toString(), true));
        }

        // The maximum number of towers varies depending on the number of players
        int       maxNumOfTowers;
        switch (numOfPlayers) {
            case 2, 4 -> maxNumOfTowers = 8;
            case 3    -> maxNumOfTowers = 6;
            default   -> throw new IllegalStateException("The number of players must be between 2 and 4. Here the players are " + numOfPlayers);
        }
        // Remove the towers between towerCount and maxNumOfTowers
        for (int i = towerCount; i < maxNumOfTowers; i++) {
            schoolBoardTowerID = IDHelper.gsFindSchoolBoardTowerID(this, i);
            schoolBoardTowerID.setImage(null);
        }
    }

    // region GSUpdateDiningRoom

    /**
     * Updates the DiningRoom in the GameScene according to the updated model
     * @param diningRoom the diningRoom to update
     * @param player the localPlayer
     * @param gpt the globalProfessorTable to update
     */
    public void gsUpdateDiningRoom(DiningRoom diningRoom, Player player, GlobalProfessorTable gpt, Set<GameValues> updatedValues) {
        boolean containsModel = updatedValues.contains(GameValues.MODEL);

        if (containsModel || updatedValues.contains(GameValues.GLOBALPROFESSORTABLE)) {
            // Updates the professorsDiningRoom
            gsUpdateDiningRoomProfessors(gpt, player);
        }

        if (containsModel || updatedValues.contains(GameValues.SCHOOLBOARDARRAY) || updatedValues.contains(GameValues.DININGROOM)
                          || updatedValues.contains(GameValues.DININGROOMARRAY)  || updatedValues.contains(GameValues.PLAYERARRAY)) {
            // Updates the studentsDiningRoom
            gsUpdateDiningRoomStudents(diningRoom);
        }
    }

    /**
     * Updates the ProfessorDiningRoom in the GameScene according to the updated model
     * @param gpt the globalProfessorTable to update
     * @param player the localPlayer
     */
    public void gsUpdateDiningRoomProfessors(GlobalProfessorTable gpt, Player player) {
        ImageView gptImageView;
        // For each color (professor) checks if the localPlayer is or not the controller
        for (Color color : Color.values()) {
            gptImageView = IDHelper.gsFindProfessorOnProfTableID(this, color);
            gptImageView.setVisible(gpt.getProfessorLocation(color).equals(player));
        }
    }

    /**
     * Updates the StudentsDiningRoom in the GameScene according to the updated diningRoom
     * @param diningRoom the diningRoom to update
     */
    public void gsUpdateDiningRoomStudents(DiningRoom diningRoom) {
        ImageView diningRoomStudent;

        // For each table (color)
        for (Color color : Color.values()) {
            // "Adds" the Students
            for (int i = 0; i < diningRoom.getStudentCounters(color); i++) {
                // Gets the ID of the student in the position i in the diningRoom
                diningRoomStudent     = IDHelper.gsFindStudentDiningRoomID(this, color, i);

                // Sets the image to visible ("adding it")
                diningRoomStudent.setVisible(true);
            }

            // "Removes" the students
            for (int i = diningRoom.getStudentCounters(color); i < ViewGUI.MAX_DINING_ROOM_STUDENTS; i++) {
                // Gets the ID of the student in the position i in the diningRoom
                diningRoomStudent     = IDHelper.gsFindStudentDiningRoomID(this, color, i);

                // Sets the image to not visible ("removing it")
                diningRoomStudent.setVisible(false);
            }
        }
    }

    // endregion GSUpdateDiningRoom

    /**
     * Updates the Entrance in the GameScene according to the updated entrance
     * @param entrance the entrance to update
     */
    public void gsUpdateEntrance(Entrance entrance) {
        Color     entranceStudentColor;
        ImageView entranceStudent;
        String    entranceStudentPath;

        // For each student in the entrance
        for (int i = 0; i < entrance.getStudents().length; i++) {

            // If the student exists
            if (entrance.getStudents()[i] != null) {

                // Gets the Color of the student in the position i
                entranceStudentColor = entrance.getStudents()[i];

                // Gets the ID of the student in the position i in the entrance
                entranceStudent      = IDHelper.gsFindStudentEntranceID(this, i);

                // Finds the handlerPath of the corresponding image that I need to put in the entrance
                entranceStudentPath  = PathHelper.fromStudentColorToHandlerPath(entranceStudentColor);

                // Sets the image using the path just found
                entranceStudent.setImage(new Image(getClass().getClassLoader().getResource(entranceStudentPath).toString(), true));
            }

            else {
                // Gets the ID of the student in the position i in the entrance
                entranceStudent     = IDHelper.gsFindStudentEntranceID(this, i);

                // Removes the image present
                entranceStudent.setImage(null);
            }
        }
    }

    // endregion GSUpdateSchoolBoard

    // region GSUpdateExpertMode

    /**
     * Updates the ExpertModeElements in the GameScene according to the updated model
     * @param model the model to update
     * @param player the localPlayer
     */
    public void gsUpdateExpertModeElements(GameModel model, Player player, Set<GameValues> updatedValues) {
        boolean containsModel = updatedValues.contains(GameValues.MODEL);

        if (containsModel || updatedValues.contains(GameValues.CHARACTERCARDARRAY)) {
            // Updates the CharacterCards
            gsUpdateCharacterCards(model.getCharacterCards());
        }

        if (containsModel || updatedValues.contains(GameValues.PLAYERARRAY)) {
            // Updates the player's coinsCount
            gsUpdatePlayerCoins(player);
        }
    }

    // region GSUpdateCharacterCards

    /**
     * Updates the CharacterCards in the GameScene according to the updated array
     * @param characterCards the characterCard array to update
     */
    public void gsUpdateCharacterCards(CharacterCard[] characterCards) {
        ImageView hasCoin_img;
        // Based on the type of CharacterCard calls different methods
        for (int i = 0; i < characterCards.length; i++) {
            if (characterCards[i] instanceof CharacterCardNoEntry c) {
                gsUpdateCharacterCardNoEntry(c, i);
            }
            else if (characterCards[i] instanceof CharacterCardStudent c) {
                gsUpdateCharacterCardStudent(c, i);
            }
            else {
                gsUpdateCharacterCard(characterCards[i], i);
            }

            // Update hasCoin
            hasCoin_img = IDHelper.gsFindCharacterCardCoinID(this, i);
            hasCoin_img.setVisible(characterCards[i].getHasCoin());
        }
    }

    /**
     * Updates the CharacterCardNoEntry in the GameScene according to the updated card
     * @param characterCardNoEntry the updated card
     * @param index the index of the card in the GameScene
     */
    public void gsUpdateCharacterCardNoEntry(CharacterCardNoEntry characterCardNoEntry, int index) {
        // Updates the CharacterCardImage
        gsUpdateCharacterCardImage(characterCardNoEntry, index);

        // Shows the Rectangle that holds the noEntryTiles
        Rectangle CC_noEntryTile_background;
        CC_noEntryTile_background = IDHelper.gsFindCharacterCardRectangleElementsID(this, index);
        CC_noEntryTile_background.setVisible(true);

        ImageView CC_noEntryTile;
        String    CC_noEntryTile_ImgPath = PathHelper.fromImageTypesToHandlerPath(ImageTypes.NOENTRYTILE_IMG);
        // For each noEntryTile present, sets the image
        for (int i = 0; i < characterCardNoEntry.getNoEntryCount(); i++) {
            CC_noEntryTile = IDHelper.gsFindCharacterCardElementID(this, index, i);
            CC_noEntryTile.setImage(new Image(getClass().getResourceAsStream(CC_noEntryTile_ImgPath)));
        }

        // For each noEntryTile absent, remove the img
        for (int i = characterCardNoEntry.getNoEntryCount(); i < ViewGUI.MAX_CC_ELEMENTS; i++) {
            CC_noEntryTile = IDHelper.gsFindCharacterCardElementID(this, index, i);
            CC_noEntryTile.setImage(null);
        }
    }

    /**
     * Updates the CharacterCardStudent in the GameScene according to the updated card
     * @param characterCardStudent the updated card
     * @param index the index of the card in the GameScene
     */
    public void gsUpdateCharacterCardStudent(CharacterCardStudent characterCardStudent, int index) {
        // Updates the CharacterCardImage
        gsUpdateCharacterCardImage(characterCardStudent, index);

        // Shows the Rectangle that holds the students
        Rectangle CC_students_background;
        CC_students_background = IDHelper.gsFindCharacterCardRectangleElementsID(this, index);
        CC_students_background.setVisible(true);

        ImageView CC_student;
        Color     CC_student_Color;
        String    CC_student_ImgPath;
        // For each student present, sets the image depending on the studentColor
        for (int i = 0; i < characterCardStudent.getStudents().length; i++) {
            CC_student         = IDHelper.gsFindCharacterCardElementID(this, index, i);
            CC_student_Color   = characterCardStudent.getStudents()[i];
            CC_student_ImgPath = PathHelper.fromStudentColorToHandlerPath(CC_student_Color);
            CC_student.setImage(new Image(getClass().getResourceAsStream(CC_student_ImgPath)));
        }

        // For each student absent, remove the img
        for (int i = characterCardStudent.getStudents().length; i < ViewGUI.MAX_CC_ELEMENTS; i++) {
            CC_student = IDHelper.gsFindCharacterCardElementID(this, index, i);
            CC_student.setImage(null);
        }
    }

    /**
     * Updates the CharacterCard in the GameScene according to the updated card
     * @param characterCard the updated card
     * @param index the index of the card in the GameScene
     */
    public void gsUpdateCharacterCard(CharacterCard characterCard, int index) {
        // Updates the CharacterCardImage
        gsUpdateCharacterCardImage(characterCard, index);

        // Hides the Rectangle that holds different elements
        Rectangle CC_students_background;
        CC_students_background = IDHelper.gsFindCharacterCardRectangleElementsID(this, index);
        CC_students_background.setVisible(false);
    }

    /**
     * Updates the CharacterCard image in the GameScene according to the updated card and index
     * @param characterCard the updated card
     * @param index the index of the card in the GameScene
     */
    public void gsUpdateCharacterCardImage(CharacterCard characterCard, int index) {
        ImageView CC_ImgView;
        String    CC_ImgPath;

        // Gets the character corresponding to the card
        Character character = characterCard.getCharacter();

        // Gets the ID of the card
        CC_ImgView = IDHelper.gsFindCharacterCardImageID(this, index);

        // Gets the path of the image
        CC_ImgPath = PathHelper.fromCharacterEnumToFXMLPath(character);

        // Sets the image
        CC_ImgView.setImage(new Image(getClass().getResourceAsStream(CC_ImgPath)));
    }

    // endregion GSUpdateCharacterCards

    /**
     * Updates the CoinCount of the localPlayer in the GameScene according to the updated localPlayer
     * @param player the updated localPlayer
     */
    public void gsUpdatePlayerCoins(Player player){
        int coinCount = 1;

        // The player could be different instances, so I call the method on both
        if (player instanceof PlayerExpert p){
            coinCount = p.getCoinCount();
        }
        else if (player instanceof PlayerTeamExpert p){
            coinCount = p.getCoinCount();
        }

        // Sets the CoinCount in the GameScene
        coinCount_text.setText(String.valueOf(coinCount));

        // Makes the CoinCount visible
        coinCount_text.setVisible(true);
    }

    // endregion GSUpdateExpertMode

    // endregion GSUpdateModel

    public void activateClicksCharacterCards(CharacterCard[] playableCharacterCards) {

        Set<CharacterCard> playableCharacterCardsSet = new HashSet<>();
        Collections.addAll(playableCharacterCardsSet, playableCharacterCards);

        CharacterCard[] characterCardsModel = gui.getModel().getCharacterCards();
        int             characterCardIndex;
        ImageView       characterCardImgView;

        for (int i = 0; i < characterCardsModel.length; i++) {
            if (playableCharacterCardsSet.contains(characterCardsModel[i])) {
                characterCardImgView = IDHelper.gsFindCharacterCardImageID(this, i);

                // Creates a function that will handle the characterCardClick
                EventHandler<MouseEvent> clickOnCharacterCardHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // Invokes the function clickOnCharacterCard
                        clickOnCharacterCard(mouseEvent);

                        mouseEvent.consume();
                    }
                };

                // Then links the created handler to the click of the CCImage
                characterCardImgView.setOnMouseClicked(clickOnCharacterCardHandler);
            }
        }

        Alert possiblePlay = GUIAlert.getAlert(GUIAlert.PLAY_CHARACTERCARD, null);

    }

    // region DeactivateClicks

    public void deactivateClicks() {
        deactivateClicksIslands();
        deactivateClicksCloudTiles();
        deactivateClicksCharacterCards();
        deactivateClicksCharacterCardsElements();
        deactivateClicksDiningRoomTables();
        deactivateClicksAssistantCards();
        deactivateClicksEntranceStudents();
        deactivateClicksInputButton();
    }

    public void deactivateClicksIslands() {
        AnchorPane islandPane;
        for (int i = 0; i < ViewGUI.MAX_ISLANDS; i++) {
            islandPane = IDHelper.gsFindIslandAnchorPaneID(this, i);
            islandPane.setOnMouseClicked(null);
        }
    }

    public void deactivateClicksCloudTiles() {
        AnchorPane cloudTilePane;
        for (int i = 0; i < ViewGUI.MAX_CLOUD_TILES; i++) {
            cloudTilePane = IDHelper.gsFindCloudAnchorPaneID(this, i);
            cloudTilePane.setOnMouseClicked(null);
        }
    }

    public void deactivateClicksCharacterCards() {
        ImageView characterCardsImgView;
        for (int i = 0; i < ViewGUI.MAX_CC; i++) {
            characterCardsImgView = IDHelper.gsFindCharacterCardImageID(this, i);
            characterCardsImgView.setOnMouseClicked(null);
        }
    }

    public void deactivateClicksCharacterCardsElements() {
        ImageView characterCardsElementImgView;
        for (int i = 0; i < ViewGUI.MAX_CC_ELEMENTS; i++) {
            characterCardsElementImgView = IDHelper.gsFindCharacterCardImageID(this, i);
            characterCardsElementImgView.setOnMouseClicked(null);
        }
    }

    public void deactivateClicksDiningRoomTables() {
        AnchorPane diningRoomPane;
        for (Color color : Color.values()) {
            diningRoomPane = IDHelper.gsFindDiningRoomTablePaneID(this, color);
            diningRoomPane.setOnMouseClicked(null);
        }
    }

    public void deactivateClicksAssistantCards() {
        ImageView assistantCardImgView;
        for (int i = 0; i < ViewGUI.MAX_ASSISTANT_CARDS; i++) {
            assistantCardImgView = IDHelper.gsFindAssistantCardID(this, i);
            assistantCardImgView.setOnMouseClicked(null);
        }
    }

    public void deactivateClicksEntranceStudents() {
        ImageView entranceStudentImgView;
        for (int i = 0; i < ViewGUI.MAX_ENTRANCE_STUDENTS; i++) {
            entranceStudentImgView = IDHelper.gsFindStudentEntranceID(this, i);
            entranceStudentImgView.setOnMouseClicked(null);
        }
    }

    public void deactivateClicksInputButton() {
        input_button.setOnMouseClicked(null);
    }


    // endregion DeactivateClicks

    public void gsRequestPlayCharacterCard(CharacterCard[] playableCharacterCards) {
        activateClicksCharacterCards(playableCharacterCards);
    }

    public void clickOnCharacterCard(MouseEvent mouseEvent) {
        deactivateClicksCharacterCards();

        // Gets the characterCard from then mouseEvent
        ImageView selectedCharacterCardID = (ImageView) mouseEvent.getSource();

        // Gets the index from the selected ImgView, then gets the corresponding CharacterCard
        int characterCardIndex = InfoHelper.gsFindCharacterCardIndex(selectedCharacterCardID);
        CharacterCard characterCard = gui.getModel().getCharacterCard(characterCardIndex);

        gui.forwardViewToVirtualController(characterCard);
    }

    /**
     * Sets the ViewGUI at which the GameSceneHandler is related
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }

    // Example of access to the nodes using the filter function
    // pane.getchildren().stream().filter( x -> x instanceOf ...).
}
