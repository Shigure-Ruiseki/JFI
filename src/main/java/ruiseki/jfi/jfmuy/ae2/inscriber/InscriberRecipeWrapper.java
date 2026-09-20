package ruiseki.jfi.jfmuy.ae2.inscriber;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import appeng.api.features.IInscriberRecipe;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class InscriberRecipeWrapper implements IRecipeWrapper {

    private final List<ItemStack> topInputs;
    private final List<ItemStack> centerInputs;
    private final List<ItemStack> bottomInputs;
    private final ItemStack output;

    public InscriberRecipeWrapper(IInscriberRecipe recipe) {
        this.topInputs = new ArrayList<>(
            recipe.getTopOptional()
                .asSet());
        this.centerInputs = recipe.getInputs();
        this.bottomInputs = new ArrayList<>(
            recipe.getBottomOptional()
                .asSet());
        this.output = recipe.getOutput();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputSlots = new ArrayList<>();

        inputSlots.add(topInputs);
        inputSlots.add(centerInputs);
        inputSlots.add(bottomInputs);

        ingredients.setInputLists(VanillaTypes.ITEM, inputSlots);

        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
