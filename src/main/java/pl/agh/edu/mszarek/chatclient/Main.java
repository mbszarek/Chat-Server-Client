package pl.agh.edu.mszarek.chatclient;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.agh.edu.mszarek.model.Client;

public class Main extends Application {
    static Client client = new Client();
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent mainChat = FXMLLoader.load(getClass().getResource("../../../../../../resources/mainChat.fxml"));
        primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(mainChat));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }


    public static void main(String[] args) {
        launch(args);
    }
}