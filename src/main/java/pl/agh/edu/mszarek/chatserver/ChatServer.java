package pl.agh.edu.mszarek.chatserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
    private int portNumber;
    private ServerSocket serverSocket = null;
    static Map<String, Map<String, ObjectOutputStream>> chatRooms = new HashMap<>();

    public void startServer() {
        try{
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Chat server is running, you can run client.");
            acceptClients();
        } catch (IOException ioe) {
            System.err.println("Can't establish connection.");
            ioe.printStackTrace();
        }
    }

    private void acceptClients() throws IOException{
        while(serverSocket!=null){
            Socket socket = serverSocket.accept();
            new Thread(new ClientThread(socket)).start();
        }
    }

    private ChatServer(){
        this.portNumber = 8080;
    }

    private ChatServer(int port){
        this.portNumber = port;
    }

    public static void main(String[] args){
        ChatServer server;
        if(args.length>0) {
            if(args[0].matches("[a-zA-Z]")) {
                System.err.println("Usage: java -jar ... $PORTNUMBER");
                throw new IllegalArgumentException();
            }
            server = new ChatServer(Integer.parseInt(args[0]));
        }
        else server = new ChatServer();
        server.startServer();
    }
}
