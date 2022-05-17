package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.Address;

public interface View {
    void initialize(); //TODO: decidere se collassare nel costruttore

    void setUpBoard();

    Address getAddress();

    void signalConnectionError();
}
