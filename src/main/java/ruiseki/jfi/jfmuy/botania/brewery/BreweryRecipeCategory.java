package ruiseki.jfi.jfmuy.botania.brewery;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IFocus;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.okcore.helper.LangHelpers;
import vazkii.botania.common.lib.LibMisc;

public class BreweryRecipeCategory implements IRecipeCategory<BreweryRecipeWrapper> {

    public static final String UID = "botania.brewery";
    private final IDrawableStatic background;
    private final String localizedName;

    public BreweryRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("botania", "textures/gui/neiBrewery.png");
        background = guiHelper.createDrawable(location, 28, 6, 131, 55);
        localizedName = LangHelpers.localize("botania.nei.brewery");
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
    public String getModName() {
        return LibMisc.MOD_NAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull BreweryRecipeWrapper recipeWrapper,
        @Nonnull IIngredients ingredients) {

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        IFocus<?> focus = recipeLayout.getFocus();

        recipeLayout.getItemStacks()
            .init(0, true, 10, 35);
        recipeLayout.getItemStacks()
            .set(0, getItemMatchingFocus(focus, IFocus.Mode.OUTPUT, outputs.getFirst(), inputs.getFirst()));

        int index = 1, posX = 76 - (inputs.size() * 9);
        for (int i = 1; i < inputs.size(); i++) {
            List<ItemStack> o = inputs.get(i);
            recipeLayout.getItemStacks()
                .init(index, true, posX, 0);
            recipeLayout.getItemStacks()
                .set(index, o);
            index++;
            posX += 18;
        }

        recipeLayout.getItemStacks()
            .init(7, false, 58, 35);
        recipeLayout.getItemStacks()
            .set(7, getItemMatchingFocus(focus, IFocus.Mode.INPUT, inputs.getFirst(), outputs.getFirst()));
    }

    /**
     * If an item in this recipe is focused, returns the corresponding item instead of all the containers/results.
     */
    private List<ItemStack> getItemMatchingFocus(IFocus<?> focus, IFocus.Mode mode, List<ItemStack> focused,
        List<ItemStack> other) {
        if (focus != null && focus.getMode() == mode) {
            ItemStack focusStack = (ItemStack) focus.getValue();
            for (int i = 0; i < focused.size(); i++)
                if (focusStack.isItemEqual(focused.get(i))) return Collections.singletonList(other.get(i));
        }
        return other;
    }
}
