package ruiseki.jfi.jfmuy.enderio;

import cpw.mods.fml.common.Loader;
import crazypants.enderio.nei.AlloySmelterRecipeHandler;
import crazypants.enderio.nei.EnchanterRecipeHandler;
import crazypants.enderio.nei.SagMillRecipeHandler;
import crazypants.enderio.nei.SliceAndSpliceRecipeHandler;
import crazypants.enderio.nei.SoulBinderRecipeHandler;
import crazypants.enderio.nei.VatRecipeHandler;
import ruiseki.jfi.jfmuy.enderio.alloy.AlloySmelterRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.enchanter.EnchanterRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.sagmill.SagMillRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.slicensplice.SliceAndSpliceRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.soulbinder.SoulBinderRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.vat.VatRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;
import ruiseki.jfmuy.plugins.nei.RecipeHarvester;

@JFMUYPlugin(value = "EnderIO")
public class EnderIOPlugin implements IModPlugin {

    @Override
    public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
        if (Loader.isModLoaded("NotEnoughItems")) {
            try {
                RecipeHarvester.addBlacklistedClass(AlloySmelterRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(EnchanterRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(SagMillRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(SliceAndSpliceRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(SoulBinderRecipeHandler.class);
                RecipeHarvester.addBlacklistedClass(VatRecipeHandler.class);
            } catch (Throwable ignore) {}
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        try {
            AlloySmelterRecipeCategory.register(registry);
            EnchanterRecipeCategory.register(registry);
            SagMillRecipeCategory.register(registry);
            SliceAndSpliceRecipeCategory.register(registry);
            SoulBinderRecipeCategory.register(registry);
            VatRecipeCategory.register(registry);
        } catch (Throwable ignore) {}
    }

    @Override
    public void register(IModRegistry registry) {
        try {
            AlloySmelterRecipeCategory.initialize(registry);
            EnchanterRecipeCategory.initialize(registry);
            SagMillRecipeCategory.initialize(registry);
            SliceAndSpliceRecipeCategory.initialize(registry);
            SoulBinderRecipeCategory.initialize(registry);
            VatRecipeCategory.initialize(registry);
        } catch (Throwable ignore) {}
    }

}
