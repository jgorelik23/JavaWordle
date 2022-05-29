
import javax.swing.*;
import java.awt.*;

public class LetterBox {
    private String letter;
    private LetterStatus status;
    private final JLabel box;

    public LetterBox() {
        this.letter = "";
        this.status = LetterStatus.none;
        box = new JLabel();

        // sets all box styles that will not be changed
        box.setOpaque(true);
        box.setForeground(GameTheme.WHITE);
        box.setPreferredSize(new Dimension(80, 70));
        box.setMaximumSize(new Dimension(80, 70));
        box.setHorizontalAlignment(SwingConstants.CENTER);
        box.setVerticalAlignment(SwingConstants.CENTER);
        box.setFont(new Font("Arial", Font.BOLD, 24));
        setDefaultStyles();
    }

    public void setDefaultStyles() {
        // sets all box styles that will later be changed
        box.setText("");
        box.setBackground(GameTheme.BACKGROUND);
        box.setBorder(GameTheme.DEFAULT_BORDER);
        box.setForeground(GameTheme.WHITE);
    }

    public String getLetter() {
        return this.letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;

        // updates letter in letter box
        this.box.setText(letter);
    }

    public LetterStatus getStatus() {
        return this.status;
    }

    public void setStatus(LetterStatus status) {
        this.status = status;

        // updates background color of letter based on if letter is correct, present, or absent
        switch (getStatus()) {
            case correct -> {
                getBox().setBackground(GameTheme.CORRECT_GREEN);
                getBox().setBorder(GameTheme.GREEN_BORDER);
            }
            case present -> {
                getBox().setBackground(GameTheme.PRESENT_YELLOW);
                getBox().setBorder(GameTheme.YELLOW_BORDER);
            }
            case absent -> {
                getBox().setBackground(GameTheme.ABSENT_GREY);
                getBox().setBorder(GameTheme.DEFAULT_BORDER);
            }
        }
    }

    public JLabel getBox() {
        return this.box;
    }

    @Override
    public String toString() { // TODO: ? del
        return "Letter: " + this.letter;
    }
}