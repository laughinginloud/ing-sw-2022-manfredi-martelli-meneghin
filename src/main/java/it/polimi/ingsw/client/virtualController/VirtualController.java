package it.polimi.ingsw.client.virtualController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.PlayCharacterAction;
import it.polimi.ingsw.common.message.Message;
import it.polimi.ingsw.common.message.MessageType;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VirtualController extends Thread implements Closeable {
    private static final Gson messageBuilder = new GsonBuilder().setPrettyPrinting().create();

    private final Socket           socket;
    private final DataInputStream  inputStream;
    private final DataOutputStream outputStream;

    private final View             view;

    public VirtualController(Address address, View view) throws IOException {
        socket       = new Socket(address.ipAddress(), address.port());
        inputStream  = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        this.view    = view;
    }

    public void close() {
        try {
            interrupt();
            socket.close();
        }

        // If the close throws an exception it means the socket is already closed, so I don't care
        catch (IOException ignored) {}
    }

    public void run() {
        while (!interrupted()) {
            try {
                messageInterpreter(messageBuilder.fromJson(inputStream.readUTF(), Message.class));
            }

            catch (SocketTimeoutException ignored) {/*FIXME: pensare a ping*/}

            catch (Exception e) {
                view.signalConnectionError();
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            outputStream.writeUTF(messageBuilder.toJson(message));
        }

        catch (Exception ignored) {
            //TODO
        }
    }

    private void messageInterpreter(Message message) {
        switch (message.type()) {
            case PING -> sendMessage(new Message(MessageType.PONG, null));

            case REQUESTVALUE -> {
                //TODO: [RequestValue] - Per ora non è necessario, ma potrebbe diventarlo
            }

            case SENDINFO -> {
                Map<GameValues, Object> map = (Map<GameValues, Object>) message.value();

                if (map.containsKey(GameValues.MODEL)) {
                    Client.setModel((GameModel) map.get(GameValues.MODEL));
                    map.remove(GameValues.MODEL);
                }

                map.forEach((v, o) -> modifyModelInfo(Client.getModel(), v, o));

                view.setUpBoard(); //TODO: cambiare in notifymodelchanges
            }

            case ILLEGALMESSAGE -> view.signalConnectionError(); //FIXME: cambiare in illegalmessage, tanto server rimanda comando

            case ILLEGALVALUE   -> view.signalConnectionError(); //FIXME: cambiare in illegalvalue, tanto server rimanda comando

            case REQUESTACTION -> {
                @SuppressWarnings("unchecked")
                Tuple<GameActions, Object> dataValue = (Tuple<GameActions, Object>) message.value();

                // Manages the received RequestAction depending on the GameCommandAction
                switch(dataValue.left()) {
                    case USERNAMEANDMAGICAGE -> {
                        Set<String> forbiddenUsernames = (Set<String>) dataValue.right();
                        UsernameAndMagicAge usernameAndAgeInsertion;

                        do {
                            usernameAndAgeInsertion = view.requestUsernameAndMagicAge();
                        } while (forbiddenUsernames.contains(usernameAndAgeInsertion.username()));

                        Tuple<GameActions, UsernameAndMagicAge> usernameAndMagicAgeResponse = new Tuple<>(GameActions.INSERTEDUSERNAMEANDAGE, usernameAndAgeInsertion);
                        sendMessage(new Message(MessageType.RESPONSEACTION, usernameAndMagicAgeResponse));
                    }

                    // When the server asks the player if he wants to resume an old game (if it exists)
                    case LOADGAME -> {
                        // Call the specific View's method and store the player's decision
                        Boolean                     loadGameChoice   = view.askResumeGame();
                        Tuple<GameActions, Boolean> loadGameResponse = new Tuple<>(GameActions.LOADGAMECHOICE, loadGameChoice);

                        // Send the player's choice to the server, via message
                        sendMessage(new Message(MessageType.RESPONSEACTION, loadGameResponse));

                    }

                    case PLAYASSISTANTCARD -> {
                        // Gets the playable AssistantCard sent by the server
                        AssistantCard[] playableAssistantCard = (AssistantCard[]) dataValue.right();

                        // Asks the player which AssistantCard he wants to play and stores it in a Tuple
                        AssistantCard                     chosenAssistantCard   = view.requestPlayAssistantCard(playableAssistantCard);
                        Tuple<GameActions, AssistantCard> playAssistantResponse = new Tuple<>(GameActions.CHOSENASSISTANTCARD, chosenAssistantCard);

                        // Send the player's choice to the server, via message
                        sendMessage(new Message(MessageType.RESPONSEACTION, playAssistantResponse));
                    }

                    case MOVESTUDENT -> {
                        @SuppressWarnings("unchecked")
                        // Stores the Map received from the Server via message
                        Map<GameValues, Object> receivedMap = (Map<GameValues, Object>) dataValue.right();

                        // Gets from the Map the entrance's students
                        Color[] entranceStudents = (Color[]) receivedMap.get(GameValues.COLORARRAY);

                        // Asks the player which student he would like to move
                        int selectedStudentIndex = view.requestStudentEntranceSelection(entranceStudents);
                        onlyMoveStudent(receivedMap, selectedStudentIndex);
                    }

                    case MOVEMOTHERNATURE -> {
                        @SuppressWarnings("unchecked")
                        // Stores the Map received from the Server via message
                        Map<GameValues, Object> receivedMap = (Map<GameValues, Object>) dataValue.right();

                        // Gets from the Map the maximum possible movement of mother nature
                        int maximumMovement = (int) receivedMap.get(GameValues.MAXMOTHERNATUREMOVEMENT);

                        // Asks the player in which Island he wants to move MotherNature to, according to the maxMotherNatureMovement
                        int selectedMovement = view.requestMotherNatureMovement(maximumMovement);
                        onlyMoveMotherNature(selectedMovement);
                    }

                    case CHOOSECLOUD -> {
                        @SuppressWarnings("unchecked")
                        // Stores the Map received from the Server via message
                        Map<GameValues, Object> receivedMap = (Map<GameValues, Object>) dataValue.right();

                        // Gets from the Map the availableClouds
                        CloudTile[] availableClouds = (CloudTile[]) receivedMap.get(GameValues.CLOUDARRAY);

                        // Asks the player from which CloudTile he wants to draw students during his turn
                        CloudTile selectedCloud = view.requestCloudTileSelection(availableClouds);
                        onlyChooseCloud(selectedCloud);
                    }

                    case MOVESTUDENTORPLAYCARD -> {
                        @SuppressWarnings("unchecked")
                        // Stores the Map received from the Server via message
                        Map<GameValues, Object> receivedMap = (Map<GameValues, Object>) dataValue.right();

                        // Gets from the Map the entrance's students and the playable CharacterCards
                        Color[]         entranceStudents       = (Color[])         receivedMap.get(GameValues.COLORARRAY);
                        CharacterCard[] playableCharacterCards = (CharacterCard[]) receivedMap.get(GameValues.CHARACTERCARDARRAY);

                        // Lets the player select a CharacterCard or a student from the entrance
                        Object selectedItem = view.requestMoveStudentOrPlayCC(entranceStudents, playableCharacterCards);

                        // If the player decided to move a student recall the normal-moveStudent method
                        if (selectedItem instanceof Integer selectedStudentIndex)
                            onlyMoveStudent(receivedMap, selectedStudentIndex);

                        // If the player decided to play a CharacterCard, sends the chosen characterCard to the controller
                        if (selectedItem instanceof CharacterCard selectedCharacterCard)
                            sendCharacterCardChoice(selectedCharacterCard);
                    }

                    case MOVEMOTHERNATUREORPLAYCARD -> {
                        @SuppressWarnings("unchecked")
                        // Stores the Map received from the Server via message
                        Map<GameValues, Object> receivedMap = (Map<GameValues, Object>) dataValue.right();

                        // Gets from the Map the maximum possible movement of motherNature  and the playable CharacterCards
                        int             maximumMovement        = (int)             receivedMap.get(GameValues.MAXMOTHERNATUREMOVEMENT);
                        CharacterCard[] playableCharacterCards = (CharacterCard[]) receivedMap.get(GameValues.CHARACTERCARDARRAY);

                        // Lets the player select a CharacterCard or the Island where he wants to move motherNature
                        Object selectedItem = view.requestMoveMotherNatureOrPlayCC(maximumMovement, playableCharacterCards);

                        // If the player decided to move motherNature recall the normal-moveMotherNature method
                        if (selectedItem instanceof Integer selectedMovement)
                            onlyMoveMotherNature(selectedMovement);

                        // If the player decided to play a CharacterCard, sends the chosen characterCard to the controller
                        if (selectedItem instanceof CharacterCard selectedCharacterCard)
                            sendCharacterCardChoice(selectedCharacterCard);
                    }

                    case CHOOSECLOUDORPLAYCARD -> {
                        @SuppressWarnings("unchecked")
                        // Stores the Map received from the Server via message
                        Map<GameValues, Object> receivedMap = (Map<GameValues, Object>) dataValue.right();

                        // Gets from the Map the availableClouds and the playable CharacterCards
                        CloudTile[]     availableClouds        = (CloudTile[])     receivedMap.get(GameValues.CLOUDARRAY);
                        CharacterCard[] playableCharacterCards = (CharacterCard[]) receivedMap.get(GameValues.CHARACTERCARDARRAY);

                        // Lets the player select a CharacterCard or the CloudTile from which he wants to draw the students
                        Object selectedItem = view.requestChooseCloudOrPlayCC(availableClouds, playableCharacterCards);

                        // If the player choose a CloudTile recall the normal-chooseCloudTile method
                        if (selectedItem instanceof CloudTile selectedCloud)
                            onlyChooseCloud(selectedCloud);

                        // If the player decided to play a CharacterCard, sends the chosen characterCard to the controller
                        if (selectedItem instanceof CharacterCard selectedCharacterCard)
                            sendCharacterCardChoice(selectedCharacterCard);
                    }

                    case ENDTURN -> {
                        // Asks the player whether he wants to end his turn or to play a characterCard. If he wants to end his turn:
                        if (view.askEndOfTurn()) {
                            Tuple<GameActions, UsernameAndMagicAge> endTurnResponse = new Tuple<>(GameActions.ENDTHISTURN, null);
                            sendMessage(new Message(MessageType.RESPONSEACTION, endTurnResponse));
                        }

                        // If the player wants to play a CharacterCard
                        else {
                            @SuppressWarnings("unchecked")
                            // Stores the Map received from the Server via message
                            Map<GameValues, Object> receivedMap = (Map<GameValues, Object>) dataValue.right();

                            // Gets from the Map the playable CharacterCards
                            CharacterCard[] playableCharacterCards = (CharacterCard[]) receivedMap.get(GameValues.CHARACTERCARDARRAY);

                            // Asks the player which characterCards he wants to play
                            CharacterCard selectedCharacterCard = view.requestPlayCharacterCard(playableCharacterCards);

                            //Then sends the chosen characterCard to the controller
                            sendCharacterCardChoice(selectedCharacterCard);
                        }
                    }

                    case CHARACTERCARDEFFECT -> {
                        @SuppressWarnings("unchecked")
                        Map<GameValues, Object> characterCardEffectMap = (Map<GameValues, Object>) dataValue.right();
                        PlayCharacterAction     characterAction        = (PlayCharacterAction)     characterCardEffectMap.get(GameValues.CHARACTERVALUE);

                        // Manages the received action "characterAction" depending on the character and on the phase
                        // of the character's activateEffect phase
                        switch(characterAction) {
                            case MONKFIRST -> {
                                Color[]  characterCardStudents = (Color[])  characterCardEffectMap.get(GameValues.CARDSTUDENTS);
                                Island[] islands               = (Island[]) characterCardEffectMap.get(GameValues.ISLANDARRAY);
                                int      position              = (int)      characterCardEffectMap.get(GameValues.CHARACTERCARDPOSITION);

                                // Asks the player to select the students he wants to move from the CharacterCard
                                int selectedStudentIndex = view.chooseStudentFromCharacterCard(position, characterCardStudents);

                                // Asks the player to select the Island where he wants to move the selectedPlayer
                                int selectedIslandIndex  = view.requestChooseIsland(islands);

                                // Returns the selectedStudentIndex and the selectedIslandIndex
                                // TODO:
                            }

                            case STANDARDBEARERFIRST, HERBALISTFIRST -> {
                                Island[] islands = (Island[]) characterCardEffectMap.get(GameValues.ISLANDARRAY);

                                // Asks the player to select the Island where he wants to calculate "influence" or where he
                                // wants to put the noEntryTile, depending on the characterCard that is being played
                                int selectedIslandIndex  = view.requestChooseIsland(islands);

                                // Returns the selectedStudentIndex
                                // TODO:
                            }

                            case JESTERFIRST -> {
                                // Gets how many students the player can decide to move
                                int maxMovementJester = (int) characterCardEffectMap.get(GameValues.MAXMOVEMENTJESTER);

                                // Asks the player how many students he would like to move using the characterCard 'Jester'
                                int selectedNumOfMovements = view.requestHowManyStudentsToMove(maxMovementJester);

                                // Returns the selected numOfMovements
                                //TODO:
                            }

                            case JESTERSECOND -> {
                                // Gets from the message the students present on the Entrance and on the CharacterCard, and the characterCardPosition
                                Color[]  entranceStudents      = (Color[])  characterCardEffectMap.get(GameValues.ENTRANCESTUDENTS);
                                Color[]  characterCardStudents = (Color[])  characterCardEffectMap.get(GameValues.CARDSTUDENTS);
                                int      position              = (int)      characterCardEffectMap.get(GameValues.CHARACTERCARDPOSITION);

                                // Asks the player to select the students he wants to move from the CharacterCard
                                int characterCardStudentIndex = view.chooseStudentFromCharacterCard(position,characterCardStudents);
                                int entranceStudentIndex      = view.chooseStudentFromEntrance(entranceStudents);

                                // Returns the entranceStudentIndex and the characterCardStudentIndex
                                // TODO:
                            }

                            case MERCHANTFIRST -> {
                                // Gets from the message the color that can be selected by the player using the characterCard 'MERCHANT'
                                Color[] colors = (Color[]) characterCardEffectMap.get(GameValues.COLORARRAY);

                                // Asks the player which color he wants to select in order to inhibit it during the calculus
                                // of the influence during its turn
                                Color selectedColor = view.requestChooseColor(colors);

                                // Returns the selected color!
                                //TODO:
                            }

                            case BARDFIRST -> {
                                //Gets how many students the player can decide to move using the characterCard 'Bard'
                                int maxMovementBard = (int) characterCardEffectMap.get(GameValues.MAXMOVEMENTBARD);

                                // Asks the player how many students he would like to move using the characterCard 'Jester'
                                int selectedNumOfMovements = view.requestHowManyStudentsToMove(maxMovementBard);

                                // Returns the selectedNumOfMovements
                                //TODO:
                            }

                            case BARDSECOND -> {
                                Color[] swappableStudents = (Color[]) characterCardEffectMap.get(GameValues.BARDSWAPPABLESTUDENTS);

                                // Asks the player which available students from the Entrance he wants to swap with a DiningRoomStudent
                                int entranceStudentIndex = view.chooseStudentFromEntrance(swappableStudents);

                                //TODO: [FixVirtualController] - Sistemare accesso al model
                                // Devo ricavare il vero colore dello studente selezionato dalla Entrance
                                Color selectedStudentColor = Color.RED;

                                // Gets the color of the compatible DiningRooms, according to the Info received via message from the Server
                                Color[] compatibleDiningRoom = getCompatibleDiningRooms(characterCardEffectMap, selectedStudentColor);

                                // Asks the player from which DiningRoom he wants to take the students to swap the entrance's student with
                                Color selectedDiningRoom = view.requestChooseDiningRoom(compatibleDiningRoom);

                                // Returns the entranceStudentIndex and the chosen DiningRoomTableColor
                                // TODO: Returns + modificare BardStrategy affinchè riceva direttamente l'entranceStudentIndex
                            }

                            case PRINCESSFIRST -> {
                                // Gets from the message the movableStudents present on the CharacterCard and the characterCardPosition
                                Color[] movableCCStudents = (Color[]) characterCardEffectMap.get(GameValues.CARDSTUDENTS);
                                int     position          = (int)     characterCardEffectMap.get(GameValues.CHARACTERCARDPOSITION);

                                // Asks the player to select the students he wants to move from the CharacterCard
                                int selectedStudentIndex = view.chooseStudentFromCharacterCard(position,movableCCStudents);

                                // Returns the index of the selectedStudent from the characterCard's students
                                //TODO:
                            }

                            case THIEFFIRST -> {
                                // Gets from the message the color that can be selected by the player using the characterCard 'THIEF'
                                Color[] reducibleStudents = (Color[]) characterCardEffectMap.get(GameValues.REDUCIBLECOLOR);

                                // Asks the player which color he wants to select in order to remove/reduce students of
                                // the same color from the players diningRoomTables
                                Color selectedColor = view.requestChooseColor(reducibleStudents);

                                // Returns the selected color!
                                //TODO:
                            }
                        }
                    }
                }
            }

            case GAMEWINNER -> view.signalConnectionError(); //TODO: cambiare in signalwinner //FIXME: correggere sendinfo dei winner/s/cassetti

            case GAMEDRAW -> view.signalConnectionError(); //TODO: cambiare in signaldraw

            case GAMESTART -> view.setUpBoard(); //TODO: cambiare in startgame

            case GAMEPROGRESS -> {
                view.signalConnectionError(); //TODO: cambiare in signalgameprogress
                close();
            }
        }
    }

    private void modifyModelInfo(GameModel model, GameValues value, Object object) {
        switch (value) {
            case ISLANDARRAY -> model.setIsland((Island[]) object);

            case PLAYERARRAY -> model.setPlayer((Player[]) object);

            case CLOUDARRAY  -> model.setCloudTile((CloudTile[]) object); //Utilizzo: scelta tra nuvole, sostituzione dati model

            case CHARACTERCARDARRAY -> model.setCharacterCard((CharacterCard[]) object); //Utilizzo: scelta, sostuzione

            case MOTHERNATURE -> model.setMotherNaturePosition((int) object);

            case ENTRANCE -> {/*TODO: gestire anche player (indice?)*/
                Tuple<Integer, Entrance> tuple = (Tuple<Integer, Entrance>) object;
                model.getPlayer(tuple.left()).getSchoolBoard().setEntrance(tuple.right());
            }

            case ENTRANCEARRAY -> {/*TODO: entrance ordinate per player*/
                Entrance[] entrances = (Entrance[]) object;
                for (int i = 0; i < entrances.length; ++i)
                    model.getPlayer(i).getSchoolBoard().setEntrance(entrances[i]);
            }

            case DININGROOM -> {
                Tuple<Integer, DiningRoom> tuple = (Tuple<Integer, DiningRoom>) object;
                model.getPlayer(tuple.left()).getSchoolBoard().setDiningRoom(tuple.right());
            }

            case DININGROOMARRAY -> {
                DiningRoom[] diningRooms = (DiningRoom[]) object;
                for (int i = 0; i < diningRooms.length; ++i)
                    model.getPlayer(i).getSchoolBoard().setDiningRoom(diningRooms[i]);
            }

            case SCHOOLBOARDARRAY -> {
                SchoolBoard[] schoolBoards = (SchoolBoard[]) object;
                for (int i = 0; i < schoolBoards.length; ++i)
                    model.getPlayer(i).setSchoolBoard(schoolBoards[i]);
            }

            case GLOBALPROFESSORTABLE -> model.setGlobalProfessorTable((GlobalProfessorTable) object);

            case COINPOOL -> model.setCoinPool((int) object);

            //TODO: hmm
            // Decidere come gestire i casi fisicamente impossibili
            case REMAININGTOWER -> {
            }

            case ISLANDNUM -> {
            }
            //ENDTODO
        }
    }

    /**
     * Manages the movement of a selected entrance's student during the execution of GameStateMoveStudent, then a message "responseAction" to the
     * controller, labeling it with the gameActions "moveStudentInfo"
     * @param receivedMap A Map containing the flags about DiningRoomTableFreeSeats
     * @param selectedStudentIndex An int representing the index of the selected entrance's student
     */
    private void onlyMoveStudent(Map<GameValues, Object> receivedMap, int selectedStudentIndex) {
        // Gets the entrance's students and the info about DiningRoomTableFreeSeats from the message received from the server
        Boolean[] diningRoomTableFreeSeats = (Boolean[]) receivedMap.get(GameValues.BOOLARRAY);

        // Asks where he wants to move the selected student to (islands or diningRoom) and stores it in a Tuple
        MoveStudentInfo                     moveStudentInfo      = view.movementStudentEntrance(selectedStudentIndex, diningRoomTableFreeSeats);
        Tuple<GameActions, MoveStudentInfo> moveStudentResponse  = new Tuple<>(GameActions.MOVESTUDENTINFO, moveStudentInfo);

        // Send the player's movement to the server, via a ResponseAction, labeled with a GameAction "MoveStudentInfo"
        sendMessage(new Message(MessageType.RESPONSEACTION, moveStudentResponse));
    }

    /**
     * Send to the controller the mother nature movement selected by the player
     * @param selectedMovement The motherNature movement selected by the player
     */
    private void onlyMoveMotherNature(int selectedMovement) {
        Tuple<GameActions, Integer> moveMotherNatureResponse = new Tuple<>(GameActions.CHOSENMOTHERNATUREMOVEMENT, selectedMovement);
        sendMessage(new Message(MessageType.RESPONSEACTION, moveMotherNatureResponse));
    }

    /**
     * Send to the Controller the students retrieved by the cloud chosen by the player after he received "ChooseCloud" message
     * @param selectedCloud The cloudTile selected by the player
     */
    private void onlyChooseCloud(CloudTile selectedCloud) {
        // Gets the students present on the selected cloudTile and save them in a Tuple
        Color[]                     studentsOfSelectedCloud = selectedCloud.getStudents();
        Tuple<GameActions, Color[]> chooseCloudResponse     = new Tuple<>(GameActions.STUDENTSOFSELECTEDCLOUD, studentsOfSelectedCloud);

        // Sends a message of type "responseAction" containing the Tuple
        sendMessage(new Message(MessageType.RESPONSEACTION, chooseCloudResponse));
    }

    /**
     * Sends to the controller the character correspondent to the CharacterCard the player decided to play
     * @param selectedCharacterCard The CharacterCard selected by the player
     */
    private void sendCharacterCardChoice(CharacterCard selectedCharacterCard) {
        // Gets the Character correspondent to the chosen CharacterCard and save it in a Tuple
        Character                     selectedCharacter     = selectedCharacterCard.getCharacter();
        Tuple<GameActions, Character> playCharacterResponse = new Tuple<>(GameActions.CHOSENCHARACTER, selectedCharacter);

        sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));
    }

    private Color[] getCompatibleDiningRooms(Map<GameValues, Object> characterCardEffectMap, Color selectedColor) {
        // Gets from the message the BardSwapMap in order to make "clickable" only allowed diningRoom's students
        @SuppressWarnings("unchecked")
        Map<Color, Boolean[]> possibleMovementMap = (Map<Color, Boolean[]>) characterCardEffectMap.get(GameValues.BARDSWAPMAP);

        // Gets the color of the diningRoomTable where the students can be moved
        List<Color> compatibleDiningRoomList = new ArrayList<>();
        Boolean[] diningRoomFlag = possibleMovementMap.get(selectedColor);
        for (Color color : Color.values())
            if (diningRoomFlag[color.ordinal()])
                compatibleDiningRoomList.add(color);

        // Transform the list into an array of compatible diningRooms' colors
        Color[] compatibleDiningRoom = new Color[compatibleDiningRoomList.size()];
        for (int i = 0; i < compatibleDiningRoomList.size(); i++)
            compatibleDiningRoom[i] = compatibleDiningRoomList.get(i);

        return compatibleDiningRoom;
    }
}
