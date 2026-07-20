package ruiseki.jfi.jfmuy.thermalexpansion.dynamo.enervation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.dynamo.BaseFuelWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class EnervationFuelWrapper extends BaseFuelWrapper {

    protected List<ItemStack> inputs;
    protected int maxEnergy;

    public EnervationFuelWrapper(IGuiHelper guiHelper, ItemStack fuel, int energy) {
        this(guiHelper, fuel, energy, 0);
    }

    public EnervationFuelWrapper(IGuiHelper guiHelper, ItemStack fuel, int energy, int maxEnergy) {
        List<ItemStack> recipeInputs = new ArrayList<>();
        if (fuel != null) {
            recipeInputs.add(fuel);
        }

        this.inputs = recipeInputs;
        this.energy = energy;
        this.maxEnergy = maxEnergy;

        IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_FLUX);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();

        int basePower = 80;

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
        ingredients.setInputs(VanillaTypes.ITEM, inputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
        minecraft.fontRenderer.drawString(this.energy + " RF", 96, 10 + (recipeHeight - 9) / 2, 0xD00000);
    }
}
