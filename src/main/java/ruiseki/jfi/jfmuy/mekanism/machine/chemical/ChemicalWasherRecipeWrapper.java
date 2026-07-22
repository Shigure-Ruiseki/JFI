package ruiseki.jfi.jfmuy.mekanism.machine.chemical;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import mekanism.common.recipe.machines.WasherRecipe;
import mekanism.common.tile.TileEntityChemicalWasher;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfi.jfmuy.mekanism.machine.MekanismRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class ChemicalWasherRecipeWrapper extends MekanismRecipeWrapper<WasherRecipe> {

    public ChemicalWasherRecipeWrapper(WasherRecipe recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients
            .setInput(VanillaTypes.FLUID, new FluidStack(FluidRegistry.WATER, TileEntityChemicalWasher.WATER_USAGE));
        ingredients.setInput(MekanismPlugin.TYPE_GAS, recipe.recipeInput.ingredient);
        ingredients.setOutput(MekanismPlugin.TYPE_GAS, recipe.recipeOutput.output);
    }
}
