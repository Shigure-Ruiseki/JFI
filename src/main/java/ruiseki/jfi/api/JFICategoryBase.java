package ruiseki.jfi.api;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import ruiseki.jfi.Reference;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public abstract class JFICategoryBase<T extends IRecipeWrapper> implements IRecipeCategory<T> {

    private final IDrawable background;
    protected IDrawableAnimated progress;
    protected final int arrowX, arrowY;

    public JFICategoryBase(IGuiHelper guiHelper, boolean animateArrow, int width, int height, int arrowX, int arrowY) {
        ResourceLocation texture = getTexture();
        background = guiHelper.createDrawable(texture, 0, 0, width, height);
        if (animateArrow) {
            IDrawableStatic progressDrawable = guiHelper.createDrawable(texture, 0, height, 24, 16);
            progress = guiHelper
                .createAnimatedDrawable(progressDrawable, 300, IDrawableAnimated.StartDirection.LEFT, false);
        }
        this.arrowX = arrowX;
        this.arrowY = arrowY;
    }

    public abstract ResourceLocation getTexture();

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        if (progress != null) progress.draw(minecraft, arrowX, arrowY);
    }

    @Override
    public String getModName() {
        return Reference.MOD_NAME;
    }

}
