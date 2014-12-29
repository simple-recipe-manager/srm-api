package com.simplerecipemanager.configuration;

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDBMapperFactory {

	public DynamoDBMapper build(Environment environment) {

		AmazonDynamoDBClient client = new AmazonDynamoDBClient(
				new InstanceProfileCredentialsProvider());
		DynamoDBMapper mapper = new DynamoDBMapper(client);
		environment.lifecycle().manage(new Managed() {
			@Override
			public void start() {
			}

			@Override
			public void stop() {
			}
		});

		return mapper;
	}
}
