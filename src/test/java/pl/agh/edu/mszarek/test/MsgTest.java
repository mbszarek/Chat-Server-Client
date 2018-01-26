package pl.agh.edu.mszarek.test;

import org.junit.Test;
import static org.junit.Assert.*;
import pl.agh.edu.mszarek.model.Message;

public class MsgTest {

    @Test
    public void testPrivateMsg(){
        Message msg = new Message("a", "a", "@a abc");
        assertEquals(msg.getPrivateMsg(), true);
        assertEquals(msg.getAuthor(), "a");
        assertEquals(msg.getChatRoom(), "a");
    }

    @Test
    public void testNotEquals(){
        Message msg = new Message("a", "a", "@a abc");
        Message msg2 = new Message("a", "a", "a abc");
        assertNotEquals(msg.toString(), msg2.toString());
    }
}
