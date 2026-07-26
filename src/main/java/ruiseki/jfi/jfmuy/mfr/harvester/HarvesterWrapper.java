package ruiseki.jfi.jfmuy.mfr.harvester;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class HarvesterWrapper implements IRecipeWrapper {

    private final FluidStack sludgeOutput;

    public HarvesterWrapper(int sludgeAmount) {
        this.sludgeOutput = FluidRegistry.getFluidStack("sludge", sludgeAmount);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (this.sludgeOutput != null) {
            ingredients.setOutput(VanillaTypes.FLUID, this.sludgeOutput);
        }
    }
}
