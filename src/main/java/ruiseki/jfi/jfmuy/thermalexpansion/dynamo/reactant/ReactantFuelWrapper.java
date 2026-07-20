package ruiseki.jfi.jfmuy.thermalexpansion.dynamo.reactant;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.dynamo.BaseFuelWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class ReactantFuelWrapper extends BaseFuelWrapper {

    protected List<ItemStack> inputs;
    protected List<FluidStack> inputFluids;

    public ReactantFuelWrapper(IGuiHelper guiHelper, ItemStack reactant, FluidStack fluid, int energy) {
        this(guiHelper, reactant, fluid, energy, RecipeUidsTE.DYNAMO_REACTANT);
    }

    public ReactantFuelWrapper(IGuiHelper guiHelper, ItemStack reactant, FluidStack fluid, int energy, String uIdIn) {
        uId = uIdIn;

        List<ItemStack> recipeInputs = new ArrayList<>();
        if (reactant != null) {
            recipeInputs.add(reactant);
        }

        List<FluidStack> recipeFluids = new ArrayList<>();
        if (fluid != null) {
            recipeFluids.add(fluid);
        }

        this.inputs = recipeInputs;
        this.inputFluids = recipeFluids;
        this.energy = energy;

        IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_FLAME_GREEN);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();

        int durationTicks = Math.max(10, energy / 40);

        durationFill = guiHelper
            .createAnimatedDrawable(progressDrawable, durationTicks, IDrawableAnimated.StartDirection.TOP, true);
        energyMeter = guiHelper
            .createAnimatedDrawable(energyDrawable, 1000, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, inputs);
        ingredients.setInputs(VanillaTypes.FLUID, inputFluids);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        durationFill.draw(minecraft, 22, 43);
        energyMeter.draw(minecraft, 71, 7);

        minecraft.fontRenderer.drawString(energy + " RF", 96, (recipeHeight - 9) / 2, 0x808080);
    }
}
