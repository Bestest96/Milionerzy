package model;

import java.util.Arrays;
import java.util.HashSet;

/**
 * The {@link model.GameModel} class represents the data for the game.
 */
public class GameModel {
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

    private final int[] money;

    private Boolean[] lifelinesUsed;

    /**
     * The {@link model.GameModel} class constructor.
     */
    public GameModel() {
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
    }

    public HashSet<Integer> getUsedID() {
        return usedID;
    }

    public int[] getMoney() {
        return money;
    }

    public Boolean[] getLifelinesUsed() {
        return lifelinesUsed;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public byte getQuestionCounter() {
        return questionCounter;
    }

    public void setQuestionCounter(byte questionCounter) {
        this.questionCounter = questionCounter;
    }

    public void resetState() {
        usedID.clear();
        questionCounter = 0;
        correctAnswer = null;
        for (int i = 0; i < 3; ++i)
            lifelinesUsed[i] = false;
    }
}
