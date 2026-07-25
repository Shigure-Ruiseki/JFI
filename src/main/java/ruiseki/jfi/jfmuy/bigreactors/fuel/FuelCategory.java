package ruiseki.jfi.jfmuy.bigreactors.fuel;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.jetbrains.annotations.Nullable;

import erogenousbeef.bigreactors.common.BigReactors;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;

public class FuelCategory implements IRecipeCategory<FuelWrapper> {

    public static final String UID = "bigreactors.fuel";

    private final IDrawable icon;
    private final IDrawable foreground;
    private final IDrawable background;

    public FuelCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BigReactors.ingotGeneric, 1, 0));
        this.background = guiHelper.createBlankDrawable(94, 36);
        this.foreground = guiHelper
            .createDrawable(new ResourceLocation("jfi:textures/gui/bigreactors/background.png"), 0, 0, 76, 18);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("jfi.gui.fuel");
    }

    @Override
    public String getModName() {
        return BigReactors.NAME;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.foreground.draw(minecraft, 9,9);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FuelWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 9, 9);
        itemStacks.init(1, false, 67, 9);
        itemStacks.set(ingredients);
    }
}
