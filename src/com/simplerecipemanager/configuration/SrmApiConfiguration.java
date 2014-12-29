package com.simplerecipemanager.configuration;

import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SrmApiConfiguration extends Configuration {

	@Valid
	@NotNull
	private DynamoDBMapperFactory mapperFactory = new DynamoDBMapperFactory();

	@JsonProperty("mapperFactory")
	public DynamoDBMapperFactory getMapperFactory() {
		return mapperFactory;
	}

	@JsonProperty("mapperFactory")
	public void setMapperFactory(DynamoDBMapperFactory mapperFactory) {
		this.mapperFactory = mapperFactory;
	}

}
