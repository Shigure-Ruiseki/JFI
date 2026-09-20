package ruiseki.jfi.jfmuy.ae2.facade;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import appeng.items.parts.ItemFacade;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.IShapedCraftingRecipeWrapper;

public class FacadeRecipeWrapper implements IShapedCraftingRecipeWrapper {

    private final List<List<ItemStack>> inputs;
    private final ItemStack output;

    public FacadeRecipeWrapper(ItemFacade facade, ItemStack anchor, ItemStack outputItem) {
        this.output = outputItem.copy();
        this.output.stackSize = 4;

        this.inputs = new ArrayList<>(9);
        ItemStack textureItem = facade.getTextureItem(outputItem);

        Object[] rawItems = new Object[] { null, anchor, null, anchor, textureItem, anchor, null, anchor, null };

        for (Object obj : rawItems) {
            List<ItemStack> slotList = new ArrayList<>();
            if (obj instanceof ItemStack stack) {
                slotList.add(stack);
            }
            inputs.add(slotList);
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }
}
