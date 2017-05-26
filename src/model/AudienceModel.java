package model;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Luki on 24.05.2017.
 */
public class AudienceModel {
    String[] answers;
    int[] percents;

    public AudienceModel() {
        answers = new String[4];
        percents = new int[4];
        answers[0] = "A";
        answers[1] = "B";
        answers[2] = "C";
        answers[3] = "D";
        for (int i = 0; i < 4; ++i)
            percents[i] = 0;
    }

    public void setPercents(int correctIndex, int questionCounter) {
        setPercents(correctIndex, questionCounter, -1, false);
    }

    public void setPercents(int correctIndex, int questionCounter, int otherAns) {
        setPercents(correctIndex, questionCounter, otherAns, true);
    }

    private void setPercents(int correctIndex, int questionCounter, int otherAns, Boolean is5050Used) {
        Arrays.stream(percents).forEach(i -> i = 0);
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

    public String[] getAnswers() {
        return answers;
    }

    public int[] getPercents() {
        return percents;
    }
}
