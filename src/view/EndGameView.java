package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The {@link view.EndGameView} class represents the look of the end game screen.
 */
public final class EndGameView {
    /**
     * The label with the information about user's winnings.
     */
    private final Label label;
    /**
     * A button that returns to main menu when clicked.
     */
    private final Button returnToMain;
    /**
     * A button that restarts the game when clicked.
     */
    private final Button restartGame;
    /**
     * A scene representing the end game's look.
     */
    private final Scene scene;

    /**
     * An {@link view.EndGameView} class constructor.
     */
    EndGameView() {
        label = new Label();
        label.getStylesheets().add("Label.css");
        label.setId("endGameLabel");
        //label.setPrefWidth(View.getStage().getWidth()/2.0);
        //label.setPrefHeight(View.getStage().getHeight()/6.0);
        label.autosize();
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        returnToMain = createButton();
        restartGame = createButton();
        VBox root = new VBox();
        root.getChildren().add(label);
        root.getChildren().add(restartGame);
        root.getChildren().add(returnToMain);
        root.setId("endGame");
        root.setAlignment(Pos.CENTER);
        scene = new Scene(root, View.getStage().getWidth(), View.getStage().getHeight());
        scene.getStylesheets().add("Scene.css");
    }

    /**
     * A getter for the end game label.
     * @return an end game label.
     */
    public Label getLabel() {
        return label;
    }
    /**
     * A getter for the return to main menu button.
     * @return a return to main menu button.
     */
    public Button getReturnToMain() {
        return returnToMain;
    }

    /**
     * A getter for the restart game button.
     * @return a restart game button.
     */
    public Button getRestartGame() {
        return restartGame;
    }

    /**
     * A getter for the scene of the end game view.
     * @return a scene defining the end game's look.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Creates a button for the end game view with the menu style.
     * @return a created button.
     */
    private Button createButton() {
        Button btn = new Button();
        btn.getStylesheets().add("Button.css");
        btn.setId("menuButton");
        btn.setPrefHeight(View.getStage().getHeight()/6.0);
        btn.setPrefWidth(View.getStage().getWidth()/2.0);
        return btn;
    }
}
