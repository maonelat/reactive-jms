package africa.luna;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Session;
import java.util.Random;

/**
 * Created by loyiso on 2020/04/25.
 */
@ApplicationScoped
public class MessageProducer {

    @Inject
    ConnectionFactory connectionFactory;
//    @Inject @Channel("jms-q")
//    Emitter<String> emitter;

    public void sendMessage(String msg){
//        emitter.send(Message.of(msg));
        try {
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            context.createProducer().send(context.createQueue("DEV.QUEUE.1"), msg);
        }catch (Exception r){
            r.printStackTrace();
        }
    }

}
