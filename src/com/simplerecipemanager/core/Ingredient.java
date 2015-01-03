package com.simplerecipemanager.core;

import java.util.Set;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.simplerecipemanager.db.RemoteTable;
import com.simplerecipemanager.db.RemotedTable;
import com.simplerecipemanager.db.RemotedTableMarshaller;
import com.simplerecipemanager.db.RemotedTableSetMarhsaller;
import com.simplerecipemanager.db.UUIDMarshaller;

@DynamoDBTable(tableName = Ingredient.TABLE_NAME)
public class Ingredient implements RemotedTable {

	public static final String TABLE_NAME = "Ingredients";

	private UUID ingredientId;
	private String name;
	private Set<Ingredient> substitutions;
	private Set<ProcessingTag> processingTags;
	private Note notes;
	private String usda_num;

	@DynamoDBMarshalling(marshallerClass = UUIDMarshaller.class)
	@DynamoDBHashKey
	public UUID getId() {
		return ingredientId;
	}

	public void setId(UUID ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DynamoDBMarshalling(marshallerClass = RemotedTableSetMarhsaller.class)
	@RemoteTable(inflationClass = Ingredient.class)
	public Set<Ingredient> getSubstitutions() {
		return substitutions;
	}

	public void setSubstitutions(Set<Ingredient> substitutions) {
		this.substitutions = substitutions;
	}

	@RemoteTable(inflationClass = ProcessingTag.class)
	@DynamoDBMarshalling(marshallerClass = RemotedTableSetMarhsaller.class)
	public Set<ProcessingTag> getProcessingTags() {
		return processingTags;
	}

	public void setProcessingTags(Set<ProcessingTag> processingTags) {
		this.processingTags = processingTags;
	}

	@DynamoDBMarshalling(marshallerClass = RemotedTableMarshaller.class)
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
