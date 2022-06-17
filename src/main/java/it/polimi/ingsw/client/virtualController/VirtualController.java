package it.polimi.ingsw.client.virtualController;

import com.google.gson.Gson;
import it.polimi.ingsw.client.Address;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.PlayCharacterAction;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.message.Message;
import it.polimi.ingsw.common.message.MessageType;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.model.Character;
import it.polimi.ingsw.common.json.Constants;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.viewRecord.GameRules;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class VirtualController extends Thread implements Closeable {
    private static final Gson messageBuilder = Constants.jsonBuilder;

    private final Socket           socket;
    private final DataInputStream  inputStream;
    private final DataOutputStream outputStream;

    private final View             view;

    private       VCStates         vcState;
    private       InfoMap          savedReceivedMap = null;
    private       InfoMap          savedToSendMap   = null;

    private       String           username;

    public VirtualController(Address address, View view) throws IOException {
        socket       = new Socket(address.ipAddress(), address.port());
        inputStream  = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        socket.setSoTimeout(5000);

        this.view    = view;

        // Sets himself as the VirtualController linked to the View that just invoked this method
        view.setVirtualController(this);

        start();
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
        while (!isInterrupted()) {
            try {
                String msg;
                synchronized (inputStream) {
                    msg = inputStream.readUTF();
                }
                System.out.println(msg);
                messageInterpreter(messageBuilder.fromJson(msg, Message.class));
            }

            catch (EOFException ignored) {}

            // If the input stream timeouts, it means that the server is offline, as I should receive at least a ping
            catch (Exception ignored) {
                if (isInterrupted())
                    return;

                view.signalConnectionError();
                close();
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            String msg = messageBuilder.toJson(message);
            System.out.println(msg);
            synchronized (outputStream) {
                outputStream.writeUTF(msg);
                outputStream.flush();
            }
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void messageInterpreter(Message message) {
        switch (message.type()) {
            case PING                    -> sendMessage(new Message(MessageType.PONG, null));

            case SENDINFO                -> {
                InfoMap map = (InfoMap) message.value();

                if (map.containsKey(GameValues.MODEL)) {
                    view.setModel((GameModel) map.get(GameValues.MODEL));
                    map.remove(GameValues.MODEL);

                    // Sends to the view the notification about the values of the model that
                    // has been updated by the SendInfo
                    Set<GameValues> updatedValues = new HashSet<>();
                    updatedValues.add(GameValues.MODEL);
                    view.updateModel(view.getModel(), updatedValues);
                }

                map.forEach((v, o) -> modifyModelInfo(view.getModel(), v, o));
            }

            case REQUESTACTION           -> {
                @SuppressWarnings("unchecked")
                Tuple<GameActions, Object> dataValue = (Tuple<GameActions, Object>) message.value();
                switchRequestAction(dataValue);
            }

            case GAMEWINNER              -> view.signalWinner((Player)       message.value());
            case GAMEWINNERTEAM          -> view.signalWinner((List<Player>) message.value());
            case GAMEDRAW                -> view.signalDraw  ((List<Player>) message.value());

            case GAMESTART               -> view.notifyGameStart();
            case CUR_PLAYER_END_TURN     -> view.notifyEndOfTurn();
            case OTHER_PLAYER_START_TURN -> view.notifyStartGameTurn((String) message.value());

            case GAMEINTERRUPT           -> view.notifyPlayerDisconnection();

            case GAMEPROGRESS            -> {
                view.notifyGameInProgress();
                close();
            }
        }
    }

    private void modifyModelInfo(GameModel model, GameValues value, Object object) {
        switch (value) {
            case COINPOOL             -> model.setCoinPool((int) object);

            case ISLANDARRAY          -> model.setIsland((Island[]) object);

            case PLAYERARRAY          -> {
                model.setPlayer((Player[]) object);

                GlobalProfessorTable gpt = model.getGlobalProfessorTable();

                for (Color color : Color.values())
                    gpt.getProfessorLocation(color).ifPresent(p -> gpt.setProfessorLocation(color, p));
            }

            case CLOUDARRAY           -> model.setCloudTile((CloudTile[]) object);

            case CHARACTERCARDARRAY   -> model.setCharacterCard((CharacterCard[]) object);

            case MOTHERNATURE         -> model.setMotherNaturePosition((int) object);

            case GLOBALPROFESSORTABLE -> {
                GlobalProfessorTable gpt = (GlobalProfessorTable) object;

                for (Color color : Color.values())
                    gpt.getProfessorLocation(color).ifPresent(p -> gpt.setProfessorLocation(color, p));

                model.setGlobalProfessorTable(gpt);
            }

            case ENTRANCE             -> {
                Tuple<Integer, Entrance> tuple = (Tuple<Integer, Entrance>) object;
                model.getPlayer(tuple.left()).getSchoolBoard().setEntrance(tuple.right());
            }

            case ENTRANCEARRAY        -> {
                Entrance[] entrances = (Entrance[]) object;
                for (int i = 0; i < entrances.length; ++i)
                    model.getPlayer(i).getSchoolBoard().setEntrance(entrances[i]);
            }

            case DININGROOM           -> {
                Tuple<Integer, DiningRoom> tuple = (Tuple<Integer, DiningRoom>) object;
                model.getPlayer(tuple.left()).getSchoolBoard().setDiningRoom(tuple.right());
            }

            case DININGROOMARRAY      -> {
                DiningRoom[] diningRooms = (DiningRoom[]) object;
                for (int i = 0; i < diningRooms.length; ++i)
                    model.getPlayer(i).getSchoolBoard().setDiningRoom(diningRooms[i]);
            }

            case SCHOOLBOARDARRAY     -> {
                SchoolBoard[] schoolBoards = (SchoolBoard[]) object;
                for (int i = 0; i < schoolBoards.length; ++i)
                    model.getPlayer(i).setSchoolBoard(schoolBoards[i]);
            }
        }

        // Sends to the view the notification about the values of the model that
        // has been updated by the SendInfo
        Set<GameValues> updatedValues = new HashSet<>();
        updatedValues.add(value);
        view.updateModel(view.getModel(), updatedValues);
    }

    /**
     * Manages the movement of a selected entrance's student during the execution of GameStateMoveStudent, then a message "responseAction" to the
     * controller, labeling it with the gameActions "moveStudentInfo"
     * @param receivedMap A Map containing the flags about DiningRoomTableFreeSeats
     * @param selectedStudentIndex An int representing the index of the selected entrance's student
     */
    private void onlyMoveStudent(InfoMap receivedMap, int selectedStudentIndex) {
        // Gets the entrance's students and the info about DiningRoomTableFreeSeats from the message received from the server
        Boolean[] diningRoomTableFreeSeats = (Boolean[]) receivedMap.get(GameValues.BOOLARRAY);

        // Saves the Virtual Controller State
        this.vcState = VCStates.REQ_MOVE_STUD_SECOND;

        // Asks where he wants to move the selected student to (islands or diningRoom)
        view.requestStudentEntranceMovement(selectedStudentIndex, diningRoomTableFreeSeats);
    }

    /**
     * Sends to the controller the mother nature movement selected by the player
     * @param selectedIslandIndex The Island where the player wants to move motherNature to
     */
    private void onlyMoveMotherNature(int selectedIslandIndex) {
        Tuple<GameActions, Integer> moveMotherNatureResponse = new Tuple<>(GameActions.CHOSENMOTHERNATUREMOVEMENT, selectedIslandIndex);
        sendMessage(new Message(MessageType.RESPONSEACTION, moveMotherNatureResponse));
    }

    /**
     * Sends to the Controller the students retrieved by the cloud chosen by the player after he received "ChooseCloud" message
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
    private synchronized void sendCharacterCardChoice(CharacterCard selectedCharacterCard) {
        // Gets the Character correspondent to the chosen CharacterCard and save it in a Tuple
        Character                     selectedCharacter     = selectedCharacterCard.getCharacter();
        Tuple<GameActions, Character> playCharacterResponse = new Tuple<>(GameActions.CHOSENCHARACTER, selectedCharacter);

        sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));
    }

    private Color[] getCompatibleDiningRooms(InfoMap characterCardEffectMap, Color selectedColor) {
        // Gets from the message the BardSwapMap in order to make "clickable" only allowed diningRoom's students
        @SuppressWarnings("unchecked")
        Map<Color, Boolean[]> possibleMovementMap = (Map<Color, Boolean[]>) characterCardEffectMap.get(GameValues.BARDSWAPMAP);

        // Gets the color of the diningRoomTable where the students can be moved
        List<Color> compatibleDiningRoomList = new ArrayList<>();
        Boolean[]   diningRoomFlag           = possibleMovementMap.get(selectedColor);
        for (Color color : Color.values())
            if (diningRoomFlag[color.ordinal()])
                compatibleDiningRoomList.add(color);

        // Transform the list into an array of compatible diningRooms' colors
        Color[] compatibleDiningRoom = new Color[compatibleDiningRoomList.size()];
        for (int i = 0; i < compatibleDiningRoomList.size(); i++)
            compatibleDiningRoom[i] = compatibleDiningRoomList.get(i);

        return compatibleDiningRoom;
    }

    private void switchRequestAction(Tuple<GameActions, Object> dataValue) {
        // Manages the received RequestAction depending on the GameCommandAction
        switch(dataValue.left()) {
            case USERNAMEANDMAGICAGE        -> {
                Set<String> forbiddenUsernames = (Set<String>) dataValue.right();

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_USER_AGE;

                // Sends a request to the View
                view.requestUsernameAndMagicAge(forbiddenUsernames);
            }

            case RULES                      -> {
                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_RULES;

                // Asks the player which rules he would like to play with, then saves the record GameRules in a tuple
                view.askRules();
            }

            case WIZARD                     -> {
                // Gets the availableWizards sent by the server
                Wizard[] availableWizards = (Wizard[]) dataValue.right();

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_WIZARD;

                // Asks the player which wizard he wants to play, then saves it in a Tuple(GameActions, Wizard)
                view.requestWizard(availableWizards);
            }

            case LOADGAME                   -> {
                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_LOAD;

                // Call the specific View's method and store the player's decision
                view.askReloadGame();
            }

            case PLAYASSISTANTCARD          -> {
                // Gets the playable AssistantCard sent by the server
                AssistantCard[] playableAssistantCard = (AssistantCard[]) dataValue.right();

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_PLAY_ASS;

                // Asks the player which AssistantCard he wants to play
                view.requestPlayAssistantCard(playableAssistantCard);
            }

            case MOVESTUDENT                -> {
                // Stores the Map received from the Server via message
                InfoMap receivedMap = (InfoMap) dataValue.right();

                // Gets from the Map the entrance's students
                Color[] entranceStudents = (Color[]) receivedMap.get(GameValues.COLORARRAY);

                // Saves the receivedMap in this instance's supportMap in order to use it after the interaction with the user
                // and the Virtual Controller state
                savedReceivedMap = new InfoMap(receivedMap);
                this.vcState = VCStates.REQ_MOVE_STUD_FIRST;

                // Asks the player which student he would like to move
                view.chooseStudentFromEntrance(entranceStudents);
            }

            case MOVEMOTHERNATURE           -> {
                // Stores the Map received from the Server via message
                InfoMap receivedMap = (InfoMap) dataValue.right();

                // Gets from the Map the maximum possible movement of mother nature
                Island[] possibleMovement = (Island[]) receivedMap.get(GameValues.MNPOSSIBLEMOVEMENTS);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_MOVE_MN;

                // Asks the player in which Island he wants to move MotherNature to, according to the maxMotherNatureMovement
                view.requestMotherNatureMovement(possibleMovement);
            }

            case CHOOSECLOUD                -> {
                // Stores the Map received from the Server via message
                InfoMap receivedMap = (InfoMap) dataValue.right();

                // Gets from the Map the availableClouds
                CloudTile[] availableClouds = (CloudTile[]) receivedMap.get(GameValues.CLOUDARRAY);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_CHOOSE_CLOUD;

                // Asks the player from which CloudTile he wants to draw students during his turn
                view.requestCloudTileSelection(availableClouds);
            }

            case MOVESTUDENTORPLAYCARD      -> {
                // Stores the Map received from the Server via message
                InfoMap receivedMap = (InfoMap) dataValue.right();

                // Gets from the Map the entrance's students and the playable CharacterCards
                Color[] entranceStudents = (Color[]) receivedMap.get(GameValues.COLORARRAY);
                CharacterCard[] playableCharacterCards = (CharacterCard[]) receivedMap.get(GameValues.CHARACTERCARDARRAY);

                // Saves the receivedMap in this instance's supportMap in order to use it after the interaction with the user
                // and the Virtual Controller state
                savedReceivedMap = new InfoMap(receivedMap);
                this.vcState = VCStates.REQ_MOVE_STUD_OR_PLAY;

                // Lets the player select a CharacterCard or a student from the entrance
                view.requestMoveStudentOrPlayCC(entranceStudents, playableCharacterCards);
            }

            case MOVEMOTHERNATUREORPLAYCARD -> {
                // Stores the Map received from the Server via message
                InfoMap receivedMap = (InfoMap) dataValue.right();

                // Gets from the Map the maximum possible movement of motherNature  and the playable CharacterCards
                Island[] possibleMovement = (Island[]) receivedMap.get(GameValues.MNPOSSIBLEMOVEMENTS);
                CharacterCard[] playableCharacterCards = (CharacterCard[]) receivedMap.get(GameValues.CHARACTERCARDARRAY);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_MOVE_MN_OR_PLAY;

                // Lets the player select a CharacterCard or the Island where he wants to move motherNature
                view.requestMoveMotherNatureOrPlayCC(possibleMovement, playableCharacterCards);
            }

            case CHOOSECLOUDORPLAYCARD      -> {
                // Stores the Map received from the Server via message
                InfoMap receivedMap = (InfoMap) dataValue.right();

                // Gets from the Map the availableClouds and the playable CharacterCards
                CloudTile[] availableClouds = (CloudTile[]) receivedMap.get(GameValues.CLOUDARRAY);
                CharacterCard[] playableCharacterCards = (CharacterCard[]) receivedMap.get(GameValues.CHARACTERCARDARRAY);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_CHOOSE_CLOUD_OR_PLAY;

                // Lets the player select a CharacterCard or the CloudTile from which he wants to draw the students
                view.requestChooseCloudOrPlayCC(availableClouds, playableCharacterCards);
            }

            case ENDTURN                    -> {
                // Stores the Map received from the Server via message
                InfoMap receivedMap = (InfoMap) dataValue.right();

                // Saves the receivedMap in this instance's supportMap in order to use it after the interaction with the user
                // and the Virtual Controller state
                savedReceivedMap = new InfoMap(receivedMap);
                this.vcState = VCStates.REQ_END_TURN_ASK;

                // Asks the player whether he wants to end his turn or to play a characterCard
                view.askEndOfTurn();
            }

            case CHARACTERCARDEFFECT        -> {
                InfoMap characterCardEffectMap = (InfoMap) dataValue.right();
                PlayCharacterAction     characterAction        = (PlayCharacterAction)     characterCardEffectMap.get(GameValues.CHARACTERVALUE);

                switchReqActCharEffect(characterCardEffectMap, characterAction);
            }
        }
    }

    private void switchReqActCharEffect(InfoMap characterCardEffectMap, PlayCharacterAction characterAction) {
        // Manages the received action "characterAction" depending on the character and on the phase
        // of the character's activateEffect phase
        switch (characterAction) {
            case MONKFIRST -> {
                Color[]  characterCardStudents  = (Color[])  characterCardEffectMap.get(GameValues.CARDSTUDENTS);
                int      characterCardPosition  = (int)      characterCardEffectMap.get(GameValues.CHARACTERCARDPOSITION);
                int      numOfAvailableStudents =            characterCardStudents.length;

                // Saves the characterCardEffectMap in this instance's supportMap in order to use it after the interaction with the user
                // and saves the Virtual Controller state
                savedReceivedMap = new InfoMap(characterCardEffectMap);
                this.vcState     = VCStates.REQ_MONK_FIRST__STUDENT;

                // Asks the player to select the students he wants to move from the CharacterCard
                view.chooseStudentFromCharacterCard(characterCardPosition, characterCardStudents, numOfAvailableStudents);
            }

            case STANDARDBEARERFIRST, HERBALISTFIRST -> {
                Island[] islands = (Island[]) characterCardEffectMap.get(GameValues.ISLANDARRAY);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_BEARER_FIRST_OR_HERB_FIRST;

                // Asks the player to select the Island where he wants to calculate "influence" or where he
                // wants to put the noEntryTile, depending on the characterCard that is being played
                view.requestChooseIsland(islands);
            }

            case JESTERFIRST -> {
                // Gets how many students the player can decide to move
                int maxMovementJester = (int) characterCardEffectMap.get(GameValues.MAXMOVEMENTJESTER);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_JEST_FIRST;

                // Asks the player how many students he would like to move using the characterCard 'Jester'
                view.requestHowManyStudentsToMove(maxMovementJester);
            }

            case JESTERSECOND -> {
                // Gets from the receivedMessageInfo the students present on the CharacterCard and the characterCardPosition
                Color[] characterCardStudents    = (Color[]) characterCardEffectMap.get(GameValues.CARDSTUDENTS);
                int     characterCardPosition    = (int)     characterCardEffectMap.get(GameValues.CHARACTERCARDPOSITION);
                int     numOfCCAvailableStudents =           characterCardStudents.length;

                // Saves the characterCardEffectMap in this instance's supportMap in order to use it after the interaction with the user
                // and saves the Virtual Controller state
                savedReceivedMap = new InfoMap(characterCardEffectMap);
                this.vcState     = VCStates.REQ_JEST_SECOND__CARD;

                // Asks the player to select the students he wants to move from the CharacterCard
                view.chooseStudentFromCharacterCard(characterCardPosition, characterCardStudents, numOfCCAvailableStudents);
            }

            case MERCHANTFIRST -> {
                // Gets from the message the color that can be selected by the player using the characterCard 'MERCHANT'
                Color[] colors = (Color[]) characterCardEffectMap.get(GameValues.COLORARRAY);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_MERCH_FIRST;

                // Asks the player which color he wants to select in order to inhibit it during the calculus
                // of the influence during its turn
                view.requestChooseColor(colors);
            }

            case BARDFIRST -> {
                //Gets how many students the player can decide to move using the characterCard 'Bard'
                int maxMovementBard = (int) characterCardEffectMap.get(GameValues.MAXMOVEMENTBARD);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_BARD_FIRST;

                // Asks the player how many students he would like to move using the characterCard 'Bard'
                view.requestHowManyStudentsToMove(maxMovementBard);
            }

            case BARDSECOND -> {
                Color[] swappableStudents = (Color[]) characterCardEffectMap.get(GameValues.BARDSWAPPABLESTUDENTS);

                // Saves the characterCardEffectMap in this instance's supportMap in order to use it after the interaction with the user
                // and saves the Virtual Controller state
                savedReceivedMap = new InfoMap(characterCardEffectMap);
                this.vcState     = VCStates.REQ_BARD_SECOND_STUDENT;

                // Asks the player which available students from the Entrance he wants to swap with a DiningRoomStudent
                view.chooseStudentFromEntrance(swappableStudents);
            }

            case PRINCESSFIRST -> {
                // Gets from the message the movableStudents present on the CharacterCard and the characterCardPosition
                Color[] movableCCStudents  = (Color[]) characterCardEffectMap.get(GameValues.CARDSTUDENTS);
                int characterCardPosition  = (int)     characterCardEffectMap.get(GameValues.CHARACTERCARDPOSITION);
                int numOfAvailableStudents = movableCCStudents.length;

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_PRINCESS_FIRST;

                // Asks the player to select the students he wants to move from the CharacterCard
                view.chooseStudentFromCharacterCard(characterCardPosition, movableCCStudents, numOfAvailableStudents);
            }

            case THIEFFIRST -> {
                // Gets from the message the color that can be selected by the player using the characterCard 'THIEF'
                Color[] reducibleStudents = (Color[]) characterCardEffectMap.get(GameValues.REDUCIBLECOLOR);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_THIEF_FIRST;

                // Asks the player which color he wants to select in order to remove/reduce students of
                // the same color from the players diningRoomTables
                view.requestChooseColor(reducibleStudents);
            }
        }
    }

    public synchronized void messageAfterUserInteraction (Object infoToSend) {
        switch(this.vcState) {
            case REQ_USER_AGE                   -> {
                UsernameAndMagicAge                     usernameAndAgeInsertion     = (UsernameAndMagicAge) infoToSend;
                Tuple<GameActions, UsernameAndMagicAge> usernameAndMagicAgeResponse = new Tuple<>(GameActions.INSERTEDUSERNAMEANDAGE, usernameAndAgeInsertion);
                sendMessage(new Message(MessageType.RESPONSEACTION, usernameAndMagicAgeResponse));

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_RULES                      -> {
                // Saves the record GameRules, containing the rules he would like to play with in a tuple
                GameRules                     rulesChoice      = (GameRules) infoToSend;
                Tuple<GameActions, GameRules> askRulesResponse = new Tuple<>(GameActions.CHOSENRULES, rulesChoice);

                // Sends the player's choice to the server, via message
                sendMessage(new Message(MessageType.RESPONSEACTION, askRulesResponse));

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_WIZARD                     -> {
                // Saves in a Tuple(GameActions, Wizard) which wizard the player wants to play
                Wizard                     wizardChoice      = (Wizard) infoToSend;
                Tuple<GameActions, Wizard> askWizardResponse = new Tuple<>(GameActions.CHOSENWIZARD, wizardChoice);

                // Send the player's choice to the server, via message
                sendMessage(new Message(MessageType.RESPONSEACTION, askWizardResponse));

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_LOAD                       -> {
                // Saves the player specific decision to reload a game or not, in a Tuple
                Boolean                     loadGameChoice   = (Boolean) infoToSend;
                Tuple<GameActions, Boolean> loadGameResponse = new Tuple<>(GameActions.LOADGAMECHOICE, loadGameChoice);

                // Send the player's choice to the server, via message
                sendMessage(new Message(MessageType.RESPONSEACTION, loadGameResponse));

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_PLAY_ASS                   -> {
                // Saves the AssistantCard the player played in a Tuple
                AssistantCard                     chosenAssistantCard   = (AssistantCard) infoToSend;
                Tuple<GameActions, AssistantCard> playAssistantResponse = new Tuple<>(GameActions.CHOSENASSISTANTCARD, chosenAssistantCard);

                // Send the player's choice to the server, via message
                sendMessage(new Message(MessageType.RESPONSEACTION, playAssistantResponse));

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_MOVE_STUD_FIRST            -> {
                // Gets the index of the student played by the player
                int selectedStudentIndex = (int) infoToSend;

                // Continues the method, using the savedReceivedMap as parameter, in order to access
                // to other fields sent beforehand by the Server, necessary for the student movement
                onlyMoveStudent(savedReceivedMap, selectedStudentIndex);
            }

            case REQ_MOVE_STUD_SECOND           -> {
                // Saves in a Tuple the MoveStudentInfo (where does the player wants to move the student, diningRoom or Island (and its number))
                MoveStudentInfo                     moveStudentInfo      = (MoveStudentInfo) infoToSend;
                Tuple<GameActions, MoveStudentInfo> moveStudentResponse  = new Tuple<>(GameActions.MOVESTUDENTINFO, moveStudentInfo);

                // Send the player's movement to the server, via a ResponseAction, labeled with a GameAction "MoveStudentInfo"
                sendMessage(new Message(MessageType.RESPONSEACTION, moveStudentResponse));

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_MOVE_MN                    -> {
                // Saves in which Island the player wanted to move MotherNature
                int selectedMovement = (int) infoToSend;
                onlyMoveMotherNature(selectedMovement);

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_CHOOSE_CLOUD               -> {
                // Saves the CloudTile the player selected between the provided ones
                CloudTile selectedCloud = (CloudTile) infoToSend;
                onlyChooseCloud(selectedCloud);

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_MOVE_STUD_OR_PLAY          -> {
                // Gets the item selected by the player: it could be a studentIndex or a characterCard
                Object selectedItem = infoToSend;

                // If the player decided to move a student recall the normal-moveStudent method
                if (selectedItem instanceof Integer selectedStudentIndex)
                    onlyMoveStudent(savedReceivedMap, selectedStudentIndex);

                // If the player decided to play a CharacterCard, sends the chosen characterCard to the controller
                if (selectedItem instanceof CharacterCard selectedCharacterCard) {
                    sendCharacterCardChoice(selectedCharacterCard);

                    // Resets the vcState
                    this.vcState = null;
                }
            }

            case REQ_MOVE_MN_OR_PLAY            -> {
                // Gets the item selected by the player: it could be a studentIndex or a characterCard
                Object selectedItem = infoToSend;

                // If the player decided to move motherNature recall the normal-moveMotherNature method
                if (selectedItem instanceof Integer selectedIslandIndex)
                    onlyMoveMotherNature(selectedIslandIndex);

                // If the player decided to play a CharacterCard, sends the chosen characterCard to the controller
                if (selectedItem instanceof CharacterCard selectedCharacterCard)
                    sendCharacterCardChoice(selectedCharacterCard);

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_CHOOSE_CLOUD_OR_PLAY       -> {
                // Lets the player select a CharacterCard or the CloudTile from which he wants to draw the students
                Object selectedItem = infoToSend;

                // If the player choose a CloudTile recall the normal-chooseCloudTile method
                if (selectedItem instanceof CloudTile selectedCloud)
                    onlyChooseCloud(selectedCloud);

                // If the player decided to play a CharacterCard, sends the chosen characterCard to the controller
                if (selectedItem instanceof CharacterCard selectedCharacterCard)
                    sendCharacterCardChoice(selectedCharacterCard);

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_END_TURN_ASK               -> {
                Boolean endMyTurn = (Boolean) infoToSend;

                // If the player wants to end his own turn
                if (endMyTurn) {
                    Tuple<GameActions, UsernameAndMagicAge> endTurnResponse = new Tuple<>(GameActions.ENDTHISTURN, null);
                    sendMessage(new Message(MessageType.RESPONSEACTION, endTurnResponse));

                    // Resets the vcState
                    this.vcState = null;
                }

                // If the player wants to play a CharacterCard
                else {
                    // Gets from the Map the playable CharacterCards
                    CharacterCard[] playableCharacterCards = (CharacterCard[]) savedReceivedMap.get(GameValues.CHARACTERCARDARRAY);

                    // Saves the Virtual Controller state
                    this.vcState = VCStates.REQ_END_TURN_PLAY;

                    // Asks the player which characterCards he wants to play
                    view.requestPlayCharacterCard(playableCharacterCards);
                }
            }

            case REQ_END_TURN_PLAY              -> {
                // Saves which characterCards has been played by the player
                CharacterCard selectedCharacterCard = (CharacterCard) infoToSend;

                //Then sends the chosen characterCard to the controller
                sendCharacterCardChoice(selectedCharacterCard);

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_MONK_FIRST__STUDENT        -> {
                // Gets the student that the player wants to move from the characterCard
                int selectedStudentIndex = (int) infoToSend;

                // Saves the user choice in the savedToSendMap
                savedToSendMap = new InfoMap();
                savedToSendMap.put(GameValues.STUDENTINDEX, selectedStudentIndex);

                // Gets from the savedReceivedMap the available Island received beforehand from the server
                Island[] islands = (Island[]) savedReceivedMap.get(GameValues.ISLANDARRAY);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_MONK_FIRST__ISLAND;

                // Asks the player to select the Island where he wants to move the selectedStudent
                view.requestChooseIsland(islands);
            }

            case REQ_MONK_FIRST__ISLAND         -> {
                // Gets the index of the Island where the player wants to move the student from the characterCard
                int selectedIslandIndex = (int) infoToSend;
                savedToSendMap.put(GameValues.ISLANDINDEX, selectedIslandIndex);

                // Returns the selectedStudentIndex and the selectedIslandIndex
                Tuple<GameActions, InfoMap> playCharacterResponse = new Tuple<>(GameActions.CHOSENFIELDSMAP, savedToSendMap);
                sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));

                // Nullify the savedToSendMap, in order to prevent possible wrongly accesses by functions
                // that don't need this support-map
                savedToSendMap = null;

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_BEARER_FIRST_OR_HERB_FIRST -> {
               // Gets which is the islandIndex selected by the player to calculate the "influence" or to put
                // a noEntryTile on
                int selectedIslandIndex = (int) infoToSend;

                // Saves into a map the islandIndex
                InfoMap stdHerbMap = new InfoMap();
                stdHerbMap.put(GameValues.ISLANDINDEX, selectedIslandIndex);

                // Returns the selectedIslandIndex
                Tuple<GameActions, InfoMap> playCharacterResponse = new Tuple<>(GameActions.CHOSENFIELDSMAP, stdHerbMap);
                sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_JEST_FIRST                 -> {
                // Gets the number of students the player wanted to move using the characterCard 'Jester'
                int selectedNumOfMovements = (int) infoToSend;

                // Saves into a map the number correspondent to the chosen MovementJester
                InfoMap jesterFirstMap = new InfoMap();
                jesterFirstMap.put(GameValues.MOVEMENTJESTER, selectedNumOfMovements);

                // Returns the selected numOfMovements that will be done using the Jester effect
                Tuple<GameActions, InfoMap> playCharacterResponse = new Tuple<>(GameActions.CHOSENFIELDSMAP, jesterFirstMap);
                sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_JEST_SECOND__CARD          -> {
                // Gets the student that the player wants to move from the characterCard to the Entrance
                int characterCardStudentIndex = (int) infoToSend;

                // Saves the student selected by the player from the CharacterCard in the savedToSendMap
                savedToSendMap = new InfoMap();
                savedToSendMap.put(GameValues.CARDSTUDENTINDEX, characterCardStudentIndex);

                // Gets from the savedReceivedMap the movable entranceStudents received beforehand from the server
                Color[] entranceStudents = (Color[]) savedReceivedMap.get(GameValues.ENTRANCESTUDENTS);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_JEST_SECOND__ENTRANCE;

                // Asks the player to select the students he wants to move from the CharacterCard
                view.chooseStudentFromEntrance(entranceStudents);
            }

            case REQ_JEST_SECOND__ENTRANCE      -> {
                // Gets the index of the student selected by the player from the Entrance
                int entranceStudentIndex = (int) infoToSend;

                // Saves into a map the entranceStudentIndex of the selected Student
                savedToSendMap.put(GameValues.ENTRANCESTUDENTINDEX, entranceStudentIndex);

                // Returns the Map where has been saved the entranceStudentIndex and the characterCardStudentIndex
                Tuple<GameActions, InfoMap> playCharacterResponse = new Tuple<>(GameActions.CHOSENFIELDSMAP, savedToSendMap);
                sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));

                // Nullify the savedToSendMap, in order to prevent possible wrongly accesses by functions
                // that don't need this support-map
                savedToSendMap = null;

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_MERCH_FIRST                -> {
               // Gets the color selected by the player that won't be considered during the influence's calculus
                Color selectedColor = (Color) infoToSend;

                // Saves into a map the selectedColor in order to send it to the Controller
                InfoMap merchantMap = new InfoMap();
                merchantMap.put(GameValues.MERCHANTCOLOR, selectedColor);

                // Returns the Map containing the color selected by the students
                Tuple<GameActions, InfoMap> playCharacterResponse = new Tuple<>(GameActions.CHOSENFIELDSMAP, merchantMap);
                sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_BARD_FIRST                 -> {
                // Asks the player how many students he would like to move using the characterCard 'Bard'
                // Gets the number of students the player wanted to move using the characterCard 'Jester'
                int selectedNumOfMovements = (int) infoToSend;

                // Saves into a map the number correspondent to the chosen MovementBard
                InfoMap bardFirstMap = new InfoMap();
                bardFirstMap.put(GameValues.MOVEMENTBARD, selectedNumOfMovements);

                // Returns the selected numOfMovements that will be done using the Bard effect
                Tuple<GameActions, InfoMap> playCharacterResponse = new Tuple<>(GameActions.CHOSENFIELDSMAP, bardFirstMap);
                sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_BARD_SECOND_STUDENT        -> {
                // Asks the player which available students from the Entrance he wants to swap with a DiningRoomStudent
                int entranceStudentIndex = (int) infoToSend;

                // Saves into a map the entranceStudentIndex
                savedToSendMap = new InfoMap();
                savedToSendMap.put(GameValues.ENTRANCESTUDENTINDEX, entranceStudentIndex);

                // Gets the Entrance of the player (sent by the Server) and then find which is the color of
                // the student that has been selected by the player, according to its entranceIndex
                Entrance entranceToMoveFrom   = (Entrance) savedReceivedMap.get(GameValues.ENTRANCE);
                Color    selectedStudentColor = entranceToMoveFrom.getStudents()[entranceStudentIndex];

                // Gets the color of the compatible DiningRooms, according to the Info received via message from the Server
                Color[] compatibleDiningRoom = getCompatibleDiningRooms(savedReceivedMap, selectedStudentColor);

                // Saves the Virtual Controller state
                this.vcState = VCStates.REQ_BARD_SECOND_DINING;

                // Asks the player from which DiningRoomTable he wants to take the students to swap the entrance's student with
                view.requestChooseDiningRoom(compatibleDiningRoom);
            }

            case REQ_BARD_SECOND_DINING         -> {
                // Gets the color of the DiningRoomTable from which the player wants to take the student to swap with
                // the entrance's student selected previously
                Color selectedDiningRoomTable = (Color) infoToSend;

                // Saves into a map the color of the selected DiningRoomTable
                savedToSendMap.put(GameValues.DININGROOMTABLECOLOR, selectedDiningRoomTable);

                // Returns the Map where has been saved the entranceStudentIndex and the chosen DiningRoomTableColor
                Tuple<GameActions, InfoMap> playCharacterResponse = new Tuple<>(GameActions.CHOSENFIELDSMAP, savedToSendMap);
                sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));

                // Nullify the savedToSendMap, in order to prevent possible wrongly accesses by functions
                // that don't need this support-map
                savedToSendMap = null;

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_PRINCESS_FIRST             -> {
                // Gets the student selected by the player, chosen from the CharacterCard's students
                int selectedStudentIndex = (int) infoToSend;

                // Saves into a map the studentIndex of the selectedStudent from the characterCard's students
                InfoMap princessMap = new InfoMap();
                princessMap.put(GameValues.STUDENTINDEX, selectedStudentIndex);

                // Returns the Map containing the selectedStudentIndex
                Tuple<GameActions, InfoMap> playCharacterResponse = new Tuple<>(GameActions.CHOSENFIELDSMAP, princessMap);
                sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));

                // Nullify the savedToSendMap, in order to prevent possible wrongly accesses by functions
                // that don't need this support-map
                savedToSendMap = null;

                // Resets the vcState
                this.vcState = null;
            }

            case REQ_THIEF_FIRST                -> {
                // Gets the color that has been selected by the player in order to remove/reduce the students
                // of the same color present in the players' diningRooms
                Color selectedColor = (Color) infoToSend;

                // Saves into a map the selectedColor
                InfoMap thiefMap = new InfoMap();
                thiefMap.put(GameValues.REDUCECOLOR, selectedColor);

                // Returns the Map containing the selectedColor
                Tuple<GameActions, InfoMap> playCharacterResponse = new Tuple<>(GameActions.CHOSENFIELDSMAP, thiefMap);
                sendMessage(new Message(MessageType.RESPONSEACTION, playCharacterResponse));

                // Nullify the savedToSendMap, in order to prevent possible wrongly accesses by functions
                // that don't need this support-map
                savedToSendMap = null;

                // Resets the vcState
                this.vcState = null;
            }
        }

        // Resets the vcState and the savedReceivedMap after having sent the message
        this.savedReceivedMap = null;
    }

    /**
     * Gets the username of the local player (client)
     * @return the username of the local player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the local player (client
     * @param username the username to be set
     */
    public void setUsername (String username) {
        this.username = username;
    }
}
