package pl.agh.edu.mszarek.model;

import pl.agh.edu.mszarek.model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private String host;


    private int port;
    private String name;
    private String channel;
    private ObjectOutputStream os;
    private ObjectInputStream is;

    public void connect(){
        try{
            socket = new Socket("localhost", this.port);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
            os.writeObject(this.channel);
            os.writeObject(this.name);
        } catch (IOException ioe){
            System.err.println("Can't connect to server.");
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    public void sendMsg(Message msg) throws IOException{
        os.writeObject(msg);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {

        this.host = host;
    }


    public Message receiveMsg(){
        Message msg = null;
        try{
            msg = (Message) is.readObject();
        }catch(IOException ioe){
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        return msg;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Socket getSocket() {

        return socket;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getChannel() {
        return channel;
    }
}
