package view;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;

/**
 * The View class represents the actual look of an application and all the views to switch.
 */
public class View {
    /**
     * A stage at which all the scenes are displayed.
     */
    private static Stage stage;
    /**
     * An actual displayed scene.
     */
    private Scene scene;
    /**
     * Width of an application window.
     */
    private double width;
    /**
     * Height of an application window.
     */
    private double height;
    /**
     * An object representing the view of the main menu.
     */
    private MainMenuView mainMenuView;
    /**
     * An object representing the view of the menu to add questions.
     */
    private AddQuestionView addQuestionView;
    /**
     * An object representing the game look.
     */
    private GameView gameView;

    private EndGameView endGameView;

    private AudienceView audienceView;

    private FriendView friendView;

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
        this.scene = scene;
        stage.setScene(scene);
        //stage.setFullScreen(true);
        //stage.centerOnScreen();
        stage.show();
    }

    /**
     * Sets the dimensions of the application's window.
     */
    public void setDimensions() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        width = dimension.width;
        stage.setWidth(width);
        height = dimension.height;
        stage.setHeight(height);
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

    public EndGameView getEndGameView() {
        return endGameView;
    }

    public FriendView getFriendView() {
        return friendView;
    }

    public AudienceView getAudienceView() {
        return audienceView;
    }

    public void setAudienceView(AudienceView audienceView) {
        this.audienceView = audienceView;
    }

    /**
     * A getter for an application's main scene.
     * @return an application's main scene.
     */
    public static Stage getStage() {
        return stage;
    }

}
