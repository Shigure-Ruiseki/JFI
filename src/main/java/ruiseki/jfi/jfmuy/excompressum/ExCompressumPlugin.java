package ruiseki.jfi.jfmuy.excompressum;

import net.blay09.mods.excompressum.ModBlocks;
import net.blay09.mods.excompressum.ModItems;
import net.blay09.mods.excompressum.compat.nei.RecipeHandlerBarrelProcess;
import net.blay09.mods.excompressum.compat.nei.RecipeHandlerComposting;
import net.blay09.mods.excompressum.compat.nei.RecipeHandlerCompressedHammer;
import net.blay09.mods.excompressum.compat.nei.RecipeHandlerHeavySieve;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Loader;
import ruiseki.jfi.jfmuy.excompressum.barrel.BarrelProcessRecipeCategory;
import ruiseki.jfi.jfmuy.excompressum.composting.CompostingRecipeCategory;
import ruiseki.jfi.jfmuy.excompressum.hammer.CompressedHammerRecipeCategory;
import ruiseki.jfi.jfmuy.excompressum.sieve.HeavySieveRecipeCategory;
import ruiseki.jfi.jfmuy.exnihilo.hammer.HammerRecipeCategory;
import ruiseki.jfi.jfmuy.exnihilo.sieve.SieveRecipeCategory;
import ruiseki.jfi.jfmuy.nei.RecipeHarvester;
import ruiseki.jfmuy.api.IModPlugin;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.ISubtypeRegistry;
import ruiseki.jfmuy.api.JFMUYPlugin;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

@JFMUYPlugin(value = "excompressum")
public class ExCompressumPlugin implements IModPlugin {

    @Override
    public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
        if (Loader.isModLoaded("NotEnoughItems")) {
            try {
                RecipeHarvester.addBlacklistedClass(RecipeHandlerBarrelProcess.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerComposting.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerCompressedHammer.class);
                RecipeHarvester.addBlacklistedClass(RecipeHandlerHeavySieve.class);
            } catch (Throwable ignore) {}
        }
    }

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
