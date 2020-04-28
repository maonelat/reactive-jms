package africa.luna;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by loyiso on 2020/04/25.
 */
@ApplicationScoped
public class MessageConsumer implements Runnable{

    @Inject
    ConnectionFactory connectionFactory;

    private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

    List<String> messages = new ArrayList();

    void onStart(@Observes StartupEvent ev) {
        System.out.println("Start Thread");
        scheduler.submit(this);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }

    public List<String> getMessages(){
        return messages;
    }

    public void run() {
        try{
            JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            JMSConsumer consumer = context.createConsumer(context.createQueue("DEV.QUEUE.1"));
            System.out.println("Waiting for Messages......."+"\n"+consumer.toString());
            while (true) {
                Message message = consumer.receive();
                System.out.println("Message received:");
                if (message == null) return;
                messages.add(message.getBody(String.class));
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

//    @Incoming("jms-q")
//    public void readMessage(String msg){
//        System.out.println("******\n\n"+msg);
//    }

}
