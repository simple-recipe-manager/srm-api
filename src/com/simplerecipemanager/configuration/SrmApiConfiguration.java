package com.simplerecipemanager.configuration;

import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SrmApiConfiguration extends Configuration {

	@Valid
	@NotNull
	private DyanmoDBMapperFactory mapperFactory = new DyanmoDBMapperFactory();

	@JsonProperty("mapperFactory")
	public DyanmoDBMapperFactory getMapperFactory() {
		return mapperFactory;
	}

	@JsonProperty("mapperFactory")
	public void setMapperFactory(DyanmoDBMapperFactory mapperFactory) {
		this.mapperFactory = mapperFactory;
	}

}
