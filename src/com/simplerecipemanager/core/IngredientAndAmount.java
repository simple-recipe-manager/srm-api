package com.simplerecipemanager.core;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.simplerecipemanager.db.RemoteTable;
import com.simplerecipemanager.db.RemotedTable;
import com.simplerecipemanager.db.RemotedTableMarshaller;

@DynamoDBTable(tableName = IngredientAndAmount.TABLE_NAME)
public class IngredientAndAmount implements RemotedTable {

	public static final String TABLE_NAME = "IngredientsAndAmounts";

	private UUID id;
	private Ingredient ingredient;
	private double value;
	private UnitTag unit;

	@RemoteTable
	@DynamoDBMarshalling(marshallerClass = RemotedTableMarshaller.class)
	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	@DynamoDBHashKey
	public String getId() {
		return this.id.toString();
	}

	@Override
	public void setId(String id) {
		this.id = UUID.fromString(id);
	}

	@Override
	public String toString() {
		return this.getId();
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@RemoteTable
	@DynamoDBMarshalling(marshallerClass = RemotedTableMarshaller.class)
	public UnitTag getUnit() {
		return unit;
	}

	public void setUnit(UnitTag unit) {
		this.unit = unit;
	}
}
