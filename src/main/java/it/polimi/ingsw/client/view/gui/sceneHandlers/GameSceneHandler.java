package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handler (or Controller) of the scene GameScene (gamePage.fxml)
 * The game's main scene
 * @author Giovanni Manfredi
 */
public class GameSceneHandler implements GUIHandler {

    private ViewGUI gui;

    // region FXML_Ids

    @FXML
    private BorderPane border_pane;

    @FXML
    private AnchorPane game_pane;

    // region Sky

    @FXML
    private AnchorPane sky_pane;

    // region Islands

    @FXML
    private AnchorPane islands_pane;

    // region Island0

    @FXML
    private AnchorPane isl0_pane;

    @FXML
    private ImageView isl0_img;

    @FXML
    private ImageView isl0_tower_img;

    @FXML
    private Text isl0_towersCount_text;

    @FXML
    private ImageView isl0_yellowStud_img;

    @FXML
    private Text isl0_yellowStudCount_text;

    @FXML
    private ImageView isl0_redStud_img;

    @FXML
    private Text isl0_redStudCount_text;

    @FXML
    private ImageView isl0_blueStud_img;

    @FXML
    private Text isl0_blueStudCount_text;

    @FXML
    private ImageView isl0_pinkStud_img;

    @FXML
    private Text isl0_pinkStudCount_text;

    @FXML
    private ImageView isl0_greenStud_img;

    @FXML
    private Text isl0_greenStudCount_text;

    @FXML
    private ImageView isl0_motherNature_img;

    @FXML
    private ImageView isl0_noEntryTile_img;

    @FXML
    private Text isl0_noEntryTilesCount_text;

    // endregion Island0

    // region Island1

    @FXML
    private AnchorPane isl1_pane;

    @FXML
    private ImageView isl1_img;

    @FXML
    private ImageView isl1_tower_img;

    @FXML
    private Text isl1_towersCount_text;

    @FXML
    private ImageView isl1_yellowStud_img;

    @FXML
    private Text isl1_yellowStudCount_text;

    @FXML
    private ImageView isl1_redStud_img;

    @FXML
    private Text isl1_redStudCount_text;

    @FXML
    private ImageView isl1_blueStud_img;

    @FXML
    private Text isl1_blueStudCount_text;

    @FXML
    private ImageView isl1_pinkStud_img;

    @FXML
    private Text isl1_pinkStudCount_text;

    @FXML
    private ImageView isl1_greenStud_img;

    @FXML
    private Text isl1_greenStudCount_text;

    @FXML
    private ImageView isl1_motherNature_img;

    @FXML
    private ImageView isl1_noEntryTile_img;

    @FXML
    private Text isl1_noEntryTilesCount_text;

    // endregion Island1

    // region Island2

    @FXML
    private AnchorPane isl2_pane;

    @FXML
    private ImageView isl2_img;

    @FXML
    private ImageView isl2_tower_img;

    @FXML
    private Text isl2_towersCount_text;

    @FXML
    private ImageView isl2_yellowStud_img;

    @FXML
    private Text isl2_yellowStudCount_text;

    @FXML
    private ImageView isl2_redStud_img;

    @FXML
    private Text isl2_redStudCount_text;

    @FXML
    private ImageView isl2_blueStud_img;

    @FXML
    private Text isl2_blueStudCount_text;

    @FXML
    private ImageView isl2_pinkStud_img;

    @FXML
    private Text isl2_pinkStudCount_text;

    @FXML
    private ImageView isl2_greenStud_img;

    @FXML
    private Text isl2_greenStudCount_text;

    @FXML
    private ImageView isl2_motherNature_img;

    @FXML
    private ImageView isl2_noEntryTile_img;

    @FXML
    private Text isl2_noEntryTilesCount_text;

    // endregion Island2

    // region Island3

    @FXML
    private AnchorPane isl3_pane;

    @FXML
    private ImageView isl3_img;

    @FXML
    private ImageView isl3_tower_img;

    @FXML
    private Text isl3_towersCount_text;

