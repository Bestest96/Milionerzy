import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.View;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        View.setStage(primaryStage);
        View view = new View();
        Model model = new Model(view);
        Controller controller = new Controller(model, view);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
