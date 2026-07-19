package ruiseki.jfi.jfmuy.enderio.vat;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import com.enderio.core.client.render.RenderUtil;

import crazypants.enderio.machine.recipe.IRecipe;
import crazypants.enderio.machine.recipe.RecipeInput;
import crazypants.enderio.power.PowerDisplayUtil;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class VatRecipeWrapper implements IRecipeWrapper {

    private final IRecipe recipe;
    private final List<List<ItemStack>> inputStacks;
    private final List<List<ItemStack>> outputStacks;

    private FluidStack inputFluid;
    private final FluidStack outputFluid;
    private final int energyRequired;

    private final List<Float> slot1Multipliers = new ArrayList<>();
    private final List<Float> slot2Multipliers = new ArrayList<>();
    private float fluidMultiplier = 1.0f;

    public Rectangle inTankBounds = new Rectangle(3, 1, 15, 47);
    public Rectangle outTankBounds = new Rectangle(105, 1, 15, 47);

    public VatRecipeWrapper(IRecipe recipe) {
        this.recipe = recipe;
        this.energyRequired = recipe.getEnergyRequired();
        this.outputFluid = recipe.getOutputs()[0].getFluidOutput();

        this.inputStacks = new ArrayList<>();
        this.outputStacks = new ArrayList<>();

        List<ItemStack> slot1List = new ArrayList<>();
        List<ItemStack> slot2List = new ArrayList<>();

        for (RecipeInput input : recipe.getInputs()) {
            if (input.getInput() != null) {
                List<ItemStack> equivalents = getInputs(input);
                if (input.getSlotNumber() == 0) {
                    slot1List.addAll(equivalents);
                    for (ItemStack ignored : equivalents) {
                        slot1Multipliers.add(input.getMulitplier());
                    }
                } else if (input.getSlotNumber() == 1) {
                    slot2List.addAll(equivalents);
                    for (ItemStack ignored : equivalents) {
                        slot2Multipliers.add(input.getMulitplier());
                    }
                }
            } else if (input.getFluidInput() != null) {
                this.inputFluid = input.getFluidInput();
                this.fluidMultiplier = input.getMulitplier();
            }
        }

        if (!slot1List.isEmpty()) this.inputStacks.add(slot1List);
        if (!slot2List.isEmpty()) this.inputStacks.add(slot2List);
    }

    private List<ItemStack> getInputs(RecipeInput input) {
        List<ItemStack> result = new ArrayList<>();
        result.add(input.getInput());
        ItemStack[] eq = input.getEquivelentInputs();
        if (eq != null) {
            for (ItemStack st : eq) {
                result.add(st);
            }
        }
        return result;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, this.inputStacks);
        ingredients.setOutputLists(VanillaTypes.ITEM, this.outputStacks);

        if (inputFluid != null) {
            ingredients.setInput(VanillaTypes.FLUID, inputFluid);
        }
        if (outputFluid != null) {
            ingredients.setOutput(VanillaTypes.FLUID, outputFluid);
        }
    }

    public int getCurrentInputFluidAmount(int indexSlot1, int indexSlot2) {
        float m1 = (!slot1Multipliers.isEmpty() && indexSlot1 < slot1Multipliers.size())
            ? slot1Multipliers.get(indexSlot1)
            : 1.0f;
        float m2 = (!slot2Multipliers.isEmpty() && indexSlot2 < slot2Multipliers.size())
            ? slot2Multipliers.get(indexSlot2)
            : 1.0f;
        return (int) Math.round(FluidContainerRegistry.BUCKET_VOLUME * m1 * m2);
    }

    public int getCurrentResultFluidAmount(int indexSlot1, int indexSlot2) {
        return (int) Math.round(getCurrentInputFluidAmount(indexSlot1, indexSlot2) * fluidMultiplier);
    }

    public List<Float> getSlot1Multipliers() {
        return slot1Multipliers;
    }

    public List<Float> getSlot2Multipliers() {
        return slot2Multipliers;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int ticks = (int) (minecraft.theWorld.getTotalWorldTime() / 30);

        int idx1 = 0;
        if (!slot1Multipliers.isEmpty()) {
            idx1 = ticks % slot1Multipliers.size();
        }
        int idx2 = 0;
        if (!slot2Multipliers.isEmpty()) {
            idx2 = ticks % slot2Multipliers.size();
        }

        GL11.glEnable(GL11.GL_BLEND);
        if (inputFluid != null && inputFluid.getFluid() != null) {
            RenderUtil.renderGuiTank(
                inputFluid,
                FluidContainerRegistry.BUCKET_VOLUME * 8,
                getCurrentInputFluidAmount(idx1, idx2),
                inTankBounds.x,
                inTankBounds.y,
                0,
                inTankBounds.width,
                inTankBounds.height);
        }

        if (outputFluid != null && outputFluid.getFluid() != null) {
            RenderUtil.renderGuiTank(
                outputFluid,
                FluidContainerRegistry.BUCKET_VOLUME * 8,
                getCurrentResultFluidAmount(idx1, idx2),
                outTankBounds.x,
                outTankBounds.y,
                0,
                outTankBounds.width,
                outTankBounds.height);
        }
        GL11.glDisable(GL11.GL_BLEND);

        String energyString = PowerDisplayUtil.formatPower(energyRequired) + " " + PowerDisplayUtil.abrevation();
        int energyWidth = minecraft.fontRenderer.getStringWidth(energyString);
        minecraft.fontRenderer.drawString(energyString, 98 - (energyWidth / 2), 58, 0x808080, false);

        if (!slot1Multipliers.isEmpty()) {
            String str1 = "x" + slot1Multipliers.get(idx1);
            int w1 = minecraft.fontRenderer.getStringWidth(str1);
            minecraft.fontRenderer.drawString(str1, 37 - (w1 / 2), 21, 0x808080, false);
        }

        if (!slot2Multipliers.isEmpty()) {
            String str2 = "x" + slot2Multipliers.get(idx2);
            int w2 = minecraft.fontRenderer.getStringWidth(str2);
            minecraft.fontRenderer.drawString(str2, 86 - (w2 / 2), 21, 0x808080, false);
        }
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return new ArrayList<>();
    }

    public IRecipe getRecipe() {
        return this.recipe;
    }
}
