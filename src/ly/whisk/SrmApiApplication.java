package ly.whisk;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import ly.whisk.configuration.SrmApiConfiguration;
import ly.whisk.health.ConnectionHealthCheck;
import ly.whisk.resources.EchoResource;
import ly.whisk.resources.HealthCheckResource;
import ly.whisk.resources.IndexResource;
import ly.whisk.resources.RecipesResource;
import ly.whisk.resources.SearchResource;

import org.eclipse.jetty.servlet.ServletHolder;

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
		arg0.addBundle(new ViewBundle<SrmApiConfiguration>());

	}

	@Override
	public void run(SrmApiConfiguration configuration, Environment environment)
			throws Exception {

		final RecipesResource reipceResource = new RecipesResource(
				configuration.getMapperFactory().build(environment));
		environment.jersey().register(reipceResource);

		environment.getApplicationContext()
				.addServlet(
						new ServletHolder(createServlet(new EchoResource(
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

		final IndexResource index = new IndexResource();
		environment.jersey().register(index);

	}

	private SpeechletServlet createServlet(final Speechlet speechlet) {
		SpeechletServlet servlet = new SpeechletServlet();
		servlet.setSpeechlet(speechlet);
		return servlet;
	}
}
