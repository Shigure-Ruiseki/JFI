package ruiseki.jfi.jfmuy.ic2.scrapbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;

import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class ScrapboxRecipeCategory implements IRecipeCategory<ScrapboxRecipeWrapper> {

    public static final String UID = "ic2.scrapbox";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new ScrapboxRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);
            registry.addRecipeCatalyst(Ic2Items.scrapBox, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    public static final int width = 140;
    public static final int height = 65;

    private final IDrawableStatic background;

    public ScrapboxRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(IC2.textureDomain + ":textures/gui/ScrapboxRecipes.png");
        this.background = guiHelper.createDrawable(location, 14, 14, width, height);
    }

    public static List<ScrapboxRecipeWrapper> getRecipes() {
        List<ScrapboxRecipeWrapper> recipes = new ArrayList<>();
        if (Recipes.scrapboxDrops != null) {
            Map<ItemStack, Float> drops = Recipes.scrapboxDrops.getDrops();

            float totalWeight = 0.0f;
            for (Float weight : drops.values()) {
                if (weight != null) {
                    totalWeight += weight;
                }
            }

            for (Map.Entry<ItemStack, Float> entry : drops.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    recipes.add(new ScrapboxRecipeWrapper(entry.getKey(), entry.getValue(), totalWeight));
                }
            }
        }
        return recipes;
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Scrapbox";
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
    public void setRecipe(IRecipeLayout recipeLayout, ScrapboxRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(INPUT_SLOT, true, 41, 20);
        guiItemStacks.init(OUTPUT_SLOT, false, 101, 20);

        guiItemStacks.set(ingredients);
    }
}
