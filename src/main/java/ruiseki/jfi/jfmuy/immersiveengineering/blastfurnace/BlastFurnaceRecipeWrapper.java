package ruiseki.jfi.jfmuy.immersiveengineering.blastfurnace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import blusunrize.immersiveengineering.api.crafting.BlastFurnaceRecipe;
import blusunrize.immersiveengineering.client.ClientUtils;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class BlastFurnaceRecipeWrapper implements IRecipeWrapper {

    private final BlastFurnaceRecipe recipe;
    private final List<List<ItemStack>> inputs;
    private final List<ItemStack> fuels;

    public BlastFurnaceRecipeWrapper(BlastFurnaceRecipe recipe) {
        this.recipe = recipe;
        this.inputs = new ArrayList<>();
        this.fuels = new ArrayList<>();

        if (recipe.input instanceof String) {
            this.inputs.add(OreDictionary.getOres((String) recipe.input));
        } else if (recipe.input instanceof ItemStack) {
            this.inputs.add(Collections.singletonList((ItemStack) recipe.input));
        } else if (recipe.input instanceof List) {
            this.inputs.add((List<ItemStack>) recipe.input);
        }

        for (Object fuelObj : BlastFurnaceRecipe.blastFuels.keySet()) {
            if (fuelObj instanceof String) {
                this.fuels.addAll(OreDictionary.getOres((String) fuelObj));
            } else if (fuelObj instanceof ItemStack) {
                this.fuels.add((ItemStack) fuelObj);
            } else if (fuelObj instanceof List) {
                this.fuels.addAll((List<ItemStack>) fuelObj);
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> fullInputs = new ArrayList<>(inputs);
        fullInputs.add(fuels);
        ingredients.setInputLists(VanillaTypes.ITEM, fullInputs);

        List<ItemStack> outputs = new ArrayList<>();
        outputs.add(recipe.output);
        if (recipe.slag != null) {
            outputs.add(recipe.slag);
        }
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        float seconds = recipe.time / 20.0F;
        String timeString = seconds + "s";

        ClientUtils.font()
            .drawString(
                timeString,
                125 - ClientUtils.font()
                    .getStringWidth(timeString) / 2,
                32,
                11184810,
                true);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 42 && mouseX <= 58 && mouseY >= 45 && mouseY <= 61) {
            Minecraft mc = Minecraft.getMinecraft();
            int ticks = (int) (mc.theWorld.getTotalWorldTime() / 20);
            if (!fuels.isEmpty()) {
                ItemStack currentFuel = fuels.get(ticks % fuels.size());
                if (currentFuel != null && BlastFurnaceRecipe.isValidBlastFuel(currentFuel)) {
                    List<String> tooltip = new ArrayList<>();
                    tooltip.add(
                        EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted(
                            "desc.ImmersiveEngineering.info.blastFuelTime",
                            BlastFurnaceRecipe.getBlastFuelTime(currentFuel)));
                    return tooltip;
                }
            }
        }
        return Collections.emptyList();
    }

    public BlastFurnaceRecipe getRecipe() {
        return this.recipe;
    }
}
