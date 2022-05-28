
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private LetterBox[][] boxes;
    private final JPanel grid;

    private final int WIDTH = 500;

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
        gridContainer.setPreferredSize(new Dimension(WIDTH - 150, 500));
        gridContainer.setMaximumSize(new Dimension(WIDTH - 150, 500));
        gridContainer.setLayout(new BorderLayout(10, 0));

        optionsContainer.setBackground(GameTheme.BACKGROUND);
        optionsContainer.setPreferredSize(new Dimension(WIDTH, 100));

        // creates grid with boxes for letter
        grid = new JPanel();
        grid.setLayout(new GridLayout(6, 5, 5, 5));

        grid.setPreferredSize(new Dimension(WIDTH - 150, 400));
        grid.setMaximumSize(new Dimension(WIDTH - 150, 400));

        grid.setBackground(GameTheme.BACKGROUND);
        createBoxes();

        // JLabel for messages
        JLabel alertLabel = new JLabel("test");
        alertLabel.setOpaque(true);
        alertLabel.setForeground(GameTheme.WHITE);
        alertLabel.setBackground(GameTheme.BACKGROUND);

        // listens for presses in the JFrame, with the Game class implementing the method for the keypress event
        addKeyListener(new Game(boxes));

        // lays out all containers and their sub-containers
        // title label at top, grid container at center, options container at bottom
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(createTitle(), BorderLayout.NORTH);
        mainContainer.add(gridContainer, BorderLayout.CENTER);
        mainContainer.add(optionsContainer, BorderLayout.SOUTH);
        gridContainer.add(grid, BorderLayout.NORTH);
        gridContainer.add(alertLabel, BorderLayout.SOUTH);

        // boilerplate frame configuration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // TODO: set to true and set min screen height/width
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public JLabel createTitle() {
        // creates title label with text
        JLabel titleLabel = new JLabel("WORDLE");

        // sets size of JLabel
        titleLabel.setPreferredSize(new Dimension(WIDTH, 80));

        // centers title within container horizontally and vertically
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);

        // text styling
        titleLabel.setOpaque(true);
        titleLabel.setForeground(GameTheme.WHITE);
        titleLabel.setBackground(GameTheme.BACKGROUND);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));

        return titleLabel;
    }

    public void createBoxes() {
        // initializes 2d array for letter boxes
        boxes = new LetterBox[6][5];

        // populates 2d array and adds boxes to the grid container
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                LetterBox letterBox = new LetterBox(r, c);
                boxes[r][c] = letterBox;
                grid.add(letterBox.getBox());
            }
        }
    }

    public static void main(String[] args) {
        new Frame();
    }
}