package ruiseki.jfi.jfmuy.thermaldynamics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import cofh.thermaldynamics.duct.attachments.cover.CoverHelper;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.ingredients.IIngredientRegistry;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;

@JFMUYPlugin
public class ThermalDynamicsPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        List<CoverRecipeWrapper> coverRecipes = new ArrayList<>();
        IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
        Collection<ItemStack> allItems = ingredientRegistry.getAllIngredients(VanillaTypes.ITEM);
        for (ItemStack stack : allItems) {
            if (stack != null && CoverHelper.isValid(stack)) {
                ItemStack materialInput = stack.copy();
                materialInput.stackSize = 1;

                coverRecipes.add(new CoverRecipeWrapper(materialInput));
            }
        }

        if (!coverRecipes.isEmpty()) {
            registry.addRecipes(coverRecipes, VanillaRecipeCategoryUid.CRAFTING);
        }
    }
}
