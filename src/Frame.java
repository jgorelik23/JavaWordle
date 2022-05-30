
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    /*

    About Frame:
    This class is the main class that contains the main JFrame and all the elements within it.

     */

    private LetterBox[][] boxes;
    private final JPanel grid;

    private final static int WIDTH = 500;

    public Frame() {
        setTitle("WORDLE");

        // creates all containers
        Container mainContainer = getContentPane();
        JPanel gridContainer = new JPanel();
        JPanel buttonsContainer = new JPanel();

        // sets size of main frame
        setSize(WIDTH, 900);

        // sets background size and background colors of all containers
        gridContainer.setBackground(GameTheme.BACKGROUND);
        buttonsContainer.setBackground(GameTheme.BACKGROUND);
        buttonsContainer.setPreferredSize(new Dimension(WIDTH, 100));

        // creates and styles grid with boxes for letter
        grid = new JPanel();
        grid.setLayout(new GridLayout(6, 5, 5, 5));
        grid.setPreferredSize(new Dimension(WIDTH - 150, 400));
        grid.setBackground(GameTheme.BACKGROUND);

        // adds letter boxes to grid
        createBoxes();

        // creates new game/reset button and statsBtn
        JButton playBtn = createButton("RESET");
        JButton statsBtn = createButton("STATS");

        Game game = new Game(boxes, playBtn);

        // attaches action listener to play button that will call reset method in Game class
        playBtn.addActionListener(e -> game.reset());

        // attaches action listener to stats button that will create an instance of the Stats class
        statsBtn.addActionListener(e -> new Stats());

        // listens for presses in the JFrame, with the Game class implementing the method for the keypress event
        addKeyListener(game);

        // lays out all containers and their sub-containers
        // title label at top, grid container at center, buttons container at bottom
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(createTitle("WORDLE", 36), BorderLayout.NORTH);
        mainContainer.add(gridContainer, BorderLayout.CENTER);
        mainContainer.add(buttonsContainer, BorderLayout.SOUTH);
        gridContainer.add(grid);

        // adds buttons to button container
        buttonsContainer.add(playBtn);
        buttonsContainer.add(statsBtn);

        // boilerplate frame configuration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public static JLabel createTitle(String text, int fontSize) {
        // creates title label with text
        JLabel titleLabel = new JLabel(text);

        // sets size of JLabel
        titleLabel.setPreferredSize(new Dimension(WIDTH, 60));

        // centers title within container horizontally and vertically
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);

        // text styling
        titleLabel.setOpaque(true);
        titleLabel.setForeground(GameTheme.WHITE);
        titleLabel.setBackground(GameTheme.BACKGROUND);
        titleLabel.setFont(new Font("Arial", Font.BOLD, fontSize));

        return titleLabel;
    }

    public static JButton createButton(String btnText) {
        // creates new JButton
        JButton btn = new JButton(btnText);

        // styles button
        btn.setOpaque(true);
        btn.setForeground(GameTheme.WHITE);
        btn.setBackground(GameTheme.LIGHT_GREY);
        btn.setBorderPainted(false);
        btn.setFocusable(false);
        btn.setFont(new Font("Arial", Font.BOLD, 16));

        return btn;
    }

    public void createBoxes() {
        // initializes 2d array for letter boxes
        boxes = new LetterBox[6][5];

        // populates 2d array and adds boxes to the grid container
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                LetterBox letterBox = new LetterBox();
                boxes[r][c] = letterBox;
                grid.add(letterBox.getBox());
            }
        }
    }

    public static void main(String[] args) {
        new Frame();
    }
}
