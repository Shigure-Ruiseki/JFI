package ruiseki.jfi.jfmuy.tconstruct.melting;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.util.ColorUtils;

public class MeltingRecipeWrapper implements IRecipeWrapper {

    private final List<ItemStack> inputs;
    private final FluidStack outputFluid;
    private final int temperature;

    public MeltingRecipeWrapper(ItemStack input) {
        this(Collections.singletonList(input));
    }

    public MeltingRecipeWrapper(List<ItemStack> inputs) {
        this.inputs = inputs;
        ItemStack sample = (inputs != null && !inputs.isEmpty()) ? inputs.get(0) : null;

        if (sample != null) {
            this.temperature = Smeltery.getLiquifyTemperature(sample);
            this.outputFluid = Smeltery.getSmelteryResult(sample);
        } else {
            this.temperature = 0;
            this.outputFluid = null;
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(inputs));
        if (outputFluid != null) {
            ingredients.setOutput(VanillaTypes.FLUID, outputFluid);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        String tempStr = temperature + " C";
        int stringWidth = minecraft.fontRenderer.getStringWidth(tempStr);

        minecraft.fontRenderer
            .drawString(tempStr, 81 - stringWidth / 2, 9, ColorUtils.neiTemperature.getColor(), false);
    }

    public FluidStack getOutputFluid() {
        return this.outputFluid;
    }

    public int getTemperature() {
        return this.temperature;
    }
}
