package com.simplerecipemanager.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.simplerecipemanager.core.Ingredient;

public class IngredientMarshaller implements DynamoDBMarshaller<Ingredient> {

	@Override
	public String marshall(Ingredient arg0) {

	}

	@Override
	public Ingredient unmarshall(Class<Ingredient> arg0, String arg1) {
		return null;
	}

}
