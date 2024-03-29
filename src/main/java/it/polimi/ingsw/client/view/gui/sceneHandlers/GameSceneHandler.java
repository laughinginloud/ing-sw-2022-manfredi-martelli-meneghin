package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.*;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.*;

/**
 * Handler (or Controller) of the scene GameScene (gameScenePage.fxml)
 * The game's main scene
 *
 * @author Giovanni Manfredi and Sebastiano Meneghin
 */

// Warnings for unused fields have been disabled, since the fields that these fields are accessing
// could be useful in the future and difficult to add without opening the .fxml
// Warnings for gui field has been suppressed to allow future modification of the class
// Warnings for possible null parameters to an addImage have been suppressed because the input is constant
// Warnings for classes that are not exported has been suppressed because are intentionally not exported
@SuppressWarnings({"unused", "FieldCanBeLocal", "ConstantConditions", "ClassEscapesDefinedScope"})

public final class GameSceneHandler implements GUIHandler {

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

    /**
     * Sets the ViewGUI at which the ServerInfoHandler is related
     *
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }

    // region GSUpdateModel

    /**
     * Updates the GameScene according to the updated model
     *
     * @param model  the model to update
     * @param player the localPlayer
     */
    public void gsUpdateModel(GameModel model, Player player, Set<GameValues> updatedValues) {
        boolean containsModel = updatedValues.contains(GameValues.MODEL);

        if (containsModel || updatedValues.contains(GameValues.ISLANDARRAY) || updatedValues.contains(GameValues.MOTHERNATURE))
        {
            // Updates the islands
            gsUpdateIslands(model.getIslands(), model.getMotherNaturePosition());
        }

        if (containsModel || updatedValues.contains(GameValues.CLOUDARRAY))
        {
            // Updates the cloudTiles
            gsUpdateCloudTiles(model.getCloudTiles(), model.getPlayersCount());
        }

        if (containsModel || updatedValues.contains(GameValues.PLAYERARRAY))
        {
            // Updates the AssistantCards deck
            gsUpdateAssistantDeck(player.getAssistantDeck(), player.getPlayerWizard());
        }

        if (containsModel || updatedValues.contains(GameValues.SCHOOLBOARDARRAY) || updatedValues.contains(GameValues.ENTRANCE)
                          || updatedValues.contains(GameValues.ENTRANCEARRAY)    || updatedValues.contains(GameValues.DININGROOM)
                          || updatedValues.contains(GameValues.DININGROOMARRAY)  || updatedValues.contains(GameValues.PLAYERARRAY)
                          || updatedValues.contains(GameValues.GLOBALPROFESSORTABLE))
        {
            // Updates the SchoolBoard
            gsUpdateSchoolBoard(model, player, updatedValues);
        }

        // If the game is in expertMode
        if (model.getExpertMode() && (updatedValues.contains(GameValues.PLAYERARRAY) || updatedValues.contains(GameValues.CHARACTERCARDARRAY)))
        {
            // Updates the expertMode elements
            gsUpdateExpertModeElements(model, player, updatedValues);
        }
    }

    // region GSUpdateSky

