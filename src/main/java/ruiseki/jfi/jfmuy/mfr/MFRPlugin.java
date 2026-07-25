package ruiseki.jfi.jfmuy.mfr;

import ruiseki.jfi.jfmuy.mfr.bioreactor.BioReactorCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "MineFactoryReloaded")
public class MFRPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        BioReactorCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        BioReactorCategory.initialize(registry);
    }
}
