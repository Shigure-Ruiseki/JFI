package ruiseki.jfi.jfmuy.bigreactors.reactor;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import erogenousbeef.bigreactors.common.BigReactors;
import ruiseki.jfi.jfmuy.bigreactors.DrawableFrame;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;

public class ReactorCategory implements IRecipeCategory<ReactorWrapper> {

    public static final String UID = "bigreactors.reactor";

    private final IDrawable icon;
    private final IDrawable background;

    public ReactorCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BigReactors.blockReactorPart));
        this.background = new DrawableFrame(
            new ResourceLocation("bigreactors:textures/blocks/tile.blockReactorPart.casing.corner.png"),
            new ResourceLocation("bigreactors:textures/blocks/tile.blockReactorPart.casing.eastwest.png"),
            new ResourceLocation("bigreactors:textures/blocks/tile.blockReactorPart.casing.face.png"));
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("jfi.gui.reactor");
    }

    @Override
    public String getModName() {
        return BigReactors.NAME;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ReactorWrapper recipeWrapper, IIngredients ingredients) {
        if (recipeWrapper.getReactorEntry()
            .isBlock()) {
            final IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

            itemStacks.init(0, true, 15, 15);

            itemStacks.set(ingredients);
        } else {
            final IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

            fluidStacks.init(0, true, 16, 16);

            fluidStacks.set(ingredients);
        }
    }
}
