package ruiseki.jfi.jfmuy.thermalexpansion.transposer;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.gui.client.machine.GuiTransposer;
import ruiseki.jfi.jfmuy.thermalexpansion.BaseRecipeCategory;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public abstract class TransposerRecipeCategory extends BaseRecipeCategory<TransposerRecipeWrapper> {

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new TransposerRecipeCategoryFill(guiHelper));
        registry.addRecipeCategories(new TransposerRecipeCategoryExtract(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        TransposerRecipeCategoryFill.initialize(registry);
        TransposerRecipeCategoryExtract.initialize(registry);
        registry.addRecipeClickArea(
            GuiTransposer.class,
            112,
            19,
            24,
            16,
            RecipeUidsTE.TRANSPOSER_FILL,
            RecipeUidsTE.TRANSPOSER_EXTRACT);
    }

    protected IDrawableStatic speed;
    protected IDrawableStatic tankOverlay;

    public TransposerRecipeCategory(IGuiHelper guiHelper) {

        background = guiHelper.drawableBuilder(GuiTransposer.TEXTURE, 73, 8, 96, 62)
            .addPadding(0, 0, 24, 24)
            .build();
        energyMeter = Drawables.getDrawables(guiHelper)
            .getEnergyEmpty();
        speed = Drawables.getDrawables(guiHelper)
            .getScale(Drawables.SCALE_BUBBLE);
        tankOverlay = Drawables.getDrawables(guiHelper)
            .getTankLargeOverlay(0);
        localizedName = StringHelper.localize("tile.thermalexpansion.machine.transposer.name");
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

        speed.draw(minecraft, 67, 41);
        energyMeter.draw(minecraft, 2, 8);
    }

}
