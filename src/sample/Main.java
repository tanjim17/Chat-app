package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;
    public static Client client;

    @Override
    public void start(Stage primaryStage) throws Exception{

        client = new Client();

        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent root = loader.load();

        LogInControl lc = loader.getController();
        client.setLogInControl(lc);

        stage.setTitle("Chat Application");
        stage.setScene(new Scene(root, 800, 400));
        stage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }

}
