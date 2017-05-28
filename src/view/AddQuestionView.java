package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The {@link view.AddQuestionView} class represents the look of a scene that is used to add questions.
 */
public final class AddQuestionView {
    /**
     * A scene of the view representing the whole look.
     */
    private final Scene scene;
    /**
     * A button responsible for returning to a main menu.
     */
    private final Button returnToMain;
    /**
     * A button responsible for adding question to a database.
     */
    private final Button addToDB;
    /**
     * An array of radio buttons describing the difficulty of a question to be added.
     */
    private final RadioButton diff[];
    /**
     * A text field representing the question to be added.
     */
    private final TextField question;
    /**
     * An array of text fields representing answers to be added.
     */
    private final TextField answer[];

    /**
     * {@link view.AddQuestionView} class constructor
     */
    AddQuestionView() {
        VBox root = new VBox();
        VBox radioBox = new VBox();
        ToggleGroup tg = new ToggleGroup();
        HBox top = new HBox();
        top.setPadding(new Insets(0, 0, 10, 0));
        HBox ansAB = new HBox();
        HBox ansCD = new HBox();
        diff = new RadioButton[3];
        for (int i = 0; i < 3; ++i) {
            diff[i] = createRadioButton();
            radioBox.getChildren().add(diff[i]);
            diff[i].setAlignment(Pos.CENTER);
            diff[i].setToggleGroup(tg);
        }
        diff[0].setSelected(true);
        question = new TextField();
        question.setAlignment(Pos.CENTER);
        question.getStylesheets().add("TextField.css");
        question.setPrefWidth(View.getStage().getWidth());
        question.setPrefHeight(View.getStage().getHeight()/6);
        returnToMain = createButton();
        addToDB = createButton();
        answer = new TextField[4];
        for (int i = 0; i < 4; ++i)
            answer[i] = createAnswer();
        answer[0].setId("goodAnswer");
        top.getChildren().add(returnToMain);
        top.getChildren().add(radioBox);
        top.getChildren().add(addToDB);
        ansAB.getChildren().add(answer[0]);
        ansAB.getChildren().add(answer[1]);
        ansCD.getChildren().add(answer[2]);
        ansCD.getChildren().add(answer[3]);
        root.getChildren().add(top);
        root.getChildren().add(question);
        root.getChildren().add(ansAB);
        root.getChildren().add(ansCD);
        root.setAlignment(Pos.CENTER);
        root.setId("addQuestion");
        scene = new Scene (root, View.getStage().getWidth(), View.getStage().getHeight());
        scene.getStylesheets().add("Scene.css");
        resetState();
    }

    /**
     * Resets the state of {@link view.AddQuestionView} scene to default.
     */
    public void resetState() {
        diff[0].setSelected(true);
        question.clear();
        for (TextField tf : answer)
            tf.clear();
        returnToMain.requestFocus();
    }

    /**
     * A getter for question written by user.
     * @return a question written by user in a text field.
     */
    public TextField getQuestion() {
        return question;
    }
    /**
     * A getter for answers written by user.
     * @return an array of answers written by user in a text fields.
     */
    public TextField[] getAnswers() {
        return answer;
    }
    /**
     * A getter for difficulty radio buttons.
     * @return an array of difficulty radio buttons.
     */
    public RadioButton[] getDiff() {
        return diff;
    }

    /**
     * A getter for the add question scene.
     * @return the add question scene.
     */
    public Scene getScene() {
        return scene;
    }
    /**
     * A getter for return to main menu button.
     * @return the return to main menu button.
     */
    public Button getReturnToMainButton() {
        return returnToMain;
    }

    /**
     * A getter for add a question to a database button.
     * @return the add question to a database button.
     */
    public Button getAddToDBButton() {
        return addToDB;
    }

    /**
     * Creates a new {@link TextField} object for an answer and formats it to the stage's size.
     * @return a {@link TextField} object for an answer.
     */
    private TextField createAnswer() {
        TextField field = new TextField();
        field.setAlignment(Pos.CENTER);
        field.getStylesheets().add("TextField.css");
        field.setPrefWidth(View.getStage().getWidth()/2.0);
        field.setPrefHeight(View.getStage().getHeight()/6.0);
        return field;
    }

    /**
     * Creates a {@link Button} object and formats it to the stage's size.
     * @return a {@link Button} object.
     */
    private Button createButton() {
        Button btn = new Button();
        btn.getStylesheets().add("Button.css");
        btn.setId("menuButton");
        btn.setPrefWidth(View.getStage().getWidth()/3.0);
        btn.setPrefHeight(View.getStage().getHeight()/6.0);
        return btn;
    }

    /**
     * Creates a {@link RadioButton} object and formats it to the stage's size.
     * @return a {@link RadioButton} object.
     */
    private RadioButton createRadioButton() {
        RadioButton rb = new RadioButton();
        rb.getStylesheets().add("RadioButton.css");
        rb.setPrefWidth(View.getStage().getWidth()/3.0);
        rb.setPrefHeight(View.getStage().getHeight()/18.0);
        return rb;
    }
}
