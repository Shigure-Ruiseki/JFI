package ruiseki.jfi.jfmuy.mekanism.machine.chemical;

import net.minecraft.client.Minecraft;

import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.machines.DissolutionRecipe;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiIngredientGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class ChemicalDissolutionChamberRecipeCategory<WRAPPER extends ChemicalDissolutionChamberRecipeWrapper>
    extends BaseRecipeCategory<WRAPPER> {

    public ChemicalDissolutionChamberRecipeCategory(IGuiHelper helper) {
        super(
            helper,
            "mekanism:gui/nei/GuiChemicalDissolutionChamber.png",
            RecipeHandler.Recipe.CHEMICAL_DISSOLUTION_CHAMBER.getRecipeName(),
            "gui.chemicalDissolutionChamber.short",
            null,
            3,
            3,
            170,
            79);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
        drawTexturedRect(64 - xOffset, 40 - yOffset, 176, 63, (int) (48 * ((float) timer.getValue() / 20F)), 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients) {
        DissolutionRecipe tempRecipe = recipeWrapper.getRecipe();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, true, 25 - xOffset, 35 - yOffset);
        itemStacks.set(0, tempRecipe.getInput().ingredient);
        IGuiIngredientGroup<GasStack> gasStacks = recipeLayout.getIngredientsGroup(MekanismPlugin.TYPE_GAS);
        initGas(
            gasStacks,
            0,
            true,
            6 - xOffset,
            5 - yOffset,
            16,
            58,
            new GasStack(GasRegistry.getGas("sulfuricAcid"), ChemicalDissolutionChamberRecipeWrapper.INJECT_USAGE),
            true);
        initGas(gasStacks, 1, false, 134 - xOffset, 14 - yOffset, 16, 58, tempRecipe.getOutput().output, true);
    }
}
