package it.polimi.ingsw.common.termutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing useful CLI constants
 * @author Mattia Martelli
 */
public /*static*/ final class Constants {

    // Hid the constructor to emulate a static class
    private Constants() {}

    // region Logos

    /**
     * Eriantys logo in list form
     */
    public static final List<String> logoList = List.copyOf(mkLogoList()); // NB: copyOf is used to make the list immutable

    /**
     * Eriantys logo in string form
     */
    public static final String logoBlock =
        """
            oooooooooooo           o8o                            .                       \s
            `888'     `8           `"'                          .o8                       \s
             888         oooo d8b oooo   .oooo.   ooo. .oo.   .o888oo oooo    ooo  .oooo.o\s
             888oooo8    `888""8P `888  `P  )88b  `888P"Y88b    888    `88.  .8'  d88(  "8\s
             888    "     888      888   .oP"888   888   888    888     `88..8'   `"Y88b. \s
             888       o  888      888  d8(  888   888   888    888 .    `888'    o.  )88b\s
            o888ooooood8 d888b    o888o `Y888""8o o888o o888o   "888"     .8'     8""888P'\s
                                                                      .o..P'              \s
                                                                      `Y8P'               \s
            """;

    /**
     * Used to initialize the list
     */
    private static List<String> mkLogoList() {
        List<String> logo = new ArrayList<>();

        logo.add("oooooooooooo           o8o                            .                        ");
        logo.add("`888'     `8           `\"'                          .o8                        ");
        logo.add(" 888         oooo d8b oooo   .oooo.   ooo. .oo.   .o888oo oooo    ooo  .oooo.o");
        logo.add(" 888oooo8    `888\"\"8P `888  `P  )88b  `888P\"Y88b    888    `88.  .8'  d88(  \"8 ");
        logo.add(" 888    \"     888      888   .oP\"888   888   888    888     `88..8'   `\"Y88b.  ");
        logo.add(" 888       o  888      888  d8(  888   888   888    888 .    `888'    o.  )88b ");
        logo.add("o888ooooood8 d888b    o888o `Y888\"\"8o o888o o888o   \"888\"     .8'     8\"\"888P' ");
        logo.add("                                                          .o..P'               ");
        logo.add("                                                          `Y8P'                ");

        return logo;
    }

    // endregion
}
