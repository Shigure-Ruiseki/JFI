package ruiseki.jfi.jfmuy.nei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class NEITemplateWrapper implements IRecipeWrapper {

    @NotNull
    private final TemplateRecipeHandler handler;
    private final int recipeIndex;

    public NEITemplateWrapper(@NotNull TemplateRecipeHandler handler, int recipeIndex) {
        this.handler = handler;
        this.recipeIndex = recipeIndex;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputLists = new ArrayList<>();

        List<PositionedStack> inputs = handler.getIngredientStacks(recipeIndex);
        if (inputs != null) {
            for (PositionedStack stack : inputs) {
                if (stack != null && stack.items != null && stack.items.length > 0) {
                    inputLists.add(Arrays.asList(stack.items));
                }
            }
        }

        List<PositionedStack> otherStacks = handler.getOtherStacks(recipeIndex);
        if (otherStacks != null) {
            for (PositionedStack stack : otherStacks) {
                if (stack != null && stack.items != null && stack.items.length > 0) {
                    inputLists.add(Arrays.asList(stack.items));
                }
            }
        }
        ingredients.setInputLists(VanillaTypes.ITEM, inputLists);

        PositionedStack result = handler.getResultStack(recipeIndex);
        if (result != null && result.items != null && result.items.length > 0) {
            ingredients.setOutputs(VanillaTypes.ITEM, Arrays.asList(result.items));
        } else {
            ingredients.setOutputs(VanillaTypes.ITEM, Collections.emptyList());
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (recipeIndex >= 0 && recipeIndex < handler.numRecipes()) {
            handler.cycleticks = (int) (System.currentTimeMillis() / 50L);
            try {
                handler.drawBackground(recipeIndex);
                handler.drawForeground(recipeIndex);
            } catch (Throwable ignored) {}
        }
    }

    public TemplateRecipeHandler getHandler() {
        return handler;
    }

    public int getRecipeIndex() {
        return recipeIndex;
    }
}
