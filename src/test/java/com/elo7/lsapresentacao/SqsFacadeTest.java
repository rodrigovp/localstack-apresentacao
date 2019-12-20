package com.elo7.lsapresentacao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.rodnet.lsapresentacao.SqsFacade;
import cloud.localstack.docker.LocalstackDocker;
import cloud.localstack.docker.LocalstackDockerTestRunner;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.Message;

@RunWith(LocalstackDockerTestRunner.class)
@LocalstackDockerProperties(randomizePorts = true, services = {"sqs"})
public class SqsFacadeTest {

	private final SqsFacade subject = new SqsFacade(URI.create(LocalstackDocker.INSTANCE.getEndpointSQS()));

	@Test
	public void createQueue() {
		String queueName = "uma_fila";
		CreateQueueResponse queue = subject.createQueue(queueName);
		String endpointSqs = LocalstackDocker.INSTANCE.getEndpointSQS();
		
		assertThat(queue.queueUrl(), is(equalTo(endpointSqs+"/queue/"+queueName)));
	}

	@Test
	public void sendAndReceiveMessage() {
		CreateQueueResponse queue = subject.createQueue("sendMessage");
		String testMessage = "Esta é uma mensagem de teste";

		subject.sendMessage(queue.queueUrl(), testMessage);

		List<Message> messages = subject.receiveMessage(queue.queueUrl());

		assertThat(messages.size(), is(equalTo(1)));
		assertThat(messages.get(0).body(), is(equalTo(testMessage)));
	}

}