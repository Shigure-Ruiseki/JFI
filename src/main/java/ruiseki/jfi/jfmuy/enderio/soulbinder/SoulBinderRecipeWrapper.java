package ruiseki.jfi.jfmuy.enderio.soulbinder;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import crazypants.enderio.EnderIO;
import crazypants.enderio.machine.soul.ISoulBinderRecipe;
import crazypants.enderio.power.PowerDisplayUtil;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;
import ruiseki.okcore.helper.LangHelpers;

public class SoulBinderRecipeWrapper implements IRecipeWrapper {

    private final ISoulBinderRecipe recipe;
    private final List<List<ItemStack>> inputs;
    private final List<List<ItemStack>> outputs;

    private final String energyString;
    private final int xpCost;

    public SoulBinderRecipeWrapper(ISoulBinderRecipe recipe) {
        this.recipe = recipe;

        this.inputs = new ArrayList<>();

        this.inputs.add(getSoulVialInputs(recipe.getSupportedSouls()));

        List<ItemStack> mainInput = new ArrayList<>();
        if (recipe.getInputStack() != null) {
            mainInput.add(recipe.getInputStack());
        }
        this.inputs.add(mainInput);

        this.outputs = new ArrayList<>();

        List<ItemStack> mainOutput = new ArrayList<>();
        if (recipe.getOutputStack() != null) {
            mainOutput.add(recipe.getOutputStack());
        }
        this.outputs.add(mainOutput);

        List<ItemStack> emptyVial = new ArrayList<>();
        emptyVial.add(new ItemStack(EnderIO.itemSoulVessel));
        this.outputs.add(emptyVial);

        this.energyString = PowerDisplayUtil.formatPower(recipe.getEnergyRequired()) + " "
            + PowerDisplayUtil.abrevation();
        this.xpCost = recipe.getExperienceLevelsRequired();
    }

    private List<ItemStack> getSoulVialInputs(List<String> mobs) {
        List<ItemStack> result = new ArrayList<>(mobs.size());
        for (String mobName : mobs) {
            ItemStack sv = new ItemStack(EnderIO.itemSoulVessel);
            sv.stackTagCompound = new NBTTagCompound();
            sv.stackTagCompound.setString("id", mobName);
            result.add(sv);
        }
        return result;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, this.inputs);
        ingredients.setOutputLists(VanillaTypes.ITEM, this.outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int centerX = 65;

        int energyWidth = minecraft.fontRenderer.getStringWidth(energyString);
        minecraft.fontRenderer.drawString(energyString, centerX - (energyWidth / 2), 35, 0x808080, false);

        if (xpCost > 0) {
            String xpString = LangHelpers.localize("container.repair.cost", xpCost);
            int xpWidth = minecraft.fontRenderer.getStringWidth(xpString);
            minecraft.fontRenderer.drawString(xpString, centerX - (xpWidth / 2), 45, 0x80FF20, false);
        }
    }

    public ISoulBinderRecipe getRecipe() {
        return this.recipe;
    }
}
