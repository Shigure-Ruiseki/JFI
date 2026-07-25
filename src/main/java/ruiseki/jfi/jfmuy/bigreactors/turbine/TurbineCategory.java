package ruiseki.jfi.jfmuy.bigreactors.turbine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.google.common.collect.ImmutableList;

import erogenousbeef.bigreactors.common.BigReactors;
import erogenousbeef.bigreactors.common.multiblock.block.BlockTurbinePart;
import erogenousbeef.bigreactors.common.multiblock.block.BlockTurbineRotorPart;
import ruiseki.jfi.jfmuy.bigreactors.DrawableFrame;
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

public class TurbineCategory implements IRecipeCategory<TurbineWrapper> {

    public static final String UID = "bigreactors.turbine";

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

    private final IDrawable icon;
    private final IDrawable background;

    public TurbineCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(BigReactors.blockTurbinePart));
        this.background = new DrawableFrame(
            new ResourceLocation("bigreactors:textures/blocks/tile.blockTurbinePart.housing.corner.png"),
            new ResourceLocation("bigreactors:textures/blocks/tile.blockTurbinePart.housing.edge.0.png"),
            new ResourceLocation("bigreactors:textures/blocks/tile.blockTurbinePart.housing.face.png"));
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("jfi.gui.turbine");
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
    public void setRecipe(IRecipeLayout recipeLayout, TurbineWrapper recipeWrapper, IIngredients ingredients) {
        final IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 15, 15);

        itemStacks.set(ingredients);
    }
}
