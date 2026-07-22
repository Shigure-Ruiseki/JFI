package ruiseki.jfi.jfmuy.mekanism.machine.chemical;

import mekanism.common.recipe.machines.OxidationRecipe;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfi.jfmuy.mekanism.machine.MekanismRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class ChemicalOxidizerRecipeWrapper
    extends MekanismRecipeWrapper<OxidationRecipe> {

    public ChemicalOxidizerRecipeWrapper(OxidationRecipe recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, recipe.recipeInput.ingredient);
        ingredients.setOutput(MekanismPlugin.TYPE_GAS, recipe.recipeOutput.output);
    }
}
