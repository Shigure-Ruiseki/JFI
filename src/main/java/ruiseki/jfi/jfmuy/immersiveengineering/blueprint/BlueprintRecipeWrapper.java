package ruiseki.jfi.jfmuy.immersiveengineering.blueprint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe;
import blusunrize.immersiveengineering.common.IEContent;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class BlueprintRecipeWrapper implements IRecipeWrapper {

    private final int blueprintMeta;
    private final BlueprintCraftingRecipe recipe;
    private final ItemStack blueprintStack;

    public BlueprintRecipeWrapper(int blueprintMeta, BlueprintCraftingRecipe recipe) {
        this.blueprintMeta = blueprintMeta;
        this.recipe = recipe;
        this.blueprintStack = new ItemStack(IEContent.itemBlueprint, 1, blueprintMeta);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputs = new ArrayList<>();

        inputs.add(Collections.singletonList(this.blueprintStack));

        ArrayList<Object> formattedInputs = recipe.getFormattedInputs();
        for (int i = 0; i < 6; i++) {
            if (i < formattedInputs.size() && recipe.inputs[i] != null) {
                Object obj = formattedInputs.get(i);
                if (obj instanceof List) {
                    inputs.add((List<ItemStack>) obj);
                } else if (obj instanceof ItemStack) {
                    inputs.add(Collections.singletonList((ItemStack) obj));
                } else {
                    inputs.add(Collections.emptyList());
                }
            } else {
                inputs.add(Collections.emptyList());
            }
        }

        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }
}
