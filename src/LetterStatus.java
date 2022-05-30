
public enum LetterStatus {

    /*

    About LetterStatus:
    This enum represents all the possible types of statuses that a letter box can have. Explanations for each are
    beside each constant.

     */

    none, // default, before a guess is entered in the box
    absent, // the letter in the box is in correct position
    present, // the letter in the box is in the target word, but not in correct position
    correct // the letter in the box is not in the target word

}
