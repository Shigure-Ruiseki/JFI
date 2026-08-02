package ruiseki.jfi.jfmuy.thermaldynamics;

import cofh.thermaldynamics.plugins.nei.RecipeHandlerCover;
import cpw.mods.fml.common.Loader;
import ruiseki.jfi.jfmuy.thermaldynamics.crafting.CoverRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.jfmuy.plugins.nei.RecipeHarvester;

@JFMUYPlugin(value = "ThermalDynamics")
public class ThermalDynamicsPlugin implements IModPlugin {

    @Override
    public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
        if (Loader.isModLoaded("NotEnoughItems")) {
            try {
                RecipeHarvester.addBlacklistedClass(RecipeHandlerCover.class);
            } catch (Throwable ignore) {}
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        CoverRecipeCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        CoverRecipeCategory.initialize(registry);
        registry.getRecipeTransferRegistry()
            .copyRecipeTransferHandlers(VanillaRecipeCategoryUid.CRAFTING, RecipeUidsTD.COVERS);
    }
}
