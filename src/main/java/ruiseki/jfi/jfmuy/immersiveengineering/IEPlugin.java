package ruiseki.jfi.jfmuy.immersiveengineering;

import ruiseki.jfi.jfmuy.immersiveengineering.arcfurnace.ArcFurnaceRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.blastfurnace.BlastFurnaceRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.blueprint.BlueprintRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.bottling.BottlingRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.cokeoven.CokeOvenRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "ImmersiveEngineering")
public class IEPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        CokeOvenRecipeCategory.register(registry);
        BottlingRecipeCategory.register(registry);
        BlueprintRecipeCategory.register(registry);
        BlastFurnaceRecipeCategory.register(registry);
        ArcFurnaceRecipeCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        CokeOvenRecipeCategory.initialize(registry);
        BottlingRecipeCategory.initialize(registry);
        BlueprintRecipeCategory.initialize(registry);
        BlastFurnaceRecipeCategory.initialize(registry);
        ArcFurnaceRecipeCategory.initialize(registry);
    }
}
