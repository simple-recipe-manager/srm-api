package com.simplerecipemanager.configuration;

import io.dropwizard.setup.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDBMapperFactory {

	private AWSCredentialsProvider stringProvider;

	public DynamoDBMapperFactory(String accessKey, String secretKey) {
		this.stringProvider = new AWSCredentialsProvider() {

			@Override
			public void refresh() {
			}

			@Override
			public AWSCredentials getCredentials() {
				return new AWSCredentials() {

					@Override
					public String getAWSSecretKey() {
						return secretKey;
					}

					@Override
					public String getAWSAccessKeyId() {
						return accessKey;
					}
				};
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
