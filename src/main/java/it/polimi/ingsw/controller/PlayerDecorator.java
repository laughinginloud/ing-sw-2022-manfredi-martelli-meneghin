package it.polimi.ingsw.controller;

public abstract class PlayerDecorator {
    private Player basePlayer;

    public int getPlayerID() {
        return basePlayer.getPlayerID();
    }

    public Wizard getPlayerWizard() {
        return basePlayer.getPlayerWizard();
    }

    public String getUsername() {
        return basePlayer.getUsername();
    }
}
