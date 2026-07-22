package ruiseki.jfi.jfmuy.excompressum.composting;

import java.util.List;

import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class CompostingRecipeWrapper implements IRecipeWrapper {

    private final List<ItemStack> inputs;
    private final List<Float> values;
    private final ItemStack output;

    public CompostingRecipeWrapper(List<ItemStack> inputs, List<Float> values, ItemStack output) {
        this.inputs = inputs;
        this.values = values;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    public Float getValueForIndex(int index) {
        if (index >= 0 && index < values.size()) {
            return values.get(index);
        }
        return null;
    }
}
