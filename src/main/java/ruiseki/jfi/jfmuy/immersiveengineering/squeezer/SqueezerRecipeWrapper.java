package ruiseki.jfi.jfmuy.immersiveengineering.squeezer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import blusunrize.immersiveengineering.api.energy.DieselHandler;
import blusunrize.immersiveengineering.client.ClientUtils;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SqueezerRecipeWrapper implements IRecipeWrapper {

    private final DieselHandler.SqueezerRecipe recipe;
    private final List<List<ItemStack>> itemInputs;
    private final ItemStack itemOutput;
    private final FluidStack fluidOutput;

    public SqueezerRecipeWrapper(DieselHandler.SqueezerRecipe recipe) {
        this.recipe = recipe;
        this.itemInputs = new ArrayList<>();

        List<ItemStack> inputList = new ArrayList<>();
        if (recipe.input instanceof String) {
            inputList.addAll(OreDictionary.getOres((String) recipe.input));
        } else if (recipe.input instanceof ItemStack) {
            inputList.add(((ItemStack) recipe.input).copy());
        } else if (recipe.input instanceof List) {
            for (Object obj : (List<?>) recipe.input) {
                if (obj instanceof ItemStack) {
                    inputList.add(((ItemStack) obj).copy());
                }
            }
        }
        this.itemInputs.add(inputList);

        this.itemOutput = recipe.output != null ? recipe.output.copy() : null;
        this.fluidOutput = recipe.fluid != null ? recipe.fluid.copy() : null;
    }

    public DieselHandler.SqueezerRecipe getRecipe() {
        return this.recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, itemInputs);

        if (itemOutput != null) {
            ingredients.setOutput(VanillaTypes.ITEM, itemOutput);
        }

        if (fluidOutput != null) {
            ingredients.setOutput(VanillaTypes.FLUID, fluidOutput);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (recipe.time > 0) {
            int ticks = (int) (System.currentTimeMillis() / 50 % recipe.time);
            int h = (int) (18.0F * ((float) ticks / (float) recipe.time));
            ClientUtils.drawGradientRect(75, 26 + h, 82, 44, -2829653, -3882338);
        }
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 72 && mouseX <= 82 && mouseY >= 25 && mouseY <= 43) {
            List<String> tooltip = new ArrayList<>();
            tooltip.add(EnumChatFormatting.GRAY + "Time: " + EnumChatFormatting.WHITE + recipe.time + " Ticks");
            return tooltip;
        }
        return Collections.emptyList();
    }
}
