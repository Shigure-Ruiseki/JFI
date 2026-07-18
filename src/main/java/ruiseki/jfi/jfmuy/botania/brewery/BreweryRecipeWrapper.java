package ruiseki.jfi.jfmuy.botania.brewery;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.ImmutableList;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import vazkii.botania.api.recipe.RecipeBrew;
import vazkii.botania.common.item.ModItems;

public class BreweryRecipeWrapper implements IRecipeWrapper {

    private final List<List<ItemStack>> input;
    private final List<ItemStack> output;

    private static final List<ItemStack> inputs = Arrays.asList(
        new ItemStack(ModItems.vial),
        new ItemStack(ModItems.vial, 1, 1),
        new ItemStack(ModItems.incenseStick),
        new ItemStack(ModItems.bloodPendant));

    public BreweryRecipeWrapper(RecipeBrew recipeBrew) {
        ImmutableList.Builder<List<ItemStack>> inputBuilder = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> outputBuilder = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> containers = ImmutableList.builder();

        for (ItemStack stack : inputs) {
            ItemStack brewed = recipeBrew.getOutput(stack);
            if (brewed != null) {
                containers.add(stack);
                outputBuilder.add(brewed);
            }
        }
        inputBuilder.add(containers.build());

        for (Object o : recipeBrew.getInputs()) {
            if (o instanceof ItemStack) {
                inputBuilder.add(ImmutableList.of((ItemStack) o));
            }
            if (o instanceof String) {
                inputBuilder.add(OreDictionary.getOres((String) o));
            }
        }

        input = inputBuilder.build();
        output = outputBuilder.build();
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, input);
        ingredients.setOutputLists(VanillaTypes.ITEM, ImmutableList.of(output));
    }
}
