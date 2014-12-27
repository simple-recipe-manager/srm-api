package com.simplerecipemanager.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {
	private String id;
	private String name;

	public Recipe() {

	}

	@JsonProperty
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
