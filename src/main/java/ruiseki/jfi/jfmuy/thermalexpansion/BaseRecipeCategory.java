package ruiseki.jfi.jfmuy.thermalexpansion;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cofh.thermalexpansion.ThermalExpansion;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public abstract class BaseRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

    protected IDrawableStatic background;
    protected IDrawableStatic energyMeter;
    protected IDrawable icon;
    protected String localizedName;

    public BaseRecipeCategory() {

    }

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
    public List<String> getTooltipStrings(int mouseX, int mouseY) {

        return Collections.emptyList();
    }

}
