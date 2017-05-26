package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Luki on 25.05.2017.
 */
public class FriendModel {
    private ArrayList<String> texts;

    private int[] propabilities;

    public FriendModel() {
        propabilities = new int[4];
        for (int i = 0; i < 4; ++i)
            propabilities[i] = 0;
        texts = new ArrayList<>();
        texts.add("Wydaje mi się, że jest to odpowiedź ");
        texts.add("To na pewno jest odpowiedź ");
        texts.add("Strzelałbym ");
    }

    public String createFriendText(int correctIndex, int questionCounter, int otherAns) {
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

    private char returnChosenAnswer(int correctIndex, int questionCounter) {
        return returnChosenAnswer(correctIndex, questionCounter, false, -1);
    }

    private char returnChosenAnswer(int correctIndex, int questionCounter, int otherAns) {
        return returnChosenAnswer(correctIndex, questionCounter, true, otherAns);
    }

    private char returnChosenAnswer(int correctIndex, int questionCounter, Boolean is5050Used, int otherAns) {
        Arrays.stream(propabilities).forEach(i -> i = 0);
        int toDistribute = 100;
        propabilities[correctIndex] = 44 - 4 * questionCounter;
        toDistribute -= 44 - 4 * questionCounter;
        Random random = new Random();
        char toRet = 'A';
        if (is5050Used) {
            int x = random.nextInt(toDistribute + 1);
            propabilities[correctIndex] += x;
            toDistribute -= x;
            propabilities[otherAns] = toDistribute;
            if (propabilities[correctIndex] >= propabilities[otherAns])
                toRet += correctIndex;
            else
                toRet += otherAns;
            return toRet;
        }
        for (int i = 0; i < 3; ++i) {
            int x = random.nextInt(toDistribute + 1);
            propabilities[i] += x;
            toDistribute -= x;
        }
        propabilities[3] += toDistribute;
        int tmp = 0;
        for (int i = 1; i < 4; ++i)
            if (propabilities[i] > propabilities[tmp])
                tmp = i;
        toRet += tmp;
        return toRet;
    }
}
