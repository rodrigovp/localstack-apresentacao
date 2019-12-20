package br.com.rodnet.lsapresentacao;

import java.net.URI;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

public class S3Facade {

	private S3Client s3;
	private Region region;

	public S3Facade(final URI endpoint) {
		this.region = Region.US_EAST_1;
		this.s3 = S3Client.builder()
				.region(region)
				.endpointOverride(endpoint)
				.build();
	}

	public CreateBucketResponse createBucket(final String bucketName) {
		CreateBucketConfiguration bucketConfiguration = CreateBucketConfiguration.builder()
				.locationConstraint(region.id())
				.build();
		CreateBucketRequest request = CreateBucketRequest.builder()
				.bucket(bucketName)
				.createBucketConfiguration(bucketConfiguration)
				.build();
		return s3.createBucket(request);
	}

	public ListBucketsResponse listBuckets() {
		return s3.listBuckets();
	}
}
