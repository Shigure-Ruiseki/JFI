package ruiseki.jfi.jfmuy.thermalexpansion.machine.insolator;

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
import cofh.thermalexpansion.gui.client.machine.GuiInsolator;
import cofh.thermalexpansion.util.crafting.InsolatorManager;
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

public class InsolatorRecipeCategory extends BaseRecipeCategory<InsolatorRecipeWrapper> {

    public static void register(IRecipeCategoryRegistration registry) {
        IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(new InsolatorRecipeCategory(guiHelper));
    }

    public static void initialize(IModRegistry registry) {
        try {
            IJFMUYHelpers jeiHelpers = registry.getJFMUYHelpers();
            IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

            registry.addRecipes(getRecipes(guiHelper), RecipeUidsTE.INSOLATOR);
            registry.addRecipeClickArea(GuiInsolator.class, 79, 34, 24, 16, RecipeUidsTE.INSOLATOR);
            registry.addRecipeCatalyst(BlockMachine.insolator, RecipeUidsTE.INSOLATOR);
        } catch (Throwable t) {
            JFI.okLog(Level.ERROR, "Bad/null recipe!", t);
        }
    }

    public static List<InsolatorRecipeWrapper> getRecipes(IGuiHelper guiHelper) {

        List<InsolatorRecipeWrapper> recipes = new ArrayList<>();

        for (InsolatorManager.RecipeInsolator recipe : InsolatorManager.getRecipeList()) {
            recipes.add(new InsolatorRecipeWrapper(guiHelper, recipe));
        }
        return recipes;
    }

    protected IDrawableStatic progress;
    protected IDrawableStatic speed;
    protected IDrawableStatic tank;
    protected IDrawableStatic tankOverlay;

    public InsolatorRecipeCategory(IGuiHelper guiHelper) {

        background = guiHelper.drawableBuilder(GuiInsolator.TEXTURE, 26, 11, 144, 62)
            .addPadding(0, 0, 16, 0)
            .build();
        energyMeter = Drawables.getDrawables(guiHelper)
            .getEnergyEmpty();
        localizedName = StringHelper.localize("tile.thermalexpansion.machine.insolator.name");

        progress = Drawables.getDrawables(guiHelper)
            .getProgress(Drawables.PROGRESS_ARROW);
        speed = Drawables.getDrawables(guiHelper)
            .getScale(Drawables.SCALE_SUN);
        tank = Drawables.getDrawables(guiHelper)
            .getTank(Drawables.TANK);
        tankOverlay = Drawables.getDrawables(guiHelper)
            .getTankSmallOverlay(Drawables.TANK);
    }

    @Nonnull
    @Override
    public String getUid() {

        return RecipeUidsTE.INSOLATOR;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

        progress.draw(minecraft, 69, 23);
        speed.draw(minecraft, 34, 33);
        tank.draw(minecraft, 141, 0);
        energyMeter.draw(minecraft, 2, 8);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, InsolatorRecipeWrapper recipeWrapper, IIngredients ingredients) {

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<FluidStack>> inputFluids = ingredients.getInputs(VanillaTypes.FLUID);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        guiItemStacks.init(0, true, 21, 14);
        guiItemStacks.init(1, true, 45, 14);
        guiItemStacks.init(2, false, 105, 14);

        guiFluidStacks.init(0, true, 142, 1, 16, 60, TEProps.MAX_FLUID_LARGE, false, tankOverlay);

        guiItemStacks.set(0, inputs.get(0));
        guiItemStacks.set(1, inputs.get(1));
        guiItemStacks.set(2, outputs.get(0));

        guiFluidStacks.set(0, inputFluids.get(0));

        if (outputs.size() > 1) {
            guiItemStacks.init(3, false, 105, 41);
            guiItemStacks.set(3, outputs.get(1));

            guiItemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {

                if (slotIndex == 3) {
                    tooltip.add(StringHelper.localize("info.cofh.chance") + ": " + recipeWrapper.chance + "%");
                }
            });
        }
        guiFluidStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {

            tooltip.add(StringHelper.LIGHT_BLUE + StringHelper.localize("info.cofh.input"));
        });
    }

}
