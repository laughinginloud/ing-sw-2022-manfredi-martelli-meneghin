module it.polimi.ingsw.client.view.gui.sceneHandlers{
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.jline;


    opens it.polimi.ingsw.client.view.gui.sceneHandlers to javafx.fxml;
    exports it.polimi.ingsw.client.view.gui.sceneHandlers;

    opens it.polimi.ingsw.client.view.gui to javafx.fxml;
    exports it.polimi.ingsw.client.view.gui;
}
