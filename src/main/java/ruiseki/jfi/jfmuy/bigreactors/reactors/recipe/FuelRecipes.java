package ruiseki.jfi.jfmuy.bigreactors.reactors.recipe;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;

import erogenousbeef.bigreactors.api.data.ReactorReaction;
import erogenousbeef.bigreactors.api.data.SourceProductMapping;
import erogenousbeef.bigreactors.common.BigReactors;
import ruiseki.jfi.jfmuy.bigreactors.fuel.FuelCategory;
import ruiseki.jfi.jfmuy.bigreactors.fuel.FuelEntry;
import ruiseki.jfi.jfmuy.bigreactors.fuel.FuelWrapper;
import ruiseki.jfi.jfmuy.bigreactors.reactors.ExtremeReactorsData;
import ruiseki.jfi.util.OreDictHelper;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class FuelRecipes {

    public static void register(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
            new FuelCategory(
                registry.getJFMUYHelpers()
                    .getGuiHelper()));
    }

    public static void initialize(IModRegistry registry) {
        if (BigReactors.blockYelloriumFuelRod != null) {
            registry.addRecipeCatalyst(new ItemStack(BigReactors.blockYelloriumFuelRod, 1, 0), FuelCategory.UID);
        }

        if (BigReactors.blockReactorPart != null) {
            ItemStack reactorController = new ItemStack(BigReactors.blockReactorPart, 1, 1);
            registry.addRecipeCatalyst(reactorController, FuelCategory.UID);

            ItemStack controlRod = new ItemStack(BigReactors.blockReactorPart, 1, 2);
            registry.addRecipeCatalyst(controlRod, FuelCategory.UID);

            ItemStack accessPort = new ItemStack(BigReactors.blockReactorPart, 1, 4);
            registry.addRecipeCatalyst(accessPort, FuelCategory.UID);
        }

        final ImmutableSortedMap<String, String> conversionMapping = getConversionMapping();
        final ImmutableMap<String, List<String>> fuelOreDictMapping = getFuelOreDictMapping();

        registry.addRecipes(
            conversionMapping.entrySet()
                .stream()
                .filter(
                    entry -> fuelOreDictMapping.containsKey(entry.getKey())
                        && fuelOreDictMapping.containsKey(entry.getValue()))
                .map(
                    entry -> new FuelEntry(
                        fuelOreDictMapping.get(entry.getKey()),
                        fuelOreDictMapping.get(entry.getValue())))
                .map(FuelWrapper::new)
                .collect(Collectors.toList()),
            FuelCategory.UID);
    }

    private static ImmutableSortedMap<String, String> getConversionMapping() {
        Map<String, String> map = ExtremeReactorsData.ReactorConversions_reactions.values()
            .stream()
            .collect(
                Collectors.toMap(
                    ReactorReaction::getSource,
                    ReactorReaction::getProduct,
                    (existing, replacement) -> existing));

        return ImmutableSortedMap.copyOf(map);
    }

    private static ImmutableMap<String, List<String>> getFuelOreDictMapping() {
        Map<String, List<String>> map = ExtremeReactorsData.Reactants_reactantToSolid.entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue()
                        .stream()
                        .map(SourceProductMapping::getProduct)
                        .distinct()
                        .filter(OreDictHelper::doesOreExist)
                        .collect(Collectors.toList())))
            .entrySet()
            .stream()
            .filter(
                entry -> !entry.getValue()
                    .isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return ImmutableMap.copyOf(map);
    }
}
