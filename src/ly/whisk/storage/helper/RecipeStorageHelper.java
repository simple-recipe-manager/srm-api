package ly.whisk.storage.helper;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class RecipeStorageHelper {


	private DynamoDBMapper mapper;

	public RecipeStorageHelper(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}

	public ly.whisk.storage.Recipe getRecipeForId(String id) {
		UUID toLoad = UUID.fromString(id);
		return this.mapper.load(ly.whisk.storage.Recipe.class,
				toLoad.toString());
	}

	public ly.whisk.storage.Recipe validateAndSaveRecipe(
			ly.whisk.storage.Recipe toSave, String idToSave) {
		toSave.setId(idToSave);
		this.mapper.save(toSave);
		return toSave;
	}
}
