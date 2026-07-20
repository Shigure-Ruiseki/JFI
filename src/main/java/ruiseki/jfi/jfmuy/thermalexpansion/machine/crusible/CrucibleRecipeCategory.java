package ruiseki.jfi.jfmuy.thermalexpansion.machine.crusible;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.Level;

import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.core.TEProps;
import cofh.thermalexpansion.gui.client.machine.GuiCrucible;
import cofh.thermalexpansion.util.crafting.CrucibleManager;
import ruiseki.jfi.JFI;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.machine.BaseRecipeCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.IJFMUYHelpers;
import ruiseki.jfmuy.api.IModRegistry;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiFluidStackGroup;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeCategoryRegistration;

public class CrucibleRecipeCategory extends BaseRecipeCategory<CrucibleRecipeWrapper> {

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new CrucibleRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jfmuyHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jfmuyHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), RecipeUidsTE.CRUCIBLE);
            registry.addRecipeClickArea(GuiCrucible.class, 103, 34, 24, 16, RecipeUidsTE.CRUCIBLE);
            registry.addRecipeCatalyst(BlockMachine.crucible, RecipeUidsTE.CRUCIBLE);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<CrucibleRecipeWrapper> getRecipes(IGuiHelper guiHelper) {

        List<CrucibleRecipeWrapper> recipes = new ArrayList<>();

        for (CrucibleManager.RecipeCrucible recipe : CrucibleManager.getRecipeList()) {
            recipes.add(new CrucibleRecipeWrapper(guiHelper, recipe));
        }
        return recipes;
    }

    protected IDrawableStatic progress;
    protected IDrawableStatic speed;
    protected IDrawableStatic tank;
    protected IDrawableStatic tankOverlay;

    public CrucibleRecipeCategory(IGuiHelper guiHelper) {

        background = guiHelper.drawableBuilder(GuiCrucible.TEXTURE, 26, 11, 72, 62)
            .addPadding(0, 0, 16, 40)
            .build();
        energyMeter = Drawables.getDrawables(guiHelper)
            .getEnergyEmpty();
        localizedName = StringHelper.localize("tile.thermalexpansion.machine.crucible.name");

        progress = Drawables.getDrawables(guiHelper)
            .getProgress(2);
        speed = Drawables.getDrawables(guiHelper)
            .getScale(Drawables.SCALE_FLAME);
        tank = Drawables.getDrawables(guiHelper)
            .getTank(Drawables.TANK);
        tankOverlay = Drawables.getDrawables(guiHelper)
            .getTankLargeOverlay(Drawables.TANK);
    }

    @Nonnull
    @Override
    public String getUid() {

        return RecipeUidsTE.CRUCIBLE;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

        progress.draw(minecraft, 69, 23);
        speed.draw(minecraft, 45, 33);
        tank.draw(minecraft, 105, 0);
        energyMeter.draw(minecraft, 2, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CrucibleRecipeWrapper recipeWrapper, IIngredients ingredients) {

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<FluidStack>> outputs = ingredients.getOutputs(VanillaTypes.FLUID);

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiItemStacks.init(0, true, 45, 14);
        guiFluidStacks.init(0, false, 106, 1, 16, 60, TEProps.MAX_FLUID_LARGE, false, tankOverlay);

        guiItemStacks.set(0, inputs.get(0));
        guiFluidStacks.set(0, outputs.get(0));
    }

}
