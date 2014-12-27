package com.simplerecipemanager.resources;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.simplerecipemanager.core.Recipe;

@Path("/recipes")
public class RecipeResource {

	public static final String FORMAT_JSON = "JSON";
	public static final String FORMAT_YAML = "YAML";
	private final String template;

	public RecipeResource(String template) {
		this.template = template;
	}

	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	public Recipe getRecipeForId(@QueryParam("id") String id,
			@QueryParam("format") Optional<String> format) {
		return getRecipeForId(id, format.or(FORMAT_JSON));
	}

	private Recipe getRecipeForId(String id, String format) {
		Recipe r = new Recipe();
		r.setName("lulz");
		r.setId(UUID.randomUUID().toString());
		return r;
	}

}
