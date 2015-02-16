package ly.whisk.storage.helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ly.whisk.model.Ingredient;
import ly.whisk.model.IngredientAndAmount;
import ly.whisk.storage.Author;
import ly.whisk.storage.HACCP;
import ly.whisk.storage.Note;
import ly.whisk.storage.OvenFan;
import ly.whisk.storage.OvenTemp;
import ly.whisk.storage.ProcessingTag;
import ly.whisk.storage.Recipe;
import ly.whisk.storage.SourceBook;
import ly.whisk.storage.Step;
import ly.whisk.storage.TemperatureUnit;
import ly.whisk.storage.UnitTag;
import ly.whisk.storage.Yield;

public class RecipeAdapter {

	private RecipeAdapter() {

	}

	public static ly.whisk.storage.Recipe toStorageRecipe(
			ly.whisk.model.Recipe inRecipe) throws MalformedURLException {
		ly.whisk.storage.Recipe storage = new Recipe();
		storage.setAddedAt(inRecipe.getAdded_at());
		// TODO: Fix this
		// storage.setAllIngredients();
		storage.setCookingTime(inRecipe.getCooking_time());
		storage.setId(inRecipe.getRecipe_uuid());
		storage.setNotes(toStorageNote(inRecipe.getNotes()));
		storage.setOven_fan(toStorageOvenFan(inRecipe.getOven_fan()));
		storage.setOven_temp(toStorageOvenTemp(inRecipe.getOven_temp()));
		storage.setOven_time(inRecipe.getOven_time());
		storage.setPrepTime(inRecipe.getPrep_time());
		storage.setRecipe_name(inRecipe.getRecipe_name());
		storage.setSource_authors(toStorageAuthors(inRecipe.getSource_authors()));
		storage.setSource_book(toStorageSourceBook(inRecipe.getSource_book()));
		storage.setSteps(toStorageSteps(inRecipe.getSteps()));
		storage.setYields(toStorageYields(inRecipe.getYields()));
		storage.setDefaultImageURL(new URL(inRecipe.getDefault_image_url()));
		return storage;
	}

	public static ly.whisk.model.Recipe toAPIRecipe(
			ly.whisk.storage.Recipe inRecipe) {
		ly.whisk.model.Recipe tr = new ly.whisk.model.Recipe();
		tr.setCooking_time(inRecipe.getCookingTime());
		tr.setRecipe_uuid(inRecipe.getId());
		tr.setAdded_at(inRecipe.getAddedAt());
		tr.setIs_private(inRecipe.isPrivate());
		tr.setNotes(toAPINote(inRecipe.getNotes()));
		tr.setOven_fan(toAPIOvenFan(inRecipe.getOven_fan()));
		tr.setIngredients(toAPIIngredients(inRecipe.getAllIngredients()));
		tr.setOven_temp(toAPIOvenTemp(inRecipe.getOven_temp()));
		tr.setOven_time(inRecipe.getOven_time());
		tr.setPrep_time(inRecipe.getPrepTime());
		tr.setRecipe_name(inRecipe.getRecipe_name());
		tr.setRecipe_uuid(inRecipe.getId());
		// TODO: Fix this
		// tr.setSource_authors(inRecipe.getSource_authors());
		// tr.setSource_book(inRecipe.getSource_book());
		// tr.setSource_url(inRecipe.getSource_url());
		tr.setSteps(toAPISteps(inRecipe.getSteps()));
		tr.setYields(toAPIYields(inRecipe.getYields()));
		tr.setDefault_image_url(inRecipe.getDefaultImageURL().toString());

		return tr;
	}

	public static List<ly.whisk.model.IngredientAndAmount> toAPIIngredients(
			Set<ly.whisk.storage.IngredientAndAmount> inIngr) {
		if (inIngr == null || inIngr.size() == 0) {
			return null;
		}
		List<ly.whisk.model.IngredientAndAmount> tr = new ArrayList<>();
		for (ly.whisk.storage.IngredientAndAmount ingr : inIngr) {
			tr.add(toAPIIngredient(ingr));
		}
		return tr;
	}

