package com.simplerecipemanager.resources;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.codahale.metrics.annotation.Timed;
import com.simplerecipemanager.core.Ingredient;
import com.simplerecipemanager.core.IngredientAndAmount;
import com.simplerecipemanager.core.Note;
import com.simplerecipemanager.core.OvenFan;
import com.simplerecipemanager.core.OvenTemp;
import com.simplerecipemanager.core.Recipe;
import com.simplerecipemanager.core.SourceBook;
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response doStuff() throws MalformedURLException {
		Recipe toAdd = new Recipe();
		toAdd.setId(UUID.randomUUID().toString());
		Note recipeNote = new Note();
		recipeNote.setId(UUID.randomUUID().toString());
		recipeNote.setNote("These are my favorite cookies.");
		toAdd.setNotes(recipeNote);
		toAdd.setOven_fan(OvenFan.OFF);

		OvenTemp t = new OvenTemp();
		t.setAmount(375);
		t.setUnit(TemperatureUnit.FAHRENHEIT);

		toAdd.setOven_temp(t);

		toAdd.setOven_time(10);

		toAdd.setPrepTime(15);

		toAdd.setCookingTime(10);

		toAdd.setRecipe_name("Original Nestle Toll House Chocolate Chip Cookies");

		toAdd.setSource_url(new URL(
				"https://www.verybestbaking.com/recipes/18476/original-nestle-toll-house-chocolate-chip-cookies/"));

		SourceBook book = new SourceBook();
		book.setId(UUID.randomUUID().toString());
		book.setTitle("Nestle Tollhouse Cookbook");
		toAdd.setSource_book(book);

		Set<Yield> yields = new HashSet<Yield>();

		UnitTag tag = new UnitTag();
		tag.setId(UUID.randomUUID().toString());
		tag.setTag("cookies");

		Yield y = new Yield();
		y.setServes(60);
		y.setUnit(tag);
		y.setId(UUID.randomUUID().toString());

		yields.add(y);

		toAdd.setYields(yields);

		UnitTag cup = new UnitTag();
		cup.setId(UUID.randomUUID().toString());
		cup.setTag("cup");

		UnitTag teaspoon = new UnitTag();
		teaspoon.setId(UUID.randomUUID().toString());
		teaspoon.setTag("teaspoon");

		UnitTag large = new UnitTag();
		large.setId(UUID.randomUUID().toString());
		large.setTag("large");

		IngredientAndAmount flourCups = new IngredientAndAmount();
		flourCups.setId(UUID.randomUUID().toString());
		flourCups.setValue(2.25);
		flourCups.setUnit(cup);

		Ingredient flour = new Ingredient();
		flour.setId(UUID.randomUUID().toString());
		flour.setName("flour");

		flourCups.setIngredient(flour);

		IngredientAndAmount bsTsp = new IngredientAndAmount();
		bsTsp.setId(UUID.randomUUID().toString());
		bsTsp.setUnit(teaspoon);
		bsTsp.setValue(1);

		Ingredient bakingSoda = new Ingredient();
		bakingSoda.setId(UUID.randomUUID().toString());
		bakingSoda.setName("baking soda");

		bsTsp.setIngredient(bakingSoda);

		IngredientAndAmount saltTsp = new IngredientAndAmount();
		saltTsp.setId(UUID.randomUUID().toString());
		saltTsp.setUnit(teaspoon);
		saltTsp.setValue(1);

		Ingredient salt = new Ingredient();
		salt.setId(UUID.randomUUID().toString());
		salt.setName("salt");

		saltTsp.setIngredient(salt);

		IngredientAndAmount butterCup = new IngredientAndAmount();
		butterCup.setId(UUID.randomUUID().toString());
		butterCup.setUnit(cup);
		butterCup.setValue(1);

		Ingredient butter = new Ingredient();
		butter.setName("butter");
		butter.setId(UUID.randomUUID().toString());
		butterCup.setIngredient(butter);

		toAdd.addIngredientAndAmountForYeild(flourCups, y);
		toAdd.addIngredientAndAmountForYeild(bsTsp, y);
		toAdd.addIngredientAndAmountForYeild(saltTsp, y);
		toAdd.addIngredientAndAmountForYeild(butterCup, y);

		Set<Step> steps = new HashSet<Step>();

		Step s1 = new Step();
		s1.setId(UUID.randomUUID().toString());
		s1.setOrder(0);
		s1.setStepDetails("Combine flour, baking soda and salt in small bowl.");

		Step s2 = new Step();
		s2.setId(UUID.randomUUID().toString());
		s2.setOrder(1);
		s2.setStepDetails("Beat butter, granulated sugar, brown sugar and vanilla extract in large mixer bowl until creamy.");

		Step s3 = new Step();
		s3.setId(UUID.randomUUID().toString());
		s3.setOrder(2);
		s3.setStepDetails("Add eggs, one at a time, beating well after each addition.");

		Step s4 = new Step();
		s4.setId(UUID.randomUUID().toString());
		s4.setOrder(3);
		s4.setStepDetails("Gradually beat in flour mixture.");

		Step s5 = new Step();
		s5.setId(UUID.randomUUID().toString());
		s5.setOrder(4);
		s5.setStepDetails("Stir in morsels and nuts.");

		Step s6 = new Step();
		s6.setId(UUID.randomUUID().toString());
		s6.setOrder(5);
		s6.setStepDetails("Drop by rounded tablespoon onto ungreased baking sheets.");

		steps.add(s1);
		steps.add(s2);
		steps.add(s3);
		steps.add(s4);
		steps.add(s5);
		steps.add(s6);

		toAdd.setSteps(steps);

		try {
			mapper.save(toAdd);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Response.ok(toAdd).build();

	}
}
