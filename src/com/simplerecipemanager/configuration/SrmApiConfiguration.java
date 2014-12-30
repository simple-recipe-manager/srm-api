package com.simplerecipemanager.configuration;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SrmApiConfiguration extends Configuration {

	private DynamoDBMapperFactory mapperFactory;

	private String accessKey;

	private String secretKey;

	public DynamoDBMapperFactory getMapperFactory() {
		synchronized (this) {
			if (mapperFactory == null) {
				mapperFactory = new DynamoDBMapperFactory(this.getAccessKey(),
						this.getSecretKey());
			}
		}
		return this.mapperFactory;
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

}
