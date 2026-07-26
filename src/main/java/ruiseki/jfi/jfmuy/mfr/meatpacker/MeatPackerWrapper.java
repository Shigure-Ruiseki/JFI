package ruiseki.jfi.jfmuy.mfr.meatpacker;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class MeatPackerWrapper implements IRecipeWrapper {

    private final FluidStack inputFluid;
    private final ItemStack outputItem;

    public MeatPackerWrapper(FluidStack inputFluid, ItemStack outputItem) {
        this.inputFluid = inputFluid;
        this.outputItem = outputItem;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (this.inputFluid != null) {
            ingredients.setInput(VanillaTypes.FLUID, this.inputFluid);
        }
        if (this.outputItem != null) {
            ingredients.setOutput(VanillaTypes.ITEM, this.outputItem);
        }
    }
}
