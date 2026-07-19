package ruiseki.jfi.jfmuy.enderio.enchanter;

import java.util.ArrayList;
import java.util.List;

import crazypants.enderio.machine.enchanter.EnchanterRecipe;
import crazypants.enderio.machine.enchanter.EnchanterRecipeManager;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class EnchanterRecipeMarker {

    public static List<IRecipeWrapper> getValidRecipes() {
        List<IRecipeWrapper> recipes = new ArrayList<>();

        for (EnchanterRecipe recipe : EnchanterRecipeManager.getInstance()
            .getRecipes()) {
            if (recipe != null && recipe.isValid()) {
                recipes.add(new EnchanterRecipeWrapper(recipe));
            }
        }

        return recipes;
    }
}
