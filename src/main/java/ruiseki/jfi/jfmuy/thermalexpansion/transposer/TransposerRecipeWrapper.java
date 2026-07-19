package ruiseki.jfi.jfmuy.thermalexpansion.transposer;

import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalexpansion.util.crafting.TransposerManager;
import ruiseki.jfi.jfmuy.thermalexpansion.BaseRecipeWrapper;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.ThermalExpansionPlugin;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.gui.IGuiIngredient;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class TransposerRecipeWrapper extends BaseRecipeWrapper {

    /* Recipe */
    protected List<List<ItemStack>> inputs;
    protected List<List<FluidStack>> inputFluids;
    protected List<List<ItemStack>> outputs;
    protected List<List<FluidStack>> outputFluids;

    protected int chance;

    /* Animation */
    protected IDrawableStatic progressBack;
    protected IDrawableAnimated fluid;
    protected IDrawableAnimated progress;
    protected IDrawableAnimated speed;

    protected Map<Integer, ? extends IGuiIngredient<FluidStack>> guiFluids;

    public TransposerRecipeWrapper() {

    }

    public TransposerRecipeWrapper(IGuiHelper guiHelper, TransposerManager.RecipeTransposer recipe, String uIdIn) {

        uId = uIdIn;

        List<ItemStack> recipeInputs = new ArrayList<>();

        int oreID = new TransposerManager.ComparableItemStackTransposer(recipe.getInput()).oreID;
        if (oreID != -1) {
            for (ItemStack ore : OreDictionary.getOres(ItemHelper.oreProxy.getOreName(oreID), false)) {
                recipeInputs.add(ItemHelper.cloneStack(ore, recipe.getInput().stackSize));
            }
        } else {
            recipeInputs.add(recipe.getInput());
        }
        List<ItemStack> recipeOutputs = new ArrayList<>();
        recipeOutputs.add(recipe.getOutput());

        List<FluidStack> recipeFluids = new ArrayList<>();
        recipeFluids.add(recipe.getFluid());

        inputs = singletonList(recipeInputs);
        outputs = singletonList(recipeOutputs);

        if (uId.equals(RecipeUidsTE.TRANSPOSER_FILL)) {
            inputFluids = singletonList(recipeFluids);
            outputFluids = Collections.emptyList();
        } else {
            inputFluids = Collections.emptyList();
            outputFluids = singletonList(recipeFluids);
        }
        energy = recipe.getEnergy();
        chance = recipe.getChance();

        int basePower = ThermalExpansion.config.get("Machine.Transposer", "BasePower", 40);

        if (uId.equals(RecipeUidsTE.TRANSPOSER_FILL)) {
            progressBack = Drawables.getDrawables(guiHelper)
                .getProgressLeft(Drawables.PROGRESS_DROP);

            IDrawableStatic fluidDrawable = Drawables.getDrawables(guiHelper)
                .getProgressLeft(Drawables.PROGRESS_DROP);
            IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
                .getProgressLeftFill(Drawables.PROGRESS_DROP);

            fluid = guiHelper.createAnimatedDrawable(
                fluidDrawable,
                energy / basePower,
                IDrawableAnimated.StartDirection.RIGHT,
                true);
            progress = guiHelper.createAnimatedDrawable(
                progressDrawable,
                energy / basePower,
                IDrawableAnimated.StartDirection.RIGHT,
                false);
        } else {
            progressBack = Drawables.getDrawables(guiHelper)
                .getProgress(Drawables.PROGRESS_DROP);

            IDrawableStatic fluidDrawable = Drawables.getDrawables(guiHelper)
                .getProgress(Drawables.PROGRESS_DROP);
            IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
                .getProgressFill(Drawables.PROGRESS_DROP);

            fluid = guiHelper.createAnimatedDrawable(
                fluidDrawable,
                Math.max(10, energy / basePower),
                IDrawableAnimated.StartDirection.LEFT,
                true);
            progress = guiHelper.createAnimatedDrawable(
                progressDrawable,
                Math.max(10, energy / basePower),
                IDrawableAnimated.StartDirection.LEFT,
                false);
        }
        IDrawableStatic speedDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_BUBBLE);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();

        speed = guiHelper.createAnimatedDrawable(speedDrawable, 1000, IDrawableAnimated.StartDirection.TOP, true);
        energyMeter = guiHelper
            .createAnimatedDrawable(energyDrawable, 1000, IDrawableAnimated.StartDirection.TOP, true);
    }

    public void setGuiFluids(Map<Integer, ? extends IGuiIngredient<FluidStack>> fluids) {

        guiFluids = fluids;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutputLists(VanillaTypes.ITEM, outputs);

        if (uId.equals(RecipeUidsTE.TRANSPOSER_FILL)) {
            ingredients.setInputLists(VanillaTypes.FLUID, inputFluids);
        } else {
            ingredients.setOutputLists(VanillaTypes.FLUID, outputFluids);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

        if (guiFluids == null) {
            return;
        }
        progressBack.draw(minecraft, 63, 11);
        ThermalExpansionPlugin.drawFluid(
            63,
            11,
            guiFluids.get(0)
                .getDisplayedIngredient(),
            24,
            16);
        fluid.draw(minecraft, 63, 11);
        progress.draw(minecraft, 63, 11);
        speed.draw(minecraft, 67, 41);
        energyMeter.draw(minecraft, 2, 8);
    }

}
