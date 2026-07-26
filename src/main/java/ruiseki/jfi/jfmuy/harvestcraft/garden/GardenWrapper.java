package ruiseki.jfi.jfmuy.harvestcraft.garden;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class GardenWrapper implements IRecipeWrapper {

    private final ItemStack garden;
    private final List<ItemStack> outputs;
    private final String biomeTransKey;

    public GardenWrapper(ItemStack garden, List<ItemStack> outputs, String biomeTransKey) {
        this.garden = garden;
        this.outputs = outputs;
        this.biomeTransKey = biomeTransKey;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, garden);
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer fontRenderer = minecraft.fontRenderer;

        String biomeName = I18n.format(biomeTransKey);
        String textToDraw = I18n.format("harvestcraft.nei.garden.found", biomeName);

        int y = 78;
        @SuppressWarnings("unchecked")
        List<String> lines = fontRenderer.listFormattedStringToWidth(textToDraw, 162);

        for (String line : lines) {
            fontRenderer.drawString(line, 2, y, 0x000000);
            y += 11;
        }
    }
}
