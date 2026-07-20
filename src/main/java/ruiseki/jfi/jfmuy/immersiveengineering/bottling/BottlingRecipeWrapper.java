package ruiseki.jfi.jfmuy.immersiveengineering.bottling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import blusunrize.immersiveengineering.api.crafting.BottlingMachineRecipe;
import blusunrize.immersiveengineering.client.ClientUtils;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class BottlingRecipeWrapper implements IRecipeWrapper {

    private final BottlingMachineRecipe recipe;
    private final List<List<ItemStack>> inputs;

    public BottlingRecipeWrapper(BottlingMachineRecipe recipe) {
        this.recipe = recipe;
        this.inputs = new ArrayList<>();

        if (recipe.input instanceof String) {
            this.inputs.add(OreDictionary.getOres((String) recipe.input));
        } else if (recipe.input instanceof ItemStack) {
            this.inputs.add(Collections.singletonList((ItemStack) recipe.input));
        } else if (recipe.input instanceof List) {
            this.inputs.add((List<ItemStack>) recipe.input);
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.output);

        if (recipe.fluidInput != null) {
            ingredients.setInput(VanillaTypes.FLUID, recipe.fluidInput);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int processTicks = 30;
        float seconds = processTicks / 20.0F;

        String timeString = seconds + "s / " + processTicks + " Ticks";

        ClientUtils.font()
            .drawString(
                timeString,
                88 - ClientUtils.font()
                    .getStringWidth(timeString) / 2,
                55,
                11184810,
                true);
    }

    public BottlingMachineRecipe getRecipe() {
        return this.recipe;
    }
}
