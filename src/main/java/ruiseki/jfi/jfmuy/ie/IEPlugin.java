package ruiseki.jfi.jfmuy.ie;

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
