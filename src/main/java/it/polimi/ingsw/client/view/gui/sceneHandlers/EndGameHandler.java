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
 * @author Giovanni Manfredi
 */
public class EndGameHandler implements GUIHandler {

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
     * @param winner the game's winner
     */
    public void displayWinner(Player winner) {
        description_label.setText("And the winner is ...");

        // Sets the winning player Wizard and username
        String wizardImgPath = PathHelper.fromWizardEnumToHandlerPath(winner.getPlayerWizard());
        wizard0_img.setImage(new Image(getClass().getResourceAsStream(wizardImgPath)));
        user0_label.setText(winner.getUsername());

        ImageView wizard;
        Label     username;
        // For each wizard and username that did not win hides them
        for (int i = 1; i < 4; i++) {
            wizard   = IDHelper.egFindWizardID(this, i);
            wizard.setVisible(false);

            username = IDHelper.egFindUsernameID(this, i);
            username.setVisible(false);
        }
    }

    /**
     * Shows the players in the winning team in the EndGameScene
     * @param team the game's winning team
     */
    public void displayWinningTeam(List<Player> team) {
        description_label.setText("And the winning team is ...");

        displayPlayers(team);

        // For each wizard and username not in the team hides it
        wizard0_img.setVisible(false);
        wizard3_img.setVisible(false);
        user0_label.setVisible(false);
        user3_label.setVisible(false);
    }

    /**
     * Shows the players who ended the game in a draw in the EndGameScene
     * @param drawers the players who ended the game in a draw
     */
    public void displayDraw(List<Player> drawers) {
        description_label.setText("It's a draw! Between ...");

        displayPlayers(drawers);

        ImageView currPlayerWizard;
        Label     currPlayerUsername;
        // For each wizard and username not in the List hides them
        for (int i = drawers.size(); i < 4; i++) {
            currPlayerWizard   = IDHelper.egFindWizardID(this, i);
            currPlayerWizard.setVisible(false);

            currPlayerUsername = IDHelper.egFindUsernameID(this, i);
            currPlayerUsername.setVisible(false);
        }
    }

    /**
     * Shows the players array it receives as input in the EndGameScene
     * @param players the players to be shown
     */
    private void displayPlayers(List<Player> players) {
        Player    curr;
        ImageView currPlayerWizard;
        Label     currPlayerUsername;
        String    wizardImgPath;
        int       k = 0, m = 4;

        // If the players are 2 or less, I'll display them from the second imgView (to center them)
        if (players.size() < 3) {
            k = 1;
            m = 3;
        }

        for (int i = k; i < m; i++) {
            // Gets the current Player from the list
            curr = players.get(i);

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
     * @param gui the ViewGUI instance
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
