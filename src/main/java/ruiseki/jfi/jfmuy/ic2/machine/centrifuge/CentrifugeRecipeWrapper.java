package ruiseki.jfi.jfmuy.ic2.machine.centrifuge;

import net.minecraft.client.Minecraft;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ruiseki.jfi.jfmuy.ic2.machine.MachineRecipeWrapper;

public class CentrifugeRecipeWrapper extends MachineRecipeWrapper {

    private final int minHeat;

    public CentrifugeRecipeWrapper(IRecipeInput input, RecipeOutput output) {
        super(input, output);

        if (output.metadata != null && output.metadata.hasKey("minHeat")) {
            this.minHeat = output.metadata.getInteger("minHeat");
        } else {
            this.minHeat = 0;
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString("heat:", 32, 24, 0x404040);
        minecraft.fontRenderer.drawString(String.valueOf(minHeat), 32, 40, 0x404040);
    }
}
