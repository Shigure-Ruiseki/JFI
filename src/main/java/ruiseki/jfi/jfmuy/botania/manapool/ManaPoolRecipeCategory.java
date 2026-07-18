package ruiseki.jfi.jfmuy.botania.manapool;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.okcore.client.renderer.GlStateManager;
import ruiseki.okcore.helper.ItemNBTHelpers;
import ruiseki.okcore.helper.LangHelpers;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.lib.LibMisc;

public class ManaPoolRecipeCategory implements IRecipeCategory<ManaPoolRecipeWrapper> {

    public static final String UID = "botania.manaPool";
    private final IDrawable background;
    private final String localizedName;
    private final IDrawable overlay;
    private final ItemStack renderStack = new ItemStack(ModBlocks.pool);

    public ManaPoolRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 55);
        localizedName = LangHelpers.localize("botania.nei.manaPool");
        overlay = guiHelper
            .createDrawable(new ResourceLocation("botania", "textures/gui/pureDaisyOverlay.png"), 0, 0, 64, 46);
        ItemNBTHelpers.setBoolean(renderStack, "RenderFull", true);
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

    @Nullable
    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        overlay.draw(minecraft, 40, 0);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull ManaPoolRecipeWrapper recipeWrapper,
        @Nonnull IIngredients ingredients) {
        int index = 0;

        recipeLayout.getItemStacks()
            .init(index, true, 32, 12);
        recipeLayout.getItemStacks()
            .set(
                index,
                ingredients.getInputs(VanillaTypes.ITEM)
                    .get(0));

        index++;

        if (ingredients.getInputs(VanillaTypes.ITEM)
            .size() > 1) {
            // Has catalyst
            recipeLayout.getItemStacks()
                .init(index, true, 12, 12);
            recipeLayout.getItemStacks()
                .set(
                    index,
                    ingredients.getInputs(VanillaTypes.ITEM)
                        .get(1));
            index++;
        }

        recipeLayout.getItemStacks()
            .init(index, true, 62, 12);
        recipeLayout.getItemStacks()
            .set(index, renderStack);
        index++;

        recipeLayout.getItemStacks()
            .init(index, false, 93, 12);
        recipeLayout.getItemStacks()
            .set(
                index,
                ingredients.getOutputs(VanillaTypes.ITEM)
                    .getFirst());
    }

    @Nonnull
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return new ArrayList<>();
    }

    @Nonnull
    @Override
    public String getModName() {
        return LibMisc.MOD_NAME;
    }

}
