package ruiseki.jfi.jfmuy.enderio.alloy;

import java.util.ArrayList;
import java.util.List;

import crazypants.enderio.machine.alloy.AlloyRecipeManager;
import crazypants.enderio.machine.recipe.IRecipe;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class AlloySmelterRecipeMarker {

    public static List<IRecipeWrapper> getValidRecipes() {
        List<IRecipeWrapper> recipes = new ArrayList<>();

        for (IRecipe recipe : AlloyRecipeManager.getInstance()
            .getRecipes()) {
            if (recipe != null) {
                recipes.add(new AlloySmelterRecipeWrapper(recipe));
            }
        }

        for (IRecipe recipe : AlloyRecipeManager.getInstance()
            .getVanillaRecipe()
            .getAllRecipes()) {
            if (recipe != null) {
                recipes.add(new AlloySmelterRecipeWrapper(recipe));
            }
        }

        return recipes;
    }
}
