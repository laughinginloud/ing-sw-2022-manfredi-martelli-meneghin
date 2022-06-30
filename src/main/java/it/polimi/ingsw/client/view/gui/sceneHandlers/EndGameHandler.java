package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.IDHelper;
import it.polimi.ingsw.client.view.gui.PathHelper;
import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.common.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Handler (or Controller) of the scene EndGame (endGamePage.fxml)
 * The client is shown the game's winner(s) or of a draw
 *
 * @author Giovanni Manfredi
 */

// Warnings for unused fields have been disabled, since the fields that these fields are accessing
// could be useful in the future and difficult to add without opening the .fxml
// Warnings for gui field has been suppressed to allow future modification of the class
// Warnings for possible null parameters to an addImage have been suppressed because the input is constant
// Warnings for classes that are not exported has been suppressed because are intentionally not exported
@SuppressWarnings({"unused", "FieldCanBeLocal", "ConstantConditions", "ClassEscapesDefinedScope"})

public final class EndGameHandler implements GUIHandler {

    private ViewGUI gui;

    // region FXML_Ids

    @FXML
    public BorderPane endGame_borderPane;

    @FXML
    public AnchorPane endGame_pane;

    @FXML
    public ImageView background_img;

    @FXML
    public Label title_pane;

    @FXML
    public Label description_label;

    @FXML
    public GridPane winners_gridPane;

    @FXML
    public ImageView wizard0_img;

    @FXML
    public ImageView wizard1_img;

    @FXML
    public ImageView wizard2_img;

    @FXML
    public ImageView wizard3_img;

    @FXML
    public Label user0_label;

    @FXML
    public Label user1_label;

    @FXML
    public Label user2_label;

    @FXML
    public Label user3_label;

    // endregion FXML_Ids

    /**
     * Shows the single winner it receives as input in the EndGameScene
     *
     * @param winner the game's winner
     */
    public void displayWinner(Player winner) {
        description_label.setText("And the winner is ...");

        // Sets the winning player Wizard and username
        String wizardImgPath = PathHelper.fromWizardEnumToHandlerPath(winner.getPlayerWizard());
        wizard0_img.setImage(new Image(getClass().getResourceAsStream(wizardImgPath)));
        user0_label.setText(winner.getUsername());
    }

    /**
     * Shows the players in the winning team in the EndGameScene
     *
     * @param team the game's winning team
     */
    public void displayWinningTeam(List<Player> team) {
        description_label.setText("And the winning team is ...");

        displayPlayers(team);
    }

    /**
     * Shows the players who ended the game in a draw in the EndGameScene
     *
     * @param drawers the players who ended the game in a draw
     */
    public void displayDraw(List<Player> drawers) {
        description_label.setText("It's a draw! Between ...");

        displayPlayers(drawers);
    }

    /**
     * Shows the players array it receives as input in the EndGameScene
     *
     * @param players the players to be shown
     */
    private void displayPlayers(List<Player> players) {
        Player    curr;
        ImageView currPlayerWizard;
        Label     currPlayerUsername;
        String    wizardImgPath;
        int       k = 0, m = 4;

        // If the players are 2, I'll display them from the second imgView (to center them)
        if (players.size() == 2) {
            k = 1;
            m = 3;
        }

        for (int i = k, j = 0; i < m; i++, j++) {
            // Gets the current Player from the list
            curr = players.get(j);

            // Gets the id of the ImageView and the path to the img
            currPlayerWizard = IDHelper.egFindWizardID(this, i);
            wizardImgPath    = PathHelper.fromWizardEnumToHandlerPath(curr.getPlayerWizard());

            // Sets the correct img
            currPlayerWizard.setImage(new Image(getClass().getResourceAsStream(wizardImgPath)));

            // Gets the id of the Label
            currPlayerUsername = IDHelper.egFindUsernameID(this, i);

            // Sets the correct Username
            currPlayerUsername.setText(curr.getUsername());
        }
    }

    /**
     * Sets the ViewGUI at which the EndGameHandler is related
     *
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
