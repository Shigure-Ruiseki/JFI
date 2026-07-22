package ruiseki.jfi.jfmuy.mekanism.machine.other;

import net.minecraft.client.Minecraft;

import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.machines.ThermalEvaporationRecipe;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class ThermalEvaporationRecipeCategory<WRAPPER extends ThermalEvaporationRecipeWrapper>
    extends BaseRecipeCategory<WRAPPER> {

    public ThermalEvaporationRecipeCategory(IGuiHelper helper) {
        super(
            helper,
            "mekanism:gui/nei/GuiThermalEvaporationController.png",
            RecipeHandler.Recipe.THERMAL_EVAPORATION_PLANT.getRecipeName(),
            "gui.thermalEvaporationController.short",
            null,
            3,
            12,
            170,
            62);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        super.drawExtras(minecraft);
        drawTexturedRect(49 - xOffset, 64 - yOffset, 176, 59, 78, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients) {
        ThermalEvaporationRecipe tempRecipe = recipeWrapper.getRecipe();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        fluidStacks.init(
            0,
            true,
            7 - xOffset,
            14 - yOffset,
            16,
            58,
            tempRecipe.getInput().ingredient.amount,
            false,
            fluidOverlayLarge);
        fluidStacks.init(
            1,
            false,
            153 - xOffset,
            14 - yOffset,
            16,
            58,
            tempRecipe.getOutput().output.amount,
            false,
            fluidOverlayLarge);
        fluidStacks.set(0, tempRecipe.recipeInput.ingredient);
        fluidStacks.set(1, tempRecipe.recipeOutput.output);
    }
}
