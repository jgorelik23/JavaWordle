
import javax.swing.*;
import java.awt.*;

public class Stats extends JFrame {

    /*

    About Stats:
    This class is opened when the "STATS" button is hit on the main frame. It displays the player's history of game
    statistics and gives the user the option to clear the statistics.

     */

    private final Container statsContainer;
    private JLabel[] statsLabels;
    private final JButton clearBtn;

    public Stats() {
        setTitle("STATS");

        // creates main frame and styles it
        Container mainContainer = getContentPane();
        mainContainer.setBackground(GameTheme.BACKGROUND);
        mainContainer.setPreferredSize(new Dimension(300, 400));
        mainContainer.setLayout(new BorderLayout());

        // adds title to main frame
        mainContainer.add(Frame.createTitle("STATS", 30), BorderLayout.NORTH);

        setSize(300, 400);

        // creates stats container, styles it, and sets up layout manager
        statsContainer = new Container();
        statsContainer.setLayout(new GridLayout(8, 1, 0, 5));
        statsContainer.setBackground(GameTheme.BACKGROUND);

        // creates clear button and attaches action listener to it
        clearBtn = Frame.createButton("CLEAR STATS");
        clearBtn.addActionListener(e -> clearStats());

        // creates all labels and adds it the to the stats container
        createStatsLabels();

        // adds stats container to main frame
        mainContainer.add(statsContainer, BorderLayout.CENTER);

        // boilerplate frame configuration
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void createStatsLabels() {
        // array for all stats labels
        statsLabels = new JLabel[7];

        for (int i = -1; i <= 5; i++) {
            JLabel statsLabel = new JLabel();

            // styles label
            statsLabel.setOpaque(true);
            statsLabel.setForeground(GameTheme.WHITE);
            statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            statsLabel.setHorizontalAlignment(JLabel.CENTER);
            statsLabel.setVerticalAlignment(JLabel.CENTER);
            statsLabel.setBackground(GameTheme.BACKGROUND);
            statsLabel.setForeground(GameTheme.WHITE);

            // gets point from local storage
            String scoreFromLocalStorage = LocalStorage.getItem(Integer.toString(i));

            // if scoreFromLocalStorage is null, score is set to none, else, it is set to the value from local storage
            String score = scoreFromLocalStorage == null ? "none" : scoreFromLocalStorage;

            // formats text for JLabel
            statsLabel.setText(getStatsLabelText(i, score));

            // adds label to array
            statsLabels[i + 1] = statsLabel;

            // adds label to frame
            statsContainer.add(statsLabel);
        }

        // adds clear button to bottom of all labels
        statsContainer.add(clearBtn);
    }

    // method that is called when clear button is hit
    private void clearStats() {
        // empties localStorage.txt
        LocalStorage.clear();

        for (int i = -1; i < 6; i++) { // resets all JLabels to have a value of "none"
            statsLabels[i + 1].setText(getStatsLabelText(i, "none"));
        }
    }

    // formats the name of each stat label
    private String getStatsLabelText(int index, String score) {
        String labelText;

        switch (index) {
            // -1 represents a fail
            case -1 -> labelText = "Fails: " + score;

            // 0 represents that the game was won in one guess
            case 0 -> labelText = (index + 1) + " guess: " + score;

            // if not a fail or won in one guess, the default case gets executed
            default -> labelText = (index + 1) + " guesses: " + score;
        }

        return labelText;
    }

}
