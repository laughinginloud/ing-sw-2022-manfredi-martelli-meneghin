package it.polimi.ingsw.model;

public class Player {
    private int playerID;
    private AssistantCard[] assistantDeck;
    private Wizard playerWizard;
    private String username;
    private SchoolBoard schoolBoard;

    private void assistantDeckSetup() {
        assistantDeck = new AssistantCard[10];

        for (int i = 0; i < 10; ++i) {
            assistantDeck[i] = new AssistantCard(i + 1, (i + 1) % 2 == 0 ? (i + 1) / 2 : (i + 2) / 2);
        }
    }

    public int getPlayerID() {
        return playerID;
    }

    public AssistantCard getAssistantCard(int index) {
        AssistantCard temp = assistantDeck[index];
        System.arraycopy(assistantDeck, index + 1, assistantDeck, index, assistantDeck.length - index - 1);
        return temp;
    }

    public Wizard getPlayerWizard() {
        return playerWizard;
    }

    public String getUsername() {
        return username;
    }
}
