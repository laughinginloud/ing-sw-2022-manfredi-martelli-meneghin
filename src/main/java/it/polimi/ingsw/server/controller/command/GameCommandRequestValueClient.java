package it.polimi.ingsw.server.controller.command;

//Server -> Client
public class GameCommandRequestValueClient extends GameCommandRequestValue {
    public GameCommandRequestValueClient(GameCommandValues value) {
        super(value);
    }

    public Object executeCommand() {
        //TODO: da implementare lato client, una volta implementata la view
        return value;
    }
}
