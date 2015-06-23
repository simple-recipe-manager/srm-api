package ly.whisk.resources;

import ly.whisk.storage.Recipe;
import ly.whisk.storage.helper.RecipeStorageHelper;

import org.apache.commons.lang3.StringUtils;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.model.Hit;
import com.amazonaws.services.cloudsearchdomain.model.SearchRequest;
import com.amazonaws.services.cloudsearchdomain.model.SearchResult;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class EchoResource implements Speechlet {

	private RecipeStorageHelper helper;
	private AmazonCloudSearchDomain search;

	public EchoResource(DynamoDBMapper mapper,
			AmazonCloudSearchDomain amazonCloudSearchDomain) {
		this.helper = new RecipeStorageHelper(mapper);
		this.search = amazonCloudSearchDomain;
	}

	@Override
	public SpeechletResponse onIntent(IntentRequest arg0, Session arg1)
			throws SpeechletException {
		Intent intent = arg0.getIntent();
		if (arg1.isNew()) {
			return getWelcomeResponse();
		} else if (intent != null && intent.getName().equals("FindRecipe")) {
			return findRecipeByName(intent.getSlot("Recipe"));
		} else {
			throw new SpeechletException("Invalid intent");
		}
	}

	private SpeechletResponse findRecipeByName(Slot slot) {
		SearchResult result = this.search.search(new SearchRequest()
				.withQuery(slot.getValue()));
		if (result.getHits().getFound() > 0) {
			for (Hit hit : result.getHits().getHit()) {
				String id = hit.getId();
				if (id == null || StringUtils.isBlank(id)) {
					continue;
				}
				Recipe found = helper.getRecipeForId(id);
				if (found == null) {
					continue;
				}
				return buildRecipeSpeechletResponse(found);
			}
		}
		return buildSpeechletResponse(
				"No results",
				"Sorry, I couldn't find any recipes that matched \""
						+ slot.getValue() + "\".", false);
	}

	private SpeechletResponse buildRecipeSpeechletResponse(Recipe found) {
		SimpleCard card = new SimpleCard();

		card.setTitle(String.format("SessionSpeechlet - %s",
				found.getRecipe_name()));
		card.setContent(String.format("SessionSpeechlet - %s", found.getNotes()));

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText("I found a recipe, \"" + found.getRecipe_name() + "\".");

		// Create the speechlet response.
		SpeechletResponse response = new SpeechletResponse();
		response.setShouldEndSession(false);
		response.setOutputSpeech(speech);
		response.setCard(card);
		return response;
	}

	@Override
	public SpeechletResponse onLaunch(LaunchRequest arg0, Session arg1)
			throws SpeechletException {
		return getWelcomeResponse();
	}

	@Override
	public void onSessionEnded(SessionEndedRequest arg0, Session arg1)
			throws SpeechletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSessionStarted(SessionStartedRequest arg0, Session arg1)
			throws SpeechletException {
	}

	private SpeechletResponse getWelcomeResponse() {
		String speechOutput = "Hello, welcome to Whiskly. You can say things like \"Let's make cake\" or \"Find me a recipe for fried chicken\"";
		return buildSpeechletResponse("Welcome", speechOutput, false);
	}

	/**
	 * Creates and returns the visual and spoken response with shouldEndSession
	 * flag
	 *
	 * @param title
	 *            title for the companion application home card
	 * @param output
	 *            output content for speech and companion application home card
	 * @param shouldEndSession
	 *            should the session be closed
	 * @return SpeechletResponse spoken and visual response for the given input
	 */
	private SpeechletResponse buildSpeechletResponse(final String title,
			final String output, final boolean shouldEndSession) {
		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle(String.format("%s", title));
		card.setContent(String.format("%s", output));

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(output);

		// Create the speechlet response.
		SpeechletResponse response = new SpeechletResponse();
		response.setShouldEndSession(shouldEndSession);
		response.setOutputSpeech(speech);
		response.setCard(card);
		return response;
	}
}
