package ruiseki.jfi.jfmuy.immersiveengineering.cokeoven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import blusunrize.immersiveengineering.api.crafting.CokeOvenRecipe;
import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.IEContent;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class CokeOvenRecipeWrapper implements IRecipeWrapper {

    private final CokeOvenRecipe recipe;
    private final List<List<ItemStack>> inputs;

    public CokeOvenRecipeWrapper(CokeOvenRecipe recipe) {
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

        if (recipe.creosoteOutput > 0) {
            ingredients.setOutput(VanillaTypes.FLUID, new FluidStack(IEContent.fluidCreosote, recipe.creosoteOutput));
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        float seconds = recipe.time / 20.0F;

        String timeString = seconds + "s";

        ClientUtils.font()
            .drawString(
                timeString,
                70 - ClientUtils.font()
                    .getStringWidth(timeString) / 2,
                53,
                11184810,
                true);
    }
}
