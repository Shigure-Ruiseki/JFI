package ruiseki.jfi.jfmuy.bigreactors.reactor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.google.common.collect.ImmutableList;

import erogenousbeef.bigreactors.common.BigReactors;
import ruiseki.jfi.jfmuy.bigreactors.DrawableFrame;
import ruiseki.jfi.jfmuy.bigreactors.ExtremeReactorsData;
import ruiseki.jfi.util.OreDictHelper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawable;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.recipe.IRecipeCategory;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class ReactorCategory implements IRecipeCategory<ReactorWrapper> {

    public static final String UID = "bigreactors.reactor";

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

    private final IDrawable icon;
    private final IDrawable background;

    public ReactorCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BigReactors.blockReactorPart));
        this.background = new DrawableFrame(
            new ResourceLocation("bigreactors:textures/blocks/tile.blockReactorPart.casing.corner.png"),
            new ResourceLocation("bigreactors:textures/blocks/tile.blockReactorPart.casing.eastwest.png"),
            new ResourceLocation("bigreactors:textures/blocks/tile.blockReactorPart.casing.face.png"));
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("jfi.gui.reactor");
    }

    @Override
    public String getModName() {
        return BigReactors.NAME;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ReactorWrapper recipeWrapper, IIngredients ingredients) {
        if (recipeWrapper.getReactorEntry()
            .isBlock()) {
            final IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

            itemStacks.init(0, true, 15, 15);

            itemStacks.set(ingredients);
        } else {
            final IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

            fluidStacks.init(0, true, 16, 16);

            fluidStacks.set(ingredients);
        }
    }
}
