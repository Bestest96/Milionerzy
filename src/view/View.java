package view;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;

/**
 * The View class represents the actual look of an application and all the views to switch.
 */
public final class View {
    /**
     * A stage at which all the scenes are displayed.
     */
    private static Stage stage;
    /**
     * An object representing the view of the main menu.
     */
    private final MainMenuView mainMenuView;
    /**
     * An object representing the view of the menu to add questions.
     */
    private final AddQuestionView addQuestionView;
    /**
     * An object representing the game look.
     */
    private final GameView gameView;
    /**
     * An object representing the end game look.
     */
    private final EndGameView endGameView;
    /**
     * An object representing the audience view (bar chart, cannot be final due to bar chart bug).
     */
    private AudienceView audienceView;
    /**
     * An object representing the friend view (his "advice").
     */
    private final FriendView friendView;

    /**
     * Sets the application's main stage.
     * @param stage a stage to be set as main stage.
     */
    public static void setStage (Stage stage) {
        View.stage = stage;
    }

    /**
     * View class constructor.
     * @throws ClassNotFoundException when no class Main is found.
     */
    public View () throws ClassNotFoundException {
        setDimensions();
        mainMenuView = new MainMenuView();
        addQuestionView = new AddQuestionView();
        gameView = new GameView();
        endGameView = new EndGameView();
        audienceView = new AudienceView();
        friendView = new FriendView();
        stage.setTitle("Milionerzy");
        stage.getIcons().add((new ImageView(Class.forName("Main").getResource("logo.jpg").toExternalForm()).getImage()));
        setActiveScene(mainMenuView.getScene());
    }

    /**
     * Sets the active scene to be displayed for the application.
     * @param scene a scene to be displayed.
     */
    public void setActiveScene (Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    /**
     * A getter for an object representing the main menu view.
     * @return an object representing the main menu view.
     */
    public MainMenuView getMainMenuView () {
        return mainMenuView;
    }

    /**
     * A getter for an object representing the adding question view.
     * @return an object representing the adding question view.
     */
    public AddQuestionView getAddQuestionView () {
        return addQuestionView;
    }

    /**
     * A getter for an object representing the game view.
     * @return an object representing the game view.
     */
    public GameView getGameView() {
        return gameView;
    }
    /**
     * A getter for an object representing the end game view.
     * @return an object representing the end game view.
     */
    public EndGameView getEndGameView() {
        return endGameView;
    }
    /**
     * A getter for an object representing the friend view.
     * @return an object representing the friend view.
     */
    public FriendView getFriendView() {
        return friendView;
    }
    /**
     * A getter for an object representing the audience view.
     * @return an object representing the audience view.
     */
    public AudienceView getAudienceView() {
        return audienceView;
    }

    /**
     * A setter for the audience view (due to buggy bar charts this has to be made this way).
     * @param audienceView a new audience view to be set.
     */
    public void setAudienceView(AudienceView audienceView) {
        this.audienceView = audienceView;
    }

    /**
     * A getter for an application's main scene.
     * @return an application's main scene.
     */
    static Stage getStage() {
        return stage;
    }

    /**
     * Sets the dimensions of the application's window.
     */
    private void setDimensions() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setWidth(dimension.getWidth());
        stage.setHeight(dimension.getHeight());
    }
}
