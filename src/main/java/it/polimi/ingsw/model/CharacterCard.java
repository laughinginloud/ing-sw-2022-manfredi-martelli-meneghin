package it.polimi.ingsw.model;

/**
 * Class representing a CharacterCard
 * @author Sebastiano Meneghin
 */
public class CharacterCard {
    private int cost;
    private int cardID;

    public CharacterCard(int cardID) {
        this.cardID = cardID;
    }

    public int getCost() { return this.cost; }

    public void setCost(int cost) { this.cost = cost; }

    public int getCardID() { return this.cardID; }
}
