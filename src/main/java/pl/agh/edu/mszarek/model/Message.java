package pl.agh.edu.mszarek.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message implements Serializable {
    private String author = null;
    private String chatRoom = null;
    private String content = null;
    private String recipient = null;
    private Boolean privateMsg = false;

    public Message(String author, String chatRoom, String text){
        this.author = author;
        this.chatRoom = chatRoom;
        String pattern = "^@([a-zA-Z0-9]+)[ ]+([^ ].*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if(m.find()){
            this.privateMsg = true;
            this.recipient = m.group(1);
            this.content = m.group(2);
        } else {
            this.content = text;
        }
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getRecipient() {
        return recipient;
    }

    public Boolean getPrivateMsg() {
        return privateMsg;
    }

    @Override
    public String toString(){
        if(privateMsg){
            return "{" + LocalTime.now() + "} <" + this.author +"> directly to <" + this.recipient + ">: " + this.content + "\n";
        } else {
            return "{" + LocalTime.now() + "} <" + this.author + ">: " + this.content + "\n";
        }
    }
}
