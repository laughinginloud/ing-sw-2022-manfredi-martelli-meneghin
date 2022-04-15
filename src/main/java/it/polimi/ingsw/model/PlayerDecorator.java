package it.polimi.ingsw.model;

/**
 * Abstract class providing the interface for the player's decorators
 * @author Mattia Martelli
 */
public abstract class PlayerDecorator {
    /**
     * The player to be decorated
     */
    protected final Player basePlayer;

    protected PlayerDecorator(Player basePlayer) {
        this.basePlayer = basePlayer;
    }

    public AssistantCard getAssistantCard(int index) {
        return basePlayer.getAssistantCard(index);
    }

    public AssistantCard[] getAssistantDeck() {
        return basePlayer.getAssistantDeck();
    }

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
