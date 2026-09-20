package ruiseki.jfi.jfmuy.ae2;

import appeng.container.implementations.ContainerCraftingTerm;
import appeng.core.AEConfig;
import appeng.core.features.AEFeature;
import appeng.integration.modules.NEIHelpers.NEIAEShapedRecipeHandler;
import appeng.integration.modules.NEIHelpers.NEIAEShapelessRecipeHandler;
import appeng.integration.modules.NEIHelpers.NEIFacadeRecipeHandler;
import appeng.integration.modules.NEIHelpers.NEIGrinderRecipeHandler;
import appeng.integration.modules.NEIHelpers.NEIInscriberRecipeHandler;
import appeng.integration.modules.NEIHelpers.NEIWorldCraftingHandler;
import appeng.recipes.game.ShapedRecipe;
import appeng.recipes.game.ShapelessRecipe;
import cpw.mods.fml.common.Loader;
import ruiseki.jfi.jfmuy.ae2.crafting.AE2CraftingHandler;
import ruiseki.jfi.jfmuy.ae2.crafting.AE2CraftingPatternHandler;
import ruiseki.jfi.jfmuy.ae2.crafting.AEShapedRecipeWrapper;
import ruiseki.jfi.jfmuy.ae2.crafting.AEShapelessRecipeWrapper;
import ruiseki.jfi.jfmuy.ae2.facade.FacadeRecipeCategory;
import ruiseki.jfi.jfmuy.ae2.grinder.GrinderRecipeCategory;
import ruiseki.jfi.jfmuy.ae2.inscriber.InscriberRecipeCategory;
import ruiseki.jfi.jfmuy.ae2.worldcrafting.WorldCraftingRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.api.recipe.VanillaRecipeCategoryUid;
import ruiseki.jfmuy.plugins.nei.RecipeHarvester;

@JFMUYPlugin(value = "appliedenergistics2")
public class AE2Plugin implements IModPlugin {

    @Override
    public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
        if (Loader.isModLoaded("NotEnoughItems")) {
            try {
                RecipeHarvester.addBlacklistedClass(NEIAEShapedRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIAEShapelessRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIInscriberRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIWorldCraftingHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIGrinderRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(NEIFacadeRecipeHandler.class);
            } catch (Throwable ignore) {}
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        try {
            InscriberRecipeCategory.register(registry);
            WorldCraftingRecipeCategory.register(registry);
            if (AEConfig.instance.isFeatureEnabled(AEFeature.GrindStone)) {
                GrinderRecipeCategory.register(registry);
            }
            if (AEConfig.instance.isFeatureEnabled(AEFeature.Facades)
                && AEConfig.instance.isFeatureEnabled(AEFeature.EnableFacadeCrafting)) {
                FacadeRecipeCategory.register(registry);
            }
        } catch (Throwable ignore) {}
    }

    @Override
    public void register(IModRegistry registry) {
        try {
            registry.addAdvancedGuiHandlers(new AE2GuiHandler());

            registry.handleRecipes(ShapedRecipe.class, AEShapedRecipeWrapper::new, VanillaRecipeCategoryUid.CRAFTING);
            registry
                .handleRecipes(ShapelessRecipe.class, AEShapelessRecipeWrapper::new, VanillaRecipeCategoryUid.CRAFTING);

            InscriberRecipeCategory.initialize(registry);
            WorldCraftingRecipeCategory.initialize(registry);
            if (AEConfig.instance.isFeatureEnabled(AEFeature.GrindStone)) {
                GrinderRecipeCategory.initialize(registry);
            }
            if (AEConfig.instance.isFeatureEnabled(AEFeature.Facades)
                && AEConfig.instance.isFeatureEnabled(AEFeature.EnableFacadeCrafting)) {
                FacadeRecipeCategory.initialize(registry);
            }

            registry.getRecipeTransferRegistry()
                .addRecipeTransferHandler(
                    new AE2CraftingHandler<>(ContainerCraftingTerm.class),
                    VanillaRecipeCategoryUid.CRAFTING);
            registry.getRecipeTransferRegistry()
                .addRecipeTransferHandler(new AE2CraftingPatternHandler(), VanillaRecipeCategoryUid.CRAFTING);
        } catch (Throwable ignore) {}
    }
}
