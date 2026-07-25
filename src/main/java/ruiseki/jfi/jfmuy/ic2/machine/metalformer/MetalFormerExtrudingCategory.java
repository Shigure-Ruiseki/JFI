package ruiseki.jfi.jfmuy.ic2.machine.metalformer;

import org.apache.logging.log4j.Level;

import ic2.api.recipe.Recipes;
import ic2.core.Ic2Items;
import ruiseki.jfi.JFI;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class MetalFormerExtrudingCategory extends MetalFormerCategory {

    public static final String UID = "ic2.metalformer.extruding";

    public MetalFormerExtrudingCategory(IGuiHelper guiHelper) {
        super(guiHelper, "Extruding");
    }

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new MetalFormerExtrudingCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            registry.addRecipes(getRecipesFromManager(Recipes.metalformerExtruding), UID);
            registerClickArea(registry, UID);
            registry.addRecipeCatalyst(Ic2Items.metalformer, UID);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    @Override
    public String getUid() {
        return UID;
    }
}
