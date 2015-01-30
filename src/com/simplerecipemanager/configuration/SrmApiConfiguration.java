package com.simplerecipemanager.configuration;

import io.dropwizard.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SrmApiConfiguration extends Configuration {

	private DynamoDBMapperFactory mapperFactory;
	private CloudSearchFactory cloudSearchFactory;
	private String accessKey;
	private String secretKey;
	private String cloudSearchNameEndpoint;

	public DynamoDBMapperFactory getMapperFactory() {
		synchronized (this) {
			if (mapperFactory == null) {
				mapperFactory = new DynamoDBMapperFactory(getChain());
			}
		}
		return this.mapperFactory;
	}

	public CloudSearchFactory getCloudSearchFactory() {
		synchronized (this) {
			if (cloudSearchFactory == null) {
				cloudSearchFactory = new CloudSearchFactory(getChain(),
						this.cloudSearchNameEndpoint);
			}
		}
		return cloudSearchFactory;
	}

	@JsonProperty
	public String getAccessKey() {
		return accessKey;
	}

	@JsonProperty
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	@JsonProperty
	public String getSecretKey() {
		return secretKey;
	}

	@JsonProperty
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	@JsonProperty
	public String getCloudSearchNameEndpoint() {
		return cloudSearchNameEndpoint;
	}

	@JsonProperty
	public void setCloudSearchNameEndpoint(String cloudSearchNameEndpoint) {
		this.cloudSearchNameEndpoint = cloudSearchNameEndpoint;
	}

	private AWSCredentialsProviderChain getChain() {

		AWSCredentialsProvider stringProvider = null;

		if (this.getSecretKey() != null && this.getAccessKey() != null) {
			final AWSCredentials stringCreds = new BasicAWSCredentials(
					accessKey, secretKey);
			stringProvider = new AWSCredentialsProvider() {

				@Override
				public void refresh() {
				}

				@Override
				public AWSCredentials getCredentials() {
					return stringCreds;
				}
			};
		}

		return new AWSCredentialsProviderChain(
				new InstanceProfileCredentialsProvider(), stringProvider);
	}

}
