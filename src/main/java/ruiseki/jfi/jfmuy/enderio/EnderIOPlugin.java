package ruiseki.jfi.jfmuy.enderio;

import ruiseki.jfi.jfmuy.enderio.alloy.AlloySmelterRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.enchanter.EnchanterRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.sagmill.SagMillRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.slicensplice.SliceAndSpliceRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.soulbinder.SoulBinderRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.vat.VatRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "EnderIO")
public class EnderIOPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        AlloySmelterRecipeCategory.register(registry);
        EnchanterRecipeCategory.register(registry);
        SagMillRecipeCategory.register(registry);
        SliceAndSpliceRecipeCategory.register(registry);
        SoulBinderRecipeCategory.register(registry);
        VatRecipeCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        AlloySmelterRecipeCategory.initialize(registry);
        EnchanterRecipeCategory.initialize(registry);
        SagMillRecipeCategory.initialize(registry);
        SliceAndSpliceRecipeCategory.initialize(registry);
        SoulBinderRecipeCategory.initialize(registry);
        VatRecipeCategory.initialize(registry);
    }

}
