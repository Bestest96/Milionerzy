package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * The {@link view.MainMenuView} class represents the look of the main menu.
 */
public class MainMenuView {
    /**
     * The scene containing main menu's components.
     */
    private Scene scene;
    /**
     * A button responsible for starting the game.
     */
    private Button play;
    /**
     * A button responsible for going to add question menu.
     */
    private Button addQuestion;
    /**
     * A button responsible for exiting the application.
     */
    private Button exit;

    /**
     * {@link MainMenuView} class constructor.
     */
    public MainMenuView() {
        play = new Button();
        addQuestion = new Button();
        exit = new Button();
        setButtonStyle(play);
        setButtonStyle(addQuestion);
        setButtonStyle(exit);
        VBox root = new VBox();
        root.setId("mainMenu");
        root.getChildren().add(play);
        root.getChildren().add(addQuestion);
        root.getChildren().add(exit);
        root.setAlignment(Pos.CENTER);
        scene = new Scene (root, View.getStage().getWidth(), View.getStage().getHeight());
        scene.getStylesheets().add("Scene.css");
        resetState();
    }

    /**
     * Resets the state of {@link view.MainMenuView} scene to default.
     */
    public void resetState() {
        play.requestFocus();
    }

    /**
     * Sets the style of button in the main menu.
     * @param btn a button to be "styled".
     */
    private void setButtonStyle(Button btn) {
        btn.getStylesheets().add("Button.css");
        btn.setId("menuButton");
        btn.setPrefWidth(View.getStage().getWidth()/2.0);
        btn.setPrefHeight(View.getStage().getHeight()/6.0);
    }

    /**
     * A getter for the main menu scene.
     * @return the main menu scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * A getter for the play button.
     * @return the play button.
     */
    public Button getPlayButton() {
        return play;
    }

    /**
     * A getter for the add question button.
     * @return the add question button.
     */
    public Button getAddQuestionButton() {
        return addQuestion;
    }

    /**
     * A getter for the exit button.
     * @return the exit button.
     */
    public Button getExitButton() {
        return exit;
    }

}
