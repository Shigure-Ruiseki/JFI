package ruiseki.jfi.jfmuy.botania.crafting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableList;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.ICraftingRecipeWrapper;
import ruiseki.okcore.enums.EnumDye;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

public class SpecialFloatingFlowerWrapper implements ICraftingRecipeWrapper {

    private final List<List<ItemStack>> inputs = new ArrayList<>();
    private final ItemStack output;

    public SpecialFloatingFlowerWrapper(String type) {
        ItemStack specialFlower = ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.specialFlower), type);
        inputs.add(ImmutableList.of(specialFlower));

        List<ItemStack> normalFloaters = new ArrayList<>();
        for (EnumDye color : EnumDye.values()) {
            normalFloaters.add(new ItemStack(ModBlocks.floatingFlower, 1, color.getColor()));
        }
        inputs.add(normalFloaters);

        output = ItemBlockSpecialFlower.ofType(new ItemStack(ModBlocks.floatingSpecialFlower), type);
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
