package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by Luki on 22.05.2017.
 */
public class EndGameView {
    private Label label;
    private Button returnToMain;
    private Button restartGame;
    private Scene scene;

    public EndGameView() {
        label = new Label();
        label.getStylesheets().add("Label.css");
        label.setId("endGameLabel");
        label.setPrefWidth(View.getStage().getWidth()/2.0);
        label.setPrefHeight(View.getStage().getHeight()/6.0);
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

    public Label getLabel() {
        return label;
    }

    public Button getReturnToMain() {
        return returnToMain;
    }

    public Button getRestartGame() {
        return restartGame;
    }

    public Scene getScene() {
        return scene;
    }

    private Button createButton() {
        Button btn = new Button();
        btn.getStylesheets().add("Button.css");
        btn.setId("menuButton");
        btn.setPrefHeight(View.getStage().getHeight()/6.0);
        btn.setPrefWidth(View.getStage().getWidth()/2.0);
        return btn;
    }
}
