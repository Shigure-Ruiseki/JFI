package ruiseki.jfi.jfmuy.enderio.soulbinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import crazypants.enderio.ModObject;
import crazypants.enderio.machine.IMachineRecipe;
import crazypants.enderio.machine.MachineRecipeRegistry;
import crazypants.enderio.machine.soul.ISoulBinderRecipe;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SoulBinderRecipeMarker {

    public static List<IRecipeWrapper> getValidRecipes() {
        List<IRecipeWrapper> recipes = new ArrayList<>();

        Map<String, IMachineRecipe> registeredRecipes = MachineRecipeRegistry.instance
            .getRecipesForMachine(ModObject.blockSoulBinder.unlocalisedName);

        if (registeredRecipes != null && !registeredRecipes.isEmpty()) {
            for (IMachineRecipe recipe : registeredRecipes.values()) {
                if (recipe instanceof ISoulBinderRecipe validRecipes) {
                    recipes.add(new SoulBinderRecipeWrapper(validRecipes));
                }
            }
        }

        return recipes;
    }
}
