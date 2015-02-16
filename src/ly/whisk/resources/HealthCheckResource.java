package ly.whisk.resources;

import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;

@Path("/healthcheck")
public class HealthCheckResource {

	private HealthCheckRegistry registry;

	public HealthCheckResource(HealthCheckRegistry hcr) {
		this.registry = hcr;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Timed
	public Response returnHealthCheck() {
		Map<String, Result> hcResults = registry.runHealthChecks();
		int fails = 0;
		StringBuilder failureMessage = new StringBuilder(); 
				for (Entry<String, Result> r : hcResults.entrySet()) {
			if (!r.getValue().isHealthy()) {
				failureMessage.append(String.format("%s message was %s", r.getKey(), r.getValue().getMessage()));
			}
		}
		if (fails == 0) {
			return Response.ok().entity("OK").build();
		} else {
			return Response.serverError().entity(failureMessage.toString()).build();
		}
	}

}
