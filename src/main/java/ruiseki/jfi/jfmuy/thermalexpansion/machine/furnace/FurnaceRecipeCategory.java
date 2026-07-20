package ruiseki.jfi.jfmuy.thermalexpansion.machine.furnace;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.gui.client.machine.GuiFurnace;
import cofh.thermalexpansion.util.crafting.FurnaceManager;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.machine.BaseRecipeCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class FurnaceRecipeCategory extends BaseRecipeCategory<FurnaceRecipeWrapper> {

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new FurnaceRecipeCategory(guiHelper));
        registry.addRecipeCategories(new FurnaceRecipeCategoryFood(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), RecipeUidsTE.FURNACE);
            registry
                .addRecipeClickArea(GuiFurnace.class, 79, 34, 24, 16, RecipeUidsTE.FURNACE, RecipeUidsTE.FURNACE_FOOD);
            registry.addRecipeCatalyst(BlockMachine.furnace, RecipeUidsTE.FURNACE);

            FurnaceRecipeCategoryFood.initialize(registry);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<FurnaceRecipeWrapper> getRecipes(IGuiHelper guiHelper) {

        List<FurnaceRecipeWrapper> recipes = new ArrayList<>();

        for (FurnaceManager.RecipeFurnace recipe : FurnaceManager.getRecipeList()) {
            recipes.add(new FurnaceRecipeWrapper(guiHelper, recipe));
        }
        return recipes;
    }

    protected IDrawableStatic progress;
    protected IDrawableStatic speed;

    public FurnaceRecipeCategory(IGuiHelper guiHelper) {

        background = guiHelper.drawableBuilder(GuiFurnace.TEXTURE, 26, 11, 124, 62)
            .addPadding(0, 0, 16, 0)
            .build();
        energyMeter = Drawables.getDrawables(guiHelper)
            .getEnergyEmpty();
        localizedName = StringHelper.localize("tile.thermalexpansion.machine.furnace.name");

        progress = Drawables.getDrawables(guiHelper)
            .getProgress(Drawables.PROGRESS_ARROW);
        speed = Drawables.getDrawables(guiHelper)
            .getScale(Drawables.SCALE_FLAME);
    }

    @Nonnull
    @Override
    public String getUid() {

        return RecipeUidsTE.FURNACE;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

        progress.draw(minecraft, 69, 23);
        speed.draw(minecraft, 45, 33);
        energyMeter.draw(minecraft, 2, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FurnaceRecipeWrapper recipeWrapper, IIngredients ingredients) {

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 45, 14);
        guiItemStacks.init(1, false, 105, 23);

        guiItemStacks.set(0, inputs.getFirst());
        guiItemStacks.set(1, outputs.getFirst());
    }

}
