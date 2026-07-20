package ruiseki.jfi.jfmuy.thermaldynamics;

import ruiseki.jfi.jfmuy.thermaldynamics.crafting.CoverRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.jfmuy.recipes.RecipeTransferRegistry;

@JFMUYPlugin(value = "ThermalDynamics")
public class ThermalDynamicsPlugin implements IModPlugin {

    public static RecipeTransferRegistry transferRegistryInstance;

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
