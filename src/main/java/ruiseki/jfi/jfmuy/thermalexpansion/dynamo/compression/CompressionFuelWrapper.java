package ruiseki.jfi.jfmuy.thermalexpansion.dynamo.compression;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.dynamo.BaseFuelWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class CompressionFuelWrapper extends BaseFuelWrapper {

    protected List<FluidStack> inputs;

    public CompressionFuelWrapper(IGuiHelper guiHelper, FluidStack fuel, int energy) {
        List<FluidStack> recipeInputs = new ArrayList<>();
        if (fuel != null) {
            recipeInputs.add(fuel);
        }

        this.inputs = recipeInputs;
        this.energy = energy;

        IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_FLAME);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();

        int basePower = 40;

        durationFill = guiHelper.createAnimatedDrawable(
            progressDrawable,
            Math.max(10, energy / basePower),
            IDrawableAnimated.StartDirection.TOP,
            true);
        energyMeter = guiHelper
            .createAnimatedDrawable(energyDrawable, 1000, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.FLUID, inputs);
    }
}
