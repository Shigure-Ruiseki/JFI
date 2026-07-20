package ruiseki.jfi.jfmuy.thermaldynamics.crafting;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermaldynamics.item.ItemCover;
import ruiseki.jfi.jfmuy.thermaldynamics.RecipeUidsTD;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.ICraftingGridHelper;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class CoverRecipeCategory implements IRecipeCategory<CoverRecipeWrapper> {

    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;

    public static final int width = 116;
    public static final int height = 54;

    public static void register(IRecipeCategoryRegistration registry) {

        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new CoverRecipeCategory(guiHelper));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void initialize(IModRegistry registry) {
        registry.addRecipes(getRecipes(), RecipeUidsTD.COVERS);
        registry.addRecipeCatalyst(
            ItemCover.getCoverList()
                .getFirst(),
            RecipeUidsTD.COVERS);
    }

    public static List<CoverRecipeWrapper> getRecipes() {

        List<CoverRecipeWrapper> recipes = new LinkedList<>();

        for (ItemStack stack : ItemCover.getCoverList()) {
            recipes.add(new CoverRecipeWrapper(stack));
        }
        return recipes;
    }

    private final IDrawableStatic background;
    private final String localizedName;
    private final ICraftingGridHelper craftingGridHelper;

    public CoverRecipeCategory(IGuiHelper guiHelper) {

        ResourceLocation location = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");

        background = guiHelper.createDrawable(location, 29, 16, width, height);
        localizedName = StringHelper.localize("recipe.thermaldynamics.covers");
        craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
    }

    @Override
    public String getUid() {

        return RecipeUidsTD.COVERS;
    }

    @Override
    public String getTitle() {

        return localizedName;
    }

    @Override
    public String getModName() {

        return "ThermalDynamics";
    }

    @Override
    public IDrawable getBackground() {

        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CoverRecipeWrapper recipeWrapper, IIngredients ingredients) {

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(craftOutputSlot, false, 94, 18);

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = craftInputSlot1 + x + (y * 3);
                guiItemStacks.init(index, true, x * 18, y * 18);
            }
        }
        craftingGridHelper.setInputs(guiItemStacks, ingredients.getInputs(VanillaTypes.ITEM));
        guiItemStacks.set(
            craftOutputSlot,
            ingredients.getOutputs(VanillaTypes.ITEM)
                .get(0));
    }
}
