package ruiseki.jfi.jfmuy.botania.crafting;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.collect.ImmutableList;

import ruiseki.jfmuy.api.gui.IGuiItemStackGroup;
import ruiseki.jfmuy.api.gui.IRecipeLayout;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IFocus;
import ruiseki.jfmuy.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import vazkii.botania.api.item.IAncientWillContainer;
import vazkii.botania.common.Botania;
import vazkii.botania.common.crafting.recipe.AncientWillRecipe;
import vazkii.botania.common.item.ModItems;

public class AncientWillRecipeWrapper implements ICustomCraftingRecipeWrapper {

    private final List<List<ItemStack>> inputs;
    private final List<ItemStack> output;

    public AncientWillRecipeWrapper(AncientWillRecipe recipe) {

        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> helmets = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> wills = ImmutableList.builder();
        helmets.add(new ItemStack(ModItems.terrasteelHelm));
        if (Botania.thaumcraftLoaded) {
            helmets.add(new ItemStack(ModItems.terrasteelHelmRevealing));
        }
        for (int i = 0; i < 6; i++) {
            wills.add(new ItemStack(ModItems.ancientWill, 1, i));
        }

        output = helmets.build();
        builder.add(output);
        builder.add(wills.build());
        inputs = builder.build();
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutputLists(VanillaTypes.ITEM, ImmutableList.of(output));
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IIngredients ingredients) {
        IFocus<?> focus = recipeLayout.getFocus();
        IGuiItemStackGroup group = recipeLayout.getItemStacks();
        group.set(ingredients);

        if (focus != null) {
            ItemStack focused = (ItemStack) focus.getValue();

            if (focus.getMode() == IFocus.Mode.INPUT && focused.getItem() == ModItems.ancientWill) {
                group.set(2, new ItemStack(ModItems.ancientWill, 1, focused.getItemDamage()));
                group.set(0, getHelmetsWithWill(focused.getItemDamage()));
            } else if (focused.getItem() instanceof IAncientWillContainer) { // helmet
                group.set(1, new ItemStack(focused.getItem()));
                group.set(0, getWillsOnHelmet(focused.getItem()));
            }
        }
    }

    private List<ItemStack> getHelmetsWithWill(int meta) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        for (ItemStack itemStack : output) {
            ItemStack toAdd = itemStack.copy();
            ((IAncientWillContainer) toAdd.getItem()).addAncientWill(toAdd, meta);
            builder.add(toAdd);
        }
        return builder.build();
    }

    private List<ItemStack> getWillsOnHelmet(Item item) {
        if (item instanceof IAncientWillContainer) {
            ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
            for (int i = 0; i < 6; i++) {
                ItemStack stack = new ItemStack(item);
                ((IAncientWillContainer) item).addAncientWill(stack, i);
                builder.add(stack);
            }
            return builder.build();
        }
        return ImmutableList.of();
    }
}
