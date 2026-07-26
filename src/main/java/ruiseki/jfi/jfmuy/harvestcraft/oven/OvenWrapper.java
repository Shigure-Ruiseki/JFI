package ruiseki.jfi.jfmuy.harvestcraft.oven;

import net.minecraft.item.ItemStack;

import com.pam.harvestcraft.ItemRegistry;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class OvenWrapper implements IRecipeWrapper {

    private final ItemStack input;
    private final ItemStack output;

    public OvenWrapper(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, input);

        if (ItemRegistry.oliveoilItem != null) {
            ingredients.setInput(VanillaTypes.ITEM, new ItemStack(ItemRegistry.oliveoilItem));
        }

        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
