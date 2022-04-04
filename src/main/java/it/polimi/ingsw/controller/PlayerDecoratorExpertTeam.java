package it.polimi.ingsw.controller;

public class PlayerDecoratorExpertTeam extends PlayerDecorator {
    private int coinCount;
    private Player teamMember;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public Player getTeamMember() {
        return teamMember;
    }
}
