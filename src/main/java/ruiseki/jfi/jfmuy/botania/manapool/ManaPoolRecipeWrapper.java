package ruiseki.jfi.jfmuy.botania.manapool;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.ImmutableList;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import ruiseki.okcore.client.renderer.GlStateManager;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.mana.TilePool;

public class ManaPoolRecipeWrapper implements IRecipeWrapper {

    private final List<List<ItemStack>> input;
    private final ItemStack output;
    private final int mana;

    public ManaPoolRecipeWrapper(RecipeManaInfusion recipe) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();

        if (recipe.getInput() instanceof ItemStack) {
            builder.add(ImmutableList.of((ItemStack) recipe.getInput()));
        } else if (recipe.getInput() instanceof String) {
            builder.add(OreDictionary.getOres((String) recipe.getInput()));
        }

        if (recipe.isAlchemy()) {
            builder.add(ImmutableList.of(new ItemStack(ModBlocks.alchemyCatalyst)));
        } else if (recipe.isConjuration()) {
            builder.add(ImmutableList.of(new ItemStack(ModBlocks.conjurationCatalyst)));
        }

        input = builder.build();
        output = recipe.getOutput();
        mana = recipe.getManaToConsume();
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, input);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        GlStateManager.enableAlpha();
        HUDHandler.renderManaBar(20, 50, 0x0000FF, 0.75F, mana, TilePool.MAX_MANA / 10);
        GlStateManager.disableAlpha();
    }

    @Nonnull
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return ImmutableList.of();
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }

}
