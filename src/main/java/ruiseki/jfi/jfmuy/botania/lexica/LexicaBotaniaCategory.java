package ruiseki.jfi.jfmuy.botania.lexica;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.okcore.helper.LangHelpers;
import vazkii.botania.common.item.ModItems;

public class LexicaBotaniaCategory implements IRecipeCategory<LexicaBotaniaWrapper> {

    public static final String UID = "botania.lexica";
    private final IDrawable background;
    private final IDrawable overlay;
    private final String localizedName;

    public LexicaBotaniaCategory(IGuiHelper guiHelper) {
        this.background = guiHelper
            .createDrawable(new ResourceLocation("botania", "textures/gui/neiBlank.png"), 0, 0, 166, 130);
        this.localizedName = LangHelpers.localize("botania.nei.lexica");
        this.overlay = guiHelper.createDrawableIngredient(new ItemStack(ModItems.lexicon));
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public String getModName() {
        return "Botania";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return overlay;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, LexicaBotaniaWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 16, 4);
        guiItemStacks.set(ingredients);

        guiItemStacks.init(1, false, 132, 4);
        guiItemStacks.set(
            1,
            ingredients.getOutputs(VanillaTypes.ITEM)
                .getFirst());
    }
}
