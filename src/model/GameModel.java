package model;

import java.util.HashSet;

/**
 * The {@link model.GameModel} class represents the data for the game.
 */
public final class GameModel {
    /**
     * A HashSet object storing all IDs of used questions to avoid repetition.
     */
    private HashSet<Integer> usedID;
    /**
     * A String object representing the correct answer for the question.
     */
    private String correctAnswer;
    /**
     * A counter which describes the number of the actual question (from 0 to 11).
     */
    private byte questionCounter;
    /**
     * An array containing the money for each question.
     */
    private final int[] money;
    /**
     * An array holding used lifelines.
     */
    private Boolean[] lifelinesUsed;
    /**
     * A text for the resign button.
     */
    private final String resign;

    /**
     * The {@link model.GameModel} class constructor.
     */
    GameModel() {
        usedID = new HashSet<>(12);
        correctAnswer = null;
        questionCounter = 0;
        money = new int[12];
        money[0] = 500;
        money[1] = 1000;
        money[2] = 2000;
        money[3] = 5000;
        money[4] = 10000;
        money[5] = 20000;
        money[6] = 40000;
        money[7] = 75000;
        money[8] = 125000;
        money[9] = 250000;
        money[10] = 500000;
        money[11] = 1000000;
        lifelinesUsed = new Boolean[3];
        lifelinesUsed[0] = false;
        lifelinesUsed[1] = false;
        lifelinesUsed[2] = false;
        resign = "Rezygnuję";
    }

    /**
     * A getter for used ids in order not to repeat the question.
     * @return a set of question ids that were used.
     */
    public HashSet<Integer> getUsedID() {
        return usedID;
    }

    /**
     * A getter for money array.
     * @return an array of money for questions.
     */
    public int[] getMoney() {
        return money;
    }

    /**
     * A getter for used lifelines.
     * @return an array of booleans indicating used lifelines.
     */
    public Boolean[] getLifelinesUsed() {
        return lifelinesUsed;
    }

    /**
     * A getter for the correct answer to the question.
     * @return a correct answer to the question.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * A setter for the correct answer to the question.
     * @param correctAnswer a correct answet to be set.
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * A getter for the actual question counter.
     * @return the actual question counter.
     */
    public byte getQuestionCounter() {
        return questionCounter;
    }

    /**
     * A setter for the actual question counter.
     * @param questionCounter  the value of question counter to be set.
     */
    public void setQuestionCounter(byte questionCounter) {
        this.questionCounter = questionCounter;
    }

    /**
     * Resets the state of the model to start the game again.
     */
    public void resetState() {
        usedID.clear();
        questionCounter = 0;
        correctAnswer = null;
        for (int i = 0; i < 3; ++i)
            lifelinesUsed[i] = false;
    }

    /**
     * A getter for the resign button label.
     * @return the resign button label.
     */
    String getResign() {
        return resign;
    }

}