    /**
     * Updates the Islands in the GameScene according to the array of islands given
     *
     * @param islands              the array of islands to update
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

            if (islands[i].getNoEntryTileCount() != null) {
                // Set NoEntryTile img and NoEntryTilesCount
                islandNoEntryTile = IDHelper.gsFindNoEntryTileImgOnIslandID(this, i);
                islandNoEntryTilesCountText = IDHelper.gsFindNoEntryTileCountOnIslandID(this, i);
                islandNoEntryTilesCount = islands[i].getNoEntryTileCount();
                if (islandNoEntryTilesCount > 0) {
                    islandNoEntryTile.setVisible(true);
                    islandNoEntryTilesCountText.setText(String.valueOf(islandNoEntryTilesCount));
                    islandNoEntryTilesCountText.setVisible(true);
                } else {
                    islandNoEntryTile.setVisible(false);
                    islandNoEntryTilesCountText.setVisible(false);
                }
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
     *
     * @param cloudTiles   the array of cloudTiles to update
     * @param numOfPlayers the number of players in the game
     */
    public void gsUpdateCloudTiles(CloudTile[] cloudTiles, int numOfPlayers) {
        ImageView cloudTileStudent;
        Color     cloudTileStudentColor;
        String    cloudTileStudentPath;
        int       cloudTileMaxNumOfStudents;
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
                cloudTileStudentColor = cloudTiles[i].getStudents()[j];

                // For each student present on the island
                if (cloudTileStudentColor != null) {
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

        for (int i = 0; i < numOfPlayers; i++) {
            cloudAnchorPaneID = IDHelper.gsFindCloudAnchorPaneID(this, i);
            cloudAnchorPaneID.setVisible(true);
        }

        // For each not iterated cloud hide it from the board
        for (int i = numOfPlayers; i < ViewGUI.MAX_CLOUD_TILES; i++) {
            cloudAnchorPaneID = IDHelper.gsFindCloudAnchorPaneID(this, i);
            cloudAnchorPaneID.setVisible(false);
        }
    }

    // endregion GSUpdateSky

    /**
     * Updates the assistantCard deck in the GameScene of the player by "flipping" cards if not present
     *
     * @param assistantDeck the deck of assistantCards to update
     * @param wizard        the wizard associated with the player
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
        String    assistantCardPath;
        // For each assistantCard in a fullDeck (i.e. for each card existent)
        for (AssistantCard assistantCard : fullDeck) {
            // Gets the ID of the assistantCard from the assistantCard enum
            assistantCardID         = IDHelper.gsFindAssistantCardID(this, assistantCard);

            // If the card isn't in the availableDeck -> Flip the card
            if (!availableAssistantCardsSet.contains(assistantCard)){
                // Finds the handlerPath of the corresponding image that I need to put (wizard)
                assistantCardPath = PathHelper.fromWizardEnumToHandlerPath(wizard);

                // Sets the image using the path just found
                assistantCardID.setImage(new Image(getClass().getResourceAsStream(assistantCardPath)));
            }
        }
    }

    // region GSUpdateSchoolBoard

    /**
     * Updates the SchoolBoard in the GameScene according to the updated model
     *
     * @param model  the model to update
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
     *
     * @param towerColor   the color of the towers (BLACK, GREY, WHITE)
     * @param towerCount   the number of towers present
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
            schoolBoardTowerID.setImage(new Image(getClass().getResourceAsStream(towerPath)));
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
     *
     * @param diningRoom the diningRoom to update
     * @param player     the localPlayer
     * @param gpt        the globalProfessorTable to update
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
     *
     * @param gpt    the globalProfessorTable to update
     * @param player the localPlayer
     */
    public void gsUpdateDiningRoomProfessors(GlobalProfessorTable gpt, Player player) {
        // For each color (professor) checks if the localPlayer is or not the controller
        for (Color color : Color.values())
            gpt.getProfessorLocation(color).ifPresent(p -> {
                ImageView imageView = IDHelper.gsFindProfessorOnProfTableID(this, color);
                imageView.setVisible(p.getUsername().equals(player.getUsername()));
            });
    }

    /**
     * Updates the StudentsDiningRoom in the GameScene according to the updated diningRoom
     *
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
     *
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
                entranceStudent.setImage(new Image(getClass().getResourceAsStream(entranceStudentPath)));
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
     *
     * @param model  the model to update
     * @param player the localPlayer
     */
    public void gsUpdateExpertModeElements(GameModel model, Player player, Set<GameValues> updatedValues) {
        if (updatedValues.contains(GameValues.CHARACTERCARDARRAY)) {
            // Shows the ExpertMode pane
            characterCards_pane.setVisible(true);
            // Updates the CharacterCards
            gsUpdateCharacterCards(model.getCharacterCards());
        }

        if (updatedValues.contains(GameValues.PLAYERARRAY)) {
            // Updates the player's coinsCount
            gsUpdatePlayerCoins(player);
        }
    }

    // region GSUpdateCharacterCards

    /**
     * Updates the CharacterCards in the GameScene according to the updated array
     *
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
     *
     * @param characterCardNoEntry the updated card
     * @param index                the index of the card in the GameScene
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
     *
     * @param characterCardStudent the updated card
     * @param index                the index of the card in the GameScene
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
        for (int i = 0; i < ViewGUI.MAX_CC_ELEMENTS; i++) {
            // If the student is present on the CharacterCard's Students array
            if (i < characterCardStudent.getStudents().length && characterCardStudent.getStudents()[i] != null) {
                CC_student = IDHelper.gsFindCharacterCardElementID(this, index, i);
                CC_student_Color = characterCardStudent.getStudents()[i];
                CC_student_ImgPath = PathHelper.fromStudentColorToHandlerPath(CC_student_Color);
                CC_student.setImage(new Image(getClass().getResourceAsStream(CC_student_ImgPath)));
            }
            else {
                CC_student = IDHelper.gsFindCharacterCardElementID(this, index, i);
                CC_student.setImage(null);
            }
        }
    }

    /**
     * Updates the CharacterCard in the GameScene according to the updated card
     *
     * @param characterCard the updated card
     * @param index         the index of the card in the GameScene
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
     *
     * @param characterCard the updated card
     * @param index         the index of the card in the GameScene
     */
    public void gsUpdateCharacterCardImage(CharacterCard characterCard, int index) {
        ImageView CC_ImgView;
        String    CC_ImgPath;

        // Gets the character corresponding to the card
        Character character = characterCard.getCharacter();

        // Gets the ID of the card
        CC_ImgView = IDHelper.gsFindCharacterCardImageID(this, index);

        // Gets the path of the image
        CC_ImgPath = PathHelper.fromCharacterEnumToHandlerPath(character);

        // Sets the image
        CC_ImgView.setImage(new Image(getClass().getResourceAsStream(CC_ImgPath)));
    }

    // endregion GSUpdateCharacterCards

    /**
     * Updates the CoinCount of the localPlayer in the GameScene according to the updated localPlayer
     *
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

    // region ActivateClicks

    /**
     * Associates clickHandler to each available assistantCard - activating the clicks on the AssistantCards
     *
     * @param playableAssistantCards the available assistantCards that have to be set clickable
     */
    public void activateClicksAssistantCards(AssistantCard[] playableAssistantCards) {
        int localPlayerIndex = gui.getLocalPlayerIndex();

        Set<AssistantCard> playableAssistantCardSet = new HashSet<>();
        Collections.addAll(playableAssistantCardSet, playableAssistantCards);

        AssistantCard[] assistantCardOnModel = gui.getModel().getPlayers()[localPlayerIndex].getAssistantDeck();
        ImageView       assistantCardImageView;

        for (AssistantCard assistantCard : assistantCardOnModel) {
            if (playableAssistantCardSet.contains(assistantCard)) {
                assistantCardImageView = IDHelper.gsFindAssistantCardID(this, assistantCard.cardValue());

                EventHandler<MouseEvent> clickOnAssistantCardHandler = mouseEvent -> {
                    // Associates to the click of the mouse the function clickOnAssistantCard
                    // as response to the user's action
                    clickOnAssistantCard(mouseEvent);

                    mouseEvent.consume();
                };

                // Then links the created mouseEventHandler to the click of the ACImage
                assistantCardImageView.setOnMouseClicked(clickOnAssistantCardHandler);
            }
        }
    }

    // region ActivateClicksSky

    /**
     * Associates clickHandler to each available islands - activating the clicks on the Islands
     *
     * @param availableIslands       the available islands that have to be set clickable
     * @param otherElementsClickable the otherElements that have to be set clickable
     */
    public void activateClicksIslands(Island[] availableIslands, boolean otherElementsClickable) {
        Island[] islandFromModel       = gui.getModel().getIslands();
        int      islandCount           = islandFromModel.length;
        int      availableIslandsCount = availableIslands.length;
        int      motherNaturePosition  = gui.getModel().getMotherNaturePosition();

        AnchorPane islandAnchorPane;

        int islandToSelectIndex = (motherNaturePosition + 1) % islandCount;

        for (int i = 0; i < availableIslandsCount; i++) {
            islandAnchorPane = IDHelper.gsFindIslandAnchorPaneID(this, islandToSelectIndex);

            // Creates a function that will handle the islandClick
            EventHandler<MouseEvent> clickOnIslandHandler = mouseEvent -> {
                // Invokes the function clickOnIsland
                clickOnIsland(mouseEvent, otherElementsClickable);

                mouseEvent.consume();
            };

            islandAnchorPane.setOnMouseClicked(clickOnIslandHandler);

            islandToSelectIndex = (islandToSelectIndex + 1) % islandCount;
        }
    }

    /**
     * Associates clickHandler to each available cloudTiles - activating the clicks on the CloudTiles
     *
     * @param availableClouds        the available cloudTiles that have to be set clickable
     * @param otherElementsClickable the otherElements that have to be set clickable
     */
    public void activateClicksCloudTiles(CloudTile[] availableClouds, boolean otherElementsClickable) {
        int             numOfAvailableClouds           = availableClouds.length;
        CloudTile[]     cloudTilesModel                = gui.getModel().getCloudTiles();
        int[]           availableCloudsPositionOnModel = new int[numOfAvailableClouds];

        // For all the cloudTiles that has been sent by the VirtualController
        for (int i = 0; i < numOfAvailableClouds; i++) {
            boolean cloudMatch = false;
            Color[] colorOnTile = availableClouds[i].getStudents();

            // For all the cloudTiles that are currently present in the Model
            for (int k = 0; k < cloudTilesModel.length; k++) {
                // If a comparison with this Model's cloudTime hasn't been found yet
                if (cloudTilesModel[k] != null) {
                    // For all the students on the cloudTiles check which is the Model's cloudTile that
                    // is equal to the sent one - that has the same students on it
                    for (int j = 0; j < colorOnTile.length; j++) {
                        cloudMatch = false;

                        // If there's at least one student who doesn't match with the specific model's cloudTile
                        if (availableClouds[i].getStudents()[j] != cloudTilesModel[k].getStudents()[j])
                            break;

                        // If all the students match between the model cloudTile and the provided cloudTile, set cloudMatch to true
                        if (j == colorOnTile.length - 1)
                            cloudMatch = true;
                    }

                    if (cloudMatch) {
                        // Saves the position of the availableCloud on the Model, then set to null this cell of the array!
                        availableCloudsPositionOnModel[i] = k;
                        cloudTilesModel[k] = null;
                        break;
                    }

                    // If at the end of the loop there are not matches with a provided error there must be an error with the network
                    // or the GameStateController
                    else if (k == cloudTilesModel.length - 1)
                        throw new AssertionError("\nThere are no matches with that cloud that need to be set clickable!\n");
                }
            }
        }

        AnchorPane cloudTilePane;
        for (int i = 0; i < numOfAvailableClouds; i++) {
            int currentCloud = availableCloudsPositionOnModel[i];
            cloudTilePane = IDHelper.gsFindCloudAnchorPaneID(this, currentCloud);

            EventHandler<MouseEvent> clickOnCloudTileHandler = mouseEvent -> {
                // Associates to the click of the mouse the function clickOnCloudTile
                // as response to the user's action
                clickOnCloudTile(mouseEvent, otherElementsClickable);

                mouseEvent.consume();
            };

            cloudTilePane.setOnMouseClicked(clickOnCloudTileHandler);
        }
    }

    // endregion ActivateClicksSky

    // region ActivateClicksSchoolBoard

    /**
     * Associates clickHandler to each compatible diningRoomTable - activating the clicks on the DiningRoomTables
     *
     * @param compatibleDiningRoomTable the available diningRoomTables that have to be set clickable
     */
    public void activateClicksDiningRoomTables(Color[] compatibleDiningRoomTable) {

        AnchorPane diningRoomTablePane;

        for (Color color : compatibleDiningRoomTable) {
            diningRoomTablePane = IDHelper.gsFindDiningRoomTablePaneID(this, color);

            EventHandler<MouseEvent> clickOnDiningRoomTableHandler = mouseEvent -> {
                // Associates to the click of the mouse the function clickOnDiningRoomTable
                // as response to the user's action
                clickOnDiningRoomTable(mouseEvent);

                mouseEvent.consume();
            };

            diningRoomTablePane.setOnMouseClicked(clickOnDiningRoomTableHandler);
        }
    }

    /**
     * Associates clickHandler to each compatible student in the entrance - activating the clicks on the EntranceStudents
     *
     * @param availableColors        the available entranceStudents that have to be set clickable
     * @param otherElementsClickable the otherElements that have to be set clickable
     */
    public void activateClicksEntranceStudents(Color[] availableColors, boolean otherElementsClickable) {
        Color[] entranceStudents = gui.getLocalPlayer().getSchoolBoard().getEntrance().getStudents();

        ImageView entranceStudentImageView;

        for (Color color : availableColors) {
            if (color == null)
                continue;

            for (int studentPos = 0; studentPos < entranceStudents.length; studentPos++) {
                if (color.equals(entranceStudents[studentPos])) {
                    entranceStudentImageView = IDHelper.gsFindStudentEntranceID(this, studentPos);

                    EventHandler<MouseEvent> clickOnEntranceStudentHandler = mouseEvent -> {
                        // Associates to the click of the mouse the function clickOnEntranceStudent
                        // as response to the user's action
                        clickOnEntranceStudent(mouseEvent, otherElementsClickable);

                        mouseEvent.consume();
                    };

                    entranceStudentImageView.setOnMouseClicked(clickOnEntranceStudentHandler);
                }
            }
        }
    }

    // endregion ActivateClicksSchoolBoard

    // region ActivateClicksExpertMode

    /**
     * Associates clickHandler to each playable characterCards - activating the clicks on the CharacterCards
     *
     * @param playableCharacterCards the playable characterCards that have to be set clickable
     * @param otherElementsClickable the otherElements that have to be set clickable
     */
    public void activateClicksCharacterCards(CharacterCard[] playableCharacterCards, boolean otherElementsClickable) {
        CharacterCard[] characterCardsModel = gui.getModel().getCharacterCards();
        ImageView       characterCardImgView;

        for (CharacterCard playableCharacterCard : playableCharacterCards) {
            for (int j = 0; j < characterCardsModel.length; j++) {
                if (playableCharacterCard.getCharacter().ordinal() == characterCardsModel[j].getCharacter().ordinal()) {
                    characterCardImgView = IDHelper.gsFindCharacterCardImageID(this, j);

                    // Creates a function that will handle the characterCardClick
                    EventHandler<MouseEvent> clickOnCharacterCardHandler = mouseEvent -> {
                        // Invokes the function clickOnCharacterCard
                        clickOnCharacterCard(mouseEvent, otherElementsClickable);

                        mouseEvent.consume();
                    };

                    // Then links the created handler to the click of the CCImage
                    characterCardImgView.setOnMouseClicked(clickOnCharacterCardHandler);
                }
            }
        }
    }

    /**
     * Associates clickHandler to each available characterCardElement - activating the clicks on the CharacterCardElement
     *
     * @param characterCardPosition  the index of the CharacterCard in the GameModel (which contains a CC array)
     * @param availableColors        the available CC Students on the CC (for CharacterCardStudent)
     * @param numOfAvailableElements the number of AvailableElements on the CC (for CharacterCardStudent and Elements)
     */
    public void activateClicksCharacterCardElements(int characterCardPosition, Color[] availableColors, int numOfAvailableElements) {
        CharacterCardStudent playedCharacterCard    = (CharacterCardStudent) gui.getModel().getCharacterCard(characterCardPosition);
        Color[]              studentOnCharacterCard = playedCharacterCard.getStudents();
        int                  numOfCCElements        = studentOnCharacterCard.length;

        ImageView ccStudentImageView;

        for (Color color : availableColors) {
            for (int i = 0; i < numOfCCElements; i++) {
                if (studentOnCharacterCard[i] != null) {
                    if (color.equals(studentOnCharacterCard[i])) {
                        ccStudentImageView = IDHelper.gsFindCharacterCardElementID(this, characterCardPosition, i);

                        EventHandler<MouseEvent> clickOnCharacterCardStudentHandler = mouseEvent -> {
                            // Associates to the click of the mouse the function clickOnCharacterCardElements
                            // as response to the user's action
                            clickOnCharacterCardStudent(mouseEvent);

                            mouseEvent.consume();
                        };

                        ccStudentImageView.setOnMouseClicked(clickOnCharacterCardStudentHandler);
                    }
                }
            }
        }
    }

    /**
     * Associates clickHandler to the inputButton - activating the click on the inputButton
     */
    public void activateClicksInputButton() {
        // Creates a function that will handle the characterCardClick
        EventHandler<MouseEvent> clickOnInputButtonHandler = mouseEvent -> {
            // Invokes the function clickOnCharacterCard
            clickOnInputButton(mouseEvent);

            mouseEvent.consume();
        };

        // Then links the created handler to the click of the CCImage
        input_button.setOnMouseClicked(clickOnInputButtonHandler);
    }

    // endregion ActivateClicksExpertMode

    // region ActivateMultipleClicks

    /**
     * Associates clickHandler to the diningRoomTables and islands - activating the click on the diningRoomTables and islands
     *
     * @param selectedStudentIndex the index of the selected student in the entrance
     * @param diningRoomFreeTables an array of boolean which for each color has:
     *                             'true' if the table is free and 'false' if the table is full
     */
    public void activateClicksDiningRoomTablesAndIsland(int selectedStudentIndex, Boolean[] diningRoomFreeTables) {
        // Gets the IslandsArray from the model
        Island[] availableIslands = gui.getModel().getIslands();

        // Retrieve which are Colors of the DiningRoomTable that have to be set "clickable"
        List<Color> compatibleTablesList = new ArrayList<>();
        for (Color color : Color.values())
            if (diningRoomFreeTables[color.ordinal()])
                compatibleTablesList.add(color);

        int     compatibleTablesLength = compatibleTablesList.size();
        Color[] compatibleTables       = new Color[compatibleTablesLength];
        for (int i = 0; i < compatibleTablesLength; i++)
            compatibleTables[i] = compatibleTablesList.get(i);

        // Calls the activateClicks method, inserting also the selectedStudentIndex as a parameter
        // to require the VirtualController method's specification and respond back with the
        // requested values (the record MoveStudentInfo)
        activateClicksIslandsDuringEntranceMovement(availableIslands, selectedStudentIndex);
        activateClicksDiningRoomTablesDuringEntranceMovement(compatibleTables, selectedStudentIndex);
    }

    /**
     * Associates clickHandler to the islands during the MoveStudentsState - activating the clicks on the islands
     *
     * @param availableIslands     the available islands to be set clickable
     * @param selectedStudentIndex the index of the selected student in the Entrance
     */
    public void activateClicksIslandsDuringEntranceMovement(Island[] availableIslands, int selectedStudentIndex) {
        Island[] islandFromModel = gui.getModel().getIslands();
        int      islandCount     = islandFromModel.length;

        Set<Island> availableIslandSet = new HashSet<>();
        Collections.addAll(availableIslandSet, availableIslands);

        AnchorPane islandAnchorPane;

        for (int i = 0; i < islandCount; i++) {
            if (availableIslandSet.contains(islandFromModel[i])) {
                islandAnchorPane = IDHelper.gsFindIslandAnchorPaneID(this, i);

                // Creates a function that will handle the islandClick
                EventHandler<MouseEvent> clickOnIslandHandler = mouseEvent -> {
                    // Invokes the function clickOnIsland
                    clickOnIslandDuringEntranceMovement(mouseEvent, selectedStudentIndex);

                    mouseEvent.consume();
                };

                islandAnchorPane.setOnMouseClicked(clickOnIslandHandler);
            }
        }
    }

    /**
     * Associates clickHandler to the diningRoomTables during the MoveStudentsState - activating the clicks on the diningRoomTables
     *
     * @param compatibleTables     the tables compatible to be set clickable (not full tables)
     * @param selectedStudentIndex the index of the selected student in the Entrance
     */
    public void activateClicksDiningRoomTablesDuringEntranceMovement(Color[] compatibleTables, int selectedStudentIndex) {
        AnchorPane diningRoomTablePane;

        for (Color color : compatibleTables) {
            diningRoomTablePane = IDHelper.gsFindDiningRoomTablePaneID(this, color);

            EventHandler<MouseEvent> clickOnDiningRoomTableHandler = mouseEvent -> {
                // Associates to the click of the mouse the function clickOnDiningRoomTable
                // as response to the user's action
                clickOnDiningRoomTablesDuringEntranceMovement(mouseEvent, selectedStudentIndex);

                mouseEvent.consume();
            };

            diningRoomTablePane.setOnMouseClicked(clickOnDiningRoomTableHandler);
        }
    }

    // endregion ActivateMultipleClicks

    // region ActivateMultipleClicksExpertMode

    /**
     * Associates clickHandler to the CloudTiles and CC - activating the clicks on the CloudTiles and CC
     *
     * @param availableClouds        the cloudTiles available to be set clickable
     * @param playableCharacterCards the playable CC to be set clickable
     */
    public void activateClicksCloudTilesAndCCs(CloudTile[] availableClouds, CharacterCard[] playableCharacterCards) {
        activateClicksCloudTiles(availableClouds, true);
        activateClicksCharacterCards(playableCharacterCards, true);
    }

    /**
     * Associates clickHandler to the EntranceStudents and CC - activating the clicks on the CloudTiles and CC
     *
     * @param entranceStudents       the students in the entrance to be set clickable
     * @param playableCharacterCards the playable CC to be set clickable
     */
    public void activateClicksEntranceStudentsAndCC (Color[] entranceStudents, CharacterCard[] playableCharacterCards) {
        activateClicksEntranceStudents(entranceStudents, true);
        activateClicksCharacterCards(playableCharacterCards, true);
    }

    /**
     * Associates clickHandler to the Islands and CC - activating the clicks on the Islands and CC
     *
     * @param possibleMovement       the islands on which the movement is possible, to be set clickable
     * @param playableCharacterCards the playable CC to be set clickable
     */
    public void activateClicksIslandsAndCCs(Island[] possibleMovement, CharacterCard[] playableCharacterCards) {
        activateClicksIslands(possibleMovement, true);
        activateClicksCharacterCards(playableCharacterCards, true);
    }

    // endregion ActivateMultipleClicksExpertMode

    // endregion ActivateClicks

    // region DeactivateClicks

    /**
     * Deactivates all clicks on the interface
     */
    public void deactivateClicks() {
        deactivateClicksAssistantCards();

        // Deactivates Sky
        deactivateClicksIslands();
        deactivateClicksCloudTiles();

        // Deactivates SchoolBoard
        deactivateClicksDiningRoomTables();
        deactivateClicksEntranceStudents();

        // Deactivates ExpertModeElements
        deactivateClicksCharacterCards();
        deactivateClicksCharacterCardsElements();

        deactivateClicksInputButton();
    }

    /**
     * Deactivates the clicks on the AssistantCards
     */
    public void deactivateClicksAssistantCards() {
        ImageView assistantCardImgView;
        for (int i = 0; i < ViewGUI.MAX_ASSISTANT_CARDS; i++) {
            assistantCardImgView = IDHelper.gsFindAssistantCardID(this, i + 1);
            assistantCardImgView.setOnMouseClicked(null);
        }
    }

    // region DeactivateClicksSky

    /**
     * Deactivates the clicks on the Islands
     */
    public void deactivateClicksIslands() {
        AnchorPane islandPane;
        for (int i = 0; i < ViewGUI.MAX_ISLANDS; i++) {
            islandPane = IDHelper.gsFindIslandAnchorPaneID(this, i);
            islandPane.setOnMouseClicked(null);
        }
    }

    /**
     * Deactivates the clicks on the CloudTiles
     */
    public void deactivateClicksCloudTiles() {
        AnchorPane cloudTilePane;
        for (int i = 0; i < ViewGUI.MAX_CLOUD_TILES; i++) {
            cloudTilePane = IDHelper.gsFindCloudAnchorPaneID(this, i);
            cloudTilePane.setOnMouseClicked(null);
        }
    }

    // endregion DeactivateClicksSky

    // region DeactivateClicksSchoolBoard

    /**
     * Deactivates the clicks on the DiningRoomTables
     */
    public void deactivateClicksDiningRoomTables() {
        AnchorPane diningRoomPane;
        for (Color color : Color.values()) {
            diningRoomPane = IDHelper.gsFindDiningRoomTablePaneID(this, color);
            diningRoomPane.setOnMouseClicked(null);
        }
    }

    /**
     * Deactivates the clicks on the EntranceStudents
     */
    public void deactivateClicksEntranceStudents() {
        ImageView entranceStudentImgView;
        for (int i = 0; i < ViewGUI.MAX_ENTRANCE_STUDENTS; i++) {
            entranceStudentImgView = IDHelper.gsFindStudentEntranceID(this, i);
            entranceStudentImgView.setOnMouseClicked(null);
        }
    }

    // endregion DeactivateClicksSchoolBoard

    // region DeactivateClicksExpertMode

    /**
     * Deactivates the clicks on the CC
     */
    public void deactivateClicksCharacterCards() {
        ImageView characterCardsImgView;
        for (int i = 0; i < ViewGUI.MAX_CC; i++) {
            characterCardsImgView = IDHelper.gsFindCharacterCardImageID(this, i);
            characterCardsImgView.setOnMouseClicked(null);
        }
    }

    /**
     * Deactivates the clicks on the CC elements of all the CharacterCards
     */
    public void deactivateClicksCharacterCardsElements() {
        int numOfCharacterCard = gui.getModel().getCharacterCards().length;

        for (int i = 0; i < numOfCharacterCard; i++) {
            ImageView correspondentCCID      = IDHelper.gsFindCharacterCardImageID(this, i);
            deactivateClicksCharacterCardsElements(correspondentCCID);
        }
    }

    /**
     * Deactivates the clicks on the CC elements of  a specific CharacterCard
     * @param characterCardID the ImageView of the specific CharacterCard
     */
    public void deactivateClicksCharacterCardsElements(ImageView characterCardID) {
        ImageView characterCardsElementImgView;
        for (int i = 0; i < ViewGUI.MAX_CC_ELEMENTS; i++) {
            characterCardsElementImgView = IDHelper.gsFindCharacterCardElementID(this, characterCardID, i);
            characterCardsElementImgView.setOnMouseClicked(null);
        }
    }

    /**
     * Deactivates the click on the input button
     */
    public void deactivateClicksInputButton() {
        input_button.setOnMouseClicked(null);
    }

    // endregion DeactivateClicksExpertMode

    // endregion DeactivateClicks

    // region ClickOnEvents

    /**
     * Method called on the click of a "assistantCard"
     * Deactivates the clicks of all AssistantCards, then forwards
     * the selected AssistantCard to the ViewGUI
     *
     * @param mouseEvent The event that happened on the GameScenePane
     */
    public void clickOnAssistantCard (MouseEvent mouseEvent) {
        int localPlayerIndex = gui.getLocalPlayerIndex();

        // Deactivates the click on each AssistantCard, erasing the function associated to their message
        deactivateClicksAssistantCards();

        // Gets the ImageView of the selected assistantCard, then retrieve is position on the
        // assistantCardDeck and gets its instance from the model
        ImageView     selectedAssistantCardID    = (ImageView) mouseEvent.getSource();

        // Finds the cardValue of the selected AssistantCard from the selectedAssistantCardID and
        // gets all the currently present Model's AssistantCards
        int             selectedAssistantCardValue = InfoHelper.gsFindAssistantCardIndex(selectedAssistantCardID);
        AssistantCard[] modelAssistantDeck         = gui.getModel().getPlayer(localPlayerIndex).getAssistantDeck();

        // Retrieve the real position in the AssistantDeck of the selected AssistantCard
        int positionOnModelAssCardArray = 0;
        for (int i = 0; i < modelAssistantDeck.length; i++) {
            if (modelAssistantDeck[i].cardValue() == selectedAssistantCardValue) {
                positionOnModelAssCardArray = i;
                break;
            }
        }

        AssistantCard selectedAssistantCard = gui.getModel().getPlayer(localPlayerIndex).getAssistantCard(positionOnModelAssCardArray);

        // Notifies the ViewGUI (and the VirtualController) about the user choice
        gui.forwardViewToVirtualController(selectedAssistantCard);
    }

    // region ClickOnSkyEvents

    /**
     * Method called on the click of an "island"
     * Deactivates the clicks of all Islands, then forwards
     * the selected island to the ViewGUI
     *
     * @param mouseEvent             The event that happened on the GameScenePane
     * @param otherElementsClickable If the CC were clickable (true) they need to be deactivated
     */
    public void clickOnIsland(MouseEvent mouseEvent, boolean otherElementsClickable) {
        // If the player chose the Island when also CharacterCards were clickable, deactivates all of them
        if (otherElementsClickable) { deactivateClicksCharacterCards(); }

        deactivateClicksIslands();

        AnchorPane selectedIslandPaneID = (AnchorPane) mouseEvent.getSource();
        int        selectedIslandIndex  = InfoHelper.gsFindIslandIndex(selectedIslandPaneID);

        gui.forwardViewToVirtualController(selectedIslandIndex);
    }

    /**
     * Method called on the click of a "cloudTile"
     * Deactivates the clicks of all CloudTiles, then forwards
     * the selected cloudTile to the ViewGUI
     *
     * @param mouseEvent             The event that happened on the GameScenePane
     * @param otherElementsClickable If the CC were clickable (true) they need to be deactivated
     */
    public void clickOnCloudTile(MouseEvent mouseEvent, boolean otherElementsClickable) {
        // If the player chose the CloudTile when also CharacterCards were clickable, deactivates all of them
        if (otherElementsClickable) { deactivateClicksCharacterCards(); }

        deactivateClicksCloudTiles();

        AnchorPane selectedCloudTilePaneID = (AnchorPane) mouseEvent.getSource();
        int        selectedCloudTileIndex  = InfoHelper.gsFindCloudTileIndex(selectedCloudTilePaneID);

        gui.forwardViewToVirtualController(selectedCloudTileIndex);
    }

    /**
     * Method called on the click of an "island" during the MoveStudentsState
     * Deactivates the clicks of all Islands, then forwards
     * the selected island to the ViewGUI
     *
     * @param mouseEvent           The event that happened on the GameScenePane
     * @param selectedStudentIndex The index of the selected student in the Entrance
     */
    public void clickOnIslandDuringEntranceMovement(MouseEvent mouseEvent, int selectedStudentIndex) {
        // Deactivates the click-linked function of both Islands and DiningRoomTables
        deactivateClicksIslands();
        deactivateClicksDiningRoomTables();

        AnchorPane selectedIslandPaneID = (AnchorPane) mouseEvent.getSource();
        int        selectedIslandIndex  = InfoHelper.gsFindIslandIndex(selectedIslandPaneID);

        // Creates a new record "MoveStudentInfo", where to save the number of the island and set
        // to false the boolean "toDiningRoom" related to the student's movement
        MoveStudentInfo infoFromIslandClick = new MoveStudentInfo(false, selectedIslandIndex, selectedStudentIndex);
        gui.forwardViewToVirtualController(infoFromIslandClick);
    }

    // endregion ClickOnSkyEvents

    // region ClicksOnSchoolBoardEvents

    /**
     * Method called on the click of a "diningRoomTable"
     * Deactivates the clicks of all diningRoomTables, then forwards
     * the selected diningRoomTable to the ViewGUI
     *
     * @param mouseEvent The event that happened on the GameScenePane
     */
    public void clickOnDiningRoomTable(MouseEvent mouseEvent) {
        deactivateClicksDiningRoomTables();

        AnchorPane selectedTablePaneID    = (AnchorPane) mouseEvent.getSource();
        Color      selectedTablePaneColor = InfoHelper.gsFindDiningRoomTableColor(selectedTablePaneID);

        gui.forwardViewToVirtualController(selectedTablePaneColor);
    }

    /**
     * Method called on the click of an "entranceStudent"
     * Deactivates the clicks of all entranceStudents, then forwards
     * the selected entranceStudent to the ViewGUI
     *
     * @param mouseEvent             The event that happened on the GameScenePane
     * @param otherElementsClickable If the CC were clickable (true) they need to be deactivated
     */
    public void clickOnEntranceStudent(MouseEvent mouseEvent, boolean otherElementsClickable) {
        // If the player chose the CloudTile when also CharacterCards were clickable, deactivates all of them
        if (otherElementsClickable) { deactivateClicksCharacterCards(); }

        deactivateClicksEntranceStudents();

        ImageView selectedEntranceStudentID    = (ImageView) mouseEvent.getSource();
        int       selectedEntranceStudentIndex = InfoHelper.gsFindEntranceStudentIndex(selectedEntranceStudentID);

        gui.forwardViewToVirtualController(selectedEntranceStudentIndex);
    }

    /**
     * Method called on the click of a "diningRoomTable" during the MoveStudentsState
     * Deactivates the clicks of all DiningRoomTables, then forwards
     * the selected diningRoomTable to the ViewGUI
     *
     * @param mouseEvent           The event that happened on the GameScenePane
     * @param selectedStudentIndex The index of the selected student in the Entrance
     */
    public void clickOnDiningRoomTablesDuringEntranceMovement(MouseEvent mouseEvent, int selectedStudentIndex) {
        // Deactivates the click-linked function of both Islands and DiningRoomTables
        deactivateClicksIslands();
        deactivateClicksDiningRoomTables();

        // Creates a new record "MoveStudentInfo", where to save the index of the selected
        // students and to set "true" the boolean "toDiningRoom"
        MoveStudentInfo infoFromIslandClick = new MoveStudentInfo(true, null, selectedStudentIndex);
        gui.forwardViewToVirtualController(infoFromIslandClick);
    }

    // endregion ClicksOnSchoolBoardEvents

    // region ClicksOnExpertModeEvents

    /**
     * Method called on the click of a "characterCard"
     * Deactivates the clicks of all CharacterCards, then forwards
     * the selected characterCards to the ViewGUI
     *
     * @param mouseEvent             The event that happened on the GameScenePane
     * @param otherElementsClickable If the CC were clickable (true) they need to be deactivated
     */
    public void clickOnCharacterCard(MouseEvent mouseEvent, boolean otherElementsClickable) {
        // If the player chose the characterCard when other elements were clickable, deactivates all of them
        if (otherElementsClickable) {
            deactivateClicksEntranceStudents();
            deactivateClicksIslands();
            deactivateClicksCloudTiles();
        }

        deactivateClicksCharacterCards();

        // Gets the characterCard from then mouseEvent
        ImageView selectedCharacterCardID = (ImageView) mouseEvent.getSource();

        // Gets the index from the selected ImgView, then gets the corresponding CharacterCard
        int           characterCardIndex = InfoHelper.gsFindCharacterCardIndex(selectedCharacterCardID);
        CharacterCard characterCard      = gui.getModel().getCharacterCard(characterCardIndex);

        gui.forwardViewToVirtualController(characterCard);
    }

    /**
     * Method called on the click of a "characterCardElement"
     * Deactivates the clicks of all CharacterCardElements, then forwards
     * the selected characterCardElements to the ViewGUI
     *
     * @param mouseEvent The event that happened on the GameScenePane
     */
    public void clickOnCharacterCardStudent(MouseEvent mouseEvent) {
        ImageView selectedCCStudentID    = (ImageView) mouseEvent.getSource();

        int       correspondentCCPos     = InfoHelper.gsFindCharacterCardIndexFromSelectedElement(selectedCCStudentID);
        ImageView correspondentCCID      = IDHelper.gsFindCharacterCardImageID(this, correspondentCCPos);
        deactivateClicksCharacterCardsElements(correspondentCCID);

        int       selectedCCStudentIndex = InfoHelper.gsFindCharacterCardElementIndex(selectedCCStudentID);
        gui.forwardViewToVirtualController(selectedCCStudentIndex);
    }

    /**
     * Method called on the click of the Spinner's Button
     * Deactivates the clicks of the inputButton, then forwards
     * the selected numberOfStudents to move to the ViewGUI and hides the input pane
     *
     * @param mouseEvent The event that happened on the GameScenePane
     */
    public void clickOnInputButton(MouseEvent mouseEvent) {
        deactivateClicksInputButton();

        int studentsToMove = input_spinner.getValue();

        gui.forwardViewToVirtualController(studentsToMove);

        input_pane.setVisible(false);
    }

    // endregion ClicksOnExpertModeEvents

    // endregion ClickOnEvents

    // region OtherEvents

    /**
     * Shows the inputPane and setUps the spinner
     *
     * @param maxNum max number of selectable students
     */
    public void showInputPane(int maxNum) {
        // Sets a factory integers between 2 and 4
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxNum);
        // Sets default value
        valueFactory.setValue(1);

        // Assigns the spinnerFactory to the spinner
        input_spinner.setValueFactory(valueFactory);

        // Shows the inputPane
        input_pane.setVisible(true);
    }

