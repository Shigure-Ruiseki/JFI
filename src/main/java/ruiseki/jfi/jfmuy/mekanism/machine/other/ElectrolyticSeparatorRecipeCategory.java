package ruiseki.jfi.jfmuy.mekanism.machine.other;

import mekanism.api.gas.GasStack;
import mekanism.client.gui.element.GuiFluidGauge;
import mekanism.client.gui.element.GuiGasGauge;
import mekanism.client.gui.element.GuiGauge;
import mekanism.client.gui.element.GuiPowerBar;
import mekanism.client.gui.element.GuiProgress;
import mekanism.client.gui.element.GuiSlot;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.machines.SeparatorRecipe;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiIngredientGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class ElectrolyticSeparatorRecipeCategory<WRAPPER extends ElectrolyticSeparatorRecipeWrapper>
    extends BaseRecipeCategory<WRAPPER> {

    public ElectrolyticSeparatorRecipeCategory(IGuiHelper helper) {
        super(
            helper,
            "mekanism:gui/GuiElectrolyticSeparator.png",
            RecipeHandler.Recipe.ELECTROLYTIC_SEPARATOR.getRecipeName(),
            "tile.MachineBlock2.ElectrolyticSeparator.name",
            GuiProgress.ProgressBar.BI,
            4,
            9,
            167,
            62);
    }

    @Override
    protected void addGuiElements() {
        guiElements.add(GuiFluidGauge.getDummy(GuiGauge.Type.STANDARD, this, guiLocation, 5, 10));
        guiElements.add(GuiGasGauge.getDummy(GuiGauge.Type.SMALL, this, guiLocation, 58, 18));
        guiElements.add(GuiGasGauge.getDummy(GuiGauge.Type.SMALL, this, guiLocation, 100, 18));
        guiElements.add(new GuiPowerBar(this, new GuiPowerBar.IPowerInfoHandler() {

            @Override
            public double getLevel() {
                return 1F;
            }
        }, guiLocation, 164, 15));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.NORMAL, this, guiLocation, 25, 34));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.NORMAL, this, guiLocation, 58, 51));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.NORMAL, this, guiLocation, 100, 51));
        guiElements
            .add(new GuiSlot(GuiSlot.SlotType.NORMAL, this, guiLocation, 142, 34).with(GuiSlot.SlotOverlay.POWER));
        guiElements.add(new GuiProgress(new GuiProgress.IProgressInfoHandler() {

            @Override
            public double getProgress() {
                return 1;
            }
        }, progressBar, this, guiLocation, 78, 29));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients) {
        SeparatorRecipe tempRecipe = recipeWrapper.getRecipe();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        fluidStacks.init(0, true, 2, 2, 16, 58, tempRecipe.getInput().ingredient.amount, false, fluidOverlayLarge);
        fluidStacks.set(
            0,
            ingredients.getInputs(VanillaTypes.FLUID)
                .get(0));
        IGuiIngredientGroup<GasStack> gasStacks = recipeLayout.getIngredientsGroup(MekanismPlugin.TYPE_GAS);
        initGas(gasStacks, 0, false, 59 - xOffset, 19 - yOffset, 16, 28, tempRecipe.recipeOutput.leftGas, true);
        initGas(gasStacks, 1, false, 101 - xOffset, 19 - yOffset, 16, 28, tempRecipe.recipeOutput.rightGas, true);
    }
}
