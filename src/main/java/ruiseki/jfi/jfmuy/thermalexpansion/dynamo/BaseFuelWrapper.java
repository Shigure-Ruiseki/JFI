package ruiseki.jfi.jfmuy.thermalexpansion.dynamo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;

import cofh.lib.util.helpers.StringHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public abstract class BaseFuelWrapper implements IRecipeWrapper {

    protected String uId;
    protected int energy;

    protected IDrawableAnimated durationFill;
    protected IDrawableAnimated energyMeter;

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

        durationFill.draw(minecraft, 34, 43);
        energyMeter.draw(minecraft, 71, 7);

        minecraft.fontRenderer.drawString(energy + " RF", 96, (recipeHeight - 9) / 2, 0x808080);
    }

    @Nullable
    public List<String> getTooltipStrings(int mouseX, int mouseY) {

        List<String> tooltip = new ArrayList<>();

        if (mouseX > 71 && mouseX < 84 && mouseY > 7 && mouseY < 48) {
            tooltip.add(StringHelper.localize("info.cofh.energy") + ": " + energy + " RF");
        }
        return tooltip;
    }

}
