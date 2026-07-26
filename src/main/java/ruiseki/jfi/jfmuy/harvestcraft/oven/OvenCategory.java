package ruiseki.jfi.jfmuy.harvestcraft.oven;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import com.pam.harvestcraft.BlockRegistry;
import com.pam.harvestcraft.OvenRecipes;

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

public class OvenCategory implements IRecipeCategory<OvenWrapper> {

    public static final String UID = "harvestcraft.oven";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new OvenCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);

            if (BlockRegistry.pamOven != null) {
                registry.addRecipeCatalyst(new ItemStack(BlockRegistry.pamOven), UID);
            }
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private static List<OvenWrapper> getRecipes() {
        List<OvenWrapper> recipes = new ArrayList<>();
        Map<ItemStack, ItemStack> smeltingList = OvenRecipes.smelting()
            .getSmeltingList();

        if (smeltingList != null) {
            for (Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    recipes.add(new OvenWrapper(entry.getKey(), entry.getValue()));
                }
            }
        }
        return recipes;
    }

    private final IDrawable background;

    public OvenCategory(IGuiHelper guiHelper) {
        ResourceLocation guiTexture = new ResourceLocation("harvestcraft", "textures/gui/oven.png");
        this.background = guiHelper.createDrawable(guiTexture, 3, 8, 170, 66);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("tile.oven.name");
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
    public void setRecipe(IRecipeLayout recipeLayout, OvenWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 52, 8);
        itemStacks.init(1, true, 52, 44);
        itemStacks.init(2, false, 112, 26);

        itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (slotIndex == 1 && input) {
                tooltip.add(StatCollector.translateToLocal("tile.oven.fuel.tooltip"));
            }
        });

        itemStacks.set(ingredients);
    }
}
