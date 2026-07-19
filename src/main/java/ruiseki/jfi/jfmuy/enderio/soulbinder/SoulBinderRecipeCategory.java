package ruiseki.jfi.jfmuy.enderio.soulbinder;

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

public class SoulBinderRecipeCategory implements IRecipeCategory<SoulBinderRecipeWrapper> {

    public static final String UID = "EnderIOSoulBinder";

    private final IDrawable background;
    private final IDrawableAnimated progressArrow;

    public SoulBinderRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation backgroundLocation = new ResourceLocation("enderio:textures/gui/23/soulFuser.png");

        this.background = guiHelper.createDrawable(backgroundLocation, 29, 21, 128, 55);

        IDrawableAnimated.StartDirection right = IDrawableAnimated.StartDirection.LEFT;
        this.progressArrow = guiHelper
            .createAnimatedDrawable(guiHelper.createDrawable(backgroundLocation, 177, 14, 23, 17), 160, right, false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("enderio.nei.soulbinder");
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
        progressArrow.draw(minecraft, 52, 13);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, final SoulBinderRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 8, 12);

        guiItemStacks.init(1, true, 29, 12);

        guiItemStacks.init(2, false, 82, 12);

        guiItemStacks.init(3, false, 104, 12);

        guiItemStacks.set(ingredients);
    }
}
