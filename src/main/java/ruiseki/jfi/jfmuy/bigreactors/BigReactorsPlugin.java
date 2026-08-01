package ruiseki.jfi.jfmuy.bigreactors;

import org.jetbrains.annotations.NotNull;

import cpw.mods.fml.common.Loader;
import ruiseki.jfi.jfmuy.bigreactors.cyanite.CyaniteReprocessorCategory;
import ruiseki.jfi.jfmuy.bigreactors.fuel.FuelCategory;
import ruiseki.jfi.jfmuy.bigreactors.reactor.ReactorCategory;
import ruiseki.jfi.jfmuy.bigreactors.turbine.TurbineCategory;
import ruiseki.jfi.jfmuy.nei.RecipeHarvester;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import tonius.neiintegration.mods.bigreactors.RecipeHandlerCyaniteReprocessor;

@JFMUYPlugin(value = "BigReactors")
public class BigReactorsPlugin implements IModPlugin {

    @Override
    public void registerSubtypes(@NotNull ISubtypeRegistry subtypeRegistry) {
        if (Loader.isModLoaded("NotEnoughItems")) {
            try {
                RecipeHarvester.addBlacklistedClass(RecipeHandlerCyaniteReprocessor.class);
            } catch (Throwable ignore) {}
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        FuelCategory.register(registry);
        ReactorCategory.register(registry);
        TurbineCategory.register(registry);
        CyaniteReprocessorCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        FuelCategory.initialize(registry);
        ReactorCategory.initialize(registry);
        TurbineCategory.initialize(registry);
        CyaniteReprocessorCategory.initialize(registry);
    }
}
