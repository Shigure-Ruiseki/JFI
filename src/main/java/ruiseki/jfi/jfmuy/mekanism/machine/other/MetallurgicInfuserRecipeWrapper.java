package ruiseki.jfi.jfmuy.mekanism.machine.other;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;

import mekanism.common.InfuseStorage;
import mekanism.common.recipe.machines.MetallurgicInfuserRecipe;
import ruiseki.jfi.jfmuy.mekanism.machine.MekanismRecipeWrapper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;

public class MetallurgicInfuserRecipeWrapper extends MekanismRecipeWrapper<MetallurgicInfuserRecipe> {

    public MetallurgicInfuserRecipeWrapper(MetallurgicInfuserRecipe recipe) {
        super(recipe);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> inputStacks = Collections.singletonList(recipe.recipeInput.inputStack);
        List<ItemStack> infuseStacks = MetallurgicInfuserRecipeCategory.getInfuseStacks(recipe.getInput().infuse.type);
        ingredients.setInput(VanillaTypes.ITEM, recipe.recipeInput.inputStack);
        ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(inputStacks, infuseStacks));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.recipeOutput.output);
    }

    @Override
    public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (mc.currentScreen != null) {
            mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            mc.currentScreen.drawTexturedModelRectFromIcon(2, 2, recipe.getInput().infuse.type.icon, 4, 52);
        }
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (mouseX >= 2 && mouseX < 6 && mouseY >= 2 && mouseY < 54) {
            InfuseStorage infuse = recipe.getInput().infuse;
            return Collections.singletonList(infuse.type.getLocalizedName() + ": " + infuse.amount);
        }
        return Collections.emptyList();
    }
}
