package ruiseki.jfi.jfmuy.ic2;

import ruiseki.jfi.jfmuy.ic2.crafting.AdvRecipeCategory;
import ruiseki.jfi.jfmuy.ic2.crafting.AdvShapelessRecipeCategory;
import ruiseki.jfi.jfmuy.ic2.machine.blockcutter.BlockCutterCategory;
import ruiseki.jfi.jfmuy.ic2.machine.centrifuge.CentrifugeCategory;
import ruiseki.jfi.jfmuy.ic2.machine.compressor.CompressorCategory;
import ruiseki.jfi.jfmuy.ic2.machine.extractor.ExtractorCategory;
import ruiseki.jfi.jfmuy.ic2.machine.macerator.MaceratorCategory;
import ruiseki.jfi.jfmuy.ic2.machine.metalformer.MetalFormerCuttingCategory;
import ruiseki.jfi.jfmuy.ic2.machine.metalformer.MetalFormerExtrudingCategory;
import ruiseki.jfi.jfmuy.ic2.machine.metalformer.MetalFormerRollingCategory;
import ruiseki.jfi.jfmuy.ic2.machine.orewashing.OreWashingCategory;
import ruiseki.jfi.jfmuy.ic2.scrapbox.ScrapboxRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin
public class IC2Plugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
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
    }

    @Override
    public void register(IModRegistry registry) {
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
    }
}
