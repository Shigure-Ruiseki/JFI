package ruiseki.jfi.jfmuy.mfr.composter;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class ComposterWrapper implements IRecipeWrapper {

    private final FluidStack sewageInput;
    private final ItemStack fertilizerOutput;

    public ComposterWrapper(ItemStack fertilizer, int sewageAmount) {
        this.sewageInput = FluidRegistry.getFluidStack("sewage", sewageAmount);
        this.fertilizerOutput = fertilizer;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (this.sewageInput != null) {
            ingredients.setInput(VanillaTypes.FLUID, this.sewageInput);
        }
        if (this.fertilizerOutput != null) {
            ingredients.setOutput(VanillaTypes.ITEM, this.fertilizerOutput);
        }
    }
}
