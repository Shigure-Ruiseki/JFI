package ruiseki.jfi.jfmuy.thermalexpansion.dynamo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;

import cofh.thermalexpansion.ThermalExpansion;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public abstract class BaseFuelCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

    protected IDrawableStatic background;
    protected IDrawableStatic energyMeter;
    protected IDrawableStatic durationEmpty;
    protected IDrawable icon;
    protected String localizedName;

    @Nonnull
    @Override
    public String getTitle() {

        return localizedName;
    }

    @Override
    public String getModName() {

        return ThermalExpansion.modName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {

        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {

        return icon;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

        energyMeter.draw(minecraft, 71, 7);
        durationEmpty.draw(minecraft, 34, 43);
    }

}
