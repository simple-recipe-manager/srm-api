package com.simplerecipemanager;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.simplerecipemanager.configuration.SrmApiConfiguration;
import com.simplerecipemanager.health.ConnectionHealthCheck;
import com.simplerecipemanager.resources.HealthCheckResource;
import com.simplerecipemanager.resources.RecipesResource;
import com.simplerecipemanager.resources.SearchResource;
import com.simplerecipemanager.resources.TestResource;

public class SrmApiApplication extends Application<SrmApiConfiguration> {
	public static void main(String[] args) throws Exception {
		new SrmApiApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<SrmApiConfiguration> arg0) {
		arg0.addBundle(new AssetsBundle("/assets", "/"));
	}

	@Override
	public void run(SrmApiConfiguration configuration, Environment environment)
			throws Exception {

		final RecipesResource reipceResource = new RecipesResource(
				configuration.getMapperFactory().build(environment));
		environment.jersey().register(reipceResource);

		final SearchResource searchResource = new SearchResource(configuration
				.getCloudSearchFactory().buildNameSearch(environment));
		environment.jersey().register(searchResource);

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
