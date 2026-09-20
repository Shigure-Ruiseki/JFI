package ruiseki.jfi.jfmuy.ae2.worldcrafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class WorldCraftingRecipeWrapper implements IRecipeWrapper {

    private final ItemStack output;
    private final String details;

    public WorldCraftingRecipeWrapper(ItemStack output, String details) {
        this.output = output;
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer fr = minecraft.fontRenderer;
        fr.drawSplitString(details, 10, 25, 150, 0x404040);
    }
}
