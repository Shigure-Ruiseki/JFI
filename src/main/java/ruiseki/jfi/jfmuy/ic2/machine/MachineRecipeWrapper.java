package ruiseki.jfi.jfmuy.ic2.machine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.StackUtil;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class MachineRecipeWrapper implements IRecipeWrapper {

    private final List<List<ItemStack>> inputs;
    private final List<ItemStack> outputs;

    public MachineRecipeWrapper(IRecipeInput input, RecipeOutput output) {
        if (input == null) {
            throw new NullPointerException("Input must not be null.");
        }
        if (output == null || output.items.isEmpty()) {
            throw new IllegalArgumentException("Output must not be null or empty.");
        }

        List<ItemStack> inputList = new ArrayList<>();
        if (input.getInputs() != null) {
            for (ItemStack item : input.getInputs()) {
                if (item != null) {
                    inputList.add(StackUtil.copyWithSize(item, input.getAmount()));
                }
            }
        }

        this.inputs = new ArrayList<>();
        this.inputs.add(inputList);
        this.outputs = new ArrayList<>(output.items);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    public List<List<ItemStack>> getInputs() {
        return inputs;
    }

    public List<ItemStack> getOutputs() {
        return outputs;
    }
}
