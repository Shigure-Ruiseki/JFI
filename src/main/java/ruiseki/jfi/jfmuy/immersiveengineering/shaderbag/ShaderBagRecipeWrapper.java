package ruiseki.jfi.jfmuy.immersiveengineering.shaderbag;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableList;

import blusunrize.immersiveengineering.api.shader.ShaderRegistry;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.ICraftingRecipeWrapper;

public class ShaderBagRecipeWrapper implements ICraftingRecipeWrapper {

    protected final List<ItemStack> inputList;
    protected final ItemStack outputStack;

    public ShaderBagRecipeWrapper(EnumRarity outputRarity, boolean inputBag) {
        ArrayList<EnumRarity> upperRarities = ShaderRegistry.getHigherRarities(outputRarity);
        this.inputList = new ArrayList<>();

        if (inputBag) {
            for (EnumRarity r : upperRarities) {
                ItemStack bag = new ItemStack(IEContent.itemShaderBag);
                ItemNBTHelper.setString(bag, "rarity", r.toString());
                inputList.add(bag);
            }
        } else {
            for (ShaderRegistry.ShaderRegistryEntry entry : ShaderRegistry.shaderRegistry.values()) {
                if (upperRarities.contains(entry.getRarity())) {
                    ItemStack shader = new ItemStack(IEContent.itemShader);
                    ItemNBTHelper.setString(shader, "shader_name", entry.getName());
                    inputList.add(shader);
                }
            }
        }

        ItemStack bag = new ItemStack(IEContent.itemShaderBag, inputBag ? 2 : 1);
        ItemNBTHelper.setString(bag, "rarity", outputRarity.toString());
        this.outputStack = bag;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, ImmutableList.of(inputList));
        ingredients.setOutputs(VanillaTypes.ITEM, ImmutableList.of(outputStack));
    }
}
