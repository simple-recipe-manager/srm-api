package com.simplerecipemanager.configuration;

import io.dropwizard.setup.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDBMapperFactory {

	private final AWSCredentialsProvider stringProvider;
	private final AWSCredentials stringCreds;

	public DynamoDBMapperFactory(String accessKey, String secretKey) {

		this.stringCreds = new BasicAWSCredentials(accessKey, secretKey);
		this.stringProvider = new AWSCredentialsProvider() {

			@Override
			public void refresh() {
			}

			@Override
			public AWSCredentials getCredentials() {
				return DynamoDBMapperFactory.this.stringCreds;
			}
		};
	}

	public DynamoDBMapper build(Environment environment) {
		AWSCredentialsProviderChain chain = new AWSCredentialsProviderChain(
				new InstanceProfileCredentialsProvider(), stringProvider);
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(chain);
		return new DynamoDBMapper(client);
	}
}