    // endregion OtherEvents

    // region GSRequests

    /**
     * Requests the player which assistantCard he wants to play between the provided assistantCards
     *
     * @param assistantCards An array of AssistantCards that are currently playable
     */
    public void gsRequestPlayAssistantCard(AssistantCard[] assistantCards) {
        activateClicksAssistantCards(assistantCards);

        Alert playAssistantCard = GUIAlert.getAlert(GUIAlert.CHOOSE_ASSISTANT, "");
        playAssistantCard.showAndWait();
    }

    // region GSSkyRequests

    /**
     * Requests the player to choose an Island. It sets to "clickable"
     * only the island present in the "availableIslands" array
     *
     * @param availableIslands An array of Island representing the Island that can be chosen by the player
     */
    public void gsRequestChooseIsland(Island[] availableIslands) {
        activateClicksIslands(availableIslands, false);

        Alert selectIsland = GUIAlert.getAlert(GUIAlert.SELECT_ISLAND, "");
        selectIsland.showAndWait();
    }

    /**
     * Requests the player how far he wants to move MotherNature
     * It sets to clickable only the Islands that can be selected by the player, according to the
     * provided Islands' array
     *
     * @param possibleMovement An array containing the Islands that can be moved by the player
     */
    public void gsRequestMotherNatureMovement(Island[] possibleMovement) {
        activateClicksIslands(possibleMovement, false);

        Alert moveMotherNature = GUIAlert.getAlert(GUIAlert.MOVE_MOTHER_NATURE, "");
        moveMotherNature.showAndWait();
    }

