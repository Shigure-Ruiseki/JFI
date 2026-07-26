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

public class AnimalTrapCategory extends TrapRecipeCategory {

    public static final String UID = "harvestcraft.animaltrap";

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new AnimalTrapCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipes(), UID);

            if (BlockRegistry.pamanimalTrap != null) {
                registry.addRecipeCatalyst(new ItemStack(BlockRegistry.pamanimalTrap), UID);
            }
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<TrapRecipeWrapper> getRecipes() {
        return buildRecipes(TrapRecipes.ANIMAL_TRAP_RECIPES, TrapRecipeWrapper.Ground::new);
    }

    public AnimalTrapCategory(IGuiHelper guiHelper) {
        super(guiHelper, "ground");
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("harvestcraft.nei.trap.animal");
    }
}
