package ruiseki.jfi.jfmuy.thermalexpansion.machine.sawmill;

import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalexpansion.util.crafting.SawmillManager;
import cofh.thermalexpansion.util.crafting.SawmillManager.RecipeSawmill;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.machine.BaseRecipeWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class SawmillRecipeWrapper extends BaseRecipeWrapper {

    /* Recipe */
    protected List<List<ItemStack>> inputs;
    protected List<ItemStack> outputs;

    protected int chance;

    /* Animation */
    protected IDrawableAnimated progress;
    protected IDrawableAnimated speed;

    public SawmillRecipeWrapper(IGuiHelper guiHelper, RecipeSawmill recipe) {

        this(guiHelper, recipe, RecipeUidsTE.SAWMILL);
    }

    public SawmillRecipeWrapper(IGuiHelper guiHelper, RecipeSawmill recipe, String uIdIn) {

        uId = uIdIn;

        List<ItemStack> recipeInputs = new ArrayList<>();
        List<ItemStack> recipeOutputs = new ArrayList<>();

        int oreID = new SawmillManager.ComparableItemStackSawmill(recipe.getInput()).oreID;
        if (oreID != -1) {
            for (ItemStack ore : OreDictionary.getOres(ItemHelper.oreProxy.getOreName(oreID), false)) {
                recipeInputs.add(ItemHelper.cloneStack(ore, recipe.getInput().stackSize));
            }
        } else {
            recipeInputs.add(recipe.getInput());
        }
        recipeOutputs.add(recipe.getPrimaryOutput());

        if (recipe.getSecondaryOutput() != null) {
            recipeOutputs.add(recipe.getSecondaryOutput());
        }
        energy = recipe.getEnergy();
        inputs = singletonList(recipeInputs);
        outputs = recipeOutputs;

        chance = recipe.getSecondaryOutputChance();

        IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
            .getProgressFill(Drawables.PROGRESS_ARROW);
        IDrawableStatic speedDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_SAW);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();

        int basePower = ThermalExpansion.config.get("Machine.Sawmill", "BasePower", 40);

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
        speed.draw(minecraft, 45, 33);
        energyMeter.draw(minecraft, 2, 8);

        if (chance > 0) {
            String dispChance = chance + "%";
            minecraft.fontRenderer.drawString(dispChance, 102 - 6 * dispChance.length(), 48, 0x808080);
        }
    }

}
