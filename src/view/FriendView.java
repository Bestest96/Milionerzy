package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Luki on 25.05.2017.
 */
public class FriendView {
    private Stage friendStage;

    private Label friendHelp;

    public FriendView() {
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

    public void setFriendHelp(String friendHelp) {
        this.friendHelp.setText(friendHelp);
    }

    public void display() {
        friendStage.showAndWait();
    }
}
