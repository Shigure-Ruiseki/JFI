package ruiseki.jfi.jfmuy.enderio;

import net.minecraft.item.ItemStack;

import crazypants.enderio.EnderIO;
import ruiseki.jfi.jfmuy.enderio.alloy.AlloySmelterRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.alloy.AlloySmelterRecipeMarker;
import ruiseki.jfi.jfmuy.enderio.enchanter.EnchanterRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.enchanter.EnchanterRecipeMarker;
import ruiseki.jfi.jfmuy.enderio.sagmill.SagMillRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.sagmill.SagMillRecipeMarker;
import ruiseki.jfi.jfmuy.enderio.slicensplice.SliceAndSpliceRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.slicensplice.SliceAndSpliceRecipeMarker;
import ruiseki.jfi.jfmuy.enderio.soulbinder.SoulBinderRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.soulbinder.SoulBinderRecipeMarker;
import ruiseki.jfi.jfmuy.enderio.vat.VatRecipeCategory;
import ruiseki.jfi.jfmuy.enderio.vat.VatRecipeMarker;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "EnderIO")
public class EnderIOPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jfmuyHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jfmuyHelpers.getGuiHelper();
        registry.addRecipeCategories(
            new AlloySmelterRecipeCategory(guiHelper),
            new EnchanterRecipeCategory(guiHelper),
            new SagMillRecipeCategory(guiHelper),
            new SliceAndSpliceRecipeCategory(guiHelper),
            new SoulBinderRecipeCategory(guiHelper),
            new VatRecipeCategory(guiHelper));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(AlloySmelterRecipeMarker.getValidRecipes(), AlloySmelterRecipeCategory.UID);
        registry.addRecipes(EnchanterRecipeMarker.getValidRecipes(), EnchanterRecipeCategory.UID);
        registry.addRecipes(SagMillRecipeMarker.getValidRecipes(), SagMillRecipeCategory.UID);
        registry.addRecipes(SliceAndSpliceRecipeMarker.getValidRecipes(), SliceAndSpliceRecipeCategory.UID);
        registry.addRecipes(SoulBinderRecipeMarker.getValidRecipes(), SoulBinderRecipeCategory.UID);
        registry.addRecipes(VatRecipeMarker.getValidRecipes(), VatRecipeCategory.UID);

        registry.addRecipeCatalyst(new ItemStack(EnderIO.blockAlloySmelter), AlloySmelterRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(EnderIO.blockEnchanter), EnchanterRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(EnderIO.blockCrusher), SagMillRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(EnderIO.blockSliceAndSplice), SliceAndSpliceRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(EnderIO.blockSoulFuser), SoulBinderRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(EnderIO.blockVat), VatRecipeCategory.UID);
    }

}
