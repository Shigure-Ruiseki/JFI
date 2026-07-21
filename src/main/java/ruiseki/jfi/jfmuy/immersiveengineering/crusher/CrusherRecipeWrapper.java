package ruiseki.jfi.jfmuy.immersiveengineering.crusher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import blusunrize.immersiveengineering.client.ClientUtils;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class CrusherRecipeWrapper implements IRecipeWrapper {

    private final CrusherRecipe recipe;
    private final List<List<ItemStack>> inputs;
    private final List<ItemStack> outputs;

    public CrusherRecipeWrapper(CrusherRecipe recipe) {
        this.recipe = recipe;
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();

        if (recipe.input instanceof String) {
            this.inputs.add(net.minecraftforge.oredict.OreDictionary.getOres((String) recipe.input));
        } else if (recipe.input instanceof ItemStack) {
            this.inputs.add(Collections.singletonList((ItemStack) recipe.input));
        } else if (recipe.input instanceof List) {
            this.inputs.add((List<ItemStack>) recipe.input);
        }

        if (recipe.output != null) {
            this.outputs.add(recipe.output);
        }

        if (recipe.secondaryOutput != null) {
            for (ItemStack sec : recipe.secondaryOutput) {
                if (sec != null) {
                    this.outputs.add(sec);
                }
            }
        }
    }

    public int getSecondaryOutputsCount() {
        return recipe.secondaryOutput != null ? recipe.secondaryOutput.length : 0;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        String energyStr = recipe.energy + " RF";
        int textX = 120 - ClientUtils.font()
            .getStringWidth(energyStr) / 2;
        ClientUtils.font()
            .drawString(energyStr, textX, 20, 0x777777, false);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> tooltip = new ArrayList<>();

        if (recipe.secondaryOutput != null && recipe.secondaryChance != null) {
            for (int i = 0; i < recipe.secondaryOutput.length; i++) {
                int slotX = 90 + i * 18;
                int slotY = 40;

                if (mouseX >= slotX && mouseX <= slotX + 16 && mouseY >= slotY && mouseY <= slotY + 16) {
                    float chance = recipe.secondaryChance[i] * 100.0F;
                    tooltip.add(
                        EnumChatFormatting.GRAY + String.format(
                            "%s %.0f%%",
                            StatCollector.translateToLocal("desc.ImmersiveEngineering.info.chance"),
                            chance));
                }
            }
        }

        return tooltip;
    }
}
