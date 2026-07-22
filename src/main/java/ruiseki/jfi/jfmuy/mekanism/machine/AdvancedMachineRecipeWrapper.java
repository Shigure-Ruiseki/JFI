package ruiseki.jfi.jfmuy.mekanism.machine;

import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasStack;
import mekanism.common.recipe.machines.AdvancedMachineRecipe;
import mekanism.common.recipe.machines.MachineRecipe;
import mekanism.common.tile.TileEntityAdvancedElectricMachine;
import ruiseki.jfi.jfmuy.mekanism.MekanismPlugin;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class AdvancedMachineRecipeWrapper<RECIPE extends AdvancedMachineRecipe<?>>
    extends MekanismRecipeWrapper<RECIPE> {

    private static final int BASE_GAS_PER_TICK = 1;

    public AdvancedMachineRecipeWrapper(RECIPE recipe) {
        super(recipe);
    }

    public AdvancedMachineRecipeWrapper(MachineRecipe machineRecipe) {
        super((RECIPE) machineRecipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, recipe.getInput().itemStack);
        ingredients.setInput(
            MekanismPlugin.TYPE_GAS,
            new GasStack(recipe.getInput().gasType, TileEntityAdvancedElectricMachine.MAX_GAS * BASE_GAS_PER_TICK));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput().output);
    }

    public List<ItemStack> getFuelStacks(Gas gasType) {
        return Collections.emptyList();
    }
}
