package model;

/**
 * Created by Luki on 22.05.2017.
 */
public class EndGameModel {

    private final String returnToMain;
    private final String restartGame;

    public EndGameModel() {
        returnToMain = "Wróc do menu";
        restartGame = "Zagraj jeszcze raz!";
    }

    public String getReturnToMain() {
        return returnToMain;
    }

    public String getRestartGame() {
        return restartGame;
    }
}