    @FXML
    private ImageView isl3_yellowStud_img;

    @FXML
    private Text isl3_yellowStudCount_text;

    @FXML
    private ImageView isl3_redStud_img;

    @FXML
    private Text isl3_redStudCount_text;

    @FXML
    private ImageView isl3_blueStud_img;

    @FXML
    private Text isl3_blueStudCount_text;

    @FXML
    private ImageView isl3_pinkStud_img;

    @FXML
    private Text isl3_pinkStudCount_text;

    @FXML
    private ImageView isl3_greenStud_img;

    @FXML
    private Text isl3_greenStudCount_text;

    @FXML
    private ImageView isl3_motherNature_img;

    @FXML
    private ImageView isl3_noEntryTile_img;

    @FXML
    private Text isl3_noEntryTilesCount_text;

    // endregion Island3

    // region Island4

    @FXML
    private AnchorPane isl4_pane;

    @FXML
    private ImageView isl4_img;

    @FXML
    private ImageView isl4_tower_img;

    @FXML
    private Text isl4_towersCount_text;

    @FXML
    private ImageView isl4_yellowStud_img;

    @FXML
    private Text isl4_yellowStudCount_text;

    @FXML
    private ImageView isl4_redStud_img;

    @FXML
    private Text isl4_redStudCount_text;

    @FXML
    private ImageView isl4_blueStud_img;

    @FXML
    private Text isl4_blueStudCount_text;

    @FXML
    private ImageView isl4_pinkStud_img;

    @FXML
    private Text isl4_pinkStudCount_text;

    @FXML
    private ImageView isl4_greenStud_img;

    @FXML
    private Text isl4_greenStudCount_text;

    @FXML
    private ImageView isl4_motherNature_img;

    @FXML
    private ImageView isl4_noEntryTile_img;

    @FXML
    private Text isl4_noEntryTilesCount_text;

    // endregion Island4

    // region Island5

    @FXML
    private AnchorPane isl5_pane;

    @FXML
    private ImageView isl5_img;

    @FXML
    private ImageView isl5_tower_img;

    @FXML
    private Text isl5_towersCount_text;

    @FXML
    private ImageView isl5_yellowStud_img;

    @FXML
    private Text isl5_yellowStudCount_text;

    @FXML
    private ImageView isl5_redStud_img;

    @FXML
    private Text isl5_redStudCount_text;

    @FXML
    private ImageView isl5_blueStud_img;

    @FXML
    private Text isl5_blueStudCount_text;

    @FXML
    private ImageView isl5_pinkStud_img;

    @FXML
    private Text isl5_pinkStudCount_text;

    @FXML
    private ImageView isl5_greenStud_img;

    @FXML
    private Text isl5_greenStudCount_text;

    @FXML
    private ImageView isl5_motherNature_img;

    @FXML
    private ImageView isl5_noEntryTile_img;

    @FXML
    private Text isl5_noEntryTilesCount_text;

    // endregion Island5

    // region Island6

    @FXML
    private AnchorPane isl6_pane;

    @FXML
    private ImageView isl6_img;

    @FXML
    private ImageView isl6_tower_img;

    @FXML
    private Text isl6_towersCount_text;

    @FXML
    private ImageView isl6_yellowStud_img;

    @FXML
    private Text isl6_yellowStudCount_text;

    @FXML
    private ImageView isl6_redStud_img;

    @FXML
    private Text isl6_redStudCount_text;

    @FXML
    private ImageView isl6_blueStud_img;

    @FXML
    private Text isl6_blueStudCount_text;

    @FXML
    private ImageView isl6_pinkStud_img;

    @FXML
    private Text isl6_pinkStudCount_text;

    @FXML
    private ImageView isl6_greenStud_img;

    @FXML
    private Text isl6_greenStudCount_text;

    @FXML
    private ImageView isl6_motherNature_img;

    @FXML
    private ImageView isl6_noEntryTile_img;

    @FXML
    private Text isl6_noEntryTilesCount_text;

    // endregion Island6

    // region Island7

