package ruiseki.jfi.jfmuy.mekanism.machine;

import mekanism.common.recipe.inputs.ItemStackInput;
import mekanism.common.recipe.machines.MachineRecipe;
import mekanism.common.recipe.outputs.ItemStackOutput;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class MachineRecipeWrapper<RECIPE extends MachineRecipe<?, ?, RECIPE>> extends MekanismRecipeWrapper<RECIPE> {

    public MachineRecipeWrapper(RECIPE recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        Object inputObj = recipe.getInput();
        if (inputObj instanceof ItemStackInput input) {
            if (input.ingredient != null) {
                ingredients.setInput(VanillaTypes.ITEM, input.ingredient);
            }
        }

        Object outputObj = recipe.getOutput();
        if (outputObj instanceof ItemStackOutput output) {
            if (output.output != null) {
                ingredients.setOutput(VanillaTypes.ITEM, output.output);
            }
        }
    }
}