	public static ly.whisk.model.IngredientAndAmount toAPIIngredient(
			ly.whisk.storage.IngredientAndAmount in) {
		if (in == null) {
			return null;
		}
		ly.whisk.model.IngredientAndAmount tr = new IngredientAndAmount();
		tr.setId(in.getId());
		tr.setUnit(toAPIUnitTag(in.getUnit()));
		tr.setValue(in.getValue());
		tr.setIngredient(toAPIIngredient(in.getIngredient()));
		return tr;
	}

	public static ly.whisk.model.Ingredient toAPIIngredient(
			ly.whisk.storage.Ingredient in) {
		if (in == null) {
			return null;
		}
		ly.whisk.model.Ingredient tr = new Ingredient();
		tr.setId(in.getId());
		tr.setName(in.getName());
		tr.setNote(toAPINote(in.getNotes()));
		tr.setUsda_num(in.getUsda_num());
		if (in.getProcessingTags() == null
				|| in.getProcessingTags().size() == 0) {
			tr.setProcessingTags(null);
		} else {
			List<ly.whisk.model.ProcessingTag> toSet = new ArrayList<>();
			for (ly.whisk.storage.ProcessingTag tag : in.getProcessingTags()) {
				toSet.add(toAPIProcessingTag(tag));
			}
			tr.setProcessingTags(toSet);
		}
		if (in.getSubstitutions() == null || in.getSubstitutions().size() == 0) {
			tr.setSubstitutions(null);
		} else {
			List<ly.whisk.model.Ingredient> toSet = new ArrayList<>();
			for (ly.whisk.storage.Ingredient ingr : in.getSubstitutions()) {
				toSet.add(toAPIIngredient(ingr));
			}
			tr.setSubstitutions(toSet);
		}
		return tr;
	}

	public static ly.whisk.model.ProcessingTag toAPIProcessingTag(
			ProcessingTag inTag) {
		if (inTag == null) {
			return null;
		}
		ly.whisk.model.ProcessingTag tr = new ly.whisk.model.ProcessingTag();
		tr.setId(inTag.getId());
		tr.setTag(inTag.getTag());
		return tr;

	}

	public static List<ly.whisk.model.Yield> toAPIYields(Set<Yield> inYields) {
		if (inYields == null || inYields.size() == 0) {
			return null;
		}
		List<ly.whisk.model.Yield> tr = new ArrayList<>();
		for (Yield y : inYields) {
			tr.add(toAPIYield(y));
		}
		return tr;
	}

	public static ly.whisk.model.Yield toAPIYield(Yield inYield) {
		if (inYield == null) {
			return null;
		}
		ly.whisk.model.Yield tr = new ly.whisk.model.Yield();
		tr.setId(inYield.getId());
		tr.setServes(inYield.getServes());
		tr.setUnit(toAPIUnitTag(inYield.getUnit()));
		return tr;
	}

	public static ly.whisk.model.UnitTag toAPIUnitTag(UnitTag inTag) {
		if (inTag == null) {
			return null;
		}
		ly.whisk.model.UnitTag tr = new ly.whisk.model.UnitTag();
		tr.setId(inTag.getId());
		tr.setTag(inTag.getTag());
		return tr;
	}

	public static List<ly.whisk.model.Step> toAPISteps(Set<Step> inSteps) {
		if (inSteps == null || inSteps.size() == 0) {
			return null;
		}
		List<ly.whisk.model.Step> tr = new ArrayList<>();
		for (Step s : inSteps) {
			tr.add(toAPIStep(s));
		}
		return tr;
	}

	public static ly.whisk.model.Step toAPIStep(Step inStep) {
		if (inStep == null) {
			return null;
		}
		ly.whisk.model.Step tr = new ly.whisk.model.Step();
		tr.setId(inStep.getId());
		tr.setNotes(toAPINote(inStep.getNotes()));
		tr.setHaccp(toAPIHaccp(inStep.getHaccp()));
		tr.setOrder(inStep.getOrder());
		tr.setStep_details(inStep.getStepDetails());
		return tr;
	}

