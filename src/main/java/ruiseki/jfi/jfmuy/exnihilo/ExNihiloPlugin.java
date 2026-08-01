package ruiseki.jfi.jfmuy.exnihilo;

import cpw.mods.fml.common.Loader;
import exnihilo.compatibility.nei.RecipeHandlerHammer;
import exnihilo.compatibility.nei.RecipeHandlerSieve;
import ruiseki.jfi.jfmuy.exnihilo.hammer.HammerRecipeCategory;
import ruiseki.jfi.jfmuy.exnihilo.sieve.SieveRecipeCategory;
import ruiseki.jfi.jfmuy.nei.RecipeHarvester;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "exnihilo")
public class ExNihiloPlugin implements IModPlugin {

    @Override
    public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
        if (Loader.isModLoaded("NotEnoughItems")) {
            try {
                RecipeHarvester.addBlacklistedClass(RecipeHandlerSieve.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerHammer.class);
            } catch (Throwable ignore) {}
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        HammerRecipeCategory.register(registry);
        SieveRecipeCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        HammerRecipeCategory.initialize(registry);
        SieveRecipeCategory.initialize(registry);
    }
}
