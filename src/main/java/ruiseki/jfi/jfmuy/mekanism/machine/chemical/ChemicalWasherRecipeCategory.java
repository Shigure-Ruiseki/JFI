package ruiseki.jfi.jfmuy.mekanism.machine.chemical;

import net.minecraft.client.Minecraft;

import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.machines.WasherRecipe;
import mekanism.common.tile.TileEntityChemicalWasher;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiIngredientGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class ChemicalWasherRecipeCategory<WRAPPER extends ChemicalWasherRecipeWrapper>
    extends BaseRecipeCategory<WRAPPER> {

    public ChemicalWasherRecipeCategory(IGuiHelper helper) {
        super(
            helper,
            "mekanism:gui/nei/GuiChemicalWasher.png",
            RecipeHandler.Recipe.CHEMICAL_WASHER.getRecipeName(),
            "tile.MachineBlock2.ChemicalWasher.name",
            null,
            3,
            3,
            170,
            70);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
        drawTexturedRect(61 - xOffset, 39 - yOffset, 176, 63, 55, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients) {
        WasherRecipe tempRecipe = recipeWrapper.getRecipe();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        fluidStacks.init(
            0,
            true,
            6 - xOffset,
            5 - yOffset,
            16,
            58,
            TileEntityChemicalWasher.WATER_USAGE,
            false,
            fluidOverlayLarge);
        fluidStacks.set(
            0,
            ingredients.getInputs(VanillaTypes.FLUID)
                .get(0));
        IGuiIngredientGroup<GasStack> gasStacks = recipeLayout.getIngredientsGroup(MekanismPlugin.TYPE_GAS);
        initGas(gasStacks, 0, true, 27 - xOffset, 14 - yOffset, 16, 58, tempRecipe.getInput().ingredient, true);
        initGas(gasStacks, 1, false, 134 - xOffset, 14 - yOffset, 16, 58, tempRecipe.getOutput().output, true);
    }
}
