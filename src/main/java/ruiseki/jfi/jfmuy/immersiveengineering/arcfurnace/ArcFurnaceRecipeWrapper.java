package ruiseki.jfi.jfmuy.immersiveengineering.arcfurnace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;
import blusunrize.immersiveengineering.client.ClientUtils;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class ArcFurnaceRecipeWrapper implements IRecipeWrapper {

    private final ArcFurnaceRecipe recipe;
    private final List<List<ItemStack>> inputs;
    private final List<ItemStack> actualOutputs;
    private int outputCount = 0;

    public ArcFurnaceRecipeWrapper(ArcFurnaceRecipe recipe) {
        this.recipe = recipe;
        this.inputs = new ArrayList<>();
        this.actualOutputs = new ArrayList<>();

        if (recipe.input instanceof String) {
            this.inputs.add(OreDictionary.getOres((String) recipe.input));
        } else if (recipe.input instanceof ItemStack) {
            this.inputs.add(Collections.singletonList((ItemStack) recipe.input));
        } else if (recipe.input instanceof List) {
            this.inputs.add((List<ItemStack>) recipe.input);
        }

        ItemStack[] stackAdditives = new ItemStack[recipe.additives.length];
        for (int i = 0; i < recipe.additives.length; i++) {
            Object addObj = recipe.additives[i];
            if (addObj != null) {
                if (addObj instanceof String) {
                    this.inputs.add(OreDictionary.getOres((String) addObj));
                } else if (addObj instanceof ItemStack) {
                    this.inputs.add(Collections.singletonList((ItemStack) addObj));
                } else if (addObj instanceof List) {
                    this.inputs.add((List<ItemStack>) addObj);
                }
                stackAdditives[i] = ApiUtils.getItemStackFromObject(addObj);
            } else {
                this.inputs.add(Collections.emptyList());
            }
        }

        while (this.inputs.size() < 5) {
            this.inputs.add(Collections.emptyList());
        }

        ItemStack sampleInput = (recipe.input instanceof String) ? (OreDictionary.getOres((String) recipe.input)
            .isEmpty() ? null
                : OreDictionary.getOres((String) recipe.input)
                    .get(0))
            : ApiUtils.getItemStackFromObject(recipe.input);

        ItemStack[] outs = recipe.getOutputs(sampleInput, stackAdditives);
        if (outs != null && outs.length > 0) {
            for (ItemStack out : outs) {
                if (out != null) {
                    this.actualOutputs.add(out);
                }
            }
        } else if (recipe.output != null) {
            this.actualOutputs.add(recipe.output);
        }

        this.outputCount = this.actualOutputs.size();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        List<ItemStack> allOutputs = new ArrayList<>(actualOutputs);
        if (recipe.slag != null) {
            allOutputs.add(recipe.slag);
        }
        ingredients.setOutputs(VanillaTypes.ITEM, allOutputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        String energyStr = recipe.energyPerTick + " RF/t";
        ClientUtils.font()
            .drawString(
                energyStr,
                68 - ClientUtils.font()
                    .getStringWidth(energyStr) / 2,
                0,
                7829367,
                false);

        float seconds = recipe.time / 20.0F;
        String timeStr = seconds + "s";
        ClientUtils.font()
            .drawString(
                timeStr,
                64 - ClientUtils.font()
                    .getStringWidth(timeStr) / 2,
                12,
                7829367,
                false);
    }

    public int getOutputCount() {
        return this.outputCount;
    }

    public ArcFurnaceRecipe getRecipe() {
        return this.recipe;
    }
}
