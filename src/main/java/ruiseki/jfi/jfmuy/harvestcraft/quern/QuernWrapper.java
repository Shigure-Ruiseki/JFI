package ruiseki.jfi.jfmuy.harvestcraft.quern;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class QuernWrapper implements IRecipeWrapper {

    private final ItemStack input;
    private final ItemStack output;

    public QuernWrapper(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, input);
        ingredients.setInput(VanillaTypes.ITEM, new ItemStack(Blocks.stone_pressure_plate));
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
