package ruiseki.jfi.jfmuy.thermalexpansion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import cofh.lib.util.helpers.StringHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public abstract class BaseRecipeWrapper implements IRecipeWrapper {

    protected int energy;
    protected String uId;

    protected IDrawableAnimated energyMeter;

    public String getUid() {

        return uId;
    }

    @Nullable
    public List<String> getTooltipStrings(int mouseX, int mouseY) {

        List<String> tooltip = new ArrayList<>();

        if (energyMeter != null && mouseX > 2 && mouseX < 15 && mouseY > 8 && mouseY < 49) {
            tooltip.add(StringHelper.localize("info.cofh.energy") + ": " + energy + " RF");
        }
        return tooltip;
    }

}
