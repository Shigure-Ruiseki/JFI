package ruiseki.jfi.jfmuy.ic2.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import ic2.core.AdvRecipe;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.IShapedCraftingRecipeWrapper;

public class AdvRecipeWrapper implements IShapedCraftingRecipeWrapper {

    private final AdvRecipe recipe;
    private final List<List<ItemStack>> inputs;
    private final ItemStack output;

    public AdvRecipeWrapper(AdvRecipe recipe) {
        this.recipe = recipe;
        this.output = recipe.output;
        this.inputs = new ArrayList<>(9);

        int inputIndex = 0;
        for (int i = 0; i < 9; ++i) {
            if ((recipe.masks[0] & (1 << (8 - i))) != 0) {
                List<ItemStack> expanded = AdvRecipe.expand(recipe.input[inputIndex]);
                inputs.add(expanded != null ? expanded : Collections.emptyList());
                inputIndex++;
            } else {
                inputs.add(Collections.emptyList());
            }
        }
    }

    public AdvRecipe getRecipe() {
        return recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }
}
