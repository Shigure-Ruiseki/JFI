package ruiseki.jfi.jfmuy.botania.puredaisy;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.ImmutableList;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import vazkii.botania.api.recipe.RecipePureDaisy;

public class PureDaisyRecipeWrapper implements IRecipeWrapper {

    private List<ItemStack> inputs = ImmutableList.of();
    private ItemStack output = null;
    private FluidStack fluidInput = null;
    private FluidStack fluidOutput = null;

    public PureDaisyRecipeWrapper(RecipePureDaisy recipe) {
        if (recipe.getInput() instanceof String) {
            inputs = ImmutableList.copyOf(OreDictionary.getOres((String) recipe.getInput()));
        } else if (recipe.getInput() instanceof Block b) {
            Fluid fluid = FluidRegistry.lookupFluidForBlock(b);

            if (fluid != null) {
                fluidInput = new FluidStack(fluid, 1000);
            } else {
                inputs = ImmutableList.of(new ItemStack(b, 1, OreDictionary.WILDCARD_VALUE));
            }
        }

        Block outBlock = recipe.getOutput();
        int outMeta = recipe.getOutputMeta();
        Fluid fluidOut = FluidRegistry.lookupFluidForBlock(outBlock);

        if (fluidOut != null) {
            fluidOutput = new FluidStack(fluidOut, 1000);
        } else {
            output = new ItemStack(outBlock, 1, outMeta);
        }
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        if (!inputs.isEmpty()) {
            ingredients.setInputs(VanillaTypes.ITEM, inputs);
        }

        if (fluidInput != null) {
            ingredients.setInput(VanillaTypes.FLUID, fluidInput);
        }

        if (output != null) {
            ingredients.setOutput(VanillaTypes.ITEM, output);
        }

        if (fluidOutput != null) {
            ingredients.setOutput(VanillaTypes.FLUID, fluidOutput);
        }
    }
}
