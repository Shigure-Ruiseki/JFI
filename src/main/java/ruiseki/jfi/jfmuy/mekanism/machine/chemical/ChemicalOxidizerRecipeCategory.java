package ruiseki.jfi.jfmuy.mekanism.machine.chemical;

import mekanism.api.gas.GasStack;
import mekanism.client.gui.element.GuiGasGauge;
import mekanism.client.gui.element.GuiGauge;
import mekanism.client.gui.element.GuiProgress;
import mekanism.client.gui.element.GuiSlot;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.machines.OxidationRecipe;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiIngredientGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class ChemicalOxidizerRecipeCategory<WRAPPER extends ChemicalOxidizerRecipeWrapper>
    extends BaseRecipeCategory<WRAPPER> {

    public ChemicalOxidizerRecipeCategory(IGuiHelper helper) {
        super(
            helper,
            "mekanism:gui/GuiChemicalOxidizer.png",
            RecipeHandler.Recipe.CHEMICAL_OXIDIZER.getRecipeName(),
            "tile.MachineBlock2.ChemicalOxidizer.name",
            GuiProgress.ProgressBar.LARGE_RIGHT,
            20,
            12,
            132,
            62);
    }

    @Override
    protected void addGuiElements() {
        guiElements.add(GuiGasGauge.getDummy(GuiGauge.Type.STANDARD, this, guiLocation, 133, 13));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.NORMAL, this, guiLocation, 25, 35));
        guiElements.add(new GuiProgress(new GuiProgress.IProgressInfoHandler() {

            @Override
            public double getProgress() {
                return (double) timer.getValue() / 20F;
            }
        }, progressBar, this, guiLocation, 62, 39));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients) {
        OxidationRecipe tempRecipe = recipeWrapper.getRecipe();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, true, 25 - xOffset, 35 - yOffset);
        itemStacks.set(0, tempRecipe.getInput().ingredient);
        IGuiIngredientGroup<GasStack> gasStacks = recipeLayout.getIngredientsGroup(MekanismPlugin.TYPE_GAS);
        initGas(gasStacks, 0, false, 134 - xOffset, 14 - yOffset, 16, 58, tempRecipe.recipeOutput.output, true);
    }
}
