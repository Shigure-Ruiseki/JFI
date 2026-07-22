package ruiseki.jfi.jfmuy.tconstruct.casting;

import net.minecraft.util.ResourceLocation;

import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import tconstruct.TConstruct;

public abstract class CastingRecipeCategory implements IRecipeCategory<CastingRecipeWrapper> {

    public static void register(IRecipeCategoryRegistration registry) {
        CastingBasinRecipeCategory.register(registry);
        CastingTableRecipeCategory.register(registry);
    }

    public static void initialize(IModRegistry registry) {
        CastingBasinRecipeCategory.initialize(registry);
        CastingTableRecipeCategory.initialize(registry);
    }

    private final IDrawable background;
    private final String title;

    public CastingRecipeCategory(IGuiHelper guiHelper, String titleKey, int backgroundV) {
        ResourceLocation castingTex = new ResourceLocation("tinker", "textures/gui/nei/casting.png");

        this.background = guiHelper.createDrawable(castingTex, 0, backgroundV, 108, 56);
        this.title = net.minecraft.util.StatCollector.translateToLocal(titleKey);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getModName() {
        return TConstruct.modID;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CastingRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        fluidStacks.init(
            0,
            true,
            30,
            8,
            6,
            recipeWrapper.getRecipe().cast != null ? 11 : 27,
            recipeWrapper.getRecipe().castingMetal.amount,
            true,
            null);

        itemStacks.init(0, true, 24, 18);

        itemStacks.init(1, false, 79, 17);

        itemStacks.set(ingredients);
        fluidStacks.set(ingredients);
    }
}
