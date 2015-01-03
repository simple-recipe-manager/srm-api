package com.simplerecipemanager;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.simplerecipemanager.configuration.SrmApiConfiguration;
import com.simplerecipemanager.core.Amount;
import com.simplerecipemanager.core.Ingredient;
import com.simplerecipemanager.core.IngredientAndAmount;
import com.simplerecipemanager.core.Note;
import com.simplerecipemanager.core.OvenFan;
import com.simplerecipemanager.core.OvenTemp;
import com.simplerecipemanager.core.Recipe;
import com.simplerecipemanager.core.Step;
import com.simplerecipemanager.core.TemperatureUnit;
import com.simplerecipemanager.core.UnitTag;
import com.simplerecipemanager.core.Yield;
import com.simplerecipemanager.health.ConnectionHealthCheck;
import com.simplerecipemanager.resources.HealthCheckResource;
import com.simplerecipemanager.resources.RecipesResource;
import com.simplerecipemanager.resources.TestResource;

public class SrmApiApplication extends Application<SrmApiConfiguration> {
	public static void main(String[] args) throws Exception {
		new SrmApiApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<SrmApiConfiguration> arg0) {
	}

	@Override
	public void run(SrmApiConfiguration configuration, Environment environment)
			throws Exception {

		final RecipesResource reipceResource = new RecipesResource(
				configuration.getMapperFactory().build(environment));
		environment.jersey().register(reipceResource);

		final ConnectionHealthCheck connHC = new ConnectionHealthCheck(
				configuration.getMapperFactory().build(environment));
		environment.healthChecks().register("Connection", connHC);

		final HealthCheckResource hcR = new HealthCheckResource(
				environment.healthChecks());
		environment.jersey().register(hcR);

		final TestResource test = new TestResource(configuration
				.getMapperFactory().build(environment));
		environment.jersey().register(test);

	}
}
