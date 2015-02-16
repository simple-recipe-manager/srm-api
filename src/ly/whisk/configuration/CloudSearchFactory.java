package ly.whisk.configuration;

import io.dropwizard.setup.Environment;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomainClient;

public class CloudSearchFactory {

	private AWSCredentialsProviderChain chain;
	private String endpoint;

	public CloudSearchFactory(AWSCredentialsProviderChain chain, String endpoint) {
		this.chain = chain;
		this.endpoint = endpoint;
	}

	public AmazonCloudSearchDomain buildNameSearch(Environment environment) {
		AmazonCloudSearchDomainClient client = new AmazonCloudSearchDomainClient(
				this.chain);
		client.setEndpoint(this.endpoint);
		return client;
	}
}
