package model;

import java.util.Random;

/**
 * The {@link model.AudienceModel} represents the data responsible for calculating audience percentages.
 */
final class AudienceModel {
    /**
     * An array containing answer labels (A, B, C and D).
     */
    private final String[] answers;
    /**
     * An array containing percents of votes for each answer.
     */
    private int[] percents;

    /**
     * A {@link model.AudienceModel} class constructor.
     */
    AudienceModel() {
        answers = new String[4];
        percents = new int[4];
        answers[0] = "A";
        answers[1] = "B";
        answers[2] = "C";
        answers[3] = "D";
        for (int i = 0; i < 4; ++i)
            percents[i] = 0;
    }

    /**
     * A method that calls {@link model.AudienceModel#setPercents(int, int, int, Boolean)} when 50/50 is not used.
     * @param correctIndex an index of the correct question.
     * @param questionCounter an actual question counter (in range from 0 to 11).
     */
    void setPercents(int correctIndex, int questionCounter) {
        setPercents(correctIndex, questionCounter, -1, false);
    }
    /**
     * A method that calls {@link model.AudienceModel#setPercents(int, int, int, Boolean)} when 50/50 is used.
     * @param correctIndex an index of the correct answer.
     * @param questionCounter an actual question counter (in range from 0 to 11).
     * @param otherAns the other question that should be calculated after 50/50.
     */
    void setPercents(int correctIndex, int questionCounter, int otherAns) {
        setPercents(correctIndex, questionCounter, otherAns, true);
    }

    /**
     * A method that sets the answer's percents based on random distribution.
     * @param correctIndex an index of the correct answer.
     * @param questionCounter an actual question counter (in range from 0 to 11).
     * @param otherAns the other question that shoulb be calculated after 50/50, if it's -1 it is not used.
     * @param is5050Used describes whether the 50/50 lifeline was used.
     */
    private void setPercents(int correctIndex, int questionCounter, int otherAns, Boolean is5050Used) {
        for (int i = 0; i < 4; ++i)
            percents[i] = 0;
        int toDistribute = 100;
        percents[correctIndex] = 44 - 4 * questionCounter;
        toDistribute -= 44 - 4 * questionCounter;
        Random random = new Random();
        if (is5050Used) {
            int x = random.nextInt(toDistribute + 1);
            percents[correctIndex] += x;
            toDistribute -= x;
            percents[otherAns] = toDistribute;
            return;
        }
        for (int i = 0; i < 3; ++i) {
            int x = random.nextInt(toDistribute + 1);
            percents[i] += x;
            toDistribute -= x;
        }
        percents[3] += toDistribute;
}

    /**
     * A getter for answer labels.
     * @return an array of answer labels.
     */
    String[] getAnswers() {
        return answers;
    }

    /**
     * A getter for percents.
     * @return an array of percents.
     */
    int[] getPercents() {
        return percents;
    }
}