    @FXML
    private AnchorPane isl7_pane;

    @FXML
    private ImageView isl7_img;

    @FXML
    private ImageView isl7_tower_img;

    @FXML
    private Text isl7_towersCount_text;

    @FXML
    private ImageView isl7_yellowStud_img;

    @FXML
    private Text isl7_yellowStudCount_text;

    @FXML
    private ImageView isl7_redStud_img;

    @FXML
    private Text isl7_redStudCount_text;

    @FXML
    private ImageView isl7_blueStud_img;

    @FXML
    private Text isl7_blueStudCount_text;

    @FXML
    private ImageView isl7_pinkStud_img;

    @FXML
    private Text isl7_pinkStudCount_text;

    @FXML
    private ImageView isl7_greenStud_img;

    @FXML
    private Text isl7_greenStudCount_text;

    @FXML
    private ImageView isl7_motherNature_img;

    @FXML
    private ImageView isl7_noEntryTile_img;

    @FXML
    private Text isl7_noEntryTilesCount_text;

    // endregion Island7

    // region Island8

    @FXML
    private AnchorPane isl8_pane;

    @FXML
    private ImageView isl8_img;

    @FXML
    private ImageView isl8_tower_img;

    @FXML
    private Text isl8_towersCount_text;

    @FXML
    private ImageView isl8_yellowStud_img;

    @FXML
    private Text isl8_yellowStudCount_text;

    @FXML
    private ImageView isl8_redStud_img;

    @FXML
    private Text isl8_redStudCount_text;

    @FXML
    private ImageView isl8_blueStud_img;

    @FXML
    private Text isl8_blueStudCount_text;

    @FXML
    private ImageView isl8_pinkStud_img;

    @FXML
    private Text isl8_pinkStudCount_text;

    @FXML
    private ImageView isl8_greenStud_img;

    @FXML
    private Text isl8_greenStudCount_text;

    @FXML
    private ImageView isl8_motherNature_img;

    @FXML
    private ImageView isl8_noEntryTile_img;

    @FXML
    private Text isl8_noEntryTilesCount_text;

    // endregion Island8

    // region Island9

    @FXML
    private AnchorPane isl9_pane;

    @FXML
    private ImageView isl9_img;

    @FXML
    private ImageView isl9_tower_img;

    @FXML
    private Text isl9_towersCount_text;

    @FXML
    private ImageView isl9_yellowStud_img;

    @FXML
    private Text isl9_yellowStudCount_text;

    @FXML
    private ImageView isl9_redStud_img;

    @FXML
    private Text isl9_redStudCount_text;

    @FXML
    private ImageView isl9_blueStud_img;

    @FXML
    private Text isl9_blueStudCount_text;

    @FXML
    private ImageView isl9_pinkStud_img;

    @FXML
    private Text isl9_pinkStudCount_text;

    @FXML
    private ImageView isl9_greenStud_img;

    @FXML
    private Text isl9_greenStudCount_text;

    @FXML
    private ImageView isl9_motherNature_img;

    @FXML
    private ImageView isl9_noEntryTile_img;

    @FXML
    private Text isl9_noEntryTilesCount_text;

    // endregion Island9

    // region Island10

    @FXML
    private AnchorPane isl10_pane;

    @FXML
    private ImageView isl10_img;

    @FXML
    private ImageView isl10_tower_img;

    @FXML
    private Text isl10_towersCount_text;

    @FXML
    private ImageView isl10_yellowStud_img;

    @FXML
    private Text isl10_yellowStudCount_text;

    @FXML
    private ImageView isl10_redStud_img;

    @FXML
    private Text isl10_redStudCount_text;

    @FXML
    private ImageView isl10_blueStud_img;

    @FXML
    private Text isl10_blueStudCount_text;

    @FXML
    private ImageView isl10_pinkStud_img;

    @FXML
    private Text isl10_pinkStudCount_text;

    @FXML
    private ImageView isl10_greenStud_img;

    @FXML
    private Text isl10_greenStudCount_text;

    @FXML
    private ImageView isl10_motherNature_img;

