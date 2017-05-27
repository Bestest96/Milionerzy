package model;

/**
 * The {@link model.EndGameModel} represents the data for the end game.
 */
final class EndGameModel {
    /**
     * A text for the return to main menu button.
     */
    private final String returnToMain;
    /**
     * A text for the button restarting the game.
     */
    private final String restartGame;

    /**
     * A {@link model.EndGameModel} class constructor.
     */
    EndGameModel() {
        returnToMain = "Wróc do menu";
        restartGame = "Zagraj jeszcze raz!";
    }

    /**
     * A getter for the return to main menu text.
     * @return the return to main menu text.
     */
    String getReturnToMain() {
        return returnToMain;
    }

    /**
     * A getter for restart the game text.
     * @return restart the game text.
     */
    String getRestartGame() {
        return restartGame;
    }
}
