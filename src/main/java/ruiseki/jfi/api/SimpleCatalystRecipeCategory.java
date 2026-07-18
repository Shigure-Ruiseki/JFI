package ruiseki.jfi.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import ruiseki.jfmuy.Reference;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public abstract class SimpleCatalystRecipeCategory<T extends IRecipeWrapper> extends JFICategoryBase<T> {

    private final ItemStack catalyst;

    public static final ResourceLocation TEXTURE = new ResourceLocation(
        Reference.MOD_ID,
        "textures/gui/simple_catalyst_recipe.png");

    public SimpleCatalystRecipeCategory(IGuiHelper guiHelper, ItemStack catalyst, boolean animateArrow) {
        super(guiHelper, animateArrow, 66, 41, 21, 18);
        this.catalyst = catalyst;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, T wrapper, IIngredients ingredients) {
        IGuiItemStackGroup items = recipeLayout.getItemStacks();
        items.init(0, false, 47, 19);
        items.init(1, true, 1, 19);
        items.init(2, true, 24, 1);
        items.set(
            0,
            ingredients.getOutputs(VanillaTypes.ITEM)
                .getFirst());
        items.set(
            1,
            ingredients.getInputs(VanillaTypes.ITEM)
                .getFirst());
        items.set(2, catalyst);
    }

    public ItemStack getCatalyst() {
        return catalyst;
    }

}
