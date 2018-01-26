package pl.agh.edu.mszarek.chatclient.LoggingScenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Channel {

    private static String channel = "default";


    public static String display() {


        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login");
        window.setMinWidth(400);

        //Label
        Label label = new Label("Insert channel name");

        //TextField
        TextField field = new TextField();

        //Button
        Button button = new Button("log in");
        button.setDefaultButton(true);
        button.setOnAction(e -> {
            if (!field.getText().equals(""))
                channel = field.getText();
            window.close();
        });

        VBox layout = new VBox(30);
        layout.getChildren().addAll(label, field, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return channel;
    }
}
