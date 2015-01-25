package com.simplerecipemanager.core;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simplerecipemanager.db.OvenFanMarshaller;
import com.simplerecipemanager.db.OvenTempMarshaller;
import com.simplerecipemanager.db.RemoteTable;
import com.simplerecipemanager.db.RemotedTableMarshaller;
import com.simplerecipemanager.db.RemotedTableSetMarhsaller;
import com.simplerecipemanager.db.URLMarshaller;

@DynamoDBTable(tableName = Recipe.TABLE_NAME)
public class Recipe {

	public static final String TABLE_NAME = "Recipes";
	public static final String HASH_KEY_NAME = "recipe_uuid";

	private UUID id;
	private String recipe_name;
	private OvenFan oven_fan; // Off, Low, High
	private OvenTemp oven_temp;
	private long oven_time;
	private Set<Yield> yields;
	private Note notes;
	private SourceBook source_book;
	private Set<Author> source_authors;
	private URL source_url;
	private Set<Step> steps;
	private Set<URL> imageURLs;
	private URL defaultImageURL;
	private Map<String, Set<String>> yieldSets;
	private Set<IngredientAndAmount> allIngredients;
	private long prepTime;
	private long cookingTime;

	public Recipe() {
		this.allIngredients = new HashSet<>();
	}

	@DynamoDBHashKey(attributeName = HASH_KEY_NAME)
	public String getId() {
		return id.toString();
	}

	public void setId(String id) {
		this.id = UUID.fromString(id);
	}

	public String getRecipe_name() {
		return recipe_name;
	}

	public void setRecipe_name(String recipe_name) {
		this.recipe_name = recipe_name;
	}

	@DynamoDBMarshalling(marshallerClass = OvenFanMarshaller.class)
	public OvenFan getOven_fan() {
		return oven_fan;
	}

	public void setOven_fan(OvenFan oven_fan) {
		this.oven_fan = oven_fan;
	}

	public long getOven_time() {
		return oven_time;
	}

	public void setOven_time(long oven_time) {
		this.oven_time = oven_time;
	}

	private IngredientAndAmount ingrAndAmountForId(String id) {
		for (IngredientAndAmount ia : this.allIngredients) {
			if (ia.getId().equals(id)) {
				return ia;
			}
		}
		return null;
	}

	@DynamoDBIgnore
	public Map<Yield, Set<IngredientAndAmount>> getIngredients() {
		Map<Yield, Set<IngredientAndAmount>> toReturn = new HashMap<Yield, Set<IngredientAndAmount>>();
		for (Yield y : this.yields) {
			Set<String> ingrdsAndAmountForYield = this.yieldSets.get(y.getId());
			if (ingrdsAndAmountForYield == null) {
				continue;
			}
			Set<IngredientAndAmount> forYield = new HashSet<IngredientAndAmount>();
			for (String ingrAndAmountId : ingrdsAndAmountForYield) {
				forYield.add(ingrAndAmountForId(ingrAndAmountId));
			}
			toReturn.put(y, forYield);
		}
		return toReturn;
	}

	public void setIngredients(Map<Yield, Set<IngredientAndAmount>> ingrs) {
		// no-op
	}

	@RemoteTable
	@DynamoDBMarshalling(marshallerClass = RemotedTableMarshaller.class)
	public SourceBook getSource_book() {
		return source_book;
	}

	public void setSource_book(SourceBook source_book) {
		this.source_book = source_book;
	}

	public Set<URL> getImageURLs() {
		return imageURLs;
	}

	public void setImageURLs(Set<URL> imageURLs) {
		this.imageURLs = imageURLs;
	}

	@DynamoDBMarshalling(marshallerClass = URLMarshaller.class)
	public URL getDefaultImageURL() {
		return defaultImageURL;
	}

	public void setDefaultImageURL(URL defaultImageURL) {
		this.defaultImageURL = defaultImageURL;
	}

	@DynamoDBMarshalling(marshallerClass = OvenTempMarshaller.class)
	public OvenTemp getOven_temp() {
		return oven_temp;
	}

	public void setOven_temp(OvenTemp oven_temp) {
		this.oven_temp = oven_temp;
	}

	@RemoteTable(inflationClass = Author.class)
	@DynamoDBMarshalling(marshallerClass = RemotedTableSetMarhsaller.class)
	public Set<Author> getSource_authors() {
		return source_authors;
	}

	public void setSource_authors(Set<Author> source_authors) {
		this.source_authors = source_authors;
	}

	@DynamoDBMarshalling(marshallerClass = URLMarshaller.class)
	public URL getSource_url() {
		return source_url;
	}

	public void setSource_url(URL source_url) {
		this.source_url = source_url;
	}

	public void setSteps(Set<Step> steps) {
		this.steps = steps;
	}

	@RemoteTable(inflationClass = Step.class)
	@DynamoDBMarshalling(marshallerClass = RemotedTableSetMarhsaller.class)
	public Set<Step> getSteps() {
		return steps;
	}

	@RemoteTable
	@DynamoDBMarshalling(marshallerClass = RemotedTableMarshaller.class)
	public Note getNotes() {
		return notes;
	}

	public void setNotes(Note notes) {
		this.notes = notes;
	}

	@DynamoDBMarshalling(marshallerClass = RemotedTableSetMarhsaller.class)
	@RemoteTable(inflationClass = Yield.class)
	public Set<Yield> getYields() {
		return yields;
	}

	public void setYields(Set<Yield> yields) {
		this.yields = yields;
	}

	@JsonIgnore
	public Map<String, Set<String>> getYieldSets() {
		Map<String, Set<String>> tr = new HashMap<>();
		for (Entry<String, Set<String>> entry : this.yieldSets.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				tr.put(entry.getKey(), entry.getValue());
			}
		}
		return tr;
	}

	public void setYieldSets(Map<String, Set<String>> yieldSets) {
		this.yieldSets = yieldSets;
	}

	public void addIngredientAndAmountForYeild(IngredientAndAmount iaa, Yield y) {
		if (allIngredients == null) {
			allIngredients = new HashSet<>();
		}
		this.allIngredients.add(iaa);
		if (this.yieldSets == null) {
			this.yieldSets = new HashMap<>();
		}
		if (this.yieldSets.get(y.getId()) == null) {
			this.yieldSets.put(y.getId(), new HashSet<String>());
		}
		this.yieldSets.get(y.getId()).add(iaa.getId());
	}

	@DynamoDBMarshalling(marshallerClass = RemotedTableSetMarhsaller.class)
	@RemoteTable(inflationClass = IngredientAndAmount.class)
	@JsonIgnore
	public Set<IngredientAndAmount> getAllIngredients() {
		return allIngredients;
	}

	public void setAllIngredients(Set<IngredientAndAmount> allIngredients) {
		this.allIngredients = allIngredients;
	}

	public long getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(long prepTime) {
		this.prepTime = prepTime;
	}

	public long getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(long cookingTime) {
		this.cookingTime = cookingTime;
	}
}
