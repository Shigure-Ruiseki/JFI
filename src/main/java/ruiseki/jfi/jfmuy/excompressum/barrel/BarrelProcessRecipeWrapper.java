package ruiseki.jfi.jfmuy.excompressum.barrel;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class BarrelProcessRecipeWrapper implements IRecipeWrapper {

    private final ItemStack fluidItem;
    private final ItemStack inputItem;
    private final ItemStack outputItem;

    public BarrelProcessRecipeWrapper(ItemStack fluidItem, ItemStack inputItem, ItemStack outputItem) {
        this.fluidItem = fluidItem;
        this.inputItem = inputItem;
        this.outputItem = outputItem;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> inputs = new ArrayList<>();
        inputs.add(fluidItem);
        inputs.add(inputItem);

        ingredients.setInputs(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, outputItem);
    }
}
