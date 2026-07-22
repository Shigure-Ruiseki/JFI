package ruiseki.jfi.jfmuy.excompressum.hammer;

import java.util.List;

import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class CompressedHammerRecipeWrapper implements IRecipeWrapper {

    private final ItemStack input;
    private final List<ItemStack> outputs;

    public CompressedHammerRecipeWrapper(ItemStack input, List<ItemStack> outputs) {
        this.input = input;
        this.outputs = outputs;
    }

    public ItemStack getInput() {
        return input;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, input);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }
}
