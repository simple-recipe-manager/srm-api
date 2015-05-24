package ly.whisk;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ly.whisk.configuration.SrmApiConfiguration;
import ly.whisk.health.ConnectionHealthCheck;
import ly.whisk.resources.EchoApi;
import ly.whisk.resources.HealthCheckResource;
import ly.whisk.resources.RecipesApi;
import ly.whisk.resources.SearchResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import com.bazaarvoice.dropwizard.assets.ConfiguredAssetsBundle;

public class SrmApiApplication extends Application<SrmApiConfiguration> {
	public static void main(String[] args) throws Exception {
		new SrmApiApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<SrmApiConfiguration> arg0) {
		arg0.addBundle(new ConfiguredAssetsBundle("/assets/", "/"));
	}

	@Override
	public void run(SrmApiConfiguration configuration, Environment environment)
			throws Exception {

		environment.jersey().setUrlPattern("/v1/*");
		final RecipesApi reipceResource = new RecipesApi(configuration
				.getMapperFactory().build(environment));
		environment.jersey().register(reipceResource);

		environment.getApplicationContext()
				.addServlet(
						new ServletHolder(createServlet(new EchoApi(
								configuration.getMapperFactory().build(
										environment), configuration
										.getCloudSearchFactory()
										.buildNameSearch(environment)))),
						"/echo");

		final SearchResource searchResource = new SearchResource(configuration
				.getCloudSearchFactory().buildNameSearch(environment));
		environment.jersey().register(searchResource);

		final ConnectionHealthCheck connHC = new ConnectionHealthCheck(
				configuration.getMapperFactory().build(environment));
		environment.healthChecks().register("Connection", connHC);

		final HealthCheckResource hcR = new HealthCheckResource(
				environment.healthChecks());
		environment.jersey().register(hcR);

	}

	private SpeechletServlet createServlet(final Speechlet speechlet) {
		SpeechletServlet servlet = new SpeechletServlet();
		servlet.setSpeechlet(speechlet);
		return servlet;
	}
}
