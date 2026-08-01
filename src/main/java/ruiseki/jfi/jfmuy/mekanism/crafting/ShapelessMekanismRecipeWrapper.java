package ruiseki.jfi.jfmuy.mekanism.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import mekanism.common.recipe.ShapelessMekanismRecipe;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.ICraftingRecipeWrapper;

public class ShapelessMekanismRecipeWrapper implements ICraftingRecipeWrapper {

    private final ShapelessMekanismRecipe recipe;

    public ShapelessMekanismRecipeWrapper(ShapelessMekanismRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ArrayList<Object> inputObjs = recipe.getInput();
        if (inputObjs == null) {
            return;
        }

        List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();

        for (Object obj : inputObjs) {
            if (obj instanceof ItemStack) {
                List<ItemStack> list = new ArrayList<ItemStack>();
                list.add((ItemStack) obj);
                inputs.add(list);
            } else if (obj instanceof ArrayList) {
                @SuppressWarnings("unchecked")
                ArrayList<ItemStack> oreList = (ArrayList<ItemStack>) obj;
                inputs.add(oreList);
            } else {
                inputs.add(null);
            }
        }

        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }
}
