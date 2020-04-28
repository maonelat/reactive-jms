package africa.luna;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("")
public class ExampleResource {
    @Inject
    MessageProducer messageProducer;

    @Inject
    MessageConsumer messageConsumer;

    @GET
    @Path("/send/{message}")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@PathParam("message")String msg) {
        messageProducer.sendMessage(msg);
        return "Sent";
    }

    @GET
    @Path("/read")
    @Produces(MediaType.TEXT_PLAIN)
    public List<String> read() {
        return messageConsumer.getMessages();
    }
}