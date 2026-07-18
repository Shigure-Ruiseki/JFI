package ruiseki.jfi.jfmuy.botania.orechid;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.okcore.client.renderer.GlStateManager;
import ruiseki.okcore.helper.LangHelpers;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.common.lib.LibBlockNames;
import vazkii.botania.common.lib.LibMisc;

public class OrechidRecipeCategory implements IRecipeCategory<OrechidRecipeWrapper> {

    public static final String UID = "botania.orechid";
    private final IDrawableStatic background;
    private final String localizedName;
    private final IDrawableStatic overlay;

    public OrechidRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(96, 44);
        localizedName = LangHelpers.localize("botania.nei.orechid");
        overlay = guiHelper
            .createDrawable(new ResourceLocation("botania", "textures/gui/pureDaisyOverlay.png"), 0, 0, 64, 44);
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
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull OrechidRecipeWrapper recipeWrapper,
        @Nonnull IIngredients ingredients) {
        final IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 9, 12);
        itemStacks.set(
            0,
            ingredients.getInputs(VanillaTypes.ITEM)
                .get(0));

        itemStacks.init(1, true, 39, 12);
        itemStacks.set(1, ItemBlockSpecialFlower.ofType(LibBlockNames.SUBTILE_ORECHID));

        itemStacks.init(2, true, 68, 12);
        itemStacks.set(
            2,
            ingredients.getOutputs(VanillaTypes.ITEM)
                .get(0));
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        overlay.draw(minecraft, 17, 0);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    @Nonnull
    @Override
    public String getModName() {
        return LibMisc.MOD_NAME;
    }

}
