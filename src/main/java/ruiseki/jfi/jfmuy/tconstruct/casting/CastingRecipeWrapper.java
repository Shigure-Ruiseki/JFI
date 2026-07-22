package ruiseki.jfi.jfmuy.tconstruct.casting;

import java.util.Collections;

import net.minecraft.client.Minecraft;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import tconstruct.library.crafting.CastingRecipe;

public class CastingRecipeWrapper implements IRecipeWrapper {

    private final CastingRecipe recipe;

    public CastingRecipeWrapper(CastingRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (recipe.cast != null) {
            ingredients.setInputs(VanillaTypes.ITEM, Collections.singletonList(recipe.cast));
        } else {
            ingredients.setInputs(VanillaTypes.ITEM, Collections.emptyList());
        }

        if (recipe.castingMetal != null) {
            ingredients.setInputs(VanillaTypes.FLUID, Collections.singletonList(recipe.castingMetal));
        }

        if (recipe.output != null) {
            ingredients.setOutput(VanillaTypes.ITEM, recipe.output);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (recipe.coolTime > 0) {
            float seconds = recipe.coolTime / 20.0F;
            String timeStr = String.format("%.1fs", seconds);
            minecraft.fontRenderer
                .drawString(timeStr, 58 - minecraft.fontRenderer.getStringWidth(timeStr) / 2, 42, 0x777777, false);
        }
    }

    public CastingRecipe getRecipe() {
        return this.recipe;
    }
}
