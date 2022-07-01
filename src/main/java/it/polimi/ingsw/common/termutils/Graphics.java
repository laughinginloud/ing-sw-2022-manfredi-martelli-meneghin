package it.polimi.ingsw.common.termutils;

import it.polimi.ingsw.common.model.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static it.polimi.ingsw.common.termutils.Ansi.*;
import static it.polimi.ingsw.common.termutils.Ansi.Direction.*;

/**
 * Class containing useful CLI constants
 * @author Mattia Martelli
 */
@SuppressWarnings("unused")
public /*static*/ final class Graphics {

    // Hid the constructor to emulate a static class
    private Graphics() {}

    // region Logos

    /**
     * Eriantys logo in immutable list form
     */
    public static final List<String> logoList = List.copyOf(mkLogoList());

    /**
     * Eriantys logo in string form
     */
    public static final String logoBlock =
        """
            oooooooooooo           o8o                           .                      \s
            `888'     `8           `"'                         .o8                      \s
             888         oooo d8b oooo   .oooo.   ooo. .oo.  .o888oo oooo    ooo .oooo.o\s
             888oooo8    `888""8P `888  `P  )88b  `888P"Y88b   888    `88.  .8' d88(  "8\s
             888    "     888      888   .oP"888   888   888   888     `88..8'  `"Y88b. \s
             888       o  888      888  d8(  888   888   888   888 .    `888'   o.  )88b\s
            o888ooooood8 d888b    o888o `Y888""8o o888o o888o  "888"     .8'    8""888P'\s
                                                                     .o..P'             \s
                                                                     `Y8P'              \s
            """;

    /**
     * Used to initialize the list
     */
    private static List<String> mkLogoList() {
        List<String> logo = new ArrayList<>();

        logo.add("oooooooooooo           o8o                           .");
        logo.add("`888'     `8           `\"'                         .o8");
        logo.add(" 888         oooo d8b oooo   .oooo.   ooo. .oo.  .o888oo oooo    ooo .oooo.o");
        logo.add(" 888oooo8    `888\"\"8P `888  `P  )88b  `888P\"Y88b   888    `88.  .8' d88(  \"8");
        logo.add(" 888    \"     888      888   .oP\"888   888   888   888     `88..8'  `\"Y88b.");
        logo.add(" 888       o  888      888  d8(  888   888   888   888 .    `888'   o.  )88b");
        logo.add("o888ooooood8 d888b    o888o `Y888\"\"8o o888o o888o  \"888\"     .8'    8\"\"888P'");
        logo.add("                                                         .o..P'");
        logo.add("                                                         `Y8P'");

        return logo;
    }

    // endregion

    // region Island

    /**
     * Draw an island
     * @param writer       The writer to draw on
     * @param island       The island to draw
     * @param motherNature Whether mother nature is present on the current island
     */
    public static void drawIsland(PrintWriter writer, Island island, boolean motherNature) {
        List<String> islandList = buildIsland(island, motherNature);

        islandList.forEach(s -> {
            writer.print(s);
            moveCursor(writer, DOWN, 1);
            moveCursor(writer, LEFT, 19);
        });
    }

    /**
     * Build an island to be drawn
     * @param island       The island to draw
     * @param motherNature Whether mother nature is present on the current island
     * @return The populated island
     */
    private static List<String> buildIsland(Island island, boolean motherNature) {
        List<String> islandList = new ArrayList<>();

        islandList.add("    ___________    ");
        islandList.add("   /           \\   ");
        islandList.add("  /      %s      \\  " .formatted(formatTower(island)));
        islandList.add(" /  %s         %s  \\ ".formatted(formatStudent(island, Color.RED), formatStudent(island, Color.YELLOW)));
        islandList.add("/        %s        \\" .formatted(formatStudent(island, Color.GREEN)));
        islandList.add("\\   %s         %s   /".formatted(formatStudent(island, Color.PINK), formatStudent(island, Color.BLUE)));
        islandList.add(" \\               / ");
        islandList.add("  \\      %s      /  " .formatted(formatMotherNature(motherNature)));
        islandList.add("   \\___________/   ");

        return islandList;
    }

    /**
     * Format a specific student
     * @param island  The island to be populated
     * @param student The student to format
     * @return The formatted student
     */
    private static String formatStudent(Island island, Color student) {
        int stdCnt = island.getStudentCounters(student);

        return stdCnt == 0 ?
            " " :
            colorString(stdCnt == 1 ?
                " " :
                String.valueOf(stdCnt), getStudentColor(student, true), BLACK) + (stdCnt >= 10 ? "\b " : "");
    }

    /**
     * Format a specific tower
     * @param island The island to be populated
     * @return The formatted tower
     */
    private static String formatTower(Island island) {
        if (island.getTowerColor() == null)
            return " ";

        int twrCnt = island.getMultiplicity();

        return colorString(twrCnt == 1 ?
            " " :
            String.valueOf(twrCnt), getTowerColor(island.getTowerColor(), true));
    }

    /**
     * Format mother nature
     * @param motherNature Whether mother nature is present on the current island
     * @return The formatted pawn
     */
    private static String formatMotherNature(boolean motherNature) {
        return motherNature ? colorString(" ", Ansi.BACKGROUND_YELLOW) : " ";
    }

    // endregion

    // region Table

    /**
     * Draw a school board table
     * @param writer      The writer to write to
     * @param schoolBoard The school board to draw
     * @param model       The GameModel instance
     * @param curplayer   The current player
     */
    public static void drawTable(PrintWriter writer, SchoolBoard schoolBoard, GameModel model, Player curplayer) {
        List<String> table = buildTable(schoolBoard, model, curplayer);

        table.forEach(s -> {
            writer.print(s);
            writer.print(moveCursor(DOWN, 1));
            writer.print(moveCursor(LEFT, 51));
        });
    }

    /**
     * Build a school board
     * @param schoolBoard The school board to build
     * @param model       The GameModel instance
     * @param curPLayer   The current player
     * @return The built school board
     */
    private static List<String> buildTable(SchoolBoard schoolBoard, GameModel model, Player curPLayer) {
        Color[] ent = schoolBoard.getEntrance().getStudents();

        List<String> table = new ArrayList<>();
        table.add(" _________________________________________________ ");
        table.add("|        |                         |     |        |");
        table.add("|     %s  |   %s  |  %s  |  %s  %s  |" .formatted(entranceStudent(ent, 0), buildDining(schoolBoard.getDiningRoom(), Color.GREEN), formatProfessor(Color.GREEN, model, curPLayer), schoolTower(schoolBoard, 0), schoolTower(schoolBoard, 1)));
        table.add("|  %s  %s  |   %s  |  %s  |  %s  %s  |".formatted(entranceStudent(ent, 1), entranceStudent(ent, 2), buildDining(schoolBoard.getDiningRoom(), Color.RED), formatProfessor(Color.RED, model, curPLayer), schoolTower(schoolBoard, 2), schoolTower(schoolBoard, 3)));
        table.add("|  %s  %s  |   %s  |  %s  |  %s  %s  |".formatted(entranceStudent(ent, 3), entranceStudent(ent, 4), buildDining(schoolBoard.getDiningRoom(), Color.YELLOW), formatProfessor(Color.YELLOW, model, curPLayer), schoolTower(schoolBoard, 4), schoolTower(schoolBoard, 5)));
        table.add("|  %s  %s  |   %s  |  %s  |  %s  %s  |".formatted(entranceStudent(ent, 5), entranceStudent(ent, 6), buildDining(schoolBoard.getDiningRoom(), Color.PINK), formatProfessor(Color.PINK, model, curPLayer), schoolTower(schoolBoard, 6), schoolTower(schoolBoard, 7)));
        table.add("|  %s  %s  |   %s  |  %s  |        |".formatted(entranceStudent(ent, 7), entranceStudent(ent, 8), buildDining(schoolBoard.getDiningRoom(), Color.BLUE), formatProfessor(Color.BLUE, model, curPLayer)));
        table.add("|________|_________________________|_____|________|");

        return table;
    }

    /**
     * Format an entrance's student
     * @param students The students from the entrance
     * @param index    The index of the student
     * @return The formatted student
     */
    private static String entranceStudent(Color[] students, int index) {
        return students.length > index && students[index] != null ? colorString(" ", getStudentColor(students[index], true)) : " ";
    }

    /**
     * Format a dining room's lane
     * @param diningRoom The dining room to format
     * @param color      The color from the dining room to format
     * @return The formatted lane
     */
    private static String buildDining(DiningRoom diningRoom, Color color) {
        int count = diningRoom.getStudentCounters(color);

        return (colorString(" ", getStudentColor(color, true)) + " ").repeat(count) +
            "  ".repeat(10 - count);
    }

    /**
     * Format the specified professor
     * @param color     The color of the professor
     * @param model     The GameModel instance
     * @param curPlayer The current player
     * @return The formatted professor
     */
    private static String formatProfessor(Color color, GameModel model, Player curPlayer) {
        Optional<Player> profLocation = model.getGlobalProfessorTable().getProfessorLocation(color);

        return profLocation.isEmpty() || profLocation.get().getPlayerID() != curPlayer.getPlayerID() ?
            " " :
            colorString(" ", getStudentColor(color, true));
    }

    /**
     * Format the specified tower
     * @param schoolBoard The school board
     * @param index       Theindex of the tower
     * @return The formatted tower
     */
    private static String schoolTower(SchoolBoard schoolBoard, int index) {
        return schoolBoard.getTowerCount() > index ? colorString(" ", getTowerColor(schoolBoard.getTowerColor(), true)) : " ";
    }

    // endregion

    // region Cloud

    /**
     * Draw a cloud
     * @param writer    The writer to write to
     * @param cloudTile The cloud tile to draw
     */
    public static void drawCloud(PrintWriter writer, CloudTile cloudTile) {
        List<String> cloud = buildCloud(cloudTile);

        cloud.forEach(s -> {
            writer.print(s);
            writer.print(Ansi.moveCursor(Ansi.Direction.DOWN, 1));
            writer.print(Ansi.moveCursor(Ansi.Direction.LEFT, 15));
        });
    }

    /**
     * Build the specified cloud tile
     * @param cloudTile The cloud tile to build
     * @return The built cloud tile
     */
    private static List<String> buildCloud(CloudTile cloudTile) {
        List<String> cloud = new ArrayList<>();

        cloud.add("  ~~~~~~~~~~~  ");
        cloud.add(" (           ) ");
        cloud.add("(    %s   %s    )".formatted(cloudStudent(cloudTile, 0), cloudStudent(cloudTile, 1)));
        cloud.add(" (           ) ");
        cloud.add("(    %s   %s    )".formatted(cloudStudent(cloudTile, 2), cloudStudent(cloudTile, 3)));
        cloud.add(" (           ) ");
        cloud.add("  ~~~~~~~~~~~  ");

        return cloud;
    }

    /**
     * Format a specified color counter
     * @param cloudTile The cloud tile containing the students
     * @param index     The x of the student to format
     * @return The formatted student
     */
    private static String cloudStudent(CloudTile cloudTile, int index) {
        return cloudTile.getStudents()[index] != null ? colorString(" ", getStudentColor(cloudTile.getStudents()[index], true)) : " ";
    }

    // endregion

}
