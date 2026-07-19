package ruiseki.jfi.jfmuy.enderio.sagmill;

import java.util.ArrayList;
import java.util.List;

import crazypants.enderio.machine.crusher.CrusherRecipeManager;
import crazypants.enderio.machine.recipe.IRecipe;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SagMillRecipeMarker {

    public static List<IRecipeWrapper> getValidRecipes() {
        List<IRecipeWrapper> recipes = new ArrayList<>();

        for (IRecipe recipe : CrusherRecipeManager.getInstance()
            .getRecipes()) {
            if (recipe != null) {
                recipes.add(new SagMillRecipeWrapper(recipe));
            }
        }

        return recipes;
    }
}
