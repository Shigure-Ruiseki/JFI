package ruiseki.jfi.jfmuy.ae2.grinder;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import appeng.api.features.IGrinderEntry;
import appeng.core.localization.ColorUtils;
import appeng.core.localization.GuiText;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class GrinderRecipeWrapper implements IRecipeWrapper {

    private final ItemStack input;
    private final ItemStack output;
    private final ItemStack optionalOutput;
    private final ItemStack secondOptionalOutput;

    private final boolean hasOptional;
    private final String displayChance;

    public GrinderRecipeWrapper(IGrinderEntry recipe) {
        this.input = recipe.getInput();
        this.output = recipe.getOutput();
        this.optionalOutput = recipe.getOptionalOutput();
        this.secondOptionalOutput = recipe.getSecondOptionalOutput();

        boolean optional = false;
        String chanceStr = "";

        int optionalChancePercent = (int) (recipe.getOptionalChance() * 100);
        int secondOptionalChancePercent = (int) (recipe.getSecondOptionalChance() * 100);

        if (optionalOutput != null) {
            optional = true;
            chanceStr = String.format(GuiText.OfSecondOutput.getLocal(), optionalChancePercent);
        }

        if (secondOptionalOutput != null) {
            optional = true;
            chanceStr = String
                .format(GuiText.MultipleOutputs.getLocal(), optionalChancePercent, secondOptionalChancePercent);
        }

        this.hasOptional = optional;
        this.displayChance = chanceStr;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (input != null) {
            ingredients.setInput(VanillaTypes.ITEM, input);
        }

        List<ItemStack> outputs = new ArrayList<>();
        if (output != null) {
            outputs.add(output);
        }
        if (optionalOutput != null) {
            outputs.add(optionalOutput);
        }
        if (secondOptionalOutput != null) {
            outputs.add(secondOptionalOutput);
        }

        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer fr = minecraft.fontRenderer;
        if (hasOptional) {
            int width = fr.getStringWidth(displayChance);
            fr.drawString(displayChance, (recipeWidth - width) / 2, 5, ColorUtils.neiGrindstoneRecipeChance.getColor());
        } else {
            String noSecText = GuiText.NoSecondOutput.getLocal();
            int width = fr.getStringWidth(noSecText);
            fr.drawString(noSecText, (recipeWidth - width) / 2, 5, ColorUtils.neiGrindstoneNoSecondOutput.getColor());
        }
    }
}
