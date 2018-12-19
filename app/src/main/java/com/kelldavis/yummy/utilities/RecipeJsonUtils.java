package com.kelldavis.yummy.utilities;

import android.content.Context;

import com.kelldavis.yummy.model.Recipe;
import com.kelldavis.yummy.model.Ingredient;
import com.kelldavis.yummy.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RecipeJsonUtils {
    private static final String TAG = RecipeJsonUtils.class.getSimpleName();
    private static final String JSON_RECIPE_NAME_KEY = "name";
    private static final String JSON_RECIPE_SERVINGS_KEY = "servings";
    private static final String JSON_RECIPE_IMAGE_KEY = "image";
    private static final String JSON_RECIPE_INGREDIENTS_KEY = "ingredients";
    private static final String JSON_RECIPE_INGREDIENT_KEY = "ingredient";
    private static final String JSON_RECIPE_STEPS_KEY = "steps";
    private static final String JSON_RECIPE_QUANTITY_KEY = "quantity";
    private static final String JSON_RECIPE_MEASURE_KEY = "measure";
    private static final String JSON_RECIPE_ID_KEY = "id";
    private static final String JSON_RECIPE_SHORT_DESCRIPTION_KEY = "shortDescription";
    private static final String JSON_RECIPE_DESCRIPTION_KEY = "description";
    private static final String JSON_RECIPE_VIDEO_URL_KEY = "videoURL";
    private static final String JSON_RECIPE_THUMBNAIL_URL_KEY = "thumbnailURL";
    private static final String LARGE_PHOTO_TYPE = "z.jpg";
    private static final String THUMBNAIL_PHOTO_TYPE = "s.jpg";

    public static Recipe[] getRecipesFromJson(Context context, String recipesJsonString) throws JSONException {
        JSONArray recipesArray = new JSONArray(recipesJsonString);
        Recipe[] recipes = new Recipe[recipesArray.length()];
        HashMap<String, String[]> photoUrls = new HashMap<>();

        /*
        * Each recipe has its own picture.
        * */

        photoUrls.put("Nutella Pie", new String[]{"https://c1.staticflickr.com/5/4868/44558641120_2a3658694b_", "A recipe that will make you fall into a puddle of ecstasy."});
        photoUrls.put("Brownies", new String[]{"https://c1.staticflickr.com/5/4906/44558641040_806b14c0c4_", "Your will love these rich, fudgy brownies."});
        photoUrls.put("Yellow Cake", new String[]{"https://c1.staticflickr.com/5/4880/45652557764_d980b7c30a_", "Tender, buttery, afternoon indulgence."});
        photoUrls.put("Cheesecake", new String[]{"https://c1.staticflickr.com/5/4832/45652557714_3b90517353_", "Elegant, creamy, delicious, and easy to make."});

        for (int id = 0; id < recipesArray.length(); id++) {
            JSONObject recipe = recipesArray.getJSONObject(id);
            String recipeName = recipe.getString(JSON_RECIPE_NAME_KEY);
            int recipeServings = recipe.getInt(JSON_RECIPE_SERVINGS_KEY);
            String recipeImageUrl = recipe.getString(JSON_RECIPE_IMAGE_KEY);

            JSONArray ingredients = recipe.getJSONArray(JSON_RECIPE_INGREDIENTS_KEY);
            Ingredient[] recipeIngredients = getIngredientsFromJson(context, ingredients);

            JSONArray steps = recipe.getJSONArray(JSON_RECIPE_STEPS_KEY);
            Step[] recipeSteps = getStepsFromJson(context, steps);

            if (recipeImageUrl.isEmpty()) {
                recipeImageUrl = photoUrls.get(recipeName)[0] + LARGE_PHOTO_TYPE;
            }
            String recipethumbnailUrl = photoUrls.get(recipeName)[0] + THUMBNAIL_PHOTO_TYPE;
            String blurb = photoUrls.get(recipeName)[1];

            Recipe card = new Recipe(id,
                    recipeName,
                    recipeIngredients,
                    recipeSteps,
                    recipeServings,
                    recipeImageUrl,
                    recipethumbnailUrl,
                    blurb);
            recipes[id] = card;
        }

        return recipes;
    }

    private static Ingredient[] getIngredientsFromJson(Context context, JSONArray ingredientsArray) throws JSONException {
        Ingredient[] ingredients = new Ingredient[ingredientsArray.length()];

        for (int i = 0; i < ingredientsArray.length(); i++) {
            JSONObject ingredientJson = ingredientsArray.getJSONObject(i);
            String ingredientName = ingredientJson.getString(JSON_RECIPE_INGREDIENT_KEY);
            // some of the ingredients don't have a space between a word and a following open paren
            ingredientName = ingredientName.replaceAll("(\\S)\\(", "$1 \\(");
            int ingredientQuantity = ingredientJson.getInt(JSON_RECIPE_QUANTITY_KEY);
            String ingredientMeasurementUnit = ingredientJson.getString(JSON_RECIPE_MEASURE_KEY);

            Ingredient ingredient = new Ingredient(ingredientName,
                    ingredientQuantity,
                    ingredientMeasurementUnit);
            ingredients[i] = ingredient;
        }
        return ingredients;
    }

    private static Step[] getStepsFromJson(Context context, JSONArray stepsArray) throws JSONException {
        Step[] steps = new Step[stepsArray.length()];

        for (int i = 0; i < stepsArray.length(); i++) {
            JSONObject stepJson = stepsArray.getJSONObject(i);
            int stepId = stepJson.getInt(JSON_RECIPE_ID_KEY);
            String stepShortDescription = stepJson.getString(JSON_RECIPE_SHORT_DESCRIPTION_KEY);
            String stepDescription = stepJson.getString(JSON_RECIPE_DESCRIPTION_KEY);
            String stepVideoUrl = stepJson.getString(JSON_RECIPE_VIDEO_URL_KEY);
            String stepThumbnailUrl = stepJson.getString(JSON_RECIPE_THUMBNAIL_URL_KEY);

            Step step = new Step(stepId,
                    stepShortDescription,
                    stepDescription,
                    stepVideoUrl,
                    stepThumbnailUrl);

            steps[i] = step;
        }
        return steps;
    }
}
