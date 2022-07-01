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
import java.util.Objects;
import java.util.Optional;

/**
 * Class containing static methods to manage saved games
 * @author Mattia Martelli
 */
public final class GameSave {

    private GameSave(){}

    /**
     * Alias for the json serializer
     */
    private static final Gson saveBuilder = Constants.jsonBuilder;

    /**
     * The default saves folder path
     */
    private static final File SAVE_FOLDER = new File("./saves/");

    /**
     * Save the current game
     * @param fileName The name of the save
     * @param state    The DFA state the save will point at
     * @throws IOException If there is an error whilst accessing the file
     */
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

        // If there was a null pointer exception, just end gracefully
        catch (NullPointerException ignored) {}
    }

    /**
     * Delet the current save file
     * @param fileName The name of the save to delete
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteGame(String fileName) {
        File save = new File(SAVE_FOLDER, fileName);

        save.delete();
    }

    /**
     * Load the desired save
     * @param save The save to load
     * @throws IOException If the save cannot be loaded
     */
    public static void loadGame(File save) throws IOException {
        SaveData data = saveBuilder.fromJson(Files.readString(save.toPath()), SaveData.class);
        GameController.loadGameState(data.gameState());
    }

    /**
     * Find a save for the desired player
     * @param username The username for which to look for
     * @return An Optional containing a save, if it exists
     */
    public static Optional<File> findSavedGame(String username) {
        try {
            for (File savedGame : Objects.requireNonNull(SAVE_FOLDER.listFiles()))
                if (savedGame.getName().contains(username))
                    return Optional.of(savedGame);
        }

        catch (NullPointerException ignored) {
            return Optional.empty();
        }

        return Optional.empty();

    }

    /**
     * Check the read and write permissions
     * @return <code>true</code> if the program can read and write, <code>false</code> otherwise
     */
    public static boolean checkFolderPermissions() {
        return SAVE_FOLDER.exists() ?
            SAVE_FOLDER.canWrite() && SAVE_FOLDER.canRead():
            SAVE_FOLDER.mkdir()    && SAVE_FOLDER.canRead();
    }
}
