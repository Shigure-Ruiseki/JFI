package ruiseki.jfi.jfmuy.thermaldynamics.crafting;

import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableList;

import cofh.lib.util.helpers.ItemHelper;
import cofh.thermaldynamics.duct.TDDucts;
import cofh.thermaldynamics.duct.attachments.cover.CoverHelper;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.ICraftingRecipeWrapper;

public class CoverRecipeWrapper implements ICraftingRecipeWrapper {

    protected final ItemStack coverStack;
    protected final ItemStack coverBlock;

    public CoverRecipeWrapper(ItemStack cover) {

        coverStack = ItemHelper.cloneStack(cover, 6);
        coverBlock = CoverHelper.getCoverItemStack(coverStack, false);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        ingredients.setInputs(VanillaTypes.ITEM, ImmutableList.of(coverBlock, TDDucts.structure.itemStack));
        ingredients.setOutputs(VanillaTypes.ITEM, ImmutableList.of(coverStack));
    }

}
