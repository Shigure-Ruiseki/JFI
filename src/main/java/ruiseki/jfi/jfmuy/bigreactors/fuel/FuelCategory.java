package ruiseki.jfi.jfmuy.bigreactors.fuel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;

import erogenousbeef.bigreactors.api.data.ReactorReaction;
import erogenousbeef.bigreactors.api.data.SourceProductMapping;
import erogenousbeef.bigreactors.common.BigReactors;
import ruiseki.jfi.jfmuy.bigreactors.ExtremeReactorsData;
import ruiseki.jfi.util.OreDictHelper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class FuelCategory implements IRecipeCategory<FuelWrapper> {

    public static final String UID = "bigreactors.fuel";

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

    private final IDrawable icon;
    private final IDrawable foreground;
    private final IDrawable background;

    public FuelCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BigReactors.ingotGeneric, 1, 0));
        this.background = guiHelper.createBlankDrawable(94, 36);
        this.foreground = guiHelper
            .createDrawable(new ResourceLocation("jfi:textures/gui/bigreactors/background.png"), 0, 0, 76, 18);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("jfi.gui.fuel");
    }

    @Override
    public String getModName() {
        return BigReactors.NAME;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.foreground.draw(minecraft, 9, 9);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FuelWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 9, 9);
        itemStacks.init(1, false, 67, 9);
        itemStacks.set(ingredients);
    }
}
