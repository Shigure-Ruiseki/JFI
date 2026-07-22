package ruiseki.jfi.jfmuy.mekanism.machine.other;

import net.minecraft.client.Minecraft;

import mekanism.api.gas.GasStack;
import mekanism.common.recipe.RecipeHandler.Recipe;
import mekanism.common.recipe.machines.SolarNeutronRecipe;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfmuy.api.gui.IGuiIngredientGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class SolarNeutronRecipeCategory<WRAPPER extends SolarNeutronRecipeWrapper> extends BaseRecipeCategory<WRAPPER> {

    public SolarNeutronRecipeCategory(ruiseki.jfmuy.api.IGuiHelper helper) {
        super(
            helper,
            "mekanism:gui/nei/GuiSolarNeutronActivator.png",
            Recipe.SOLAR_NEUTRON_ACTIVATOR.getRecipeName(),
            "tile.MachineBlock3.SolarNeutronActivator.name",
            null,
            3,
            12,
            170,
            70);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
        drawTexturedRect(64 - xOffset, 39 - yOffset, 176, 58, 55, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients) {
        SolarNeutronRecipe tempRecipe = recipeWrapper.getRecipe();
        IGuiIngredientGroup<GasStack> gasStacks = recipeLayout.getIngredientsGroup(MekanismPlugin.TYPE_GAS);
        initGas(gasStacks, 0, true, 26 - xOffset, 14 - yOffset, 16, 58, tempRecipe.recipeInput.ingredient, true);
        initGas(gasStacks, 1, false, 134 - xOffset, 14 - yOffset, 16, 58, tempRecipe.recipeOutput.output, true);
    }
}
