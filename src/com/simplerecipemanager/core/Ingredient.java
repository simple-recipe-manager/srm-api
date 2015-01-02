package com.simplerecipemanager.core;

import java.util.List;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.simplerecipemanager.db.UUIDMarshaller;

@DynamoDBTable(tableName = Ingredient.TABLE_NAME)
public class Ingredient {

	public static final String TABLE_NAME = "Ingredients";

	private UUID ingredientId;
	private String name;
	private List<Ingredient> substitutions;
	private List<ProcessingTag> processingTags;
	private Note notes;
	private String usda_num;

	@DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
	@DynamoDBHashKey
	public UUID getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(UUID ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredient> getSubstitutions() {
		return substitutions;
	}

	public void setSubstitutions(List<Ingredient> substitutions) {
		this.substitutions = substitutions;
	}

	public List<ProcessingTag> getProcessingTags() {
		return processingTags;
	}

	public void setProcessingTags(List<ProcessingTag> processingTags) {
		this.processingTags = processingTags;
	}

	public Note getNotes() {
		return notes;
	}

	public void setNotes(Note notes) {
		this.notes = notes;
	}

	public String getUsda_num() {
		return usda_num;
	}

	public void setUsda_num(String usda_num) {
		this.usda_num = usda_num;
	}

}
