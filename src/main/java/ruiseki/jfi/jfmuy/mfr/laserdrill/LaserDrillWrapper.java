package ruiseki.jfi.jfmuy.mfr.laserdrill;

import java.text.NumberFormat;
import java.util.Collections;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class LaserDrillWrapper implements IRecipeWrapper {

    private final ItemStack output;
    private final ItemStack focus;
    private final float chance;

    public LaserDrillWrapper(ItemStack output, int weight, int totalWeight, ItemStack focus) {
        this.output = output;
        this.focus = focus;
        this.chance = (float) weight / (float) totalWeight;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (this.focus != null) {
            ingredients.setInput(VanillaTypes.ITEM, this.focus);
        } else {
            ingredients.setInputs(VanillaTypes.ITEM, Collections.emptyList());
        }
        if (this.output != null) {
            ingredients.setOutput(VanillaTypes.ITEM, this.output);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(2);
        String chanceString = percentFormat.format(this.chance);

        int stringWidth = minecraft.fontRenderer.getStringWidth(chanceString);
        minecraft.fontRenderer.drawString(chanceString, 83 - stringWidth / 2, 44, 0x808080);
    }
}
