package ruiseki.jfi.jfmuy.thermalexpansion.crafting.upgrade;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.TEBlocks;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
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
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;

public class CraftingUpgradeRecipeCategory implements IRecipeCategory<CraftingUpgradeRecipeWrapper> {

    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;

    public static final int width = 116;
    public static final int height = 54;

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new CraftingUpgradeRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        List<CraftingUpgradeRecipeWrapper> jfmuyRecipes = new ArrayList<>();

        try {
            for (IRecipe recipe : CraftingManager.getInstance()
                .getRecipeList()) {
                if (recipe.getClass()
                    .getName()
                    .equals("cofh.thermalexpansion.plugins.nei.handlers.NEIRecipeWrapper")) {
                    Object recipeTypeObj = recipe.getClass()
                        .getMethod("getRecipeType")
                        .invoke(recipe);

                    if (recipeTypeObj != null && recipeTypeObj.toString()
                        .equals("UPGRADE")) {
                        IRecipe wrappedRecipe = (IRecipe) recipe.getClass()
                            .getMethod("getWrappedRecipe")
                            .invoke(recipe);
                        if (wrappedRecipe != null) {
                            jfmuyRecipes.add(new CraftingUpgradeRecipeWrapper(wrappedRecipe));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        registry.addRecipes(jfmuyRecipes, RecipeUidsTE.CRAFTING_UPGRADE);
        registry.copyRecipeCatalyst(VanillaRecipeCategoryUid.CRAFTING, RecipeUidsTE.CRAFTING_UPGRADE);
        registry.getRecipeTransferRegistry()
            .copyRecipeTransferHandlers(VanillaRecipeCategoryUid.CRAFTING, RecipeUidsTE.CRAFTING_UPGRADE);
    }

    private final IDrawableStatic background;
    private final IDrawable icon;
    private final String localizedName;
    private final ICraftingGridHelper craftingGridHelper;

    public CraftingUpgradeRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");
        this.background = guiHelper.createDrawable(location, 29, 16, width, height);
        this.localizedName = StringHelper.localize("recipe.thermalexpansion.upgrade");
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(TEBlocks.blockFrame, 1, 3));
        this.craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
    }

    @Override
    public String getUid() {
        return RecipeUidsTE.CRAFTING_UPGRADE;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public String getModName() {
        return "ThermalExpansion";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CraftingUpgradeRecipeWrapper recipeWrapper,
        IIngredients ingredients) {
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
                .getFirst());
    }
}
