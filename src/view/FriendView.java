package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The {@link view.FriendView} class represents the look of the friend lifeline.
 */
public final class FriendView {
    /**
     * The stage at which the friend lifeline will be displayed.
     */
    private Stage friendStage;
    /**
     * A label which will contain friend's text.
     */
    private Label friendHelp;

    /**
     * A {@link view.FriendView} class constructor.
     */
    FriendView() {
        friendStage = new Stage();
        StackPane sp = new StackPane();
        friendHelp = new Label();
        friendHelp.getStylesheets().add("Label.css");
        friendHelp.setId("friendLabel");
        friendHelp.setWrapText(true);
        sp.setAlignment(Pos.CENTER);
        sp.getChildren().add(friendHelp);
        //sp.setId("friendScene");
        Scene scene = new Scene(sp, 500, 200);
        //scene.getStylesheets().add("Scene.css");
        friendStage.setScene(scene);
        friendStage.setAlwaysOnTop(true);
        friendStage.setResizable(false);
    }

    /**
     * Sets the label with friend's text/
     * @param friendHelp a text to be set to the label.
     */
    public void setFriendHelp(String friendHelp) {
        this.friendHelp.setText(friendHelp);
    }

    /**
     * Displays the stage with friend's advise.
     */
    public void display() {
        friendStage.showAndWait();
    }
}
