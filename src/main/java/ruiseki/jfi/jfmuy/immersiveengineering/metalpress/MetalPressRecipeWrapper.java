package ruiseki.jfi.jfmuy.immersiveengineering.metalpress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class MetalPressRecipeWrapper implements IRecipeWrapper {

    private final MetalPressRecipe recipe;
    private final List<List<ItemStack>> inputs;
    private final ItemStack output;

    public MetalPressRecipeWrapper(MetalPressRecipe recipe) {
        this.recipe = recipe;
        this.inputs = new ArrayList<>();

        Object in = recipe.input;
        List<ItemStack> inputList = new ArrayList<>();

        if (in instanceof String) {
            List<ItemStack> ores = OreDictionary.getOres((String) in);
            for (ItemStack s : ores) {
                if (s != null) {
                    ItemStack copy = s.copy();
                    copy.stackSize = recipe.inputSize;
                    inputList.add(copy);
                }
            }
        } else if (in instanceof ItemStack) {
            ItemStack copy = ((ItemStack) in).copy();
            copy.stackSize = recipe.inputSize;
            inputList.add(copy);
        } else if (in instanceof List) {
            for (Object obj : (List<?>) in) {
                if (obj instanceof ItemStack) {
                    ItemStack copy = ((ItemStack) obj).copy();
                    copy.stackSize = recipe.inputSize;
                    inputList.add(copy);
                }
            }
        }

        this.inputs.add(inputList);

        List<ItemStack> moldList = new ArrayList<>();
        if (recipe.mold != null) {
            if (recipe.mold.oreID != -1) {
                String oreName = OreDictionary.getOreName(recipe.mold.oreID);
                moldList.addAll(OreDictionary.getOres(oreName));
            } else if (recipe.mold.stack != null) {
                moldList.add(recipe.mold.stack);
            }
        }

        this.inputs.add(moldList);

        this.output = recipe.output != null ? recipe.output.copy() : null;
    }

    public MetalPressRecipe getRecipe() {
        return this.recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        if (output != null) {
            ingredients.setOutput(VanillaTypes.ITEM, output);
        }
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 68 && mouseX <= 82 && mouseY >= 10 && mouseY <= 26) {
            List<String> tooltip = new ArrayList<>();
            tooltip.add(EnumChatFormatting.GRAY + "Energy: " + recipe.energy + " RF");
            return tooltip;
        }
        return Collections.emptyList();
    }
}
