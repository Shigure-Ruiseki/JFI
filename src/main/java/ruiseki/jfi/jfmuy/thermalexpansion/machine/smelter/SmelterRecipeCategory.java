package ruiseki.jfi.jfmuy.thermalexpansion.machine.smelter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.gui.client.machine.GuiSmelter;
import cofh.thermalexpansion.util.crafting.SmelterManager;
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

public class SmelterRecipeCategory extends BaseRecipeCategory<SmelterRecipeWrapper> {

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new SmelterRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), RecipeUidsTE.SMELTER);
            registry.addRecipeClickArea(GuiSmelter.class, 79, 34, 24, 16, RecipeUidsTE.SMELTER);
            registry.addRecipeCatalyst(BlockMachine.smelter, RecipeUidsTE.SMELTER);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<SmelterRecipeWrapper> getRecipes(IGuiHelper guiHelper) {

        List<SmelterRecipeWrapper> recipes = new ArrayList<>();

        for (SmelterManager.RecipeSmelter recipe : SmelterManager.getRecipeList()) {
            recipes.add(new SmelterRecipeWrapper(guiHelper, recipe));
        }
        return recipes;
    }

    protected IDrawableStatic progress;
    protected IDrawableStatic speed;

    public SmelterRecipeCategory(IGuiHelper guiHelper) {

        background = guiHelper.drawableBuilder(GuiSmelter.TEXTURE, 26, 11, 124, 62)
            .addPadding(0, 0, 16, 10)
            .build();
        energyMeter = Drawables.getDrawables(guiHelper)
            .getEnergyEmpty();
        localizedName = StringHelper.localize("tile.thermalexpansion.machine.smelter.name");

        progress = Drawables.getDrawables(guiHelper)
            .getProgress(Drawables.PROGRESS_ARROW);
        speed = Drawables.getDrawables(guiHelper)
            .getScale(Drawables.SCALE_FLAME);
    }

    @Nonnull
    @Override
    public String getUid() {

        return RecipeUidsTE.SMELTER;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

        progress.draw(minecraft, 69, 23);
        speed.draw(minecraft, 34, 33);
        energyMeter.draw(minecraft, 2, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SmelterRecipeWrapper recipeWrapper, IIngredients ingredients) {

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 21, 14);
        guiItemStacks.init(1, true, 45, 14);
        guiItemStacks.init(2, false, 105, 14);

        guiItemStacks.set(0, inputs.get(0));
        guiItemStacks.set(1, inputs.get(1));
        guiItemStacks.set(2, outputs.get(0));

        if (outputs.size() > 1) {
            guiItemStacks.init(3, false, 105, 41);
            guiItemStacks.set(3, outputs.get(1));

            guiItemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {

                if (slotIndex == 3) {
                    tooltip.add(StringHelper.localize("info.cofh.chance") + ": " + recipeWrapper.chance + "%");
                }
            });
        }
    }

}
