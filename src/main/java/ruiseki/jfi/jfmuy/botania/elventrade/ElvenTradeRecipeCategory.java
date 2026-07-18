package ruiseki.jfi.jfmuy.botania.elventrade;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.okcore.client.renderer.GlStateManager;
import ruiseki.okcore.helper.LangHelpers;
import vazkii.botania.common.block.BlockAlfPortal;
import vazkii.botania.common.lib.LibMisc;

public class ElvenTradeRecipeCategory implements IRecipeCategory<ElvenTradeRecipeWrapper> {

    public static final String UID = "botania.elvenTrade";
    private final String localizedName;
    private final IDrawable background;
    private final IDrawable overlay;

    public ElvenTradeRecipeCategory(IGuiHelper guiHelper) {
        localizedName = LangHelpers.localize("botania.nei.elvenTrade");
        background = guiHelper.createBlankDrawable(145, 95);
        overlay = guiHelper
            .createDrawable(new ResourceLocation("botania", "textures/gui/elvenTradeOverlay.png"), 0, 15, 140, 90);
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

        minecraft.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

        IIcon sprite = BlockAlfPortal.portalTex;
        if (sprite != null) {
            Tessellator tess = Tessellator.instance;
            tess.startDrawingQuads();

            int startX = 22;
            int startY = 25;
            int stopX = 70;
            int stopY = 73;

            tess.addVertexWithUV(startX, startY, 0, sprite.getMinU(), sprite.getMinV());
            tess.addVertexWithUV(startX, stopY, 0, sprite.getMinU(), sprite.getMaxV());
            tess.addVertexWithUV(stopX, stopY, 0, sprite.getMaxU(), sprite.getMaxV());
            tess.addVertexWithUV(stopX, startY, 0, sprite.getMaxU(), sprite.getMinV());
            tess.draw();
        }
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull ElvenTradeRecipeWrapper recipeWrapper,
        @Nonnull IIngredients ingredients) {
        int index = 0, posX = 42;
        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            recipeLayout.getItemStacks()
                .init(index, true, posX, 0);
            recipeLayout.getItemStacks()
                .set(index, o);
            index++;
            posX += 18;
        }

        for (int i = 0; i < ingredients.getOutputs(VanillaTypes.ITEM)
            .size(); i++) {
            List<ItemStack> stacks = ingredients.getOutputs(VanillaTypes.ITEM)
                .get(i);
            recipeLayout.getItemStacks()
                .init(index + i, false, 93 + i % 2 * 20, 41 + i / 2 * 20);
            recipeLayout.getItemStacks()
                .set(index + i, stacks);
        }
    }

    @Nonnull
    @Override
    public String getModName() {
        return LibMisc.MOD_NAME;
    }
}
