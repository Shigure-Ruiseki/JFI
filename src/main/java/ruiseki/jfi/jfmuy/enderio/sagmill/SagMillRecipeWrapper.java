package ruiseki.jfi.jfmuy.enderio.sagmill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import crazypants.enderio.machine.crusher.CrusherRecipeManager;
import crazypants.enderio.machine.crusher.GrindingBall;
import crazypants.enderio.machine.recipe.IRecipe;
import crazypants.enderio.machine.recipe.RecipeInput;
import crazypants.enderio.machine.recipe.RecipeOutput;
import crazypants.enderio.power.PowerDisplayUtil;
import ruiseki.jfmuy.api.gui.ITooltipCallback;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class SagMillRecipeWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {

    private final IRecipe recipe;
    private final List<ItemStack> mainInputs;
    private final List<ItemStack> grindingBalls;
    private final String energyString;

    public SagMillRecipeWrapper(IRecipe recipe) {
        this.recipe = recipe;

        this.mainInputs = new ArrayList<>();
        RecipeInput inputObj = recipe.getInputs()[0];
        if (inputObj.getInput() != null) {
            this.mainInputs.add(inputObj.getInput());
        }
        ItemStack[] equivs = inputObj.getEquivelentInputs();
        if (equivs != null) {
            this.mainInputs.addAll(Arrays.asList(equivs));
        }

        this.grindingBalls = new ArrayList<>();
        List<GrindingBall> activeBalls = CrusherRecipeManager.getInstance()
            .getBalls();
        for (GrindingBall ball : activeBalls) {
            if (ball.getInput() != null) {
                this.grindingBalls.add(ball.getInput());
            }
        }

        this.energyString = PowerDisplayUtil.formatPower(recipe.getEnergyRequired()) + " "
            + PowerDisplayUtil.abrevation();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputs = new ArrayList<>();
        inputs.add(this.mainInputs);
        inputs.add(this.grindingBalls);
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        List<List<ItemStack>> outputs = new ArrayList<>();
        RecipeOutput[] recipeOutputs = recipe.getOutputs();

        for (int i = 0; i < 4; i++) {
            List<ItemStack> slotOutput = new ArrayList<>();
            if (i < recipeOutputs.length && recipeOutputs[i].getOutput() != null) {
                slotOutput.add(recipeOutputs[i].getOutput());
            }
            outputs.add(slotOutput);
        }
        ingredients.setOutputLists(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(energyString, 76, 33, 0x808080, false);
    }

    @Override
    public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
        if (!input && slotIndex >= 1 && slotIndex <= 4) {
            RecipeOutput[] recipeOutputs = recipe.getOutputs();
            int outputIndex = slotIndex - 1;

            if (outputIndex < recipeOutputs.length && recipeOutputs[outputIndex].getOutput() != null) {
                float chance = recipeOutputs[outputIndex].getChance();
                if (chance > 0.0F && chance < 1.0F) {
                    int chanceInt = (int) (chance * 100);
                    tooltip.add(
                        EnumChatFormatting.GRAY + MessageFormat
                            .format(StatCollector.translateToLocal("enderio.nei.sagmill.outputchance"), chanceInt));
                }
            }
        }
    }

    public IRecipe getRecipe() {
        return this.recipe;
    }
}
