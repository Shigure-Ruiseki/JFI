package ruiseki.jfi.jfmuy.bigreactors.turbine;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import erogenousbeef.bigreactors.common.BigReactors;
import ruiseki.jfi.jfmuy.bigreactors.DrawableFrame;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;

public class TurbineCategory implements IRecipeCategory<TurbineWrapper> {

    public static final String UID = "bigreactors.turbine";

    private final IDrawable icon;
    private final IDrawable background;

    public TurbineCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BigReactors.blockTurbinePart));
        this.background = new DrawableFrame(
            new ResourceLocation("bigreactors:textures/blocks/tile.blockTurbinePart.housing.corner.png"),
            new ResourceLocation("bigreactors:textures/blocks/tile.blockTurbinePart.housing.edge.0.png"),
            new ResourceLocation("bigreactors:textures/blocks/tile.blockTurbinePart.housing.face.png"));
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("jfi.gui.turbine");
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
    public void setRecipe(IRecipeLayout recipeLayout, TurbineWrapper recipeWrapper, IIngredients ingredients) {
        final IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 15, 15);

        itemStacks.set(ingredients);
    }
}
