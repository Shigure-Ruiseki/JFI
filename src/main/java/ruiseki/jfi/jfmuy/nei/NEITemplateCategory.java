package ruiseki.jfi.jfmuy.nei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipeTab;
import codechicken.nei.recipe.HandlerInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;

public class NEITemplateCategory implements IRecipeCategory<NEITemplateWrapper> {

    private final String uid;
    private final String title;
    private final IDrawable background;

    public NEITemplateCategory(IGuiHelper guiHelper, TemplateRecipeHandler handler, String recipeId) {
        this.title = handler.getRecipeName();
        this.uid = recipeId;

        int width = 166;
        int height = 65;

        try {
            HandlerInfo info = GuiRecipeTab.getHandlerInfo(handler.getHandlerId(), recipeId);
            if (info != null) {
                width = info.getWidth();
                height = info.getHeight();
            }
        } catch (Throwable ignored) {}

        this.background = guiHelper.createBlankDrawable(width, height);
    }

    @Override
    public String getUid() {
        return this.uid;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getModName() {
        return "Not Enough Items";
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, NEITemplateWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        int slotIndex = 0;
        int recipeIdx = recipeWrapper.getRecipeIndex();
        TemplateRecipeHandler currentHandler = recipeWrapper.getHandler();

        if (currentHandler == null || recipeIdx < 0 || recipeIdx >= currentHandler.numRecipes()) {
            return;
        }

        List<PositionedStack> inputs = currentHandler.getIngredientStacks(recipeIdx);
        if (inputs != null) {
            for (PositionedStack stack : inputs) {
                if (isValidStack(stack)) {
                    itemStacks.init(slotIndex, true, stack.relx - 1, stack.rely - 1);
                    itemStacks.set(slotIndex, extractStacks(stack));
                    slotIndex++;
                }
            }
        }

        PositionedStack result = currentHandler.getResultStack(recipeIdx);
        if (isValidStack(result)) {
            itemStacks.init(slotIndex, false, result.relx - 1, result.rely - 1);
            itemStacks.set(slotIndex, extractStacks(result));
            slotIndex++;
        }

        List<PositionedStack> otherStacks = currentHandler.getOtherStacks(recipeIdx);
        if (otherStacks != null) {
            for (PositionedStack stack : otherStacks) {
                if (isValidStack(stack)) {
                    itemStacks.init(slotIndex, true, stack.relx - 1, stack.rely - 1);
                    itemStacks.set(slotIndex, extractStacks(stack));
                    slotIndex++;
                }
            }
        }
    }

    private boolean isValidStack(PositionedStack stack) {
        if (stack == null) return false;
        if (stack.items != null && stack.items.length > 0) {
            for (ItemStack is : stack.items) {
                if (is != null) return true;
            }
        }
        return stack.item != null;
    }

    private List<ItemStack> extractStacks(PositionedStack pStack) {
        if (pStack == null) return Collections.emptyList();

        if (pStack.items != null && pStack.items.length > 0) {
            List<ItemStack> list = new ArrayList<>(pStack.items.length);
            for (ItemStack is : pStack.items) {
                if (is != null) {
                    list.add(is.copy());
                }
            }
            return list;
        } else if (pStack.item != null) {
            return Collections.singletonList(pStack.item.copy());
        }
        return Collections.emptyList();
    }
}
