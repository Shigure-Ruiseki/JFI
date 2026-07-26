package ruiseki.jfi.jfmuy.mfr.sewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SewerWrapper implements IRecipeWrapper {

    private final boolean essenceRecipe;
    private final FluidStack outputFluid;

    public SewerWrapper(boolean essenceRecipe) {
        this.essenceRecipe = essenceRecipe;
        if (!essenceRecipe) {
            this.outputFluid = FluidRegistry.getFluidStack("sewage", 4000);
        } else {
            this.outputFluid = FluidRegistry.getFluidStack("mobessence", 4000);
        }
    }

    public boolean isEssenceRecipe() {
        return essenceRecipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (this.outputFluid != null) {
            ingredients.setOutput(VanillaTypes.FLUID, this.outputFluid);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        ResourceLocation overlayTexture = new ResourceLocation("jfi", "textures/gui/mfr/overlays.png");

        if (!essenceRecipe) {
            minecraft.getTextureManager()
                .bindTexture(overlayTexture);
            minecraft.currentScreen.drawTexturedModalRect(48, 24, 0, 64, 16, 16);
        } else {
            minecraft.getTextureManager()
                .bindTexture(overlayTexture);
            minecraft.currentScreen.drawTexturedModalRect(48, 24, 0, 80, 16, 16);
        }
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 48 && mouseX <= 64 && mouseY >= 24 && mouseY <= 40) {
            List<String> tooltip = new ArrayList<>();
            if (!essenceRecipe) {
                tooltip.add(StatCollector.translateToLocal("jfi.mfr.sewer.animals"));
                tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("jfi.mfr.sewer.animals.1"));
            } else {
                tooltip.add(StatCollector.translateToLocal("jfi.mfr.sewer.xp"));
                tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("jfi.mfr.sewer.xp.1"));
            }
            return tooltip;
        }
        return Collections.emptyList();
    }
}
