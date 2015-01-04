package com.simplerecipemanager.core;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.simplerecipemanager.db.RemoteTable;
import com.simplerecipemanager.db.RemotedTableMarshaller;

public class IngredientAndAmount {
	private Ingredient ingredient;
	private Amount amount;

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	@RemoteTable
	@DynamoDBMarshalling(marshallerClass = RemotedTableMarshaller.class)
	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

}