    @FXML
    private ImageView isl10_noEntryTile_img;

    @FXML
    private Text isl10_noEntryTilesCount_text;

    // endregion Island10

    // region Island11

    @FXML
    private AnchorPane isl11_pane;

    @FXML
    private ImageView isl11_img;

    @FXML
    private ImageView isl11_tower_img;

    @FXML
    private Text isl11_towersCount_text;

    @FXML
    private ImageView isl11_yellowStud_img;

    @FXML
    private Text isl11_yellowStudCount_text;

    @FXML
    private ImageView isl11_redStud_img;

    @FXML
    private Text isl11_redStudCount_text;

    @FXML
    private ImageView isl11_blueStud_img;

    @FXML
    private Text isl11_blueStudCount_text;

    @FXML
    private ImageView isl11_pinkStud_img;

    @FXML
    private Text isl11_pinkStudCount_text;

    @FXML
    private ImageView isl11_greenStud_img;

    @FXML
    private Text isl11_greenStudCount_text;

    @FXML
    private ImageView isl11_motherNature_img;

    @FXML
    private ImageView isl11_noEntryTile_img;

    @FXML
    private Text isl11_noEntryTilesCount_text;

    // endregion Island11

    // endregion Islands

    // region CloudTiles

    @FXML
    private GridPane cloudTiles_grid;

    // region CloudTile0

    @FXML
    private AnchorPane grid00_pane;

    @FXML
    private AnchorPane cloudTile0_pane;

    @FXML
    private ImageView cloud0_img;

    @FXML
    private ImageView CT0_student0_img;

    @FXML
    private ImageView CT0_student1_img;

    @FXML
    private ImageView CT0_student2_img;

    @FXML
    private ImageView CT0_student3_img;

    // endregion CloudTile0

    // region CloudTile1

    @FXML
    private AnchorPane grid01_pane;

    @FXML
    private AnchorPane cloudTile1_pane;

    @FXML
    private ImageView cloud1_img;

    @FXML
    private ImageView CT1_student0_img;

    @FXML
    private ImageView CT1_student1_img;

    @FXML
    private ImageView CT1_student2_img;

    @FXML
    private ImageView CT1_student3_img;

    // endregion CloudTile1

    // region CloudTile2

    @FXML
    private AnchorPane grid10_pane;

    @FXML
    private AnchorPane cloudTile2_pane;

    @FXML
    private ImageView cloud2_img;

    @FXML
    private ImageView CT2_student0_img;

    @FXML
    private ImageView CT2_student1_img;

    @FXML
    private ImageView CT2_student2_img;

    @FXML
    private ImageView CT2_student3_img;

    // endregion CloudTile2

    // region CloudTile3

    @FXML
    private AnchorPane grid11_pane;

    @FXML
    private AnchorPane cloudTile3_pane;

    @FXML
    private ImageView cloud3_img;

    @FXML
    private ImageView CT3_student0_img;

    @FXML
    private ImageView CT3_student1_img;

    @FXML
    private ImageView CT3_student2_img;

    @FXML
    private ImageView CT3_student3_img;

    // endregion CloudTile3

    // endregion CloudTiles

    // endregion Sky

    // region ExpertMode

    @FXML
    private AnchorPane characterCards_pane;

    // region CharacterCards

    @FXML
    private Rectangle characterBackground_game;

    // region CharacterCard0

    @FXML
    private ImageView characterCard0_img;

    @FXML
    private ImageView CC0_hasCoin_img;

    @FXML
    private Rectangle CC0_elem_background;

    @FXML
    private GridPane CC0_gridPane;

    @FXML
    private ImageView CC0_elem0_img;

    @FXML
    private ImageView CC0_elem1_img;

    @FXML
    private ImageView CC0_elem2_img;

    @FXML
    private ImageView CC0_elem3_img;

    @FXML
    private ImageView CC0_elem4_img;

    @FXML
    private ImageView CC0_elem5_img;

    // endregion CharacterCard0

    // region CharacterCard1

    @FXML
    private ImageView characterCard1_img;

