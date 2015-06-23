package ly.whisk.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ly.whisk.views.IndexView;

import com.codahale.metrics.annotation.Timed;

@Path("/")
public class IndexResource {

	public IndexResource() {

	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Timed
	public IndexView getRoot() {
		return new IndexView();
	}

}
