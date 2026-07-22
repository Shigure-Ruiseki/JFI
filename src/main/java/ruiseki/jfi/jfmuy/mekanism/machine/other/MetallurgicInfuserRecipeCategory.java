package ruiseki.jfi.jfmuy.mekanism.machine.other;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;

import mekanism.api.infuse.InfuseRegistry;
import mekanism.api.infuse.InfuseType;
import mekanism.client.gui.element.GuiPowerBar;
import mekanism.client.gui.element.GuiProgress;
import mekanism.client.gui.element.GuiSlot;
import mekanism.common.recipe.RecipeHandler;
import mekanism.common.recipe.machines.MetallurgicInfuserRecipe;
import ruiseki.jfi.jfmuy.mekanism.BaseRecipeCategory;
import ruiseki.jfmuy.api.IGuiHelper;
import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;

public class MetallurgicInfuserRecipeCategory<WRAPPER extends MetallurgicInfuserRecipeWrapper>
    extends BaseRecipeCategory<WRAPPER> {

    public MetallurgicInfuserRecipeCategory(IGuiHelper helper) {
        super(
            helper,
            "mekanism:gui/GuiMetallurgicInfuser.png",
            RecipeHandler.Recipe.METALLURGIC_INFUSER.getRecipeName(),
            "tile.MachineBlock.MetallurgicInfuser.name",
            GuiProgress.ProgressBar.MEDIUM,
            5,
            16,
            166,
            54);
    }

    public static List<ItemStack> getInfuseStacks(InfuseType type) {
        return InfuseRegistry.getObjectMap()
            .entrySet()
            .stream()
            .filter(obj -> obj.getValue().type == type)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    @Override
    protected void addGuiElements() {
        guiElements.add(new GuiSlot(GuiSlot.SlotType.EXTRA, this, guiLocation, 16, 34));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.INPUT, this, guiLocation, 50, 42));
        guiElements
            .add(new GuiSlot(GuiSlot.SlotType.POWER, this, guiLocation, 142, 34).with(GuiSlot.SlotOverlay.POWER));
        guiElements.add(new GuiSlot(GuiSlot.SlotType.OUTPUT, this, guiLocation, 108, 42));
        guiElements.add(new GuiPowerBar(this, new GuiPowerBar.IPowerInfoHandler() {

            @Override
            public double getLevel() {
                return 1F;
            }
        }, guiLocation, 164, 15));
        guiElements.add(new GuiProgress(new GuiProgress.IProgressInfoHandler() {

            @Override
            public double getProgress() {
                return (double) timer.getValue() / 20F;
            }
        }, GuiProgress.ProgressBar.MEDIUM, this, guiLocation, 70, 46));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients) {
        MetallurgicInfuserRecipe tempRecipe = recipeWrapper.getRecipe();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, true, 45, 26);
        itemStacks.init(1, false, 103, 26);
        itemStacks.init(2, true, 11, 18);
        itemStacks.set(0, tempRecipe.getInput().inputStack);
        itemStacks.set(1, tempRecipe.getOutput().output);
        itemStacks.set(2, getInfuseStacks(tempRecipe.getInput().infuse.type));
    }
}
