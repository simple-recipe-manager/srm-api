package com.simplerecipemanager.health;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.codahale.metrics.health.HealthCheck;
import com.simplerecipemanager.core.Recipe;

public class ConnectionHealthCheck extends HealthCheck {

	private DynamoDBMapper mapper;

	public ConnectionHealthCheck(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	protected Result check() throws Exception {
		List<Recipe> scanResult = mapper.scan(Recipe.class,
				new DynamoDBScanExpression());
		if (scanResult != null) {
			return Result.unhealthy("Scan result was null");
		}
		return Result.healthy("Dynamo was healthy");
	}

}
