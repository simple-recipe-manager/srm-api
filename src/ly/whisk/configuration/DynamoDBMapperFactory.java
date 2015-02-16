package ly.whisk.configuration;

import io.dropwizard.setup.Environment;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.RemoteTableDynamoDBMapper;

public class DynamoDBMapperFactory {

	private AWSCredentialsProviderChain chain;

	public DynamoDBMapperFactory(AWSCredentialsProviderChain chainProvider) {
		this.chain = chainProvider;
	}

	public DynamoDBMapper build(Environment environment) {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(chain);
		return new RemoteTableDynamoDBMapper(client);
	}
}
