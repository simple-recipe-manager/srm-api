package ly.whisk.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.model.SearchRequest;
import com.amazonaws.services.cloudsearchdomain.model.SearchResult;
import com.codahale.metrics.annotation.Timed;

@Path("/search")
public class SearchResource {

	private AmazonCloudSearchDomain awsCloudSearch;

	public SearchResource(AmazonCloudSearchDomain search) {
		this.awsCloudSearch = search;
	}

	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	public SearchResult search(@QueryParam("q") String query) {
		SearchResult result = this.awsCloudSearch.search(new SearchRequest()
				.withQuery(query));
		return result;
	}
}
