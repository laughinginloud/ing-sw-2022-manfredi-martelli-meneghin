package it.polimi.ingsw.common.viewRecord;

import it.polimi.ingsw.common.model.*;

import java.util.Optional;

public record MoveStudentInfo(boolean toDiningRoom, Integer islandNum, int studentIndex) {}

//TODO: aggiungere il numero dell'isola, aggiungere la scelta del numero dello studente invece del colore
