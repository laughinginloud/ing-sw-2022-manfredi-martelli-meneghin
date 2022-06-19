package it.polimi.ingsw.server.controller.save;

import com.google.gson.Gson;
import it.polimi.ingsw.common.json.Constants;
import it.polimi.ingsw.server.controller.ControllerData;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.state.GameState;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class containing static methods to manage saved games
 * @author Mattia Martelli
 */
public class GameSave {
    private static final Gson saveBuilder = Constants.jsonBuilder;

    private static final File SAVE_FOLDER = new File("./saves/");

    public static void saveGame(String fileName, GameState state) throws IOException {
        SaveData data = new SaveData(ControllerData.getInstance(), state);
        Path     path = Paths.get(SAVE_FOLDER.getAbsolutePath(), fileName);

        try (final BufferedWriter bf = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING)) {
            bf.write(saveBuilder.toJson(data));
            bf.flush();
        }
    }

    public static void deleteGame(String fileName) {
        File save = new File(SAVE_FOLDER, fileName);

        save.delete();
    }

    public static void loadGame(File save) throws IOException {
        SaveData data = saveBuilder.fromJson(Files.readString(save.toPath()), SaveData.class);
        GameController.loadGameState(data.gameState());
    }

    public static Optional<File> findSavedGame(String username) {
        for (File savedGame : SAVE_FOLDER.listFiles())
            if (savedGame.getName().contains(username))
                return Optional.of(savedGame);

        return Optional.empty();
    }

    public static boolean checkFolderPermissions() {
        return SAVE_FOLDER.exists() ?
            SAVE_FOLDER.canRead() && SAVE_FOLDER.canWrite() :
            SAVE_FOLDER.mkdir();
    }
}
