package ruiseki.jfi.jfmuy.tconstruct.dryingrack;

import java.util.Collections;

import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import tconstruct.library.crafting.DryingRackRecipes.DryingRecipe;

public class DryingRackRecipeWrapper implements IRecipeWrapper {

    private final DryingRecipe recipe;

    public DryingRackRecipeWrapper(DryingRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (recipe.input != null) {
            ingredients.setInputs(VanillaTypes.ITEM, Collections.singletonList(recipe.input));
        }
        if (recipe.result != null) {
            ingredients.setOutput(VanillaTypes.ITEM, recipe.result);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int time = recipe.time;
        int seconds = time / 20;

        String durationStr = StatCollector
            .translateToLocalFormatted("tconstruct.nei.dryingrack.duration", time, seconds);

        int stringWidth = minecraft.fontRenderer.getStringWidth(durationStr);
        minecraft.fontRenderer.drawString(durationStr, 61 - stringWidth / 2, 40, 0x808080, false);
    }

    public DryingRecipe getRecipe() {
        return this.recipe;
    }
}
