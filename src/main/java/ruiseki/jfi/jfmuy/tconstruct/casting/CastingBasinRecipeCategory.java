package ruiseki.jfi.jfmuy.tconstruct.casting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.smeltery.TinkerSmeltery;

public class CastingBasinRecipeCategory extends CastingRecipeCategory {

    public static final String UID = "tconstruct.casting_basin";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new CastingBasinRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            if (TConstructRegistry.getItemStack("searedBasin") != null) {
                registry.addRecipeCatalyst(TConstructRegistry.getItemStack("searedBasin"), UID);
            }

            registry.addRecipeCatalyst(new ItemStack(TinkerSmeltery.searedBlock, 1, 2), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null Casting Basin recipe!", t);
        }
    }

    public static List<CastingRecipeWrapper> getRecipes() {
        List<CastingRecipeWrapper> recipes = new ArrayList<>();
        LiquidCasting casting = TConstructRegistry.getBasinCasting();
        if (casting != null) {
            for (CastingRecipe recipe : casting.getCastingRecipes()) {
                if (recipe != null && recipe.output != null) {
                    recipes.add(new CastingRecipeWrapper(recipe));
                }
            }
        }
        return recipes;
    }

    public CastingBasinRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, "tconstruct.nei.castingbasin", 62);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CastingRecipeWrapper recipeWrapper, IIngredients ingredients) {
        super.setRecipe(recipeLayout, recipeWrapper, ingredients);
    }
}
