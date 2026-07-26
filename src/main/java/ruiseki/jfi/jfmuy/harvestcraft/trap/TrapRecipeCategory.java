package ruiseki.jfi.jfmuy.harvestcraft.trap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import com.pam.harvestcraft.TrapRecipes;

import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;

public abstract class TrapRecipeCategory implements IRecipeCategory<TrapRecipeWrapper> {

    @FunctionalInterface
    public interface WrapperFactory {

        TrapRecipeWrapper create(ItemStack in, ItemStack out, double chance);
    }

    private final IDrawable background;

    public TrapRecipeCategory(IGuiHelper help, String png) {
        this.background = help
            .drawableBuilder(
                new ResourceLocation("jfi", "textures/gui/harvestcraft/" + png + "_trap_small.png"),
                0,
                0,
                81,
                29)
            .setTextureSize(81, 29)
            .build();
    }

    public static List<TrapRecipeWrapper> buildRecipes(Map<Item, List<TrapRecipes.WeightedItemStack>> recipeMap,
        WrapperFactory factory) {
        List<TrapRecipeWrapper> recipes = new ArrayList<>();

        if (recipeMap == null) return recipes;

        for (Map.Entry<Item, List<TrapRecipes.WeightedItemStack>> entry : recipeMap.entrySet()) {
            Item baitItem = entry.getKey();
            List<TrapRecipes.WeightedItemStack> outputs = entry.getValue();

            if (baitItem == null || outputs == null || outputs.isEmpty()) {
                continue;
            }

            int totalWeight = 0;
            for (TrapRecipes.WeightedItemStack weighted : outputs) {
                if (weighted != null) {
                    totalWeight += weighted.itemWeight;
                }
            }

            if (totalWeight <= 0) {
                continue;
            }

            ItemStack baitStack = new ItemStack(baitItem);

            for (TrapRecipes.WeightedItemStack weighted : outputs) {
                if (weighted != null && weighted.stack != null) {
                    double chance = (double) weighted.itemWeight / totalWeight;
                    recipes.add(factory.create(baitStack, weighted.stack, chance));
                }
            }
        }

        return recipes;
    }

    @Override
    public String getModName() {
        return "Pam's HarvestCraft";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, TrapRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 22, 8);
        itemStacks.init(1, false, 58, 8);

        itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (slotIndex == 1) {
                double chance = recipeWrapper.getChance();
                String formattedChance = String.format("%.2f%%", chance * 100.0);
                tooltip.add(EnumChatFormatting.GRAY + "Chance: " + EnumChatFormatting.GOLD + formattedChance);
            }
        });

        itemStacks.set(ingredients);
    }
}
