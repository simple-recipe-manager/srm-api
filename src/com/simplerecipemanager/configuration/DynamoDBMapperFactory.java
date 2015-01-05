package com.simplerecipemanager.configuration;

import io.dropwizard.setup.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.RemoteTableDynamoDBMapper;

public class DynamoDBMapperFactory {

	private AWSCredentialsProvider stringProvider;
	private AWSCredentials stringCreds;

	public DynamoDBMapperFactory(final String accessKey, final String secretKey) {

		if (accessKey != null && secretKey != null) {
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
	}

	public DynamoDBMapper build(Environment environment) {
		AWSCredentialsProviderChain chain = new AWSCredentialsProviderChain(
				new InstanceProfileCredentialsProvider(), stringProvider);
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(chain);
		return new RemoteTableDynamoDBMapper(client);
	}
}
