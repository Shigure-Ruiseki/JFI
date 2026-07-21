package ruiseki.jfi.jfmuy.immersiveengineering.refinery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

import blusunrize.immersiveengineering.api.energy.DieselHandler;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class RefineryRecipeWrapper implements IRecipeWrapper {

    private final DieselHandler.RefineryRecipe recipe;
    private final List<List<FluidStack>> inputs;
    private final FluidStack output;

    public RefineryRecipeWrapper(DieselHandler.RefineryRecipe recipe) {
        this.recipe = recipe;
        this.inputs = new ArrayList<>();

        List<FluidStack> input0List = new ArrayList<>();
        if (recipe.input0 != null) {
            input0List.add(recipe.input0.copy());
        }
        this.inputs.add(input0List);

        List<FluidStack> input1List = new ArrayList<>();
        if (recipe.input1 != null) {
            input1List.add(recipe.input1.copy());
        }
        this.inputs.add(input1List);

        this.output = recipe.output != null ? recipe.output.copy() : null;
    }

    public DieselHandler.RefineryRecipe getRecipe() {
        return this.recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.FLUID, inputs);

        if (output != null) {
            ingredients.setOutput(VanillaTypes.FLUID, output);
        }
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 72 && mouseX <= 102 && mouseY >= 26 && mouseY <= 44) {
            List<String> tooltip = new ArrayList<>();
            tooltip.add(EnumChatFormatting.GRAY + "Refining Process");
            return tooltip;
        }
        return Collections.emptyList();
    }
}
