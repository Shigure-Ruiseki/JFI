package ruiseki.jfi.jfmuy.mekanism.machine.chemical;

import net.minecraft.client.Minecraft;

import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.machines.ChemicalInfuserRecipe;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiIngredientGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class ChemicalInfuserRecipeCategory<WRAPPER extends ChemicalInfuserRecipeWrapper>
    extends BaseRecipeCategory<WRAPPER> {

    public ChemicalInfuserRecipeCategory(IGuiHelper helper) {
        super(
            helper,
            "mekanism:gui/nei/GuiChemicalInfuser.png",
            RecipeHandler.Recipe.CHEMICAL_INFUSER.getRecipeName(),
            "tile.MachineBlock2.ChemicalInfuser.name",
            null,
            3,
            3,
            170,
            80);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
        drawTexturedRect(47 - xOffset, 39 - yOffset, 176, 71, 28, 8);
        drawTexturedRect(101 - xOffset, 39 - yOffset, 176, 63, 28, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients) {
        ChemicalInfuserRecipe tempRecipe = recipeWrapper.getRecipe();
        IGuiIngredientGroup<GasStack> gasStacks = recipeLayout.getIngredientsGroup(MekanismPlugin.TYPE_GAS);
        initGas(gasStacks, 0, true, 26 - xOffset, 14 - yOffset, 16, 58, tempRecipe.getInput().leftGas, true);
        initGas(gasStacks, 1, true, 134 - xOffset, 14 - yOffset, 16, 58, tempRecipe.getInput().rightGas, true);
        initGas(gasStacks, 2, false, 80 - xOffset, 5 - yOffset, 16, 58, tempRecipe.getOutput().output, true);
    }
}
