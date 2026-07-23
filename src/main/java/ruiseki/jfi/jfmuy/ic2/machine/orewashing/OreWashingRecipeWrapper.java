package ruiseki.jfi.jfmuy.ic2.machine.orewashing;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class OreWashingRecipeWrapper extends MachineRecipeWrapper {

    private final int reqWater;
    private final FluidStack waterInput;

    public OreWashingRecipeWrapper(IRecipeInput input, RecipeOutput output) {
        super(input, output);

        if (output.metadata != null && output.metadata.hasKey("amount")) {
            this.reqWater = output.metadata.getInteger("amount");
        } else {
            this.reqWater = 0;
        }

        this.waterInput = new FluidStack(FluidRegistry.WATER, this.reqWater);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        super.getIngredients(ingredients);
        if (this.reqWater > 0) {
            ingredients.setInput(VanillaTypes.FLUID, this.waterInput);
        }
    }

    public int getReqWater() {
        return reqWater;
    }

    public FluidStack getWaterInput() {
        return waterInput;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString("require:", 8, 20, 0x404040);
        minecraft.fontRenderer.drawString("water", 8, 33, 0x404040);
        minecraft.fontRenderer.drawString(reqWater + "mb", 8, 46, 0x404040);
    }
}
