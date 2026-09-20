package ruiseki.jfi.jfmuy.ae2.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import appeng.api.exceptions.MissingIngredientError;
import appeng.api.exceptions.RegistrationError;
import appeng.api.recipes.IIngredient;
import appeng.core.AEConfig;
import appeng.recipes.game.ShapelessRecipe;
import appeng.util.Platform;
import ruiseki.jfmuy.api.ingredients.IIngredients;
import ruiseki.jfmuy.api.ingredients.VanillaTypes;
import ruiseki.jfmuy.api.recipe.wrapper.ICraftingRecipeWrapper;

public class AEShapelessRecipeWrapper implements ICraftingRecipeWrapper {

    private final ShapelessRecipe recipe;
    private final List<List<ItemStack>> inputs;
    private final ItemStack output;

    public AEShapelessRecipeWrapper(ShapelessRecipe recipe) {
        this.recipe = recipe;
        this.output = recipe.getRecipeOutput();
        this.inputs = new ArrayList<>();

        Object[] items = recipe.getInput()
            .toArray();
        final boolean useSingleItems = AEConfig.instance.disableColoredCableRecipesInNEI();

        for (Object item : items) {
            if (item instanceof IIngredient ing) {
                try {
                    ItemStack[] is = ing.getItemStackSet();
                    Object preferredObj = useSingleItems ? Platform.findPreferred(is) : is;
                    List<ItemStack> expanded = new ArrayList<>();

                    if (preferredObj instanceof ItemStack preferred) {
                        expanded.add(preferred);
                    } else if (preferredObj instanceof ItemStack[]preferredArray) {
                        Collections.addAll(expanded, preferredArray);
                    } else if (is != null) {
                        Collections.addAll(expanded, is);
                    }

                    if (!expanded.isEmpty()) {
                        this.inputs.add(expanded);
                    }
                } catch (RegistrationError | MissingIngredientError ignored) {}
            }
        }
    }

    public ShapelessRecipe getRecipe() {
        return recipe;
    }

    public boolean isValid() {
        return !inputs.isEmpty() && output != null;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
