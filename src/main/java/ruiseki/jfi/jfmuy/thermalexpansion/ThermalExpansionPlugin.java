package ruiseki.jfi.jfmuy.thermalexpansion;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import cofh.lib.render.RenderHelper;
import ruiseki.jfi.jfmuy.thermalexpansion.transposer.TransposerRecipeCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.okcore.client.renderer.GlStateManager;

@JFMUYPlugin
public class ThermalExpansionPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jfmuyHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jfmuyHelpers.getGuiHelper();

        TransposerRecipeCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {

        TransposerRecipeCategory.initialize(registry);

        registry.getRecipeTransferRegistry()
            .addRecipeTransferHandler(new WorkbenchRecipeTransferHandler(), VanillaRecipeCategoryUid.CRAFTING);
    }

    public static void drawFluid(int x, int y, FluidStack fluid, int width, int height) {
        if (fluid == null || fluid.getFluid() == null) {
            return;
        }

        int renderX = x;
        int renderY = y;
        int renderWidth = width;
        int renderHeight = height;

        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        RenderHelper.setBlockTextureSheet();

        int color = fluid.getFluid()
            .getColor(fluid);
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        if (alpha == 0.0F) alpha = 1.0F;

        GlStateManager.color(red, green, blue, alpha);

        IIcon stillIcon = fluid.getFluid()
            .getStillIcon();
        if (stillIcon == null) {
            stillIcon = fluid.getFluid()
                .getIcon(fluid);
        }

        if (stillIcon != null) {
            drawTiledTexture(renderX, renderY, stillIcon, renderWidth, renderHeight, x, y);
        }

        GL11.glPopMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawTiledTexture(int x, int y, IIcon icon, int width, int height, int baseX, int baseY) {
        if (icon == null) return;

        int drawHeight;
        int drawWidth;

        for (int i = 0; i < width; i += 16) {
            for (int j = 0; j < height; j += 16) {
                drawWidth = Math.min(width - i, 16);
                drawHeight = Math.min(height - j, 16);

                drawDirectionalIcon(x + i, y + j, icon, drawWidth, drawHeight, baseX, baseY);
            }
        }
    }

    private static void drawDirectionalIcon(int x, int y, IIcon icon, int width, int height, int baseX, int baseY) {
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();

        double uLength = maxU - minU;
        double vLength = maxV - minV;

        int relativeX = x - baseX;
        int relativeY = y - baseY;

        double targetMinU = minU + uLength * (double) (relativeX % 16) / 16.0D;
        double targetMaxU = targetMinU + uLength * (double) width / 16.0D;

        double targetMinV = minV + vLength * (double) (relativeY % 16) / 16.0D;
        double targetMaxV = targetMinV + vLength * (double) height / 16.0D;

        double zLevel = GuiDraw.gui.getZLevel();
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) x, (double) (y + height), zLevel, targetMinU, targetMaxV);
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), zLevel, targetMaxU, targetMaxV);
        tessellator.addVertexWithUV((double) (x + width), (double) y, zLevel, targetMaxU, targetMinV);
        tessellator.addVertexWithUV((double) x, (double) y, zLevel, targetMinU, targetMinV);
        tessellator.draw();
    }
}
