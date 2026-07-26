package ruiseki.jfi.jfmuy.mfr.slaughterhouse;

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

public class SlaughterhouseWrapper implements IRecipeWrapper {

    private final List<FluidStack> outputFluids = new ArrayList<>();

    public SlaughterhouseWrapper() {
        FluidStack meat = FluidRegistry.getFluidStack("meat", 4000);
        FluidStack pinkSlime = FluidRegistry.getFluidStack("pinkslime", 4000);

        if (meat != null) {
            this.outputFluids.add(meat);
        }
        if (pinkSlime != null) {
            this.outputFluids.add(pinkSlime);
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (!this.outputFluids.isEmpty()) {
            ingredients.setOutputs(VanillaTypes.FLUID, this.outputFluids);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        ResourceLocation overlayTexture = new ResourceLocation("jfi", "textures/gui/mfr/overlays.png");
        minecraft.getTextureManager()
            .bindTexture(overlayTexture);
        minecraft.currentScreen.drawTexturedModalRect(28, 24, 0, 64, 16, 16);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 28 && mouseX <= 44 && mouseY >= 24 && mouseY <= 40) {
            List<String> tooltip = new ArrayList<>();
            tooltip.add(StatCollector.translateToLocal("jfi.mfr.slaughterhouse.animals"));
            tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("jfi.mfr.slaughterhouse.animals.1"));
            return tooltip;
        }
        return Collections.emptyList();
    }
}
