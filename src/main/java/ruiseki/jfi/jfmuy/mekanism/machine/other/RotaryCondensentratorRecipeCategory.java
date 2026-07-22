package ruiseki.jfi.jfmuy.mekanism.machine.other;

import net.minecraft.client.Minecraft;

import mekanism.api.gas.GasStack;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiIngredientGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class RotaryCondensentratorRecipeCategory extends BaseRecipeCategory<RotaryCondensentratorRecipeWrapper> {

    private final boolean condensentrating;

    public RotaryCondensentratorRecipeCategory(IGuiHelper helper, boolean condensentrating) {
        super(
            helper,
            "mekanism:gui/nei/GuiRotaryCondensentrator.png",
            condensentrating ? "mekanism.rotary_condensentrator_condensentrating"
                : "mekanism.rotary_condensentrator_decondensentrating",
            condensentrating ? "gui.condensentrating" : "gui.decondensentrating",
            null,
            3,
            12,
            170,
            71);
        this.condensentrating = condensentrating;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
        drawTexturedRect(64 - xOffset, 39 - yOffset, 176, condensentrating ? 123 : 115, 48, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RotaryCondensentratorRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        IGuiIngredientGroup<GasStack> gasStacks = recipeLayout.getIngredientsGroup(MekanismPlugin.TYPE_GAS);
        fluidStacks.init(
            0,
            !condensentrating,
            134 - xOffset,
            14 - yOffset,
            16,
            58,
            RotaryCondensentratorRecipeWrapper.FLUID_AMOUNT,
            false,
            fluidOverlayLarge);
        if (condensentrating) {
            initGas(
                gasStacks,
                0,
                true,
                26 - xOffset,
                14 - yOffset,
                16,
                58,
                new GasStack(recipeWrapper.getGasType(), RotaryCondensentratorRecipeWrapper.GAS_AMOUNT),
                true);
            fluidStacks.set(
                0,
                ingredients.getOutputs(VanillaTypes.FLUID)
                    .get(0));
        } else {
            initGas(
                gasStacks,
                0,
                false,
                26 - xOffset,
                14 - yOffset,
                16,
                58,
                new GasStack(recipeWrapper.getGasType(), RotaryCondensentratorRecipeWrapper.GAS_AMOUNT),
                true);
            fluidStacks.set(
                0,
                ingredients.getInputs(VanillaTypes.FLUID)
                    .get(0));
        }
    }
}
