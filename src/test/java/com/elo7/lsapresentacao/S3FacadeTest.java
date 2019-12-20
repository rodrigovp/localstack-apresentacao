package com.elo7.lsapresentacao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.rodnet.lsapresentacao.S3Facade;
import cloud.localstack.docker.LocalstackDocker;
import cloud.localstack.docker.LocalstackDockerTestRunner;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

@RunWith(LocalstackDockerTestRunner.class)
@LocalstackDockerProperties(randomizePorts = true, services = {"s3"})
public class S3FacadeTest {

	private static final String BUCKET_NAME = "umbucket";

	private final S3Facade subject = new S3Facade(URI.create(LocalstackDocker.INSTANCE.getEndpointS3()));

	@Before
	public void createBucket() {
		subject.createBucket(BUCKET_NAME);
	}

	@Test
	public void createAndListBuckets(){
		ListBucketsResponse buckets = subject.listBuckets();

		assertThat(buckets.buckets().size(), is(equalTo(1)));
		assertThat(buckets.buckets().get(0).name(), is(equalTo(BUCKET_NAME)));
	}
}