    /**
     * Requests the player to choose a CloudTile from the availableClouds
     *
     * @param availableClouds An array of CloudTile representing the available CloudTiles
     */
    public void gsRequestCloudTileSelection(CloudTile[] availableClouds) {
        activateClicksCloudTiles(availableClouds, false);

        Alert selectCloudTile = GUIAlert.getAlert(GUIAlert.SELECT_CLOUD, "");
        selectCloudTile.showAndWait();
    }

    // endregion GSSkyRequests

    // region GSSchoolBoardRequests

    /**
     * Requests the player to choose a diningRoomTable from the provided ones.
     * It links the diningRoomTable with theirs color, then make "clickable" only the diningRoomTable
     * that have the same color of the provided "compatibleDiningRoomTable" colors' array
     *
     * @param compatibleDiningRoomTable The color of the diningRoomTables that can be chosen by the player
     */
    public void gsRequestChooseDiningRoom(Color[] compatibleDiningRoomTable) {
        activateClicksDiningRoomTables(compatibleDiningRoomTable);

        Alert selectDiningRoomTable = GUIAlert.getAlert(GUIAlert.SELECT_DINING_ROOM_TABLE, "");
        selectDiningRoomTable.showAndWait();
    }

    /**
     * Requests the player to choose a student from his Entrance.
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param availableColors The colors correspondent to the students that can be
     *                        moved/picked from the Entrance
     */
    public void gsChooseStudentFromEntrance(Color[] availableColors) {
        activateClicksEntranceStudents(availableColors, false);

        Alert selectEntranceStudent = GUIAlert.getAlert(GUIAlert.SELECT_ENTRANCE_STUDENT, "");
        selectEntranceStudent.showAndWait();
    }

