package ruiseki.jfi.jfmuy.thermalexpansion.machine.crusible;

import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalexpansion.util.crafting.CrucibleManager;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.ThermalExpansionPlugin;
import ruiseki.jfi.jfmuy.thermalexpansion.machine.BaseRecipeWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class CrucibleRecipeWrapper extends BaseRecipeWrapper {

    /* Recipe */
    protected List<List<ItemStack>> inputs;
    protected List<FluidStack> outputFluids;

    /* Animation */
    protected IDrawableAnimated fluid;
    protected IDrawableAnimated progress;
    protected IDrawableAnimated speed;

    public CrucibleRecipeWrapper(IGuiHelper guiHelper, CrucibleManager.RecipeCrucible recipe) {

        this(guiHelper, recipe, RecipeUidsTE.CRUCIBLE);
    }

    public CrucibleRecipeWrapper(IGuiHelper guiHelper, CrucibleManager.RecipeCrucible recipe, String uIdIn) {

        uId = uIdIn;

        List<ItemStack> recipeInputs = new ArrayList<>();
        ItemStack inputStack = recipe.getInput();
        int[] oreIds = OreDictionary.getOreIDs(inputStack);
        if (oreIds.length > 0) {
            String oreName = OreDictionary.getOreName(oreIds[0]);
            for (ItemStack ore : OreDictionary.getOres(oreName, false)) {
                recipeInputs.add(ItemHelper.cloneStack(ore, inputStack.stackSize));
            }
        } else {
            recipeInputs.add(inputStack);
        }
        List<FluidStack> recipeOutputFluids = new ArrayList<>();
        recipeOutputFluids.add(recipe.getOutput());

        inputs = singletonList(recipeInputs);
        outputFluids = recipeOutputFluids;

        energy = recipe.getEnergy();

        IDrawableStatic fluidDrawable = Drawables.getDrawables(guiHelper)
            .getProgress(Drawables.PROGRESS_DROP);
        IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
            .getProgressFill(Drawables.PROGRESS_DROP);
        IDrawableStatic speedDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_FLAME);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();

        int basePower = ThermalExpansion.config.get("Machine.Crucible", "BasePower", 40);

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
        ingredients.setOutputs(VanillaTypes.FLUID, outputFluids);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        ThermalExpansionPlugin.drawFluid(69, 23, outputFluids.get(0), 24, 16);
        fluid.draw(minecraft, 69, 23);
        progress.draw(minecraft, 69, 23);
        speed.draw(minecraft, 45, 33);
        energyMeter.draw(minecraft, 2, 8);
    }

}