    @FXML
    private ImageView CC1_hasCoin_img;

    @FXML
    private Rectangle CC1_elem_background;

    @FXML
    private GridPane CC1_gridPane;

    @FXML
    private ImageView CC1_elem0_img;

    @FXML
    private ImageView CC1_elem1_img;

    @FXML
    private ImageView CC1_elem2_img;

    @FXML
    private ImageView CC1_elem3_img;

    @FXML
    private ImageView CC1_elem4_img;

    @FXML
    private ImageView CC1_elem5_img;

    // endregion CharacterCard1

    // region CharacterCard2

    @FXML
    private ImageView characterCard2_img;

    @FXML
    private ImageView CC2_hasCoin_img;

    @FXML
    private Rectangle CC2_elem_background;

    @FXML
    private GridPane CC2_gridPane;

    @FXML
    private ImageView CC2_elem0_img;

    @FXML
    private ImageView CC2_elem1_img;

    @FXML
    private ImageView CC2_elem2_img;

    @FXML
    private ImageView CC2_elem3_img;

    @FXML
    private ImageView CC2_elem4_img;

    @FXML
    private ImageView CC2_elem5_img;

    // endregion CharacterCard3

    // endregion CharacterCards

    // region Coins

    @FXML
    private Rectangle coin_background_img;

    @FXML
    private ImageView coinCount_img;

    @FXML
    private Text coinCount_text;

    // endregion Coins

    // endregion ExpertMode

    // region AssistantCards

    @FXML
    private AnchorPane assistantCards_pane;

    @FXML
    private Rectangle assistantBackground_game;

    @FXML
    private GridPane AC_gridPane;

    @FXML
    private ImageView assistant1_game;

    @FXML
    private ImageView assistant2_game;

    @FXML
    private ImageView assistant3_game;

    @FXML
    private ImageView assistant4_game;

    @FXML
    private ImageView assistant5_game;

    @FXML
    private ImageView assistant6_game;

    @FXML
    private ImageView assistant7_game;

    @FXML
    private ImageView assistant8_game;

    @FXML
    private ImageView assistant9_game;

    @FXML
    private ImageView assistant10_game;

    // endregion AssistantCards

    // region PlayersSchoolBoardsButton

    @FXML
    private Rectangle playerButton_game;

    @FXML
    private ImageView playersIcon_game;

    // endregion PlayersSchoolBoardsButton

    // region SchoolBoard

    @FXML
    private AnchorPane schoolBoard_pane;

    @FXML
    private ImageView schoolBoard_img;

    // region Towers

    @FXML
    private AnchorPane towerArea_pane;

    @FXML
    private GridPane towers_gridPane;

    @FXML
    private ImageView tower0_img;

    @FXML
    private ImageView tower1_img;

    @FXML
    private ImageView tower2_img;

    @FXML
    private ImageView tower3_img;

    @FXML
    private ImageView tower4_img;

    @FXML
    private ImageView tower5_img;

    @FXML
    private ImageView tower6_img;

    @FXML
    private ImageView tower7_img;

    // endregion Towers

    // region DiningRoom

    @FXML
    private AnchorPane diningRoom_pane;

    // region ProfessorTable

    @FXML
    private AnchorPane professorTable_pane;

    @FXML
    private ImageView greenProfessor_img;

    @FXML
    private ImageView redProfessor_img;

    @FXML
    private ImageView yellowProfessor_img;

    @FXML
    private ImageView pinkProfessor_img;

    @FXML
    private ImageView blueProfessor_img;

    // endregion ProfessorTable

    // region GreenDiningRoom

    @FXML
    private AnchorPane greenDiningRoom_pane;

    @FXML
    private ImageView greenDR_student0_img;

    @FXML
    private ImageView greenDR_student1_img;

    @FXML
    private ImageView greenDR_student2_img;

    @FXML
    private ImageView greenDR_student3_img;

    @FXML
    private ImageView greenDR_student4_img;

    @FXML
    private ImageView greenDR_student5_img;

