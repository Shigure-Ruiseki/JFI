package ruiseki.jfi.jfmuy.mekanism.machine.chemical;

import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.common.recipe.machines.DissolutionRecipe;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfi.jfmuy.mekanism.machine.MekanismRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class ChemicalDissolutionChamberRecipeWrapper extends MekanismRecipeWrapper<DissolutionRecipe> {

    public static final int INJECT_USAGE = 100;

    public ChemicalDissolutionChamberRecipeWrapper(DissolutionRecipe recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(MekanismPlugin.TYPE_GAS, new GasStack(GasRegistry.getGas("sulfuricAcid"), INJECT_USAGE));

        ingredients.setInput(VanillaTypes.ITEM, recipe.getInput().ingredient);

        ingredients.setOutput(MekanismPlugin.TYPE_GAS, recipe.getOutput().output);
    }
}
