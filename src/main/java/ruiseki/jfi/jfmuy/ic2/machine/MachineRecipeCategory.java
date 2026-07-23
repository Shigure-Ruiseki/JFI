package ruiseki.jfi.jfmuy.ic2.machine;

import net.minecraft.util.ResourceLocation;

import ic2.core.IC2;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;

public abstract class MachineRecipeCategory<T extends MachineRecipeWrapper> implements IRecipeCategory<T> {

    protected static final int INPUT_SLOT = 0;
    protected static final int OUTPUT_SLOT_START = 1;

    protected final IDrawableStatic background;
    protected final IDrawableAnimated progressBar;
    protected final IDrawableAnimated energyBar;

    public MachineRecipeCategory(IGuiHelper guiHelper, String guiTexture) {
        this(guiHelper, guiTexture, 7, 14, 140, 65);
    }

    public MachineRecipeCategory(IGuiHelper guiHelper, String guiTexture, int u, int v, int width, int height) {
        ResourceLocation location = new ResourceLocation(guiTexture);

        this.background = guiHelper.createDrawable(location, u, v, width, height);

        IDrawableStatic progressDrawable = guiHelper.createDrawable(location, 176, 14, 24, 17);
        this.progressBar = guiHelper
            .createAnimatedDrawable(progressDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);

        IDrawableStatic energyDrawable = guiHelper.createDrawable(location, 176, 0, 14, 14);
        this.energyBar = guiHelper
            .createAnimatedDrawable(energyDrawable, 300, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    protected int getInputPosX() {
        return 51 - 3;
    }

    protected int getInputPosY() {
        return 6 - 4;
    }

    protected int getOutputPosX() {
        return 111 - 3;
    }

    protected int getOutputPosY() {
        return 24 - 4;
    }

    protected boolean isOutputsVertical() {
        return true;
    }

    @Override
    public String getModName() {
        return IC2.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(net.minecraft.client.Minecraft minecraft) {
        this.progressBar.draw(minecraft, 74 - 3, 23 - 4);
        this.energyBar.draw(minecraft, 51 - 3, 25 - 4);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, T recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(INPUT_SLOT, true, getInputPosX(), getInputPosY());

        int outputCount = recipeWrapper.getOutputs()
            .size();
        for (int i = 0; i < outputCount; i++) {
            int x = getOutputPosX();
            int y = getOutputPosY();

            if (isOutputsVertical()) {
                y += i * 18;
            } else {
                x += i * 18;
            }

            guiItemStacks.init(OUTPUT_SLOT_START + i, false, x, y);
        }

        guiItemStacks.set(ingredients);
    }
}
