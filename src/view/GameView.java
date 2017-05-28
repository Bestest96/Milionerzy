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
    private final Scene scene;
    /**
     * An array of buttons used to check answers.
     */
    private final Button[] answers;
    /**
     * An array of lifelines used to use lifelines.
     */
    private final Button[] lifelines;
    /**
     * A button used to resign from further play.
     */
    private final Button resign;
    /**
     * A label used to display the question.
     */
    private final Label question;
    /**
     * An array of labels used to describe the money prizes.
     */
    private final Label[] money;

    /**
     * A {@link view.GameView} class constructor.
     */
    GameView() {
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
            lifelines[i].getStylesheets().add("Button.css");
            helps.getChildren().add(lifelines[i]);
        }
        lifelines[0].setId("5050");
        lifelines[1].setId("friend");
        lifelines[2].setId("audience");
        info.getChildren().add(helps);
        money = new Label[12];
        for (int i = 11; i >= 0; --i) {
            money[i] = createMoneyLabel();
            info.getChildren().add(money[i]);
        }
        resign = new Button();
        resign.setPrefWidth(View.getStage().getWidth()/3.0);
        resign.setPrefHeight(View.getStage().getHeight()/15.0);
        resign.getStylesheets().add("Button.css");
        resign.setId("resignButton");
        info.getChildren().add(resign);
        info.setAlignment(Pos.CENTER);
        root.getChildren().add(info);
        root.setId("game");
        scene = new Scene(root, View.getStage().getWidth(), View.getStage().getHeight());
        scene.getStylesheets().add("Scene.css");
    }

    /**
     * A getter for the question label.
     * @return the question label.
     */
    public Label getQuestion() {
        return question;
    }

    /**
     * A gettr for the answer buttons.
     * @return an array of answer buttons.
     */
    public Button[] getAnswers() {
        return answers;
    }

    /**
     * A getter for the money labels.
     * @return an array of money labels.
     */
    public Label[] getMoney() {
        return money;
    }

    /**
     * A getter for the lifeline buttons.
     * @return na array of lifeline buttons.
     */
    public Button[] getLifelines() {
        return lifelines;
    }

    /**
     * A getter for the resign button.
     * @return the resign button
     */
    public Button getResign() {
        return resign;
    }

    /**
     * A getter for the scene of the game view.
     * @return the scene withe the game view's look.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Creates an answer button with the appropriate style.
     * @return a created answer button.
     */
    private Button createAnswerButton() {
        Button btn = new Button();
        btn.getStylesheets().add("Button.css");
        btn.setId("gameButton");
        btn.setPrefHeight(View.getStage().getHeight()/8.0);
        btn.setPrefWidth(View.getStage().getWidth()/3.0);
        btn.setAlignment(Pos.CENTER);
        return btn;
    }

    /**
     * Creates a money label with an appropriate style.
     * @return a created money label.
     */
    private Label createMoneyLabel() {
        Label lb = new Label();
        lb.setPrefWidth(View.getStage().getWidth()/9.0);
        lb.setPrefHeight(View.getStage().getHeight()/15.0);
        lb.getStylesheets().add("Label.css");
        lb.setId("moneyLabel");
        lb.setAlignment(Pos.CENTER);
        return lb;
    }

    /**
     * Creates a question label.
     * @return a created question label.
     */
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

    /**
     * Resets the state of the view in order to start again.
     */
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
        resign.setDisable(false);
        money[0].setId("currentQuestionLabel");
        money[1].setId("guaranteedMoneyLabel");
        money[6].setId("guaranteedMoneyLabel");
        lifelines[0].setId("5050");
        lifelines[1].setId("friend");
        lifelines[2].setId("audience");
    }

    /**
     * Sets the disabled status for buttons.
     * @param disable true or false - whether the buttons should be disabled or not.
     * @param disableLifelines an array of booleans describing whether lifeline buttons should be affected.
     */
    public void setDisabledButtons(Boolean disable, Boolean[] disableLifelines) {
        for (Button b : answers)
            b.setDisable(disable);
        for (int i = 0 ; i < 3 ; ++i)
            if (!disableLifelines[i])
                lifelines[i].setDisable(disable);
        resign.setDisable(disable);
}

}
