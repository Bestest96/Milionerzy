import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.View;

/**
 * The {@link Main} class initializes the MVC and JavaFX components.
 * @author Lukasz Lepak
 * @version 1.3.1
 */
public class Main extends Application {
    /**
     * The start method initializes Model, View and Controller of the application.
     * @param primaryStage the main stage of the application.
     * @throws Exception when initialization goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        View.setStage(primaryStage);
        View view = new View();
        Model model = new Model(view);
        new Controller(model, view);
    }
    /**
     * The main method of the application tha initializes JavaFX.
     * @param args args from the console (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
