package ruiseki.jfi.jfmuy.bigreactors.reactors.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableList;

import erogenousbeef.bigreactors.common.BigReactors;
import erogenousbeef.bigreactors.common.multiblock.block.BlockTurbinePart;
import erogenousbeef.bigreactors.common.multiblock.block.BlockTurbineRotorPart;
import ruiseki.jfi.jfmuy.bigreactors.reactors.ExtremeReactorsData;
import ruiseki.jfi.jfmuy.bigreactors.turbine.TurbineCategory;
import ruiseki.jfi.jfmuy.bigreactors.turbine.TurbineEntry;
import ruiseki.jfi.jfmuy.bigreactors.turbine.TurbineWrapper;
import ruiseki.jfi.util.OreDictHelper;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class TurbineRecipes {

    public static void register(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
            new TurbineCategory(
                registry.getJFMUYHelpers()
                    .getGuiHelper()));
    }

    public static void initialize(IModRegistry registry) {
        if (BigReactors.blockTurbineRotorPart != null) {
            registry.addRecipeCatalyst(
                new ItemStack(BigReactors.blockTurbineRotorPart, 1, BlockTurbineRotorPart.METADATA_SHAFT),
                TurbineCategory.UID);
            registry.addRecipeCatalyst(
                new ItemStack(BigReactors.blockTurbineRotorPart, 1, BlockTurbineRotorPart.METADATA_BLADE),
                TurbineCategory.UID);
        }

        if (BigReactors.blockTurbinePart != null) {
            registry.addRecipeCatalyst(
                new ItemStack(BigReactors.blockTurbinePart, 1, BlockTurbinePart.METADATA_HOUSING),
                TurbineCategory.UID);
            registry.addRecipeCatalyst(
                new ItemStack(BigReactors.blockTurbinePart, 1, BlockTurbinePart.METADATA_CONTROLLER),
                TurbineCategory.UID);
            registry.addRecipeCatalyst(
                new ItemStack(BigReactors.blockTurbinePart, 1, BlockTurbinePart.METADATA_BEARING),
                TurbineCategory.UID);
        }

        if (BigReactors.blockMultiblockGlass != null) {
            registry.addRecipeCatalyst(new ItemStack(BigReactors.blockMultiblockGlass, 1, 0), TurbineCategory.UID);
        }

        List<TurbineWrapper> recipes = new ArrayList<>();
        for (String blockKey : getBlocks()) {
            if (OreDictHelper.doesOreExist(blockKey)) {
                recipes.add(new TurbineWrapper(new TurbineEntry(blockKey)));
            }
        }

        registry.addRecipes(recipes, TurbineCategory.UID);
    }

    private static ImmutableList<String> getBlocks() {
        if (ExtremeReactorsData.TurbineCoil_blocks == null) {
            return ImmutableList.of();
        }
        return ImmutableList.copyOf(ExtremeReactorsData.TurbineCoil_blocks.keySet());
    }
}
