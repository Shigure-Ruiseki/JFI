package ruiseki.jfi.jfmuy.harvestcraft;

import ruiseki.jfi.jfmuy.harvestcraft.apiary.ApiaryCategory;
import ruiseki.jfi.jfmuy.harvestcraft.churn.ChurnCategory;
import ruiseki.jfi.jfmuy.harvestcraft.garden.GardenCategory;
import ruiseki.jfi.jfmuy.harvestcraft.oven.OvenCategory;
import ruiseki.jfi.jfmuy.harvestcraft.presser.PresserCategory;
import ruiseki.jfi.jfmuy.harvestcraft.quern.QuernCategory;
import ruiseki.jfi.jfmuy.harvestcraft.trap.AnimalTrapCategory;
import ruiseki.jfi.jfmuy.harvestcraft.trap.FishTrapCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "harvestcraft")
public class HarvestCraftPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        ApiaryCategory.register(registry);
        ChurnCategory.register(registry);
        OvenCategory.register(registry);
        PresserCategory.register(registry);
        QuernCategory.register(registry);
        AnimalTrapCategory.register(registry);
        FishTrapCategory.register(registry);
        GardenCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        ApiaryCategory.initialize(registry);
        ChurnCategory.initialize(registry);
        OvenCategory.initialize(registry);
        PresserCategory.initialize(registry);
        QuernCategory.initialize(registry);
        AnimalTrapCategory.initialize(registry);
        FishTrapCategory.initialize(registry);
        GardenCategory.initialize(registry);
    }
}
