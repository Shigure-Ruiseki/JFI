package ruiseki.jfi.jfmuy.ie;

import blusunrize.immersiveengineering.client.nei.NEIBlastFurnaceHandler;
import blusunrize.immersiveengineering.client.nei.NEIBlueprintHandler;
import blusunrize.immersiveengineering.client.nei.NEIBottlingMachineHandler;
import blusunrize.immersiveengineering.client.nei.NEICokeOvenHandler;
import blusunrize.immersiveengineering.client.nei.NEICrusherHandler;
import blusunrize.immersiveengineering.client.nei.NEIFermenterHandler;
import blusunrize.immersiveengineering.client.nei.NEIMetalPressHandler;
import blusunrize.immersiveengineering.client.nei.NEIRefineryHandler;
import blusunrize.immersiveengineering.client.nei.NEIShaderBagHandler;
import blusunrize.immersiveengineering.client.nei.NEISqueezerHandler;
import cpw.mods.fml.common.Loader;
import ruiseki.jfi.jfmuy.ie.arcfurnace.ArcFurnaceRecipeCategory;
import ruiseki.jfi.jfmuy.ie.blastfurnace.BlastFurnaceRecipeCategory;
import ruiseki.jfi.jfmuy.ie.blueprint.BlueprintRecipeCategory;
import ruiseki.jfi.jfmuy.ie.bottling.BottlingRecipeCategory;
import ruiseki.jfi.jfmuy.ie.cokeoven.CokeOvenRecipeCategory;
import ruiseki.jfi.jfmuy.ie.crusher.CrusherRecipeCategory;
import ruiseki.jfi.jfmuy.ie.fermenter.FermenterRecipeCategory;
import ruiseki.jfi.jfmuy.ie.metalpress.MetalPressRecipeCategory;
import ruiseki.jfi.jfmuy.ie.refinery.RefineryRecipeCategory;
import ruiseki.jfi.jfmuy.ie.shaderbag.ShaderBagRecipeCategory;
import ruiseki.jfi.jfmuy.ie.squeezer.SqueezerRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.plugins.nei.RecipeHarvester;

@JFMUYPlugin(value = "ImmersiveEngineering")
public class IEPlugin implements IModPlugin {

    @Override
    public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
        if (Loader.isModLoaded("NotEnoughItems")) {
            try {
                RecipeHarvester.addBlacklistedClass(NEIShaderBagHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIBlueprintHandler.class);
                RecipeHarvester.addBlacklistedClass(NEICokeOvenHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIBlastFurnaceHandler.class);
                RecipeHarvester.addBlacklistedClass(NEISqueezerHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIFermenterHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIRefineryHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIBottlingMachineHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIMetalPressHandler.class);
                RecipeHarvester.addBlacklistedClass(NEICrusherHandler.class);
            } catch (Throwable ignore) {}
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        ArcFurnaceRecipeCategory.register(registry);
        BottlingRecipeCategory.register(registry);
        BlueprintRecipeCategory.register(registry);
        BlastFurnaceRecipeCategory.register(registry);
        CokeOvenRecipeCategory.register(registry);
        CrusherRecipeCategory.register(registry);
        FermenterRecipeCategory.register(registry);
        MetalPressRecipeCategory.register(registry);
        RefineryRecipeCategory.register(registry);
        ShaderBagRecipeCategory.register(registry);
        SqueezerRecipeCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        CokeOvenRecipeCategory.initialize(registry);
        BottlingRecipeCategory.initialize(registry);
        BlueprintRecipeCategory.initialize(registry);
        BlastFurnaceRecipeCategory.initialize(registry);
        ArcFurnaceRecipeCategory.initialize(registry);
        CrusherRecipeCategory.initialize(registry);
        FermenterRecipeCategory.initialize(registry);
        MetalPressRecipeCategory.initialize(registry);
        RefineryRecipeCategory.initialize(registry);
        ShaderBagRecipeCategory.initialize(registry);
        SqueezerRecipeCategory.initialize(registry);
    }
}
