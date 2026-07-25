package ruiseki.jfi.jfmuy.bigreactors.cyanite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class CyaniteReprocessorWrapper implements IRecipeWrapper {

    private final List<ItemStack> inputs;
    private final ItemStack output;
    private final FluidStack water;

    public CyaniteReprocessorWrapper(List<ItemStack> inputs, ItemStack output) {
        this.inputs = new ArrayList<>();
        for (ItemStack stack : inputs) {
            if (stack != null) {
                ItemStack inputCopy = stack.copy();
                inputCopy.stackSize = 2;
                this.inputs.add(inputCopy);
            }
        }
        this.output = output.copy();
        this.water = new FluidStack(FluidRegistry.WATER, 1000);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(this.inputs));
        ingredients.setInput(VanillaTypes.FLUID, this.water);
        ingredients.setOutput(VanillaTypes.ITEM, this.output);
    }
}
