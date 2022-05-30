
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameTheme {

    /*

    About GameTheme:
    This utility class contains only static variables that represent colors and borders used multiple times throughout
    the game.

     */

    // list of colors used multiple times in game
    public static final Color CORRECT_GREEN = new Color(0x538d4e);
    public static final Color PRESENT_YELLOW = new Color(0xb59f3b);
    public static final Color BACKGROUND = new Color(0x121213);
    public static final Color ABSENT_GREY = new Color(0x3a3a3c);
    public static final Color LIGHT_GREY = new Color(0x565758);
    public static final Color WHITE = new Color(0xFFFFFF);

    // list of borders used multiple times in game
    private static final int BORDER_THICKNESS = 2;
    public static final Border GREEN_BORDER = BorderFactory.createLineBorder(CORRECT_GREEN, BORDER_THICKNESS);
    public static final Border YELLOW_BORDER = BorderFactory.createLineBorder(PRESENT_YELLOW, BORDER_THICKNESS);
    public static final Border DEFAULT_BORDER = BorderFactory.createLineBorder(ABSENT_GREY, BORDER_THICKNESS);
    public static final Border LIGHT_GREY_BORDER = BorderFactory.createLineBorder(LIGHT_GREY, BORDER_THICKNESS);

}
