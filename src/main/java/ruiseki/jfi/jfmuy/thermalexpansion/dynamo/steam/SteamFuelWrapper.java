package ruiseki.jfi.jfmuy.thermalexpansion.dynamo.steam;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.dynamo.BaseFuelWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class SteamFuelWrapper extends BaseFuelWrapper {

    protected List<ItemStack> inputs;

    public SteamFuelWrapper(IGuiHelper guiHelper, ItemStack fuel, int energy) {
        List<ItemStack> recipeInputs = new ArrayList<>();
        recipeInputs.add(fuel);

        this.inputs = recipeInputs;
        this.energy = energy;

        IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_FLAME);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();
        int processingTicks = Math.max(10, energy / 40);

        durationFill = guiHelper
            .createAnimatedDrawable(progressDrawable, processingTicks, IDrawableAnimated.StartDirection.TOP, true);
        energyMeter = guiHelper
            .createAnimatedDrawable(energyDrawable, 1000, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, inputs);
    }
}
