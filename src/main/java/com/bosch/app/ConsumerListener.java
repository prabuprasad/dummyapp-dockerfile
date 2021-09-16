package com.bosch.app;

import com.bosch.app.influx.InfluxDbAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Profile("consumer")
public class ConsumerListener {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
    InfluxDbAccessor influxDbAccessor;

    @JmsListener(containerFactory="queueListenerContainerFactory", destination = "#{'${solace.queue.list}'.split(',')[0]}")
    public void listenToQueue1(Message message){
        Date receiveTime = new Date();

        if (message instanceof TextMessage) {
            TextMessage tm = (TextMessage) message;
            try {
                System.out.println(
                    "Message Received at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(receiveTime)
                            + " with message content of: " + tm.getText());
            } catch (JMSException e) {
                e.printStackTrace();
        }
        }
        if (message instanceof BytesMessage){
            try {
                BytesMessage byteMessage = (BytesMessage) message;
                byte[] byteData = null;
                int length = ((int) byteMessage.getBodyLength());
                byteData = new byte[length];
                byteMessage.readBytes(byteData);
                byteMessage.reset();
                String m = new String(byteData);
                influxDbAccessor.writeMeasurements(m);
                System.out.println(m);
            }catch (javax.jms.JMSException e){
                logger.error("error ",e);
            }
        }
        else {
            System.out.println(message.toString());
        }
    }

    @JmsListener(containerFactory="queueListenerContainerFactory",destination = "#{'${solace.queue.list}'.split(',')[1]}")
    public void listenToQueue2(Message message){
    	logger.info("listener called queue2 {}  ",message);

    }
//
//    @JmsListener(containerFactory="queueListenerContainerFactory",destination = "SpringTestQueue")
//    public void listenToSpringTestQueue(Message message){
//        logger.info("listener called listenToSpringTestQueue {}  ",message);
//
//    }
//
//
//    @JmsListener(containerFactory="topicListenerContainerFactory" , destination = "demo-topic",subscription = "demo-topic")
//    public void listenToDemoTopic(Message message){
//        System.out.println("topic subscribed ====> "+message);
//
//    }
}
