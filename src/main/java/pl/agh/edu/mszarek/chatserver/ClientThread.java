package pl.agh.edu.mszarek.chatserver;

import pl.agh.edu.mszarek.model.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientThread implements Runnable{
    private final Socket socket;
    private final ObjectInputStream is;
    private final ObjectOutputStream os;
    private final String chatRoom;
    private final String name;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        is = new ObjectInputStream(socket.getInputStream());
        os = new ObjectOutputStream(socket.getOutputStream());
        String nm = null;
        String cr = null;
        try {
            cr = (String) is.readObject();
            System.out.println(cr);
            nm = (String) is.readObject();
            System.out.println(nm);
        } catch (ClassNotFoundException e) {
            System.err.println("Wrong name.");
            e.printStackTrace();
            this.socket.close();
        }
        chatRoom = cr;
        name = nm;
    }

    @Override
    public void run(){
        Map<String,ObjectOutputStream> tmp = null;
        if(ChatServer.chatRooms.entrySet().stream().filter(item -> item.getKey().equals(chatRoom)).toArray().length == 1) {
            tmp = ChatServer.chatRooms.get(this.chatRoom);
        } else {
            tmp = new HashMap<>();
            ChatServer.chatRooms.put(this.chatRoom, tmp);
        }
            tmp.put(name, os);
            try{
                while(!socket.isClosed()){
                    Message msg = (Message) is.readObject();
                    System.out.println(msg.toString());
                    if(msg != null && msg.getChatRoom().equals(this.chatRoom)){
                        if(msg.getPrivateMsg()){
                            tmp.entrySet().stream().filter(item -> msg.getRecipient().equals(item.getKey())).
                                    forEach(item ->
                            {
                                try {
                                    item.getValue().writeObject(msg);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            tmp.forEach((key, value) -> {
                                try {
                                    value.writeObject(msg);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                }
                os.close();
                is.close();
                socket.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (ClassNotFoundException cnfe){
                cnfe.printStackTrace();
                System.exit(1);
            } finally {
                tmp.remove(name, os);
            }
        }


}
