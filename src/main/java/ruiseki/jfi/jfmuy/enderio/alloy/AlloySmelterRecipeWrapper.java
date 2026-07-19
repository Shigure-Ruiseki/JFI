package ruiseki.jfi.jfmuy.enderio.alloy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import codechicken.nei.ItemList;
import crazypants.enderio.machine.recipe.IRecipe;
import crazypants.enderio.machine.recipe.RecipeInput;
import crazypants.enderio.power.PowerDisplayUtil;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class AlloySmelterRecipeWrapper implements IRecipeWrapper {

    private final IRecipe recipe;

    public AlloySmelterRecipeWrapper(IRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputs = new ArrayList<>();
        for (RecipeInput recipeInput : recipe.getInputs()) {
            if (recipeInput != null) {
                inputs.add(getInputsWithPermutations(recipeInput));
            }
        }
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        if (recipe.getOutputs() != null && recipe.getOutputs().length > 0) {
            ItemStack outputStack = recipe.getOutputs()[0].getOutput();
            ingredients.setOutput(VanillaTypes.ITEM, outputStack);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        String energyString = PowerDisplayUtil.formatPower(recipe.getEnergyRequired()) + " "
            + PowerDisplayUtil.abrevation();
        minecraft.fontRenderer.drawString(energyString, 73, 52, 0x808080, false);
    }

    private List<ItemStack> getInputsWithPermutations(RecipeInput input) {
        List<ItemStack> result = new ArrayList<>();
        if (input.getInput() != null) {
            result.add(input.getInput());
        }

        ItemStack[] equivs = input.getEquivelentInputs();
        if (equivs != null) {
            for (ItemStack item : equivs) {
                if (item.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                    List<ItemStack> permutations = ItemList.itemMap.get(item.getItem());
                    if (permutations != null && !permutations.isEmpty()) {
                        for (ItemStack stack : permutations) {
                            ItemStack perm = stack.copy();
                            perm.stackSize = item.stackSize;
                            result.add(perm);
                        }
                    } else {
                        ItemStack base = new ItemStack(item.getItem(), item.stackSize);
                        base.stackTagCompound = item.stackTagCompound;
                        result.add(base);
                    }
                } else {
                    result.add(item.copy());
                }
            }
        }
        return result;
    }

    public IRecipe getRecipe() {
        return this.recipe;
    }
}
