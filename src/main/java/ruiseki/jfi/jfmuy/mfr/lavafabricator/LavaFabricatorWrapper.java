package ruiseki.jfi.jfmuy.mfr.lavafabricator;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class LavaFabricatorWrapper implements IRecipeWrapper {

    private final FluidStack outputLava;

    public LavaFabricatorWrapper() {
        this.outputLava = FluidRegistry.getFluidStack("lava", LavaFabricatorCategory.LAVA_PER_OPERATION);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (this.outputLava != null) {
            ingredients.setOutput(VanillaTypes.FLUID, this.outputLava);
        }
    }
}
