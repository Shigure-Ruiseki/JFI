package ruiseki.jfi.jfmuy.thermalexpansion.machine.insolator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import cofh.lib.util.helpers.FluidHelper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalexpansion.util.crafting.InsolatorManager;
import cofh.thermalexpansion.util.crafting.InsolatorManager.RecipeInsolator;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.ThermalExpansionPlugin;
import ruiseki.jfi.jfmuy.thermalexpansion.machine.BaseRecipeWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class InsolatorRecipeWrapper extends BaseRecipeWrapper {

    /* Recipe */
    protected List<List<ItemStack>> inputs;
    protected List<FluidStack> inputFluids;
    protected List<ItemStack> outputs;

    protected int chance;

    /* Animation */
    protected IDrawableAnimated fluid;
    protected IDrawableAnimated progress;
    protected IDrawableAnimated speed;

    public InsolatorRecipeWrapper(IGuiHelper guiHelper, RecipeInsolator recipe) {

        this(guiHelper, recipe, RecipeUidsTE.INSOLATOR);
    }

    public InsolatorRecipeWrapper(IGuiHelper guiHelper, RecipeInsolator recipe, String uIdIn) {

        uId = uIdIn;

        List<List<ItemStack>> recipeInputs = new ArrayList<>();
        List<FluidStack> recipeInputFluids = new ArrayList<>();
        List<ItemStack> recipeInputsPrimary = new ArrayList<>();
        List<ItemStack> recipeInputsSecondary = new ArrayList<>();

        int oreID = new InsolatorManager.ComparableItemStackInsolator(recipe.getPrimaryInput()).oreID;
        if (oreID != -1) {
            for (ItemStack ore : OreDictionary.getOres(ItemHelper.oreProxy.getOreName(oreID), false)) {
                recipeInputsPrimary.add(ItemHelper.cloneStack(ore, recipe.getPrimaryInput().stackSize));
            }
        } else {
            recipeInputsPrimary.add(recipe.getPrimaryInput());
        }
        oreID = new InsolatorManager.ComparableItemStackInsolator(recipe.getSecondaryInput()).oreID;
        if (oreID != -1) {
            for (ItemStack ore : OreDictionary.getOres(ItemHelper.oreProxy.getOreName(oreID), false)) {
                recipeInputsSecondary.add(ItemHelper.cloneStack(ore, recipe.getSecondaryInput().stackSize));
            }
        } else {
            recipeInputsSecondary.add(recipe.getSecondaryInput());
        }
        recipeInputs.add(recipeInputsSecondary);
        recipeInputs.add(recipeInputsPrimary);
        recipeInputFluids.add(FluidHelper.WATER);

        List<ItemStack> recipeOutputs = new ArrayList<>();
        recipeOutputs.add(recipe.getPrimaryOutput());

        if (recipe.getSecondaryOutput() != null) {
            recipeOutputs.add(recipe.getSecondaryOutput());
        }
        inputs = recipeInputs;
        inputFluids = recipeInputFluids;
        outputs = recipeOutputs;

        energy = recipe.getEnergy();
        chance = recipe.getSecondaryOutputChance();

        IDrawableStatic fluidDrawable = Drawables.getDrawables(guiHelper)
            .getProgress(Drawables.PROGRESS_ARROW_FLUID);
        IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
            .getProgressFill(Drawables.PROGRESS_ARROW_FLUID);
        IDrawableStatic speedDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_SUN);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();

        int basePower = ThermalExpansion.config.get("Machine.Pulverizer", "BasePower", 40);

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
        speed = guiHelper.createAnimatedDrawable(speedDrawable, 1000, IDrawableAnimated.StartDirection.TOP, true);
        energyMeter = guiHelper
            .createAnimatedDrawable(energyDrawable, 1000, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setInputs(VanillaTypes.FLUID, inputFluids);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

        ThermalExpansionPlugin.drawFluid(69, 23, inputFluids.get(0), 24, 16);

        fluid.draw(minecraft, 69, 23);
        progress.draw(minecraft, 69, 23);
        speed.draw(minecraft, 34, 33);
        energyMeter.draw(minecraft, 2, 8);

        if (chance > 0) {
            String dispChance = chance + "%";
            minecraft.fontRenderer.drawString(dispChance, 102 - 6 * dispChance.length(), 48, 0x808080);
        }
    }

}
