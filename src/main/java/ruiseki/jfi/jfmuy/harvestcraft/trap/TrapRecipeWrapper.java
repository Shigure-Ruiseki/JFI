package ruiseki.jfi.jfmuy.harvestcraft.trap;

import net.minecraft.item.ItemStack;

import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.IRecipeWrapper;

public abstract class TrapRecipeWrapper implements IRecipeWrapper {

    private final ItemStack in;
    private final ItemStack out;
    private final double chance;

    public TrapRecipeWrapper(ItemStack in, ItemStack out, double chance) {
        this.in = in;
        this.out = out;
        this.chance = chance;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, in);
        ingredients.setOutput(VanillaTypes.ITEM, out);
    }

    public double getChance() {
        return chance;
    }

    public static class Ground extends TrapRecipeWrapper {

        public Ground(ItemStack in, ItemStack out, double chance) {
            super(in, out, chance);
        }
    }

    public static class Water extends TrapRecipeWrapper {

        public Water(ItemStack in, ItemStack out, double chance) {
            super(in, out, chance);
        }
    }
}
