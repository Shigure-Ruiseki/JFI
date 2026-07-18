package ruiseki.jfi.jfmuy.botania.crafting;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableList;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.ICraftingRecipeWrapper;
import vazkii.botania.common.crafting.recipe.TerraPickTippingRecipe;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.terrasteel.ItemTerraPick;

public class TerraPickTippingRecipeWrapper implements ICraftingRecipeWrapper {

    private final List<List<ItemStack>> inputs;
    private final ItemStack output;

    public TerraPickTippingRecipeWrapper(TerraPickTippingRecipe recipe) {
        inputs = ImmutableList.of(
            ImmutableList.of(new ItemStack(ModItems.terraPick)),
            ImmutableList.of(new ItemStack(ModItems.elementiumPick)));
        output = new ItemStack(ModItems.terraPick);
        ItemTerraPick.setTipped(output);
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
