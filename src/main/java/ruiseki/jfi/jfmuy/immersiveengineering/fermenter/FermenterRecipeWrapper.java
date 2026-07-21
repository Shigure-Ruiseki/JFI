package ruiseki.jfi.jfmuy.immersiveengineering.fermenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import blusunrize.immersiveengineering.api.energy.DieselHandler;
import blusunrize.immersiveengineering.client.ClientUtils;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class FermenterRecipeWrapper implements IRecipeWrapper {

    private final DieselHandler.FermenterRecipe recipe;
    private final List<List<ItemStack>> inputs;

    public FermenterRecipeWrapper(DieselHandler.FermenterRecipe recipe) {
        this.recipe = recipe;
        this.inputs = new ArrayList<>();

        Object in = recipe.input;
        if (in instanceof String) {
            this.inputs.add(OreDictionary.getOres((String) in));
        } else if (in instanceof ItemStack) {
            this.inputs.add(Collections.singletonList((ItemStack) in));
        } else if (in instanceof List) {
            this.inputs.add((List<ItemStack>) in);
        } else {
            this.inputs.add(Collections.emptyList());
        }
    }

    public DieselHandler.FermenterRecipe getRecipe() {
        return this.recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        if (recipe.output != null) {
            ingredients.setOutput(VanillaTypes.ITEM, recipe.output);
        }

        if (recipe.fluid != null) {
            ingredients.setOutput(VanillaTypes.FLUID, recipe.fluid);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int totalTime = recipe.time;
        if (totalTime > 0) {
            long ticks = minecraft.theWorld != null ? minecraft.theWorld.getTotalWorldTime() : 0;
            int h = (int) (18.0F * ((float) (ticks % totalTime) / (float) totalTime));
            ClientUtils.drawGradientRect(75, 26 + h, 82, 44, -2829653, -3882338);
        }
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> tooltip = new ArrayList<>();

        if (mouseX >= 75 && mouseX <= 82 && mouseY >= 26 && mouseY <= 44) {
            tooltip.add(EnumChatFormatting.GRAY + String.valueOf(recipe.time) + " Ticks");
        }

        return tooltip;
    }
}
