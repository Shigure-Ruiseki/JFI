package ruiseki.jfi.jfmuy.botania.crafting;

import java.util.ArrayList;
import java.util.List;

import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import vazkii.botania.api.BotaniaAPI;

public class SpecialFloatingFlowerMarker {

    public static List<IRecipeWrapper> getValidRecipes() {
        List<IRecipeWrapper> floatingFlowerRecipes = new ArrayList<>();
        for (String type : BotaniaAPI.subtilesForCreativeMenu) {
            floatingFlowerRecipes.add(new SpecialFloatingFlowerWrapper(type));
        }
        return floatingFlowerRecipes;
    }
}