    /**
     * Requests the player to move the selected student from his entrance to an Island or to a table
     * of his diningRoom
     *
     * @param selectedStudentIndex The index of the entrance's player selected by the player
     * @param diningRoomFreeTables An array of boolean indicating which DiningRoomTables still
     *                             have free seats (where the player can move the student)
     */
    public void gsRequestStudentEntranceMovement(int selectedStudentIndex, Boolean[] diningRoomFreeTables) {
        activateClicksDiningRoomTablesAndIsland(selectedStudentIndex, diningRoomFreeTables);

        // Gets the color of the student that has been selected from the Entrance
        Entrance currentPlayerEntrance = gui.getLocalPlayer().getSchoolBoard().getEntrance();
        Color selectedStudentColor = currentPlayerEntrance.getStudents()[selectedStudentIndex];

        Alert moveSelectedStudent = GUIAlert.getAlert(GUIAlert.MOVE_ENTRANCE_STUDENT_DR_OR_ISL, selectedStudentColor.toString());
        moveSelectedStudent.showAndWait();
    }

    // endregion GSSchoolBoardRequests

    // region GSExpertModeRequests

    /**
     * Requests the player to select a CharacterCard from the playable ones
     *
     * @param playableCharacterCards The array of playable CharacterCards
     */
    public void gsRequestPlayCharacterCard(CharacterCard[] playableCharacterCards) {
        activateClicksCharacterCards(playableCharacterCards, false);
        Alert possiblePlay = GUIAlert.getAlert(GUIAlert.SELECT_CHARACTER_CARD, null);
        possiblePlay.showAndWait();
    }

