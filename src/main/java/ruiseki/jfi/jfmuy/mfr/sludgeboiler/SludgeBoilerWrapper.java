package ruiseki.jfi.jfmuy.mfr.sludgeboiler;

import java.text.NumberFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SludgeBoilerWrapper implements IRecipeWrapper {

    private final FluidStack inputFluid;
    private final ItemStack outputItem;
    private final float chance;

    public SludgeBoilerWrapper(FluidStack inputFluid, ItemStack outputItem, int weight, int totalWeight) {
        this.inputFluid = inputFluid;
        this.outputItem = outputItem;
        this.chance = totalWeight > 0 ? (float) weight / (float) totalWeight : 0.0F;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (this.inputFluid != null) {
            ingredients.setInput(VanillaTypes.FLUID, this.inputFluid);
        }
        if (this.outputItem != null) {
            ingredients.setOutput(VanillaTypes.ITEM, this.outputItem);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(2);
        String chanceString = percentFormat.format(this.chance);

        int stringWidth = minecraft.fontRenderer.getStringWidth(chanceString);
        minecraft.fontRenderer.drawString(chanceString, 57 - stringWidth / 2, 44, 0x808080);
    }
}
