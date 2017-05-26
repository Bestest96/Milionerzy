package model;

/**
 * The {@link model.MainMenuModel} class represents the data for the main menu screen (mainly button labels).
 */
public class MainMenuModel {
    /**
     * A text for a button responsible for starting the main game.
     */
    private String playText;
    /**
     * A text for button launching add question screen.
     */
    private String addQText;
    /**
     * A text for button exiting the application.
     */
    private String exitText;

    /**
     * {@link model.MainMenuModel} class constructor
     */
    public MainMenuModel() {
        playText = "Graj!";
        addQText = "Dodaj pytanie";
        exitText = "Wyjdź";
    }

    /**
     * A getter for a play button text.
     * @return a play button text.
     */
    public String getPlayText() {
        return playText;
    }

    /**
     * A getter for an add question button text.
     * @return an add question button text.
     */
    public String getAddQText() {
        return addQText;
    }

    /**
     * A getter for an exit application text.
     * @return an exit application text.
     */
    public String getExitText() {
        return exitText;
    }
}
