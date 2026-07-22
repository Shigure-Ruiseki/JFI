package ruiseki.jfi.jfmuy.exnihilo;

import ruiseki.jfi.jfmuy.exnihilo.hammer.HammerRecipeCategory;
import ruiseki.jfi.jfmuy.exnihilo.sieve.SieveRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "exnihilo")
public class ExNihiloPlugin implements IModPlugin {

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
