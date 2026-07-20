package ruiseki.jfi.jfmuy.thermalexpansion.machine.charger;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalexpansion.util.crafting.ChargerManager;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.machine.BaseRecipeWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class ChargerRecipeWrapper extends BaseRecipeWrapper {

    /* Recipe */
    protected List<ItemStack> inputs;
    protected List<ItemStack> outputs;

    /* Animation */
    protected IDrawableAnimated progress;

    public ChargerRecipeWrapper(IGuiHelper guiHelper, ChargerManager.RecipeCharger recipe) {

        List<ItemStack> recipeInputs = new ArrayList<>();
        recipeInputs.add(recipe.getInput());

        List<ItemStack> recipeOutputs = new ArrayList<>();
        recipeOutputs.add(recipe.getOutput());

        inputs = recipeInputs;
        outputs = recipeOutputs;

        energy = recipe.getEnergy();

        IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_FLUX);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();

        int basePower = ThermalExpansion.config.get("Machine.Charger", "BasePower", 40);

        progress = guiHelper.createAnimatedDrawable(
            progressDrawable,
            Math.max(10, energy / basePower),
            IDrawableAnimated.StartDirection.BOTTOM,
            false);
        energyMeter = guiHelper
            .createAnimatedDrawable(energyDrawable, 1000, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        ingredients.setInputs(VanillaTypes.ITEM, inputs);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

        progress.draw(minecraft, 34, 43);
        energyMeter.draw(minecraft, 2, 8);
    }

}
