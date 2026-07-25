package ruiseki.jfi.jfmuy.bigreactors;

import ruiseki.jfi.jfmuy.bigreactors.reactors.recipe.FuelRecipes;
import ruiseki.jfi.jfmuy.bigreactors.reactors.recipe.ReactorRecipes;
import ruiseki.jfi.jfmuy.bigreactors.reactors.recipe.TurbineRecipes;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "BigReactors")
public class BigReactorsPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        FuelRecipes.register(registry);
        ReactorRecipes.register(registry);
        TurbineRecipes.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        FuelRecipes.initialize(registry);
        ReactorRecipes.initialize(registry);
        TurbineRecipes.initialize(registry);
    }
}
