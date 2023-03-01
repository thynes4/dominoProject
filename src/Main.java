import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Boneyard boneyard = new Boneyard();
        boneyard.initialize();
        System.out.print(boneyard);
        System.out.print(boneyard.draw());
    }
}
