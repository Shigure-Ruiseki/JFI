package ruiseki.jfi.jfmuy.mfr;

import cpw.mods.fml.common.Loader;
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
import ruiseki.jfi.jfmuy.nei.RecipeHarvester;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import tonius.neiintegration.mods.minefactoryreloaded.RecipeHandlerBioReactor;
import tonius.neiintegration.mods.minefactoryreloaded.RecipeHandlerComposter;
import tonius.neiintegration.mods.minefactoryreloaded.RecipeHandlerGrinder;
import tonius.neiintegration.mods.minefactoryreloaded.RecipeHandlerHarvester;
import tonius.neiintegration.mods.minefactoryreloaded.RecipeHandlerLaserDrill;
import tonius.neiintegration.mods.minefactoryreloaded.RecipeHandlerLavaFabricator;
import tonius.neiintegration.mods.minefactoryreloaded.RecipeHandlerMeatPacker;
import tonius.neiintegration.mods.minefactoryreloaded.RecipeHandlerSewer;
import tonius.neiintegration.mods.minefactoryreloaded.RecipeHandlerSlaughterhouse;
import tonius.neiintegration.mods.minefactoryreloaded.RecipeHandlerSludgeBoiler;

@JFMUYPlugin(value = "MineFactoryReloaded")
public class MFRPlugin implements IModPlugin {

    @Override
    public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
        if (Loader.isModLoaded("NotEnoughItems") && Loader.isModLoaded("neiintegration")) {
            try {
                RecipeHarvester.addBlacklistedClass(RecipeHandlerBioReactor.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerComposter.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerGrinder.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerHarvester.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerLaserDrill.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerLavaFabricator.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerMeatPacker.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerSewer.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerSlaughterhouse.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerSludgeBoiler.class);
            } catch (Throwable ignore) {}
        }
    }

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
