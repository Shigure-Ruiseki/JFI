package ruiseki.jfi.jfmuy.botania.petalapothecary;

import java.awt.Point;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.okcore.client.renderer.GlStateManager;
import ruiseki.okcore.helper.LangHelpers;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.lib.LibMisc;

public class PetalApothecaryRecipeCategory implements IRecipeCategory<PetalApothecaryRecipeWrapper> {

    public static final String UID = "botania.petals";
    private final IDrawableStatic background;
    private final String localizedName;
    private final IDrawableStatic overlay;

    public PetalApothecaryRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(114, 97);
        localizedName = LangHelpers.localize("botania.nei.petalApothecary");
        overlay = guiHelper
            .createDrawable(new ResourceLocation("botania", "textures/gui/petalOverlay.png"), 17, 11, 114, 82);
    }

    @Nonnull
    @Override
    public String getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        overlay.draw(minecraft, 0, 4);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull PetalApothecaryRecipeWrapper recipeWrapper,
        @Nonnull IIngredients ingredients) {
        recipeLayout.getItemStacks()
            .init(0, true, 47, 44);
        recipeLayout.getItemStacks()
            .set(0, new ItemStack(ModBlocks.altar));

        int index = 1;
        double angleBetweenEach = 360.0 / ingredients.getInputs(VanillaTypes.ITEM)
            .size();
        Point point = new Point(47, 12), center = new Point(47, 44);

        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            recipeLayout.getItemStacks()
                .init(index, true, point.x, point.y);
            recipeLayout.getItemStacks()
                .set(index, o);
            index += 1;
            point = rotatePointAbout(point, center, angleBetweenEach);
        }

        recipeLayout.getItemStacks()
            .init(index, false, 86, 11);
        recipeLayout.getItemStacks()
            .set(
                index,
                ingredients.getOutputs(VanillaTypes.ITEM)
                    .get(0));
    }

    private Point rotatePointAbout(Point in, Point about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Point((int) newX, (int) newY);
    }

    @Nonnull
    @Override
    public String getModName() {
        return LibMisc.MOD_NAME;
    }

}
