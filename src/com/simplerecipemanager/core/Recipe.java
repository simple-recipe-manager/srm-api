package com.simplerecipemanager.core;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplerecipemanager.db.UUIDMarshaller;

@DynamoDBTable(tableName = Recipe.TABLE_NAME)
public class Recipe {

	public static final String TABLE_NAME = "Recipes";

	private UUID recipe_uuid;
	private String recipe_name;
	private OvenFan oven_fan; // Off, Low, High
	private OvenTemp oven_temp;
	private long oven_time;
	private Map<Yield, IngredientAndAmount> ingredients;
	private Note notes;
	private SourceBook source_book;
	private List<Author> source_authors;
	private URL source_url;
	private List<Step> steps;
	private List<URL> imageURLs;
	private URL defaultImageURL;

	public Recipe() {

	}

	@DynamoDBHashKey
	@DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
	@JsonProperty(required = true)
	public UUID getRecipe_uuid() {
		return recipe_uuid;
	}

	public void setRecipe_uuid(UUID recipe_uuid) {
		this.recipe_uuid = recipe_uuid;
	}

	public String getRecipe_name() {
		return recipe_name;
	}

	public void setRecipe_name(String recipe_name) {
		this.recipe_name = recipe_name;
	}

	public OvenFan getOven_fan() {
		return oven_fan;
	}

	public void setOven_fan(OvenFan oven_fan) {
		this.oven_fan = oven_fan;
	}

	public long getOven_time() {
		return oven_time;
	}

	public void setOven_time(long oven_time) {
		this.oven_time = oven_time;
	}

	@JsonIgnore
	public Map<Yield, IngredientAndAmount> getIngredients() {
		return ingredients;
	}

	@JsonIgnore
	public void setIngredients(Map<Yield, IngredientAndAmount> ingredients) {
		this.ingredients = ingredients;
	}

	public SourceBook getSource_book() {
		return source_book;
	}

	public void setSource_book(SourceBook source_book) {
		this.source_book = source_book;
	}

	public Set<Yield> getYields() {
		return this.ingredients.keySet();
	}

	public List<URL> getImageURLs() {
		return imageURLs;
	}

	public void setImageURLs(List<URL> imageURLs) {
		this.imageURLs = imageURLs;
	}

	public URL getDefaultImageURL() {
		return defaultImageURL;
	}

	public void setDefaultImageURL(URL defaultImageURL) {
		this.defaultImageURL = defaultImageURL;
	}

	public OvenTemp getOven_temp() {
		return oven_temp;
	}

	public void setOven_temp(OvenTemp oven_temp) {
		this.oven_temp = oven_temp;
	}

	public List<Author> getSource_authors() {
		return source_authors;
	}

	public void setSource_authors(List<Author> source_authors) {
		this.source_authors = source_authors;
	}

	public URL getSource_url() {
		return source_url;
	}

	public void setSource_url(URL source_url) {
		this.source_url = source_url;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public Note getNotes() {
		return notes;
	}

	public void setNotes(Note notes) {
		this.notes = notes;
	}
}