    /**
     * Requests the player to choose a student from a specific CharacterCard, between the students provided
     * It sets to "clickable" only the students colored as the students contained in "availableColors"
     *
     * @param characterCardPosition The position in the characterCardArray of the
     *                              characterCard that is being played
     * @param availableColors       The colors correspondent to the students that
     *                              can be chosen between the characterCard's students
     * @param numOfAvailableStudent The number of students available on the characterCard (it could be useful)
     */
    public void gsChooseStudentFromCharacterCard(int characterCardPosition, Color[] availableColors, int numOfAvailableStudent) {
        activateClicksCharacterCardElements(characterCardPosition, availableColors, numOfAvailableStudent);

        // Gets the name of the characterCard that has been played by the player that has to draw the student
        String nameOfCharacter = gui.getModel().getCharacterCard(characterCardPosition).getCharacter().toString();

        Alert selectCharacterCardStudent = GUIAlert.getAlert(GUIAlert.SELECT_CC_STUDENT, nameOfCharacter);
        selectCharacterCardStudent.showAndWait();
    }

    /**
     * Requests the player to choose a color between the provided ones
     *
     * @param availableColors The color that can be chosen by the player
     */
    public void gsRequestChooseColor(Color[] availableColors) {
        activateClicksDiningRoomTables(availableColors);

        Alert selectColor = GUIAlert.getAlert(GUIAlert.SELECT_COLOR, "");
        selectColor.showAndWait();
    }

