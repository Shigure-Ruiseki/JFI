package ruiseki.jfi.jfmuy.harvestcraft.apiary;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import com.pam.harvestcraft.BlockRegistry;
import com.pam.harvestcraft.ItemRegistry;

import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class ApiaryCategory implements IRecipeCategory<ApiaryWrapper> {

    public static final String UID = "harvestcraft.apiary";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new ApiaryCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);

            if (BlockRegistry.pamApiary != null) {
                registry.addRecipeCatalyst(new ItemStack(BlockRegistry.pamApiary), UID);
            }
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private static List<ApiaryWrapper> getRecipes() {
        List<ApiaryWrapper> recipes = new ArrayList<>();
        if (ItemRegistry.queenbeeItem != null) {
            recipes.add(new ApiaryWrapper());
        }
        return recipes;
    }

    private final IDrawable background;

    public ApiaryCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("harvestcraft", "textures/gui/apiary.png");
        this.background = guiHelper.createDrawable(guiTexture, 3, 8, 170, 66);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("tile.apiary.name");
    }

    @Override
    public String getModName() {
        return "Pam's HarvestCraft";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ApiaryWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 23, 27);

        for (int i = 0; i < ApiaryWrapper.BEE_PRODUCTS.size(); i++) {
            itemStacks.init(1 + i, false, 59 + 18 * i, 9);
        }

        itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (!input && slotIndex >= 1 && slotIndex <= ApiaryWrapper.BEE_PRODUCTS.size()) {
                int productIndex = slotIndex - 1;
                Float chance = new ArrayList<>(ApiaryWrapper.BEE_PRODUCTS.values()).get(productIndex);
                NumberFormat percentFormat = NumberFormat.getPercentInstance();
                percentFormat.setMaximumFractionDigits(1);
                tooltip.add("Chance: " + percentFormat.format(chance));
            }
        });
        itemStacks.set(ingredients);
    }
}
