package pl.agh.edu.mszarek.chatclient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.agh.edu.mszarek.model.Message;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainChatController implements Initializable {

    @FXML
    private TextArea messagesText;

    @FXML
    private TextField insertText;

    @FXML
    private Label roomName;

    @FXML
    private Label userName;

    public MainChatController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.client.setHost(Hostname.display());
        Main.client.setPort(Port.display());
        Main.client.setName(LoginBox.display());
        Main.client.setChannel(Channel.display());
        Main.client.connect();
        roomName.setText(Main.client.getChannel());
        userName.setText(Main.client.getName());
        readMessages();
    }

    @FXML
    public void sendMessage() {
        if (!insertText.getText().equals("")) {
            try {
                Main.client.sendMsg(new Message(Main.client.getName(), Main.client.getChannel(), insertText.getText()));
                System.out.println(Main.client.getName());
            } catch (IOException e) {
                System.err.println("Couldn't send message");
                e.printStackTrace();
            }
            insertText.clear();
        }
    }


    private void readMessages() {
        Thread thread = new Thread(() -> {
            while (!Main.client.getSocket().isClosed()) {
                Message message = null;
                message = Main.client.receiveMsg();
                if (message != null) {
                    messagesText.appendText(message.toString());
                }

            }
        }
        );
        thread.setDaemon(true);
        thread.start();
    }


    public void quit() {
        Platform.exit();
    }

}

