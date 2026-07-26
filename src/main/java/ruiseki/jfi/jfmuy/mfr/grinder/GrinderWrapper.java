package ruiseki.jfi.jfmuy.mfr.grinder;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class GrinderWrapper implements IRecipeWrapper {

    private final FluidStack mobEssenceOutput;

    public GrinderWrapper() {
        this.mobEssenceOutput = FluidRegistry.getFluidStack("mobessence", 4000);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (this.mobEssenceOutput != null) {
            ingredients.setOutput(VanillaTypes.FLUID, this.mobEssenceOutput);
        }
    }
}
