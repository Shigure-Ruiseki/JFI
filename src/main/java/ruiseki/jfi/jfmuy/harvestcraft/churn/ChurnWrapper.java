package ruiseki.jfi.jfmuy.harvestcraft.churn;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.pam.harvestcraft.ItemRegistry;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class ChurnWrapper implements IRecipeWrapper {

    private final ItemStack input;
    private final ItemStack saltFuel;
    private final ItemStack output;

    public ChurnWrapper(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
        this.saltFuel = ItemRegistry.saltItem != null ? new ItemStack(ItemRegistry.saltItem) : null;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> inputs = new ArrayList<>();
        if (this.input != null) {
            inputs.add(this.input);
        }
        if (this.saltFuel != null) {
            inputs.add(this.saltFuel);
        }

        ingredients.setInputs(VanillaTypes.ITEM, inputs);

        if (this.output != null) {
            ingredients.setOutput(VanillaTypes.ITEM, this.output);
        }
    }
}
