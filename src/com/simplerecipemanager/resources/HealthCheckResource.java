package com.simplerecipemanager.resources;

import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;

@Path("/healthcheck")
public class HealthCheckResource {

	private HealthCheckRegistry registry;

	public HealthCheckResource(HealthCheckRegistry hcr) {

	}

	@GET
	public Response returnHealthCheck() {
		Map<String, Result> hcResults = registry.runHealthChecks();
		for (Entry<String, Result> r : hcResults.entrySet()) {
			if (!r.getValue().isHealthy()) {
				return Response.serverError().entity(r.getValue().getMessage())
						.build();
			}
		}
		return Response.ok().build();
	}

}
