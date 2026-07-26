package ruiseki.jfi.jfmuy.harvestcraft.presser;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class PresserWrapper implements IRecipeWrapper {

    private final ItemStack input;
    private final ItemStack outputPrimary;
    private final ItemStack outputSecondary;

    public PresserWrapper(ItemStack input, ItemStack outputPrimary, ItemStack outputSecondary) {
        this.input = input;
        this.outputPrimary = outputPrimary;
        this.outputSecondary = outputSecondary;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (this.input != null) {
            ingredients.setInput(VanillaTypes.ITEM, this.input);
        }

        List<ItemStack> outputs = new ArrayList<>();
        if (this.outputPrimary != null) {
            outputs.add(this.outputPrimary);
        }
        if (this.outputSecondary != null) {
            outputs.add(this.outputSecondary);
        }

        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }
}
