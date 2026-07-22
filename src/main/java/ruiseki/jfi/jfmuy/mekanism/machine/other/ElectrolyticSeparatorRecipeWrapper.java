package ruiseki.jfi.jfmuy.mekanism.machine.other;

import java.util.Arrays;

import mekanism.common.recipe.machines.SeparatorRecipe;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfi.jfmuy.mekanism.machine.MekanismRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class ElectrolyticSeparatorRecipeWrapper extends MekanismRecipeWrapper<SeparatorRecipe> {

    public ElectrolyticSeparatorRecipeWrapper(SeparatorRecipe recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, recipe.recipeInput.ingredient);
        ingredients.setOutputs(
            MekanismPlugin.TYPE_GAS,
            Arrays.asList(recipe.recipeOutput.leftGas, recipe.recipeOutput.rightGas));
    }
}
