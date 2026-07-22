package ruiseki.jfi.jfmuy.mekanism.machine.chemical;

import net.minecraft.client.Minecraft;

import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.machines.CrystallizerRecipe;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiIngredientGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class ChemicalCrystallizerRecipeCategory<WRAPPER extends ChemicalCrystallizerRecipeWrapper>
    extends BaseRecipeCategory<WRAPPER> {

    public ChemicalCrystallizerRecipeCategory(IGuiHelper helper) {
        super(
            helper,
            "mekanism:gui/nei/GuiChemicalCrystallizer.png",
            RecipeHandler.Recipe.CHEMICAL_CRYSTALLIZER.getRecipeName(),
            "tile.MachineBlock2.ChemicalCrystallizer.name",
            null,
            5,
            3,
            147,
            79);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
        drawTexturedRect(53 - xOffset, 61 - yOffset, 176, 63, (int) (48 * ((float) timer.getValue() / 20F)), 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients) {
        CrystallizerRecipe tempRecipe = recipeWrapper.getRecipe();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, false, 130 - xOffset, 56 - yOffset);
        itemStacks.set(0, tempRecipe.getOutput().output);
        IGuiIngredientGroup<GasStack> gasStacks = recipeLayout.getIngredientsGroup(MekanismPlugin.TYPE_GAS);
        initGas(gasStacks, 0, true, 6 - xOffset, 5 - yOffset, 16, 58, tempRecipe.getInput().ingredient, true);
    }
}
