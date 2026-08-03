package ruiseki.jfi.jfmuy.ic2;

import cpw.mods.fml.common.Loader;
import ic2.core.Ic2Items;
import ic2.neiIntegration.core.recipehandler.AdvRecipeHandler;
import ic2.neiIntegration.core.recipehandler.AdvShapelessRecipeHandler;
import ic2.neiIntegration.core.recipehandler.BlastFurnaceRecipeHandler;
import ic2.neiIntegration.core.recipehandler.BlockCutterRecipeHandler;
import ic2.neiIntegration.core.recipehandler.CentrifugeRecipeHandler;
import ic2.neiIntegration.core.recipehandler.CompressorRecipeHandler;
import ic2.neiIntegration.core.recipehandler.ExtractorRecipeHandler;
import ic2.neiIntegration.core.recipehandler.FluidCannerRecipeHandler;
import ic2.neiIntegration.core.recipehandler.LatheRecipeHandler;
import ic2.neiIntegration.core.recipehandler.MaceratorRecipeHandler;
import ic2.neiIntegration.core.recipehandler.MetalFormerRecipeHandlerCutting;
import ic2.neiIntegration.core.recipehandler.MetalFormerRecipeHandlerExtruding;
import ic2.neiIntegration.core.recipehandler.MetalFormerRecipeHandlerRolling;
import ic2.neiIntegration.core.recipehandler.OreWashingRecipeHandler;
import ic2.neiIntegration.core.recipehandler.ScrapboxRecipeHandler;
import ic2.neiIntegration.core.recipehandler.SolidCannerRecipeHandler;
import ruiseki.jfi.jfmuy.ic2.crafting.AdvRecipeCategory;
import ruiseki.jfi.jfmuy.ic2.crafting.AdvShapelessRecipeCategory;
import ruiseki.jfi.jfmuy.ic2.machine.blastfurnace.BlastFurnaceCategory;
import ruiseki.jfi.jfmuy.ic2.machine.blockcutter.BlockCutterCategory;
import ruiseki.jfi.jfmuy.ic2.machine.centrifuge.CentrifugeCategory;
import ruiseki.jfi.jfmuy.ic2.machine.compressor.CompressorCategory;
import ruiseki.jfi.jfmuy.ic2.machine.extractor.ExtractorCategory;
import ruiseki.jfi.jfmuy.ic2.machine.fluidcanner.FluidCannerRecipeCategory;
import ruiseki.jfi.jfmuy.ic2.machine.lathe.LatheRecipeCategory;
import ruiseki.jfi.jfmuy.ic2.machine.macerator.MaceratorCategory;
import ruiseki.jfi.jfmuy.ic2.machine.metalformer.MetalFormerCuttingCategory;
import ruiseki.jfi.jfmuy.ic2.machine.metalformer.MetalFormerExtrudingCategory;
import ruiseki.jfi.jfmuy.ic2.machine.metalformer.MetalFormerRollingCategory;
import ruiseki.jfi.jfmuy.ic2.machine.orewashing.OreWashingCategory;
import ruiseki.jfi.jfmuy.ic2.machine.solidcanner.SolidCannerRecipeCategory;
import ruiseki.jfi.jfmuy.ic2.scrapbox.ScrapboxRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.jfmuy.plugins.nei.RecipeHarvester;

@JFMUYPlugin(value = "IC2")
public class IC2Plugin implements IModPlugin {

    @Override
    public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
        if (Loader.isModLoaded("jfic2c")) return;
        if (Loader.isModLoaded("NotEnoughItems")) {
            try {
                RecipeHarvester.addBlacklistedClass(AdvRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(AdvShapelessRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(MaceratorRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(ExtractorRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(CompressorRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(ScrapboxRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(MetalFormerRecipeHandlerExtruding.class);
                RecipeHarvester.addBlacklistedClass(MetalFormerRecipeHandlerCutting.class);
                RecipeHarvester.addBlacklistedClass(MetalFormerRecipeHandlerRolling.class);
                RecipeHarvester.addBlacklistedClass(CentrifugeRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(BlockCutterRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(OreWashingRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(SolidCannerRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(BlastFurnaceRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(LatheRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(FluidCannerRecipeHandler.class);
            } catch (Throwable ignore) {}
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        if (Loader.isModLoaded("jfic2c")) return;
        try {
            AdvRecipeCategory.register(registry);
            AdvShapelessRecipeCategory.register(registry);
            MaceratorCategory.register(registry);
            ExtractorCategory.register(registry);
            CompressorCategory.register(registry);
            ScrapboxRecipeCategory.register(registry);
            MetalFormerCuttingCategory.register(registry);
            MetalFormerExtrudingCategory.register(registry);
            MetalFormerRollingCategory.register(registry);
            CentrifugeCategory.register(registry);
            BlockCutterCategory.register(registry);
            OreWashingCategory.register(registry);
            SolidCannerRecipeCategory.register(registry);
            BlastFurnaceCategory.register(registry);
            LatheRecipeCategory.register(registry);
            FluidCannerRecipeCategory.register(registry);
        } catch (Throwable ignore) {}
    }

    @Override
    public void register(IModRegistry registry) {
        if (Loader.isModLoaded("jfic2c")) return;
        try {
            AdvRecipeCategory.initialize(registry);
            AdvShapelessRecipeCategory.initialize(registry);
            MaceratorCategory.initialize(registry);
            ExtractorCategory.initialize(registry);
            CompressorCategory.initialize(registry);
            ScrapboxRecipeCategory.initialize(registry);
            MetalFormerCuttingCategory.initialize(registry);
            MetalFormerExtrudingCategory.initialize(registry);
            MetalFormerRollingCategory.initialize(registry);
            CentrifugeCategory.initialize(registry);
            BlockCutterCategory.initialize(registry);
            OreWashingCategory.initialize(registry);
            SolidCannerRecipeCategory.initialize(registry);
            BlastFurnaceCategory.initialize(registry);
            LatheRecipeCategory.initialize(registry);
            FluidCannerRecipeCategory.initialize(registry);

            registry.addRecipeCatalyst(Ic2Items.ironFurnace, VanillaRecipeCategoryUid.SMELTING);
            registry.addRecipeCatalyst(Ic2Items.electroFurnace, VanillaRecipeCategoryUid.SMELTING);
            registry.addRecipeCatalyst(Ic2Items.inductionFurnace, VanillaRecipeCategoryUid.SMELTING);
        } catch (Throwable ignore) {}
    }
}
