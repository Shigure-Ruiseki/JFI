package ruiseki.jfi.jfmuy.mfr;

import ruiseki.jfi.jfmuy.mfr.bioreactor.BioReactorCategory;
import ruiseki.jfi.jfmuy.mfr.composter.ComposterCategory;
import ruiseki.jfi.jfmuy.mfr.grinder.GrinderCategory;
import ruiseki.jfi.jfmuy.mfr.harvester.HarvesterCategory;
import ruiseki.jfi.jfmuy.mfr.laserdrill.LaserDrillCategory;
import ruiseki.jfi.jfmuy.mfr.lavafabricator.LavaFabricatorCategory;
import ruiseki.jfi.jfmuy.mfr.meatpacker.MeatPackerCategory;
import ruiseki.jfi.jfmuy.mfr.sewer.SewerCategory;
import ruiseki.jfi.jfmuy.mfr.slaughterhouse.SlaughterhouseCategory;
import ruiseki.jfi.jfmuy.mfr.sludgeboiler.SludgeBoilerCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "MineFactoryReloaded")
public class MFRPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        BioReactorCategory.register(registry);
        ComposterCategory.register(registry);
        GrinderCategory.register(registry);
        HarvesterCategory.register(registry);
        LaserDrillCategory.register(registry);
        LavaFabricatorCategory.register(registry);
        MeatPackerCategory.register(registry);
        SewerCategory.register(registry);
        SlaughterhouseCategory.register(registry);
        SludgeBoilerCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        BioReactorCategory.initialize(registry);
        ComposterCategory.initialize(registry);
        GrinderCategory.initialize(registry);
        HarvesterCategory.initialize(registry);
        LaserDrillCategory.initialize(registry);
        LavaFabricatorCategory.initialize(registry);
        MeatPackerCategory.initialize(registry);
        SewerCategory.initialize(registry);
        SlaughterhouseCategory.initialize(registry);
        SludgeBoilerCategory.initialize(registry);
    }
}
