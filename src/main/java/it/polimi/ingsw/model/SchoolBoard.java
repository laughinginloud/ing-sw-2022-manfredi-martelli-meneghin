package it.polimi.ingsw.model;

public class SchoolBoard {
    private TowerColor towerColor;
    private int towerCount;
    private Entrance entrance;
    private DiningRoom diningRoom;

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public int getTowerCount() {
        return towerCount;
    }

    public void setTowerCount(int towerCount) {
        this.towerCount = towerCount;
    }
}
