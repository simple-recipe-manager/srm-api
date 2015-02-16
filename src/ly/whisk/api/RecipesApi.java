package ly.whisk.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ly.whisk.model.Recipe;
import ly.whisk.storage.helper.RecipeAdapter;
import ly.whisk.storage.helper.RecipeStorageHelper;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/recipes")
@Api(value = "/recipes", description = "the recipes API")
@Produces({ "application/json" })
public class RecipesApi {

	private RecipeStorageHelper helper;

	public RecipesApi(DynamoDBMapper mapper) {
		this.helper = new RecipeStorageHelper(mapper);
	}

	@GET
	@Path("")
	@ApiOperation(value = "Search all recipes", notes = "Returns a listing of all recipes, paged.", response = Recipe.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 0, message = "Unexpected error") })
	public Response recipesGet(
			@ApiParam(value = "The page number to give back.") @QueryParam("page") Integer page,
			@ApiParam(value = "The size of each page to give back.") @QueryParam("size") Integer size,
			@ApiParam(value = "Searches all text fields.") @QueryParam("search") String search)
			throws NotFoundException {
		// do some magic!
		return Response
				.ok()
				.entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!"))
				.build();
	}

	@POST
	@Path("")
	@ApiOperation(value = "New Recipe", notes = "Adds a new recipe to the database, adding a unique id on the way in.", response = Recipe.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "Unexpected error") })
	public Response recipesPost(@ApiParam(value = "") Recipe Recipe)
			throws NotFoundException {
		// do some magic!
		return Response
				.ok()
				.entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!"))
				.build();
	}

	@GET
	@Path("/{id}")
	@ApiOperation(value = "Single Recipe", notes = "Returns a single recipe for a given id.", response = Recipe.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "Unexpected error") })
	public Response recipesGet(
			@ApiParam(value = "Id of the recipe to fetch") @PathParam("id") String id)
			throws NotFoundException {
		Recipe loaded = RecipeAdapter.toAPIRecipe(this.helper
				.getRecipeForId(id));
		if (loaded != null) {
			return Response.ok().entity(loaded).build();
		} else {
			return Response
					.status(Status.NOT_FOUND)
					.entity(new NotFoundException(404, "No matching record.")).build();
		}
	}

}
