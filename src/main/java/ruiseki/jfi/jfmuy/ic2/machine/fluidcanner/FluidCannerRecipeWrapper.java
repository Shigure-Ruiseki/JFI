package ruiseki.jfi.jfmuy.ic2.machine.fluidcanner;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import ic2.api.recipe.IRecipeInput;
import ic2.core.item.ItemFluidCell;
import ic2.core.util.StackUtil;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class FluidCannerRecipeWrapper implements IRecipeWrapper {

    private final IRecipeInput additive;
    private final FluidStack fluidInput;
    private final FluidStack fluidOutput;

    public FluidCannerRecipeWrapper(IRecipeInput additive, FluidStack fluidInput, FluidStack fluidOutput) {
        this.additive = additive;
        this.fluidInput = fluidInput;
        this.fluidOutput = fluidOutput;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> itemInputs = new ArrayList<>();

        List<ItemStack> additiveStacks = new ArrayList<>();
        for (ItemStack stack : additive.getInputs()) {
            additiveStacks.add(StackUtil.copyWithSize(stack, additive.getAmount()));
        }
        itemInputs.add(additiveStacks);

        ingredients.setInputLists(VanillaTypes.ITEM, itemInputs);

        ItemStack outputCell = ItemFluidCell.getUniversalFluidCell(this.fluidOutput);
        if (outputCell != null) {
            ingredients.setOutput(VanillaTypes.ITEM, outputCell);
        }

        ingredients.setInput(VanillaTypes.FLUID, this.fluidInput);
        ingredients.setOutput(VanillaTypes.FLUID, this.fluidOutput);
    }
}
