package com.simplerecipemanager;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.simplerecipemanager.configuration.SrmApiConfiguration;
import com.simplerecipemanager.health.ConnectionHealthCheck;
import com.simplerecipemanager.resources.HealthCheckResource;
import com.simplerecipemanager.resources.RecipeResource;

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
		final RecipeResource reipceResource = new RecipeResource("YOLO");
		environment.jersey().register(reipceResource);

		ConnectionHealthCheck connHC = new ConnectionHealthCheck();
		environment.healthChecks().register("Connection", connHC);
		
		final HealthCheckResource hcR = new HealthCheckResource(environment.healthChecks());
		environment.jersey().register(hcR);
	}

}
