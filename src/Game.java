
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;

public class Game extends KeyAdapter {

    private final LetterBox[][] boxes;
    private final JButton playBtn;

    private int rowPosition;
    private int colPosition;
    private boolean gameOver;

    private String targetWord;

    public Game(LetterBox[][] boxes, JButton playBtn) {
        this.boxes = boxes;
        this.playBtn = playBtn;
        this.rowPosition = 0;
        this.colPosition = 0;
        this.gameOver = false;

        generateTargetWord();
    }

    private void generateTargetWord() {
        // generates random number which will represent the line that this games target word is on in targetWords.txt
        int randomLineNumber = (int) (Math.random() * 2315) + 1;

        try {
            // instantiates buffered reader to read from targetWords.txt - a database with all possible five-letter target words
            BufferedReader reader = new BufferedReader(new FileReader("targetWords.txt"));

            // gets this games target word based off of randomly generated line number
            for (int i = 1; i <= randomLineNumber; i++) {
                if (i == randomLineNumber) {
                    targetWord = reader.readLine();
                    System.out.println(targetWord); // ! DELETE
                    break;
                }
                reader.readLine();
            }

            reader.close();

            // System.out.println(thisGamesWord + " " + checkIfValidGuess(thisGamesWord));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // handles key presses
    public void keyPressed(KeyEvent e) {
        if (gameOver) return;

        if (e.getKeyCode() == 10) { // enter key
            if (colPosition != 5) {
                sendAlert("Invalid guess: not enough letters.");
            } else {
                String guessedWord = "";

                // takes letter from each box in current row and puts together the guessed word
                for (int i = 0; i < 5; i++) {
                    guessedWord += boxes[rowPosition][i].getLetter().toLowerCase(); // TODO: string builder / append?
                }

                // System.out.println(guessedWord);

                if (checkIfValidGuess(guessedWord)) { // TODO: write comments
                    highlightLetters(guessedWord);

                    if (guessedWord.equals(targetWord)) {
                        gameOver = true;

                        saveToLocalStorage(rowPosition);

                        playBtn.setText("NEW GAME");
                        sendAlert("You win!");
                    } else if (rowPosition == 5) {
                        gameOver = true;

                        saveToLocalStorage(-1);

                        playBtn.setText("NEW GAME");
                        sendAlert("You lost. Hit the \"NEW GAME\" button to play again!");
                    } else { // ? remove else
                        rowPosition++;
                        colPosition = 0;
                    }
                } else {
                    sendAlert("Invalid guess: not in word list.");
                }
            }
        } else if (e.getKeyCode() == 8 && colPosition > 0) { // delete key
            boxes[rowPosition][colPosition - 1].setLetter("");
            boxes[rowPosition][colPosition - 1].getBox().setBorder(GameTheme.DEFAULT_BORDER);

            colPosition--;
        } else if (e.getKeyCode() >= 65 && e.getKeyCode() <= 90 && colPosition != 5 && rowPosition != 6) { // letter press
            boxes[rowPosition][colPosition].setLetter(Character.toString(e.getKeyChar()).toUpperCase());
            boxes[rowPosition][colPosition].getBox().setBorder(GameTheme.LIGHT_GREY_BORDER);

            colPosition++;
        }
    }

    private static void sendAlert(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void highlightLetters(String guessedWord) {
        // creates array versions of target word and guessed word by splitting the string by each character
        String[] targetWordArr = targetWord.split("");
        String[] guessedWordArr = guessedWord.split("");

        System.out.println(guessedWord);

        // copy of target word, to be used in algorithm for checking if letter is correct, present, or absent
        String remainingLettersInWord = targetWord;

        for (int i = 0; i < 5; i++) {
            if (targetWordArr[i].equals(guessedWordArr[i])) { // checks for all letters in correct spot
                remainingLettersInWord = remainingLettersInWord.replaceFirst(guessedWordArr[i], "");
                boxes[rowPosition][i].setStatus(LetterStatus.correct);
            } else { // checks for all absent letters
                boxes[rowPosition][i].setStatus(LetterStatus.absent);
            }
        }

        // checks for all letters present in guess
        for (int i = 0; i < 5; i++) {
            if (remainingLettersInWord.contains(guessedWordArr[i]) && !(targetWordArr[i].equals(guessedWordArr[i]))) {
                remainingLettersInWord = remainingLettersInWord.replaceFirst(guessedWordArr[i], "");
                boxes[rowPosition][i].setStatus(LetterStatus.present);
            }
        }
    }

    private boolean checkIfValidGuess(String guessedWord) {
        try {
            // instantiates new buffered reader to read from dictionary.txt - a database with all valid five-letter words
            BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"));

            // checks if guessed word is in the database of all valid five-letter words
            for (int i = 1; i <= 12972; i++) { // REFACTOR TODO: change to while (hasNextLine);
                if (guessedWord.equals(reader.readLine())) {
                    return true;
                }
            }

            reader.close();

//            while (reader.readLine() != null) {
//                System.out.println(1);
//                if (guessedWord.equals(reader.readLine())) {
//                    return true;
//                }
//            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void reset() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                boxes[r][c].setDefaultStyles();
            }
        }

        playBtn.setText("RESET");

        generateTargetWord();

        colPosition = 0;
        rowPosition = 0;

        gameOver = false;
    }

    private void saveToLocalStorage(int score) {
        String pointsFromLocalStorage = LocalStorage.getItem(Integer.toString(score));

        if (pointsFromLocalStorage != null) {
            int points = Integer.parseInt(pointsFromLocalStorage);
            LocalStorage.setItem(Integer.toString(score), Integer.toString(points + 1));
        } else {
            LocalStorage.setItem(Integer.toString(score), Integer.toString(1));
        }
    }

}

// a = 65
// z = 90

// TODO: ? use interval to fade in backgrounds on boxes