package ruiseki.jfi.jfmuy.thermalexpansion.machine.smelter;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalexpansion.util.crafting.SmelterManager;
import cofh.thermalexpansion.util.crafting.SmelterManager.RecipeSmelter;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.machine.BaseRecipeWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class SmelterRecipeWrapper extends BaseRecipeWrapper {

    /* Recipe */
    protected List<List<ItemStack>> inputs;
    protected List<ItemStack> outputs;
    protected int chance;

    /* Animation */
    protected IDrawableAnimated progress;
    protected IDrawableAnimated speed;

    public SmelterRecipeWrapper(IGuiHelper guiHelper, RecipeSmelter recipe) {
        List<List<ItemStack>> recipeInputs = new ArrayList<>();
        List<ItemStack> recipeOutputs = new ArrayList<>();

        List<ItemStack> recipeInputsPrimary = new ArrayList<>();
        List<ItemStack> recipeInputsSecondary = new ArrayList<>();

        int oreID = SmelterManager.ComparableItemStackSmelter.getOreID(recipe.getPrimaryInput());
        if (oreID != -1) {
            for (ItemStack ore : OreDictionary.getOres(ItemHelper.oreProxy.getOreName(oreID), false)) {
                recipeInputsPrimary.add(ItemHelper.cloneStack(ore, recipe.getPrimaryInput().stackSize));
            }
        } else {
            recipeInputsPrimary.add(recipe.getPrimaryInput());
        }
        oreID = SmelterManager.ComparableItemStackSmelter.getOreID(recipe.getSecondaryInput());
        if (oreID != -1) {
            for (ItemStack ore : OreDictionary.getOres(ItemHelper.oreProxy.getOreName(oreID), false)) {
                recipeInputsSecondary.add(ItemHelper.cloneStack(ore, recipe.getSecondaryInput().stackSize));
            }
        } else {
            recipeInputsSecondary.add(recipe.getSecondaryInput());
        }

        recipeInputs.add(recipeInputsSecondary);
        recipeInputs.add(recipeInputsPrimary);

        recipeOutputs.add(recipe.getPrimaryOutput());
        energy = recipe.getEnergy();

        if (recipe.getSecondaryOutput() != null) {
            recipeOutputs.add(recipe.getSecondaryOutput());
        }

        inputs = recipeInputs;
        outputs = recipeOutputs;
        chance = recipe.getSecondaryOutputChance();

        IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
            .getProgressFill(Drawables.PROGRESS_ARROW);
        IDrawableStatic speedDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_FLAME);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();

        int basePower = ThermalExpansion.config.get("Machine.Smelter", "BasePower", 40);

        // Khởi tạo các thanh hoạt ảnh chuyển động
        progress = guiHelper.createAnimatedDrawable(
            progressDrawable,
            Math.max(10, energy / basePower),
            IDrawableAnimated.StartDirection.LEFT,
            false);
        speed = guiHelper.createAnimatedDrawable(speedDrawable, 1000, IDrawableAnimated.StartDirection.TOP, true);
        energyMeter = guiHelper
            .createAnimatedDrawable(energyDrawable, 1000, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        progress.draw(minecraft, 69, 23);
        speed.draw(minecraft, 34, 33);
        energyMeter.draw(minecraft, 2, 8);

        if (chance > 0) {
            String dispChance = chance + "%";
            minecraft.fontRenderer.drawString(dispChance, 150 - 6 * dispChance.length(), 48, 0x808080);
        }
    }
}
