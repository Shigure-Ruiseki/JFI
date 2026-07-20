package ruiseki.jfi.jfmuy.thermalexpansion.crafting.upgrade;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.ICraftingRecipeWrapper;

public class CraftingUpgradeRecipeWrapper implements ICraftingRecipeWrapper {

    protected final List<List<ItemStack>> inputs;
    protected final List<ItemStack> outputs;

    @SuppressWarnings("unchecked")
    public CraftingUpgradeRecipeWrapper(IRecipe recipe) {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();

        if (recipe instanceof ShapedRecipes) {
            ShapedRecipes shaped = (ShapedRecipes) recipe;
            outputs.add(shaped.getRecipeOutput());

            for (ItemStack stack : shaped.recipeItems) {
                List<ItemStack> inputList = new ArrayList<>();
                if (stack != null) {
                    inputList.add(stack);
                }
                inputs.add(inputList);
            }
        } else if (recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe shapedOre = (ShapedOreRecipe) recipe;
            outputs.add(shapedOre.getRecipeOutput());

            for (Object inputObj : shapedOre.getInput()) {
                List<ItemStack> inputList = new ArrayList<>();
                if (inputObj instanceof ItemStack) {
                    inputList.add((ItemStack) inputObj);
                } else if (inputObj instanceof List) {
                    inputList.addAll((List<ItemStack>) inputObj);
                }
                inputs.add(inputList);
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }
}
