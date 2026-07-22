package ruiseki.jfi.jfmuy.mekanism.machine.chemical;

import java.util.Arrays;

import mekanism.common.recipe.machines.ChemicalInfuserRecipe;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfi.jfmuy.mekanism.machine.MekanismRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class ChemicalInfuserRecipeWrapper extends MekanismRecipeWrapper<ChemicalInfuserRecipe> {

    public ChemicalInfuserRecipeWrapper(ChemicalInfuserRecipe recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients
            .setInputs(MekanismPlugin.TYPE_GAS, Arrays.asList(recipe.recipeInput.leftGas, recipe.recipeInput.rightGas));
        ingredients.setOutput(MekanismPlugin.TYPE_GAS, recipe.recipeOutput.output);
    }
}
