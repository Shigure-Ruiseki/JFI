package ruiseki.jfi.jfmuy.mekanism.machine.other;

import mekanism.common.recipe.machines.PressurizedRecipe;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfi.jfmuy.mekanism.machine.MekanismRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class PRCRecipeWrapper extends MekanismRecipeWrapper<PressurizedRecipe> {

    public PRCRecipeWrapper(PressurizedRecipe recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (recipe.recipeInput != null) {
            if (recipe.recipeInput.getSolid() != null) {
                ingredients.setInput(VanillaTypes.ITEM, recipe.recipeInput.getSolid());
            }
            if (recipe.recipeInput.getFluid() != null) {
                ingredients.setInput(VanillaTypes.FLUID, recipe.recipeInput.getFluid());
            }
            if (recipe.recipeInput.getGas() != null) {
                ingredients.setInput(MekanismPlugin.TYPE_GAS, recipe.recipeInput.getGas());
            }
        }

        if (recipe.recipeOutput != null) {
            if (recipe.recipeOutput.getItemOutput() != null) {
                ingredients.setOutput(VanillaTypes.ITEM, recipe.recipeOutput.getItemOutput());
            }
            if (recipe.recipeOutput.getGasOutput() != null) {
                ingredients.setOutput(MekanismPlugin.TYPE_GAS, recipe.recipeOutput.getGasOutput());
            }
        }
    }
}
