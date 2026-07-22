package ruiseki.jfi.jfmuy.mekanism.gas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasStack;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.ingredients.IIngredientRenderer;
import ruiseki.okcore.client.renderer.GlStateManager;
import ruiseki.okcore.fluid.FluidHelpers;
import ruiseki.okcore.helper.LangHelpers;

public class GasStackRenderer implements IIngredientRenderer<GasStack> {

    private static final int TEX_WIDTH = 16;
    private static final int TEX_HEIGHT = 16;
    private static final int MIN_FLUID_HEIGHT = 1;

    private final int capacityMb;
    private final TooltipMode tooltipMode;
    private final int width;
    private final int height;
    @Nullable
    private final IDrawable overlay;

    public GasStackRenderer() {
        this(FluidHelpers.BUCKET_VOLUME, TooltipMode.ITEM_LIST, TEX_WIDTH, TEX_HEIGHT, null);
    }

    public GasStackRenderer(int capacityMb, boolean showCapacity, int width, int height, @Nullable IDrawable overlay) {
        this(
            capacityMb,
            showCapacity ? TooltipMode.SHOW_AMOUNT_AND_CAPACITY : TooltipMode.SHOW_AMOUNT,
            width,
            height,
            overlay);
    }

    public GasStackRenderer(int capacityMb, TooltipMode tooltipMode, int width, int height,
        @Nullable IDrawable overlay) {
        this.capacityMb = capacityMb;
        this.tooltipMode = tooltipMode;
        this.width = width;
        this.height = height;
        this.overlay = overlay;
    }

    private static void drawTextureWithMasking(double xCoord, double yCoord, IIcon textureSprite, int maskTop,
        int maskRight, double zLevel) {
        double uMin = textureSprite.getMinU();
        double uMax = textureSprite.getMaxU();
        double vMin = textureSprite.getMinV();
        double vMax = textureSprite.getMaxV();
        uMax = uMax - (maskRight / 16.0 * (uMax - uMin));
        vMax = vMax - (maskTop / 16.0 * (vMax - vMin));

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(xCoord, yCoord + 16, zLevel, uMin, vMax);
        tessellator.addVertexWithUV(xCoord + 16 - maskRight, yCoord + 16, zLevel, uMax, vMax);
        tessellator.addVertexWithUV(xCoord + 16 - maskRight, yCoord + maskTop, zLevel, uMax, vMin);
        tessellator.addVertexWithUV(xCoord, yCoord + maskTop, zLevel, uMin, vMin);
        tessellator.draw();
    }

    @Override
    public void render(Minecraft minecraft, final int xPosition, final int yPosition, @Nullable GasStack gasStack) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        drawGas(minecraft, xPosition, yPosition, gasStack);
        if (overlay != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 0, 200);
            overlay.draw(minecraft, xPosition, yPosition);
            GlStateManager.popMatrix();
        }
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

    private void drawGas(Minecraft minecraft, final int xPosition, final int yPosition, @Nullable GasStack gasStack) {
        Gas gas = (gasStack == null) ? null : gasStack.getGas();
        if (gas == null || gas.getIcon() == null) {
            return;
        }
        int scaledAmount = (gasStack.amount * height) / capacityMb;
        if (gasStack.amount > 0 && scaledAmount < MIN_FLUID_HEIGHT) {
            scaledAmount = MIN_FLUID_HEIGHT;
        }
        if (scaledAmount > height) {
            scaledAmount = height;
        }
        drawTiledSprite(minecraft, xPosition, yPosition, width, height, gas, scaledAmount, gas.getIcon());
    }

    private void drawTiledSprite(Minecraft minecraft, final int xPosition, final int yPosition, final int tiledWidth,
        final int tiledHeight, Gas gas, int scaledAmount, IIcon icon) {
        minecraft.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

        final int xTileCount = tiledWidth / TEX_WIDTH;
        final int xRemainder = tiledWidth - (xTileCount * TEX_WIDTH);
        final int yTileCount = scaledAmount / TEX_HEIGHT;
        final int yRemainder = scaledAmount - (yTileCount * TEX_HEIGHT);
        final int yStart = yPosition + tiledHeight;

        for (int xTile = 0; xTile <= xTileCount; xTile++) {
            int w = (xTile == xTileCount) ? xRemainder : TEX_WIDTH;
            if (w > 0) {
                int x = xPosition + (xTile * TEX_WIDTH);
                int maskRight = TEX_WIDTH - w;
                for (int yTile = 0; yTile <= yTileCount; yTile++) {
                    int h = (yTile == yTileCount) ? yRemainder : TEX_HEIGHT;
                    if (h > 0) {
                        int y = yStart - ((yTile + 1) * TEX_HEIGHT);
                        int maskTop = TEX_HEIGHT - h;
                        drawTextureWithMasking(x, y, icon, maskTop, maskRight, 100);
                    }
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public List<String> getTooltip(Minecraft minecraft, GasStack gasStack, boolean tooltipFlag) {
        List<String> tooltip = new ArrayList<>();
        if (gasStack == null || gasStack.getGas() == null) {
            return tooltip;
        }
        Gas gasType = gasStack.getGas();
        String gasName = gasType.getLocalizedName();
        tooltip.add(gasName);

        if (tooltipMode == TooltipMode.SHOW_AMOUNT_AND_CAPACITY) {
            String amount = LangHelpers
                .localize("jei.tooltip.liquid.amount.with.capacity", gasStack.amount, capacityMb);
            tooltip.add(EnumChatFormatting.GRAY + amount);
        } else if (tooltipMode == TooltipMode.SHOW_AMOUNT) {
            String amount = LangHelpers.localize("jei.tooltip.liquid.amount", gasStack.amount);
            tooltip.add(EnumChatFormatting.GRAY + amount);
        }
        return tooltip;
    }

    @Override
    public FontRenderer getFontRenderer(Minecraft minecraft, GasStack gasStack) {
        return minecraft.fontRenderer;
    }

    public enum TooltipMode {
        SHOW_AMOUNT,
        SHOW_AMOUNT_AND_CAPACITY,
        ITEM_LIST
    }
}
