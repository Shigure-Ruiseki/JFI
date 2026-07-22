package ruiseki.jfi.jfmuy.mekanism.machine.chemical;

import mekanism.common.recipe.machines.CrystallizerRecipe;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfi.jfmuy.mekanism.machine.MekanismRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class ChemicalCrystallizerRecipeWrapper
    extends MekanismRecipeWrapper<CrystallizerRecipe> {

    public ChemicalCrystallizerRecipeWrapper(CrystallizerRecipe recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(MekanismPlugin.TYPE_GAS, recipe.recipeInput.ingredient);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.recipeOutput.output);
    }
}
