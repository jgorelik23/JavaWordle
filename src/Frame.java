
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private LetterBox[][] boxes;
    private final JPanel grid;

    private final static int WIDTH = 500;

    public Frame() {
        setTitle("WORDLE");

        // creates all containers
        Container mainContainer = getContentPane();
        JPanel gridContainer = new JPanel();
        JPanel optionsContainer = new JPanel();

        // sets size of main frame
        setSize(WIDTH, 900);

        // sets background size and background colors of all containers
        gridContainer.setBackground(GameTheme.BACKGROUND); // TODO: add message label inside of gridContainer below grid JPanel

        optionsContainer.setBackground(GameTheme.BACKGROUND);
        optionsContainer.setPreferredSize(new Dimension(WIDTH, 100));

        // creates grid with boxes for letter
        grid = new JPanel();
        grid.setLayout(new GridLayout(6, 5, 5, 5));

        grid.setPreferredSize(new Dimension(WIDTH - 150, 400));
        // grid.setMaximumSize(new Dimension(WIDTH - 150, 400)); // !!! ? del

        grid.setBackground(GameTheme.BACKGROUND);
        createBoxes();

        JButton playBtn = createButton("RESET");
        JButton scoresBtn = createButton("STATS");

        Game game = new Game(boxes, playBtn);
        // Scores scores = new Scores();

        // attaches action listener to play button that will call reset method in Game class
        playBtn.addActionListener(e -> game.reset());
        scoresBtn.addActionListener(e -> new Stats());

        // listens for presses in the JFrame, with the Game class implementing the method for the keypress event
        addKeyListener(game);

        // lays out all containers and their sub-containers
        // title label at top, grid container at center, options container at bottom
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(createTitle("WORDLE", 36), BorderLayout.NORTH);
        mainContainer.add(gridContainer, BorderLayout.CENTER);
        mainContainer.add(optionsContainer, BorderLayout.SOUTH);
        gridContainer.add(grid);
        // optionsContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        optionsContainer.add(playBtn);
        optionsContainer.add(scoresBtn);

        // boilerplate frame configuration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // TODO: set to true and set min screen height/width
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
        JButton btn = new JButton(btnText);

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