	public static ly.whisk.model.HACCP toAPIHaccp(HACCP inHaccp) {
		if (inHaccp == null) {
			return null;
		}
		ly.whisk.model.HACCP tr = new ly.whisk.model.HACCP();
		tr.setControl_point(inHaccp.getControl_point());
		tr.setCritical_control_point(inHaccp.getCritical_control_point());
		return tr;
	}

	public static ly.whisk.model.OvenFan toAPIOvenFan(OvenFan inFan) {
		if (inFan == null) {
			return null;
		}
		ly.whisk.model.OvenFan tr = new ly.whisk.model.OvenFan();
		if (OvenFan.HIGH == inFan) {
			tr.setValue(ly.whisk.model.OvenFan.valueEnum.High.name());
		} else if (OvenFan.LOW == inFan) {
			tr.setValue(ly.whisk.model.OvenFan.valueEnum.Low.name());
		} else if (OvenFan.OFF == inFan) {
			tr.setValue(ly.whisk.model.OvenFan.valueEnum.Off.name());
		}
		return tr;
	}

	public static ly.whisk.model.OvenTemp toAPIOvenTemp(OvenTemp inTemp) {
		if (inTemp == null) {
			return null;
		}
		ly.whisk.model.OvenTemp tr = new ly.whisk.model.OvenTemp();
		tr.setValue(inTemp.getAmount());
		tr.setUnit(toAPITempUnit(inTemp.getUnit()));
		return tr;
	}

	public static ly.whisk.model.TemperatureUnit toAPITempUnit(
			TemperatureUnit inUnit) {
		ly.whisk.model.TemperatureUnit tempUnit = new ly.whisk.model.TemperatureUnit();
		if (TemperatureUnit.CELSIUS == inUnit) {
			tempUnit.setValue(ly.whisk.model.TemperatureUnit.valueEnum.C.name());
		} else {
			tempUnit.setValue(ly.whisk.model.TemperatureUnit.valueEnum.F.name());
		}
		return tempUnit;
	}

	public static ly.whisk.model.Note toAPINote(Note inNote) {
		if (inNote == null) {
			return null;
		}
		ly.whisk.model.Note tr = new ly.whisk.model.Note();
		tr.setId(inNote.getId());
		tr.setNote(inNote.getNote());
		return tr;
	}

	public static Set<Yield> toStorageYields(List<ly.whisk.model.Yield> inYields) {
		if (inYields == null || inYields.size() == 0) {
			return null;
		}
		HashSet<Yield> tr = new HashSet<>();
		for (ly.whisk.model.Yield y : inYields) {
			tr.add(toStorageYield(y));
		}
		return tr;
	}

	public static Yield toStorageYield(ly.whisk.model.Yield yield) {
		if (yield == null) {
			return null;
		}
		Yield tr = new Yield();
		tr.setId(yield.getId());
		tr.setServes(yield.getServes());
		tr.setUnit(toStorageUnitTag(yield.getUnit()));
		return tr;
	}

	public static UnitTag toStorageUnitTag(ly.whisk.model.UnitTag utag) {
		UnitTag toReturn = new UnitTag();
		toReturn.setId(utag.getId());
		toReturn.setTag(utag.getTag());
		return toReturn;
	}

	public static Set<Step> toStorageSteps(List<ly.whisk.model.Step> inSteps) {
		if (inSteps == null || inSteps.size() == 0) {
			return null;
		}
		HashSet<Step> tr = new HashSet<Step>();
		for (ly.whisk.model.Step step : inSteps) {
			tr.add(toStorageStep(step));
		}
		return tr;
	}

