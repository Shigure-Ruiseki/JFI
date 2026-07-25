package ruiseki.jfi.jfmuy.ic2.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;

import ic2.core.AdvShapelessRecipe;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ruiseki.jfi.JFI;
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

public class AdvShapelessRecipeCategory implements IRecipeCategory<AdvShapelessRecipeWrapper> {

    public static final String UID = "ic2.advshapelessrecipe";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new AdvShapelessRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            registry.copyRecipeCatalyst(VanillaRecipeCategoryUid.CRAFTING, UID);
            registry.getRecipeTransferRegistry()
                .copyRecipeTransferHandlers(VanillaRecipeCategoryUid.CRAFTING, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<AdvShapelessRecipeWrapper> getRecipes() {
        List<AdvShapelessRecipeWrapper> recipes = new ArrayList<>();

        for (IRecipe irecipe : CraftingManager.getInstance()
            .getRecipeList()) {
            if (irecipe instanceof AdvShapelessRecipe recipe) {
                if (recipe.canShow()) {
                    AdvShapelessRecipeWrapper wrapper = new AdvShapelessRecipeWrapper(recipe);
                    if (wrapper.isValid()) {
                        recipes.add(wrapper);
                    }
                }
            }
        }
        return recipes;
    }

    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;

    public static final int width = 116;
    public static final int height = 54;

    private final IDrawableStatic background;
    private final IDrawable icon;
    private final ICraftingGridHelper craftingGridHelper;

    public AdvShapelessRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");
        this.background = guiHelper.createDrawable(location, 29, 16, width, height);
        this.icon = guiHelper.createDrawableIngredient(Ic2Items.reactorAccessHatch);
        this.craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Shapeless IC2 Crafting";
    }

    @Override
    public String getModName() {
        return IC2.MODID;
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
    public void setRecipe(IRecipeLayout recipeLayout, AdvShapelessRecipeWrapper recipeWrapper,
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

        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        if (!outputs.isEmpty() && !outputs.getFirst()
            .isEmpty()) {
            guiItemStacks.set(craftOutputSlot, outputs.getFirst());
        }
    }
}
