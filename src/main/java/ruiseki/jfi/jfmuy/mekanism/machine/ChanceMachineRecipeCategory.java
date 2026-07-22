package ruiseki.jfi.jfmuy.mekanism.machine;

import mekanism.client.gui.element.GuiPowerBar;
import mekanism.client.gui.element.GuiProgress;
import mekanism.client.gui.element.GuiSlot;
import mekanism.common.recipe.machines.ChanceMachineRecipe;
import mekanism.common.recipe.outputs.ChanceOutput;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class ChanceMachineRecipeCategory<RECIPE extends ChanceMachineRecipe<RECIPE>, WRAPPER extends ChanceMachineRecipeWrapper<RECIPE>>
    extends BaseRecipeCategory<WRAPPER> {

    public ChanceMachineRecipeCategory(IGuiHelper helper, String name, String unlocalized,
        GuiProgress.ProgressBar progress) {
        super(helper, "mekanism:gui/GuiBasicMachine.png", name, unlocalized, progress, 28, 16, 144, 54);
    }

    @Override
    protected void addGuiElements() {
        guiElements.add(new GuiSlot(GuiSlot.SlotType.INPUT, this, guiLocation, 55, 16));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.POWER, this, guiLocation, 55, 52).with(GuiSlot.SlotOverlay.POWER));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.OUTPUT_WIDE, this, guiLocation, 111, 30));
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
        ChanceMachineRecipe<?> tempRecipe = recipeWrapper.getRecipe();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, true, 27, 0);
        itemStacks.init(1, false, 87, 18);
        itemStacks.init(2, false, 103, 18);
        itemStacks.set(0, tempRecipe.recipeInput.ingredient);
        ChanceOutput output = tempRecipe.getOutput();
        if (output.hasPrimary()) {
            itemStacks.set(1, output.primaryOutput);
        }
        if (output.hasSecondary()) {
            itemStacks.set(2, output.secondaryOutput);
        }
    }
}
