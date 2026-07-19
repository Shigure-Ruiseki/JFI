package ruiseki.jfi.jfmuy.thermaldynamics;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import cofh.lib.util.helpers.ItemHelper;
import cofh.thermaldynamics.duct.TDDucts;
import cofh.thermaldynamics.duct.attachments.cover.CoverHelper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.ICraftingRecipeWrapper;

public class CoverRecipeWrapper implements ICraftingRecipeWrapper {

    private final ItemStack inputMaterial;
    private final ItemStack outputCover;

    public CoverRecipeWrapper(ItemStack inputMaterial) {
        this.inputMaterial = inputMaterial;
        this.outputCover = ItemHelper.cloneStack(CoverHelper.getCoverStack(inputMaterial), 6);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputs = new ArrayList<>();

        List<ItemStack> materialSlot = new ArrayList<>();
        materialSlot.add(inputMaterial);

        List<ItemStack> ductSlot = new ArrayList<>();
        ductSlot.add(TDDucts.structure.itemStack);

        inputs.add(materialSlot);
        inputs.add(ductSlot);

        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        ingredients.setOutput(VanillaTypes.ITEM, outputCover);
    }
}
