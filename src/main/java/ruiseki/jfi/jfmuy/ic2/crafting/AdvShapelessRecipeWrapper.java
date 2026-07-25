package ruiseki.jfi.jfmuy.ic2.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import ic2.core.AdvRecipe;
import ic2.core.AdvShapelessRecipe;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.ICraftingRecipeWrapper;

public class AdvShapelessRecipeWrapper implements ICraftingRecipeWrapper {

    private final AdvShapelessRecipe recipe;
    private final List<List<ItemStack>> inputs;
    private final ItemStack output;

    public AdvShapelessRecipeWrapper(AdvShapelessRecipe recipe) {
        this.recipe = recipe;
        this.output = recipe.output;
        this.inputs = new ArrayList<>();

        List<ItemStack>[] expandedInputs = AdvRecipe.expandArray(recipe.input);
        if (expandedInputs != null) {
            for (List<ItemStack> inputList : expandedInputs) {
                if (inputList != null && !inputList.isEmpty()) {
                    this.inputs.add(inputList);
                }
            }
        }
    }

    public AdvShapelessRecipe getRecipe() {
        return recipe;
    }

    public boolean isValid() {
        List<ItemStack>[] expandedInputs = AdvRecipe.expandArray(recipe.input);
        if (expandedInputs == null) {
            return false;
        }
        for (List<ItemStack> item : expandedInputs) {
            if (item != null && item.isEmpty()) {
                return false;
            }
        }
        return !inputs.isEmpty();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
