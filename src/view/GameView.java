package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The {@link view.GameView} class represents the look of the actual game.
 */
public class GameView {
    /**
     * A scene containing all {@link view.GameView} object components
     */
    private Scene scene;
    private Button[] answers;
    private Button[] lifelines;
    private Button resign;
    private Label question;
    private Label[] money;

    public GameView() {
        HBox root = new HBox();
        VBox qst = new VBox();
        HBox AB = new HBox();
        HBox CD = new HBox();
        qst.setPrefWidth(2.0*View.getStage().getWidth()/3.0);
        qst.setPrefHeight(View.getStage().getHeight());
        question = createQuestionLabel();
        qst.getChildren().add(question);
        answers = new Button[4];
        for (int i = 0; i < 4; ++i)
            answers[i] = createAnswerButton();
        AB.getChildren().add(answers[0]);
        AB.getChildren().add(answers[1]);
        AB.setPrefHeight(View.getStage().getHeight()/8.0);
        AB.setPrefWidth(2*View.getStage().getWidth()/3.0);
        CD.getChildren().add(answers[2]);
        CD.getChildren().add(answers[3]);
        CD.setPrefHeight(View.getStage().getHeight()/8.0);
        CD.setPrefWidth(2*View.getStage().getWidth()/3.0);
        qst.getChildren().add(AB);
        qst.getChildren().add(CD);
        qst.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().add(qst);
        VBox info = new VBox();
        info.setPrefWidth(View.getStage().getWidth()/3.0);
        info.setPrefHeight(View.getStage().getHeight());
        HBox helps = new HBox();
        helps.setPrefWidth(View.getStage().getWidth()/3.0);
        helps.setPrefHeight(View.getStage().getHeight()/15.0);
        lifelines = new Button[3];
        for (int i = 0; i < 3; ++i) {
            lifelines[i] = new Button();
            lifelines[i].setPrefWidth(View.getStage().getWidth()/9.0);
            lifelines[i].setPrefHeight(View.getStage().getHeight()/15.0);
            helps.getChildren().add(lifelines[i]);
        }
        lifelines[0].setText("50/50");
        lifelines[1].setText("Telefon");
        lifelines[2].setText("Publiczność");
        info.getChildren().add(helps);
        money = new Label[12];
        for (int i = 11; i >= 0; --i) {
            money[i] = createMoneyLabel();
            info.getChildren().add(money[i]);
        }
        info.setAlignment(Pos.CENTER);
        root.getChildren().add(info);
        root.setId("game");
        scene = new Scene(root, View.getStage().getWidth(), View.getStage().getHeight());
        scene.getStylesheets().add("Scene.css");
    }

    public Label getQuestion() {
        return question;
    }

    public Button[] getAnswers() {
        return answers;
    }

    public Label[] getMoney() {
        return money;
    }

    public Button[] getLifelines() {
        return lifelines;
    }

    public Scene getScene() {
        return scene;
    }

    private Button createAnswerButton() {
        Button btn = new Button();
        btn.getStylesheets().add("Button.css");
        btn.setId("gameButton");
        btn.setPrefHeight(View.getStage().getHeight()/8.0);
        btn.setPrefWidth(View.getStage().getWidth()/3.0);
        btn.setAlignment(Pos.CENTER);
        return btn;
    }

    private Label createMoneyLabel() {
        Label lb = new Label();
        lb.setPrefWidth(View.getStage().getWidth()/9.0);
        lb.setPrefHeight(View.getStage().getHeight()/15.0);
        lb.getStylesheets().add("Label.css");
        lb.setId("moneyLabel");
        lb.setAlignment(Pos.CENTER);
        return lb;
    }

    private Label createQuestionLabel() {
        Label text = new Label();
        text.getStylesheets().add("Label.css");
        text.setId("questionLabel");
        text.setPrefWidth(2*View.getStage().getWidth()/3.0);
        text.setPrefHeight(View.getStage().getHeight()/8.0);
        text.setWrapText(true);
        text.setAlignment(Pos.CENTER);
        text.setPadding(new Insets(0, 100, 0, 100));
        return text;
    }

    public void resetState() {
        for (Button b : answers) {
            b.setText("");
            b.setId("gameButton");
            b.setDisable(false);
        }
        for (Button b : lifelines)
            b.setDisable(false);
        for (Label l : money)
            l.setId("moneyLabel");
        money[0].setId("currentQuestionLabel");
        money[1].setId("guaranteedMoneyLabel");
        money[6].setId("guaranteedMoneyLabel");
    }

    public void setDisabledButtons(Boolean disable, Boolean[] disableLifelines) {
        for (Button b : answers)
            b.setDisable(disable);
        for (int i = 0 ; i < 3 ; ++i)
            if (!disableLifelines[i])
                lifelines[i].setDisable(disable);
}

}
