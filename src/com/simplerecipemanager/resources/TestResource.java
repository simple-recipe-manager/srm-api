package com.simplerecipemanager.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.codahale.metrics.annotation.Timed;
import com.simplerecipemanager.core.Amount;
import com.simplerecipemanager.core.Ingredient;
import com.simplerecipemanager.core.IngredientAndAmount;
import com.simplerecipemanager.core.Note;
import com.simplerecipemanager.core.OvenFan;
import com.simplerecipemanager.core.OvenTemp;
import com.simplerecipemanager.core.Recipe;
import com.simplerecipemanager.core.Step;
import com.simplerecipemanager.core.TemperatureUnit;
import com.simplerecipemanager.core.UnitTag;
import com.simplerecipemanager.core.Yield;

@Path("/test")
public class TestResource {

	private DynamoDBMapper mapper;

	public TestResource(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}

	@GET
	@Timed
	public Response doStuff() {
		Recipe toAdd = new Recipe();
		toAdd.setRecipe_uuid(UUID.randomUUID());
		Note recipeNote = new Note();
		recipeNote.setId(UUID.randomUUID());
		recipeNote.setNote("Hello, I'm a note!");
		toAdd.setNotes(recipeNote);
		toAdd.setOven_fan(OvenFan.LOW);

		OvenTemp t = new OvenTemp();
		t.setAmount(450);
		t.setUnit(TemperatureUnit.FAHRENHEIT);

		toAdd.setOven_temp(t);

		toAdd.setOven_time(40);

		toAdd.setRecipe_name("Cookies");

		Map<Yield, List<IngredientAndAmount>> ingredients = new HashMap<Yield, List<IngredientAndAmount>>();

		Yield y = new Yield();
		y.setServes(40);
		UnitTag tag = new UnitTag();
		tag.setId(UUID.randomUUID());
		tag.setTag("cookies");
		y.setUnit(tag);

		List<IngredientAndAmount> ingredsForYield = new ArrayList<IngredientAndAmount>();
		IngredientAndAmount ingA = new IngredientAndAmount();
		Amount amount = new Amount();
		amount.setValue(1);
		UnitTag cup = new UnitTag();
		cup.setId(UUID.randomUUID());
		cup.setTag("cup");
		amount.setUnit(cup);

		ingA.setAmount(amount);

		Ingredient flour = new Ingredient();
		flour.setIngredientId(UUID.randomUUID());
		flour.setName("flour");
		Note n = new Note();
		n.setId(UUID.randomUUID());
		n.setNote("Don't sift me bro");
		flour.setNotes(n);
		ingA.setIngredient(flour);

		ingredsForYield.add(ingA);
		ingredients.put(y, ingredsForYield);

		toAdd.setIngredients(ingredients);

		Set<Step> steps = new HashSet<Step>();
		Step s1 = new Step();
		s1.setId(UUID.randomUUID());
		s1.setOrder(0);
		s1.setStepDetails("Pour in bowl");

		Step s2 = new Step();
		s2.setId(UUID.randomUUID());
		s2.setOrder(1);
		s2.setStepDetails("Eat");

		steps.add(s1);
		steps.add(s2);

		toAdd.setSteps(steps);

		mapper.save(toAdd);
		return Response.ok().build();

	}

}
