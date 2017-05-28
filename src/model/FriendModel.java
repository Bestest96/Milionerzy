package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * The {@link model.FriendModel} class represents the data for the friend lifeline.
 */
final class FriendModel {
    /**
     * An array of texts that a friend can use.
     */
    private final ArrayList<String> texts;
    /**
     * An array of probabilities of choosing an answer.
     */
    private int[] probabilities;

    /**
     * A {@link model.FriendModel} class constructor.
     */
    FriendModel() {
        probabilities = new int[4];
        for (int i = 0; i < 4; ++i)
            probabilities[i] = 0;
        texts = new ArrayList<>();
        texts.add("Wydaje mi się, że jest to odpowiedź ");
        texts.add("To na pewno jest odpowiedź ");
        texts.add("Strzelałbym ");
        texts.add("Nie jestem pewien, ale wybrałbym odpowiedź ");
        texts.add("Coś mi mówi, że to będzie ");
        texts.add("Zaufaj mi - to jest odpowiedź ");
        texts.add("Skoro muszę coś powiedzieć, to wybiorę odpowiedż ");
        texts.add("Nie jestem w tej dziedzinie ekspertem, ale możesz zaznaczyć ");
        texts.add("Hmm, nie dam sobie głowy za to ściąć, ale wybrałbym ");
        texts.add("Nie wiem, wybrałbym ");
        texts.add("Weź odpowiedź ");
        texts.add("Zaznacz odpowiedź ");
    }

    /**
     * Creates a friend text with the chosen answer.
     * @param correctIndex the index of the correct answer.
     * @param questionCounter an actual question counter (range 0-11).
     * @param otherAns the other answer (used if lifeline 50/50 was used on that question).
     * @return a text of friend advice with the chosen question.
     */
    String createFriendText(int correctIndex, int questionCounter, int otherAns) {
        StringBuilder hint = new StringBuilder();
        Random random = new Random();
        int index = random.nextInt(texts.size());
        hint.append(texts.get(index));
        char x;
        if (otherAns != -1)
            x = returnChosenAnswer(correctIndex, questionCounter, otherAns);
        else
            x = returnChosenAnswer(correctIndex, questionCounter);
        hint.append(x);
        hint.append(".");
        return hint.toString();
    }

    /**
     * A method that calls {@link model.FriendModel#returnChosenAnswer(int, int, Boolean, int)} when 50/50 is not used.
     * @param correctIndex an index of the correct question.
     * @param questionCounter an actual question counter (range 0-11).
     * @return a char representing the chosen answer.
     */
    private char returnChosenAnswer(int correctIndex, int questionCounter) {
        return returnChosenAnswer(correctIndex, questionCounter, false, -1);
    }
    /**
     * A method that calls {@link model.FriendModel#returnChosenAnswer(int, int, Boolean, int)} when 50/50 is used.
     * @param correctIndex an index of the correct question.
     * @param questionCounter an actual question counter (in range from 0 to 11).
     * @param otherAns the other question that should be calculated after 50/50.
     * @return a char representing the chosen answer.
     */
    private char returnChosenAnswer(int correctIndex, int questionCounter, int otherAns) {
        return returnChosenAnswer(correctIndex, questionCounter, true, otherAns);
    }

    /**
     * A method that sets the answer's probabilities and chooses one based on random distribution.
     * @param correctIndex an index of the correct answer.
     * @param questionCounter an actual question counter (in range from 0 to 11).
     * @param is5050Used describes whether the 50/50 lifeline was used.
     * @param otherAns the other question that should be calculated after 50/50, if it's -1 it is not used.
     * @return a char representing the chosen answer.
     */
    private char returnChosenAnswer(int correctIndex, int questionCounter, Boolean is5050Used, int otherAns) {
        for (int i = 0; i < 4; ++i)
            probabilities[i] = 0;
        int toDistribute = 100;
        probabilities[correctIndex] = 44 - 4 * questionCounter;
        toDistribute -= 44 - 4 * questionCounter;
        Random random = new Random();
        char toRet = 'A';
        if (is5050Used) {
            int x = random.nextInt(toDistribute + 1);
            probabilities[correctIndex] += x;
            toDistribute -= x;
            probabilities[otherAns] = toDistribute;
            x = random.nextInt(100);
            if (correctIndex < otherAns) {
                if (x < probabilities[correctIndex])
                    toRet += correctIndex;
                else
                    toRet += otherAns;
            }
            else {
                    if (x < probabilities[otherAns])
                        toRet += otherAns;
                    else
                        toRet += correctIndex;
            }

            return toRet;
        }
        for (int i = 0; i < 3; ++i) {
            int x = random.nextInt(toDistribute + 1);
            probabilities[i] += x;
            toDistribute -= x;
        }
        probabilities[3] += toDistribute;
        int tmp = 0;
        int x = random.nextInt(100);
        for (int i = 0; i < 4; ++i) {
            if (x >= tmp && x < tmp + probabilities[i]) {
                toRet += i;
                break;
            }
            tmp += probabilities[i];
        }
        return toRet;
    }
}
