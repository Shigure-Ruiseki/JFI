package ruiseki.jfi.jfmuy.mekanism.machine;

import mekanism.common.recipe.machines.MachineRecipe;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public abstract class MekanismRecipeWrapper<RECIPE extends MachineRecipe<?, ?, ?>> implements IRecipeWrapper {

    protected final RECIPE recipe;

    public MekanismRecipeWrapper(RECIPE recipe) {
        this.recipe = recipe;
    }

    public RECIPE getRecipe() {
        return recipe;
    }
}
