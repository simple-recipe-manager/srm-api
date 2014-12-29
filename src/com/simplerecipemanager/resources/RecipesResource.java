package com.simplerecipemanager.resources;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.simplerecipemanager.core.Recipe;

@Path("/recipes")
public class RecipesResource {

	public static final String FORMAT_JSON = "JSON";
	public static final String FORMAT_YAML = "YAML";
	private DynamoDBMapper mapper;

	public RecipesResource(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}

	@GET
	@Path("/{id}")
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	public Recipe getRecipeForId(@PathParam("id") String id,
			@QueryParam("format") Optional<String> format) {
		return getRecipeForId(id, format.or(FORMAT_JSON));
	}

	@POST
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	public Recipe saveNewRecipe(Recipe toSave) {
		return validateAndSaveRecipe(toSave, UUID.randomUUID());
	}

	@PUT
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	public Recipe updateExisting() {
		return null;
	}

	private Recipe getRecipeForId(String id, String format) {
		return this.mapper.load(Recipe.class, id);
	}

	private Recipe validateAndSaveRecipe(Recipe toSave, UUID idToSave) {
		toSave.setRecipe_uuid(idToSave);
		this.mapper.save(toSave);
		return toSave;
	}

}
