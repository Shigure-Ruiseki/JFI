package ruiseki.jfi.jfmuy.enderio.slicensplice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import crazypants.enderio.machine.recipe.IRecipe;
import crazypants.enderio.machine.recipe.RecipeInput;
import crazypants.enderio.power.PowerDisplayUtil;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SliceAndSpliceRecipeWrapper implements IRecipeWrapper {

    private final IRecipe recipe;
    private final List<List<ItemStack>> inputs;
    private final List<ItemStack> outputs;
    private final String energyString;

    public SliceAndSpliceRecipeWrapper(IRecipe recipe) {
        this.recipe = recipe;
        this.inputs = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            inputs.add(new ArrayList<>());
        }

        for (RecipeInput recipeInput : recipe.getInputs()) {
            if (recipeInput != null) {
                int slotNum = recipeInput.getSlotNumber();
                if (slotNum >= 0 && slotNum < 6) {
                    List<ItemStack> slotIngredients = inputs.get(slotNum);
                    if (recipeInput.getInput() != null) {
                        slotIngredients.add(recipeInput.getInput());
                    }
                    ItemStack[] equivs = recipeInput.getEquivelentInputs();
                    if (equivs != null) {
                        slotIngredients.addAll(Arrays.asList(equivs));
                    }
                }
            }
        }

        List<ItemStack> axeList = new ArrayList<>();
        axeList.add(new ItemStack(Items.iron_axe));
        inputs.add(axeList);

        List<ItemStack> shearsList = new ArrayList<>();
        shearsList.add(new ItemStack(Items.shears));
        inputs.add(shearsList);

        this.outputs = new ArrayList<>();
        if (recipe.getOutputs().length > 0 && recipe.getOutputs()[0] != null) {
            this.outputs.add(recipe.getOutputs()[0].getOutput());
        }

        this.energyString = PowerDisplayUtil.formatPower(recipe.getEnergyRequired()) + " "
            + PowerDisplayUtil.abrevation();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, this.inputs);
        ingredients.setOutputs(VanillaTypes.ITEM, this.outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(energyString, 84, 57, 0x808080, false);
    }

    public IRecipe getRecipe() {
        return this.recipe;
    }
}