	public static Step toStorageStep(ly.whisk.model.Step inStep) {
		if (inStep == null) {
			return null;
		}
		Step tr = new Step();
		tr.setId(inStep.getId());
		tr.setNotes(toStorageNote(inStep.getNotes()));
		tr.setOrder(inStep.getOrder());
		tr.setStepDetails(inStep.getStep_details());
		tr.setHaccp(toStorageHaccp(inStep.getHaccp()));
		return tr;
	}

	public static HACCP toStorageHaccp(ly.whisk.model.HACCP inHaccp) {
		if (inHaccp == null) {
			return null;
		}
		HACCP tr = new HACCP();
		tr.setControl_point(inHaccp.getControl_point());
		tr.setCritical_control_point(inHaccp.getCritical_control_point());
		return tr;
	}

	public static Set<Author> toStorageAuthors(
			List<ly.whisk.model.Author> inAuthors) {
		if (inAuthors == null || inAuthors.size() == 0) {
			return null;
		}
		Set<Author> toReturn = new HashSet<Author>();
		for (ly.whisk.model.Author author : inAuthors) {
			toReturn.add(toStorageAuthor(author));
		}
		return toReturn;
	}

	public static ly.whisk.storage.Author toStorageAuthor(
			ly.whisk.model.Author inAuthor) {
		if (inAuthor == null) {
			return null;
		}
		Author toReturn = new Author();
		toReturn.setId(inAuthor.getId());
		toReturn.setName(inAuthor.getName());
		return toReturn;
	}

	public static ly.whisk.storage.SourceBook toStorageSourceBook(
			ly.whisk.model.SourceBook inBook) {
		if (inBook == null) {
			return null;
		}
		SourceBook toReturn = new SourceBook();
		toReturn.setId(inBook.getId());
		toReturn.setISBN(inBook.getISBN());
		toReturn.setNotes(toStorageNote(inBook.getNotes()));
		toReturn.setTitle(inBook.getTitle());

		HashSet<ly.whisk.storage.Author> storageAuthors = null;
		if (inBook.getAuthors() != null && inBook.getAuthors().size() > 0) {
			storageAuthors = new HashSet<>();
			for (ly.whisk.model.Author author : inBook.getAuthors()) {
				storageAuthors.add(toStorageAuthor(author));
			}
		}

		toReturn.setAuthors(storageAuthors);

		return toReturn;
	}

	public static ly.whisk.storage.Note toStorageNote(ly.whisk.model.Note inNote) {
		if (inNote == null) {
			return null;
		}
		Note newNote = new Note();
		newNote.setId(inNote.getId());
		newNote.setNote(inNote.getNote());
		return newNote;
	}

	public static ly.whisk.storage.OvenFan toStorageOvenFan(
			ly.whisk.model.OvenFan inFan) {
		if (inFan.getValue().equals(
				ly.whisk.model.OvenFan.valueEnum.Off.toString())) {
			return OvenFan.OFF;
		} else if (inFan.getValue().equals(
				ly.whisk.model.OvenFan.valueEnum.Low.toString())) {
			return OvenFan.LOW;
		} else if (inFan.getValue().equals(
				ly.whisk.model.OvenFan.valueEnum.High.toString())) {
			return OvenFan.HIGH;
		}
		return null;

	}

	public static ly.whisk.storage.OvenTemp toStorageOvenTemp(
			ly.whisk.model.OvenTemp inTemp) {
		if (inTemp == null) {
			return null;
		}
		OvenTemp toReturn = new OvenTemp();
		toReturn.setAmount(inTemp.getValue());
		toReturn.setUnit(toStorageTempetratureUnit(inTemp.getUnit()));
		return toReturn;
	}

	public static ly.whisk.storage.TemperatureUnit toStorageTempetratureUnit(
			ly.whisk.model.TemperatureUnit inUnit) {
		if (inUnit.getValue().equals(
				ly.whisk.model.TemperatureUnit.valueEnum.C.toString())) {
			return ly.whisk.storage.TemperatureUnit.CELSIUS;
		} else {
			return ly.whisk.storage.TemperatureUnit.FAHRENHEIT;
		}
	}
}
