package ruiseki.jfi.jfmuy.enderio.vat;

import java.util.ArrayList;
import java.util.List;

import crazypants.enderio.machine.recipe.IRecipe;
import crazypants.enderio.machine.vat.VatRecipeManager;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class VatRecipeMarker {

    public static List<IRecipeWrapper> getValidRecipes() {
        List<IRecipeWrapper> recipes = new ArrayList<>();

        for (IRecipe recipe : VatRecipeManager.getInstance()
            .getRecipes()) {
            if (recipe != null) {
                recipes.add(new VatRecipeWrapper(recipe));
            }
        }

        return recipes;
    }
}