    /**
     * Requests the player (using the input_pane) how many students he wants to move
     *
     * @param maxNumOfStudentMovable max number of movable students
     */
    public void gsRequestHowManyStudentsToMove (int maxNumOfStudentMovable) {
        showInputPane(maxNumOfStudentMovable);
        activateClicksInputButton();
        Alert possiblePlay = GUIAlert.getAlert(GUIAlert.SELECT_NUM_STUDENTS, null);
        possiblePlay.showAndWait();
    }

    // endregion GSExpertModeRequests

    // region GSExpertModeChoiceRequests

    /**
     * Requests the player to choose between selecting a CloudTile or playing a CharacterCard.
     * Shows to the player the CloudTiles that can be selected and the CharacterCard that can be played
     *
     * @param availableClouds        An array of CloudTiles containing the CloudTiles that have students on them
     * @param playableCharacterCards An array of CharacterCard containing the playableCharacterCards
     */
    public void gsRequestChooseCloudOrPlayCC(CloudTile[] availableClouds, CharacterCard[] playableCharacterCards) {
        activateClicksCloudTilesAndCCs(availableClouds, playableCharacterCards);

        Alert selectCloudTileOrCharacterCard = GUIAlert.getAlert(GUIAlert.SELECT_CLOUD_OR_CC, "");
        selectCloudTileOrCharacterCard.showAndWait();
    }

