package ruiseki.jfi.jfmuy.excompressum;

import cpw.mods.fml.common.Loader;
import net.blay09.mods.excompressum.ModBlocks;
import net.blay09.mods.excompressum.ModItems;
import net.minecraft.item.ItemStack;

import ruiseki.jfi.jfmuy.excompressum.barrel.BarrelProcessRecipeCategory;
import ruiseki.jfi.jfmuy.excompressum.composting.CompostingRecipeCategory;
import ruiseki.jfi.jfmuy.excompressum.hammer.CompressedHammerRecipeCategory;
import ruiseki.jfi.jfmuy.excompressum.sieve.HeavySieveRecipeCategory;
import ruiseki.jfi.jfmuy.exnihilo.hammer.HammerRecipeCategory;
import ruiseki.jfi.jfmuy.exnihilo.sieve.SieveRecipeCategory;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "excompressum")
public class ExCompressumPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        BarrelProcessRecipeCategory.register(registry);
        CompostingRecipeCategory.register(registry);
        CompressedHammerRecipeCategory.register(registry);
        HeavySieveRecipeCategory.register(registry);
    }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(ModItems.chickenStick), HammerRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModItems.oreSmasher), HammerRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.autoHammer), HammerRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.autoSieve), SieveRecipeCategory.UID);
        if (Loader.isModLoaded("Botania")) {
            registry.addRecipeCatalyst(new ItemStack(ModBlocks.manaSieve), SieveRecipeCategory.UID);
        }
        BarrelProcessRecipeCategory.initialize(registry);
        CompostingRecipeCategory.initialize(registry);
        CompressedHammerRecipeCategory.initialize(registry);
        HeavySieveRecipeCategory.initialize(registry);
    }
}
