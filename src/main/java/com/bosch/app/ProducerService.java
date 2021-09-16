package com.bosch.app;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("producer")
public class ProducerService {

	private final JmsTemplate jmsTemplate;
	private final JmsTemplate topicJmsTemplate;

	public ProducerService(@Qualifier("jmsTemplate") JmsTemplate jmsTemplate,
						   @Qualifier("topicJmsTemplate") JmsTemplate topicJmsTemplate) {
		this.jmsTemplate = jmsTemplate;
		this.topicJmsTemplate=topicJmsTemplate;
	}

	@Value("#{'${solace.queue.list}'.split(',')}")
	private List<String> queueName;

	@Scheduled(fixedRate = 5000)
	public void sendEvent() throws Exception {
		String msg = "Hello World " + System.currentTimeMillis();
		System.out.println("==========SENDING MESSAGE========== " + msg);
		jmsTemplate.convertAndSend(queueName.get(0), msg);
	}

}
