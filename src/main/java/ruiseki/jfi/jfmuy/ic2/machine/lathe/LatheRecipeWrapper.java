package ruiseki.jfi.jfmuy.ic2.machine.lathe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ic2.api.item.ILatheItem;
import ic2.core.Ic2Items;
import ic2.core.block.machine.gui.GuiLathe;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public class LatheRecipeWrapper implements IRecipeWrapper {

    private final ItemStack latheStack;

    public LatheRecipeWrapper(ItemStack latheStack) {
        this.latheStack = latheStack;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputs = new ArrayList<>();

        List<ItemStack> inputStackList = new ArrayList<>();
        inputStackList.add(this.latheStack);
        inputs.add(inputStackList);

        List<ItemStack> toolList = new ArrayList<>();
        toolList.add(Ic2Items.LathingTool);
        inputs.add(toolList);

        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        if (this.latheStack.getItem() instanceof ILatheItem) {
            ILatheItem item = (ILatheItem) this.latheStack.getItem();
            ItemStack outputByproduct = item.getOutputItem(this.latheStack, 0);
            if (outputByproduct != null) {
                ingredients.setOutput(VanillaTypes.ITEM, outputByproduct);
            }
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (!(this.latheStack.getItem() instanceof ILatheItem)) {
            return;
        }

        ILatheItem item = (ILatheItem) this.latheStack.getItem();
        int[] state = item.getCurrentState(this.latheStack);
        int segLength = 24;
        int max = item.getWidth(this.latheStack);

        GL11.glPushMatrix();

        for (int i = 0; i < 5; ++i) {
            String text = StatCollector.translateToLocalFormatted("ic2.Lathe.gui.info", state[i], max);
            minecraft.fontRenderer.drawString(text, 30 + segLength * i, 1, 4210752);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiLathe.renderILatheItemIntoGUI(this.latheStack, 30, 19);

        GL11.glPopMatrix();
    }
}
