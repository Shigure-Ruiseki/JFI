package ruiseki.jfi.jfmuy.botania.runicaltar;

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
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.common.block.tile.mana.TilePool;

public class RunicAltarRecipeWrapper implements IRecipeWrapper {

    private final List<List<ItemStack>> input;
    private final ItemStack output;
    private final int manaUsage;

    public RunicAltarRecipeWrapper(RecipeRuneAltar recipe) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for (Object o : recipe.getInputs()) {
            if (o instanceof ItemStack) {
                builder.add(ImmutableList.of((ItemStack) o));
            }
            if (o instanceof String) {
                builder.add(OreDictionary.getOres((String) o));
            }
        }
        input = builder.build();
        output = recipe.getOutput();
        manaUsage = recipe.getManaUsage();
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, input);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        GlStateManager.enableAlpha();
        HUDHandler.renderManaBar(6, 98, 0x0000FF, 0.75F, manaUsage, TilePool.MAX_MANA / 10);
        GlStateManager.disableAlpha();
    }

}
