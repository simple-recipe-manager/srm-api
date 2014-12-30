package com.simplerecipemanager.core;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simplerecipemanager.db.UUIDMarshaller;

@DynamoDBTable(tableName = Recipe.TABLE_NAME)
public class Recipe {

	public static final String TABLE_NAME = "Recipes";

	private UUID recipe_uuid;
	private String recipe_name;
	private OvenFan oven_fan; // Off, Low, High
	private long oven_temp;
	private long amount; // Number of degrees
	private TemperatureUnit unit; // C or F
	private long oven_time;
	private Map<Yield, Ingredient> ingredients;
	private String note;
	private SourceBook source_book;
	private Steps steps;
	private List<Yield> yields;
	private Map<String, Object> xFields;

	public Recipe() {

	}

	@DynamoDBHashKey
	@DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
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

	public long getOven_temp() {
		return oven_temp;
	}

	public void setOven_temp(long oven_temp) {
		this.oven_temp = oven_temp;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public TemperatureUnit getUnit() {
		return unit;
	}

	public void setUnit(TemperatureUnit unit) {
		this.unit = unit;
	}

	public long getOven_time() {
		return oven_time;
	}

	public void setOven_time(long oven_time) {
		this.oven_time = oven_time;
	}

	@JsonIgnore
	public Map<Yield, Ingredient> getIngredients() {
		return ingredients;
	}

	@JsonIgnore
	public void setIngredients(Map<Yield, Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public SourceBook getSource_book() {
		return source_book;
	}

	@JsonIgnore
	public void setSource_book(SourceBook source_book) {
		this.source_book = source_book;
	}

	public Steps getSteps() {
		return steps;
	}

	public void setSteps(Steps steps) {
		this.steps = steps;
	}

	@JsonIgnore
	public List<Yield> getYields() {
		return yields;
	}

	public void setYields(List<Yield> yields) {
		this.yields = yields;
	}

	@DynamoDBIgnore
	@JsonIgnore
	public Map<String, Object> getXFields() {
		return xFields;
	}

	public void setXFields(Map<String, Object> xFields) {
		this.xFields = xFields;
	}
}
