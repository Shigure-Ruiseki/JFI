package ruiseki.jfi.jfmuy.thermalexpansion.machine.furnace;

import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import cofh.lib.util.helpers.ItemHelper;
import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalexpansion.util.crafting.FurnaceManager;
import cofh.thermalexpansion.util.crafting.FurnaceManager.RecipeFurnace;
import ruiseki.jfi.jfmuy.thermalexpansion.Drawables;
import ruiseki.jfi.jfmuy.thermalexpansion.RecipeUidsTE;
import ruiseki.jfi.jfmuy.thermalexpansion.machine.BaseRecipeWrapper;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IDrawableAnimated;
import ruiseki.jfmuy.api.gui.IDrawableStatic;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class FurnaceRecipeWrapper extends BaseRecipeWrapper {

    /* Recipe */
    protected List<List<ItemStack>> inputs;
    protected List<ItemStack> outputs;

    /* Animation */
    protected IDrawableAnimated fluid;
    protected IDrawableAnimated progress;
    protected IDrawableAnimated speed;

    public FurnaceRecipeWrapper(IGuiHelper guiHelper, RecipeFurnace recipe) {

        this(guiHelper, recipe, RecipeUidsTE.FURNACE);
    }

    public FurnaceRecipeWrapper(IGuiHelper guiHelper, RecipeFurnace recipe, String uIdIn) {

        uId = uIdIn;

        List<ItemStack> recipeInputs = new ArrayList<>();
        List<ItemStack> recipeOutputs = new ArrayList<>();

        int oreID = new FurnaceManager.ComparableItemStackFurnace(recipe.getInput()).oreID;
        if (oreID != -1) {
            for (ItemStack ore : OreDictionary.getOres(ItemHelper.oreProxy.getOreName(oreID), false)) {
                recipeInputs.add(ItemHelper.cloneStack(ore, recipe.getInput().stackSize));
            }
        } else {
            recipeInputs.add(recipe.getInput());
        }
        switch (uId) {
            case RecipeUidsTE.FURNACE_FOOD:
                recipeOutputs.add(
                    ItemHelper.cloneStack(
                        recipe.getOutput(),
                        recipe.getOutput().stackSize + Math.max(1, recipe.getOutput().stackSize / 2)));
                energy = recipe.getEnergy() * 3 / 2;
                break;
            default:
                recipeOutputs.add(recipe.getOutput());
                energy = recipe.getEnergy();
                break;
        }
        inputs = singletonList(recipeInputs);
        outputs = recipeOutputs;

        IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper)
            .getProgressFill(Drawables.PROGRESS_ARROW);
        IDrawableStatic speedDrawable = Drawables.getDrawables(guiHelper)
            .getScaleFill(Drawables.SCALE_FLAME);
        IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper)
            .getEnergyFill();

        int basePower = ThermalExpansion.config.get("Machine.Furnace", "BasePower", 40);

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
    }

}
