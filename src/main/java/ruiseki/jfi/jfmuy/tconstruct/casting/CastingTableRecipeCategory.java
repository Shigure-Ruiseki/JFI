package ruiseki.jfi.jfmuy.tconstruct.casting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.CastingRecipe;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.smeltery.TinkerSmeltery;

public class CastingTableRecipeCategory extends CastingRecipeCategory {

    public static final String UID = "tconstruct.casting_table";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new CastingTableRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(), UID);
            if (TConstructRegistry.getItemStack("castingTable") != null) {
                registry.addRecipeCatalyst(TConstructRegistry.getItemStack("castingTable"), UID);
            }
            registry.addRecipeCatalyst(new ItemStack(TinkerSmeltery.searedBlock, 1, 0), UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null Casting Table recipe!", t);
        }
    }

    public static List<CastingRecipeWrapper> getRecipes() {
        List<CastingRecipeWrapper> recipes = new ArrayList<>();
        LiquidCasting casting = TConstructRegistry.getTableCasting();
        if (casting != null) {
            for (CastingRecipe recipe : casting.getCastingRecipes()) {
                if (recipe != null && recipe.output != null) {
                    recipes.add(new CastingRecipeWrapper(recipe));
                }
            }
        }
        return recipes;
    }

    public CastingTableRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, "tconstruct.nei.castingtable", 0);
    }

    @Override
    public String getUid() {
        return UID;
    }
}
