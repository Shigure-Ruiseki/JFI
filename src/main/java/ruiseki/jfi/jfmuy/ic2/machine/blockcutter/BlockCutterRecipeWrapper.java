package ruiseki.jfi.jfmuy.ic2.machine.blockcutter;

import net.minecraft.client.Minecraft;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeWrapper;

public class BlockCutterRecipeWrapper extends MachineRecipeWrapper {

    private final int hardness;

    public BlockCutterRecipeWrapper(IRecipeInput input, RecipeOutput output) {
        super(input, output);

        if (output.metadata != null && output.metadata.hasKey("hardness")) {
            this.hardness = output.metadata.getInteger("hardness");
        } else {
            this.hardness = 0;
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        String hardnessText = "hardness required: " + hardness;
        minecraft.fontRenderer.drawString(hardnessText, 18, 60, 0x404040);
    }
}
