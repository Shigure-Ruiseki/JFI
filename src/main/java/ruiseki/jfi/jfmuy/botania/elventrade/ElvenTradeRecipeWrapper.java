package ruiseki.jfi.jfmuy.botania.elventrade;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.ImmutableList;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import vazkii.botania.api.recipe.RecipeElvenTrade;

public class ElvenTradeRecipeWrapper implements IRecipeWrapper {

    private final List<List<ItemStack>> input;
    private final List<ItemStack> outputs;

    public ElvenTradeRecipeWrapper(RecipeElvenTrade recipe) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        for (Object o : recipe.getInputs()) {
            if (o instanceof ItemStack) {
                builder.add(ImmutableList.of((ItemStack) o));
            }
            if (o instanceof String) {
                builder.add(OreDictionary.getOres((String) o));
            }
        }
        input = builder.build();
        outputs = ImmutableList.of(recipe.getOutput());
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, input);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }
}