    @FXML
    private ImageView greenDR_student6_img;

    @FXML
    private ImageView greenDR_student7_img;

    @FXML
    private ImageView greenDR_student8_img;

    @FXML
    private ImageView greenDR_student9_img;

    // endregion GreenDiningRoom

    // region RedDiningRoom

    @FXML
    private AnchorPane redDiningRoom_pane;

    @FXML
    private ImageView redDR_student0_img;

    @FXML
    private ImageView redDR_student1_img;

    @FXML
    private ImageView redDR_student2_img;

    @FXML
    private ImageView redDR_student3_img;

    @FXML
    private ImageView redDR_student4_img;

    @FXML
    private ImageView redDR_student5_img;

    @FXML
    private ImageView redDR_student6_img;

    @FXML
    private ImageView redDR_student7_img;

    @FXML
    private ImageView redDR_student8_img;

    @FXML
    private ImageView redDR_student9_img;

    // endregion RedDiningRoom

    // region YellowDiningRoom

    @FXML
    private AnchorPane yellowDiningRoom_pane;

    @FXML
    private ImageView yellowDR_student0_img;

    @FXML
    private ImageView yellowDR_student1_img;

    @FXML
    private ImageView yellowDR_student2_img;

    @FXML
    private ImageView yellowDR_student3_img;

    @FXML
    private ImageView yellowDR_student4_img;

    @FXML
    private ImageView yellowDR_student5_img;

    @FXML
    private ImageView yellowDR_student6_img;

    @FXML
    private ImageView yellowDR_student7_img;

    @FXML
    private ImageView yellowDR_student8_img;

    @FXML
    private ImageView yellowDR_student9_img;

    // endregion RedDiningRoom

    // region PinkDiningRoom

    @FXML
    private AnchorPane pinkDiningRoom_pane;

    @FXML
    private ImageView pinkDR_student0_img;

    @FXML
    private ImageView pinkDR_student1_img;

    @FXML
    private ImageView pinkDR_student2_img;

    @FXML
    private ImageView pinkDR_student3_img;

    @FXML
    private ImageView pinkDR_student4_img;

    @FXML
    private ImageView pinkDR_student5_img;

    @FXML
    private ImageView pinkDR_student6_img;

    @FXML
    private ImageView pinkDR_student7_img;

    @FXML
    private ImageView pinkDR_student8_img;

    @FXML
    private ImageView pinkDR_student9_img;

    // endregion PinkDiningRoom

    // region BlueDiningRoom

    @FXML
    private AnchorPane blueDiningRoom_pane;

    @FXML
    private ImageView blueDR_student0_img;

    @FXML
    private ImageView blueDR_student1_img;

    @FXML
    private ImageView blueDR_student2_img;

    @FXML
    private ImageView blueDR_student3_img;

    @FXML
    private ImageView blueDR_student4_img;

    @FXML
    private ImageView blueDR_student5_img;

    @FXML
    private ImageView blueDR_student6_img;

    @FXML
    private ImageView blueDR_student7_img;

    @FXML
    private ImageView blueDR_student8_img;

    @FXML
    private ImageView blueDR_student9_img;

    // endregion BlueDiningRoom

    // endregion DiningRoom

    // region Entrance

    @FXML
    private AnchorPane entrance_pane;

    @FXML
    private ImageView E_student0_img;

    @FXML
    private ImageView E_student1_img;

    @FXML
    private ImageView E_student2_img;

    @FXML
    private ImageView E_student3_img;

    @FXML
    private ImageView E_student4_img;

    @FXML
    private ImageView E_student5_img;

    @FXML
    private ImageView E_student6_img;

    @FXML
    private ImageView E_student7_img;

    @FXML
    private ImageView E_student8_img;

    // endregion Entrance

    // endregion SchoolBoard

    // endregion FXML_Ids

    // TODO Full implementation

    /**
     * Sets the ViewGUI at which the ServerInfoHandler is related
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }

    // Example of access to the nodes using the filter function
    // pane.getchildren().stream().filter( x -> x instanceOf ...).
}
