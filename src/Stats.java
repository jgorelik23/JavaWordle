
import javax.swing.*;
import java.awt.*;

public class Stats extends JFrame {

    private final Container statsContainer;
    private JLabel[] statsLabels;

    public Stats() {

        Container mainContainer = getContentPane();
        statsContainer = new Container();
        statsContainer.setLayout(new GridLayout(8, 1, 0, 5));
        statsContainer.setBackground(GameTheme.BACKGROUND); // TODO: ? opaque
        mainContainer.setBackground(GameTheme.BACKGROUND);

        // sets size of main frame
        mainContainer.setPreferredSize(new Dimension(300, 400));
        setSize(300, 400);

        createStatsLabels();

        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(Frame.createTitle("STATS", 30), BorderLayout.NORTH);
        mainContainer.add(statsContainer, BorderLayout.CENTER);

        // boilerplate frame configuration
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false); // TODO: set to true and set min screen height/width
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void createStatsLabels() {
        statsLabels = new JLabel[7];

        for (int i = -1; i <= 5; i++) {
            JLabel statsLabel = new JLabel();

            statsLabel.setOpaque(true);
            statsLabel.setForeground(GameTheme.WHITE);
            statsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            statsLabel.setHorizontalAlignment(JLabel.CENTER);
            statsLabel.setVerticalAlignment(JLabel.CENTER);
            statsLabel.setBackground(GameTheme.BACKGROUND);
            statsLabel.setForeground(GameTheme.WHITE);

            String score = LocalStorage.getItem(Integer.toString(i)) == null ? "none" : LocalStorage.getItem(Integer.toString(i));

            statsLabel.setText(getStatsLabelText(i, score));

            statsLabels[i + 1] = statsLabel;
            statsContainer.add(statsLabel);
        }

        JButton clearHistoryBtn = Frame.createButton("CLEAR STATS");
        clearHistoryBtn.addActionListener(e -> {
            LocalStorage.clear();
            int counter = -1;
            for (JLabel label : statsLabels) {
                label.setText(getStatsLabelText(counter, "none"));
                counter++;
            }
        });
        statsContainer.add(clearHistoryBtn);
    }

    private String getStatsLabelText(int index, String score) {
        switch (index) {
            case -1 -> {
                return "Fails: " + score;
            }
            case 0 -> {
                return (index + 1) + " guess: " + score;
            }
            default -> {
                return (index + 1) + " guesses: " + score;
            }
        }
    }

}
