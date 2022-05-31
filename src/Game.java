
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Game extends KeyAdapter {

    /*

    About Game:
    This class has all the logic for the actual game play. It handles key-press events, lets the user know if they won,
    lost, entered an invalid word, generates a random target word, etc.

     */

    private final LetterBox[][] boxes;
    private final JButton playBtn;

    private int rowPosition; // tracks which row the user is typing into, increments with each guess entry
    private int colPosition; // tracks which column the user is typing into, increments with each key-press

    private boolean gameOver;

    private String targetWord;

    public Game(LetterBox[][] boxes, JButton playBtn) {
        this.boxes = boxes;
        this.playBtn = playBtn;
        this.rowPosition = 0;
        this.colPosition = 0;
        this.gameOver = false;

        // sets this games target word
        generateTargetWord();
    }

    private void generateTargetWord() {
        // generates random number which will represent the line that this games target word is on in targetWords.txt
        int randomLineNumber = (int) (Math.random() * 2315) + 1; // 2315 is the amount of words/lines in targetWords.txt

        try {
            // instantiates buffered reader to read from targetWords.txt - a database with all possible five-letter target words
            BufferedReader reader = new BufferedReader(new FileReader("targetWords.txt"));

            // gets this games target word based off of randomly generated line number
            for (int i = 1; i <= randomLineNumber; i++) {
                if (i == randomLineNumber) {
                    targetWord = reader.readLine();
                    // System.out.println(targetWord); // UNCOMMENT THIS LINE TO HAVE ANSWER PRINTED AT START OF GAME
                    break;
                }
                reader.readLine();
            }

            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // handles key presses
    public void keyPressed(KeyEvent e) {
        // prevents method from running if the game is over, except if the enter key being hit which allows the game to be reset
        if (gameOver && e.getKeyCode() != 10) return;

        if (e.getKeyCode() == 10) { // enter key
            if (gameOver) { // resets game if game is over (alternative to pressing new game button)
                reset();
            } else if (colPosition != 5) { // if not enough letters in word, it will not allow guess submission
                sendAlert("Invalid guess: not enough letters.");
            } else {
                String guessedWord = "";

                // takes letter from each box in current row and puts together the guessed word
                for (int i = 0; i < 5; i++) {
                    guessedWord += boxes[rowPosition][i].getLetter().toLowerCase();
                }

                if (checkIfValidGuess(guessedWord)) { // if the guess is the real word, the following code will execute
                    highlightLetters(guessedWord); // highlights letter boxes - more explanation in method

                    if (guessedWord.equals(targetWord)) { // if user guess correct
                        gameOver = true;

                        // saves score (row) to storage
                        saveScoreToLocalStorage(rowPosition);

                        playBtn.setText("NEW GAME");
                        sendAlert("You win!");
                    } else if (rowPosition == 5) { // if user used up all six guesses
                        gameOver = true;

                        // saves score to storage - -1 represents a fail
                        saveScoreToLocalStorage(-1);

                        playBtn.setText("NEW GAME");
                        sendAlert("You lost.\nThe correct word was \"" + targetWord.toUpperCase() + "\".\nHit the NEW GAME button to play again!");
                    } else { // if there are still guesses remaining, and they have not guessed correct yet, move on to the next row
                        rowPosition++;
                        colPosition = 0;
                    }
                } else { // notifies user that the guess is not valid, as it is not in the word list
                    sendAlert("Invalid guess: not in word list.");
                }
            }
        } else if (e.getKeyCode() == 8 && colPosition > 0) { // delete key
            // removes text from letter box
            boxes[rowPosition][colPosition - 1].setLetter("");

            // removes highlighted border from letter box
            boxes[rowPosition][colPosition - 1].getBox().setBorder(GameTheme.DEFAULT_BORDER);

            colPosition--;
        } else if (e.getKeyCode() >= 65 && e.getKeyCode() <= 90 && colPosition != 5 && rowPosition != 6) { // letter press
            // sets letter typed to letter box
            boxes[rowPosition][colPosition].setLetter(Character.toString(e.getKeyChar()).toUpperCase());

            // highlights border of letter box
            boxes[rowPosition][colPosition].getBox().setBorder(GameTheme.LIGHT_GREY_BORDER);

            colPosition++;
        }
    }

    // shows dialog box with message
    private static void sendAlert(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void highlightLetters(String guessedWord) {
        // creates array versions of target word and guessed word by splitting the string by each character
        String[] targetWordArr = targetWord.split("");
        String[] guessedWordArr = guessedWord.split("");

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
            // instantiates new scanner to read from dictionary.txt - a database with all valid five-letter words
            Scanner reader = new Scanner(new File("dictionary.txt"));

            // checks if guessed word is in the database of all valid five-letter words
            while (reader.hasNextLine()) {
                if (guessedWord.equals(reader.nextLine())) {
                    return true;
                }
            }

            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void reset() {
        // resets styles and empties all letter boxes
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                boxes[r][c].setDefaultStyles();
            }
        }

        playBtn.setText("RESET");

        // new target word for next game
        generateTargetWord();

        // resets column position and row position to the first box and first row
        colPosition = 0;
        rowPosition = 0;

        gameOver = false;
    }

    private void saveScoreToLocalStorage(int score) {
        // the stats. page works by showing you the amount of times you won a game with an amount of guesses
        // so, it would show you the amount of games you won in one guess, two guesses, etc.
        // what's referred to as the "point" is the amount of times you won a gam with the given amount of guesses

        // retrieves point (value) from local storage in relation to score, or amount of guesses (key)
        String pointsFromLocalStorage = LocalStorage.getItem(Integer.toString(score));

        if (pointsFromLocalStorage != null) { // if there is an existing amount of points for given score
            // parses retrieved points from storage
            int points = Integer.parseInt(pointsFromLocalStorage);

            // stores point for that score as one more than previous amount (increments by one)
            LocalStorage.setItem(Integer.toString(score), Integer.toString(points + 1));
        } else {
            // if no existing score for that amount of guesses, sets points for that amount to 1
            LocalStorage.setItem(Integer.toString(score), Integer.toString(1));
        }
    }

}
