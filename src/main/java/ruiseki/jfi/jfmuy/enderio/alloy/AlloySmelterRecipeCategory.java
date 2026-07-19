package ruiseki.jfi.jfmuy.enderio.alloy;

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

public class AlloySmelterRecipeCategory implements IRecipeCategory<AlloySmelterRecipeWrapper> {

    public static final String UID = "EnderIOAlloySmelter";

    private final IDrawable background;
    private final IDrawableAnimated progressBarLeft;
    private final IDrawableAnimated progressBarRight;

    public AlloySmelterRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("enderio:textures/gui/nei/alloySmelter.png"), 27, 0, 120, 65);

        this.progressBarLeft = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(new ResourceLocation("enderio:textures/gui/nei/alloySmelter.png"), 166, 0, 22, 13),
            200,
            IDrawableAnimated.StartDirection.BOTTOM,
            false);

        this.progressBarRight = guiHelper.createAnimatedDrawable(
            guiHelper.createDrawable(new ResourceLocation("enderio:textures/gui/nei/alloySmelter.png"), 166, 0, 22, 13),
            200,
            IDrawableAnimated.StartDirection.BOTTOM,
            false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("enderio.nei.alloysmelter");
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
        progressBarLeft.draw(minecraft, 24, 31);
        progressBarRight.draw(minecraft, 72, 31);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AlloySmelterRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 23 - 1, 13 - 1);
        guiItemStacks.init(1, true, 48 - 1, 3 - 1);
        guiItemStacks.init(2, true, 72 - 1, 13 - 1);

        guiItemStacks.init(3, false, 48 - 1, 42 - 1);

        guiItemStacks.set(ingredients);
    }
}
