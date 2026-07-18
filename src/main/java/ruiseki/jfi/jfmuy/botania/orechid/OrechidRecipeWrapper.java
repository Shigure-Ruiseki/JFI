package ruiseki.jfi.jfmuy.botania.orechid;

import static ruiseki.jfi.jfmuy.botania.BotaniaPlugin.doesOreExist;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import vazkii.botania.api.BotaniaAPI;

public class OrechidRecipeWrapper implements IRecipeWrapper, Comparable<OrechidRecipeWrapper> {

    private final int weight;
    private final List<List<ItemStack>> outputStacks;

    protected ItemStack getInputStack() {
        return new ItemStack(Blocks.stone, 64);
    }

    public OrechidRecipeWrapper(Map.Entry<String, Integer> entry) {
        this.weight = entry.getValue();

        final int amount = Math.max(1, Math.round((float) weight * 64 / getTotalOreWeight()));

        // Shouldn't ever return an empty list since the ore weight
        // list is filtered to only have ores with ItemBlocks
        List<ItemStack> stackList = OreDictionary.getOres(entry.getKey());

        stackList.forEach(s -> s.stackSize = amount);

        outputStacks = Collections.singletonList(stackList);
    }

    public Map<String, Integer> getOreWeights() {
        return BotaniaAPI.oreWeights;
    }

    private float getTotalOreWeight() {
        return (getOreWeights().entrySet()
            .stream()
            .filter(e -> doesOreExist(e.getKey()))
            .map(Map.Entry::getValue)
            .reduce(Integer::sum)).orElse(weight * 64 * 64);
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, getInputStack());
        ingredients.setOutputLists(VanillaTypes.ITEM, outputStacks);
    }

    @Override
    public int compareTo(@Nonnull OrechidRecipeWrapper o) {
        return Integer.compare(o.weight, weight);
    }
}
