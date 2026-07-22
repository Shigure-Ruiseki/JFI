package ruiseki.jfi.jfmuy.mekanism.machine.other;

import mekanism.common.recipe.machines.ThermalEvaporationRecipe;
import ruiseki.jfi.jfmuy.mekanism.machine.MekanismRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class ThermalEvaporationRecipeWrapper extends MekanismRecipeWrapper<ThermalEvaporationRecipe> {

    public ThermalEvaporationRecipeWrapper(ThermalEvaporationRecipe recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, recipe.getInput().ingredient);
        ingredients.setOutput(VanillaTypes.FLUID, recipe.getOutput().output);
    }
}
