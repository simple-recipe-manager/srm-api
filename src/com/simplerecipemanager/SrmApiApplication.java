package com.simplerecipemanager;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.simplerecipemanager.configuration.SrmApiConfiguration;
import com.simplerecipemanager.health.ConnectionHealthCheck;
import com.simplerecipemanager.resources.HealthCheckResource;
import com.simplerecipemanager.resources.RecipesResource;

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
	}

}
