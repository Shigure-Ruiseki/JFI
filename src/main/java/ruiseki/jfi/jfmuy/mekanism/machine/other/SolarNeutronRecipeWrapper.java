package ruiseki.jfi.jfmuy.mekanism.machine.other;

import mekanism.common.recipe.machines.SolarNeutronRecipe;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfi.jfmuy.mekanism.machine.MekanismRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class SolarNeutronRecipeWrapper extends MekanismRecipeWrapper<SolarNeutronRecipe> {

    public SolarNeutronRecipeWrapper(SolarNeutronRecipe recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(MekanismPlugin.TYPE_GAS, recipe.getInput().ingredient);
        ingredients.setOutput(MekanismPlugin.TYPE_GAS, recipe.getOutput().output);
    }
}
