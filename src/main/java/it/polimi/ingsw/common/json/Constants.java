package it.polimi.ingsw.common.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.common.GameActions;
import it.polimi.ingsw.common.GameValues;
import it.polimi.ingsw.common.json.adapters.*;
import it.polimi.ingsw.common.message.InfoMap;
import it.polimi.ingsw.common.message.Message;
import it.polimi.ingsw.common.model.*;
import it.polimi.ingsw.common.utils.Tuple;
import it.polimi.ingsw.common.viewRecord.GameRules;
import it.polimi.ingsw.common.viewRecord.MoveStudentInfo;
import it.polimi.ingsw.common.viewRecord.UsernameAndMagicAge;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.save.SaveData;

/**
 * Useful constants for both client and server
 * @author Mattia Martelli
 */
public /*static*/ final class Constants {

    // Hid the constructor to simulate the class being static
    private Constants() {}

    /**
     * JSON transformer
     */
    public static final Gson jsonBuilder = new GsonBuilder()
        // region Custom type adapters
        .registerTypeAdapter(AssistantCard.class,        new AssistantCardJSONAdapter())
        .registerTypeAdapter(AssistantCard[].class,      new AssistantCardArrayJSONAdapter())
        .registerTypeAdapter(Bag.class,                  new BagJSONAdapter())
        .registerTypeAdapter(CharacterCard.class,        new CharacterCardJSONAdapter())
        .registerTypeAdapter(CharacterCardNoEntry.class, new CharacterCardNoEntryJSONAdapter())
        .registerTypeAdapter(CharacterCardStudent.class, new CharacterCardStudentJSONAdapter())
        .registerTypeAdapter(CharacterCard[].class,      new CharacterCardArrayJSONAdapter())
        .registerTypeAdapter(CloudTile[].class,          new CloudTileArrayJSONAdapter())
        .registerTypeAdapter(Color.class,                new ColorJSONAdapter())
        .registerTypeAdapter(Color[].class,              new ColorArrayJSONAdapter())
        .registerTypeAdapter(ControllerData.class,       new ControllerDataJSONAdapter())
        .registerTypeAdapter(DiningRoom.class,           new DiningRoomJSONAdapter())
        .registerTypeAdapter(Entrance.class,             new EntranceJSONAdapter())
        .registerTypeAdapter(GameActions.class,          new GameActionsJSONAdapter())
        .registerTypeAdapter(GameModel.class,            new GameModelJSONAdapter())
        .registerTypeAdapter(GameRules.class,            new GameRulesJSONAdapter())
        .registerTypeAdapter(GameValues.class,           new GameValuesJSONAdapter())
        .registerTypeAdapter(GlobalProfessorTable.class, new GlobalProfessorTableJSONAdapter())
        .registerTypeAdapter(InfoMap.class,              new InfoMapJSONAdapter())
        .registerTypeAdapter(Island[].class,             new IslandArrayJSONAdapter())
        .registerTypeAdapter(Message.class,              new MessageJSONAdapter())
        .registerTypeAdapter(MoveStudentInfo.class,      new MoveStudentInfoJSONAdapter())
        .registerTypeAdapter(Player.class,               new PlayerJSONAdapter())
        .registerTypeAdapter(PlayerExpert.class,         new PlayerExpertJSONAdapter())
        .registerTypeAdapter(PlayerTeam.class,           new PlayerTeamJSONAdapter())
        .registerTypeAdapter(PlayerTeamExpert.class,     new PlayerTeamExpertJSONAdapter())
        .registerTypeAdapter(Player[].class,             new PlayerArrayJSONAdapter())
        .registerTypeAdapter(SaveData.class,             new SaveDataJSONAdapter())
        .registerTypeAdapter(SchoolBoard.class,          new SchoolBoardJSONAdapter())
        .registerTypeAdapter(TowerColor.class,           new TowerColorJSONAdapter())
        .registerTypeAdapter(Tuple.class,                new TupleJSONAdapter())
        .registerTypeAdapter(UsernameAndMagicAge.class,  new UsernameAndMagicAgeJSONAdapter())
        .registerTypeAdapter(Wizard.class,               new WizardJSONAdapter())
        .registerTypeAdapter(Wizard[].class,             new WizardArrayJSONAdapter())
        // endregion
        .setPrettyPrinting()
        .create();
}
