package it.polimi.ingsw.client.view.gui.sceneHandlers;

import it.polimi.ingsw.client.view.gui.ViewGUI;
import it.polimi.ingsw.common.model.Wizard;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WizardChoiceHandler implements GUIHandler {
    private ViewGUI gui;

    private Wizard[] availableWizard;

    // TODO JavaDocs + Regions
    // TODO Implementation

    @FXML
    private AnchorPane wizardChoice_pane;

    @FXML
    private ImageView wizardChoice_imgView;

    @FXML
    private AnchorPane wizardChoice_subPane;

    @FXML
    private HBox wizardChoice_hbox;

    @FXML
    private AnchorPane wizard0_pane;

    @FXML
    private ImageView wizard0_img;

    @FXML
    private AnchorPane wizard1_pane;

    @FXML
    private ImageView wizard1_img;

    @FXML
    private AnchorPane wizard2_pane;

    @FXML
    private ImageView wizard2_img;

    @FXML
    private AnchorPane wizard3_pane;

    @FXML
    private ImageView wizard3_img;

    @FXML
    private Label wizardChoice_titleLabel;

    // IT'S ONLY A TEST METHOD, IT WILL BE REMOVED
    public void testMethod(){
        // How to de-saturate a card "Not playable"
        Effect notAvailableCard = (ColorAdjust) new ColorAdjust(0,-1.0,-0.3,0);
        wizard0_img.effectProperty().set(notAvailableCard);
        // How to make it not clickable
        wizard0_img.setOnMouseClicked(null);
    }

    /**
     * Sets the wizard array called availableWizard
     * @param availableWizard The wizard that can be chosen by the player
     */
    public void setAvailableWizard(Wizard[] availableWizard) {
        this.availableWizard = availableWizard;
    }

    /**
     * Sets on the scene the elements that are clickable, i.e. wizard that can
     * be chosen by the player
     */
    public void setClickableElements() {
        Set<Wizard> availableWizardSet = new HashSet<>();
        Collections.addAll(availableWizardSet, availableWizard);

        Set<Wizard> wizardSet = Wizard.set();

        for (Wizard wizard : wizardSet) {
            ImageView wizardID = fromWizardEnumToID(wizard);

            // If the wizardCard is not contained in the wizardAvailableSet,
            // makes it un-clickable and "obscures" it
            if (!availableWizardSet.contains(wizard)) {

                // It de-saturates a card "Not playable"
                Effect notAvailableCard = (ColorAdjust) new ColorAdjust(0,-1.0,-0.3,0);
                wizardID.effectProperty().set(notAvailableCard);

                // Makes the notPlayableWizard not clickable
                wizardID.setOnMouseClicked(null);
            }

            else {
                // Creates a function that will handle the mouseClick
                EventHandler<MouseEvent> clickOnWizardHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // Invokes the function clickOnWizard
                        clickOnWizard(mouseEvent);

                        mouseEvent.consume();
                    }
                };

                // Then links the created handler to the click of the wizardImage
                wizardID.setOnMouseClicked(clickOnWizardHandler);
            }
        }
    }

    /**
     * Gets the wizard selected by the player clicking on a specific wizardCard, then
     * forward the selected wizard to the VirtualController
     * @param mouseEvent The mouseEvent generated by the player's click on a Wizard
     */
    public void clickOnWizard (MouseEvent mouseEvent) {
        // Gets the wizard from then mouseEvent
        ImageView selectedWizardID = (ImageView) mouseEvent.getSource();

        // Send to the VirtualController the wizard selected by the player
        for (Wizard wizard : availableWizard)
            if (fromWizardEnumToID(wizard) == selectedWizardID) {
                gui.forwardViewToVirtualController(wizard);
                break;
            }
    }

    /**
     * Gets the wizardImagePath of a specific Wizard
     * @param wizard The wizard we want to find the image of
     * @return A string representing the path to the wizard's image
     */
    public String fromWizardEnumToPath (Wizard wizard) {
        String wizardPath = "";

        switch (wizard) {
            case NATURE -> wizardPath = "@../images/wizards/0_NATURE.jpg";

            case DESERT -> wizardPath = "@../images/wizards/1_DESERT.jpg";

            case CLOUD  -> wizardPath = "@../images/wizards/2_CLOUD.jpg";

            case SNOW   -> wizardPath = "@../images/wizards/3_SNOW.jpg";
        }

        return wizardPath;
    }

    /**
     * Gets the wizardID of a specific Wizard
     * @param wizard The wizard we want to find the ID of
     * @return An ImageView representing the wizard ID
     */
    public ImageView fromWizardEnumToID (Wizard wizard) {
        ImageView wizardID = new ImageView();

        switch (wizard) {
            case NATURE -> wizardID = wizard0_img;

            case DESERT -> wizardID = wizard1_img;

            case CLOUD  -> wizardID = wizard2_img;

            case SNOW   -> wizardID = wizard3_img;
        }

        return wizardID;
    }

    /**
     * @param gui
     */
    @Override
    public void setGUI(ViewGUI gui) {
        this.gui = gui;
    }
}
