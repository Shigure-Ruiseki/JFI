package ruiseki.jfi.jfmuy.ic2.scrapbox;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import ic2.core.Ic2Items;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class ScrapboxRecipeWrapper implements IRecipeWrapper {

    private static final DecimalFormat CHANCE_FORMAT = new DecimalFormat("0.##%");

    private final List<ItemStack> inputs;
    private final List<ItemStack> outputs;
    private final float chance;

    public ScrapboxRecipeWrapper(ItemStack outputStack, float chance, float totalWeight) {
        this.inputs = Collections.singletonList(Ic2Items.scrapBox.copy());
        this.outputs = Collections.singletonList(outputStack);
        this.chance = totalWeight > 0 ? (chance / totalWeight) : 0.0f;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, inputs);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        String chanceText = getFormattedChance();
        int textX = 80 - (minecraft.fontRenderer.getStringWidth(chanceText) / 2);
        int textY = 11;

        minecraft.fontRenderer.drawString(chanceText, textX, textY, 0x808080);
    }

    public float getChance() {
        return chance;
    }

    public String getFormattedChance() {
        return CHANCE_FORMAT.format(chance);
    }
}
