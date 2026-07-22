package ruiseki.jfi.jfmuy.mekanism.machine;

import mekanism.api.gas.GasStack;
import mekanism.client.gui.element.GuiPowerBar;
import mekanism.client.gui.element.GuiProgress;
import mekanism.client.gui.element.GuiSlot;
import mekanism.common.recipe.machines.AdvancedMachineRecipe;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiIngredientGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class AdvancedMachineRecipeCategory<RECIPE extends AdvancedMachineRecipe<RECIPE>, WRAPPER extends AdvancedMachineRecipeWrapper<RECIPE>>
    extends BaseRecipeCategory<WRAPPER> {

    public AdvancedMachineRecipeCategory(IGuiHelper helper, String name, String unlocalized,
        GuiProgress.ProgressBar progress) {
        super(helper, "mekanism:gui/GuiAdvancedMachine.png", name, unlocalized, progress, 28, 16, 144, 54);
    }

    @Override
    protected void addGuiElements() {
        guiElements.add(new GuiSlot(GuiSlot.SlotType.INPUT, this, guiLocation, 55, 16));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.POWER, this, guiLocation, 30, 34).with(GuiSlot.SlotOverlay.POWER));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.EXTRA, this, guiLocation, 55, 52));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.OUTPUT_LARGE, this, guiLocation, 111, 30));
        guiElements.add(new GuiPowerBar(this, new GuiPowerBar.IPowerInfoHandler() {

            @Override
            public double getLevel() {
                return 1F;
            }
        }, guiLocation, 164, 15));
        guiElements.add(new GuiProgress(new GuiProgress.IProgressInfoHandler() {

            @Override
            public double getProgress() {
                return (double) timer.getValue() / 20F;
            }
        }, progressBar, this, guiLocation, 77, 37));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients) {
        AdvancedMachineRecipe<?> tempRecipe = recipeWrapper.getRecipe();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, true, 27, 0);
        itemStacks.init(1, false, 87, 18);
        itemStacks.init(2, false, 27, 36);
        itemStacks.set(0, tempRecipe.recipeInput.itemStack);
        itemStacks.set(1, tempRecipe.recipeOutput.output);
        itemStacks.set(2, recipeWrapper.getFuelStacks(tempRecipe.recipeInput.gasType));
        IGuiIngredientGroup<GasStack> gasStacks = recipeLayout.getIngredientsGroup(MekanismPlugin.TYPE_GAS);
        initGas(gasStacks, 0, true, 33, 21, 6, 12, new GasStack(tempRecipe.recipeInput.gasType, 210), false);
    }
}
