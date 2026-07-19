package ruiseki.jfi.jfmuy.enderio.sagmill;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import crazypants.enderio.EnderIO;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;

public class SagMillRecipeCategory implements IRecipeCategory<SagMillRecipeWrapper> {

    public static final String UID = "EnderIOSagMill";

    private final IDrawable background;
    private final IDrawableAnimated progressArrow;
    private final IDrawableAnimated ballProgress;

    public SagMillRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation backgroundLocation = new ResourceLocation("enderio:textures/gui/nei/crusher.png");

        this.background = guiHelper.createDrawable(backgroundLocation, 20, 0, 128, 65);

        IDrawableAnimated.StartDirection down = IDrawableAnimated.StartDirection.TOP;
        this.progressArrow = guiHelper
            .createAnimatedDrawable(guiHelper.createDrawable(backgroundLocation, 166, 0, 17, 24), 40, down, false);

        IDrawableAnimated.StartDirection up = IDrawableAnimated.StartDirection.TOP;
        this.ballProgress = guiHelper
            .createAnimatedDrawable(guiHelper.createDrawable(backgroundLocation, 166, 24, 4, 16), 160, up, true);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("enderio.nei.sagmill");
    }

    @Override
    public String getModName() {
        return EnderIO.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        progressArrow.draw(minecraft, 53, 20);
        ballProgress.draw(minecraft, 116, 12);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, final SagMillRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 53, 1);
        guiItemStacks.init(1, false, 22, 45);
        guiItemStacks.init(2, false, 43, 45);
        guiItemStacks.init(3, false, 64, 45);
        guiItemStacks.init(4, false, 85, 45);
        guiItemStacks.init(5, true, 95, 11);

        guiItemStacks.set(ingredients);

        guiItemStacks.addTooltipCallback(recipeWrapper);
    }
}
