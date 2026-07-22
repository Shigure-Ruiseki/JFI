package ruiseki.jfi.jfmuy.tconstruct.alloying;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import tconstruct.library.crafting.AlloyMix;

public class AlloyingRecipeWrapper implements IRecipeWrapper {

    private final AlloyMix recipe;
    private final List<FluidStack> inputs;
    private final FluidStack output;
    private final int maxInputAmount;

    public AlloyingRecipeWrapper(AlloyMix recipe) {
        this.recipe = recipe;
        this.output = recipe.result;
        this.inputs = new ArrayList<>(recipe.mixers);

        int max = 0;
        for (FluidStack stack : recipe.mixers) {
            if (stack != null && stack.amount > max) {
                max = stack.amount;
            }
        }
        this.maxInputAmount = max > 0 ? max : 1000;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.FLUID, inputs);

        if (output != null) {
            ingredients.setOutput(VanillaTypes.FLUID, output);
        }
    }

    public AlloyMix getRecipe() {
        return this.recipe;
    }

    public int getMaxInputAmount() {
        return this.maxInputAmount;
    }
}
