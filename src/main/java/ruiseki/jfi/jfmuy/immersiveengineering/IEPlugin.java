package ruiseki.jfi.jfmuy.immersiveengineering;

import ruiseki.jfi.jfmuy.immersiveengineering.arcfurnace.ArcFurnaceRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.blastfurnace.BlastFurnaceRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.blueprint.BlueprintRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.bottling.BottlingRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.cokeoven.CokeOvenRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.crusher.CrusherRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.fermenter.FermenterRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.metalpress.MetalPressRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.refinery.RefineryRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.shaderbag.ShaderBagRecipeCategory;
import ruiseki.jfi.jfmuy.immersiveengineering.squeezer.SqueezerRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "ImmersiveEngineering")
public class IEPlugin implements IModPlugin {

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
