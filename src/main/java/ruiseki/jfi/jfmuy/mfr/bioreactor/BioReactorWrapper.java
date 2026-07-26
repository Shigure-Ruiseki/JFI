package ruiseki.jfi.jfmuy.mfr.bioreactor;

import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class BioReactorWrapper implements IRecipeWrapper {

    private final List<ItemStack> input;
    private final FluidStack biofuelOutput;

    public BioReactorWrapper(ItemStack plantable) {
        this.input = Collections.singletonList(plantable.copy());
        this.biofuelOutput = FluidRegistry.getFluidStack("biofuel", 4000);
    }

    public BioReactorWrapper(List<ItemStack> plantables) {
        this.input = plantables;
        this.biofuelOutput = FluidRegistry.getFluidStack("biofuel", 4000);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(this.input));
        if (this.biofuelOutput != null) {
            ingredients.setOutput(VanillaTypes.FLUID, this.biofuelOutput);
        }
    }
}