    /**
     * Request the player to choose between selecting a student (and then moving it) or playing a CharacterCard.
     * Shows to the player the students that can be selected and the CharacterCard that can be played
     *
     * @param entranceStudents       The array of student in the entrance
     * @param playableCharacterCards An array of CharacterCard containing the playableCharacterCards
     */
    public void gsRequestMoveStudentOrPlayCC(Color[] entranceStudents, CharacterCard[] playableCharacterCards) {
        activateClicksEntranceStudentsAndCC(entranceStudents, playableCharacterCards);
        Alert possiblePlay = GUIAlert.getAlert(GUIAlert.SELECT_ENTRANCE_STUDENT_OR_CC, null);
        possiblePlay.showAndWait();
    }

    /**
     * Requests the player to move motherNature among the possible islands.
     * Shows to the player the Islands where motherNature could be moved and the CharacterCards
     * that can be played. It sets to clickable only the Islands that can be selected by the player,
     * according to the provided Islands' array and only the playable CharacterCards
     *
     * @param possibleMovement       An array containing the Islands that can be moved by the player
     * @param playableCharacterCards An array of CharacterCard representing the playable CharacterCards
     */
    public void gsRequestMoveMotherNatureOrPlayCC(Island[] possibleMovement, CharacterCard[] playableCharacterCards) {
        activateClicksIslandsAndCCs(possibleMovement, playableCharacterCards);

        Alert moveMotherNatureOrPlayCC = GUIAlert.getAlert(GUIAlert.MOVE_MOTHER_NATURE_OR_CC, "");
        moveMotherNatureOrPlayCC.showAndWait();
    }

    // endregion GSExpertModeChoiceRequests

    /**
     * Asks the player whether he wants to end his/her turn or play a CharacterCard
     */
    public void gsAskEndOfTurn() {
        Alert possiblePlay = GUIAlert.getAlert(GUIAlert.ASK_END_OF_TURN, null);

        Optional<ButtonType> choice = possiblePlay.showAndWait();

        // alert.showAndWait is the function that allows the alert to trigger
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            gui.forwardViewToVirtualController(true);
        }
        else {
            gui.forwardViewToVirtualController(false);
        }
    }

    // endregion GSRequests

    // region SwitchSceneToPSB

    /**
     * Method called on the click of a "players_button"
     * Changes scene to show the player the schoolBoard of all players
     */
    public void switchToPSBScene() {
        gui.switchScene(Pages.SCHOOL_BOARDS);
    }

    // endregion SwitchSceneToPSB
}
