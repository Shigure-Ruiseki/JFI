package ruiseki.jfi.jfmuy.bigreactors.reactors.recipe;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableList;

import erogenousbeef.bigreactors.common.BigReactors;
import ruiseki.jfi.jfmuy.bigreactors.reactor.ReactorCategory;
import ruiseki.jfi.jfmuy.bigreactors.reactor.ReactorEntry;
import ruiseki.jfi.jfmuy.bigreactors.reactor.ReactorWrapper;
import ruiseki.jfi.jfmuy.bigreactors.reactors.ExtremeReactorsData;
import ruiseki.jfi.util.OreDictHelper;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class ReactorRecipes {

    public static void register(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
            new ReactorCategory(
                registry.getJFMUYHelpers()
                    .getGuiHelper()));
    }

    public static void initialize(IModRegistry registry) {
        if (BigReactors.blockMultiblockGlass != null) {
            ItemStack reactorGlass = new ItemStack(BigReactors.blockMultiblockGlass, 1, 0);
            registry.addRecipeCatalyst(reactorGlass, ReactorCategory.UID);
        }

        if (BigReactors.blockReactorPart != null) {
            ItemStack reactorController = new ItemStack(BigReactors.blockReactorPart, 1, 1);
            ItemStack reactorCasing = new ItemStack(BigReactors.blockReactorPart, 1, 0);
            registry.addRecipeCatalyst(reactorController, ReactorCategory.UID);
            registry.addRecipeCatalyst(reactorCasing, ReactorCategory.UID);
        }

        final ImmutableList<String> blocks = getBlocks();
        final ImmutableList<String> fluids = getFluids();

        registry.addRecipes(
            Stream.concat(
                blocks.stream()
                    .filter(OreDictHelper::doesOreExist)
                    .map(ReactorEntry::newBlock),
                fluids.stream()
                    .filter(OreDictHelper::doesFluidExist)
                    .map(ReactorEntry::newFluid))
                .map(ReactorWrapper::new)
                .collect(Collectors.toList()),
            ReactorCategory.UID);
    }

    private static ImmutableList<String> getBlocks() {
        return ImmutableList.copyOf(ExtremeReactorsData.ReactorInterior_reactorModeratorBlocks.keySet());
    }

    private static ImmutableList<String> getFluids() {
        return ImmutableList.copyOf(ExtremeReactorsData.ReactorInterior_reactorModeratorFluids.keySet());
    }
}
