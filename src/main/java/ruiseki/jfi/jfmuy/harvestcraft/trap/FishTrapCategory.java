package ruiseki.jfi.jfmuy.harvestcraft.trap;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;

import com.pam.harvestcraft.BlockRegistry;
import com.pam.harvestcraft.TrapRecipes;

import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class FishTrapCategory extends TrapRecipeCategory {

    public static final String UID = "harvestcraft.fishtrap";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new FishTrapCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);

            if (BlockRegistry.pamfishTrap != null) {
                registry.addRecipeCatalyst(new ItemStack(BlockRegistry.pamfishTrap), UID);
            }
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<TrapRecipeWrapper> getRecipes() {
        return buildRecipes(TrapRecipes.FISH_TRAP_RECIPES, TrapRecipeWrapper.Water::new);
    }

    public FishTrapCategory(IGuiHelper guiHelper) {
        super(guiHelper, "water");
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("harvestcraft.nei.trap.fish");
    }
}
