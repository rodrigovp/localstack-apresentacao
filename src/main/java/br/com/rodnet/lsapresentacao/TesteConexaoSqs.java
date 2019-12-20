package br.com.rodnet.lsapresentacao;

import java.net.URI;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

public class TesteConexaoSqs {

	public static void main(String... args){
		SqsClient client = SqsClient.builder()
				.region(Region.US_EAST_1)
				.endpointOverride(URI.create("http://localhost:4576"))
				.build();

		String queueUrl = "http://localhost:4576/queue/teste";

		ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
				.queueUrl(queueUrl)
				.waitTimeSeconds(5)
				.maxNumberOfMessages(5)
				.build();

		while (true) {
			client.receiveMessage(receiveMessageRequest)
					.messages()
					.forEach( message -> {
						System.out.println(message.body());
					});
		}
	}
}
