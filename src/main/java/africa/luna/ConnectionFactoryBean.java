package africa.luna;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsConstants;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.ibm.msg.client.wmq.common.CommonConstants;
import io.quarkus.arc.DefaultBean;
import org.apache.activemq.artemis.jms.client.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Created by loyiso on 2020/04/25.
 */
@ApplicationScoped
public class ConnectionFactoryBean {

    @Inject
    MQConfig config;
//    @Produces
//    ConnectionFactory factory() {
//        return new ActiveMQJMSConnectionFactory("tcp://localhost:61616","quarkus", "quarkus");
//    }

    @Produces
    @DefaultBean
    public ConnectionFactory connectionFactory() throws JMSException {
        JmsFactoryFactory ff;
        JmsConnectionFactory factory;
        try {
            // Get a new JMSConnectionFactory
            ff = JmsFactoryFactory.getInstance(JmsConstants.WMQ_PROVIDER);
            factory = ff.createConnectionFactory();
            // Always work in TCP/IP client mode
            factory.setIntProperty(CommonConstants.WMQ_CONNECTION_MODE, CommonConstants.WMQ_CM_CLIENT);
            // Now set the properties in this ConnectionFactory from the config.
            factory.setStringProperty(CommonConstants.WMQ_HOST_NAME, config.getHostname().get());
            factory.setIntProperty(CommonConstants.WMQ_PORT, Integer.parseInt(config.getPort().get()));
            factory.setStringProperty(CommonConstants.WMQ_CHANNEL, config.getChannel().get());
            factory.setStringProperty(CommonConstants.WMQ_QUEUE_MANAGER, "QM1"/*config.getQueueManager().get()*/);
            factory.setStringProperty(WMQConstants.USERID,config.getUser().get());
            factory.setStringProperty(WMQConstants.PASSWORD,config.getPassword().get());
            System.out.println(factory.entrySet());
        }
        catch (JMSException je) {
            // Something went wrong. Either handle it here or throw it on.
            je.printStackTrace();
            throw je;
        }
        return factory;
    }

}
