package ruiseki.jfi.jfmuy.ic2.machine.solidcanner;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.StackUtil;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SolidCannerRecipeWrapper implements IRecipeWrapper {

    private final IRecipeInput container;
    private final IRecipeInput fill;
    private final RecipeOutput output;

    public SolidCannerRecipeWrapper(IRecipeInput container, IRecipeInput fill, RecipeOutput output) {
        this.container = container;
        this.fill = fill;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputs = new ArrayList<>();

        // Container items slot
        List<ItemStack> containerStacks = new ArrayList<>();
        for (ItemStack stack : container.getInputs()) {
            containerStacks.add(StackUtil.copyWithSize(stack, container.getAmount()));
        }
        inputs.add(containerStacks);

        // Fill items slot
        List<ItemStack> fillStacks = new ArrayList<>();
        for (ItemStack stack : fill.getInputs()) {
            fillStacks.add(StackUtil.copyWithSize(stack, fill.getAmount()));
        }
        inputs.add(fillStacks);

        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutputs(VanillaTypes.ITEM, output.items);
    }
}
