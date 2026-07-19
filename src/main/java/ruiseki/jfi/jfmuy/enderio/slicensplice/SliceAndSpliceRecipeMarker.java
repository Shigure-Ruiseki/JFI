package ruiseki.jfi.jfmuy.enderio.slicensplice;

import java.util.ArrayList;
import java.util.List;

import crazypants.enderio.machine.recipe.IRecipe;
import crazypants.enderio.machine.slicensplice.SliceAndSpliceRecipeManager;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SliceAndSpliceRecipeMarker {

    public static List<IRecipeWrapper> getValidRecipes() {
        List<IRecipeWrapper> recipes = new ArrayList<>();

        for (IRecipe recipe : SliceAndSpliceRecipeManager.getInstance()
            .getRecipes()) {
            if (recipe != null) {
                recipes.add(new SliceAndSpliceRecipeWrapper(recipe));
            }
        }

        return recipes;
    }
}
